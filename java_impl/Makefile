
JC = javac
RM = rm
.SUFFIXES: .java .class

.java.class:
	$(JC) $*.java

CLASSES = \
		Splash.java \
		Table.java \
		Bucket.java \
		Hash.java

default: build

build:
	$(JC) $(CLASSES)

run:
	java Splash $(filter-out $@,$(MAKECMDGOALS))

clean:
	$(RM) *.class

all: clean build run

.PHONY: clean build run all
