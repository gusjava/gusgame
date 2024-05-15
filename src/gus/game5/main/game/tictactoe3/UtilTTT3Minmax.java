package gus.game5.main.game.tictactoe3;

import static gus.game5.main.game.tictactoe3.UtilTTT3.EMPTY;
import static gus.game5.main.game.tictactoe3.UtilTTT3.NOUGHT;
import static gus.game5.main.game.tictactoe3.UtilTTT3.findPossiblePlays;
import static gus.game5.main.game.tictactoe3.UtilTTT3.oppositeValue;
import static gus.game5.main.game.tictactoe3.UtilTTT3.searchWinner;

import java.util.List;

import gus.game5.core.util.UtilArray;

public class UtilTTT3Minmax {

	public static int[] computePlay(int player, int[] data) {
		State state = alphaBeta(player, data, 1000000.0f, -1000000.0f);
		return state.play;
	}

    private static State alphaBeta(int player, int[] data, float alpha, float beta) {
    	int winner = searchWinner(data);
    	if(winner!=-1) {
            State state = new State();
            state.fitness = evaluate(data, player, winner);
            return state;
    	}

    	int oppositePlayer = oppositeValue(player);
        State bestState = new State();
        bestState.fitness = beta;

        List<int[]> plays = findPossiblePlays(data);
        for(int i=0; i<plays.size(); i++) {
        	int[] play = plays.get(i);
        	
        	int[] newData = UtilArray.clone(data);
        	newData[play[0]] = player;
        	
        	State nextState = alphaBeta(oppositePlayer, newData, -bestState.fitness, -alpha);
        	float value = -nextState.fitness;
        	
        	if(value > bestState.fitness) {
        		bestState.play = play;
        		bestState.fitness = value;
        	}
        	if(bestState.fitness >= alpha) break;
        }
    	return bestState;
    }

    private static float evaluate(int[] data, int player, int winner) { 
        int count = 10;
        for (int i=0; i<9; i++) if (data[i] == EMPTY) count--;
        
        float base = 1.0f;
        if(data[4]==player) {
        	if(player == NOUGHT) base -= 0.4f;
            else base += 0.4f;
        }
        
        if(winner==player) return base + (1.0f / count);
        if(winner==oppositeValue(player)) return - base - (1.0f / count);
        return base - 1.0f;
    }
	
	private static class State {
		public int[] play;
		public float fitness;
	}
}
