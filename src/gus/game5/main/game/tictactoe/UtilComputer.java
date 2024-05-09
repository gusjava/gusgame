package gus.game5.main.game.tictactoe;

import java.util.List;

import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilRandom;

public class UtilComputer {

	public static int[] randomPlay(int[][] data) {
		List<int[]> list = UtilArray.findAll(data, 0);
		return UtilRandom.randomElement(list);
	}
}
