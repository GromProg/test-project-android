package ru.whitetigersoft.test_application.Model.Utils;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by denis on 31.08.16.
 */
public class JsonParser {

    public static <T> T convert(JSONObject object, Class<T> c) {
        GsonBuilder builder = getBuilder();
        Gson gson = builder.create();
        try {
            return gson.fromJson(object.toString(), c);
        } catch (JsonSyntaxException ex) {
            Log.e("JsonParser", String.format(
                    "Parsing error: Object %s isn't an instance of a class %s",
                    object.toString(),
                    c.toString(), ex.getMessage()
            ));
            return null;
        }
    }

    public static GsonBuilder getBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    long timestamp = json.getAsJsonPrimitive().getAsInt();
                    Date date = new Date(timestamp * 1000);
                    return date;
                } catch (Exception e) {
                    return null;
                }
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime() / 1000);
            }
        });

        builder.registerTypeAdapter(Boolean.class, new JsonDeserializer<Boolean>() {

            @Override
            public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                int val = json.getAsJsonPrimitive().getAsInt();
                return val == 1;
            }
        });


        Type type = new TypeToken<List<Pair<Integer, String>>>() {
        }.getType();
        builder.registerTypeAdapter(type, new JsonDeserializer<List<Pair<Integer, String>>>() {
            @Override
            public List<Pair<Integer, String>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray names = null;
                try {
                    names = jsonObject.names();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < names.length(); i++) {

                    try {
                        list.add(new Pair<Integer, String>(Integer.parseInt(names.getString(i)), jsonObject.getString(names.getString(i))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return list;
            }
        });
        return builder;
    }

    public static Object parseEntityFromJson(JSONObject object, Class c) {
        Object obj = convert(object, c);
        return obj;
    }
}
