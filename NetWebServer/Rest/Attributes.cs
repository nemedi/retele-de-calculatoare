using System;

namespace Rest
{
    [AttributeUsage(AttributeTargets.Class, AllowMultiple = false, Inherited = false)]
    public sealed class ServiceAttribute : Attribute
    {
        public string Name
        {
            get;
            private set;
        }

        public ServiceAttribute(string name)
        {
            Name = name;
        }
    }

    [AttributeUsage(AttributeTargets.Method, AllowMultiple = false, Inherited = false)]
    public sealed class GetAttribute : Attribute
    {
        
    }

    [AttributeUsage(AttributeTargets.Method, AllowMultiple = false, Inherited = false)]
    public sealed class PostAttribute : Attribute
    {

    }

    [AttributeUsage(AttributeTargets.Method, AllowMultiple = false, Inherited = false)]
    public sealed class PutAttribute : Attribute
    {

    }

    [AttributeUsage(AttributeTargets.Method, AllowMultiple = false, Inherited = false)]
    public sealed class DeleteAttribute : Attribute
    {

    }

}
