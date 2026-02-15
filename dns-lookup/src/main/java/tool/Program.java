package tool;

import java.util.List;

import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.Type;

public class Program {
    public static void main(String[] args) {
    	// args[0]: A | AAAA | MX | TXT | CNAME | NS
    	// args[1]: domain name
    	if (args.length == 2) {
    		String type = args[0].toUpperCase();
    		if (type.startsWith("-")) {
    			type = type.substring(1);
    		}
    		String domain = args[1];
    		try {
    			Lookup lookup = new Lookup(domain, Type.value(type));
    			lookup.run();
    			if (lookup.getResult() == Lookup.SUCCESSFUL) {
    				Record[] records = lookup.getAnswers();
    				for (Record record : records) {
    					printRecord(record);
    				}
    			} else {
    				System.out.println("DNS lookup failed: " + lookup.getErrorString());
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} else {
    		System.out.println("Usage: java " + Program.class.getName() + " -<type: A | AAAA | MX | TXT | CNAME | NS> <domain>");
    		System.exit(1);
    	}
    }

	private static void printRecord(Record record) {
		if (record instanceof ARecord) {
			ARecord aRecord = (ARecord) record;
            System.out.println("IP Address: " + aRecord.getAddress().getHostAddress());
		} else if (record instanceof AAAARecord) {
			AAAARecord aaaaRecord = (AAAARecord) record;
            System.out.println("IPv6 Address: " + aaaaRecord.getAddress().getHostAddress());
		} else if (record instanceof MXRecord) {
			MXRecord mxRecord = (MXRecord) record;
            System.out.println("Priority: " + mxRecord.getPriority() + " -> Mail Server: " + mxRecord.getTarget());
		} else if (record instanceof TXTRecord) {
			TXTRecord txtRecord = (TXTRecord) record;
            List<String> textStrings = txtRecord.getStrings();
            for (String text : textStrings) {
                System.out.println(text);
            }
		} else if (record instanceof CNAMERecord) {
			CNAMERecord cnameRecord = (CNAMERecord) record;
            System.out.println("Canonical Name: " + cnameRecord.getTarget());
		} else if (record instanceof NSRecord) {
			 NSRecord nsRecord = (NSRecord) record;
             System.out.println("Name Server: " + nsRecord.getTarget());
		}
		
	}
}
