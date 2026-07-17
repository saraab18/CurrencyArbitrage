package org.example;
import java.math.BigDecimal;
public class CurrencyExchange {
    String source, destination;
    double weight;
    private static final double FEE = 0.001;

    CurrencyExchange(String source, String destination, BigDecimal rate) {
        this.source = source;
        this.destination = destination;

        double effectiveRate = rate.doubleValue() * (1 - FEE);
        this.weight = -Math.log(effectiveRate);
    }
}