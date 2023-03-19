using System.Net;
public class Program
{
    public static void Main(string[] args)
    {
        var argument = args.Length == 1 ? args[0] : Dns.GetHostName();
        IPAddress? ipAddress;
        if (IPAddress.TryParse(argument, out ipAddress))
        {
            var entry = Dns.GetHostEntry(ipAddress);
            Console.WriteLine("Aliases:");
            foreach (var alias in entry.Aliases)
            {
                Console.WriteLine("\t" + alias);
            }
        }
        else
        {
            var entry = Dns.GetHostEntry(argument);
            Console.WriteLine("Addresses:");
            foreach (var address in entry.AddressList)
            {
                Console.WriteLine("\t" + address);
            }
        }
    }
}