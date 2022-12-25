# Getting Started
* Author: Marcel Samaruga da Costa
* Github: http://github.com/marcelsamaruga
* Last Update: Dec 24th 2022

## PISMO Assessment
The api is an assessment to join Pismo.
It should expose APIs to create an account, get account details and create a new transaction for a specific account.

## Endpoints
[Docs](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)
### [POST] Create Account 
* [/accounts](http://localhost:8080/accounts)
#### Request (example):
````
{
    "document_number": "12345678900"
}
````

### [GET] Get Account Details
* [/accounts](http://localhost:8080/accounts/:accountId)
#### Response (example):
````
{
    "account_id": 1,
    "document_number": "12345678900"
}
````


### [POST] Create Account Transaction
* [/transactions](http://localhost:8080/transactions)
#### Request (example):
````
{
    "account_id": 1,
    "operation_type": 4,
    "amount": 123.45
}
````

## Running Application
Download [init.sh](https://github.com/marcelsamaruga/pismo-assessment/blob/main/init.sh)
Run the bash file init.sh.
Remember to give the right permission. [Permission](https://www.andrewcbancroft.com/blog/musings/make-bash-script-executable/)

` chmod u+x init.sh `

To run on Windows, you need to use WSL (Windows Subsystem for Linux).

Choose one of the following options:
### Pismo Assessment ####
1) Run App
2) Run Integration Tests

#### Running the app
* The command will clone the project from the github repository (if wasn't before)
* Run maven command to package the application skipping the tests.
* Run the app
* You may reach the link to open the API documentation [docs](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

Example:

```
➜  ~ curl http://localhost:8080/accounts/1
{
    "account_id": 1,
    "document_number": "12345678900"
}
```

#### Running integration tests
* The command will clone the project from the github repository (if wasn't before)
* Run maven command to test the application
* It should display "Integration tests has been completed."