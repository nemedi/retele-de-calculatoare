package com.example.xslt;

import jakarta.activation.DataHandler;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface ITransformerService {

	@WebMethod
	DataHandler transform(String mapper, DataHandler payloadHandler) throws Exception;
}
