namespace Client
{
	partial class ItemControl
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
			System.Windows.Forms.Label categoryLabel;
			System.Windows.Forms.Label nameLabel;
			System.Windows.Forms.Label productLabel;
			System.Windows.Forms.Label priceLabel;
			System.Windows.Forms.Label stateLabel;
			System.Windows.Forms.Label lastUpdatedLabel;
			this.categoryTextBox = new System.Windows.Forms.TextBox();
			this.nameTextBox = new System.Windows.Forms.TextBox();
			this.productTextBox = new System.Windows.Forms.TextBox();
			this.priceTextBox = new System.Windows.Forms.TextBox();
			this.stateComboBox = new System.Windows.Forms.ComboBox();
			this.lastUpdatedDateTimePicker = new System.Windows.Forms.DateTimePicker();
			categoryLabel = new System.Windows.Forms.Label();
			nameLabel = new System.Windows.Forms.Label();
			productLabel = new System.Windows.Forms.Label();
			priceLabel = new System.Windows.Forms.Label();
			stateLabel = new System.Windows.Forms.Label();
			lastUpdatedLabel = new System.Windows.Forms.Label();
			this.SuspendLayout();
			// 
			// categoryLabel
			// 
			categoryLabel.Location = new System.Drawing.Point(3, 30);
			categoryLabel.Name = "categoryLabel";
			categoryLabel.Size = new System.Drawing.Size(100, 20);
			categoryLabel.TabIndex = 13;
			categoryLabel.Text = "&Category";
			categoryLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// nameLabel
			// 
			nameLabel.Location = new System.Drawing.Point(3, 3);
			nameLabel.Name = "nameLabel";
			nameLabel.Size = new System.Drawing.Size(100, 20);
			nameLabel.TabIndex = 11;
			nameLabel.Text = "&Name";
			nameLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// productLabel
			// 
			productLabel.Location = new System.Drawing.Point(3, 56);
			productLabel.Name = "productLabel";
			productLabel.Size = new System.Drawing.Size(100, 20);
			productLabel.TabIndex = 15;
			productLabel.Text = "&Product";
			productLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// priceLabel
			// 
			priceLabel.Location = new System.Drawing.Point(3, 81);
			priceLabel.Name = "priceLabel";
			priceLabel.Size = new System.Drawing.Size(100, 20);
			priceLabel.TabIndex = 17;
			priceLabel.Text = "&Price";
			priceLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// stateLabel
			// 
			stateLabel.Location = new System.Drawing.Point(3, 108);
			stateLabel.Name = "stateLabel";
			stateLabel.Size = new System.Drawing.Size(100, 20);
			stateLabel.TabIndex = 19;
			stateLabel.Text = "&State";
			stateLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// lastUpdatedLabel
			// 
			lastUpdatedLabel.Location = new System.Drawing.Point(3, 134);
			lastUpdatedLabel.Name = "lastUpdatedLabel";
			lastUpdatedLabel.Size = new System.Drawing.Size(100, 20);
			lastUpdatedLabel.TabIndex = 21;
			lastUpdatedLabel.Text = "Last &Updated";
			lastUpdatedLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
			// 
			// categoryTextBox
			// 
			this.categoryTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.categoryTextBox.Location = new System.Drawing.Point(109, 29);
			this.categoryTextBox.Name = "categoryTextBox";
			this.categoryTextBox.ReadOnly = true;
			this.categoryTextBox.Size = new System.Drawing.Size(378, 20);
			this.categoryTextBox.TabIndex = 14;
			this.categoryTextBox.Tag = "false";
			// 
			// nameTextBox
			// 
			this.nameTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.nameTextBox.Location = new System.Drawing.Point(109, 3);
			this.nameTextBox.Name = "nameTextBox";
			this.nameTextBox.Size = new System.Drawing.Size(378, 20);
			this.nameTextBox.TabIndex = 12;
			// 
			// productTextBox
			// 
			this.productTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.productTextBox.Location = new System.Drawing.Point(109, 55);
			this.productTextBox.Name = "productTextBox";
			this.productTextBox.ReadOnly = true;
			this.productTextBox.Size = new System.Drawing.Size(378, 20);
			this.productTextBox.TabIndex = 16;
			this.productTextBox.Tag = "false";
			// 
			// priceTextBox
			// 
			this.priceTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.priceTextBox.Location = new System.Drawing.Point(109, 81);
			this.priceTextBox.Name = "priceTextBox";
			this.priceTextBox.Size = new System.Drawing.Size(378, 20);
			this.priceTextBox.TabIndex = 18;
			this.priceTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// stateComboBox
			// 
			this.stateComboBox.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.stateComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
			this.stateComboBox.FormattingEnabled = true;
			this.stateComboBox.Items.AddRange(new object[] {
            "Available",
            "Reserved",
            "Sold"});
			this.stateComboBox.Location = new System.Drawing.Point(109, 107);
			this.stateComboBox.Name = "stateComboBox";
			this.stateComboBox.Size = new System.Drawing.Size(378, 21);
			this.stateComboBox.TabIndex = 20;
			// 
			// lastUpdatedDateTimePicker
			// 
			this.lastUpdatedDateTimePicker.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.lastUpdatedDateTimePicker.CustomFormat = "yyyy/MM/dd HH:mm:ss";
			this.lastUpdatedDateTimePicker.Enabled = false;
			this.lastUpdatedDateTimePicker.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.lastUpdatedDateTimePicker.Location = new System.Drawing.Point(109, 134);
			this.lastUpdatedDateTimePicker.Name = "lastUpdatedDateTimePicker";
			this.lastUpdatedDateTimePicker.Size = new System.Drawing.Size(378, 20);
			this.lastUpdatedDateTimePicker.TabIndex = 22;
			this.lastUpdatedDateTimePicker.Tag = "false";
			// 
			// ItemControl
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.Controls.Add(this.lastUpdatedDateTimePicker);
			this.Controls.Add(lastUpdatedLabel);
			this.Controls.Add(this.stateComboBox);
			this.Controls.Add(stateLabel);
			this.Controls.Add(this.priceTextBox);
			this.Controls.Add(priceLabel);
			this.Controls.Add(this.productTextBox);
			this.Controls.Add(productLabel);
			this.Controls.Add(this.categoryTextBox);
			this.Controls.Add(categoryLabel);
			this.Controls.Add(this.nameTextBox);
			this.Controls.Add(nameLabel);
			this.Name = "ItemControl";
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.TextBox categoryTextBox;
		private System.Windows.Forms.TextBox nameTextBox;
		private System.Windows.Forms.TextBox productTextBox;
		private System.Windows.Forms.TextBox priceTextBox;
		private System.Windows.Forms.ComboBox stateComboBox;
		private System.Windows.Forms.DateTimePicker lastUpdatedDateTimePicker;
	}
}
