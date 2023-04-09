using System;
using System.Windows.Forms;

namespace Client
{
    public partial class MainForm : Form
    {
        MainController controller;
        public MainForm()
        {
            InitializeComponent();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            controller = new MainController(this);
            nameTextBox.DataBindings.Add("Enabled", controller, "Inactive");
            nameTextBox.DataBindings.Add("Text", controller, "Name");
            usersListBox.DataBindings.Add("DataSource", controller, "Users");
            messagesListBox.DataBindings.Add("DataSource", controller, "Messages");
            toggleLoginButton.DataBindings.Add("Text", controller, "ButtonText");
            usersListBox.DataBindings.Add("Enabled", controller, "Active");
            usersListBox.DataBindings.Add("SelectedItem", controller, "To", true);
            messagesListBox.DataBindings.Add("Enabled", controller, "Active");
            messageTextBox.DataBindings.Add("Enabled", controller, "Active");
            messageTextBox.DataBindings.Add("Text", controller, "Message");
            sendMessageButton.DataBindings.Add("Enabled", controller, "Active");
        }

        private void toggleLoginButton_Click(object sender, EventArgs e)
        {
            controller.ToggleLoginCommand.Execute(null);
        }

        private void sendMessageButton_Click(object sender, EventArgs e)
        {
            controller.SendMessageCommand.Execute(null);
        }
    }
}