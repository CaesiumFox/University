# Simple but working

MAINCLASS := io.github.caesiumfox.lab02.Main
CLASSES := io.github.caesiumfox.lab02.Main \
           io.github.caesiumfox.lab02.pokemons.Zorua
LIBS := lib/Pokemon.jar

SRCS := $(addprefix ./src/,$(addsuffix .java,$(subst .,/,$(CLASSES))))
JARF := lab02

all:
	mkdir -p bin
	javac -sourcepath src/ -d bin/ -classpath $(LIBS) $(SRCS)
	# jar cfe $(JARF).jar $(MAINCLASS) -C bin/ .

debug:
	mkdir -p bin_debug
	javac -g -d -sourcepath src/ -d bin_debug/ -classpath $(LIBS) $(SRCS)

run:
	java -classpath bin:$(LIBS) $(MAINCLASS)
	# java -jar $(JARF).jar

clean:
	rm -rf ./bin/
	rm -rf ./bin_debug/
	rm -f $(JARF).jar
