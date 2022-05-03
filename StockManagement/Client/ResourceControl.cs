using System.Reflection;
using System.Windows.Forms;
using Common;

namespace Client
{

	public partial class ResourceControl : UserControl
	{
		private Resource resource;

		public ResourceControl()
		{
			InitializeComponent();
		}

		public TreeNode Node { get; set; }

		public Resource Resource
		{
			get
			{
				return resource;
			}
			set
			{
				resource = value;
				foreach (var control in Controls)
				{
					if (control is IBindableComponent)
					{
						(control as IBindableComponent).DataBindings.Clear();
					}
				}
				ConfigureDataBindings(resource);
			}
		}

		public bool Editable
		{
			set
			{
				foreach (var control in Controls)
				{
					if (control is TextBox)
					{
						(control as TextBox).ReadOnly =
							(control as TextBox).Tag as string == "false"
							|| !value;
					}
					else if (control is ComboBox)
					{
						(control as ComboBox).Enabled = 
							(control as ComboBox).Tag as string != "false"
							&& value;
					}
					else if (control is DateTimePicker)
					{
						(control as DateTimePicker).Enabled =
							(control as DateTimePicker).Tag as string != "false"
							&& value;
					}
				}
			}
		}

		protected virtual ResourceManager Manager
		{
			get
			{
				return new ResourceManager();
			}
		}

		protected virtual void ConfigureDataBindings(Resource resource)
		{
		}

		public void PerformSave()
		{
			if (resource == null)
			{
				return;
			}
			if (Resource.Id == 0)
			{
				resource = Manager.AddResource(resource);
			}
			else if (Resource.Id > 0)
			{
				Manager.UpdateResource(resource);
			}
		}

		public void PerformRemove()
		{
			if (resource != null && resource.Id > 0)
			{
				Manager.RemoveResource(resource.Id);
			}
		}

	}
}
