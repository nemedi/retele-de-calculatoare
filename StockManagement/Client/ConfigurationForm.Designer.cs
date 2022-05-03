namespace Client
{
	partial class ConfigurationForm
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
			System.Windows.Forms.GroupBox groupBox;
			System.Windows.Forms.ColumnHeader nameColumnHeader;
			System.Windows.Forms.Button button;
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ConfigurationForm));
			this.listView = new System.Windows.Forms.ListView();
			this.valueColumnHeader = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
			groupBox = new System.Windows.Forms.GroupBox();
			nameColumnHeader = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
			button = new System.Windows.Forms.Button();
			groupBox.SuspendLayout();
			this.SuspendLayout();
			// 
			// groupBox
			// 
			groupBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			groupBox.Controls.Add(this.listView);
			groupBox.Location = new System.Drawing.Point(12, 12);
			groupBox.Name = "groupBox";
			groupBox.Size = new System.Drawing.Size(498, 315);
			groupBox.TabIndex = 0;
			groupBox.TabStop = false;
			groupBox.Text = "Properties";
			// 
			// listView
			// 
			this.listView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.listView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            nameColumnHeader,
            this.valueColumnHeader});
			this.listView.FullRowSelect = true;
			this.listView.Location = new System.Drawing.Point(6, 19);
			this.listView.Name = "listView";
			this.listView.Size = new System.Drawing.Size(486, 290);
			this.listView.TabIndex = 0;
			this.listView.UseCompatibleStateImageBehavior = false;
			this.listView.View = System.Windows.Forms.View.Details;
			// 
			// nameColumnHeader
			// 
			nameColumnHeader.Text = "Name";
			nameColumnHeader.Width = 150;
			// 
			// valueColumnHeader
			// 
			this.valueColumnHeader.Text = "Value";
			this.valueColumnHeader.Width = 300;
			// 
			// button
			// 
			button.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
			button.DialogResult = System.Windows.Forms.DialogResult.OK;
			button.Location = new System.Drawing.Point(435, 333);
			button.Name = "button";
			button.Size = new System.Drawing.Size(75, 23);
			button.TabIndex = 0;
			button.Text = "&Close";
			button.UseVisualStyleBackColor = true;
			// 
			// ConfigurationForm
			// 
			this.AcceptButton = button;
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(522, 368);
			this.Controls.Add(button);
			this.Controls.Add(groupBox);
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.Name = "ConfigurationForm";
			this.ShowInTaskbar = false;
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
			this.Text = "Configuration";
			this.Load += new System.EventHandler(this.ConfigurationForm_Load);
			groupBox.ResumeLayout(false);
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.ListView listView;
		private System.Windows.Forms.ColumnHeader valueColumnHeader;
	}
}