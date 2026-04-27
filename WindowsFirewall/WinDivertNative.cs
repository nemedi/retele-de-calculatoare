using System;
using System.Runtime.InteropServices;

internal static class WinDivert
{
    public const int WINDIVERT_LAYER_NETWORK = 0;

    [StructLayout(LayoutKind.Sequential)]
    public struct WINDIVERT_ADDRESS
    {
        public ulong Timestamp;
        public byte Layer;
        public byte Event;
        public ushort Flags;
        [MarshalAs(UnmanagedType.ByValArray, SizeConst = 64)]
        public byte[] Reserved;
    }

    [DllImport("WinDivert.dll", CallingConvention = CallingConvention.Cdecl)]
    public static extern IntPtr WinDivertOpen(
        string filter,
        int layer,
        short priority,
        ulong flags
    );

    [DllImport("WinDivert.dll", CallingConvention = CallingConvention.Cdecl)]
    public static extern bool WinDivertRecv(
        IntPtr handle,
        byte[] packet,
        uint packetLen,
        ref uint recvLen,
        ref WINDIVERT_ADDRESS addr
    );

    [DllImport("WinDivert.dll", CallingConvention = CallingConvention.Cdecl)]
    public static extern bool WinDivertSend(
        IntPtr handle,
        byte[] packet,
        uint packetLen,
        ref WINDIVERT_ADDRESS addr,
        ref uint sendLen
    );

    [DllImport("WinDivert.dll", CallingConvention = CallingConvention.Cdecl)]
    public static extern bool WinDivertClose(IntPtr handle);
}