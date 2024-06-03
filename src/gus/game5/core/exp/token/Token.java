package gus.game5.core.exp.token;

import java.util.List;
import java.util.Objects;

import gus.game5.core.util.UtilParser;

public class Token implements TokenSequence {
	
	public enum Type {
		OTHER, ELEMENT, SYMBOL, STRING, DOUBLE, INT, GROUP1, GROUP2, GROUP3
	}
	
	
	
	public Token(Type type, int start, String text) {
		this(type, start, text, text);
	}
	
	public Token(Type type, int start, String text, String value) {
		this.type = type;
		this.start = start;
		this.text = text;
		this.value = value;
		children = new TokenList();
	}
	
	public Token(Type type, int start, String text, Double value) {
		this.type = type;
		this.start = start;
		this.text = text;
		this.value = value;
		children = new TokenList();
	}
	
	public Token(Type type, int start, String text, TokenList children) {
		this.type = type;
		this.start = start;
		this.text = text;
		this.children = children;
	}
	
	
	
	
	/*
	 * START
	 */
	
	private int start;
	
	public int getStart() {
		return start;
	}
	
	/*
	 * END
	 */
	
	public int getEnd() {
		return start + text.length();
	}
	
	/*
	 * TEXT
	 */
	
	private String text;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	/*
	 * TYPE
	 */
	
	private Type type;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public boolean isOfType(Type type1) {
		return type==type1;
	}
	
	/*
	 * VALUE
	 */
	
	private Object	value;
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValue(Number value) {
		this.value = value;
	}
	
	public boolean isOfValue(Object value1) {
		return Objects.equals(value, value1);
	}
	
	/*
	 * CHILDREN
	 */
	
	private TokenList children;

	public TokenList getChildren() {
		return children;
	}
	
	public boolean hasChildren() {
		return children.size()>0;
	}
	
	public List<Token> childrenList() {
		return children.getList();
	}
	
	/*
	 * TO STRING
	 */
	
	public String toString() {
		if(hasChildren()) return type+":["+children+"]";
		return type+":["+value+"]";
	}
	
	/*
	 * PRINT PRETTY
	 */
	
	public void printPretty(String offset) {
		System.out.println(offset+type+":"+value+" ["+getStart()+"-"+getEnd()+"]");
		children.printPretty(offset+"  ");
	}
	
	
	/*
	 * IS
	 */

	public boolean isSymbol() {
		return isOfType(Type.SYMBOL);
	}
	
	public boolean isElement() {
		return isOfType(Type.ELEMENT);
	}
	
	public boolean isInt() {
		return isOfType(Type.INT);
	}
	
	public boolean isString() {
		return isOfType(Type.STRING);
	}

	public boolean isOther() {
		return isOfType(Type.OTHER);
	}
	
	public boolean isGroup1() {
		return isOfType(Type.GROUP1);
	}
	
	public boolean isGroup2() {
		return isOfType(Type.GROUP2);
	}
	
	public boolean isGroup3() {
		return isOfType(Type.GROUP3);
	}
	
	public boolean isSymbol(String value) {
		return isSymbol() && isOfValue(value);
	}
	
	public boolean isElement(String value) {
		return isElement() && isOfValue(value);
	}

	public boolean isPoint() {
		return isSymbol(UtilParser.C_POINT);
	}
	
	public boolean isQuote() {
		return isSymbol(UtilParser.C_QUOTE);
	}

	public boolean isDQuote() {
		return isSymbol(UtilParser.C_DQUOTE);
	}

	public boolean isEscape() {
		return isSymbol(UtilParser.C_ESCAPE);
	}
}
