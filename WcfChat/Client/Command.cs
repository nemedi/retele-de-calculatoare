using System;
using System.Windows.Input;

namespace Client
{
    internal class Command : ICommand
    {
        Action action;
        public event EventHandler CanExecuteChanged;

        public Command(Action action)
        {
            this.action = action;
        }

        public bool CanExecute(object parameter)
        {
            return true;
        }

        public void Execute(object parameter)
        {
            if (CanExecute(parameter))
            {
                action();
            }
        }
    }
}
