package com.dita.xd.service.implementation;

import com.dita.xd.service.TranslationService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TranslationServiceImpl implements TranslationService {
    private static final String API_KEY = "8cf401ff-fe2b-0662-c563-2c90c873773b:fx";
    private static final String API_URL = "https://api-free.deepl.com/v2/translate";

    @Override
    public String translate(String text, String targetLang) {
        JSONArray textArray = new JSONArray();
        JSONObject params = new JSONObject();
        textArray.add(text);
        params.put("text", textArray);
        params.put("target_lang", targetLang);

        String translated = null;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "DeepL-Auth-Key " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(params.toJSONString()))
                .build();

        try {
            String responseBody = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .get();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            JSONArray jsonArray = (JSONArray) jsonObject.get("translations");
            JSONObject script = (JSONObject) jsonArray.get(0);
            translated = (String) script.get("text");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translated;
    }
}
