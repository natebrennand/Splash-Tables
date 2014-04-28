CC = gcc
INCLUDES = -msse4a -msse4.1 -msse4.2
CFLAGS = -g -Wall $(INCLUDES)
LDFLAGS = -g
LDLIBS = -lm

default: probe

probe: probe.o splash.o simd.o

probe.o: probe.c splash.h

splash.o: splash.c simd.h

simd.o: simd.c simd.h

clean:
	rm -f *.o a.out core probe

all: clean probe

.PHONY: all clean
