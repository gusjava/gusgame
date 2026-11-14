package gus.game5.main.game.p1.c.mastermind;

public class Score {

	public static int[] count(int[] secret, int[] guess, int colors) {
		int blacks = 0;
		int[] secretLeft = new int[colors];
		int[] guessLeft = new int[colors];
		for(int i=0;i<secret.length;i++) {
			if(secret[i]==guess[i]) {blacks++; continue;}
			secretLeft[secret[i]]++;
			guessLeft[guess[i]]++;
		}
		int whites = 0;
		for(int c=0;c<colors;c++) whites += Math.min(secretLeft[c], guessLeft[c]);
		return new int[] {blacks, whites};
	}
}
