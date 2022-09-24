# demo

### PostgresInstallation ###

https://www.postgresqltutorial.com/postgresql-getting-started/install-postgresql-macos/

Java Version : 11

Install PgAdmin :

https://www.pgadmin.org/download/


### Run the Tests ###

mvn clean install

### APIs to be used as ###

curl --location --request POST 'localhost:8080/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName":"Mandeep",
    "lastName":"Kaur",
    "userId": "mandeep.kaur@gmail.com",
    "currency": "INR",
    "mobileNo":"9611343425"
}'


curl --location --request POST 'localhost:8080/banking/deposit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount": "51.5",
    "userId": "mandeep.kaur@gmail.com"
}'


curl --location --request GET 'localhost:8080/banking/balance/mandeep.kaur@gmail.com'


curl --location --request POST 'localhost:8080/banking/withdraw' \
--header 'Content-Type: application/json' \
--data-raw '{
    "withdrawAmount": "0.5",
    "userId": "mandeep.kaur@gmail.com"
}'
