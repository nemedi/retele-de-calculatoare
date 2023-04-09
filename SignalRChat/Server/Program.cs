namespace Server
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);
            builder.Services.AddSignalR(configure =>
            {
                configure.KeepAliveInterval = TimeSpan.FromMinutes(10);
            });
            var application = builder.Build();
            application.MapHub<ChatHub>("/chat");
            application.MapGet("/", () => "It's working!");
            application.Run();
        }
    }
}