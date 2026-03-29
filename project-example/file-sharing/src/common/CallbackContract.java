package common;

import java.io.IOException;
import java.util.Map;

public interface CallbackContract {

	void onDeny(String message);
	void onLogin();
	void onLogout();
	void onGetFilesByUser(Map<String, String[]> filesByUser);
	void onReadFile(String name) throws IOException;
	void onWriteFile(String name, byte[] content) throws IOException;
}
