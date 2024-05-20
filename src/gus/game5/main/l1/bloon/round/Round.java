package gus.game5.main.l1.bloon.round;

import gus.game5.core.util.UtilRandom;
import gus.game5.main.l1.bloon.GameBloon;
import gus.game5.main.l1.bloon.type.BlueBloon2;
import gus.game5.main.l1.bloon.type.GreenBloon3;
import gus.game5.main.l1.bloon.type.PinkBloon5;
import gus.game5.main.l1.bloon.type.RedBloon1;
import gus.game5.main.l1.bloon.type.YellowBloon4;

public class Round {
	
	private GameBloon game;
	private long duration;
	
	private double[] chances;
	
	public Round(GameBloon game, long duration, double[] chances) {
		this.game = game;
		this.duration = duration;
		this.chances = chances;
	}

	public void generate() {
		if (UtilRandom.chance((int) chances[0])) {
			game.getBloons().add(new RedBloon1(game));
		}
		if (UtilRandom.chance((int) chances[1])) {
			game.getBloons().add(new BlueBloon2(game));
		}
		if (UtilRandom.chance((int) chances[2])) {
			game.getBloons().add(new GreenBloon3(game));
		}
		if (UtilRandom.chance((int) chances[3])) {
			game.getBloons().add(new YellowBloon4(game));
		}
		if (UtilRandom.chance((int) chances[4])) {
			game.getBloons().add(new PinkBloon5(game));
		}
	}
	
	public long getDuration() {
		return duration;
	}
}
