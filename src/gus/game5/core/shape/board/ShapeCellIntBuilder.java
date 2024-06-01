package gus.game5.core.shape.board;

public interface ShapeCellIntBuilder<U extends ShapeCell> {

	public U build(int i, int j, int value);
}
