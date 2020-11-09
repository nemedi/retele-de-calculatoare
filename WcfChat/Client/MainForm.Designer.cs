namespace Client
{
	partial class MainForm
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
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
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			System.Windows.Forms.Label label1;
			System.Windows.Forms.SplitContainer splitContainer1;
			System.Windows.Forms.Label label2;
			this.lsbUsers = new System.Windows.Forms.ListBox();
			this.lsbMessages = new System.Windows.Forms.ListBox();
			this.tbxName = new System.Windows.Forms.TextBox();
			this.btnLogin = new System.Windows.Forms.Button();
			this.tbxMessage = new System.Windows.Forms.TextBox();
			label1 = new System.Windows.Forms.Label();
			splitContainer1 = new System.Windows.Forms.SplitContainer();
			label2 = new System.Windows.Forms.Label();
			((System.ComponentModel.ISupportInitialize)(splitContainer1)).BeginInit();
			splitContainer1.Panel1.SuspendLayout();
			splitContainer1.Panel2.SuspendLayout();
			splitContainer1.SuspendLayout();
			this.SuspendLayout();
			// 
			// label1
			// 
			label1.Location = new System.Drawing.Point(12, 9);
			label1.Name = "label1";
			label1.Size = new System.Drawing.Size(50, 23);
			label1.TabIndex = 0;
			label1.Text = "Name";
			label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// splitContainer1
			// 
			splitContainer1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			splitContainer1.Location = new System.Drawing.Point(12, 37);
			splitContainer1.Name = "splitContainer1";
			// 
			// splitContainer1.Panel1
			// 
			splitContainer1.Panel1.Controls.Add(this.lsbUsers);
			// 
			// splitContainer1.Panel2
			// 
			splitContainer1.Panel2.Controls.Add(this.lsbMessages);
			splitContainer1.Size = new System.Drawing.Size(776, 373);
			splitContainer1.SplitterDistance = 258;
			splitContainer1.TabIndex = 3;
			// 
			// lsbUsers
			// 
			this.lsbUsers.Dock = System.Windows.Forms.DockStyle.Fill;
			this.lsbUsers.Enabled = false;
			this.lsbUsers.FormattingEnabled = true;
			this.lsbUsers.Location = new System.Drawing.Point(0, 0);
			this.lsbUsers.Name = "lsbUsers";
			this.lsbUsers.Size = new System.Drawing.Size(258, 373);
			this.lsbUsers.TabIndex = 0;
			// 
			// lsbMessages
			// 
			this.lsbMessages.Dock = System.Windows.Forms.DockStyle.Fill;
			this.lsbMessages.Enabled = false;
			this.lsbMessages.FormattingEnabled = true;
			this.lsbMessages.Location = new System.Drawing.Point(0, 0);
			this.lsbMessages.Name = "lsbMessages";
			this.lsbMessages.Size = new System.Drawing.Size(514, 373);
			this.lsbMessages.TabIndex = 0;
			// 
			// label2
			// 
			label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
			label2.Location = new System.Drawing.Point(12, 418);
			label2.Name = "label2";
			label2.Size = new System.Drawing.Size(50, 23);
			label2.TabIndex = 4;
			label2.Text = "Message";
			label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// tbxName
			// 
			this.tbxName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.tbxName.Location = new System.Drawing.Point(68, 11);
			this.tbxName.Name = "tbxName";
			this.tbxName.Size = new System.Drawing.Size(639, 20);
			this.tbxName.TabIndex = 1;
			// 
			// btnLogin
			// 
			this.btnLogin.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.btnLogin.Location = new System.Drawing.Point(713, 8);
			this.btnLogin.Name = "btnLogin";
			this.btnLogin.Size = new System.Drawing.Size(75, 23);
			this.btnLogin.TabIndex = 2;
			this.btnLogin.Text = "Login";
			this.btnLogin.UseVisualStyleBackColor = true;
			this.btnLogin.Click += new System.EventHandler(this.btnLogin_Click);
			// 
			// tbxMessage
			// 
			this.tbxMessage.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.tbxMessage.Enabled = false;
			this.tbxMessage.Location = new System.Drawing.Point(68, 421);
			this.tbxMessage.Name = "tbxMessage";
			this.tbxMessage.Size = new System.Drawing.Size(720, 20);
			this.tbxMessage.TabIndex = 5;
			this.tbxMessage.KeyUp += new System.Windows.Forms.KeyEventHandler(this.tbxMessage_KeyUp);
			// 
			// MainForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(800, 450);
			this.Controls.Add(this.tbxMessage);
			this.Controls.Add(label2);
			this.Controls.Add(splitContainer1);
			this.Controls.Add(this.btnLogin);
			this.Controls.Add(this.tbxName);
			this.Controls.Add(label1);
			this.Name = "MainForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Chat";
			this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MainForm_FormClosed);
			this.Load += new System.EventHandler(this.MainForm_Load);
			splitContainer1.Panel1.ResumeLayout(false);
			splitContainer1.Panel2.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(splitContainer1)).EndInit();
			splitContainer1.ResumeLayout(false);
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.TextBox tbxName;
		private System.Windows.Forms.Button btnLogin;
		private System.Windows.Forms.ListBox lsbUsers;
		private System.Windows.Forms.ListBox lsbMessages;
		private System.Windows.Forms.TextBox tbxMessage;
	}
}

