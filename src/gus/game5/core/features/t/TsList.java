package gus.game5.core.features.t;

import java.util.ArrayList;

public class TsList<U> extends ArrayList<Ts<U>> implements Ts<U> {
	private static final long serialVersionUID = 1L;

	public U t(U obj) {
		for(Ts<U> ts : this) obj = ts.t(obj);
		return obj;
	}
}
