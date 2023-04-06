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
            DataContext = new MainController();
            nameTextBox.DataBindings.Add("Enabled", DataContext, "Inactive");
            usersListBox.DataBindings.Add("DataSource", DataContext, "Users");
            messagesListBox.DataBindings.Add("DataSource", DataContext, "Messages");
            button.DataBindings.Add("Text", DataContext, "ButtonText");
            usersListBox.DataBindings.Add("Enabled", DataContext, "Active");
            messagesListBox.DataBindings.Add("Enabled", DataContext, "Active");
            messageTextBox.DataBindings.Add("Enabled", DataContext, "Active");
        }

        private void messageTextBox_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter && DataContext is MainController controller)
            {
                controller.SendMessageCommand.Execute(null);
            }
        }

        private void usersListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (DataContext is MainController controller)
            {
                controller.SelectedUserIndex = usersListBox.SelectedIndex;
            }
        }

        private void button_Click(object sender, EventArgs e)
        {
            if (DataContext is MainController controller)
            {
                controller.ToggleLoginCommand.Execute(null);
            }
        }
    }
}