package common;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

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
