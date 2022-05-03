using System.Web.Script.Serialization;

namespace Rpc
{
	public class RpcResult
	{
		private string value;

		internal RpcResult(string value)
		{
			this.value = value;
		}

		public T As<T>()
		{
			return new JavaScriptSerializer().Deserialize<T>(value);
		}

	}
}
