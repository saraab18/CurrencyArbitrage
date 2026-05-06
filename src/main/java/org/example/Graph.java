package org.example;
import java.util.*;


public class Graph {
    List<Edge> allEdges;

    public Graph() {
        this.allEdges = new ArrayList<>();
    }

    public void addEdge(String u, String v, double rate) {

        double weight = -Math.log(rate);
        allEdges.add(new Edge(u, v, weight));
    }
    public void findArbitrage(String startCurrency) {
        java.util.Set<String> nodes = new java.util.HashSet<>();
        for (Edge e : allEdges) {
            nodes.add(e.source);
            nodes.add(e.destination);
        }

        java.util.Map<String, Double> distances = new java.util.HashMap<>();
        java.util.Map<String, String> parent = new java.util.HashMap<>();

        for (String node : nodes) distances.put(node, Double.MAX_VALUE);
        distances.put(startCurrency, 0.0);


        for (int i = 0; i < nodes.size() - 1; i++) {
            for (Edge edge : allEdges) {
                if (distances.get(edge.source) != Double.MAX_VALUE &&
                        distances.get(edge.source) + edge.weight < distances.get(edge.destination)) {
                    distances.put(edge.destination, distances.get(edge.source) + edge.weight);
                    parent.put(edge.destination, edge.source); // Mbajmë mend rrugën
                }
            }
        }

        for (Edge edge : allEdges) {
            if (distances.get(edge.source) != Double.MAX_VALUE &&
                    distances.get(edge.source) + edge.weight < distances.get(edge.destination)) {

                System.out.println("!!! FITIM I GJETUR !!!");

                String current = edge.destination;
                java.util.List<String> cycle = new java.util.ArrayList<>();

                for (int i = 0; i < nodes.size(); i++) {
                    current = parent.get(current);
                }

                String startNode = current;
                cycle.add(startNode);
                current = parent.get(startNode);
                while (!current.equals(startNode)) {
                    cycle.add(current);
                    current = parent.get(current);
                }
                cycle.add(startNode);

                java.util.Collections.reverse(cycle);
                System.out.println("Rruga: " + String.join(" -> ", cycle));
                return;
            }
        }
        System.out.println("Nuk u gjet asnjë rrugë fitimprurëse.");
    }

    }
