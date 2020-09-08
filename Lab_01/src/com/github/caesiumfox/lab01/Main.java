package com.github.caesiumfox.lab01;

public class Main {
	private static int[] d;
	private static double[] x;
	private static double[][] g;
	private static Generator gen;

	public static void main(String[] args) {
		if(args.length > 0 && args[0].matches("-?\\d")) { // operator && is lazy
			gen = new Generator(Integer.parseInt(args[0]));
		}
		else {
			gen = new Generator();
		}

		d = gen.GenerateArithmetic(3, 2, 12);
		x = gen.GenerateRandom(-6.0, 8.0, 15);
		g = gen.GenerateGArray(d, x);

		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < x.length; j++) {
				System.out.format("g[%d][%d] = %.4f\n", i, j, g[i][j]);
			}
		}
	}
}