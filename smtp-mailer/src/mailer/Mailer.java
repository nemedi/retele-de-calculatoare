package mailer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class Mailer {
	
	public interface MailListener {
		void onSent(int index, String feedback);
	}
	
	private String id;
	private String host;
	private int port;
	private MailData data;
	private MailListener listener;

	public Mailer(String id,
			String host,
			int port,
			String csv,
			MailListener listener) throws FileNotFoundException, IOException {
		this.id = id;
		this.host = host;
		this.port = port;
		this.data = new MailData(csv);
		this.listener = listener;
	}
	
	public void send(String from, String subjectTemplate, String bodyTemplate)
			throws AddressException, MessagingException, FileNotFoundException, IOException {
		if (data.isEmpty()) {
			return;
		}
		Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        Session session = Session.getInstance(properties, null);
        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
        transport.connect(host, port, null, null);
        for (int i = 0; i < data.getLength(); i++) {
        	String to = null;
        	String subject = new String(subjectTemplate);
        	String body = new String(bodyTemplate.getBytes());
        	for (int j = 0; j < data.getFields().length; j++) {
        		String field = data.getFields()[j];
        		String value = data.getValue(i, j);
        		subject = subject.replace("{{" + field + "}}", value);
        		body = body.replace("{{" + field + "}}", value);
        		if (MailData.EMAIL.equalsIgnoreCase(field)) {
        			to = value;
        		}
        	}
        	if (to == null) {
        		continue;
        	}
        	Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
            		InternetAddress.parse(to, false));
            message.setSubject(subject);
            message.setText(body);
            message.setHeader("X-Mailer", id);
            message.setSentDate(new Date());
            transport.sendMessage(message, message.getAllRecipients());
            listener.onSent(i, transport.getLastServerResponse());
        }
        transport.close();
	}
	
	public MailData getData() {
		return data;
	}
	
}
