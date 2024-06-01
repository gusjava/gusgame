package gus.game5.main.game.p2.board.chess2;

import static gus.game5.main.game.p2.board.chess1.UtilChess.BB;
import static gus.game5.main.game.p2.board.chess1.UtilChess.BK;
import static gus.game5.main.game.p2.board.chess1.UtilChess.BKI;
import static gus.game5.main.game.p2.board.chess1.UtilChess.BP;
import static gus.game5.main.game.p2.board.chess1.UtilChess.BQ;
import static gus.game5.main.game.p2.board.chess1.UtilChess.BR;
import static gus.game5.main.game.p2.board.chess1.UtilChess.WB;
import static gus.game5.main.game.p2.board.chess1.UtilChess.WK;
import static gus.game5.main.game.p2.board.chess1.UtilChess.WKI;
import static gus.game5.main.game.p2.board.chess1.UtilChess.WP;
import static gus.game5.main.game.p2.board.chess1.UtilChess.WQ;
import static gus.game5.main.game.p2.board.chess1.UtilChess.WR;

import java.awt.image.BufferedImage;

import gus.game5.core.game.Game;
import gus.game5.core.util.image.ImageLoader;

public class ImageLoader1 extends ImageLoader {

	public ImageLoader1(Game game) {
		super(game);
	}

	public BufferedImage valueToImg(int value) {
		switch(value) {
			case WP:return get("CHESS1_pawn1.gif");
			case WR:return get("CHESS1_rook1.gif");
			case WB:return get("CHESS1_bishop1.gif");
			case WK:return get("CHESS1_knight1.gif");
			case WQ:return get("CHESS1_queen1.gif");
			case WKI:return get("CHESS1_king1.gif");
			
			case BP:return get("CHESS1_pawn2.gif");
			case BR:return get("CHESS1_rook2.gif");
			case BB:return get("CHESS1_bishop2.gif");
			case BK:return get("CHESS1_knight2.gif");
			case BQ:return get("CHESS1_queen2.gif");
			case BKI:return get("CHESS1_king2.gif");
		}
		return null;
	}
}
