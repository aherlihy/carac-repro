#!/usr/bin/env bash

if [[ $# -ne 1 ]] ; then
    echo 'USAGE: directory'
    exit 0
fi
FACTDIR="src/test/scala/datalog/benchmarks/run-cli/$1/facts"
PROGRAM="src/test/scala/datalog/benchmarks/run-cli/$1/$1.dl"
SOUFFLE="/scratch/herlihy/souffle/build/src"

if [[ ! -d  $FACTDIR ]]; then
    echo "Fact directory $FACTDIR not found!"
    exit 0
fi
if [[ ! -f  $PROGRAM ]]; then
    echo "Program $PROGRAM not found!"
    exit 0
fi
if [[ ! -f  $SOUFFLE/souffle ]]; then
    echo "Souffle at $SOUFFLE not found!"
    exit 0
fi

$SOUFFLE/souffle --profile=$1-preprofile --emit-statistics -F $FACTDIR $PROGRAM --jobs=1  --wno=var-appears-once --wno=no-rules-nor-facts
