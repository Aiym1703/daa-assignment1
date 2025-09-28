#!/usr/bin/env bash
set -euo pipefail
mvn -q -DskipTests package
JAR=$(ls target/*-SNAPSHOT.jar | head -n1)
mkdir -p results
java -jar "$JAR" --algo "$1" --n "${2:-50000}" --trials "${3:-3}" --out "results/${1}.csv"
echo "CSV written to results/${1}.csv"
