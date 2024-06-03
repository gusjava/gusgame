package gus.game5.core.exp.resolver1;

import java.util.Map;

import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver1.t.Resolver1TElement;
import gus.game5.core.exp.resolver1.t.Resolver1TGroup2;
import gus.game5.core.exp.resolver1.t.Resolver1TGroup3;
import gus.game5.core.exp.resolver1.t.Resolver1TValue;
import gus.game5.core.exp.token.Token;

public class Resolver1Data extends Resolver1Number {
	
	protected Map<String,Object> dataMap;
	
	public Resolver1Data(Map<String,Object> dataMap) {
		super();
		this.dataMap = dataMap;

		// [.,.,.,.]
		putOpT(Token.Type.GROUP2, getRevolverTGroup2());
		// {.,.,.,.}
		putOpT(Token.Type.GROUP3, getRevolverTGroup3());
		// variable
		putOpT(Token.Type.ELEMENT, getRevolverTElement());
		// "string"
		putOpT(Token.Type.STRING, getRevolverTString());
	}
	
	public Map<String,Object> getDataMap() {
		return dataMap;
	}
	

	protected ResolverT getRevolverTGroup2() {
		return new Resolver1TGroup2(this);
	}

	protected ResolverT getRevolverTGroup3() {
		return new Resolver1TGroup3(this);
	}
	
	protected ResolverT getRevolverTElement() {
		return new Resolver1TElement(dataMap);
	}
	
	protected ResolverT getRevolverTString() {
		return new Resolver1TValue();
	}
}
