# fx-stream

This is a spring boot webflux application.
It can consume fx rate messages from a provider and expose rest end points to query the data.

Message e.g. 

"106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001"

The **_FxRateApplicationInitializer_** class
publishes some seed data.

To start the web-application
mvn spring-boot:run

## The end points are
To get specific rate

http://localhost:8080/fxRate/{baseCcy}/{quoteCcy}

e.g. http://localhost:8080/fxRate/EUR/USD

This returns all the latest rates for currency pair consumed by the application

http://localhost:8080/fxRates/