#!/bin/sh

table_size=12

bucket_size=$1
hashes=$2
reinsertions=$3

inputfile="dump$table_size.txt"
dumpfile=dumpfile
filename="$bucket_size:$hashes:$reinsertions.test"

echo "B:$bucket_size R:$reinsertions S:$table_size h:$hashes" > $filename
for i in {1..20}
do
    python ../testing/random_key_value.py $table_size > $inputfile 
    java Splash $bucket_size $reinsertions $table_size $hashes $inputfile $dumpfile < probe.txt >> $filename
done

