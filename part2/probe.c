#include <stdio.h>
#include <stdint.h>
#include <math.h>
#include <xmmintrin.h> //SSE
#include <emmintrin.h> //SSE2
#include <pmmintrin.h> //SSE3
#include <tmmintrin.h> //SSSE3
#include <smmintrin.h> //SSE4.1
#include <nmmintrin.h> //SSE4.2
#include <ammintrin.h> //SSE4A

#include "probe.h"
#include "splash.h"

int probe(int key, struct SplashTable st);

void processProbes(struct SplashTable st) {
	/*
	 *  Read probes from stdin
	 *  Output key:value pairs to stdout
	 *
	 */
	while(!feof(stdin))
	{
		int key;
		fscanf(stdin, "%d", &key);
		int result = probe(key, st);
		if(result!=0)
		{
			printf("%d %d\n", key, probe(key, st));
		}
	}

}

void print128_num(__m128i var)
{
    uint32_t *val;
    val = (uint32_t*) &var;
    printf("Numerical: %i %i %i %i \n", 
           val[0], val[1], val[2], val[3]);
}

int probe(int key, struct SplashTable st)
{
	/*
	 *  1. get the bucket indexes
	 * 	  - use "getBuckets()" from hash.h
	 *
	 *  2. load the corresponding buckets into the registers
	 *
	 *  3. SIMD cmp-eq with the keys
	 *
	 *  4. SIMD SIMD and with the payloads
	 *    - must load the payloads into registers first?
	 *
	 * 	5. SIMD OR all results for the bucket together
	 *
	 *  6. SIMD OR-ACROSS, OR results from all buckets together
	 *
	 *  7. Return the result, either value or 0
	 */

    
	//simd copy - converts 32-bit to 128-bit with key 4 times
	__m128i new_key = _mm_set1_epi32(key);

	//convert each hash multiplier into 128-bit
	__m128i hmult1 = _mm_cvtsi32_si128(st.hashMultipliers[0]);
	__m128i hmult2 = _mm_cvtsi32_si128(st.hashMultipliers[1]);

	//simd mult - getting hash functions
	__m128i hash1 = _mm_mul_epi32(new_key, hmult1);
	__m128i hash2 = _mm_mul_epi32(new_key, hmult2);

	//simd shift/mult - getting table slots
	__m128i tbl_slot1 = _mm_srli_epi32(hash1, 32-st.size);
	__m128i tbl_slot2 = _mm_srli_epi32(hash2, 32-st.size);

	//get location of buckets 
	int loc1 = _mm_cvtsi128_si32(tbl_slot1)/4;
	int loc2 = _mm_cvtsi128_si32(tbl_slot2)/4;
	//getting buckets & storing them into 128-bit vectors
	struct Bucket *bkt1 = st.buckets+loc1;
	__m128i bktvec1 = _mm_set_epi32(bkt1->keyValue[3], bkt1->keyValue[2], bkt1->keyValue[1], bkt1->keyValue[0]);
	struct Bucket *bkt2 = st.buckets+loc2;
	__m128i bktvec2 = _mm_set_epi32(bkt2->keyValue[3], bkt2->keyValue[2], bkt2->keyValue[1], bkt2->keyValue[0]);

	//simd cmp-eq between buckets and key
	__m128i ce1 = _mm_cmpeq_epi32 (bktvec1, new_key);
	__m128i ce2 = _mm_cmpeq_epi32 (bktvec2, new_key);
	
	//getting payloads
	__m128i payload1 = _mm_set_epi32(bkt1->keyValue[7], bkt1->keyValue[6], bkt1->keyValue[5], bkt1->keyValue[4]);
	__m128i payload2 = _mm_set_epi32(bkt2->keyValue[7], bkt2->keyValue[6], bkt2->keyValue[5], bkt2->keyValue[4]);

	//simd and between compare-equal vector and payloads
	__m128i and1 = _mm_and_si128 (ce1, payload1);
	__m128i and2 = _mm_and_si128 (ce2, payload2);

	//simd or
	__m128i or = _mm_or_si128(and1, and2);

	//simd or-across
	//implemented by creating 128-bit vectors of each 32-bit in or and OR'ing them all together
	__m128i a = _mm_set1_epi32(_mm_extract_epi32(or, 0));
	__m128i b = _mm_set1_epi32(_mm_extract_epi32(or, 1));
	__m128i c = _mm_set1_epi32(_mm_extract_epi32(or, 2));
	__m128i d = _mm_set1_epi32(_mm_extract_epi32(or, 3));

	__m128i finalpayload =  _mm_or_si128(_mm_or_si128(a, b), _mm_or_si128(c, d));
	int result = _mm_cvtsi128_si32(finalpayload);

	return result;
}