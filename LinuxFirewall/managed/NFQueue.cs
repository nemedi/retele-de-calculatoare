
using System;
using System.Runtime.InteropServices;

internal static class NFQueue
{
    [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    public delegate int PacketDecision(IntPtr data, int len);

    [DllImport("nfq_bridge")]
    public static extern void start_nfqueue(PacketDecision decide);
}
