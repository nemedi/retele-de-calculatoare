using System.ServiceModel;
using System.Collections.Generic;
using System.Collections.Concurrent;
using System.Linq;
using Common;

namespace Server
{
	internal class ChatService : IChat
	{
		private static IDictionary<string, IChatCallback> users = new ConcurrentDictionary<string, IChatCallback>();
		public void Login(string name)
		{
			if (users.ContainsKey(name))
			{
				Callback.OnDeny();
			}
			else
			{
				var names = new List<string>();
				names.Add("*");
				for (var enumeration = users.GetEnumerator(); enumeration.MoveNext();)
				{
					enumeration.Current.Value.OnAddUser(name);
					names.Add(enumeration.Current.Key);
				}
				users[name] = Callback;
				Callback.OnAccept(names.ToArray());
			}
		}

		public void Logout()
		{
			var (name, callback) = users.AsEnumerable()
				.Where(e => e.Value == Callback)
				.Select(entry => (entry.Key, entry.Value))
				.First();
			users.Remove(name);
			users.AsParallel()
				.ForAll(entry => entry.Value.OnRemoveUser(name));
			callback.OnExit();
		}

		public void Send(string to, string message)
		{
			var from = users.AsEnumerable()
				.Where(entry => entry.Value == Callback)
				.Select(entry => entry.Key)
				.First();
			users.AsParallel()
				.Where(entry => to == "*" || entry.Key == to || Callback == entry.Value)
				.ForAll(entry => entry.Value.OnReceive(from, message));
		}

		private IChatCallback Callback => OperationContext.Current.GetCallbackChannel<IChatCallback>();
	}
}
