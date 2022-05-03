using Common;
using Rpc;

namespace Server
{
	internal class Proxy : RpcProxy<IClientService>, IClientService
	{
		public Proxy(string endpoint) : base(endpoint)
		{
		}

		public void OnCategoryAdded(Category category)
		{
			
            Channel.OnCategoryAdded(category);
		}

		public void OnCategoryUpdated(Category category)
		{
            Channel.OnCategoryUpdated(category);
		}

		public void OnCategoryRemoved(int categoryId)
		{
            Channel.OnCategoryRemoved(categoryId);
		}

		public void OnProductAdded(Product product)
		{
            Channel.OnProductAdded(product);
		}

		public void OnProductUpdated(Product product)
		{
            Channel.OnProductUpdated(product);
		}

		public void OnProductRemoved(int productId)
		{
            Channel.OnProductRemoved(productId);
		}

		public void OnItemAdded(Item item)
		{
            Channel.OnItemAdded(item);
		}

		public void OnItemUpdated(Item item)
		{
            Channel.OnItemUpdated(item);
		}

		public void OnItemRemoved(int itemId)
		{
            Channel.OnItemRemoved(itemId);
		}

	}
}
