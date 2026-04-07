package rpc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

class RpcTransport {
	
	static {
		disableAccessWarnings();
	}
	
	public static void disableAccessWarnings() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

            Class<?> loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }
	
	private static Gson gson = new GsonBuilder()
			.registerTypeAdapter(Optional.class, new OptionalTypeAdapter<>())
			.registerTypeAdapter(RpcRequest.class, new RpcRequestDeserializer())
			.create();
	
	private static class RpcRequestDeserializer implements JsonDeserializer<RpcRequest> {

		@Override
		public RpcRequest deserialize(JsonElement element, Type type, JsonDeserializationContext context)
				throws JsonParseException {
			String service = element.getAsJsonObject().get("service") != null ?
					element.getAsJsonObject().get("service").getAsString() : null;
			String method = element.getAsJsonObject().get("method") != null ?
					element.getAsJsonObject().get("method").getAsString() : null;
			List<String> arguments = new ArrayList<String>();
			if (element.getAsJsonObject().get("arguments") != null) {
				for (Iterator<JsonElement> iterator = element.getAsJsonObject().get("arguments").getAsJsonArray().iterator();
						iterator.hasNext(); ) {
					arguments.add(iterator.next().toString());
				}
			}
			return new RpcRequest(service, method, arguments.toArray());
		}
		
	}
	
	public static <T> T deserialize(String data, Class<T> type) {
		return gson.fromJson(data, type);
	}
	
	public static String serialize(Object object) {
		return gson.toJson(object);
	}
}
