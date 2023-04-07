namespace Client
{
    public partial class MainForm : Form
    {

        public MainForm()
        {
            InitializeComponent();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            DataContext = new MainController(this);
            nameTextBox.DataBindings.Add("Enabled", DataContext, "Inactive");
            nameTextBox.DataBindings.Add("Text", DataContext, "Name");
            usersListBox.DataBindings.Add("DataSource", DataContext, "Users");
            messagesListBox.DataBindings.Add("DataSource", DataContext, "Messages");
            toggleLoginButton.DataBindings.Add("Text", DataContext, "ButtonText");
            toggleLoginButton.DataBindings.Add("Command", DataContext, "ToggleLoginCommand", true);
            usersListBox.DataBindings.Add("Enabled", DataContext, "Active");
            usersListBox.DataBindings.Add("SelectedItem", DataContext, "To", true);
            messagesListBox.DataBindings.Add("Enabled", DataContext, "Active");
            messageTextBox.DataBindings.Add("Enabled", DataContext, "Active");
            messageTextBox.DataBindings.Add("Text", DataContext, "Message");
            sendMessageButton.DataBindings.Add("Enabled", DataContext, "Active");
            sendMessageButton.DataBindings.Add("Command", DataContext, "SendMessageCommand", true);
        }
    }
}