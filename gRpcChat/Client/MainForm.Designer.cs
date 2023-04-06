namespace Client
{
    partial class MainForm
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            Label nameLabel;
            SplitContainer splitContainer;
            Label messageLabel;
            usersListBox = new ListBox();
            messagesListBox = new ListBox();
            nameTextBox = new TextBox();
            button = new Button();
            messageTextBox = new TextBox();
            nameLabel = new Label();
            splitContainer = new SplitContainer();
            messageLabel = new Label();
            ((System.ComponentModel.ISupportInitialize)splitContainer).BeginInit();
            splitContainer.Panel1.SuspendLayout();
            splitContainer.Panel2.SuspendLayout();
            splitContainer.SuspendLayout();
            SuspendLayout();
            // 
            // nameLabel
            // 
            nameLabel.Location = new Point(12, 9);
            nameLabel.Name = "nameLabel";
            nameLabel.Size = new Size(60, 23);
            nameLabel.TabIndex = 0;
            nameLabel.Text = "Name";
            nameLabel.TextAlign = ContentAlignment.MiddleLeft;
            // 
            // splitContainer
            // 
            splitContainer.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            splitContainer.Location = new Point(12, 38);
            splitContainer.Name = "splitContainer";
            // 
            // splitContainer.Panel1
            // 
            splitContainer.Panel1.Controls.Add(usersListBox);
            // 
            // splitContainer.Panel2
            // 
            splitContainer.Panel2.Controls.Add(messagesListBox);
            splitContainer.Size = new Size(985, 556);
            splitContainer.SplitterDistance = 328;
            splitContainer.TabIndex = 3;
            // 
            // usersListBox
            // 
            usersListBox.Dock = DockStyle.Fill;
            usersListBox.FormattingEnabled = true;
            usersListBox.ItemHeight = 15;
            usersListBox.Location = new Point(0, 0);
            usersListBox.Name = "usersListBox";
            usersListBox.Size = new Size(328, 556);
            usersListBox.TabIndex = 0;
            usersListBox.SelectedIndexChanged += usersListBox_SelectedIndexChanged;
            // 
            // messagesListBox
            // 
            messagesListBox.Dock = DockStyle.Fill;
            messagesListBox.FormattingEnabled = true;
            messagesListBox.ItemHeight = 15;
            messagesListBox.Location = new Point(0, 0);
            messagesListBox.Name = "messagesListBox";
            messagesListBox.Size = new Size(653, 556);
            messagesListBox.TabIndex = 0;
            // 
            // messageLabel
            // 
            messageLabel.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            messageLabel.Location = new Point(12, 599);
            messageLabel.Name = "messageLabel";
            messageLabel.Size = new Size(60, 23);
            messageLabel.TabIndex = 4;
            messageLabel.Text = "Message";
            messageLabel.TextAlign = ContentAlignment.MiddleLeft;
            // 
            // nameTextBox
            // 
            nameTextBox.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;
            nameTextBox.Location = new Point(78, 9);
            nameTextBox.Name = "nameTextBox";
            nameTextBox.Size = new Size(838, 23);
            nameTextBox.TabIndex = 1;
            // 
            // button
            // 
            button.Anchor = AnchorStyles.Top | AnchorStyles.Right;
            button.Location = new Point(922, 8);
            button.Name = "button";
            button.Size = new Size(75, 23);
            button.TabIndex = 2;
            button.Text = "Login";
            button.UseVisualStyleBackColor = true;
            button.Click += button_Click;
            // 
            // messageTextBox
            // 
            messageTextBox.Anchor = AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            messageTextBox.Location = new Point(78, 600);
            messageTextBox.Name = "messageTextBox";
            messageTextBox.Size = new Size(919, 23);
            messageTextBox.TabIndex = 5;
            messageTextBox.KeyUp += messageTextBox_KeyUp;
            // 
            // MainForm
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1009, 631);
            Controls.Add(messageTextBox);
            Controls.Add(messageLabel);
            Controls.Add(splitContainer);
            Controls.Add(button);
            Controls.Add(nameTextBox);
            Controls.Add(nameLabel);
            Name = "MainForm";
            Text = "Chat";
            Load += MainForm_Load;
            splitContainer.Panel1.ResumeLayout(false);
            splitContainer.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)splitContainer).EndInit();
            splitContainer.ResumeLayout(false);
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox nameTextBox;
        private Button button;
        private SplitContainer splitContainer;
        private TextBox messageTextBox;
        private ListBox usersListBox;
        private ListBox messagesListBox;
    }
}