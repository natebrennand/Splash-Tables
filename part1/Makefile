
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

default:
	$(JC) $(CLASSES)

clean:
	$(RM) *.class

.PHONY: clean
