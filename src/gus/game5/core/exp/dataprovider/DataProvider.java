package gus.game5.core.exp.dataprovider;

import gus.game5.core.exp.exception.ExpException;

public interface DataProvider {
	public Object provideData() throws ExpException;
}
