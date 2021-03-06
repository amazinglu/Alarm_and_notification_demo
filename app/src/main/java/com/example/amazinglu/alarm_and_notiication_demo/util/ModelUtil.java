package com.example.amazinglu.alarm_and_notiication_demo.util;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class ModelUtil {

    private static Gson gsonForSerialize = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .create();

    private static Gson gsonForDeserialize = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .create();

    public static <T> T toObject(String json, TypeToken<T> typeToken) {
        return gsonForDeserialize.fromJson(json, typeToken.getType());
    }

    public static String toJson(Object object) {
        return gsonForSerialize.toJson(object);
    }


    static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement dateStr, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return DateUtil.StringToDateTime(dateStr.getAsString());
        }
    }

    static class DateSerializer implements JsonSerializer<Date> {
        @Override
        public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DateUtil.dateTimeToString(date));
        }
    }
}
