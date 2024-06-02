package gus.game5.main.game.p2.c.board.reversi.v3;

import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.*;

import java.util.List;

import gus.game5.core.util.UtilArray;

public class UtilReversi3Minmax {
	
	public static final int DEPTH_MAX = 9;

	public static List<int[]> computePlay(int player, int[][] data) {
		State state = alphaBeta(player, 0, data, 1000000.0f, -1000000.0f);
		if(state.play==null) return randomPlay(player, data);
		return state.play;
	}

    private static State alphaBeta(int player, int depth, int[][] data, float alpha, float beta) {
        List<List<int[]>> plays = findPossiblePlays(player, data);
    	if(plays==null || depth==DEPTH_MAX) {
            State state = new State();
        	int winner = searchWinner(player, data);
            state.fitness = evaluate(data, player, winner);
            return state;
    	}

    	int oppositePlayer = oppositeValue(player);
        State bestState = new State();
        bestState.fitness = beta;

        for(int i=0; i<plays.size(); i++) {
        	List<int[]> play = plays.get(i);
        	
        	int[][] newData = UtilArray.clone(data);
        	for(int[] p : play) newData[p[0]][p[1]] = player;
        	
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

    private static float evaluate(int[][] data, int player, int winner) { 
        float emptyCount = 0f;
        float playerCount = 0f;
        float opponentCount = 0f;
        for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
        	if (data[i][j] == EMPTY) emptyCount++;
        	else if (data[i][j] == player) playerCount++;
        	else opponentCount++;
        }
        float v = (playerCount - opponentCount) / emptyCount;
        
        if(data[0][0]!=EMPTY) v += data[0][0]==player ? 5 : -5;
        if(data[0][7]!=EMPTY) v += data[0][7]==player ? 5 : -5;
        if(data[7][0]!=EMPTY) v += data[7][0]==player ? 5 : -5;
        if(data[7][7]!=EMPTY) v += data[7][7]==player ? 5 : -5;
        
        return v;
    }
	
	private static class State {
		public List<int[]> play;
		public float fitness;
	}
}
