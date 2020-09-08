package com.github.caesiumfox.lab01;

import java.lang.Math;
import java.util.Random;
import java.util.Date;

public class Generator {
	private long seed;
	private Random rand;

	public Generator() {
		this.seed = new Date().getTime();
		rand = new Random(this.seed);
	}

	public Generator(long seed) {
		this.seed = seed;
		rand = new Random(this.seed);
	}

	public long GetSeed() {
		return seed;
	}

	public int[] GenerateArithmetic(int from, int step, int count) {
		int[] result = new int[count];
		result[0] = from;
		for(int i = 1; i < count; i++) {
			result[i] = result[i - 1] + step;
		}
		return result;
	}

	public double[] GenerateRandom(double minval, double maxval, int count) {
		double[] result = new double[count];
		for(int i = 0; i < count; i++) {
			result[i] = rand.nextDouble() * (maxval - minval) + minval;
		}
		return result;
	}

	public double[][] GenerateGArray(int[] d, double[] x) {
		double[][] g = new double[d.length][x.length];
		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < x.length; j++) {
				switch(d[i]) {
				case 19:
					g[i][j] = Math.pow(
						Math.atan(0.2 * (x[j] + 1.0) / 14.0),
						0.25 / (Math.asin(Math.exp(-Math.abs(x[j]))) - 1.0)
					);
					break;
				case 5:  // Is there anything in Java similar
				case 9:  // to C++'s [[fallthrough]] to show
				case 11: // that omitting 'break' statements
				case 13: // in swith-case structures is not
				case 17: // a mistake?
				case 23:
					g[i][j] = Math.cos(Math.cos(Math.cbrt(x[j])));
					break;
				default:
					g[i][j] = Math.pow(
						0.25 - Math.pow(Math.cbrt(Math.pow(x[j], x[j] - 3.0 / 4.0)), 3.0 + Math.cos(Math.cbrt(x[j]))),
						Math.asin(Math.exp(Math.cbrt(-Math.pow(4.0 / Math.abs(x[j]), x[j]))))
					);
					break;
				}
			}
		}
		return g;
	}
}