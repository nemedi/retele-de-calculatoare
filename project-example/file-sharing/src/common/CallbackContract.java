package common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CallbackContract {

	void onDeny(String message);
	void onLogin();
	void onLogout();
	void onGetFilesByUser(Map<String, List<String>> filesByUser);
	void onReadFile(String user, String file) throws IOException;
	void onWriteFile(String file, String content) throws IOException;
}
