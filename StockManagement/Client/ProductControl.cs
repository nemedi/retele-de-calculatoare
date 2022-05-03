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
	public partial class ProductControl : ResourceControl
	{
		public ProductControl()
		{
			InitializeComponent();
		}

		protected override ResourceManager Manager
		{
			get
			{
				return new ResourceManager("AddProduct", "UpdateProduct", "RemoveProduct");
			}
		}

		protected override void ConfigureDataBindings(Resource resource)
		{
			if (!(resource is Product))
			{
				return;
			}
			var product = resource as Product;
			nameTextBox.DataBindings.Add("Text", product, "Name");
			var category = MainForm.Instance.GetResource(product.CategoryId);
			if (category is Category)
			{
				categoryTextBox.DataBindings.Add("Text", category, "Name");
			}
			var priceBinding = new Binding("Text", resource, "Price");
			priceBinding.Format += (sender, args) => args.Value = args.Value != null
				? args.Value.ToString()
				: string.Empty;
			priceBinding.Parse += (sender, args) =>
			                 {
				                 decimal value;
				                 if (decimal.TryParse(args.Value.ToString(), out value))
				                 {
					                 args.Value = value;
				                 }
			                 };
			priceTextBox.DataBindings.Add(priceBinding);
			lastUpdatedDateTimePicker.DataBindings.Add("Value", resource, "LastUpdated");
			itemsListView.SuspendLayout();
			itemsListView.Items.Clear();
			foreach (var item in product.Items)
			{
				var listViewItem = new ListViewItem(new string[]
				                                    {
					                                    item.Name,
																	item.Price.ToString()
				                                    });
				listViewItem.Tag = item;
				itemsListView.Items.Add(listViewItem);
			}
			itemsListView.ResumeLayout();
		}

		private void itemsListView_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			try
			{
				if (itemsListView.SelectedItems.Count == 1
					&& itemsListView.SelectedItems[0].Tag is Item)
				{
					var item = itemsListView.SelectedItems[0].Tag as Item;
					var node = MainForm.Instance.GetNodeByResource(item.Id);
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
