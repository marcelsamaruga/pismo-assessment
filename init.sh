#!/bin/bash

#########################################################################################

function run_app
{
	if [ ! -d "pismo-assessment" ]; then
	  	git clone https://github.com/marcelsamaruga/pismo-assessment.git
	fi

	cd "pismo-assessment"

	chmod 777 mvnw

	git pull origin main

	printf "\nCompiling project"
	./mvnw clean package -DskipTests=true
	
	java -jar target/assessment-0.0.1-SNAPSHOT.jar
}

function run_integration_tests
{
	if [ ! -d "pismo-assessment" ]; then
	  	git clone https://github.com/marcelsamaruga/pismo-assessment.git
	fi

	cd "pismo-assessment"
	
	chmod 777 mvnw

	git pull origin main

	printf "\nRunning integration tests\n\n"
	./mvnw clean test
	printf "\n\nIntegration tests has been completed"
}

#########################################################################################


printf "\n#### Pismo Assessment ####"

printf "  \n1) Run App"
printf "  \n2) Run Integration Tests"

echo

read choice

case $choice in
  1) run_app ;;
  2) run_integration_tests ;;
  *) printf "invalid option" ;;
esac
