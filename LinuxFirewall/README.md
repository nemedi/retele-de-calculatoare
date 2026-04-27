
# Linux NFQUEUE + .NET Firewall

## Overview
This project provides a ready-to-build Linux firewall equivalent to WinDivert on Windows.
Packets are intercepted in the Linux kernel using Netfilter NFQUEUE and a minimal native bridge.
All verdict decisions are made in C#.

## Requirements
- Linux
- root privileges
- libnetfilter-queue-dev
- cmake, gcc
- .NET 8 SDK

## Build Native Bridge
```bash
cd native
cmake -B build
cmake --build build
```

## Build Managed Code
```bash
cd managed
dotnet build -c Release
```

## Install iptables Rule
```bash
sudo iptables -I INPUT -p tcp --dport 6969 -j NFQUEUE --queue-num 0
```

## Run Firewall
```bash
sudo LD_LIBRARY_PATH=../native/build dotnet run
```

## Warning
If the process exits unexpectedly, queued packets may stall connectivity.
Always test carefully.

## Remove iptables Rule
```bash
sudo iptables -D INPUT -p tcp --dport 6969 -j NFQUEUE --queue-num 0
```
