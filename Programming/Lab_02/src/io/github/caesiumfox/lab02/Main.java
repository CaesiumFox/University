package io.github.caesiumfox.lab02;

import ru.ifmo.se.pokemon.*;
import io.github.caesiumfox.lab02.pokemons.Zorua;
//import io.github.caesiumfox.lab02.moves.*;

public class Main {
	public static void main(String[] args) {
		Battle b = new Battle();
		Pokemon a1 = new Zorua("Ally 1", 1);
		Pokemon f1 = new Zorua("Foe 1", 1);
		
		b.addAlly(a1);
		b.addFoe(f1);
		b.go();
	}
}
