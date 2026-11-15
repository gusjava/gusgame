package gus.game5.core.util;

public class LevelProgress {

	public enum State {PLAYING, CLEARED, FINISHED}

	private final int levelCount;
	private final int clearTicks;

	private int levelIndex;
	private State state;
	private int stateTick;

	public LevelProgress(int levelCount, int clearTicks) {
		this.levelCount = levelCount;
		this.clearTicks = clearTicks;
	}

	public void reset() {
		levelIndex = 0;
		setState(State.PLAYING);
	}

	public int getLevelIndex() {
		return levelIndex;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public State getState() {
		return state;
	}

	public boolean isPlaying() {
		return state==State.PLAYING;
	}

	public boolean isCleared() {
		return state==State.CLEARED;
	}

	public boolean isFinished() {
		return state==State.FINISHED;
	}

	public void setState(State s) {
		state = s;
		stateTick = 0;
	}

	public boolean tickClearedAndCheckAdvance() {
		stateTick++;
		return stateTick>clearTicks;
	}

	public boolean nextLevel() {
		levelIndex++;
		if(levelIndex>=levelCount) {
			setState(State.FINISHED);
			return false;
		}
		return true;
	}
}
