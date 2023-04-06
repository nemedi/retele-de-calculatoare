using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace Client
{
    public class ObservableObject : INotifyPropertyChanged
    {
        protected virtual void SetProperty<T>(ref T store, T value, [CallerMemberName] string name = "")
        {
            if (!EqualityComparer<T>.Default.Equals(store, value))
            {
                store = value;
                OnPropertyChanged(name);
            }
        }

        protected void OnPropertyChanged(string name)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));
        }

        public event PropertyChangedEventHandler? PropertyChanged;
    }
}
