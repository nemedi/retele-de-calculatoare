using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            using (var server = new Server())
            {
                server.Start(Settings.Default.Host, Settings.Default.Port, Environment.CurrentDirectory);
                Console.WriteLine("Server is started, type 'exit' to stop it.");
                while (Console.ReadLine().Trim().ToLower() != "exit")
                {
                }
            }
        }
    }
}
