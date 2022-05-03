using System;
using System.Web.Script.Serialization;

namespace Rpc
{
	internal class RpcResponse
	{
		public string Result { get; set; }

		public string SessionId { get; set; }

		public string Fault { get; set; }

		public override string ToString()
		{
			return new JavaScriptSerializer().Serialize(this);
		}

		public static RpcResponse Parse(string content)
		{
			return new JavaScriptSerializer().Deserialize<RpcResponse>(content);
		}

	}
}
