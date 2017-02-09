package uk.gov.ons.acceptance.reader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Scanner;

public class JsonReader {
    private  final Gson gson;
    private final JsonParser jsonParser;

    public JsonReader(Gson gson, JsonParser jsonParser) {
        this.gson = gson;
        this.jsonParser = jsonParser;
    }

    public String readFile(String folder, String fileName) {
        final String file = new Scanner(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(folder + "/" + fileName), "UTF-8")
                .useDelimiter("\\A")
                .next();

        return sanitise(file);
    }

    private String sanitise(String value) {
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