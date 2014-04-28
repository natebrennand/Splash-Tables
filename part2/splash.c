#include <stdio.h>
#include <math.h>
#include "splash.h"
#include "simd.h"

int splashtable(char* file)
{
	FILE *dumpfile = fopen(file, "r");
	if (dumpfile == NULL) {
		printf( "Could not open file\n" );
		return(-1);
	} else {
		int B, S, h, N;
		fscanf(dumpfile, "%d %d %d %d", &B, &S, &h, &N);

		int hashnum[h];
		int size = pow(2, S-1);
		int keys[size];
		int payloads[size];
		int i;
		for(i=0;i<h;i++) {
			fscanf(dumpfile, "%d", &(hashnum[i]));
		}
		for(i=0;i<size;i++) {
			fscanf(dumpfile, "%d %d", &(keys[i]), &(payloads[i]));
		}

		printf("B: %d, S: %d, h: %d, N: %d\n", B, S, h, N);
		for(i=0;i<h;i++) {
			printf("hash: %d\n", hashnum[i]);
		}
		for(i=0;i<size;i++) {
			printf("key: %d, payload: %d\n", keys[i], payloads[i]);
		}

		return 0;
		// return probe(hashnum, keys, payloads);
	}
	fclose(dumpfile);
}
