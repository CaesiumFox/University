# Simple but working

MAINCLASS := com.github.caesiumfox.lab01.Main
CLASSES := com.github.caesiumfox.lab01.Main \
           com.github.caesiumfox.lab01.Generator

SRCS := $(addprefix ./src/,$(addsuffix .java,$(subst .,/,$(CLASSES))))
JARF := lab01.jar

all:
	javac -d bin/ $(SRCS)
	jar cfe $(JARF) $(MAINCLASS) -C bin/ .

run:
	java -jar $(JARF)

clean:
	rm -r ./bin/*
	rm $(JARF)
