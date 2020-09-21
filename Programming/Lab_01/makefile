# Simple but working

MAINCLASS := io.github.caesiumfox.lab01.Main
CLASSES := io.github.caesiumfox.lab01.Main \
           io.github.caesiumfox.lab01.Generator

SRCS := $(addprefix ./src/,$(addsuffix .java,$(subst .,/,$(CLASSES))))
JARF := lab01

all:
	mkdir -p bin
	javac -d bin/ $(SRCS)
	jar cfe $(JARF).jar $(MAINCLASS) -C bin/ .

debug:
	mkdir -p bin_debug
	javac -g -d bin_debug/ $(SRCS)

run:
	java -jar $(JARF).jar

clean:
	rm -rf ./bin/
	rm -rf ./bin_debug/
	rm -f $(JARF).jar
