package gus.game5.core.util;

import java.awt.Color;
import java.util.List;

import gus.game5.core.game.Game;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;

public class UtilRandom {
	
	/*
	 * RANDOM BOOLEAN
	 */

	public static boolean randomBoolean() {
		return Math.random()<0.5;
	}
	
	/*
	 * RANDOM INT
	 */

	public static int randomInt(int nb) {
		return (int) (Math.random()*nb);
	}
	public static int randomInt(int min, int max) {
		return randomInt(max-min) + min;
	}
	
	/*
	 * RANDOM DOUBLE
	 */
	
	public static double randomDouble(double limit) {
		return Math.random()*limit;
	}
	public static double randomDouble(double min, double max) {
		return randomDouble(max-min) + min;
	}
	public static double randomDouble(int limit) {
		return Math.random()*limit;
	}
	public static double randomDouble(int min, int max) {
		return randomDouble(max-min) + min;
	}
	
	/*
	 * CHANCE
	 */
	
	public static boolean chance(int nb) {
		if(nb==0) return false;
		return randomInt(nb)==0;
	}
	
	public static boolean chance(double nb) {
		if(nb<0) return false;
		return randomDouble(nb+1)<1;
	}
	
	/*
	 * CHANCES
	 */
	
	public static int chances(int... nn) {
		int sum = UtilInteger.sum(nn);
		int val = randomInt(sum);
		
		for(int i=0;i<nn.length;i++) {
			if(val<nn[i]) return i;
			val -= nn[i];
		}
		return -1;
	}
	
	/*
	 * RANDOM COLOR
	 */
	
	public static Color randomColor() {
		return new Color(randomInt(256), randomInt(256), randomInt(256));
	}
	
	/*
	 * RANDOM POINT 1
	 */
	
	public static Point1 randomPoint1(double limitX, double limitY) {
		return new Point1(randomDouble(limitX), randomDouble(limitY));
	}
	
	public static Point1 randomPoint1(Game game) {
		return randomPoint1(game.gameWidth(), game.gameHeight());
	}
	
	/*
	 * RANDOM POINT 2
	 */
	
	public static Point2 randomPoint2(double limitX, double limitY) {
		return new Point2(randomDouble(limitX), randomDouble(limitY));
	}
	
	public static Point2 randomPoint2(Game game) {
		return randomPoint2(game.gameWidth(), game.gameHeight());
	}
	
	/*
	 * RANDOM ELEMENT
	 */
	
	public static <U> U randomElement(List<U> list) {
		if(list==null || list.isEmpty()) return null;
		int index = randomInt(list.size());
		return list.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public static <U> U randomElement(U... uu) {
		if(uu.length==0) return null;
		int index = randomInt(uu.length);
		return uu[index];
	}
	
	public static <U> U pickRandomElement(List<U> list) {
		if(list==null || list.isEmpty()) return null;
		int index = randomInt(list.size());
		return list.remove(index);
	}
}
