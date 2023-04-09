using Microsoft.AspNetCore.SignalR;
using System.Collections.Concurrent;
using System.Linq;

namespace Server
{
    public class ChatHub : Hub
    {
        static IDictionary<string, ISingleClientProxy> clientByName = new ConcurrentDictionary<string, ISingleClientProxy>();
        static IDictionary<string, string> nameByConnectionId = new ConcurrentDictionary<string, string>();

        bool Authenticated => nameByConnectionId.ContainsKey(Context.ConnectionId);

        public async Task Login(string name)
        {
            if (Authenticated)
            {
                await Clients.Caller.SendAsync(MessageTypes.DENY);
            } else if (clientByName.ContainsKey(name))
            {
                await Clients.Caller.SendAsync(MessageTypes.DENY);
            }
            else
            {
                clientByName.Values.ToList().ForEach(async client =>
                    await client.SendAsync(MessageTypes.ADD_USER, name));
                IList<string> users = new List<string>(clientByName.Keys.ToArray());
                users.Insert(0, "*");
                await Clients.Caller.SendAsync(MessageTypes.ACCEPT, users);
                clientByName[name] = Clients.Caller;
                nameByConnectionId[Context.ConnectionId] = name;
            }
        }

        public async void Logout()
        {
            if (Authenticated)
            {
                string name = nameByConnectionId[Context.ConnectionId];
                nameByConnectionId.Remove(Context.ConnectionId);
                clientByName.Remove(name);
                clientByName.Values.ToList().ForEach(async client =>
                    await client.SendAsync(MessageTypes.REMOVE_USER, name));
                await Clients.Caller.SendAsync(MessageTypes.EXIT);
            }
            else
            {
                await Clients.Caller.SendAsync(MessageTypes.DENY);
            }
        }

        public async void Send(string to, string message)
        {
            if (Authenticated)
            {
                var from = nameByConnectionId[Context.ConnectionId];
                if (to == "*")
                {
                    clientByName.Values.ToList().ForEach(async client =>
                        await client.SendAsync(MessageTypes.RECEIVE, from, message));
                }
                else if (clientByName.ContainsKey(to))
                {
                    await clientByName[to].SendAsync(MessageTypes.RECEIVE, from, message);
                    await Clients.Caller.SendAsync(MessageTypes.RECEIVE, from, message);
                }
                else
                {
                    await Clients.Caller.SendAsync(MessageTypes.DENY);
                }
            }
            else
            {
                await Clients.Caller.SendAsync(MessageTypes.DENY);
            }
        }
    }
}
