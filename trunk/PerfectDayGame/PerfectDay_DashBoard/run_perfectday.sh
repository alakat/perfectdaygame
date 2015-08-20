#!/bin/sh
echo "Load perfectday...."

USER=$1
PASS=$2
cd /Users/malopez/Developer/PerfectDay/perfectdaygame/PerfectDayGame/PerfectDay_DashBoard; 
mvn compile assembly:single
cp  target/PerfectDay_DashBoard-1.0-SNAPSHOT-jar-with-dependencies.jar /Users/malopez/Developer/tmp
M2_HOME=/Users/malopez/.m2/
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home
java -jar target/PerfectDay_DashBoard-1.0-SNAPSHOT-jar-with-dependencies.jar $USER $PASS
