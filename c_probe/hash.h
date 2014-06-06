
#include "splash.h"

#ifndef _HASH_H_
#define _HASH_H_

typedef struct Index
{
	int buckets[NUM_HASHES];
} Index;

// returns bucket indexes
Index getBuckets(int key, int size);

#endif

