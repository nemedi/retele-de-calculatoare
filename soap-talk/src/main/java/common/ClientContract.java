package common;

import jakarta.jws.Oneway;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface ClientContract {

	@WebMethod
	@Oneway
	void onReceive(String message);
}
