package gus.game5.core.function;

import gus.game5.core.features.h.H;

public interface Function extends H {
	
	public Function getDerived();//null: on ne sait pas
	public Boolean isEven();//null: on ne sait pas
	public Boolean isOdd();//null: on ne sait pas
	
	public boolean isDefined(double value);
}
