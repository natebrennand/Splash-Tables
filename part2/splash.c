
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "splash.h"
#include "simd.h"

void printSplashTable(struct SplashTable);

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

	Bucket b;
	int K, V;
	for(int i=0; i<st.totalSize / BUCKET_SIZE; i++) {
		int bucketIndex = i;

		for (int j=0; j<BUCKET_SIZE; j++) {
			int keyIndex = j,
				valueIndex = j + BUCKET_SIZE;

			if (2 != fscanf(dumpfile, "\n%d %d", &K, &V)) {
				fprintf(stderr, "broken!\n");
			}

			b.keyValue[keyIndex] = K;
			b.keyValue[valueIndex] = V;
		}
		st.buckets[bucketIndex] = b;
	}

	fclose(dumpfile);
	return st;
}

void printSplashTable(struct SplashTable st) {
	// print config
	printf("B: %d, S: %d, h: %d, N: %d\n", BUCKET_SIZE, st.size, NUM_HASHES, st.occupancy);

	// print hash multipliers
	for(int i=0; i<NUM_HASHES; i++) {
		printf("hash: %d\n", st.hashMultipliers[i]);
	}

	// print key:value pairs
	for(int i=0; i<pow(2, st.size); i++) {
		printf("key: %d, payload: %d\n",
			st.buckets[i / BUCKET_SIZE].keyValue[i % BUCKET_SIZE],
			st.buckets[i / BUCKET_SIZE].keyValue[(i % BUCKET_SIZE) + BUCKET_SIZE]);
	}
}
