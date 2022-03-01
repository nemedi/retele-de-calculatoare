using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Ports;
using System.Net;
using System.Reflection;
using System.Text;
using System.Web.Script.Serialization;
using System.Xml;

namespace Server
{
    internal class Server : IDisposable
    {
        private HttpListener listener;
        private string directory;
        private static IDictionary<string, string> mimes;

        public void Dispose()
        {
            Stop();
        }

        internal void Start(string host, int port, string path)
        {
            Stop();
            this.directory = path;
            listener = new HttpListener();
            listener.Prefixes.Add(string.Format("http://{0}:{1}/", host, port));
            listener.Start();
            listener.BeginGetContext(OnGetContext, null);
        }

        private void OnGetContext(IAsyncResult result)
        {
            HttpListenerContext context = null;
            try
            {
                context = listener.EndGetContext(result);
                var path = context.Request.RawUrl.Replace('/', '\\').Substring(1);
                if (path.StartsWith("!"))
                {
                    HandleService(context);
                }
                else
                {
                    HandleFile(context);    
                }
                
            }
            catch
            {
                if (context != null)
                {
                    context.Response.StatusCode = (int) HttpStatusCode.InternalServerError;
                }
            }
            finally
            {
                if (context != null)
                {
                    context.Response.OutputStream.Close();
                }
                if (listener != null)
                {
                    listener.BeginGetContext(OnGetContext, null);
                }
            }
        }

        private void HandleService(HttpListenerContext context)
        {
            Type type = GetService(context);
            if (type == null)
            {
                return;
            }
            MethodInfo method = null;
            bool hasInput = false;
            bool hasOutput = false;
            switch (context.Request.HttpMethod.ToUpper())
            {
                case "GET":
                    method = GetMethod(type, typeof (Rest.GetAttribute));
                    hasInput = false;
                    hasOutput = true;
                    break;
                case "POST":
                    method = GetMethod(type, typeof(Rest.PostAttribute));
                    hasInput = true;
                    hasOutput = true;
                    break;
                case "PUT":
                    method = GetMethod(type, typeof(Rest.PutAttribute));
                    hasInput = true;
                    hasOutput = false;
                    break;
                case "DELETE":
                    method = GetMethod(type, typeof(Rest.DeleteAttribute));
                    hasInput = true;
                    hasOutput = false;
                    break;
            }
            if (method == null)
            {
                context.Response.StatusCode = (int) HttpStatusCode.MethodNotAllowed;
                return;
            }
            context.Response.StatusCode = (int) HttpStatusCode.OK;
            if (hasInput)
            {
                var buffer = new byte[context.Request.ContentLength64];
                context.Request.InputStream.Read(buffer, 0, buffer.Length);
                var text = Encoding.UTF8.GetString(buffer);
                var parameter = new JavaScriptSerializer().Deserialize(text, method.GetParameters()[0].ParameterType);
                if (hasOutput)
                {
                    var result = method.Invoke(null, new object[] {parameter});
                    WriteResult(context, result);
                }
                else
                {
                    method.Invoke(null, new object[] {parameter});
                }
            }
            else
            {
                if (hasOutput)
                {
                    var result = method.Invoke(null, null);
                    WriteResult(context, result);
                }
                else
                {
                    method.Invoke(null, null);
                }
            }
        }

        private void WriteResult(HttpListenerContext context, object result)
        {
            var text = new JavaScriptSerializer().Serialize(result);
            var buffer = Encoding.UTF8.GetBytes(text);
            context.Response.ContentLength64 = buffer.Length;
            context.Response.OutputStream.Write(buffer, 0, buffer.Length);
        }

        private MethodInfo GetMethod(Type serviceTypeype, Type attributeType)
        {
            foreach (var method in serviceTypeype.GetMethods())
            {
                if (method.GetCustomAttributes(attributeType, false).Length > 0)
                {
                    return method;
                }
            }
            return null;
        }

        private Type GetService(HttpListenerContext context)
        {
            var path = context.Request.RawUrl.Replace('/', '\\').Substring(1);
            if (!path.StartsWith("!"))
            {
                context.Response.StatusCode = (int) HttpStatusCode.BadRequest;
                return null;
            }
            path = path.Substring(1);
            if (path.EndsWith("\\"))
            {
                path = path.Substring(0, path.Length - 1);
            }
            IDictionary<string, Type> services = GetServices(directory);
            if (!services.ContainsKey(path))
            {
                context.Response.StatusCode = (int) HttpStatusCode.ServiceUnavailable;
                return null;
            }
            return services[path];
        }

        private IDictionary<string, Type> GetServices(string s)
        {
            var services = new Dictionary<string, Type>();
            foreach (var file in Directory.GetFiles(directory, "*.dll"))
            {
                try
                {
                    var assembly = Assembly.LoadFile(file);
                    foreach (var type in assembly.GetTypes())
                    {
                        var attributes = type.GetCustomAttributes(typeof (Rest.ServiceAttribute), false);
                        if (attributes.Length > 0)
                        {
                            var attribute = attributes[0] as Rest.ServiceAttribute;
                            services[attribute.Name] = type;
                        }
                    }
                }
                catch
                {
                    continue;
                }
            }
            return services;
        }

        private void HandleFile(HttpListenerContext context)
        {
            var path = context.Request.RawUrl.Replace('/', '\\').Substring(1);
            path = Path.Combine(directory, path);
            if (File.Exists(path))
            {
                context.Response.ContentType = GetMime(path);
                var buffer = File.ReadAllBytes(path);
                context.Response.ContentLength64 = buffer.Length;
                context.Response.OutputStream.Write(buffer, 0, buffer.Length);
            }
            else
            {
                context.Response.StatusCode = (int) HttpStatusCode.NotFound;
            }
        }

        private string GetMime(string path)
        {
            if (mimes == null)
            {
                mimes = new Dictionary<string, string>();
                mimes["txt"] = "text/plain";
                mimes["html"] = "text/html";
                mimes["js"] = "text/javascript";
                mimes["jpg"] = "image/jpeg";
                mimes["gif"] = "image/gif";
                mimes["png"] = "image/png";
            }
            var extension = new FileInfo(path).Extension.Substring(1);
            return mimes.ContainsKey(extension) ? mimes[extension] : "text/plain";
        }

        private void Stop()
        {
            if (listener != null && listener.IsListening)
            {
                listener.Stop();
                listener.Prefixes.Clear();
                listener = null;
            }
        }
    }
}
