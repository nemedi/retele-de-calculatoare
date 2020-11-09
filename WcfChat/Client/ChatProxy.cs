using System.ServiceModel;
using System.ServiceModel.Channels;
using Common;

namespace Client
{
	internal class ChatProxy : DuplexClientBase<IChat>, IChat
	{
		public ChatProxy(IChatCallback callback) : base(new InstanceContext(callback))
		{
		}

		public void Login(string name)
		{
			Channel.Login(name);
		}

		public void Logout()
		{
			Channel.Logout();
		}

		public void Send(string to, string message)
		{
			Channel.Send(to, message);
		}
	}
}
