package common;

import rpc.RpcCallback;

@RpcCallback(CallbackContract.class)
public interface Contract {
	
	void login(String name, String[] files);
	void logout();
	void getFilesByUser();
	void transferFile(String user, String file);
	void onReadFile(String name, byte[] data);
}
