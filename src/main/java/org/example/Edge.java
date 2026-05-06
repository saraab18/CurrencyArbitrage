package org.example;
public class Edge {
    String source, destination;
    double weight;

    Edge(String source, String destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}