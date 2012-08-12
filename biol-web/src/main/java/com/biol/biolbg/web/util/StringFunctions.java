package com.biol.biolbg.web.util;

public class StringFunctions {
	
	public static String concat(String a,String b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		return a.concat(b);
    }
	

}
