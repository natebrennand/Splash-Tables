#include <stdio.h>
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


    /*
	//assume h = 4 & b = 2
	//lol change this key l8r
	int key = 59;
	//simd copy
	__m128i k;
	k = _mm_cvtsi32_si128(key);
	//simd mult
	//simd mult
	//simd cmp-eq
	__m128i ce1;
	ce1 = _mm_cmpeq_epi32 (__m128i key1, k);
	//simd cmp-eq
	__m128i ce2;
	ce2 = _mm_cmpeq_epi32 (__m128i key2, k);
	//simd and
	__m128i and1;
	and1 = _mm_and_si128 (__m128i payload1, ce1);
	//simd and
	__m128i and2;
	and2 = _mm_and_si128 (__m128i payload2, ce2);
	//simd or
	//simd or-across

    */
	return 1;
}

