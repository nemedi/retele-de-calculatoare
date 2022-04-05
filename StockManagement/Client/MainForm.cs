using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Web.Script.Serialization;
using System.Windows.Forms;
using Common;
using Rpc;

namespace Client
{
	public partial class MainForm : Form, IClientService
	{
		private static MainForm instance;
		private RpcHost host;
		private Proxy proxy;
		private IDictionary<int, Resource> resources;
		private IDictionary<int, TreeNode> nodes;
		private IDictionary<Type, ResourceControl> controls;
		private ResourceControl control;

		public MainForm()
		{
			InitializeComponent();
			resources = new Dictionary<int, Resource>();
			controls = new Dictionary<Type, ResourceControl>();
			nodes = new Dictionary<int, TreeNode>();
		}

		public static MainForm Instance
		{
			get
			{
				if (instance == null)
				{
					lock (typeof(MainForm))
					{
						if (instance == null)
						{
							instance = new MainForm();
						}
					}
				}
				return instance;
			}
		}

		internal Proxy Proxy
		{
			get { return proxy; }
		}

		public Resource GetResource(int resourceId)
		{
			return resources.ContainsKey(resourceId)
				? resources[resourceId]
				: null;
		}

		public TreeNode GetNodeByResource(int resourceId)
		{
			return nodes.ContainsKey(resourceId)
				? nodes[resourceId]
				: null;
		}

		private void CreateControls()
		{
			controls[typeof(Category)] = new CategoryControl();
			controls[typeof(Product)] = new ProductControl();
			controls[typeof(Item)] = new ItemControl();
			outerPanel.SuspendLayout();
			foreach (var control in controls.Values)
			{
				control.Visible = false;
				control.Dock = DockStyle.Fill;
				groupBox.Controls.Add(control);
			}
			outerPanel.ResumeLayout();
		}

		private void CreateServiceHost()
		{
			var service = new Service(this);
			var hostname = Settings.Default.ClientEndpoint.Split(':')[0];
			var port = int.Parse(Settings.Default.ClientEndpoint.Split(':')[1]);
			for (int i = 0; i < 100; i++)
			{
				try
				{
					host = new RpcHost(service, string.Format("{0}:{1}", hostname, port + i));
					break;
				}
				catch
				{
					continue;
				}
			}
		}

		private void LoadResources()
		{
			var categories = proxy.GetCategories();
			treeView.SuspendLayout();
			treeView.Nodes.Add(new TreeNode { Text = "<New Category>", Tag = typeof(Category), ImageIndex = 0, SelectedImageIndex = 0 });
			if (categories != null)
			{
				foreach (var category in categories)
				{
					var categoryNode = new TreeNode { Text = category.Name, Tag = category, ImageIndex = 0, SelectedImageIndex = 0 };
					categoryNode.Nodes.Add(new TreeNode { Text = "<New Product>", Tag = typeof(Product), ImageIndex = 1, SelectedImageIndex = 1 });
					var products = proxy.GetProductsByCategory(category.Id);
					if (products != null)
					{
						foreach (var product in products)
						{
							var productNode = new TreeNode { Text = product.Name, Tag = product, ImageIndex = 1, SelectedImageIndex = 1 };
							productNode.Nodes.Add(new TreeNode { Text = "<New Item>", Tag = typeof(Item), ImageIndex = 2, SelectedImageIndex = 2 });
							var items = proxy.GetItemsByProduct(product.Id);
							if (items != null)
							{
								foreach (var item in items)
								{
									var itemNode = new TreeNode { Text = item.Name, Tag = item, ImageIndex = 2, SelectedImageIndex = 2 };
									productNode.Nodes.Add(itemNode);
									nodes[item.Id] = itemNode;
									resources[item.Id] = item;
								}
							}
							categoryNode.Nodes.Add(productNode);
							nodes[product.Id] = productNode;
							resources[product.Id] = product;
						}
					}
					treeView.Nodes.Add(categoryNode);
					nodes[category.Id] = categoryNode;
					resources[category.Id] = category;
				}
			}
			treeView.ResumeLayout();
			treeView.ExpandAll();
		}

		private void MainForm_Load(object sender, EventArgs e)
		{
			try
			{
				CreateControls();
				CreateServiceHost();
				proxy = new Proxy(Settings.Default.ServerEndpoint);
				proxy.RegisterClient(Settings.Default.ClientEndpoint);
				LoadResources();
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		private void MainForm_FormClosed(object sender, FormClosedEventArgs e)
		{
			try
			{
				if (control != null && control.Resource != null)
				{
					proxy.UnlockResource(control.Resource.Id);
				}
				proxy.UnregisterClient(Settings.Default.ClientEndpoint);
				proxy.Dispose();
				host.Dispose();
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		public void SelectNode(TreeNode node)
		{
			if (node != null)
			{
				treeView.SelectedNode = node;
			}
		}

		private void treeView_AfterSelect(object sender, TreeViewEventArgs e)
		{
			try
			{
				if (control != null
					&& control.Resource != null)
				{
					proxy.UnlockResource(control.Resource.Id);
				}
				var type = e.Node.Tag is Type
					? e.Node.Tag as Type
					: (e.Node.Tag is Resource
						? (e.Node.Tag as Resource).GetType()
						: null);
				var resource = e.Node.Tag is Resource
					? e.Node.Tag as Resource
					: (type != null
						? Activator.CreateInstance(type) as Resource
						: null);
				if (e.Node.Parent != null && e.Node.Parent.Tag is Resource)
				{
					if (resource is Product)
					{
						var product = resource as Product;
						if (product.CategoryId == 0 && e.Node.Parent.Tag is Category)
						{
							product.CategoryId = (e.Node.Parent.Tag as Category).Id;
						}
					}
					else if (resource is Item)
					{
						var item = resource as Item;
						if (item.ProductId == 0 && e.Node.Parent.Tag is Product)
						{
							item.ProductId = (e.Node.Parent.Tag as Product).Id;
						}
					}
				}
				if (type == null
					|| resource == null
					|| !controls.ContainsKey(type))
				{
					return;
				}
				var locked = proxy.LockResource(resource.Id);
				outerPanel.Visible = e.Node != null;
				saveButton.Visible = locked;
				removeButton.Visible = locked && e.Node.Tag is Resource;
				groupBox.Text = e.Node.Text;
				control = controls[type];
				control.Editable = locked;
				control.Visible = true;
				control.BringToFront();
				control.Node = e.Node;
				control.Resource = resource;
				control.Focus();
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		private void saveButton_Click(object sender, EventArgs e)
		{
			try
			{
				if (control != null)
				{
					control.PerformSave();
					MessageBox.Show(this, "The object was saved.", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		private void removeButton_Click(object sender, EventArgs e)
		{
			try
			{
				if (control != null
					&& MessageBox.Show(this.ParentForm,
					 "Are you sure you want to remove the current object?",
					 "Confirmation",
					 MessageBoxButtons.YesNo,
					 MessageBoxIcon.Question) == DialogResult.Yes)
				{
					control.PerformRemove();
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		public void OnCategoryAdded(Category category)
		{
			if (category == null)
			{
				return;
			}
			var categoryNode = new TreeNode { Text = category.Name, Tag = category, ImageIndex = 0, SelectedImageIndex = 0 };
			categoryNode.Nodes.Add(new TreeNode { Text = "<New Product>", Tag = typeof(Product), ImageIndex = 0, SelectedImageIndex = 0 });
			treeView.SuspendLayout();
			treeView.Nodes.Add(categoryNode);
			treeView.ResumeLayout();
			nodes[category.Id] = categoryNode;
			categoryNode.ExpandAll();
		}

		public void OnCategoryUpdated(Category category)
		{
			OnResourceUpdated(category);
		}

		public void OnCategoryRemoved(int categoryId)
		{
			OnResourceRemoved(categoryId);
		}

		public void OnProductAdded(Product product)
		{
			if (product == null)
			{
				return;
			}
			var categoryNode = GetNodeByResource(product.CategoryId);
			if (categoryNode == null || !(categoryNode.Tag is Category))
			{
				return;
			}
			(categoryNode.Tag as Category).Products.Add(product);
			var productNode = new TreeNode { Text = product.Name, Tag = product, ImageIndex = 1, SelectedImageIndex = 1 };
			productNode.Nodes.Add(new TreeNode { Text = "<New Item>", Tag = typeof(Item), ImageIndex = 1, SelectedImageIndex = 1});
			var items = product.Items;
			if (items != null)
			{
				foreach (var item in items)
				{
					var itemNode = new TreeNode { Text = item.Name, Tag = item, ImageIndex = 2, SelectedImageIndex = 2};
					productNode.Nodes.Add(itemNode);
					nodes[item.Id] = itemNode;
				}
			}
			treeView.SuspendLayout();
			categoryNode.Nodes.Add(productNode);
			treeView.ResumeLayout();
			nodes[product.Id] = productNode;
			productNode.ExpandAll();
		}

		public void OnProductUpdated(Product product)
		{
			OnResourceUpdated(product);
		}

		public void OnProductRemoved(int productId)
		{
			OnResourceRemoved(productId);
		}

		public void OnItemAdded(Item item)
		{
			if (item == null)
			{
				return;
			}
			var productNode = GetNodeByResource(item.ProductId);
			if (productNode == null || !(productNode.Tag is Product))
			{
				return;
			}
			(productNode.Tag as Product).Items.Add(item);
			var itemNode = new TreeNode { Text = item.Name, Tag = item, ImageIndex = 2, SelectedImageIndex = 2};
			treeView.SuspendLayout();
			productNode.Nodes.Add(itemNode);
			treeView.ResumeLayout();
			nodes[item.Id] = itemNode;
		}

		public void OnItemUpdated(Item item)
		{
			OnResourceUpdated(item);
		}

		public void OnItemRemoved(int itemId)
		{
			OnResourceRemoved(itemId);
		}

		private void OnResourceUpdated(Resource resource)
		{
			if (resource == null
							 || !nodes.ContainsKey(resource.Id)
							 || nodes[resource.Id] == null)
			{
				return;
			}
			nodes[resource.Id].Text = resource.Name;
			resources[resource.Id] = resource;
		}

		private void OnResourceRemoved(int resourceId)
		{
			if (!nodes.ContainsKey(resourceId)
							 || nodes[resourceId] == null)
			{
				return;
			}
			treeView.SuspendLayout();
			var node = nodes[resourceId];
			if (node.Parent != null)
			{
				if (node.Parent.Tag is Category)
				{
					(node.Parent.Tag as Category).RemoveProduct(resourceId);
				}
				else if (node.Parent.Tag is Product)
				{
					(node.Parent.Tag as Product).RemoveItem(resourceId);
				}
				node.Parent.Nodes.Remove(node);
			}
			else
			{
				treeView.Nodes.Remove(node);
			}
			treeView.ResumeLayout();
			if (resources.ContainsKey(resourceId))
			{
				resources.Remove(resourceId);
			}
		}

		private void exitToolStripMenuItem_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		private void configurationToolStripMenuItem_Click(object sender, EventArgs e)
		{
			var dialog = new ConfigurationForm();
			dialog.ShowDialog(this);
		}

		private void exportToFileToolStripMenuItem_Click(object sender, EventArgs e)
		{
			try
			{
				if (saveFileDialog.ShowDialog(this) == DialogResult.OK)
				{
					var categories = new List<Category>();
					foreach (TreeNode node in treeView.Nodes)
					{
						if (node.Tag is Category)
						{
							categories.Add(node.Tag as Category);
						}
					}
					var serializer = new JavaScriptSerializer();
					var content = serializer.Serialize(categories.ToArray());
					content = FormatOutput(content);
					File.WriteAllText(saveFileDialog.FileName, content);
					MessageBox.Show(this, "Repository was exported to file.", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		public static string FormatOutput(string jsonString)
		{
			var stringBuilder = new StringBuilder();

			bool escaping = false;
			bool inQuotes = false;
			int indentation = 0;

			foreach (char character in jsonString)
			{
				if (escaping)
				{
					escaping = false;
					stringBuilder.Append(character);
				}
				else
				{
					if (character == '\\')
					{
						escaping = true;
						stringBuilder.Append(character);
					}
					else if (character == '\"')
					{
						inQuotes = !inQuotes;
						stringBuilder.Append(character);
					}
					else if (!inQuotes)
					{
						if (character == ',')
						{
							stringBuilder.Append(character);
							stringBuilder.Append("\r\n");
							stringBuilder.Append('\t', indentation);
						}
						else if (character == '[' || character == '{')
						{
							stringBuilder.Append(character);
							stringBuilder.Append("\r\n");
							stringBuilder.Append('\t', ++indentation);
						}
						else if (character == ']' || character == '}')
						{
							stringBuilder.Append("\r\n");
							stringBuilder.Append('\t', --indentation);
							stringBuilder.Append(character);
						}
						else if (character == ':')
						{
							stringBuilder.Append(character);
							stringBuilder.Append('\t');
						}
						else
						{
							stringBuilder.Append(character);
						}
					}
					else
					{
						stringBuilder.Append(character);
					}
				}
			}

			return stringBuilder.ToString();
		}

		private void importFromFileToolStripMenuItem_Click(object sender, EventArgs e)
		{
			try
			{
				if (openFileDialog.ShowDialog(this) == DialogResult.OK)
				{
					var content = File.ReadAllText(openFileDialog.FileName);
					var categories = new JavaScriptSerializer().Deserialize<Category[]>(content);
					if (categories != null)
					{
						foreach (var category in categories)
						{
							var importedCategory = proxy.AddCategory(category);
							foreach (var product in category.Products)
							{
								product.CategoryId = importedCategory.Id;
								var importedProduct = proxy.AddProduct(product);
								foreach (var item in product.Items)
								{
									item.ProductId = importedProduct.Id;
									proxy.AddItem(item);
								}
							}
						}
						MessageBox.Show(this, "Repository was imported from file.", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
					}
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}
	}
}
