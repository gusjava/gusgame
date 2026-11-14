package gus.game5.main.game.p1.c.tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {

	private List<Tetromino> pieces = new ArrayList<>();

	public Tetromino next() {
		if(pieces.isEmpty()) fill();
		return pieces.remove(pieces.size()-1);
	}

	private void fill() {
		for(Tetromino type : Tetromino.values()) pieces.add(type);
		Collections.shuffle(pieces);
	}
}
