#!/bin/bash
DAY=$1
curl https://adventofcode.com/2021/day/"$DAY"/input --cookie session="$AOC_SESSION" -o day"$DAY".txt
