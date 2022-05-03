using System;
using System.Net;
using Rpc;

namespace Server
{
	class Program
	{
		static void Main(string[] args)
		{
			Service.Repository = Repository.Load(Settings.Default.RepositoryPath);
			RpcHost.Log = Settings.Default.LogPath;
			using (var host = new RpcHost(typeof(Service), Settings.Default.ServerEndpoint))
			{
				Console.WriteLine("Server is started, type 'exit' to stop it.");
				while (Console.ReadLine().Trim().ToLower() != "exit")
				{
				}
			}
		}
	}
}
