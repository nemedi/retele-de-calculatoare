namespace Client
{
	partial class ProductControl
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
			System.Windows.Forms.Label categoryLabel;
			System.Windows.Forms.Label priceLabel;
			System.Windows.Forms.Label lastUpdatedLabel;
			System.Windows.Forms.GroupBox itemsGroupBox;
			System.Windows.Forms.ColumnHeader itemNameColumnHeader;
			System.Windows.Forms.ColumnHeader itemPriceColumnHeader;
			this.itemsListView = new System.Windows.Forms.ListView();
			this.nameTextBox = new System.Windows.Forms.TextBox();
			this.priceTextBox = new System.Windows.Forms.TextBox();
			this.lastUpdatedDateTimePicker = new System.Windows.Forms.DateTimePicker();
			this.categoryTextBox = new System.Windows.Forms.TextBox();
			nameLabel = new System.Windows.Forms.Label();
			categoryLabel = new System.Windows.Forms.Label();
			priceLabel = new System.Windows.Forms.Label();
			lastUpdatedLabel = new System.Windows.Forms.Label();
			itemsGroupBox = new System.Windows.Forms.GroupBox();
			itemNameColumnHeader = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
			itemPriceColumnHeader = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
			itemsGroupBox.SuspendLayout();
			this.SuspendLayout();
			// 
			// nameLabel
			// 
			nameLabel.Location = new System.Drawing.Point(3, 3);
			nameLabel.Name = "nameLabel";
			nameLabel.Size = new System.Drawing.Size(100, 20);
			nameLabel.TabIndex = 2;
			nameLabel.Text = "&Name";
			nameLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// categoryLabel
			// 
			categoryLabel.Location = new System.Drawing.Point(3, 30);
			categoryLabel.Name = "categoryLabel";
			categoryLabel.Size = new System.Drawing.Size(100, 20);
			categoryLabel.TabIndex = 4;
			categoryLabel.Text = "&Category";
			categoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// priceLabel
			// 
			priceLabel.Location = new System.Drawing.Point(3, 56);
			priceLabel.Name = "priceLabel";
			priceLabel.Size = new System.Drawing.Size(100, 20);
			priceLabel.TabIndex = 6;
			priceLabel.Text = "&Price";
			priceLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// lastUpdatedLabel
			// 
			lastUpdatedLabel.Location = new System.Drawing.Point(3, 82);
			lastUpdatedLabel.Name = "lastUpdatedLabel";
			lastUpdatedLabel.Size = new System.Drawing.Size(100, 20);
			lastUpdatedLabel.TabIndex = 8;
			lastUpdatedLabel.Text = "Last &Updated";
			lastUpdatedLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// itemsGroupBox
			// 
			itemsGroupBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			itemsGroupBox.Controls.Add(this.itemsListView);
			itemsGroupBox.Location = new System.Drawing.Point(6, 108);
			itemsGroupBox.Name = "itemsGroupBox";
			itemsGroupBox.Size = new System.Drawing.Size(481, 252);
			itemsGroupBox.TabIndex = 11;
			itemsGroupBox.TabStop = false;
			itemsGroupBox.Text = "Items";
			// 
			// itemsListView
			// 
			this.itemsListView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.itemsListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            itemNameColumnHeader,
            itemPriceColumnHeader});
			this.itemsListView.FullRowSelect = true;
			this.itemsListView.Location = new System.Drawing.Point(6, 19);
			this.itemsListView.Name = "itemsListView";
			this.itemsListView.Size = new System.Drawing.Size(469, 227);
			this.itemsListView.TabIndex = 5;
			this.itemsListView.UseCompatibleStateImageBehavior = false;
			this.itemsListView.View = System.Windows.Forms.View.Details;
			this.itemsListView.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.itemsListView_MouseDoubleClick);
			// 
			// itemNameColumnHeader
			// 
			itemNameColumnHeader.Text = "Name";
			itemNameColumnHeader.Width = 350;
			// 
			// itemPriceColumnHeader
			// 
			itemPriceColumnHeader.Text = "Price";
			itemPriceColumnHeader.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			itemPriceColumnHeader.Width = 100;
			// 
			// nameTextBox
			// 
			this.nameTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.nameTextBox.Location = new System.Drawing.Point(109, 3);
			this.nameTextBox.Name = "nameTextBox";
			this.nameTextBox.Size = new System.Drawing.Size(378, 20);
			this.nameTextBox.TabIndex = 3;
			// 
			// priceTextBox
			// 
			this.priceTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.priceTextBox.Location = new System.Drawing.Point(109, 56);
			this.priceTextBox.Name = "priceTextBox";
			this.priceTextBox.Size = new System.Drawing.Size(378, 20);
			this.priceTextBox.TabIndex = 7;
			this.priceTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// lastUpdatedDateTimePicker
			// 
			this.lastUpdatedDateTimePicker.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.lastUpdatedDateTimePicker.CustomFormat = "yyyy/MM/dd HH:mm:ss";
			this.lastUpdatedDateTimePicker.Enabled = false;
			this.lastUpdatedDateTimePicker.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.lastUpdatedDateTimePicker.Location = new System.Drawing.Point(109, 82);
			this.lastUpdatedDateTimePicker.Name = "lastUpdatedDateTimePicker";
			this.lastUpdatedDateTimePicker.Size = new System.Drawing.Size(378, 20);
			this.lastUpdatedDateTimePicker.TabIndex = 9;
			this.lastUpdatedDateTimePicker.Tag = "false";
			// 
			// categoryTextBox
			// 
			this.categoryTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.categoryTextBox.Location = new System.Drawing.Point(109, 29);
			this.categoryTextBox.Name = "categoryTextBox";
			this.categoryTextBox.ReadOnly = true;
			this.categoryTextBox.Size = new System.Drawing.Size(378, 20);
			this.categoryTextBox.TabIndex = 10;
			this.categoryTextBox.Tag = "false";
			// 
			// ProductControl
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.Controls.Add(itemsGroupBox);
			this.Controls.Add(this.categoryTextBox);
			this.Controls.Add(this.lastUpdatedDateTimePicker);
			this.Controls.Add(lastUpdatedLabel);
			this.Controls.Add(this.priceTextBox);
			this.Controls.Add(priceLabel);
			this.Controls.Add(categoryLabel);
			this.Controls.Add(this.nameTextBox);
			this.Controls.Add(nameLabel);
			this.Name = "ProductControl";
			itemsGroupBox.ResumeLayout(false);
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.TextBox nameTextBox;
		private System.Windows.Forms.TextBox priceTextBox;
		private System.Windows.Forms.DateTimePicker lastUpdatedDateTimePicker;
		private System.Windows.Forms.TextBox categoryTextBox;
		private System.Windows.Forms.ListView itemsListView;
	}
}
