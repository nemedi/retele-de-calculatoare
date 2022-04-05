using System;
using System.Windows.Forms;
using Common;

namespace Client
{
	internal class Service : IClientService
	{
		private MainForm form;

		public Service(MainForm form)
		{
			this.form = form;
		}

		public void OnCategoryAdded(Category category)
		{
			var invoker = new MethodInvoker(() => form.OnCategoryAdded(category));
			form.BeginInvoke(invoker);
		}

		public void OnCategoryUpdated(Category category)
		{
			var invoker = new MethodInvoker(() => form.OnCategoryUpdated(category));
			form.BeginInvoke(invoker);
		}

		public void OnCategoryRemoved(int categoryId)
		{
			var invoker = new MethodInvoker(() => form.OnCategoryRemoved(categoryId));
			form.BeginInvoke(invoker);
		}

		public void OnProductAdded(Product product)
		{
			var invoker = new MethodInvoker(() => form.OnProductAdded(product));
			form.BeginInvoke(invoker);
		}

		public void OnProductUpdated(Product product)
		{
			var invoker = new MethodInvoker(() => form.OnProductUpdated(product));
			form.BeginInvoke(invoker);
		}

		public void OnProductRemoved(int productId)
		{
			var invoker = new MethodInvoker(() => form.OnProductRemoved(productId));
			form.BeginInvoke(invoker);
		}

		public void OnItemAdded(Item item)
		{
			var invoker = new MethodInvoker(() => form.OnItemAdded(item));
			form.BeginInvoke(invoker);
		}

		public void OnItemUpdated(Item item)
		{
			var invoker = new MethodInvoker(() => form.OnItemUpdated(item));
			form.BeginInvoke(invoker);
		}

		public void OnItemRemoved(int itemId)
		{
			var invoker = new MethodInvoker(() => form.OnItemRemoved(itemId));
			form.BeginInvoke(invoker);
		}

	}
}
