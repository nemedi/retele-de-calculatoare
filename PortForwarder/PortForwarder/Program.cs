using Open.Nat;

namespace PortForwarder
{
    public class Program
    {
        static async Task Main(string[] arguments)
        {
            try
            {
                if (arguments.Length == 3 && (arguments[0].ToLower() == "tcp" || arguments[0] == "udp"))
                {
                    var protocol = arguments[0].ToLower();
                    var sourcePort = int.Parse(arguments[1]);
                    var targetPort = int.Parse(arguments[2]);
                    var discoverer = new NatDiscoverer();
                    var cancelationTokenSource = new CancellationTokenSource(10000);
                    var device = await discoverer.DiscoverDeviceAsync(PortMapper.Upnp, cancelationTokenSource);
                    await device.CreatePortMapAsync(
                        new Mapping(
                            protocol == "tcp" ? Protocol.Tcp : Protocol.Udp,
                            sourcePort,
                            targetPort,
                            $"Port Forwarding using Open.NAT ({sourcePort} => {targetPort})"
                        )
                    );
                    Console.WriteLine($"Forwarding {protocol.ToUpper()} port {sourcePort} to {targetPort}");
                    Console.WriteLine("Type 'exit' to close it.");
                    while (Console.ReadLine()?.ToLower() != "exit")
                    {
                    }
                }
            }
            catch (Exception exeption)
            {
                Console.WriteLine($"Exception: {exeption.Message}");
            }
        }
    }
}
