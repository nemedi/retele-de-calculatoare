namespace Common
{
	public interface IClientService
	{
		void OnCategoryAdded(Category category);

		void OnCategoryUpdated(Category category);

		void OnCategoryRemoved(int categoryId);

		void OnProductAdded(Product product);

		void OnProductUpdated(Product product);

		void OnProductRemoved(int productId);

		void OnItemAdded(Item item);

		void OnItemUpdated(Item item);

		void OnItemRemoved(int itemId);

	}
}
