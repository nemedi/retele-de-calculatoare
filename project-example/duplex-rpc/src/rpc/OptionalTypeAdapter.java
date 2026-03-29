package rpc;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalTypeAdapter<T> implements JsonSerializer<Optional<T>>, JsonDeserializer<Optional<T>> {

    @Override
    public JsonElement serialize(Optional<T> src, Type typeOfSrc, JsonSerializationContext context) {
        return src.map(value -> context.serialize(value))
                  .orElse(JsonNull.INSTANCE);
    }

    @Override
    public Optional<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return Optional.empty();
        }
        Type actualType;
        if (typeOfT instanceof ParameterizedType) {
            actualType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
        } else {
            actualType = Object.class;
        }
        T value = context.deserialize(json, actualType);
        return Optional.ofNullable(value);
    }
}