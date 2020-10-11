package io.github.caesiumfox.lab02.moves;

import ru.ifmo.se.pokemon.*;

public class Snarl extends SpecialMove {
	public Snarl() {
		super(Type.NORMAL, 70, 100);
	}
	@Override
	protected String describe() {
		return "лает на соперника";
	}
}