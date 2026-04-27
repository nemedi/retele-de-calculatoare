# WinDivert C# Packet Drop Example

## Description
This project demonstrates how to **drop network packets at kernel level on Windows** using **WinDivert** from **C#**.

Packets matching the WinDivert filter are intercepted in the kernel. If your program does not re-inject them using `WinDivertSend`, the packets are dropped.

## Requirements
- Windows 10 or newer
- Administrator privileges
- .NET 8 SDK
- WinDivert (download separately)

## Setup
1. Download WinDivert from https://reqrypt.org/windivert.html
2. Copy the following files next to the built executable:
   - WinDivert.dll
   - WinDivert64.sys
3. Build the project:
dotnet build -c Release

4. Test it:
WindowsFirewall.exe "ip and tcp and ((ip.DstAddr == 192.168.1.35 and tcp.DstPort == 6969) or (ip.SrcAddr == 192.168.1.35 and tcp.SrcPort == 6969))"