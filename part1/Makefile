
JC = javac
RM = rm
.SUFFIXES: .java .class

.java.class:
	$(JC) $*.java

CLASSES = \
		Table.java \
		Splash.java

default:
	$(JC) $(CLASSES)

clean:
	$(RM) *.class

test:
	java Splash 4 2 9 8 test.txt dump.txt < probe.txt

.PHONY: clean
