using System;
using System.Windows.Forms;
using System.Net.Mail;
using System.Net;

namespace SendMail
{
	public partial class MainForm : Form
	{
		public MainForm()
		{
			InitializeComponent();
		}

		private async void button_Click(object sender, EventArgs e)
		{
			try
			{
				if (textBoxFrom.Text.Trim().Length > 0
					&& textBoxTo.Text.Trim().Length > 0
					&& textBoxSubject.Text.Length > 0
					&& textBoxBody.Text.Trim().Length > 0)
				{
					var message = new MailMessage {
						From = new MailAddress(textBoxFrom.Text.Trim()),
						Subject = textBoxSubject.Text.Trim(),
						Body = textBoxBody.Text.Trim(),
						IsBodyHtml = true
					};
					foreach (var to in textBoxTo.Text.Trim().Split(','))
					{
						message.To.Add(new MailAddress(to.Trim()));
					}
					using (var client = new SmtpClient {
							Host = Settings.Default.Host,
							Port = Settings.Default.Port,
							EnableSsl = false,
							DeliveryMethod = SmtpDeliveryMethod.Network,
							Credentials = new NetworkCredential
							{
								UserName = Settings.Default.User,
								Password = Settings.Default.Password
							}
						}
					)
					{
						client.SendCompleted += (target, arguments) =>
						{
							if (arguments.Error == null)
							{
								if (arguments.Cancelled)
								{
									MessageBox.Show(this, "Message was cancled.", "Warning",
										MessageBoxButtons.OK, MessageBoxIcon.Warning);
								}
								else
								{
									MessageBox.Show(this, "Message was sent.", "Information",
										MessageBoxButtons.OK, MessageBoxIcon.Information);
								}
								
							}
							else
							{
								MessageBox.Show(this, arguments.Error.Message, "Error",
									MessageBoxButtons.OK, MessageBoxIcon.Error);
							}
							
						};
						await client.SendMailAsync(message);
					}
				}
			} catch (Exception exception)
			{
				MessageBox.Show(this, exception.Message, "Error",
					MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}
	}
}
