using Grpc.Core;
using System.Collections.Concurrent;

namespace Server
{
    public class ChatService : Chat.ChatBase
    {
        static IDictionary<string, IServerStreamWriter<Message>> writers =
            new ConcurrentDictionary<string, IServerStreamWriter<Message>>();
        public override async Task Join(IAsyncStreamReader<Message> requestStream,
            IServerStreamWriter<Message> responseStream, ServerCallContext context)
        {
            var name = string.Empty;
            while (await requestStream.MoveNext(cancellationToken: CancellationToken.None))
            {
                var request = requestStream.Current;
                switch (request.Type)
                {
                    case (int)MessageTypes.LOGIN:
                        OnLogin(requestStream, responseStream, ref name);
                        break;
                    case (int)MessageTypes.LOGOUT:
                        OnLogout(requestStream, responseStream, ref name);
                        break;
                    case (int)MessageTypes.SEND:
                        OnSend(requestStream, responseStream, ref name);
                        break;
                }
            }
            if (writers.ContainsKey(name))
            {
                writers.Remove(name);
            }
        }

        void OnLogin(IAsyncStreamReader<Message> requestStream,
            IServerStreamWriter<Message> responseStream,
            ref string name)
        {
            var request = requestStream.Current;
            var response = new Message();
            if (writers.ContainsKey(request.Arguments[0]))
            {
                response.Type = (int)MessageTypes.DENY;
                responseStream.WriteAsync(response);
            }
            else
            {
                response.Type = (int)MessageTypes.ACCEPT;
                response.Arguments.Add("*");
                response.Arguments.AddRange(writers.Keys);
                name = request.Arguments[0];
                var addUser = new Message
                {
                    Type = (int)MessageTypes.ADD_USER,
                };
                addUser.Arguments.Add(name);
                writers.Values.ToList().ForEach(writer => writer.WriteAsync(addUser));
                writers[name] = responseStream;
                responseStream.WriteAsync(response);
            }
        }

        void OnLogout(IAsyncStreamReader<Message> requestStream,
            IServerStreamWriter<Message> responseStream,
            ref string name)
        {
            var response = new Message();
            if (writers.ContainsKey(name))
            {
                writers.Remove(name);
                var removeUser = new Message
                {
                    Type = (int)MessageTypes.REMOVE_USER
                };
                removeUser.Arguments.Add(name);
                writers.Values.ToList().ForEach(writer => writer.WriteAsync(removeUser));
                response.Type = (int)MessageTypes.EXIT;
                responseStream.WriteAsync(response);
                writers.Remove(name);
                name = string.Empty;
            }
            else
            {
                response.Type = (int)MessageTypes.DENY;
                responseStream.WriteAsync(response);
            }
        }

        void OnSend(IAsyncStreamReader<Message> requestStream,
            IServerStreamWriter<Message> responseStream,
            ref string name)
        {
            var request = requestStream.Current;
            var response = new Message();
            if (writers.ContainsKey(name))
            {
                if (request.Arguments[0] == "*")
                {
                    response.Type = (int)MessageTypes.RECEIVE;
                    response.Arguments.Add(name);
                    response.Arguments.Add(request.Arguments[1]);
                    writers.Values.ToList().ForEach(writer => writer.WriteAsync(response));
                }
                else if (writers.ContainsKey(request.Arguments[0]))
                {
                    response.Type = (int)MessageTypes.RECEIVE;
                    response.Arguments.Add(name);
                    response.Arguments.Add(request.Arguments[1]);
                    writers[request.Arguments[0]].WriteAsync(response);
                    responseStream.WriteAsync(response);
                }
                else
                {
                    response.Type = (int)MessageTypes.DENY;
                    responseStream.WriteAsync(response);
                }
            }
            else
            {
                response.Type = (int)MessageTypes.DENY;
                responseStream.WriteAsync(response);
            }
        }
    }
}
