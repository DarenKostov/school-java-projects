#!/bin/bash
for i in {1..1000}; do printf "$(shuf -i 0-9999999 -n 1), " >> $1; done
