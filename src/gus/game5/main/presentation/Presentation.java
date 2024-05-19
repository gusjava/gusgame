package gus.game5.main.presentation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import gus.game5.core.draw.Draw;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.util.image.IconLoader;
import gus.game5.main.anim.fishtank.AnimFishTank2;
import gus.game5.main.anim.life1.LifeBacteria1;
import gus.game5.main.anim1.AnimBallBattle;
import gus.game5.main.anim1.AnimClock;
import gus.game5.main.anim1.AnimGameOfLife;
import gus.game5.main.anim1.AnimLandscape;
import gus.game5.main.anim1.AnimLangtonAnt;
import gus.game5.main.anim1.AnimMaze;
import gus.game5.main.game.board2.chess1.GameChess1;
import gus.game5.main.game.board2.hex1.GameHex1;
import gus.game5.main.game.board2.reversi3.GameReversi3;
import gus.game5.main.game.board2.tictactoe3.GameTicTacToe3;
import gus.game5.main.game.bomb.GameBomb;
import gus.game5.main.game.minesweeper.GameMinesweeper;
import gus.game5.main.game.missile.GameMissile;
import gus.game5.main.game.snake.GameSnake;
import gus.game5.main.game.space.GameSpace;
import gus.game5.main.game.warrior.GameWarrior;
import gus.game5.main.l1.bloon.GameBloon;
import gus.game5.main.test1.MainBall1;
import gus.game5.main.test1.MainBall1m;
import gus.game5.main.test1.MainBall2;
import gus.game5.main.test1.MainBall2p;
import gus.game5.main.test1.MainBall2r;
import gus.game5.main.test1.MainMouse1;
import gus.game5.main.test1.MainSoucoupe1;
import gus.game5.main.test1.MainSoucoupe2;
import gus.game5.main.test2.MainDraws;
import gus.game5.main.test2.MainImg1;
import gus.game5.main.test2.MainImg2;

public class Presentation extends Game {
	public static final String BASE_IMG = "/gus/game5/main/presentation/";

	public static void main(String[] args) {
		Presentation main = new Presentation();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("GusGame Presentation");
		s.setSleep(5);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	private IconLoader iconLoader = new IconLoader(BASE_IMG);
	
	protected void initialize() {}
	protected void turn() {}
	protected Draw buildDraw() {return null;}

	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Menu", 
			action("Quitter", this::exit)
		);
	}
	
	
	protected Container buildContentPane() {
		Container panel1 = buildPanel(
				new GameMinesweeper(),
				new GameSnake(),
				new GameMissile(),
				new GameBomb());
		
		Container panel2 = buildPanel(
				new GameReversi3(),
				new GameChess1(),
				new GameHex1(),
				new GameTicTacToe3());

		Container panel3 = buildPanel(
				new AnimClock(),
				new AnimLandscape(),
				new AnimMaze(),
				new AnimLangtonAnt(),
				new AnimGameOfLife(),
				new AnimBallBattle(),
				new LifeBacteria1(),
				new AnimFishTank2()
				);

		Container panel4 = buildPanel(
				new MainSoucoupe1(),
				new MainSoucoupe2(),
				new MainMouse1(),
				new MainBall1(),
				new MainBall1m(),
				new MainBall2(),
				new MainBall2p(),
				new MainBall2r(),
				new MainImg1(),
				new MainImg2(),
				new MainDraws()
				);

		Container panel5 = buildPanel(
				new GameWarrior(),
				new GameSpace(),
				new GameBloon()
				);	
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Jeux solo", panel1);
		tabbedPane.addTab("Jeux à 2", panel2);
		tabbedPane.addTab("Animations", panel3);
		tabbedPane.addTab("Petits tests", panel4);
		tabbedPane.addTab("Experimental", panel5);
		return tabbedPane;
	}
	
	private Container buildPanel(Game... games) {
		JPanel p = new JPanel(new GridLayout(0,2,10,10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		for(Game game : games) p.add(buildArea(game));
		
		JScrollPane scroll = new JScrollPane(p);
		scroll.setPreferredSize(new Dimension(900,600));
		return scroll;
	}
	
	private GameArea buildArea(Game game) {
		GameArea area = new GameArea(game);
		area.setCaptureIcon(iconLoader.get("capture.gif"));
		return area;
	}
}
