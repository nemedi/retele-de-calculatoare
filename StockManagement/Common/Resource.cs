using System;
using System.Collections.Generic;

namespace Common
{
	public class Resource
	{
		protected Resource()
		{
			LastUpdated = DateTime.Now;
		}

		public int Id { get; set; }

		public string Name { get; set; }

		public DateTime LastUpdated { get; set; }
	}

	public class Category : Resource
	{
		public Category()
		{
			Products = new List<Product>();
		}
		public IList<Product> Products { get; set; }

		public void RemoveProduct(int productId)
		{
			Product productToBeRemoved = null;
			foreach (var existingProduct in Products)
			{
				if (existingProduct.Id == productId)
				{
					productToBeRemoved = existingProduct;
					break;
				}
			}
			if (productToBeRemoved != null)
			{
				Products.Remove(productToBeRemoved);
			}
		}
	}

	public class Product : Resource
	{
		public Product()
		{
			Items = new List<Item>();
		}

		public int CategoryId { get; set; }

		public decimal Price { get; set; }

		public IList<Item> Items { get; set; }

		public void RemoveItem(int itemId)
		{
			Item itemToBeRemoved = null;
			foreach (var existingItem in Items)
			{
				if (existingItem.Id == itemId)
				{
					itemToBeRemoved = existingItem;
					break;
				}
			}
			if (itemToBeRemoved != null)
			{
				Items.Remove(itemToBeRemoved);
			}
		}
	}

	public class Item : Resource
	{
		public int ProductId { get; set; }
	
		public decimal Price { get; set; }

		public State State { get; set; }
	}

	public enum State
	{
		Available,
		Reserved,
		Sold
	}

}
