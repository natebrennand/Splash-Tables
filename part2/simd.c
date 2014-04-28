#include <stdio.h>
#include <xmmintrin.h> //SSE
#include <emmintrin.h> //SSE2
#include <pmmintrin.h> //SSE3
#include <tmmintrin.h> //SSSE3
#include <smmintrin.h> //SSE4.1
#include <nmmintrin.h> //SSE4.2
#include <ammintrin.h> //SSE4A
#include "simd.h"

int probe(int *hashMult, int *keys, int *payloads)
{
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

	return 1;
}