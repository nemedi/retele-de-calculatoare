using Grpc.Core;
using Grpc.Net.Client;
using System.ComponentModel;
using System.Windows.Input;

namespace Client
{
    internal class MainController : ObservableObject, IDisposable
    {
        Chat.ChatClient client;
        Form form;
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
        public string To { get; set; } = "*";
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

        public MainController(Form form)
        {
            this.form = form;
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
                            form.Invoke(() => OnAccept(response));
                            break;
                        case (int)MessageTypes.DENY:
                            form.Invoke(() => OnDeny(response));
                            break;
                        case (int)MessageTypes.ADD_USER:
                            form.Invoke(() => OnAddUser(response));
                            break;
                        case (int)MessageTypes.REMOVE_USER:
                            form.Invoke(() => OnRemoveUser(response));
                            break;
                        case (int)MessageTypes.EXIT:
                            form.Invoke(() => OnExit(response));
                            break;
                        case (int)MessageTypes.RECEIVE:
                            form.Invoke(() => OnReceive(response));
                            break;
                    }
                }
            });


            this.form = form;
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
            MessageBox.Show(form,
                "Failed to execute the request.",
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
            
            if (Active)
            {
                Login();
            }
            else
            {
                Logout();
            }
        }

        void Login()
        {
            var request = new Message();
            request.Type = (int)MessageTypes.LOGOUT;
            call?.RequestStream.WriteAsync(request);
        }

        void Logout()
        {
            var request = new Message();
            request.Type = (int)MessageTypes.LOGIN;
            request.Arguments.Add(Name);
            call?.RequestStream.WriteAsync(request);
        }

        void OnSendMessage()
        {
            if (Message.Length > 0 && To.Length > 0)
            {
                Message request = new Message { Type = (int)MessageTypes.SEND };
                request.Arguments.Add(To);
                request.Arguments.Add(Message);
                call.RequestStream.WriteAsync(request);
                Message = string.Empty;
            }
        }

        public void Dispose()
        {
            if (Active)
            {
                Logout();
            }
        }
    }
}
