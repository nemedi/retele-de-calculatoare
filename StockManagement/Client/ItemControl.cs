
using System;
using System.Windows.Forms;
using Common;

namespace Client
{
	public partial class ItemControl : ResourceControl
	{
		public ItemControl()
		{
			InitializeComponent();
		}

		protected override ResourceManager Manager
		{
			get
			{
				return new ResourceManager("AddItem", "UpdateItem", "RemoveItem");
			}
		}

		protected override void ConfigureDataBindings(Resource resource)
		{
			if (!(resource is Item))
			{
				return;
			}
			var item = resource as Item;
			nameTextBox.DataBindings.Add("Text", item, "Name");
			var product = MainForm.Instance.GetResource(item.ProductId);
			if (product is Product)
			{
				productTextBox.DataBindings.Add("Text", product, "Name");
				var category = MainForm.Instance.GetResource((product as Product).CategoryId);
				if (category is Category)
				{
					categoryTextBox.DataBindings.Add("Text", category, "Name");
				}
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
			var stateBinding = new Binding("SelectedIndex", item, "State");
			
			stateBinding.Format += (sender, args) => args.Value = (int) ((State) args.Value);
			stateBinding.Parse += (sender, args) => args.Value = (State) args.Value;
			stateComboBox.DataBindings.Add(stateBinding);
			lastUpdatedDateTimePicker.DataBindings.Add("Value", resource, "LastUpdated");
		}
	}
}
