namespace Server
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);
            builder.Services.AddGrpc();
            var application = builder.Build();
            application.MapGrpcService<ChatService>();
            application.MapGet("/", () => "It's working!");
            application.Run();
        }
    }
}