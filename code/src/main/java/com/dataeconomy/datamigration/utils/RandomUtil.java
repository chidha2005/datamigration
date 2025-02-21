package com.dataeconomy.datamigration.utils;

import java.security.SecureRandom;

public class RandomUtil {


	public static String getRandomPassword()
	{
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(10);
		for( int i = 0; i < 10; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();

	}
}
