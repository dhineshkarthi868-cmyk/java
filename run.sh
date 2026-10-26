#!/usr/bin/env bash
set -e
mkdir -p out
javac -d out $(find src -name '*.java') tests/ParkingTest.java
java -cp out ParkingTest
java -cp out parking.app.Main
