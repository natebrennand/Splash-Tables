
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "splash.h"

void printSplashTable(struct SplashTable);

// given a dumpfile, builds a Splash Table
struct SplashTable buildSplashtable(char* file)
{
	FILE *dumpfile = fopen(file, "r");
	if (dumpfile == NULL) {
		printf("Could not open file\n");
		exit(1);
	}

	int bucketSize, S, numHashes, N;
	fscanf(dumpfile, "%d %d %d %d", &bucketSize, &S, &numHashes, &N);

	// fail if bucket size and the number of hashes is not proper
	if (bucketSize != BUCKET_SIZE || numHashes != NUM_HASHES) {
		printf("Dump file must use a bucket size of %d and %d hashes\n.", BUCKET_SIZE, NUM_HASHES);
		exit(1);
	}

	struct SplashTable st;
	st.occupancy = N;
	st.size = S;
	st.totalSize = (int)pow(2, S);
	st.buckets = (Bucket *)malloc(sizeof(Bucket) * st.totalSize / BUCKET_SIZE);

	// scan in hash multipliers
	fscanf(dumpfile, "%d %d", &(st.hashMultipliers[0]), &(st.hashMultipliers[1]));

	// scan in key:value pairs
	for(int bucketIndex=0; bucketIndex<st.totalSize / BUCKET_SIZE; bucketIndex++) {
		Bucket b; // declare new bucket
		for (int slotIndex=0; slotIndex<BUCKET_SIZE; slotIndex++) {
			if (2 != fscanf(dumpfile, "\n%d %d",
					&(b.keyValue[slotIndex]),
					&(b.keyValue[slotIndex + BUCKET_SIZE]))) {
				fprintf(stderr, "broken!\n");
			}
		}
		st.buckets[bucketIndex] = b; // assign bucket to malloced space
	}
	fclose(dumpfile);

	printSplashTable(st);
	return st;
}

// deallocates memory malloced for the splashtable
void freeSplashTable(struct SplashTable st)
{
	free(st.buckets);
}

// prints out the info in the splashtable
void printSplashTable(struct SplashTable st)
{
	// print config
	printf("B: %d, S: %d, h: %d, N: %d\n", BUCKET_SIZE, st.size, NUM_HASHES, st.occupancy);

	// print hash multipliers
	for(int i=0; i<NUM_HASHES; i++) {
		printf("hash: %d\n", st.hashMultipliers[i]);
	}

	// print key:value pairs
	printf("KEY:VALUE\n");
	for(int i=0; i<pow(2, st.size); i++) {
		printf("%d:%d\n",
			st.buckets[i / BUCKET_SIZE].keyValue[i % BUCKET_SIZE],
			st.buckets[i / BUCKET_SIZE].keyValue[(i % BUCKET_SIZE) + BUCKET_SIZE]);
	}
}

