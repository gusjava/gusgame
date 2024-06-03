package gus.game5.core.exp.resolver;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.token.TokenSequence;
import gus.game5.core.util.UtilBoolean;
import gus.game5.core.util.UtilDate;
import gus.game5.core.util.UtilDouble;
import gus.game5.core.util.UtilInteger;
import gus.game5.core.util.UtilString;

public class ResolverResult {
	
	public enum Type {
		STRING,BOOLEAN,DATE,INTEGER,DOUBLE,LIST,SET,MAP,OBJECT,NULL
	}

	private TokenSequence sequence;
	private Object data;
	
	public ResolverResult(TokenSequence sequence, Object data) {
		this.sequence = sequence;
		this.data = data;
	}
	
	/*
	 * SEQUENCE
	 */
	
	public TokenSequence getSequence() {
		return sequence;
	}
	
	/*
	 * IS RESOLVED
	 */
	
	public boolean isResolved() {
		if(data==null) return true;
		return !(data instanceof Type);
	}
	
	/*
	 * TYPE
	 */
	
	public Type getType() {
		if(data==null) return Type.NULL;
		if(data instanceof Type) return (Type) data;
		
		if(isDataBoolean()) return Type.BOOLEAN;
		if(isDataInteger()) return Type.INTEGER;
		if(isDataDouble()) return Type.DOUBLE;
		if(isDataDate()) return Type.DATE;
		if(isDataString()) return Type.STRING;
		if(isDataList()) return Type.LIST;
		if(isDataSet()) return Type.SET;
		if(isDataMap()) return Type.MAP;
		return Type.OBJECT;
	}
	
	/*
	 * IS TYPE
	 */
	
	public boolean isTypeBoolean() {
		return getType() == Type.BOOLEAN;
	}
	
	public boolean isTypeInteger() {
		return getType() == Type.INTEGER;
	}
	
	public boolean isTypeDouble() {
		return getType() == Type.DOUBLE;
	}
	
	public boolean isTypeNumber() {
		return isTypeInteger() || isTypeDouble();
	}
	
	public boolean isTypeDate() {
		return getType() == Type.DATE;
	}
	
	public boolean isTypeString() {
		return getType() == Type.STRING;
	}
	
	public boolean isTypeList() {
		return getType() == Type.LIST;
	}
	
	/*
	 * DATA
	 */
	
	public Object getData() {
		return data;
	}
	
	public String getDataDesc() {
		if(isResolved()) return getType()+":"+data;
		return getType().toString();
	}
	
	public void printData() {
		System.out.println(getDataDesc());
	}
	
	/*
	 * IS DATA
	 */
	
	public boolean isDataNull() {
		return data==null;
	}
	
	public boolean isDataBoolean() {
		if(data==null) return false;
		if(data instanceof Boolean) return true;
		return false;
	}
	
	public boolean isDataTrue() {
		if(data==null) return false;
		if(data instanceof Boolean) return ((Boolean) data).booleanValue();
		return false;
	}
	
	public boolean isDataFalse() {
		if(data==null) return false;
		if(data instanceof Boolean) return ((Boolean) data).booleanValue()==false;
		return false;
	}
	
	public boolean isDataInteger() {
		if(data==null) return false;
		if(data instanceof Integer) return true;
		return false;
	}
	
	public boolean isDataDouble() {
		if(data==null) return false;
		if(data instanceof Double) return true;
		return false;
	}
	
	public boolean isDataNumber() {
		if(data==null) return false;
		if(data instanceof Number) return true;
		return false;
	}
	
	public boolean isDataDate() {
		if(data==null) return false;
		if(data instanceof Date) return true;
		return false;
	}
	
	public boolean isDataString() {
		if(data==null) return false;
		if(data instanceof String) return true;
		return false;
	}
	
	public boolean isDataList() {
		if(data==null) return false;
		if(data instanceof List) return true;
		return false;
	}
	
	public boolean isDataSet() {
		if(data==null) return false;
		if(data instanceof Set) return true;
		return false;
	}
	
	public boolean isDataMap() {
		if(data==null) return false;
		if(data instanceof Map) return true;
		return false;
	}
	
	
	/*
	 * AS
	 */
	
	public boolean asBoolean() {
		return UtilBoolean.objToBoolean(data);
	}
	
	public double asDouble() {
		return UtilDouble.objToDouble(data);
	}
	
	public int asInteger() {
		return UtilInteger.objToInteger(data);
	}
	
	public Date asDate() {
		return UtilDate.objToDate(data);
	}
	
	public String asString() {
		return UtilString.objToString(data);
	}
}
