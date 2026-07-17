package org.example;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class ExchangeRateProvider {

    public Map<String, Double> fetchRates(String baseCurrency) throws Exception {
        String apiKey = loadApiKey();
        if (apiKey == null) throw new Exception("API Key mungon!");

        String urlString = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;
        HttpURLConnection request = (HttpURLConnection) new URL(urlString).openConnection();
        request.connect();

        Scanner scanner = new Scanner(new InputStreamReader(request.getInputStream()));
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONObject jsonResponse = new JSONObject(response);
        JSONObject ratesJson = jsonResponse.getJSONObject("conversion_rates");

        // Konvertimi i JSON në Map
        Map<String, Double> ratesMap = new HashMap<>();
        for (String key : ratesJson.keySet()) {
            ratesMap.put(key, ratesJson.getDouble(key));
        }
        return ratesMap;
    }

    private String loadApiKey() {
        Properties prop = new Properties();
        try (InputStream input = ExchangeRateProvider.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) return null;
            prop.load(input);
            return prop.getProperty("api_key");
        } catch (IOException e) {
            return null;
        }
    }
}