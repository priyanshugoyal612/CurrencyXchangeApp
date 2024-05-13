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
- Add instrumentation with InfluxDB and Grafana for real-time monitoring and analytics.
- Provide a Angular 17 user interface for smooth interactions with the currency conversion service.


Technical clarification
• Spring Boot 3
• Maven
• Angular 17
• EhCache
• Grafana
• Prometheus
• Docker




## Installation

To run the currency conversion application using Docker Compose, follow these steps:

1. Clone the repository:

   git clone https://github.com/your-username/currency-conversion-app.git

2. Navigate to the project directory then backend app:
  cd currency-conversion-app/
3. To build the backend please run 
  
* To Start the Backend
  * Windows: mvnw.cmd spring-boot:run
  * Unix: mvnw spring-boot:run

* To Start Frontend
  * npm install
  * npm start

* After application started you can check
  * http://localhost:4200/
  
