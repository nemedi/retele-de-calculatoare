using System.Drawing;
using System.Windows.Forms;

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
            System.Windows.Forms.Label nameLabel;
            System.Windows.Forms.SplitContainer splitContainer;
            System.Windows.Forms.Label messageLabel;
            this.usersListBox = new System.Windows.Forms.ListBox();
            this.messagesListBox = new System.Windows.Forms.ListBox();
            this.nameTextBox = new System.Windows.Forms.TextBox();
            this.toggleLoginButton = new System.Windows.Forms.Button();
            this.messageTextBox = new System.Windows.Forms.TextBox();
            this.sendMessageButton = new System.Windows.Forms.Button();
            nameLabel = new System.Windows.Forms.Label();
            splitContainer = new System.Windows.Forms.SplitContainer();
            messageLabel = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(splitContainer)).BeginInit();
            splitContainer.Panel1.SuspendLayout();
            splitContainer.Panel2.SuspendLayout();
            splitContainer.SuspendLayout();
            this.SuspendLayout();
            // 
            // nameLabel
            // 
            nameLabel.Location = new System.Drawing.Point(10, 8);
            nameLabel.Name = "nameLabel";
            nameLabel.Size = new System.Drawing.Size(51, 20);
            nameLabel.TabIndex = 0;
            nameLabel.Text = "Name";
            nameLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // splitContainer
            // 
            splitContainer.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            splitContainer.Location = new System.Drawing.Point(10, 33);
            splitContainer.Name = "splitContainer";
            // 
            // splitContainer.Panel1
            // 
            splitContainer.Panel1.Controls.Add(this.usersListBox);
            // 
            // splitContainer.Panel2
            // 
            splitContainer.Panel2.Controls.Add(this.messagesListBox);
            splitContainer.Size = new System.Drawing.Size(844, 482);
            splitContainer.SplitterDistance = 281;
            splitContainer.SplitterWidth = 3;
            splitContainer.TabIndex = 3;
            // 
            // usersListBox
            // 
            this.usersListBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.usersListBox.FormattingEnabled = true;
            this.usersListBox.Location = new System.Drawing.Point(0, 0);
            this.usersListBox.Name = "usersListBox";
            this.usersListBox.Size = new System.Drawing.Size(281, 482);
            this.usersListBox.TabIndex = 0;
            // 
            // messagesListBox
            // 
            this.messagesListBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.messagesListBox.FormattingEnabled = true;
            this.messagesListBox.Location = new System.Drawing.Point(0, 0);
            this.messagesListBox.Name = "messagesListBox";
            this.messagesListBox.Size = new System.Drawing.Size(560, 482);
            this.messagesListBox.TabIndex = 0;
            // 
            // messageLabel
            // 
            messageLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            messageLabel.Location = new System.Drawing.Point(10, 519);
            messageLabel.Name = "messageLabel";
            messageLabel.Size = new System.Drawing.Size(51, 20);
            messageLabel.TabIndex = 4;
            messageLabel.Text = "Message";
            messageLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // nameTextBox
            // 
            this.nameTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.nameTextBox.Location = new System.Drawing.Point(67, 8);
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(719, 20);
            this.nameTextBox.TabIndex = 1;
            // 
            // toggleLoginButton
            // 
            this.toggleLoginButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.toggleLoginButton.Location = new System.Drawing.Point(790, 7);
            this.toggleLoginButton.Name = "toggleLoginButton";
            this.toggleLoginButton.Size = new System.Drawing.Size(64, 20);
            this.toggleLoginButton.TabIndex = 2;
            this.toggleLoginButton.Text = "Login";
            this.toggleLoginButton.UseVisualStyleBackColor = true;
            this.toggleLoginButton.Click += new System.EventHandler(this.toggleLoginButton_Click);
            // 
            // messageTextBox
            // 
            this.messageTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.messageTextBox.Location = new System.Drawing.Point(67, 520);
            this.messageTextBox.Name = "messageTextBox";
            this.messageTextBox.Size = new System.Drawing.Size(719, 20);
            this.messageTextBox.TabIndex = 5;
            // 
            // sendMessageButton
            // 
            this.sendMessageButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.sendMessageButton.Location = new System.Drawing.Point(790, 520);
            this.sendMessageButton.Name = "sendMessageButton";
            this.sendMessageButton.Size = new System.Drawing.Size(64, 20);
            this.sendMessageButton.TabIndex = 6;
            this.sendMessageButton.Text = "Send";
            this.sendMessageButton.UseVisualStyleBackColor = true;
            this.sendMessageButton.Click += new System.EventHandler(this.sendMessageButton_Click);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(865, 547);
            this.Controls.Add(this.sendMessageButton);
            this.Controls.Add(this.messageTextBox);
            this.Controls.Add(messageLabel);
            this.Controls.Add(splitContainer);
            this.Controls.Add(this.toggleLoginButton);
            this.Controls.Add(this.nameTextBox);
            this.Controls.Add(nameLabel);
            this.Name = "MainForm";
            this.Text = "Chat";
            this.Load += new System.EventHandler(this.MainForm_Load);
            splitContainer.Panel1.ResumeLayout(false);
            splitContainer.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(splitContainer)).EndInit();
            splitContainer.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private TextBox nameTextBox;
        private Button button;
        private TextBox messageTextBox;
        private ListBox usersListBox;
        private ListBox messagesListBox;
        private Button toggleLoginButton;
        private Button sendMessageButton;
    }
}