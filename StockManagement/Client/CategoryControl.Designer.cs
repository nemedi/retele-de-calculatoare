namespace Client
{
	partial class CategoryControl
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

		#region Component Designer generated code

		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			System.Windows.Forms.Label nameLabel;
			System.Windows.Forms.Label lastUpdatedLabel;
			System.Windows.Forms.GroupBox productsGroupBox;
			System.Windows.Forms.ColumnHeader productNameColumnHeader;
			System.Windows.Forms.ColumnHeader productPriceColumnHeader;
			this.productsListView = new System.Windows.Forms.ListView();
			this.nameTextBox = new System.Windows.Forms.TextBox();
			this.lastUpdatedDateTimePicker = new System.Windows.Forms.DateTimePicker();
			nameLabel = new System.Windows.Forms.Label();
			lastUpdatedLabel = new System.Windows.Forms.Label();
			productsGroupBox = new System.Windows.Forms.GroupBox();
			productNameColumnHeader = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
			productPriceColumnHeader = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
			productsGroupBox.SuspendLayout();
			this.SuspendLayout();
			// 
			// nameLabel
			// 
			nameLabel.Location = new System.Drawing.Point(3, 0);
			nameLabel.Name = "nameLabel";
			nameLabel.Size = new System.Drawing.Size(100, 20);
			nameLabel.TabIndex = 0;
			nameLabel.Text = "&Name";
			nameLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// lastUpdatedLabel
			// 
			lastUpdatedLabel.Location = new System.Drawing.Point(3, 26);
			lastUpdatedLabel.Name = "lastUpdatedLabel";
			lastUpdatedLabel.Size = new System.Drawing.Size(100, 20);
			lastUpdatedLabel.TabIndex = 2;
			lastUpdatedLabel.Text = "Last &Updated";
			lastUpdatedLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// productsGroupBox
			// 
			productsGroupBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			productsGroupBox.Controls.Add(this.productsListView);
			productsGroupBox.Location = new System.Drawing.Point(6, 52);
			productsGroupBox.Name = "productsGroupBox";
			productsGroupBox.Size = new System.Drawing.Size(481, 308);
			productsGroupBox.TabIndex = 4;
			productsGroupBox.TabStop = false;
			productsGroupBox.Text = "Products";
			// 
			// productsListView
			// 
			this.productsListView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.productsListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            productNameColumnHeader,
            productPriceColumnHeader});
			this.productsListView.FullRowSelect = true;
			this.productsListView.Location = new System.Drawing.Point(6, 19);
			this.productsListView.MultiSelect = false;
			this.productsListView.Name = "productsListView";
			this.productsListView.Size = new System.Drawing.Size(469, 283);
			this.productsListView.TabIndex = 5;
			this.productsListView.UseCompatibleStateImageBehavior = false;
			this.productsListView.View = System.Windows.Forms.View.Details;
			this.productsListView.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.productsListView_MouseDoubleClick);
			// 
			// productNameColumnHeader
			// 
			productNameColumnHeader.Text = "Name";
			productNameColumnHeader.Width = 350;
			// 
			// productPriceColumnHeader
			// 
			productPriceColumnHeader.Text = "Price";
			productPriceColumnHeader.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			productPriceColumnHeader.Width = 100;
			// 
			// nameTextBox
			// 
			this.nameTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.nameTextBox.Location = new System.Drawing.Point(109, 0);
			this.nameTextBox.Name = "nameTextBox";
			this.nameTextBox.Size = new System.Drawing.Size(378, 20);
			this.nameTextBox.TabIndex = 1;
			// 
			// lastUpdatedDateTimePicker
			// 
			this.lastUpdatedDateTimePicker.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.lastUpdatedDateTimePicker.CustomFormat = "yyyy/MM/dd HH:mm:ss";
			this.lastUpdatedDateTimePicker.Enabled = false;
			this.lastUpdatedDateTimePicker.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.lastUpdatedDateTimePicker.Location = new System.Drawing.Point(109, 26);
			this.lastUpdatedDateTimePicker.Name = "lastUpdatedDateTimePicker";
			this.lastUpdatedDateTimePicker.Size = new System.Drawing.Size(378, 20);
			this.lastUpdatedDateTimePicker.TabIndex = 3;
			this.lastUpdatedDateTimePicker.Tag = "false";
			// 
			// CategoryControl
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.Controls.Add(productsGroupBox);
			this.Controls.Add(this.lastUpdatedDateTimePicker);
			this.Controls.Add(lastUpdatedLabel);
			this.Controls.Add(this.nameTextBox);
			this.Controls.Add(nameLabel);
			this.Name = "CategoryControl";
			productsGroupBox.ResumeLayout(false);
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.TextBox nameTextBox;
		private System.Windows.Forms.DateTimePicker lastUpdatedDateTimePicker;
		private System.Windows.Forms.ListView productsListView;


	}
}
