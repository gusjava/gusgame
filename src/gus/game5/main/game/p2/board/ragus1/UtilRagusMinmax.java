package gus.game5.main.game.p2.board.ragus1;

import static gus.game5.main.game.p2.board.ragus1.UtilRagus.*;

import java.util.List;

public class UtilRagusMinmax {
	
	public static final int DEPTH_MAX = 4;

	public static int[] computePlay(int player, int[][] data) {
		State state = alphaBeta(player, 0, data, 1000000.0f, -1000000.0f);
		if(state.play==null) return randomPlay(player, data);
		return state.play;
	}

    private static State alphaBeta(int player, int depth, int[][] data, float alpha, float beta) {
        List<int[]> plays = findPossiblePlays(player, data);
    	if(plays==null || depth==DEPTH_MAX) {
            State state = new State();
            state.fitness = evaluate(data, player);
            return state;
    	}

    	int oppositePlayer = player*-1;
        State bestState = new State();
        bestState.fitness = beta;

        for(int i=0; i<plays.size(); i++) {
        	int[] play = plays.get(i);

    		int[] start = new int[] {play[0],play[1]};
    		int[] end = new int[] {play[2],play[3]};
        	int[][] newData = attemptToPlay(player, data, start, end);
        	
        	State nextState = alphaBeta(oppositePlayer, depth+1, newData, -bestState.fitness, -alpha);
        	float value = -nextState.fitness;
        	
        	if(value > bestState.fitness) {
        		bestState.play = play;
        		bestState.fitness = value;
        	}
        	if(bestState.fitness >= alpha) break;
        }
    	return bestState;
    }
    
    /*
     * EVALUATE
     */
    
    private static float[] F1 = new float[] {0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 10};
    private static float[] F2 = new float[] {10, 6, 5, 4, 3, 2, 0, 0, 0, 0, 0, 0, 0};

    private static float evaluate(int[][] data, int player) { 
    	float[] fMe = player==PLAYER_DINO ? F1 : F2;
    	float[] fHim = player==PLAYER_DINO ? F2 : F1;
    	
        float count = 0f;
		for(int i=0;i<13;i++) for(int j=0;j<8;j++) {
			int value = data[i][j];
			if(value!=0) {
				int strength = Math.abs(value);
				int[] pos = new int[] {i,j};
				boolean isBlocked = isBlocked(data, pos, value, strength);
				boolean isMe = data[i][j]*player>0;
				
				if(!isBlocked) {
					if(isMe) count += fMe[i] * strength;
					else count -= fHim[i] * strength;
				}
			}
		}
        return count;
    }
	
	private static class State {
		public int[] play;
		public float fitness;
	}
}
