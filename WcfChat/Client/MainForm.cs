using System;
using System.Windows.Forms;
using Common;

namespace Client
{
	public partial class MainForm : Form, IChatCallback
	{
		private ChatProxy proxy;

		public MainForm()
		{
			InitializeComponent();
		}

		public void OnAccept(string[] names)
		{
			tbxName.Enabled = false;
			lsbUsers.Items.Clear();
			lsbUsers.Items.AddRange(names);
			lsbMessages.Items.Clear();
			tbxMessage.Text = string.Empty;
			tbxMessage.Focus();
			lsbUsers.Enabled =
				lsbMessages.Enabled =
				tbxMessage.Enabled =
				true;
			lsbUsers.SelectedIndex = 0;
			btnLogin.Text = "Logout";
		}

		public void OnAddUser(string name)
		{
			lsbUsers.Items.Add(name);
		}

		public void OnDeny()
		{
			
		}

		public void OnExit()
		{
			tbxName.Enabled = true;
			tbxName.Focus();
			lsbUsers.Items.Clear();
			lsbMessages.Items.Clear();
			tbxMessage.Text = string.Empty;
			lsbUsers.Enabled =
				lsbMessages.Enabled =
				tbxMessage.Enabled =
				false;
			btnLogin.Text = "Login";
		}

		public void OnReceive(string from, string message)
		{
			lsbMessages.Items.Add($"{from}: {message}");
		}

		public void OnRemoveUser(string name)
		{
			lsbUsers.Items.Remove(name);
		}

		private void MainForm_Load(object sender, EventArgs e)
		{
			try
			{
				proxy = new ChatProxy(this);
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		private void btnLogin_Click(object sender, EventArgs e)
		{
			try
			{
				switch (btnLogin.Text)
				{
					case "Login":
						if (tbxName.Text.Trim().Length > 0)
						{
							proxy.Login(tbxName.Text.Trim());
						}
						break;
					case "Logout":
						proxy.Logout();
						break;
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		private void MainForm_FormClosed(object sender, FormClosedEventArgs e)
		{
			proxy.Logout();
		}

		private void tbxMessage_KeyUp(object sender, KeyEventArgs e)
		{
			try
			{
				if (e.KeyCode == Keys.Enter
								&& tbxMessage.Text.Trim().Length > 0
								&& lsbUsers.SelectedItem is string)
				{
					proxy.Send(lsbUsers.SelectedItem as string, tbxMessage.Text.Trim());
					tbxMessage.Text = string.Empty;
				}
			}
			catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}
	}
}
