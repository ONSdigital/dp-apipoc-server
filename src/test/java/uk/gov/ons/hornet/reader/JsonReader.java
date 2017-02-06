package uk.gov.ons.hornet.reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Scanner;

public class JsonReader {

    private final JsonParser jsonParser;
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    public JsonReader(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public String readFile(String folder, String fileName) {
        final String file = new Scanner(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(folder + "/" + fileName), "UTF-8")
                .useDelimiter("\\A")
                .next();

        return sanitise(file);
    }

    public String sanitise(String value) {
        return gson.toJson(jsonParser.parse(value));
    }


    public JsonElement getJson(String folder, String fileName) {
        final String file = new Scanner(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(folder + "/" + fileName), "UTF-8")
                .useDelimiter("\\A")
                .next();

        return jsonParser.parse(file);
    }



}