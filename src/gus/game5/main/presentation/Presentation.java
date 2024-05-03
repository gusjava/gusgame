package gus.game5.main.presentation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gus.game5.core.draw.Draw;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.main.anim.fishtank.AnimFishTank2;
import gus.game5.main.anim.life1.LifeBacteria1;
import gus.game5.main.anim1.AnimBallBattle;
import gus.game5.main.anim1.AnimClock;
import gus.game5.main.anim1.AnimGameOfLife;
import gus.game5.main.anim1.AnimLandscape;
import gus.game5.main.anim1.AnimLangtonAnt;
import gus.game5.main.anim1.AnimMaze;
import gus.game5.main.game.bomb.GameBomb;
import gus.game5.main.game.minesweeper.GameMinesweeper;
import gus.game5.main.game.missile.GameMissile;
import gus.game5.main.game.reversi.GameReversi;
import gus.game5.main.game.snake.GameSnake;
import gus.game5.main.game.space.GameSpace;
import gus.game5.main.game.warrior.GameWarrior;

public class Presentation extends Game {

	public static void main(String[] args) {
		Presentation main = new Presentation();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("GusGame Presentation");
		s.setSleep(5);
		s.setBackground(Color.WHITE);
	}
	
	
	protected Container buildContentPane() {
		JPanel p = new JPanel(new GridLayout(0,1,5,5));
		p.add(new GameArea(new GameReversi()));
		p.add(new GameArea(new GameSnake()));
		p.add(new GameArea(new GameMissile()));
		p.add(new GameArea(new GameBomb()));
		p.add(new GameArea(new GameMinesweeper()));
		p.add(new GameArea(new AnimClock()));
		p.add(new GameArea(new AnimBallBattle()));
		p.add(new GameArea(new AnimGameOfLife()));
		p.add(new GameArea(new AnimLandscape()));
		p.add(new GameArea(new AnimLangtonAnt()));
		p.add(new GameArea(new AnimMaze()));
		p.add(new GameArea(new LifeBacteria1()));
		p.add(new GameArea(new AnimFishTank2()));
		p.add(new GameArea(new GameWarrior()));
		p.add(new GameArea(new GameSpace()));
		
		JScrollPane scroll = new JScrollPane(p);
		scroll.setPreferredSize(new Dimension(700,500));
		return scroll;
	}
	
	
	protected void initialize() {
	}

	protected void turn() {}
	protected Draw buildDraw() {return null;}
}
