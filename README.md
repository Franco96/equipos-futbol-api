# equipos-futbol-api
API para gestionar equipos de fútbol


# Pasos para ejecutar api en docker

- mvn clean install
- docker build -t equipos-futbol-api .
- docker run -p 8080:8080 equipos-futbol-api