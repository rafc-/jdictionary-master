package org.ryan.dictionary.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.extern.java.Log;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Log
public final class DictionaryProcessor {

    static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    static final Gson GSON = new Gson();

    public static WordData fetchData(String word) {
        URL url;
        StringBuilder textData = new StringBuilder();

        try {
            url = new URL(API_URL + word);
        } catch (MalformedURLException e) {
            log.severe(String.format("Invalid URL: %s", API_URL + word));
            return null;
        }

        int responseCode;
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            responseCode =  connection.getResponseCode();

            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNext()) {
                textData.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            log.severe(String.format("Could not load: %s", API_URL + word));
            return null;
        }

        return parseJSON(textData.toString(), responseCode);
    }

    private static WordData parseJSON(String input, int code) {
        if (code == 200) {
            JsonArray array = GSON.fromJson(input, JsonArray.class);
            JsonElement data = array.get(0);
            System.out.println(data);
            return GSON.fromJson(data, WordData.class);
        }

        return null;
    }
}
