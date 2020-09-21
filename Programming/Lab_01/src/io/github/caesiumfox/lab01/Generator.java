package io.github.caesiumfox.lab01;

import java.lang.Math;

public class Generator {
	public static short[] generateArithmetic(short from, short step, int count) {
		short[] result = new short[count];
		result[0] = from;
		for(int i = 1; i < count; i++) {
			result[i] = (short)(result[i - 1] + step);
		}
		return result;
	}

	public static double[] generateRandom(double minval, double maxval, int count) {
		double[] result = new double[count];
		for(int i = 0; i < count; i++) {
			result[i] = Math.random() * (maxval - minval) + minval;
		}
		return result;
	}

	public static double[][] generateGArray(short[] d, double[] x) {
		double[][] g = new double[d.length][x.length];
		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < x.length; j++) {
				switch(d[i]) {
				case 10:
					g[i][j] = Math.pow(
						Math.atan(Math.pow(((x[j] + 5.) / 2.) * Math.E + 1., 2.)),
						Math.pow((Math.pow(x[j]/(x[j]-.5), 2.) - 1.) / Math.cos(x[j]), 3.)
					);
					break;
				case 4:
				case 8:
				case 14:
				case 18:
					g[i][j] = Math.log(Math.exp(Math.asin(1. / Math.exp(Math.abs(x[j])))));
					break;
				default:
					g[i][j] = 3. / (Math.atan(((x[j] + 5.) / 2.) * Math.E + 1.) + 3.);
					break;
				}
			}
		}
		return g;
	}
}