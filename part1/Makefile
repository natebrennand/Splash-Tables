
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

.PHONY: clean
