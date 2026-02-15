using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Web.Script.Serialization;
using System.Security.Cryptography;
using System.Text;

namespace Rpc
{
	public class RpcService : IDisposable
	{
		public static string Log { get; set; }
		private Type type;
		private object instance;
		private IDictionary<string, object> instances;
		private TcpListener listener;

		private RpcService(IPEndPoint endPoint)
		{
			instances = new Dictionary<string, object>();
			listener = new TcpListener(endPoint);
			listener.Start();
			listener.BeginAcceptTcpClient(OnAcceptTcpClient, null);
		}

		public RpcService(Type type, IPEndPoint endPoint)
			: this(endPoint)
		{
			this.type = type;
		}

		public RpcService(Type type, string endpoint)
			: this(type, new IPEndPoint(IPAddress.Parse(endpoint.Split(':')[0]), int.Parse(endpoint.Split(':')[1])))
		{
		}


		public RpcService(object service, IPEndPoint endpoint)
			: this(endpoint)
		{
			if (service != null)
			{
				this.instance = service;
				this.type = service.GetType();
			}
		}

		public RpcService(object service, string endpoint)
			: this(service, new IPEndPoint(IPAddress.Parse(endpoint.Split(':')[0]), int.Parse(endpoint.Split(':')[1])))
		{
		}


		private void OnAcceptTcpClient(IAsyncResult result)
		{
			if (listener != null && listener.Server.IsBound)
			{
				listener.BeginAcceptTcpClient(OnAcceptTcpClient, null);
			}
			TcpClient client = null;
            StreamReader reader = null;
            StreamWriter writer = null;
            var response = new RpcResponse();
            try
			{
				client = listener.EndAcceptTcpClient(result);
                reader = new StreamReader(client.GetStream());
                writer = new StreamWriter(client.GetStream());
				var content = reader.ReadLine();
				WriteLog(client, true, content);
				var request = RpcRequest.Parse(content);
				if (request.Method == "Dispose")
				{
					if (request.SessionId != null
                        && instances.ContainsKey(request.SessionId))
					{
						instances.Remove(request.SessionId);
					}
					return;
				}
				var method = type.GetMethod(request.Method);
				var parameters = method.GetParameters();
				if (request.SessionId == null)
				{
					var endpoint = (client.Client.RemoteEndPoint as IPEndPoint);
				    using (var md5 = MD5.Create())
				    {
                        request.SessionId = string.Format("{0}:{1}:{2}",
                            endpoint.Address, endpoint.Port, DateTime.Now.Ticks);
                        var builder = new StringBuilder();
                        foreach (var value in
                            md5.ComputeHash(Encoding.ASCII.GetBytes(
                                request.SessionId)))
                        {
                            builder.Append(value.ToString("X2"));
                        }
				        request.SessionId = builder.ToString();
				    }
					
				}
				object service = instance;
				if (instance != null)
				{
					service = instance;
				}
				else if (instances.ContainsKey(request.SessionId)
                    && instances[request.SessionId] != null)
				{
					service = instances[request.SessionId];
				}
				else {
					service = Activator.CreateInstance(this.type);
					instances[request.SessionId] = service;
				}
				object[] arguments = new object[parameters.Length];
				for (int i = 0; i < arguments.Length; i++)
				{
					arguments[i] = request.GetArgument(i, parameters[i].ParameterType);
				}
				response.SessionId = request.SessionId;
				object methodResult = method.Invoke(service, arguments);
				response.Result = new JavaScriptSerializer().Serialize(methodResult);
			}
			catch (Exception exception)
			{
				response.Fault = exception.Message;
			}
			finally
			{
				try
				{
					var content = response.ToString();
					WriteLog(client, false, content);
					writer.WriteLine(content);
					writer.Flush();				
				}
				catch
				{
				}
			}
		}

		private void WriteLog(TcpClient client, bool isRequest, string data)
		{
			if (Log != null && client != null)
			{
				var endpoint = (client.Client.RemoteEndPoint as IPEndPoint);
				var format = "[{0}] ";
				format += isRequest ? "Request from" : "Response to";
				format += " {1}: {2}";
				using (var stream = File.AppendText(Log))
				{
					stream.WriteLine(format,
						DateTime.Now.ToString("yyyy/MM/dd HH:mm:ss"),
						endpoint.Address,
						data);
				}
			}
		}

		public void Dispose()
		{
			if (listener != null)
			{
				listener.Stop();
				listener = null;
			}
		}
	}
}
