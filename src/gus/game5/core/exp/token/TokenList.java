package gus.game5.core.exp.token;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilList;

public class TokenList implements TokenSequence {
	
	
	public TokenList() {
		list = new ArrayList<>();
	}
	
	public TokenList(TokenList tokenList) {
		list = new ArrayList<>(tokenList.getList());
	}
	
	
	/*
	 * LIST
	 */

	private List<Token> list;
	
	public List<Token> getList() {
		return list;
	}
	
	
	/*
	 * SIZE
	 */
	
	public int size() {
		return list.size();
	}
	
	/*
	 * EMPTY
	 */
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/*
	 * GET
	 */
	
	public Token get(int index) {
		return list.get(index);
	}
	
	/*
	 * ADD
	 */
	
	public void add(Token token) {
		list.add(token);
	}
	
	/*
	 * ADD ALL
	 */
	
	public void addAll(List<Token> tokens) {
		list.addAll(tokens);
	}
	
	/*
	 * REMOVE FIRST
	 */
	
	public Token removeFirst() {
		if(list.isEmpty()) return null;
		return list.remove(0);
	}
	
	/*
	 * REMOVE FIRST
	 */
	
	public Token remove(int index) {
		return list.remove(index);
	}
	
	
	/*
	 * FIRST
	 */
	
	public Token first() {
		return UtilList.first(list);
	}
	
	/*
	 * LAST
	 */
	
	public Token last() {
		return UtilList.last(list);
	}
	
	/*
	 * START
	 */
	
	public int getStart() {
		Token first = first();
		return first!=null ? first.getStart() : 0;
	}
	
	/*
	 * END
	 */

	public int getEnd() {
		Token last = last();
		return last!=null ? last.getEnd() : 0;
	}
	
	/*
	 * TO STRING
	 */
	
	public String toString() {
		return list.toString();
	}
	
	/*
	 * CLONE
	 */
	
	public TokenList clone() {
		return new TokenList(this);
	}
	
	/*
	 * PRINT PRETTY
	 */
	
	public void printPretty() {
		printPretty("");
	}
	
	public void printPretty(String offset) {
		for(Token token : list) {
			token.printPretty(offset);
		}
	}
}
