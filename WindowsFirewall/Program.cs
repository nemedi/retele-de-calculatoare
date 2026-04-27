using System;
using static WinDivert;

class Program
{
    static void Main(string[] arguments)
    {
		if (arguments.Length == 1)
		{
			string filter = arguments[0]; // Example filter: "tcp.DstPort == 6969 and inbound"
			IntPtr handle = WinDivertOpen(
				filter,
				WINDIVERT_LAYER_NETWORK,
				0,
				0
			);
			if (handle == IntPtr.Zero)
			{
				Console.WriteLine("Failed to open WinDivert. Run as Administrator.");
				return;
			}
			Console.WriteLine("WinDivert started. Dropping packets...");
			byte[] buffer = new byte[65535];
			uint packetLen = 0;
			WINDIVERT_ADDRESS addr = new WINDIVERT_ADDRESS
			{
				Reserved = new byte[64]
			};
			while (true)
			{
				if (!WinDivertRecv(handle, buffer, (uint)buffer.Length, ref packetLen, ref addr))
					continue;
			}
			
		}
    }
}