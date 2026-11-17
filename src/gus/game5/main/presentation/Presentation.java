package gus.game5.main.presentation;

import static gus.game5.core.util.UtilGui.action;

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
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.util.image.IconLoader;
import gus.game5.main.anim.fishtank.AnimFishTank;
import gus.game5.main.anim.gameoflife1.AnimGameOfLife;
import gus.game5.main.anim.langtonant.AnimLangtonAnt;
import gus.game5.main.anim.life1.LifeBacteria1;
import gus.game5.main.anim1.AnimAntColony;
import gus.game5.main.anim1.AnimAntColonyTsp;
import gus.game5.main.anim1.AnimAutumn;
import gus.game5.main.anim1.AnimBallBattle;
import gus.game5.main.anim1.AnimBoids;
import gus.game5.main.anim1.AnimBrianBrain;
import gus.game5.main.anim1.AnimChladni;
import gus.game5.main.anim1.AnimClock;
import gus.game5.main.anim1.AnimCyclicCA;
import gus.game5.main.anim1.AnimDaisyworld;
import gus.game5.main.anim1.AnimDoublePendulum;
import gus.game5.main.anim1.AnimElementaryCA;
import gus.game5.main.anim1.AnimEpidemic;
import gus.game5.main.anim1.AnimEvolvingCreatures;
import gus.game5.main.anim1.AnimFireworks;
import gus.game5.main.anim1.AnimFlowField;
import gus.game5.main.anim1.AnimForestFire;
import gus.game5.main.anim1.AnimFractalTree;
import gus.game5.main.anim1.AnimHarmonograph;
import gus.game5.main.anim1.AnimHighLife;
import gus.game5.main.anim1.AnimHodgepodge;
import gus.game5.main.anim1.AnimLSystem;
import gus.game5.main.anim1.AnimLandscape;
import gus.game5.main.anim1.AnimLangtonLoops;
import gus.game5.main.anim1.AnimLenia;
import gus.game5.main.anim1.AnimLorenzAttractor;
import gus.game5.main.anim1.AnimMandala;
import gus.game5.main.anim1.AnimMaze;
import gus.game5.main.anim1.AnimNBody;
import gus.game5.main.anim1.AnimParticleLife2;
import gus.game5.main.anim1.AnimPendulumWave;
import gus.game5.main.anim1.AnimPlasma;
import gus.game5.main.anim1.AnimReactionDiffusion;
import gus.game5.main.anim1.AnimSandpile;
import gus.game5.main.anim1.AnimSchelling;
import gus.game5.main.anim1.AnimSlimeMold;
import gus.game5.main.anim1.AnimSnowfall;
import gus.game5.main.anim1.AnimSolarSystem;
import gus.game5.main.anim1.AnimSpirograph;
import gus.game5.main.anim1.AnimStarfield;
import gus.game5.main.anim1.AnimTSP;
import gus.game5.main.anim1.AnimTermites;
import gus.game5.main.anim1.AnimVacheQuiRit;
import gus.game5.main.anim1.AnimWaTor;
import gus.game5.main.anim1.AnimWireworld;
import gus.game5.main.edu.prog.modulo1.EduModulo1;
import gus.game5.main.game.p1.c.antivirus.GameAntivirus;
import gus.game5.main.game.p1.c.columns.GameColumns;
import gus.game5.main.game.p1.c.compteestbon.GameCompteEstBon;
import gus.game5.main.game.p1.c.drmario.GameDrMario;
import gus.game5.main.game.p1.c.flowfree.GameFlowFree;
import gus.game5.main.game.p1.c.game2048.Game2048;
import gus.game5.main.game.p1.c.labyrinthe.GameLabyrinthe;
import gus.game5.main.game.p1.c.lightsout.GameLightsOut;
import gus.game5.main.game.p1.c.madvirus.GameMadVirus;
import gus.game5.main.game.p1.c.mastermind.GameMastermind;
import gus.game5.main.game.p1.c.memory.GameMemory;
import gus.game5.main.game.p1.c.minesweeper.GameMinesweeper;
import gus.game5.main.game.p1.c.pacman.GamePacman;
import gus.game5.main.game.p1.c.pipemania.GamePipeMania;
import gus.game5.main.game.p1.c.puyo.GamePuyo;
import gus.game5.main.game.p1.c.puzzle1.GamePuzzle1;
import gus.game5.main.game.p1.c.simon.GameSimon;
import gus.game5.main.game.p1.c.snake.GameSnake;
import gus.game5.main.game.p1.c.sokoban.GameSokoban;
import gus.game5.main.game.p1.c.solitaire8.GameSolitaire8;
import gus.game5.main.game.p1.c.taquin.GameTaquin;
import gus.game5.main.game.p1.c.tetris.GameTetris;
import gus.game5.main.game.p1.c.towerofhanoi.GameTowerOfHanoi;
import gus.game5.main.game.p1.c.tron.GameTron;
import gus.game5.main.game.p1.o.asteroids.GameAsteroids;
import gus.game5.main.game.p1.o.blockrun.GameBlockRun;
import gus.game5.main.game.p1.o.bomb.GameBomb;
import gus.game5.main.game.p1.o.brickblast.GameBrickBlast;
import gus.game5.main.game.p1.o.flappy.GameFlappy;
import gus.game5.main.game.p1.o.frogger.GameFrogger;
import gus.game5.main.game.p1.o.missile.GameMissile;
import gus.game5.main.game.p1.o.space.GameSpace;
import gus.game5.main.game.p1.o.warrior.GameWarrior;
import gus.game5.main.game.p2.c.board.chess.v2.GameChess2;
import gus.game5.main.game.p2.c.board.hex.v1.GameHex1;
import gus.game5.main.game.p2.c.board.quoridor.v1.GameQuoridor1;
import gus.game5.main.game.p2.c.board.reversi.v3.GameReversi3;
import gus.game5.main.game.p2.c.board.tictactoe.v3.GameTicTacToe3;
import gus.game5.main.game.p2.o.board.ragus1.GameRagus1;
import gus.game5.main.genetic.GameImageEvolution;
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
	
	private IconLoader iconLoader = new IconLoader(this);
	
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
				new GamePuzzle1(),
				new GameAntivirus(),
				new GameMadVirus(),
				new GameSolitaire8(),
				new GameSnake(),
				new GamePacman(),
				new GameTetris(),
				new Game2048(),
				new GameColumns(),
				new GameDrMario(),
				new GamePuyo(),
				new GameLightsOut(),
				new GameSokoban(),
				new GameTaquin(),
				new GameMastermind(),
				new GameMemory(),
				new GameFlowFree(),
				new GamePipeMania(),
				new GameLabyrinthe(),
				new GameSimon(),
				new GameCompteEstBon(),
				new GameTron(),
				new GameAsteroids(),
				new GameFrogger(),
				new GameFlappy(),
				new GameBlockRun(),
				new GameTowerOfHanoi(),
				new GameMissile(),
				new GameBomb(),
				new GameBrickBlast());
		
		Container panel2 = buildPanel(
				new GameReversi3(),
				new GameChess2(),
				new GameHex1(),
				new GameRagus1(),
				new GameQuoridor1(),
				new GameTicTacToe3());

		Container panel3 = buildPanel(
				new AnimClock(),
				new AnimLandscape(),
				new AnimVacheQuiRit(),
				new AnimMaze(),
				new AnimLangtonAnt(),
				new AnimGameOfLife(),
				new AnimBallBattle(),
				new LifeBacteria1(),
				new AnimFishTank(),
				new AnimFireworks(),
				new AnimStarfield(),
				new AnimSnowfall(),
				new AnimPlasma(),
				new AnimSpirograph(),
				new AnimMandala(),
				new AnimSolarSystem(),
				new AnimPendulumWave(),
				new AnimDoublePendulum(),
				new AnimFractalTree(),
				new AnimLorenzAttractor(),
				new AnimReactionDiffusion(),
				new AnimChladni(),
				new AnimHarmonograph(),
				new AnimElementaryCA(),
				new AnimFlowField(),
				new AnimNBody(),
				new AnimParticleLife2(),
				new AnimSlimeMold(),
				new AnimWaTor(),
				new AnimAntColony(),
				new AnimLSystem(),
				new AnimTSP(),
				new AnimAntColonyTsp(),
				new AnimBoids(),
				new AnimTermites(),
				new AnimDaisyworld(),
				new AnimEpidemic(),
				new AnimEvolvingCreatures(),
				new AnimLenia(),
				new AnimCyclicCA(),
				new AnimForestFire(),
				new AnimSchelling(),
				new AnimLangtonLoops(),
				new AnimWireworld(),
				new AnimBrianBrain(),
				new AnimHighLife(),
				new AnimSandpile(),
				new AnimHodgepodge(),
				new AnimAutumn(),
				new GameImageEvolution()
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

		Container panel6 = buildPanel(
				new EduModulo1()
				);	
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Jeux solo", panel1);
		tabbedPane.addTab("Jeux à 2", panel2);
		tabbedPane.addTab("Animations", panel3);
		tabbedPane.addTab("Petits tests", panel4);
		tabbedPane.addTab("Experimental", panel5);
		tabbedPane.addTab("Educatif", panel6);
		return tabbedPane;
	}
	
	private Container buildPanel(Game... games) {
		JPanel p = new JPanel(new GridLayout(0,3,10,10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		for(Game game : games) p.add(buildArea(game));
		
		JScrollPane scroll = new JScrollPane(p);
		scroll.setPreferredSize(new Dimension(1200,600));
		return scroll;
	}
	
	private GameArea buildArea(Game game) {
		GameArea area = new GameArea(game);
		area.setCaptureIcon(iconLoader.get("capture.gif"));
		return area;
	}
}
