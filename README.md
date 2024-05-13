# Currency Xchange App By Priyanshu Goyal
This project implements a RESTful API for currency conversion, leveraging exchange rates from the SWOP API. Users can input a source currency, a target currency, and a monetary value, 
nd the API will return the converted value based on the provided exchange rates.

## Features

- Convert currency amounts from one currency to another.
- Retrieve the latest exchange rates from the SWOP API.
- Validate input parameters to ensure correct usage.
- Handle errors for invalid input and API responses.
- Implement caching for improved performance.
- Ensure security with CSRF and CSP.
- Containerize the application with Docker for consistent deployment.
- Add instrumentation with InfluxDB and Grafana for real-time monitoring and analytics. Its also preloaded with the dashboard
- Provide a Angular 17 user interface for smooth interactions with the currency conversion service.


Technical clarification
• Spring Boot 3
• Maven
• Java 21
• Angular 17
• EhCache
• Grafana
• Prometheus
• Docker




## Installation

To run the currency conversion application using Docker Compose, follow these steps:

1. Clone the repository:

   git clone https://github.com/priyanshugoyal612/CurrencyXchangeApp.git

2. Navigate to the project directory then backend app:
  cd currency-conversion-app/
3. To build the backend please run 
  
* To build the Backend
  *  mvn clean install


* Then come to the root directory where dockercompose is present and run 
  * docker-compose up
  * npm start

* After application started you can check the CurrencyXchangeApp
  * http://localhost:4200/
  
  * After Backend started you can check the CurrencyXchangeApp
  * http://localhost:8080/a[i/exchange
  
  * After Grafana started you can check the CurrencyXchangeApp
  * http://localhost:3000/
  
