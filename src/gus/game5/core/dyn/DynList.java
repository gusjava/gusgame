package gus.game5.core.dyn;

import java.util.ArrayList;

public class DynList<E extends Dyn> extends ArrayList<E> implements Dyn {
	private static final long serialVersionUID = 1L;

	public void goNext() {
		for(E element : this) element.goNext();
	}

	public void goBack() {
		for(E element : this) element.goBack();
	}
}
