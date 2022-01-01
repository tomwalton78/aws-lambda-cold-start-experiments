#!/bin/bash

repeats=5
while getopts r: flag
do
    case "${flag}" in
        r) repeats=${OPTARG};;
    esac
done

# On my machine/network it takes approx 2 mins per repeat
for i in $(seq 1 $repeats);
do
  echo "Starting test run ${i}"
  make deploy  # need to deploy each time to ensure each invocation is a cold start
  aws lambda invoke --function-name ColdStartJavaFunction /dev/null
done