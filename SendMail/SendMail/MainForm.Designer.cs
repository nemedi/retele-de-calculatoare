namespace SendMail
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
			System.Windows.Forms.Label label2;
			System.Windows.Forms.Label label3;
			System.Windows.Forms.Label label4;
			System.Windows.Forms.Button button;
			this.textBoxFrom = new System.Windows.Forms.TextBox();
			this.textBoxTo = new System.Windows.Forms.TextBox();
			this.textBoxSubject = new System.Windows.Forms.TextBox();
			this.textBoxBody = new System.Windows.Forms.TextBox();
			label1 = new System.Windows.Forms.Label();
			label2 = new System.Windows.Forms.Label();
			label3 = new System.Windows.Forms.Label();
			label4 = new System.Windows.Forms.Label();
			button = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// label1
			// 
			label1.Location = new System.Drawing.Point(13, 13);
			label1.Name = "label1";
			label1.Size = new System.Drawing.Size(55, 23);
			label1.TabIndex = 0;
			label1.Text = "From";
			label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// textBoxFrom
			// 
			this.textBoxFrom.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.textBoxFrom.Location = new System.Drawing.Point(74, 15);
			this.textBoxFrom.Name = "textBoxFrom";
			this.textBoxFrom.Size = new System.Drawing.Size(714, 20);
			this.textBoxFrom.TabIndex = 1;
			// 
			// textBoxTo
			// 
			this.textBoxTo.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.textBoxTo.Location = new System.Drawing.Point(74, 41);
			this.textBoxTo.Name = "textBoxTo";
			this.textBoxTo.Size = new System.Drawing.Size(714, 20);
			this.textBoxTo.TabIndex = 3;
			// 
			// label2
			// 
			label2.Location = new System.Drawing.Point(13, 39);
			label2.Name = "label2";
			label2.Size = new System.Drawing.Size(55, 23);
			label2.TabIndex = 2;
			label2.Text = "To";
			label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// textBoxSubject
			// 
			this.textBoxSubject.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.textBoxSubject.Location = new System.Drawing.Point(74, 67);
			this.textBoxSubject.Name = "textBoxSubject";
			this.textBoxSubject.Size = new System.Drawing.Size(714, 20);
			this.textBoxSubject.TabIndex = 5;
			// 
			// label3
			// 
			label3.Location = new System.Drawing.Point(13, 65);
			label3.Name = "label3";
			label3.Size = new System.Drawing.Size(55, 23);
			label3.TabIndex = 4;
			label3.Text = "Subject";
			label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// label4
			// 
			label4.Location = new System.Drawing.Point(13, 90);
			label4.Name = "label4";
			label4.Size = new System.Drawing.Size(55, 23);
			label4.TabIndex = 6;
			label4.Text = "Body";
			label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// textBoxBody
			// 
			this.textBoxBody.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.textBoxBody.Location = new System.Drawing.Point(74, 93);
			this.textBoxBody.Multiline = true;
			this.textBoxBody.Name = "textBoxBody";
			this.textBoxBody.Size = new System.Drawing.Size(714, 345);
			this.textBoxBody.TabIndex = 7;
			// 
			// button
			// 
			button.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
			button.Location = new System.Drawing.Point(13, 415);
			button.Name = "button";
			button.Size = new System.Drawing.Size(55, 23);
			button.TabIndex = 8;
			button.Text = "Send";
			button.UseVisualStyleBackColor = true;
			button.Click += new System.EventHandler(this.button_Click);
			// 
			// MainForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(800, 450);
			this.Controls.Add(button);
			this.Controls.Add(this.textBoxBody);
			this.Controls.Add(label4);
			this.Controls.Add(this.textBoxSubject);
			this.Controls.Add(label3);
			this.Controls.Add(this.textBoxTo);
			this.Controls.Add(label2);
			this.Controls.Add(this.textBoxFrom);
			this.Controls.Add(label1);
			this.Name = "MainForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Send Mail";
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.TextBox textBoxFrom;
		private System.Windows.Forms.TextBox textBoxTo;
		private System.Windows.Forms.TextBox textBoxSubject;
		private System.Windows.Forms.TextBox textBoxBody;
	}
}

