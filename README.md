# ReactiveSpringExperiment

A Demo to play around with the features of webflux and reactor

## Tracing
Spring cloud sleuth will send tracing data to zipkin. To run zipkin locally with docker run:

`docker run -d -p 9411:9411 openzipkin/zipkin`

Zipkin Ui will be running at http://localhost:9411/zipkin/

## Build & Test:
`./gradlew build `

## Run:
 `./gradlew bootRun` 
 
 
## Data:
 The books.csv file used to seed the database is a export of personal data from https://app.thestorygraph.com/
 
