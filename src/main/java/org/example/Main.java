package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // user zgjedh bazen
        System.out.print("Shkruaj monedhen qe do të perdoresh si BAZE (p.sh. EUR, USD, ALL): ");
        String baseCurrency = scanner.next().toUpperCase();

        try {
            ExchangeRateProvider provider = new ExchangeRateProvider();

            Map<String, Double> rates = provider.fetchRates(baseCurrency);

            // 3. Lista e monedhave per analize
            List<String> currencies = new ArrayList<>(Arrays.asList(
                    "USD", "EUR", "JPY", "GBP", "CHF", "CAD", "AUD", "NZD",
                    "NOK", "SEK", "CNY", "INR", "BRL", "MXN", "ZAR", "TRY",
                    "SGD", "HKD", "KRW", "AED"
            ));

            System.out.println("Duke analizuar tregun bazuar ne: " + baseCurrency);

            ArbitrageDetector detector = new ArbitrageDetector();

            // 4. Ndertimi i grafin
            for (String base : currencies) {
                for (String target : currencies) {
                    if (!base.equals(target) && rates.containsKey(base) && rates.containsKey(target)) {

                        double rate = rates.get(target) / rates.get(base);
                        detector.addExchange(base, target, rate);
                    }
                }
            }


            detector.findArbitrage(baseCurrency);

        } catch (Exception e) {
            System.err.println("Gabim: " + e.getMessage());
        }
    }
}