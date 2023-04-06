using Grpc.Core;
using Grpc.Net.Client;
using System.ComponentModel;
using System.Windows.Input;

namespace Client
{
    internal class MainController : ObservableObject
    {
        Chat.ChatClient client;
        private AsyncDuplexStreamingCall<Message, Message> call;
        bool active = false;
        public bool Active
        {
            get => active;
            set
            {
                SetProperty(ref active, value);
                OnPropertyChanged(nameof(ButtonText));
            }
        }
        public string ButtonText => Active ? "Logout" : "Login";
        public bool Inactive => !Active;
        public BindingList<string> Users { get; } = new BindingList<string>();
        public int SelectedUserIndex { get; set; }
        public BindingList<string> Messages { get; } = new BindingList<string>();
        public string Name { get; set; } = string.Empty;
        string message = string.Empty;
        public string Message
        {
            get => message;
            set => SetProperty(ref message, value);
        }

        public ICommand ToggleLoginCommand { get; private set; }
        public ICommand SendMessageCommand { get; private set; }

        public MainController()
        {
            ToggleLoginCommand = new Command(OnToggleLogin);
            SendMessageCommand = new Command(OnSendMessage);
            var channel = GrpcChannel.ForAddress(Settings.Default.Endpoint);
            client = new Chat.ChatClient(channel);
            this.call = client.Join();
            _ = Task.Run(async () =>
            {
                while (await call.ResponseStream.MoveNext(cancellationToken: CancellationToken.None))
                {
                    var response = call.ResponseStream.Current;
                    switch (response.Type)
                    {
                        case (int)MessageTypes.ACCEPT:
                            OnAccept(response);
                            break;
                        case (int)MessageTypes.DENY:
                            OnDeny(response);
                            break;
                        case (int)MessageTypes.ADD_USER:
                            OnAddUser(response);
                            break;
                        case (int)MessageTypes.REMOVE_USER:
                            OnRemoveUser(response);
                            break;
                        case (int)MessageTypes.EXIT:
                            OnExit(response);
                            break;
                        case (int)MessageTypes.RECEIVE:
                            OnReceive(response);
                            break;
                    }
                }
            });
        }

        void OnAccept(Message response)
        {
            Active = true;
            Users.Clear();
            response.Arguments.ToList().ForEach(user => Users.Add(user));
        }

        void OnDeny(Message response)
        {
            Active = false;
            MessageBox.Show("Failed to execute the request.",
                "Error",
                MessageBoxButtons.OK,
                MessageBoxIcon.Error); 
        }

        void OnAddUser(Message response)
        {
            Users.Add(response.Arguments[0]);
        }

        void OnRemoveUser(Message response)
        {
            Users.Remove(response.Arguments[0]);
        }

        void OnExit(Message response)
        {
            Users.Clear();
            Messages.Clear();
            Active = false;
        }

        void OnReceive(Message response)
        {
            Messages.Add($"{response.Arguments[0]}: {response.Arguments[1]}");
        }

        void OnToggleLogin()
        {
            var request = new Message();
            if (Active)
            {
                request.Type = (int)MessageTypes.LOGOUT;
                call?.RequestStream.WriteAsync(request);
            }
            else
            {
                request.Type = (int)MessageTypes.LOGIN;
                request.Arguments.Add(Name);
                call?.RequestStream.WriteAsync(request);
            }
        }

        void OnSendMessage()
        {
            if (Name.Length > 0
                && Message.Length > 0
                && SelectedUserIndex > -1
                && SelectedUserIndex < Users.Count)
            {
                Message request = new Message { Type = (int)MessageTypes.SEND };
                request.Arguments.Add(Users[SelectedUserIndex]);
                request.Arguments.Add(Message);
                call.RequestStream.WriteAsync(request);
                Message = string.Empty;
            }
        }
    }
}
