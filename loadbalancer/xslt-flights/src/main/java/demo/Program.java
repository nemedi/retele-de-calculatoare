package demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.transform.TransformerException;

public class Program {

	public static void main(String[] args) {
		try {
			byte[] input = Files.readAllBytes(Paths.get("./src/main/resources/airlines.xml")); 
			byte[] output = XsltTransformer.transform("./src/main/resources/flights.xsl", input);
			Files.write(Paths.get("./src/main/resources/flights.xml"), output);
		} catch (TransformerException | IOException e) {
			e.printStackTrace();
		}
	}

}
