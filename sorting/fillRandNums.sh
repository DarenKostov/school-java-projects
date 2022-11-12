#!/bin/bash
#0-9999999
for i in {1..25000}; do printf "$(shuf -i 0-9999 -n 1), " >> $1; done