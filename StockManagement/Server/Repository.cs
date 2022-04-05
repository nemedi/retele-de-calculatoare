using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Web.Script.Serialization;
using Common;

namespace Server
{
	internal class Repository
	{
		private string path;
		private IDictionary<int, Resource> resources;
		private IList<int> lockedResources;
		private int nextId;

		public Repository()
		{
			resources = new Dictionary<int, Resource>();
			lockedResources = new List<int>();
			Categories = new List<Category>();
		}

		public IList<Category> Categories { get; set; }

		private void Initialize()
		{
			nextId = 0;
			foreach (var category in Categories)
			{
				resources[category.Id] = category;
				if (category.Id > nextId)
				{
					nextId = category.Id;
				}
				foreach (var product in category.Products)
				{
					resources[product.Id] = product;
					if (product.Id > nextId)
					{
						nextId = product.Id;
					}
					foreach (var item in product.Items)
					{
						resources[item.Id] = item;
						if (item.Id > nextId)
						{
							nextId = item.Id;
						}
					}
				}
			}
		}

		public bool LockResource(int resourceId)
		{
			if (resourceId == 0)
			{
				return true;
			}
			else if (lockedResources.Contains(resourceId)
				|| !resources.ContainsKey(resourceId))
			{
				return false;
			}
			else
			{
				var resource = resources[resourceId];
				if (resource is Product)
				{
					if (IsLocked((resource as Product).CategoryId))
					{
						return false;
					}
				}
				else if (resource is Item)
				{
					if (IsLocked((resource as Item).ProductId))
					{
						return false;
					}
					var product = resources[(resource as Item).ProductId];
					if (!(product is Product)
						|| IsLocked((product as Product).CategoryId))
					{
						return false;
					}
				}
				lockedResources.Add(resourceId);
				return true;
			}
		}

		public bool IsLocked(int resourceId)
		{
			return lockedResources.Contains(resourceId);
		}

		public void UnlockResource(int resourceId)
		{
			if (lockedResources.Contains(resourceId))
			{
				lockedResources.Remove(resourceId);
			}
		}

		public bool Contains(Resource resource)
		{
			return resource != null
				&& resource.Id > 0
			   && resources.ContainsKey(resource.Id);
		}

		public Category AddCategory(string name)
		{
			var category = new Category
			               {
				               Id = ++nextId,
									LastUpdated = DateTime.Now,
									Name = name
			               };
			Categories.Add(category);
			resources[category.Id] = category;
			Store();
			return category;
		}

		public void UpdateCategory(Category category)
		{
			if (category != null
				&& resources[category.Id] is Category)
			{
				var existingCategory = resources[category.Id] as Category;
				existingCategory.Name = category.Name;
				Store();
			}
		}

		public void RemoveCategory(int categoryId)
		{
			if (resources[categoryId] is Category)
			{
				var existingCategory = resources[categoryId] as Category;
				Categories.Remove(existingCategory);
				resources.Remove(categoryId);
				Store();
			}
		}

		public Product[] GetProductsByCategory(int categoryId)
		{
			if (resources[categoryId] is Category)
			{
				var existingCategory = resources[categoryId] as Category;
				return existingCategory.Products.ToArray();
			}
			else
			{
				return new Product[] {};
			}
		}

		public Product AddProduct(int categoryId, string name, decimal price)
		{
			if (resources.ContainsKey(categoryId)
				&& (resources[categoryId] is Category))
			{
				var product = new Product
				              {
					              Id = ++nextId,
					              LastUpdated = DateTime.Now,
					              CategoryId = categoryId,
					              Name = name,
					              Price = price
				              };
				var existingCategory = resources[categoryId] as Category;
				existingCategory.Products.Add(product);
				resources[product.Id] = product;
				Store();
				return product;
			}
			else
			{
				return null;
			}
		}

		public void UpdateProduct(Product product)
		{
			if (product != null
				&& resources[product.Id] is Product)
			{
				var existingProduct = resources[product.Id] as Product;
				existingProduct.Name = product.Name;
				existingProduct.Price = product.Price;
				Store();
			}
		}

		public void RemoveProduct(int productId)
		{
			if (resources[productId] is Product)
			{
				var existingProduct = resources[productId] as Product;
				var existingCategory = resources[existingProduct.CategoryId] as Category;
				existingCategory.Products.Remove(existingProduct);
				resources.Remove(productId);
				Store();
			}
		}

		public Item[] GetItemsByProduct(int productId)
		{
			if (resources[productId] is Product)
			{
				var existingProduct = resources[productId] as Product;
				return existingProduct.Items.ToArray();
			}
			else
			{
				return new Item[] { };
			}
		}

		public Item AddItem(int productId, string name, decimal price)
		{
			if (resources.ContainsKey(productId)
				&& (resources[productId] is Product))
			{
				var item = new Item
				           {
					           Id = ++nextId,
					           LastUpdated = DateTime.Now,
					           ProductId = productId,
					           Name = name,
					           Price = price,
					           State = State.Available
				           };
				var existingProduct = resources[productId] as Product;
				existingProduct.Items.Add(item);
				resources[item.Id] = item;
				Store();
				return item;
			}
			else
			{
				return null;
			}
		}

		public void UpdateItem(Item item)
		{
			if (item != null
				&& resources[item.Id] is Item)
			{
				var existingItem = resources[item.Id] as Item;
				existingItem.Name = item.Name;
				existingItem.Price = item.Price;
				existingItem.State = item.State;
				Store();
			}
		}

		public void RemoveItem(int itemId)
		{
			if (resources[itemId] is Item)
			{
				var existingItem = resources[itemId] as Item;
				var existingProduct = resources[existingItem.ProductId] as Product;
				existingProduct.Items.Remove(existingItem);
				resources.Remove(itemId);
				Store();
			}
		}

		public override string ToString()
		{
			return new JavaScriptSerializer().Serialize(this);
		}

		public static Repository Load(string path)
		{
			Repository repository = null;
			if (File.Exists(path))
			{
				var content = File.ReadAllText(path);
				repository = new JavaScriptSerializer().Deserialize<Repository>(content);
			}
			else
			{
				repository = new Repository();
			}
			repository.path = path;
			repository.Initialize();
			return repository;
		}

		public void Store()
		{
			using (var writer = new StreamWriter(path))
			{
				var content = new JavaScriptSerializer().Serialize(this);
				writer.WriteLine(content);
			}
		}

	}
}
