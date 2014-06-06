
#ifndef _SPLASH_H_
#define _SPLASH_H_


#define BUCKET_SIZE 4
#define NUM_HASHES 2

typedef struct Bucket
{
	int keyValue[2 * BUCKET_SIZE];
} Bucket;

struct SplashTable
{
	int size;	// num elements = 2^size
	int totalSize;
	int occupancy;
	int hashMultipliers[2];
	Bucket *buckets;
};

struct SplashTable buildSplashtable(char *file);

#endif

