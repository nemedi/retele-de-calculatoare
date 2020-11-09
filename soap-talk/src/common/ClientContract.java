package common;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ClientContract {

	@WebMethod
	@Oneway
	void onReceive(String message);
}
