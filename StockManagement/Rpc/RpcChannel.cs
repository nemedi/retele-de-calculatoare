using System;
using System.Dynamic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Web.Script.Serialization;

namespace Rpc
{
	internal class RpcChannel : DynamicObject, IDisposable
	{
		private IPEndPoint endpoint;
		private string sessionId;

		public RpcChannel(IPEndPoint endpoint)
		{
			this.endpoint = endpoint;
		}

        public override bool TryInvokeMember(InvokeMemberBinder binder,
            object[] args, out object result)
		{
			try
			{
				using (var client = new TcpClient())
				{
					client.Connect(endpoint);
					var request = new RpcRequest(binder.Name, sessionId);
					request.SetArguments(args);
					using (var writer = new StreamWriter(client.GetStream()))
					{
						writer.WriteLine(request.ToString());
						writer.Flush();
						using (var reader = new StreamReader(client.GetStream()))
						{
							var content = reader.ReadLine();
							var response = RpcResponse.Parse(content);
							sessionId = response.SessionId;
							result = new RpcResult(response.Result);
							client.Close();
							if (response.Fault != null)
							{
								throw new Exception(response.Fault);
							}
							return true;
						}
					}
				}
			}
			catch (Exception exception)
			{
				throw exception;
			}
		}

		public void Dispose()
		{
			try
			{
				using (var client = new TcpClient())
				{
					client.Connect(endpoint);
					var request = new RpcRequest("Dispose", sessionId);
					using (var writer = new StreamWriter(client.GetStream()))
					{
						writer.WriteLine(request.ToString());
						writer.Flush();
					}
				    sessionId = null;
				}
			}
			catch (Exception exception)
			{
				throw exception;
			}
		}
	}
}
