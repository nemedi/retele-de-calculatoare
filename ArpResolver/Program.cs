using System;
using System.Collections.Generic;
using System.Net;
using System.Net.NetworkInformation;

class ArpResolver
{

	static IDictionary<IPAddress, PhysicalAddress> GetLocalIpMacMap()
	{
		var map = new Dictionary<IPAddress, PhysicalAddress>();
		foreach (var nic in NetworkInterface.GetAllNetworkInterfaces())
		{
			if (nic.OperationalStatus != OperationalStatus.Up)
				continue;
			var mac = nic.GetPhysicalAddress();
			if (mac == null || mac.GetAddressBytes().Length == 0)
				continue;
			var ipProps = nic.GetIPProperties();
			foreach (var addr in ipProps.UnicastAddresses)
			{
				if (IPAddress.IsLoopback(addr.Address))
					continue;
				map[addr.Address] = mac;
			}
		}
		return map;
	}
	
	static void Main(string[] arguments)
    {
		var map = GetLocalIpMacMap();
		foreach (var kv in map)
		{
			Console.WriteLine($"{kv.Key} → {kv.Value}");
		}
    }
}