package com.conduent.Ibtsprocessing.utility;

import java.util.Random;

public class Util 
{
	public static int getRandomNumber(int n)
	{
		int m = (int) Math.pow(10, n - 1);
	    return m+new Random().nextInt(9 * m);	    
	}
	
}
