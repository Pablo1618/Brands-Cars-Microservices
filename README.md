<img src="https://offering.solutions/wp-content/uploads/2024/03/presentation1-removebg-preview.png" width="30%" height="auto">

# Microservices App made in Java with Spring Boot and Angular

Web Services Architecture project in the 5th semester.
## Tech Stack
- Spring Boot
- Spring Data
- Spring MVC (Rest)
- Spring Cloud Gateway

## How to run it locally

Firstly, you have to install Docker and Git if you dont have them already.
#### 1. Pull this repository from GitHub

#### 2. Start docker container (while being in directory with docker-compose.yml file):
```
docker compose up -d
```
There are 6 images in total and all of them must be running for the app to work:
- brand-microservice
- car-microservice
- postgres-brands
- postgres-cars
- angular-app
- gateway

#### 3. Now you can access the website:

[localhost:80](localhost:80 "localhost:80")

<img src="https://i.ibb.co/mV6W6mYX/Zrzut-ekranu-2025-03-07-205355.png" width="75%" height="auto">
