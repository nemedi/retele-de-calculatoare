package com.example.xslt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jakarta.activation.DataHandler;
import jakarta.jws.WebService;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.ws.soap.MTOM;

@WebService(targetNamespace = "http://example.com", serviceName = "transformer",
	endpointInterface = "com.example.xslt.ITransformerService")
@MTOM
public class TransformerService implements ITransformerService {
	
	private static final TransformerFactory factory;
	
	static {
		System.setProperty(
				"javax.xml.transform.TransformerFactory",
				"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl"
				);
		factory = TransformerFactory.newInstance();
	}

	@Override
	public @XmlMimeType("application/octet-stream") DataHandler transform(String mapper,
			@XmlMimeType("application/octet-stream") DataHandler payloadHandler) throws Exception {
		String xml = transform("/xsl/" + mapper + ".xsl", convertToString(payloadHandler),
				Map.of("processedBy", InetAddress.getLocalHost().getHostName(),
						"processedAt", LocalDate.now().toString()));
		return convertToDataHandler(xml);
	}
	
	private static String convertToString(DataHandler handler)
			throws IOException {
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			handler.writeTo(stream);
			return new String(stream.toByteArray());
		}
	}
	
	private static DataHandler convertToDataHandler(String payload) 
			throws IOException {
		return new DataHandler(
				new ByteArrayDataSource(payload.getBytes(StandardCharsets.UTF_8),
						"application/xml"));
	}

	
	private static String transform(String xsl, String xml, Map<String, String> parameters) throws TransformerException {
        InputStream xslt = TransformerService.class.getResourceAsStream(xsl);
        if (xslt == null) {
            throw new IllegalArgumentException("XSLT resource not found: " + xsl);
        }
        Transformer transformer = factory.newTransformer(new StreamSource(xslt));
        parameters.forEach(transformer::setParameter);
        StringWriter out = new StringWriter();
        transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(out));
        return out.toString();
    }

}
