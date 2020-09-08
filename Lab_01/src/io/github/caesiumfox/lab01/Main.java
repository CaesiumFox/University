package io.github.caesiumfox.lab01;

import java.util.Locale;

public class Main {
	private static short[] d;
	private static double[] x;
	private static double[][] g;

	public static void main(String[] args) {
		d = Generator.GenerateArithmetic((short)4, (short)2, 8);
		x = Generator.GenerateRandom(-5.0, 15.0, 17);
		g = Generator.GenerateGArray(d, x);

		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < x.length; j++) {
				System.out.printf(Locale.US, "g[%d][%d] = %.2f\n", i, j, g[i][j]);
			}
		}
	}
}