# TreesApp
Backend for **Automated tree condition monitoring system** web service application <br/>
designed within **IT-Revolution** hackathon by team **4vesla**.

## Server side REST API
## Endpoints:
* POST /login HTTP/1.1 - jwt authorization
* POST /registration HTTP/1.1 - customer registration
* GET /activate/email/{id}/{activationCode} HTTP/1.1 - activates customer account
* GET /customers HTTP/1.1 - retrieves list of customers
* GET /customers/{id} HTTP/1.1 - retrieves customer with id
* PUT /customers/{id} HTTP/1.1 - updates data about customer with id
* DELETE /customers/{id} HTTP/1.1 - removes customer's account

## Technologies:
* Java 11
* Maven
* Spring Boot, Spring Data, Spring Security
* PostgreSQL
* FlyWay
* AWS S3
* AWS EC2

## Hosted
The REST API is hosted on domain roman-ko.net.
