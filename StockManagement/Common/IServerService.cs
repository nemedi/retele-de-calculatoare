using System;

namespace Common
{
	public interface IServerService
	{
		void RegisterClient(string endpoint);

		void UnregisterClient(string endpoint);

		bool LockResource(int resourceId);

		void UnlockResource(int resourceId);

		Category[] GetCategories();

		Category AddCategory(Category category);

		void UpdateCategory(Category category);

		void RemoveCategory(int categoryId);

		Product[] GetProductsByCategory(int categoryId);

		Product AddProduct(Product product);

		void UpdateProduct(Product product);

		void RemoveProduct(int productId);

		Item[] GetItemsByProduct(int productId);

		Item AddItem(Item item);

		void UpdateItem(Item item);

		void RemoveItem(int itemId);

	}
}
