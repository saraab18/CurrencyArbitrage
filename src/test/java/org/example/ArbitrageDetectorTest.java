package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArbitrageDetectorTest {

    @Test
    public void testArbitrageFound() {
        ArbitrageDetector detector = new ArbitrageDetector();

        // Skenar: 1 USD -> 0.9 EUR -> 0.8 GBP -> 1.5 USD
        // Llogaritja: 0.9 * 0.8 * 1.5 = 1.08 (Fitim 8%)
        detector.addExchange("USD", "EUR", 0.9);
        detector.addExchange("EUR", "GBP", 0.8);
        detector.addExchange("GBP", "USD", 1.5);

        // Kjo duhet të kthejë 'true' nëse algoritmi është korrekt
        assertTrue(detector.findArbitrage("USD"), "Algoritmi duhet të kishte gjetur arbitrazh!");
    }

    @Test
    public void testNoArbitrage() {
        ArbitrageDetector detector = new ArbitrageDetector();

        // Skenar: 1 USD -> 0.8 EUR -> 1.0 USD
        // Llogaritja: 0.8 * 1.0 = 0.8 (Humbje, jo arbitrazh)
        detector.addExchange("USD", "EUR", 0.8);
        detector.addExchange("EUR", "USD", 1.0);

        // Kjo duhet të kthejë 'false'
        assertFalse(detector.findArbitrage("USD"), "Nuk duhet të gjente arbitrazh!");
    }
}