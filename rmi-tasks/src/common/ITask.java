package common;

import java.io.Serializable;

public interface ITask<T, V> extends Serializable {

	T execute(V argument) throws Exception;
}
