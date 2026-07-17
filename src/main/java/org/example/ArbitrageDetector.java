package org.example;
import java.math.BigDecimal;
import java.util.*;

public class ArbitrageDetector {

    private final Map<String, List<CurrencyExchange>> adj = new HashMap<>();

    public void addExchange(String source, String destination, double rate) {
        BigDecimal rateAsBigDecimal = BigDecimal.valueOf(rate);
        CurrencyExchange exchange = new CurrencyExchange(source, destination, rateAsBigDecimal);

        // Nëse monedha 'source' nuk ekziston në hartë, krijo një listë të re për të
        adj.putIfAbsent(source, new ArrayList<>());
        // Shto lidhjen në listën e asaj monedhe
        adj.get(source).add(exchange);
    }

    public boolean findArbitrage(String startCurrency) {

        Set<String> currencies = adj.keySet();

        //  Inicializo distancat
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        for (String node : currencies) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(startCurrency, 0.0);

        // (Bellman-Ford)
        for (int i = 0; i < currencies.size() - 1; i++) {
            for (String u : currencies) {
                if (distances.get(u) == Double.MAX_VALUE) continue;


                if (adj.containsKey(u)) {
                    for (CurrencyExchange e : adj.get(u)) {
                        if (distances.get(u) + e.weight < distances.get(e.destination)) {
                            distances.put(e.destination, distances.get(u) + e.weight);
                            parent.put(e.destination, u);
                        }
                    }
                }
            }
        }

        //  Kontrollo cikle negative
        for (String u : currencies) {
            if (adj.containsKey(u)) {
                for (CurrencyExchange e : adj.get(u)) {
                    if (distances.get(u) != Double.MAX_VALUE &&
                            distances.get(u) + e.weight < distances.get(e.destination)) {
                        System.out.println("! FITIM I GJETUR !!!");
                        printCycle(e.destination, parent);
                        return true ;
                    }
                }
            }
        }
        System.out.println("Nuk u gjet asnje rruge fitimprurese.");
        return false;
    }

    private void printCycle(String startNode, Map<String, String> parent) {

        List<String> cycle = new ArrayList<>();
        String current = startNode;
        for (int i = 0; i < parent.size(); i++) { current = parent.get(current); }
        String start = current;
        cycle.add(start);
        current = parent.get(start);
        while (!current.equals(start)) {
            cycle.add(current);
            current = parent.get(current);
        }
        cycle.add(start);
        Collections.reverse(cycle);
        System.out.println("Rruga: " + String.join(" -> ", cycle));
    }
}