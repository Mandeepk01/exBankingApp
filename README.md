# exbankingApp

## Table of Contents

- #introduction
- #prerequisites
- #installation
- #api-endpoints
- #mockmvc-testing

## Introduction

This is the README for the `exbankingApp`. It provides information on how to set up, run, and use the application. The application allows users to create accounts, deposit and withdraw money, and check their account balances.

## Prerequisites

Before running the project, ensure you have the following prerequisites installed:

- Java Version: 20
- PostgreSQL: Follow the installation instructions to download https://www.postgresqltutorial.com/postgresql-getting-started/install-postgresql-macos/
- PgAdmin: Download it from https://www.pgadmin.org/download/


## Installation

To build and run the project, follow these steps:

1. Clone the repository to your local machine.
2. Ensure PostgreSQL is running and create a database for the application.
3. Configure the database connection in the `application.properties` file.


## Api-endpoints
### Create User

curl --location --request POST 'localhost:8080/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Mandeep",
    "lastName": "Kaur",
    "userId": "mandeep.kaur@gmail.com",
    "currency": "INR",
    "mobileNo": "9611343425"
}'

### Deposit Amount

curl --location --request POST 'localhost:8080/banking/deposit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount": 51.5,
    "userId": "mandeep.kaur@gmail.com"
}'

### Check Account Balance

curl --location --request GET 'localhost:8080/banking/balance/mandeep.kaur@gmail.com'

### Withdraw Amount

curl --location --request POST 'localhost:8080/banking/withdraw' \
--header 'Content-Type: application/json' \
--data-raw '{
    "withdrawAmount": 0.5,
    "userId": "mandeep.kaur@gmail.com"
}'

## MockMVC Testing
### To run MockMVC tests, use the following command

mvn test



   
