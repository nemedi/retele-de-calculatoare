using System;
using System.Net;

namespace Rpc
{
	public class RpcProxy<T> : IDisposable where T : class
	{
		private dynamic channel;

		public RpcProxy(IPEndPoint endpoint)
		{
            channel = new RpcChannel(endpoint);
		}

		public RpcProxy(string endpoint)
			: this(new IPEndPoint(IPAddress.Parse(endpoint.Split(':')[0]), int.Parse(endpoint.Split(':')[1])))
		{
		}

		protected dynamic Channel
		{
			get { return channel; }
		}

	    public void Dispose()
		{
            channel.Dispose();
		}
	}
}
