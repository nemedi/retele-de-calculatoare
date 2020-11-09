package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.soap.MTOM;

@WebService(endpointInterface = "server.Contract")
@MTOM
public class Service implements Contract {
	
	@Override
	public @XmlMimeType("application/octet-stream")
		DataHandler transform(String transformation,
		@XmlMimeType("application/octet-stream")
		DataHandler handler) throws Exception {
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			handler.writeTo(stream);
			byte[] data = transform(String.format("transformations/%s.xsl",
					transformation),
					stream.toByteArray());
			return new DataHandler(new String(data), "text/xml");
		}
	}
	
	private static byte[] transform(String transformation, byte[] data)
			throws TransformerException, IOException {
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			Transformer transformer = TransformerFactory.newInstance()
				.newTransformer(new StreamSource(new File(transformation)));
			transformer.transform(new StreamSource(
						new ByteArrayInputStream(data)),
					new StreamResult(stream));
			return stream.toByteArray();
		}
		
	}

}
