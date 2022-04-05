using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Common;

namespace Client
{
	public partial class CategoryControl : ResourceControl
	{
		public CategoryControl()
		{
			InitializeComponent();
		}

		protected override ResourceManager Manager
		{
			get
			{
				return new ResourceManager("AddCategory", "UpdateCategory", "RemoveCategory");
			}
		}

		protected override void ConfigureDataBindings(Resource resource)
		{
			if (!(resource is Category))
			{
				return;
			}
			var category = resource as Category;
			nameTextBox.DataBindings.Add("Text", category, "Name");
			lastUpdatedDateTimePicker.DataBindings.Add("Value", category, "LastUpdated");
			productsListView.SuspendLayout();
			productsListView.Items.Clear();
			foreach (var product in category.Products)
			{
				var listViewItem = new ListViewItem(new string[]
				                                    {
					                                    product.Name,
																	product.Price.ToString()
				                                    });
				listViewItem.Tag = product;
				productsListView.Items.Add(listViewItem);
			}
			productsListView.ResumeLayout();
		}

		private void productsListView_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			try
			{
				if (productsListView.SelectedItems.Count == 1
					&& productsListView.SelectedItems[0].Tag is Product)
				{
					var product = productsListView.SelectedItems[0].Tag as Product;
					var node = MainForm.Instance.GetNodeByResource(product.Id);
					MainForm.Instance.SelectNode(node);
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

	}
}
