#!/bin/bash

cd ~/waffle-rookies-19.5-springboot

# update deploy branch
git checkout deploy
git pull origin deploy

# move seminar directory
cp -r seminar1/assignment1/seminar/ ~

# gradle build
cd ~/seminar
./gradlew bootJar

# run jar
java -jar -Dspring.profiles.active=prod build/libs/seminar-0.0.1-SNAPSHOT.jar
