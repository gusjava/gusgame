package gus.game5.main.game.p1.c.pacman;

public enum Fruit {
	CHERRY(100), STRAWBERRY(300), ORANGE(500), APPLE(700), 
	MELON(1000), GALBOSS(2000), BELL(3000), KEY(5000);
	
	private final int score;
	
	private Fruit(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public static Fruit forLevel(int level) {
		switch(level) {
			case 1: return CHERRY;
			case 2: return STRAWBERRY;
			case 3: case 4: return ORANGE;
			case 5: case 6: return APPLE;
			case 7: case 8: return MELON;
			case 9: case 10: return GALBOSS;
			case 11: case 12: return BELL;
			default: return KEY;
		}
	}
}
