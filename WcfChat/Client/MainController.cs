using Common;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;
using System.Windows.Input;

namespace Client
{
    internal class MainController : ObservableObject, IDisposable, IChatCallback
    {
        Form form;
        ChatProxy proxy;
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
            ToggleLoginCommand = new Command(OnToggleLogin);
            SendMessageCommand = new Command(OnSendMessage);
            proxy = proxy = new ChatProxy(this);
            this.form = form;
        }

        void OnToggleLogin()
        {
            
            if (Active)
            {
                OnLogout();
            }
            else
            {
                OnLogin();
            }
        }

        void OnLogin()
        {
            proxy.Login(Name);
        }

        void OnLogout()
        {
            proxy.Logout();
        }

        void OnSendMessage()
        {
            if (Message.Length > 0 && To.Length > 0)
            {
                proxy.Send(To, Message);
                Message = string.Empty;
            }
        }

        public void Dispose()
        {
            if (Active)
            {
                OnLogout();
            }
        }

        public void OnAccept(string[] names)
        {
            Active = true;
            Users.Clear();
            new List<string>(names)
                .ForEach(user => Users.Add(user));
        }

        public void OnDeny()
        {
            Active = false;
            MessageBox.Show(form,
                "Failed to execute the request.",
                "Error",
                MessageBoxButtons.OK,
                MessageBoxIcon.Error);
        }

        public void OnAddUser(string name)
        {
            Users.Add(name);
        }

        public void OnRemoveUser(string name)
        {
            Users.Remove(name);
        }

        public void OnReceive(string from, string message)
        {
            Messages.Add($"{from}: {message}");
        }

        public void OnExit()
        {
            Users.Clear();
            Messages.Clear();
            Active = false;
        }

    }
}
