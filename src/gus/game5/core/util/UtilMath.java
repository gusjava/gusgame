package gus.game5.core.util;

public class UtilMath {

	public static boolean isDivisibleBy25(int n) {
		//number ends by 00, 25, 50 or 75
		int unit2 = n%100;
		return unit2==0 || unit2==25 || unit2==50 || unit2==75;
	}

	public static boolean isDivisibleBy10(int n) {
		//number ends by 0
		int unit = n%10;
		return unit==0;
	}

	public static boolean isDivisibleBy5(int n) {
		//number ends by 0 or 5
		int unit = n%10;
		return unit==0 || unit==5;
	}
}
