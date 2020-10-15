MAINCLASS := io.github.caesiumfox.lab02.Main
CLASSES := io.github.caesiumfox.lab02.Main \
           io.github.caesiumfox.lab02.pokemons.Basculin \
           io.github.caesiumfox.lab02.pokemons.Zorua \
           io.github.caesiumfox.lab02.pokemons.Zoroark \
           io.github.caesiumfox.lab02.pokemons.Deino \
           io.github.caesiumfox.lab02.pokemons.Zweilous \
           io.github.caesiumfox.lab02.pokemons.Hydreigon \
           io.github.caesiumfox.lab02.moves.AquaJet \
           io.github.caesiumfox.lab02.moves.Swift \
           io.github.caesiumfox.lab02.moves.Waterfall \
           io.github.caesiumfox.lab02.moves.DoubleTeam \
           io.github.caesiumfox.lab02.moves.DarkPulse \
           io.github.caesiumfox.lab02.moves.Snarl \
           io.github.caesiumfox.lab02.moves.Facade \
           io.github.caesiumfox.lab02.moves.NightSlash \
           io.github.caesiumfox.lab02.moves.FocusEnergy \
           io.github.caesiumfox.lab02.moves.DragonBreath \
           io.github.caesiumfox.lab02.moves.DoubleHit \
           io.github.caesiumfox.lab02.moves.BodySlam
LIBS := lib/Pokemon.jar

SRCS := $(addprefix ./src/,$(addsuffix .java,$(subst .,/,$(CLASSES))))
JARF := lab02

all:
	mkdir -p bin
	javac -sourcepath src/ -d bin/ -classpath $(LIBS) $(SRCS)
	echo "class-path: $(LIBS)" > manifest.mf
	jar cfem $(JARF).jar $(MAINCLASS) manifest.mf -C bin/ .
	rm manifest.mf

debug:
	mkdir -p bin_debug
	javac -g -d -sourcepath src/ -d bin_debug/ -classpath $(LIBS) $(SRCS)

run:
	java -jar $(JARF).jar

clean:
	rm -rf ./bin/
	rm -rf ./bin_debug/
	rm -f $(JARF).jar
