CC = gcc
INCLUDES = -msse4a -msse4.1 -msse4.2
CFLAGS = -g -Wall $(INCLUDES)
LDFLAGS = -g
LDLIBS = -lm


probe: main.o probe.o splash.o hash.o

main.o: main.c splash.h

hash.o: hash.c splash.h

probe.o: probe.c probe.h

splash.o: splash.c


default: probe

clean:
	rm -f *.o a.out core probe

test:
	./probe dumpfile

all: clean probe test

.PHONY: all clean test
