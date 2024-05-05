package gus.game5.core.shape.board;

public interface ShapeCellBuilder<U extends ShapeCell> {

	public U build(int i, int j);
}
