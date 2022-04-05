using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
	public partial class ConfigurationForm : Form
	{
		public ConfigurationForm()
		{
			InitializeComponent();
		}

		private void ConfigurationForm_Load(object sender, EventArgs e)
		{
			try
			{
				var type = typeof (Settings);
				listView.SuspendLayout();
				foreach (var property in type.GetProperties(BindingFlags.DeclaredOnly | ~BindingFlags.Static))
				{
					var value = property.GetValue(Settings.Default);
					var listViewItem = new ListViewItem(new string[]
					                                    {
																		property.Name,
																		value != null ? value.ToString() : string.Empty
					                                    });
					listView.Items.Add(listViewItem);
				}
				listView.ResumeLayout();
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}
	}
}
