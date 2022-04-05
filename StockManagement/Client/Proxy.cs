using System.Net;
using Common;
using Rpc;

namespace Client
{
	internal class Proxy : RpcProxy<IServerService>, IServerService
	{
		public Proxy(string endpoint)
			: base(endpoint)
		{
		}

		public void RegisterClient(string endpoint)
		{
            Channel.RegisterClient(endpoint);
        }

		public void UnregisterClient(string endpoint)
		{
            Channel.UnregisterClient(endpoint);
		}

		public bool LockResource(int resourceId)
		{
			return Channel.LockResource(resourceId).As<bool>();
		}

		public void UnlockResource(int resourceId)
		{
            Channel.UnlockResource(resourceId);
		}

		public Category[] GetCategories()
		{
			return Channel.GetCategories().As<Category[]>();
		}

		public Category AddCategory(Category category)
		{
			return Channel.AddCategory(category).As<Category>();
		}

		public void UpdateCategory(Category category)
		{
            Channel.UpdateCategory(category);
		}

		public void RemoveCategory(int categoryId)
		{
            Channel.RemoveCategory(categoryId);
		}

		public Product[] GetProductsByCategory(int categoryId)
		{
			return Channel.GetProductsByCategory(categoryId).As<Product[]>();
		}

		public Product AddProduct(Product product)
		{
			return Channel.AddProduct(product).As<Product>();
		}

		public void UpdateProduct(Product product)
		{
            Channel.UpdateProduct(product);
		}

		public void RemoveProduct(int productId)
		{
            Channel.RemoveProduct(productId);
		}

		public Item[] GetItemsByProduct(int productId)
		{
			return Channel.GetItemsByProduct(productId).As<Item[]>();
		}

		public Item AddItem(Item item)
		{
			return Channel.AddItem(item).As<Item>();
		}

		public void UpdateItem(Item item)
		{
            Channel.UpdateItem(item);
		}

		public void RemoveItem(int itemId)
		{
            Channel.RemoveItem(itemId);
		}

	}
}
