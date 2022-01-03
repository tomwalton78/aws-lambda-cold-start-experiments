#!/bin/bash

repeats=5
while getopts r: flag
do
    case "${flag}" in
        r) repeats=${OPTARG};;
    esac
done

make deploy  # need to deploy each time to ensure each invocation is a cold start

# On my machine/network it takes approx 2 mins per repeat
for i in $(seq 1 $repeats);
do
  echo "Starting test run ${i}"
  # Run in background so all invocations occur at once, and all will be a cold start
  aws lambda invoke --function-name ColdStartJavaFunction /dev/null &
done