package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

public class PacketCapture {
	public static void main(String[] args) throws Exception {
		List<PcapNetworkInterface> interfaces = Pcaps.findAllDevs();
		if (interfaces == null || interfaces.isEmpty()) {
			System.out.println("No network interfaces found.");
			return;
		}
		System.out.println("Available Network Interfaces:");
		for (int i = 0; i < interfaces.size(); i++) {
			PcapNetworkInterface nif = interfaces.get(i);
			System.out.println(i + ": " + nif.getName() + " (" + nif.getDescription() + ")");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Select interface number: ");
		int choice = Integer.parseInt(reader.readLine());
		if (choice < 0 || choice >= interfaces.size()) {
			System.out.println("Invalid selection.");
			return;
		}
		PcapNetworkInterface nif = interfaces.get(choice);
		System.out.println("Capturing on: " + nif.getName());
		int snapLen = 65536;
		int timeout = 10;
		PcapHandle handle = nif.openLive(snapLen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, timeout);
		System.out.print("Enter BPF filter (or press Enter for no filter): ");
		String filter = reader.readLine();
		if (filter != null && !filter.trim().isEmpty()) {
			try {
				handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
				System.out.println("Filter applied: " + filter);
			} catch (PcapNativeException | NotOpenException e) {
				System.out.println("Invalid filter syntax: " + e.getMessage());
				handle.close();
				return;
			}
		} else {
			System.out.println("No filter applied.");
		}
		PacketListener listener = packet -> {
			System.out.println("Packet captured:");
			System.out.println(packet);
		};
		try {
			handle.loop(-1, listener);
		} catch (InterruptedException e) {
			System.out.println("Capture stopped.");
		}
		handle.close();
	}
}
