#!/bin/bash
#0-9999999
for i in {1..50}; do printf "$(shuf -i 0-50 -n 1), " >> $1; done