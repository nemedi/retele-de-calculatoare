package server;

import java.io.ByteArrayOutputStream;

import jakarta.activation.DataHandler;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.ws.soap.MTOM;

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
			byte[] data = XsltTransformer.transform(String.format("transformations/%s.xsl",
					transformation),
					stream.toByteArray());
			return new DataHandler(new String(data), "text/xml");
		}
	}

}
