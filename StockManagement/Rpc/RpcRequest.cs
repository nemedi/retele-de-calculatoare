using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Serialization;

namespace Rpc
{
	internal class RpcRequest
	{
		private string method;
		private IList<string> arguments;
		private string sessionId;

		public RpcRequest()
		{
			this.arguments = new List<string>();
		}

		public RpcRequest(string method, string sessionId) : this()
		{
			this.method = method;
			this.sessionId = sessionId;
		}

		public void SetArguments(object[] arguments)
		{
			foreach (var argument in arguments)
			{
				var value = new JavaScriptSerializer().Serialize(argument);
				this.arguments.Add(value);
			}
		}

		public object GetArgument(int index, Type type)
		{
			if (index > -1 && index < arguments.Count)
			{
				var value = arguments[index];
				return new JavaScriptSerializer().Deserialize(value, type);
			}
			else
			{
				return null;
			}
		}

		public String Method
		{
			get { return method; }
			set { method = value; }
		}

		public string SessionId
		{
			get { return sessionId; }
			set { sessionId = value; }
		}

		public string[] Arguments
		{
			get { return arguments.ToArray(); }
			set { arguments = value.ToList(); }
		}

		public override string ToString()
		{
			return new JavaScriptSerializer().Serialize(this);
		}

		public static RpcRequest Parse(string content)
		{
			return new JavaScriptSerializer().Deserialize<RpcRequest>(content);
		}
	}

}
