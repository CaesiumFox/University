package io.github.caesiumfox.lab01;

public class Main {
	private static int[] d;
	private static double[] x;
	private static double[][] g;
	private static Generator gen;

	private static boolean seedSet = false;
	private static long seed = 0;

	public static void main(String[] args) {

		for(int i = 0; i < args.length; i++) {
			if(args[i].matches("-?\\d")) {
				seedSet = true;
				seed = Integer.parseInt(args[0]);
			}
			else {
				System.out.printf("Invalid option: %s", args[i]);
				System.exit(1);
			}
		}

		if(seedSet)
			gen = new Generator(seed);
		else
			gen = new Generator();

		d = gen.GenerateArithmetic(3, 2, 12);
		x = gen.GenerateRandom(-6.0, 8.0, 15);
		g = gen.GenerateGArray(d, x);

		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < x.length; j++) {
				System.out.printf("g[%d][%d] = %.4f\n", i, j, g[i][j]);
			}
		}
	}
}