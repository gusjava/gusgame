package gus.game5.core.clean;

import java.util.ArrayList;
import java.util.List;

public class CleanList<E extends Clean> extends ArrayList<E> implements Clean {
	private static final long serialVersionUID = 1L;
	
	public CleanList() {
		super();
	}
	
	public CleanList(List<E> cleans) {
		super(cleans);
	}
	
	@SuppressWarnings("unchecked")
	public CleanList(E... cleans) {
		super();
		for(E clean : cleans) add(clean);
	}

	public void clean() {
		for(E element : this) element.clean();
	}
}
