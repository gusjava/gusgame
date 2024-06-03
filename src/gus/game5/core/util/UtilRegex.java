package gus.game5.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gus.game5.core.features.t.T;

public class UtilRegex {
	
	public static final String META = "<([{\\^-=$!|]})?*+.>";
	
	/*
	 * QUOTE
	 */
	
	public static String quote(String s) {
		StringBuilder b = new StringBuilder();
		
		for(int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if(META.indexOf(c)>=0) b.append("\\");
			b.append(c);
		}
		return b.toString();
	}
	
	/*
	 * PATTERN
	 */
	
	public static Pattern pattern(String regex) {
		return Pattern.compile(regex, Pattern.DOTALL);
	}
	
	/*
	 * GROUP
	 */
	
	public static int group(Matcher m) {
		return m.groupCount() == 0 ? 0 : 1;
	}
	
	/*
	 * COUNT
	 */
	
	public static int count(String text, String regex) {
		if(text==null || regex==null) return 0;
		return count(text, pattern(regex));
	}

	public static int count(String text, Pattern p) {
		if(text==null || p==null) return 0;
		return _count(p.matcher(text));
	}
	
	
	
	
	/*
	 * EXTRACT :
	 * 
	 * - extractAll
	 * - extractFirst
	 * - extractLast
	 * - extractAt
	 * 
	 * - extractAll (default group)
	 * - extractFirst (default group)
	 * - extractLast (default group)
	 * - extractAt (default group)
	 */
	
	/*
	 * EXTRACT ALL
	 */

	public static List<String> extractAll(String text, String regex, int group) {
		if(text==null || regex==null) return new ArrayList<>();
		return extractAll(text, pattern(regex), group);
	}

	public static List<String> extractAll(String text, Pattern p, int group) {
		if(text==null || p==null) return new ArrayList<>();
		return _extractAll(p.matcher(text), group);
	}
	
	/*
	 * EXTRACT FIRST
	 */
	
	public static String extractFirst(String text, String regex, int group) {
		if(text==null || regex==null) return null;
		return extractFirst(text, pattern(regex), group);
	}

	public static String extractFirst(String text, Pattern p, int group) {
		if(text==null || p==null) return null;
		return _extractFirst(p.matcher(text), group);
	}
	
	/*
	 * EXTRACT LAST
	 */
	
	public static String extractLast(String text, String regex, int group) {
		if(text==null || regex==null) return null;
		return extractLast(text, pattern(regex), group);
	}

	public static String extractLast(String text, Pattern p, int group) {
		if(text==null || p==null) return null;
		return _extractLast(p.matcher(text), group);
	}
	
	/*
	 * EXTRACT AT
	 */
	
	public static String extractAt(String text, String regex, int index, int group) {
		if(text==null || regex==null) return null;
		return extractAt(text, pattern(regex), index, group);
	}

	public static String extractAt(String text, Pattern p, int index, int group) {
		if(text==null || p==null) return null;
		return _extractAt(p.matcher(text), index, group);
	}

	/*
	 * EXTRACT ALL (DEFAULT GROUP)
	 */

	public static List<String> extractAll(String text, String regex) {
		if(text==null || regex==null) return new ArrayList<>();
		return extractAll(text, pattern(regex));
	}

	public static List<String> extractAll(String text, Pattern p) {
		if(text==null || p==null) return new ArrayList<>();
		Matcher m = p.matcher(text);
		return _extractAll(m, group(m));
	}
	
	/*
	 * EXTRACT FIRST (DEFAULT GROUP)
	 */
	
	public static String extractFirst(String text, String regex) {
		if(text==null || regex==null) return null;
		return extractFirst(text, pattern(regex));
	}

	public static String extractFirst(String text, Pattern p) {
		if(text==null || p==null) return null;
		Matcher m = p.matcher(text);
		return _extractFirst(m, group(m));
	}
	
	/*
	 * EXTRACT LAST (DEFAULT GROUP)
	 */
	
	public static String extractLast(String text, String regex) {
		if(text==null || regex==null) return null;
		return extractLast(text, pattern(regex));
	}

	public static String extractLast(String text, Pattern p) {
		if(text==null || p==null) return null;
		Matcher m = p.matcher(text);
		return _extractLast(m, group(m));
	}
	
	/*
	 * EXTRACT AT (DEFAULT GROUP)
	 */
	
	public static String extractAt(String text, String regex, int index) {
		if(text==null || regex==null) return null;
		return extractAt(text, pattern(regex), index);
	}

	public static String extractAt(String text, Pattern p, int index) {
		if(text==null || p==null) return null;
		Matcher m = p.matcher(text);
		return _extractAt(m, index, group(m));
	}

	

	/*
	 * REPLACE :
	 * 
	 * - replaceAll
	 * - replaceFirst
	 * - replaceLast
	 * - replaceAt
	 * 
	 * - replaceAll (default group)
	 * - replaceFirst (default group)
	 * - replaceLast (default group)
	 * - replaceAt (default group)
	 */
	
	/*
	 * REPLACE ALL
	 */
	
	public static String replaceAll(String text, String regex, String repl, int group) {
		if(text==null || regex==null) return text;
		return replaceAll(text, pattern(regex), repl, group);
	}
	
	public static String replaceAll(String text, Pattern p, String repl, int group) {
		if(text==null || p==null) return text;
		return _replaceAll(p.matcher(text), repl, group);
	}
	
	public static String replaceAll(String text, String regex, T<String, String> t, int group) {
		if(text==null || regex==null) return text;
		return replaceAll(text, pattern(regex), t, group);
	}
	
	public static String replaceAll(String text, Pattern p, T<String, String> t, int group) {
		if(text==null || p==null) return text;
		return _replaceAll(p.matcher(text), t, group);
	}
	
	/*
	 * REPLACE FIRST
	 */

	public static String replaceFirst(String text, String regex, String repl, int group) {
		if(text==null || regex==null) return text;
		return replaceFirst(text, pattern(regex), repl, group);
	}
	
	public static String replaceFirst(String text, Pattern p, String repl, int group) {
		if(text==null || p==null) return text;
		return _replaceFirst(p.matcher(text), repl, group);
	}
	
	public static String replaceFirst(String text, String regex, T<String, String> t, int group) {
		if(text==null || regex==null) return text;
		return replaceFirst(text, pattern(regex), t, group);
	}
	
	public static String replaceFirst(String text, Pattern p, T<String, String> t, int group) {
		if(text==null || p==null) return text;
		return _replaceFirst(p.matcher(text), t, group);
	}
	
	/*
	 * REPLACE LAST
	 */

	public static String replaceLast(String text, String regex, String repl, int group) {
		if(text==null || regex==null) return text;
		return replaceLast(text, pattern(regex), repl, group);
	}
	
	public static String replaceLast(String text, Pattern p, String repl, int group) {
		if(text==null || p==null) return text;
		int count = count(text, p);
		return _replaceAt(p.matcher(text), repl, count-1, group);
	}
	
	public static String replaceLast(String text, String regex, T<String, String> t, int group) {
		if(text==null || regex==null) return text;
		return replaceLast(text, pattern(regex), t, group);
	}
	
	public static String replaceLast(String text, Pattern p, T<String, String> t, int group) {
		if(text==null || p==null) return text;
		int count = count(text, p);
		return _replaceAt(p.matcher(text), t, count-1, group);
	}
	
	/*
	 * REPLACE AT
	 */

	public static String replaceAt(String text, String regex, String repl, int index, int group) {
		if(text==null || regex==null) return text;
		return replaceAt(text, pattern(regex), repl, index, group);
	}
	
	public static String replaceAt(String text, Pattern p, String repl, int index, int group) {
		if(text==null || p==null) return text;
		return _replaceAt(p.matcher(text), repl, index, group);
	}
	
	public static String replaceAt(String text, String regex, T<String, String> t, int index, int group) {
		if(text==null || regex==null) return text;
		return replaceAt(text, pattern(regex), t, index, group);
	}
	
	public static String replaceAt(String text, Pattern p, T<String, String> t, int index, int group) {
		if(text==null || p==null) return text;
		return _replaceAt(p.matcher(text), t, index, group);
	}
	
	
	

	
	/*
	 * REPLACE ALL (DEFAULT GROUP)
	 */
	
	public static String replaceAll(String text, String regex, String repl) {
		if(text==null || regex==null) return text;
		return replaceAll(text, pattern(regex), repl);
	}
	
	public static String replaceAll(String text, Pattern p, String repl) {
		if(text==null || p==null) return text;
		Matcher m = p.matcher(text);
		return _replaceAll(m, repl, group(m));
	}
	
	public static String replaceAll(String text, String regex, T<String, String> t) {
		if(text==null || regex==null) return text;
		return replaceAll(text, pattern(regex), t);
	}
	
	public static String replaceAll(String text, Pattern p, T<String, String> t) {
		if(text==null || p==null) return text;
		Matcher m = p.matcher(text);
		return _replaceAll(m, t, group(m));
	}
	
	
	
	
	/*
	 * REPLACE FIRST (DEFAULT GROUP)
	 */

	public static String replaceFirst(String text, String regex, String repl) {
		if(text==null || regex==null) return text;
		return replaceFirst(text, pattern(regex), repl);
	}
	
	public static String replaceFirst(String text, Pattern p, String repl) {
		if(text==null || p==null) return text;
		Matcher m = p.matcher(text);
		return _replaceFirst(m, repl, group(m));
	}
	
	public static String replaceFirst(String text, String regex, T<String, String> t) {
		if(text==null || regex==null) return text;
		return replaceFirst(text, pattern(regex), t);
	}
	
	public static String replaceFirst(String text, Pattern p, T<String, String> t) {
		if(text==null || p==null) return text;
		Matcher m = p.matcher(text);
		return _replaceFirst(m, t, group(m));
	}
	
	
	
	
	/*
	 * REPLACE LAST (DEFAULT GROUP)
	 */

	public static String replaceLast(String text, String regex, String repl) {
		if(text==null || regex==null) return text;
		return replaceLast(text, pattern(regex), repl);
	}
	
	public static String replaceLast(String text, Pattern p, String repl) {
		if(text==null || p==null) return text;
		int count = count(text, p);
		return replaceAt(p.matcher(text), repl, count-1);
	}
	
	
	public static String replaceLast(String text, String regex, T<String, String> t) {
		if(text==null || regex==null) return text;
		return replaceLast(text, pattern(regex), t);
	}
	
	public static String replaceLast(String text, Pattern p, T<String, String> t) {
		if(text==null || p==null) return text;
		int count = count(text, p);
		
		Matcher m = p.matcher(text);
		return _replaceAt(m, t, count-1, group(m));
	}
	
	
	
	
	/*
	 * REPLACE AT (DEFAULT GROUP)
	 */

	public static String replaceAt(String text, String regex, String repl, int index) {
		if(text==null || regex==null) return text;
		return replaceAt(text, pattern(regex), repl, index);
	}
	
	public static String replaceAt(String text, Pattern p, String repl, int index) {
		if(text==null || p==null) return text;
		int count = count(text, p);
		if(index<0) index += count;
		if(index<0 || index>=count) return text;
		return replaceAt(p.matcher(text), repl, index);
	}
	
	public static String replaceAt(Matcher m, String repl, int index) {
		if(m==null) return null;
		return _replaceAt(m, repl, index, group(m));
	}
	
	public static String replaceAt(String text, String regex, T<String, String> t, int index) {
		if(text==null || regex==null) return text;
		return replaceAt(text, pattern(regex), t, index);
	}
	
	public static String replaceAt(String text, Pattern p, T<String, String> t, int index) {
		if(text==null || p==null) return text;
		int count = count(text, p);
		if(index<0) index += count;
		if(index<0 || index>=count) return text;
		
		Matcher m = p.matcher(text);
		return _replaceAt(m, t, index, group(m));
	}
	
	
	
	/*
	 * FROM
	 * 
	 * - replaceAllFromMap
	 * - replaceAllFromCol
	 * - extractAllFromCol
	 * - countFromCol
	 */
	
	/*
	 * REPLACE ALL FROM MAP
	 */
	
	public static String replaceAllFromMap(String s, Map<String,String> map) {
		String regex = _buildRegexFromCol(map.keySet());
		Matcher m = pattern(regex).matcher(s);
		return _replaceAll(m, map::get, 0);
	}
	
	/*
	 * REPLACE ALL FROM COL
	 */
	
	public static String replaceAllFromCol(String s, Collection<String> col, String repl) {
		String regex = _buildRegexFromCol(col);
		Matcher m = pattern(regex).matcher(s);
		return _replaceAll(m, repl, 0);
	}
	
	public static String replaceAllFromCol(String s, Collection<String> col, T<String,String> t) {
		String regex = _buildRegexFromCol(col);
		Matcher m = pattern(regex).matcher(s);
		return _replaceAll(m, t, 0);
	}
	
	/*
	 * EXTRACT ALL FROM COL
	 */
	
	public static List<String> extractAllFromCol(String s, Collection<String> col) {
		String regex = _buildRegexFromCol(col);
		Matcher m = pattern(regex).matcher(s);
		return _extractAll(m, 0);
	}
	
	/*
	 * COUNT FROM COL
	 */
	
	public static int countFromCol(String s, Collection<String> col) {
		String regex = _buildRegexFromCol(col);
		Matcher m = pattern(regex).matcher(s);
		return _count(m);
	}
	

	
	/*
	 * SPLIT
	 */
	
	public static String[] split(String s, String regex) {
		return split(s, regex, -1);
	}
	
	public static String[] split(String s, String regex, int limit) {
		return UtilString.isEmpty(s) ? new String[0] : s.split(regex, limit);
	}
	
	public static List<String> splitAsList(String s, String regex) {
		return Arrays.asList(split(s,regex));
	}
	
	public static List<String> splitAsList(String s, String regex, int limit) {
		return Arrays.asList(split(s,regex, limit));
	}
	
	
	/*
	 * SUB BEFORE
	 */
	
	public static String subB(String s, String regex, int index) {
		int len = count(s, regex);

		if(index<0) index += len+1;
		if(index<0 || index>len) return s;
		
		Matcher m = pattern(regex).matcher(s);
		StringBuffer b = new StringBuffer();
		for(int i=0;i<index;i++) {
			m.find();
			if(i<index-1) m.appendReplacement(b, m.group());
			else m.appendReplacement(b, "");
		}
		return b.toString();
	}
	
	/*
	 * SUB AFTER (A RETRAVAILLER !!!!)
	 */
	
	public static String subA(String s, String regex, int index) {
		String[] nn = split(s, regex);
		int len = nn.length;

		if(index<0) index += len;
		if(index<0 || index>len-1) return s;
		
		StringBuilder sb = new StringBuilder();
		for(int i=index;i<len; i++) {
			sb.append(nn[i]);
			if(i<len-1) sb.append(regex);
		}
		return sb.toString();
	}
	
	
	
	/*
	 * PRIVATE
	 */
	
	private static int _count(Matcher m) {
		int count = 0;
		while(m.find()) count++;
		return count;
	}
	
	private static List<String> _extractAll(Matcher m, int group) {
		List<String> list = new ArrayList<>();
		while(m.find()) list.add(m.group(group));
		return list;
	}

	private static String _extractFirst(Matcher m, int group) {
		return m.find() ? m.group(group) : null;
	}

	private static String _extractLast(Matcher m, int group) {
		String result = null;
		while(m.find()) result = m.group(group);
		return result;
	}

	private static String _extractAt(Matcher m, int index, int group) {
		List<String> list = _extractAll(m, group);
		int len = list.size();
		if(index<0) index += len;
		
		if(index<0 || index>len-1) return null;
		return list.get(index);
	}
	
	private static String _replaceAll(Matcher m, String repl, int group) {
		StringBuffer b = new StringBuffer();
		while(m.find()) {
			String replacement = Matcher.quoteReplacement(repl);
			m.appendReplacement(b, replacement);
		}
		m.appendTail(b);
		return b.toString();
	}
	
	private static String _replaceAll(Matcher m, T<String, String> t, int group) {
		StringBuffer b = new StringBuffer();
		while(m.find()) {
			String extracted = m.group(group);
			String replacement = Matcher.quoteReplacement(t.t(extracted));
			m.appendReplacement(b, replacement);
		}
		m.appendTail(b);
		return b.toString();
	}
	
	private static String _replaceFirst(Matcher m, String repl, int group) {
		StringBuffer b = new StringBuffer();
		if(m.find()) {
			String replacement = Matcher.quoteReplacement(repl);
			m.appendReplacement(b, replacement);
		}
		m.appendTail(b);
		return b.toString();
	}
	
	private static String _replaceFirst(Matcher m, T<String, String> t, int group) {
		StringBuffer b = new StringBuffer();
		if(m.find()) {
			String extracted = m.group(group);
			String replacement = Matcher.quoteReplacement(t.t(extracted));
			m.appendReplacement(b, replacement);
		}
		m.appendTail(b);
		return b.toString();
	}
	
	private static String _replaceAt(Matcher m, String repl, int index, int group) {
		for(int i=0;i<=index;i++) m.find();
		
		StringBuffer b = new StringBuffer();
		String replacement = Matcher.quoteReplacement(repl);	
		m.appendReplacement(b, replacement);
		m.appendTail(b);
		
		return b.toString();
	}
	
	private static String _replaceAt(Matcher m, T<String, String> t, int index, int group) {
		for(int i=0;i<index;i++) m.find();
		
		StringBuffer b = new StringBuffer();
		String extracted = m.group(group);
		String replacement = Matcher.quoteReplacement(t.t(extracted));	
		m.appendReplacement(b, replacement);
		m.appendTail(b);
		
		return b.toString();
	}
	
	private static String _buildRegexFromCol(Collection<String> col) {
		StringBuilder b = new StringBuilder();
		List<String> list = UtilList.sortInv(col, UtilComparator.compStrLen());
		int len = list.size();
		for(int i=0;i<len;i++) {
			b.append(quote(list.get(i)));
			if(i<len-1) b.append("|");
		}
		return b.toString();
	}
}