package com.ghn1712.guiabolso.books.serializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class Serializer {

    private Serializer() {
    }

    private static final Gson gson = new Gson();

    public static <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> String serialize(T object) {
        return gson.toJson(object, new TypeToken<T>(object.getClass()) {}.getType());
    }
}
