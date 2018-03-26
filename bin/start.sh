#!/bin/bash
#mvn spring-boot:run -Drun.arguments="--spring.profiles.active=dev"
cd ../ && mvn clean package
java -jar build/uhomed-router-web-1.0.jar --spring.profiles.active=dev