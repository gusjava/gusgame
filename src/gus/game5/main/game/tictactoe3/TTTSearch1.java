package gus.game5.main.game.tictactoe3;

public class TTTSearch1 {

//	private int[][] data;
//	private int playerSide;
//	private int opponentSide;
//	
//	public TTTSearch1(int[][] data, int side) {
//		this.data = data;
//		this.playerSide = side;
//		this.opponentSide = oppositeSide(side);
//	}
//	
//	public int[] search() {
//		List<int[]> plays = findPossiblePlays(data);
//		List<Score> scores = new ArrayList<>();
//		for(int[] play : plays) {
//			Score score = new Score(data, play, playerSide, 0);
//			if(score.isWon()) return play;
//			scores.add(score);
//		}
//		scores.forEach(Score::computeValue);
//		Score score = UtilList.findForMaxDouble(scores, Score::getValue);
//		return score.getPlay();
//	}
//	
//	private class Score {
//		private int[][] data_;
//		private int[] play_;
//		private int side_;
//		private int deep_;
//		
//		private int winner_;
//		private double value_ = 0;
//		
//		public Score(int[][] data, int[] play, int side, int deep) {
//			this.data_ = UtilArray.clone(data);
//			this.play_ = play;
//			this.side_ = side;
//			this.deep_ = deep;
//			
//			data_[play_[0]][play_[1]] = side;
//			winner_ = searchWinner(data_);
//
//			System.out.println("new score ("+deep_+")");
//			System.out.println(dataToString(data_));
//			System.out.println("---------------------------");
//		}
//		
//		public boolean isWon() {
//			return winner_==playerSide;
//		}
//		
//		public boolean isLost() {
//			return winner_==opponentSide;
//		}
//		
//		public boolean isDraw() {
//			return winner_==0;
//		}
//		
//		public int[] getPlay() {
//			return play_;
//		}
//		
//		public void computeValue() {
//			System.out.println("computeValue");
//			int otherSide = oppositeSide(side_);
//			List<int[]> plays_ = findPossiblePlays(data_);
//			for(int[] play : plays_) {
//				Score score = new Score(data_, play, otherSide, deep_+1);
//				if(score.isWon()) value_++;
//				else if(score.isLost()) value_--;
//				else if(score.isDraw()) value_-=0.1;
//				else {
//					score.computeValue();
//					value_ += score.getValue() * 0.5;
//				}
//			}
//		}
//		
//		public double getValue() {
//			return value_;
//		}
//	}
}
