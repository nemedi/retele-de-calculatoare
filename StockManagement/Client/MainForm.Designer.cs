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
			this.components = new System.ComponentModel.Container();
			System.Windows.Forms.Splitter splitter;
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
			this.saveButton = new System.Windows.Forms.Button();
			this.removeButton = new System.Windows.Forms.Button();
			this.treeView = new System.Windows.Forms.TreeView();
			this.outerPanel = new System.Windows.Forms.Panel();
			this.groupBox = new System.Windows.Forms.GroupBox();
			this.menuStrip = new System.Windows.Forms.MenuStrip();
			this.applicationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.separatorToolStripMenuItem = new System.Windows.Forms.ToolStripSeparator();
			this.saveFileDialog = new System.Windows.Forms.SaveFileDialog();
			this.openFileDialog = new System.Windows.Forms.OpenFileDialog();
			this.exportToFileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.importFromFileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.configurationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.imageList = new System.Windows.Forms.ImageList(this.components);
			splitter = new System.Windows.Forms.Splitter();
			this.outerPanel.SuspendLayout();
			this.menuStrip.SuspendLayout();
			this.SuspendLayout();
			// 
			// splitter
			// 
			splitter.Location = new System.Drawing.Point(213, 24);
			splitter.Name = "splitter";
			splitter.Size = new System.Drawing.Size(3, 637);
			splitter.TabIndex = 1;
			splitter.TabStop = false;
			// 
			// saveButton
			// 
			this.saveButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
			this.saveButton.Location = new System.Drawing.Point(725, 602);
			this.saveButton.Name = "saveButton";
			this.saveButton.Size = new System.Drawing.Size(75, 23);
			this.saveButton.TabIndex = 0;
			this.saveButton.Text = "&Save";
			this.saveButton.UseVisualStyleBackColor = true;
			this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
			// 
			// removeButton
			// 
			this.removeButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
			this.removeButton.Location = new System.Drawing.Point(644, 602);
			this.removeButton.Name = "removeButton";
			this.removeButton.Size = new System.Drawing.Size(75, 23);
			this.removeButton.TabIndex = 1;
			this.removeButton.Text = "&Remove";
			this.removeButton.UseVisualStyleBackColor = true;
			this.removeButton.Visible = false;
			this.removeButton.Click += new System.EventHandler(this.removeButton_Click);
			// 
			// treeView
			// 
			this.treeView.Dock = System.Windows.Forms.DockStyle.Left;
			this.treeView.ImageIndex = 0;
			this.treeView.ImageList = this.imageList;
			this.treeView.Location = new System.Drawing.Point(0, 24);
			this.treeView.Name = "treeView";
			this.treeView.SelectedImageIndex = 0;
			this.treeView.Size = new System.Drawing.Size(213, 637);
			this.treeView.TabIndex = 0;
			this.treeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView_AfterSelect);
			// 
			// outerPanel
			// 
			this.outerPanel.Controls.Add(this.groupBox);
			this.outerPanel.Controls.Add(this.removeButton);
			this.outerPanel.Controls.Add(this.saveButton);
			this.outerPanel.Dock = System.Windows.Forms.DockStyle.Fill;
			this.outerPanel.Location = new System.Drawing.Point(216, 24);
			this.outerPanel.Name = "outerPanel";
			this.outerPanel.Size = new System.Drawing.Size(812, 637);
			this.outerPanel.TabIndex = 2;
			this.outerPanel.Visible = false;
			// 
			// groupBox
			// 
			this.groupBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.groupBox.Location = new System.Drawing.Point(6, 3);
			this.groupBox.Name = "groupBox";
			this.groupBox.Size = new System.Drawing.Size(794, 593);
			this.groupBox.TabIndex = 2;
			this.groupBox.TabStop = false;
			this.groupBox.Text = "<Title>";
			// 
			// menuStrip
			// 
			this.menuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.applicationToolStripMenuItem});
			this.menuStrip.Location = new System.Drawing.Point(0, 0);
			this.menuStrip.Name = "menuStrip";
			this.menuStrip.Size = new System.Drawing.Size(1028, 24);
			this.menuStrip.TabIndex = 3;
			this.menuStrip.Text = "menuStrip1";
			// 
			// applicationToolStripMenuItem
			// 
			this.applicationToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.exportToFileToolStripMenuItem,
            this.importFromFileToolStripMenuItem,
            this.configurationToolStripMenuItem,
            this.separatorToolStripMenuItem,
            this.exitToolStripMenuItem});
			this.applicationToolStripMenuItem.Name = "applicationToolStripMenuItem";
			this.applicationToolStripMenuItem.Size = new System.Drawing.Size(80, 20);
			this.applicationToolStripMenuItem.Text = "&Application";
			// 
			// separatorToolStripMenuItem
			// 
			this.separatorToolStripMenuItem.Name = "separatorToolStripMenuItem";
			this.separatorToolStripMenuItem.Size = new System.Drawing.Size(157, 6);
			// 
			// saveFileDialog
			// 
			this.saveFileDialog.FileName = "repository.json";
			this.saveFileDialog.Filter = "JSON Files|*.json";
			this.saveFileDialog.RestoreDirectory = true;
			this.saveFileDialog.Title = "Export Repository to File";
			// 
			// openFileDialog
			// 
			this.openFileDialog.FileName = "repository.json";
			this.openFileDialog.Filter = "JSON Repository|*.json";
			this.openFileDialog.RestoreDirectory = true;
			// 
			// exportToFileToolStripMenuItem
			// 
			this.exportToFileToolStripMenuItem.Image = global::Client.Properties.Resources.Export;
			this.exportToFileToolStripMenuItem.Name = "exportToFileToolStripMenuItem";
			this.exportToFileToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
			this.exportToFileToolStripMenuItem.Text = "&Export to File";
			this.exportToFileToolStripMenuItem.Click += new System.EventHandler(this.exportToFileToolStripMenuItem_Click);
			// 
			// importFromFileToolStripMenuItem
			// 
			this.importFromFileToolStripMenuItem.Image = global::Client.Properties.Resources.Import;
			this.importFromFileToolStripMenuItem.Name = "importFromFileToolStripMenuItem";
			this.importFromFileToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
			this.importFromFileToolStripMenuItem.Text = "&Import from File";
			this.importFromFileToolStripMenuItem.Click += new System.EventHandler(this.importFromFileToolStripMenuItem_Click);
			// 
			// configurationToolStripMenuItem
			// 
			this.configurationToolStripMenuItem.Image = global::Client.Properties.Resources.Configuration;
			this.configurationToolStripMenuItem.Name = "configurationToolStripMenuItem";
			this.configurationToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
			this.configurationToolStripMenuItem.Text = "&Configuration";
			this.configurationToolStripMenuItem.Click += new System.EventHandler(this.configurationToolStripMenuItem_Click);
			// 
			// exitToolStripMenuItem
			// 
			this.exitToolStripMenuItem.Image = global::Client.Properties.Resources.Exit;
			this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
			this.exitToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
			this.exitToolStripMenuItem.Text = "E&xit";
			this.exitToolStripMenuItem.Click += new System.EventHandler(this.exitToolStripMenuItem_Click);
			// 
			// imageList
			// 
			this.imageList.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList.ImageStream")));
			this.imageList.TransparentColor = System.Drawing.Color.Transparent;
			this.imageList.Images.SetKeyName(0, "Category.ico");
			this.imageList.Images.SetKeyName(1, "Product.ico");
			this.imageList.Images.SetKeyName(2, "Item.ico");
			// 
			// MainForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(1028, 661);
			this.Controls.Add(this.outerPanel);
			this.Controls.Add(splitter);
			this.Controls.Add(this.treeView);
			this.Controls.Add(this.menuStrip);
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.MainMenuStrip = this.menuStrip;
			this.Name = "MainForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Stock Management";
			this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MainForm_FormClosed);
			this.Load += new System.EventHandler(this.MainForm_Load);
			this.outerPanel.ResumeLayout(false);
			this.menuStrip.ResumeLayout(false);
			this.menuStrip.PerformLayout();
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.TreeView treeView;
		private System.Windows.Forms.Panel outerPanel;
		private System.Windows.Forms.GroupBox groupBox;
		private System.Windows.Forms.Button removeButton;
		private System.Windows.Forms.Button saveButton;
		private System.Windows.Forms.MenuStrip menuStrip;
		private System.Windows.Forms.ToolStripMenuItem applicationToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem configurationToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem exportToFileToolStripMenuItem;
		private System.Windows.Forms.SaveFileDialog saveFileDialog;
		private System.Windows.Forms.ToolStripMenuItem importFromFileToolStripMenuItem;
		private System.Windows.Forms.ToolStripSeparator separatorToolStripMenuItem;
		private System.Windows.Forms.OpenFileDialog openFileDialog;
		private System.Windows.Forms.ImageList imageList;
	}
}

