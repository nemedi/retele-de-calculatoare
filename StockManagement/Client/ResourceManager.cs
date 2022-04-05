using System.Reflection;
using Common;

namespace Client
{
	public class ResourceManager
	{
		private MethodInfo addResourceMethod;

		private MethodInfo updateResourceMethod;

		private MethodInfo removeResourceMethod;

		public ResourceManager()
		{

		}

		public ResourceManager(string addResourceMethod, string updateResourceMethod, string removeResourceMethod)
		{
			var type = typeof(Proxy);
			this.addResourceMethod = type.GetMethod(addResourceMethod);
			this.updateResourceMethod = type.GetMethod(updateResourceMethod);
			this.removeResourceMethod = type.GetMethod(removeResourceMethod);
		}

		public Resource AddResource(Resource resource)
		{
			return addResourceMethod.Invoke(MainForm.Instance.Proxy, new object[] { resource }) as Resource;
		}

		public void UpdateResource(Resource resource)
		{
			updateResourceMethod.Invoke(MainForm.Instance.Proxy, new object[] { resource });
		}

		public void RemoveResource(int resourceId)
		{
			removeResourceMethod.Invoke(MainForm.Instance.Proxy, new object[] { resourceId });
		}
	}

}
