package common;

import java.io.IOException;
import java.util.List;

import rpc.RpcCallback;

@RpcCallback(CallbackContract.class)
public interface Contract {
	
	void login(String name, List<String> files);
	void logout();
	void getFilesByUser();
	void transferFile(String user, String file) throws IOException;
	void onReadFile(String user, String file, String content) throws IOException;
}
