#!/bin/sh

for b in 1 2 4 8
do
    for h in 2 4 8
    do
        for r in 1 5 20 100
        do
            ./test.sh $b $h $i
        done
    done
done

exit 0
