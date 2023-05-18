package com.conduent.tpms.qatp.classification.utility;

import java.util.Random;

/**
 * Utility class to generate random number
 * 
 * @author deepeshb
 *
 */
public class RandomNumberGenerator {

	private RandomNumberGenerator() {

	}

	public static int getRandomNumber(int n) {
		int m = (int) Math.pow(10, n - 1);
		return m + new Random().nextInt(9 * m);
	}
}
