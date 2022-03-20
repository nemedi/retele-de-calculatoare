package common;

import jakarta.jws.Oneway;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface ServerContract {

	@WebMethod
	@Oneway
	void subscribe();

	@WebMethod
	@Oneway
	void unsubscribe();
	
	@WebMethod
	@Oneway
	void send(String message);
}
