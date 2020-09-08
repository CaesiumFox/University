# Simple but working

MAINCLASS := ru.caesiumfox.lab01.Main
CLASSES := ru.caesiumfox.lab01.Main \
           ru.caesiumfox.lab01.Generator

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
