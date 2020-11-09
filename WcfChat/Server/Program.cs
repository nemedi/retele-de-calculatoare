using System;
using System.ServiceModel;

namespace Server
{
	class Program
	{
		static void Main(string[] args)
		{
			using (var host = new ServiceHost(typeof(ChatService)))
			{
				host.Open();
				Console.WriteLine("Server is running, type 'exit' to close it.");
				while (true)
				{
					if (Console.ReadLine()?.ToLower() == "exit")
					{
						break;
					}
				}
			}
		}
	}
}
