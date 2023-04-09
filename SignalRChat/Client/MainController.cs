using Microsoft.AspNetCore.SignalR.Client;
using System.ComponentModel;
using System.Windows.Input;
using System.Runtime.CompilerServices;

namespace Client
{
    internal class MainController : ObservableObject, IDisposable
    {
        Form form;
        private HubConnection connection;
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
        public string To { get; set; } = string.Empty;
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
            ToggleLoginCommand = new Command(async () => await OnToggleLogin());
            SendMessageCommand = new Command(async () => await Send());
            connection = new HubConnectionBuilder()
                .WithUrl(Settings.Default.Endpoint)
                .Build();
            connection.On<IList<string>>(MessageTypes.ACCEPT,
                (users) => form.Invoke(() => OnAccept(users)));
            connection.On(MessageTypes.DENY,
                () => form.Invoke(() => OnDeny()));
            connection.On<string>(MessageTypes.ADD_USER,
                (user) => form.Invoke(() => OnAddUser(user)));
            connection.On<string>(MessageTypes.REMOVE_USER,
                (user) => form.Invoke(() => OnRemoveUser(user)));
            connection.On<string, string>(MessageTypes.RECEIVE,
                (from, message) => form.Invoke(() => OnReceive(from, message)));
            connection.On(MessageTypes.EXIT,
                () => form.Invoke(() => OnExit()));
            this.form = form;
        }

        public async Task Start()
        {
            await connection.StartAsync();
        }

        public async Task Stop()
        {
            await connection.StopAsync();
        }

        void OnAccept(IList<string> users)
        {
            Active = true;
            Users.Clear();
            users.ToList().ForEach(user => Users.Add(user));
        }

        void OnDeny()
        {
            Active = false;
            MessageBox.Show(form,
                "Failed to execute the request.",
                "Error",
                MessageBoxButtons.OK,
                MessageBoxIcon.Error); 
        }

        void OnAddUser(string user)
        {
            Users.Add(user);
        }

        void OnRemoveUser(string user)
        {
            Users.Remove(user);
        }

        void OnExit()
        {
            Users.Clear();
            Messages.Clear();
            Active = false;
        }

        void OnReceive(string from, string content)
        {
            Messages.Add($"{from}: {content}");
        }

        async Task OnToggleLogin()
        {
            if (Active)
            {
                await Logout();
            }
            else
            {
                await Login();
            }
        }

        string GetMethodName([CallerMemberName] string methodName = "")
        {
            return methodName;
        }

        async Task Login()
        {
            await connection.InvokeAsync(GetMethodName(), Name);
        }

        async Task Logout()
        {
            await connection.InvokeAsync(GetMethodName());
        }

        async Task Send()
        {
            if (Message.Length > 0 && To.Length > 0)
            {
                await connection.InvokeAsync(GetMethodName(), To, Message);
                Message = string.Empty;
            }
        }

        public void Dispose()
        {
            if (Active)
            {
                Logout().Wait();
                Stop().Wait();
            }
        }
    }
}
