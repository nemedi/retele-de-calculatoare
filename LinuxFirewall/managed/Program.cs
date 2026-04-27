
using System;
using System.Runtime.InteropServices;

class Program
{
    static int Decide(IntPtr data, int len)
    {
        // DROP everything that reaches NFQUEUE
        return 0; // 0 = DROP, 1 = ACCEPT
    }

    static void Main()
    {
        Console.WriteLine("NFQUEUE firewall running...");
        NFQueue.start_nfqueue(Decide);
    }
}
