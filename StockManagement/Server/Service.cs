using System.Collections.Generic;
using System.Linq;
using Common;

namespace Server
{
	internal class Service : IServerService
	{
		private static IList<string> clientEndpoints;

		static Service()
		{
			clientEndpoints = new List<string>();
		}

		public static Repository Repository { get; set; }

		public void RegisterClient(string endpoint)
		{
			lock (clientEndpoints)
			{
				clientEndpoints.Add(endpoint);
			}
		}

		public void UnregisterClient(string endpoint)
		{
			lock (clientEndpoints)
			{
				clientEndpoints.Remove(endpoint);
			}
		}

		public bool LockResource(int resourceId)
		{
			return Repository.LockResource(resourceId);
		}

		public void UnlockResource(int resourceId)
		{
			Repository.UnlockResource(resourceId);
		}

		public Category[] GetCategories()
		{
			return Repository.Categories.ToArray();
		}

		public Category AddCategory(Category category)
		{
			if (!Repository.Contains(category))
			{
				category = Repository.AddCategory(category.Name);
				NotifyClients("OnCategoryAdded", category);
			}
			else
			{
				UpdateCategory(category);
			}
			return category;
		}

		public void UpdateCategory(Category category)
		{
			Repository.UpdateCategory(category);
			NotifyClients("OnCategoryUpdated", category);
		}

		public void RemoveCategory(int categoryId)
		{
			Repository.RemoveCategory(categoryId);
			NotifyClients("OnCategoryRemoved", categoryId);
		}

		public Product[] GetProductsByCategory(int categoryId)
		{
			return Repository.GetProductsByCategory(categoryId);
		}

		public Product AddProduct(Product product)
		{
			if (!Repository.Contains(product))
			{
				product = Repository.AddProduct(product.CategoryId, product.Name, product.Price);
				NotifyClients("OnProductAdded", product);
			}
			else
			{
				UpdateProduct(product);
			}
			return product;
		}

		public void UpdateProduct(Product product)
		{
			Repository.UpdateProduct(product);
			NotifyClients("OnProductUpdated", product);
		}

		public void RemoveProduct(int productId)
		{
			Repository.RemoveProduct(productId);
			NotifyClients("OnProductRemoved", productId);
		}

		public Item[] GetItemsByProduct(int productId)
		{
			return Repository.GetItemsByProduct(productId);
		}

		public Item AddItem(Item item)
		{
			if (!Repository.Contains(item))
			{
				item = Repository.AddItem(item.ProductId, item.Name, item.Price);
				NotifyClients("OnItemAdded", item);
			}
			else
			{
				UpdateItem(item);
			}
			return item;
		}

		public void UpdateItem(Item item)
		{
			Repository.UpdateItem(item);
			NotifyClients("OnItemUpdated", item);
		}

		public void RemoveItem(int itemId)
		{
			Repository.RemoveItem(itemId);
			NotifyClients("OnItemRemoved", itemId);
		}

		private void NotifyClients(string methodName, params object[] arguments)
		{
			var type = typeof (Proxy);
			var method = type.GetMethod(methodName);
			lock (clientEndpoints)
			{
				foreach (var clientEndpoint in clientEndpoints)
				{
					try
					{
						var proxy = new Proxy(clientEndpoint);
						method.Invoke(proxy, arguments);
					}
					catch
					{
					}
				}
			}			
		}

	}
}
