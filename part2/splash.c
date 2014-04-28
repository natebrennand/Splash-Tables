
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "splash.h"
#include "simd.h"


const int BUCKET_SIZE = 4,
	NUM_HASHES = 2;

struct SplashTable
{
	int size;	// num elements = 2^size
	int hashMultipliers[2];
};


int splashtable(char* file)
{
	FILE *dumpfile = fopen(file, "r");
	if (dumpfile == NULL) {
		printf("Could not open file\n");
		return(-1);
	}

	int bucketSize, S, numHashes, N;
	fscanf(dumpfile, "%d %d %d %d", &bucketSize, &S, &numHashes, &N);

	// fail if bucket size and the number of hashes is not proper
	if (bucketSize != BUCKET_SIZE || numHashes != NUM_HASHES) {
		printf("Dump file must use a bucket size of %d and %d hashes\n.", BUCKET_SIZE, NUM_HASHES);
		exit(1);
	}

	int hashnum[2];
	int totalSize = pow(2, S-1);
	int keys[totalSize];
	int payloads[totalSize];

	fscanf(dumpfile, "%d %d", &hashnum[0], &hashnum[1]);
	for(int i=0;i<totalSize;i++) {
		fscanf(dumpfile, "%d %d", &(keys[i]), &(payloads[i]));
	}

	printf("B: %d, S: %d, h: %d, N: %d\n", bucketSize, S, numHashes, N);
	for(int i=0; i<numHashes; i++) {
		printf("hash: %d\n", hashnum[i]);
	}
	for(int i=0; i<totalSize; i++) {
		printf("key: %d, payload: %d\n", keys[i], payloads[i]);
	}

	return 0;
	// return probe(hashnum, keys, payloads);

	fclose(dumpfile);
}
