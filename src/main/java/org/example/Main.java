package org.example;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph myGraph = new Graph();
        try {
            // Lidhja me API
            String urlString = " https://v6.exchangerate-api.com/v6/4a7bd937be58140641edfdee/latest/USD";
            URL url = new URL(urlString);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            Scanner scanner = new Scanner(new InputStreamReader(request.getInputStream()));
            String response = scanner.useDelimiter("\\A").next(); // KËTU krijohet 'response'
            scanner.close();


            // Leximi  (JSON)
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject rates = jsonResponse.getJSONObject("conversion_rates");
            long nextUpdate = jsonResponse.getLong("time_next_update_unix");
            System.out.println("Përditësimi i radhës pritet në: " + new java.util.Date(nextUpdate * 1000));

            double usdToAll = rates.getDouble("ALL");
            double usdToEur = rates.getDouble("EUR");

            myGraph.addEdge("USD", "ALL", usdToAll);
            myGraph.addEdge("USD", "EUR", usdToEur);

            String[] currencies = {"USD", "EUR", "ALL", "GBP", "JPY", "CHF", "CAD", "AUD", "TRY", "BRL"};

            for (String base : currencies) {
                for (String target : currencies) {
                    if (rates.has(base) && rates.has(target) && !base.equals(target)) {
                        double rateBaseToUsd = rates.getDouble(base);
                        double rateTargetToUsd = rates.getDouble(target);


                        double exchangeRate = rateTargetToUsd / rateBaseToUsd;

                        myGraph.addEdge(base, target, exchangeRate);
                    }
                }
            }

            System.out.println("Grafiku u mbush me " + currencies.length + " valuta!");
            System.out.println("Kursi GBP/JPY: " + rates.getDouble("JPY") / rates.getDouble("GBP"));
            System.out.println("Kursi JPY/GBP: " + rates.getDouble("GBP") / rates.getDouble("JPY"));

            myGraph.findArbitrage("USD");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}