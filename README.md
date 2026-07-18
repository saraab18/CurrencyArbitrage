# Currency Arbitrage Detector

A tool for detecting arbitrage opportunities across currency markets using the Bellman-Ford algorithm.

### Overview
This project identifies potential arbitrage cycles in forex markets. It converts exchange rates into logarithmic weights, transforming the problem of finding a profitable product of rates into the problem of finding a negative cycle in a directed graph.

### Technical Approach
- **Core Algorithm**: Implements Bellman-Ford to detect negative cycles within the currency graph.
- **Data Handling**: Fetches live market data via REST API.
- **Weight Calculation**: To account for real-world scenarios, the cost function incorporates fixed trading fees:
$$Weight = -\ln(Rate \times (1 - Fee))$$
- **Architecture**: Designed with separation of concerns between API data ingestion and graph-based analysis.

### Features
- **Real-time Analysis**: Calculates arbitrage paths based on live API data.
- **Fee Awareness**: Filters out theoretical arbitrage that would be invalidated by standard exchange commissions.
- **Unit Tested**: Includes JUnit 5 test cases to verify algorithm accuracy against known scenarios.

### Getting Started
1. **API Key**: Create a `src/main/resources/config.properties` file:
   ```properties
   api_key=YOUR_EXCHANGE_RATE_API_KEY
  
Build: This project uses Maven. Ensure json and junit-jupiter dependencies are included in your pom.xml.

### Running the Application
Run: Execute the Main class and provide the base currency when prompted.

## Why Bellman-Ford?
I chose Bellman-Ford because it is the best way to find "negative cycles" in a graph. Since I converted exchange rates into negative logarithms, finding a profitable arbitrage opportunity is exactly the same as finding a negative cycle. This algorithm allows me to detect complex, multi-step trades that simpler methods would miss.
