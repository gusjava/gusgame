package gus.game5.core.util;

import java.util.List;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.features.t.T;

public class UtilRegexRule1 {

	/*
	 * BUILD REGEX
	 */

	public static String buildRegex(String rule) {
		String[] n = rule.split("\\*",-1);
		if (n.length != 2)
			throw new TechnicalException("Invalid rule: " + rule);
		return buildRegex(n[0], n[1]);
	}

	public static String buildRegex(String part1, String part2) {
		if (part2.length() == 0) return UtilRegex.quote(part1) + "(.+)";
		String s1 = "" + part2.charAt(0);
		return UtilRegex.quote(part1) + "([^" + UtilRegex.quote(s1) + "]+)" + UtilRegex.quote(part2);
	}

	/*
	 * EXTRACT
	 * 
	 * - extractAll
	 * - extractFirst
	 * - extractLast
	 * - extractAt
	 */

	/*
	 * EXTRACT ALL
	 */

	public static List<String> extractAll(String text, String rule) {
		String regex = buildRegex(rule);
		return UtilRegex.extractAll(text, regex);
	}
	
	/*
	 * EXTRACT FIRST
	 */

	public static String extractFirst(String text, String rule) {
		String regex = buildRegex(rule);
		return UtilRegex.extractFirst(text, regex);
	}
	
	/*
	 * EXTRACT LAST
	 */

	public static String extractLast(String text, String rule) {
		String regex = buildRegex(rule);
		return UtilRegex.extractLast(text, regex);
	}
	
	/*
	 * EXTRACT AT
	 */

	public static String extractAt(String text, String rule, int index) {
		String regex = buildRegex(rule);
		return UtilRegex.extractAt(text, regex, index);
	}
	
	
	/*
	 * REPLACE
	 * 
	 * - replaceAll
	 * - replaceFirst
	 * - replaceLast
	 * - replaceAt
	 */
	
	/*
	 * REPLACE ALL
	 */
	
	public static String replaceAll(String text, String rule, T<String, String> t) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceAll(text, regex, t);
	}
	
	/*
	 * REPLACE FIRST
	 */
	
	public static String replaceFirst(String text, String rule, T<String, String> t) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceFirst(text, regex, t);
	}
	
	/*
	 * REPLACE LAST
	 */
	
	public static String replaceLast(String text, String rule, T<String, String> t) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceLast(text, regex, t);
	}
	
	/*
	 * REPLACE AT
	 */
	
	public static String replaceAt(String text, String rule, T<String, String> t, int index) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceAt(text, regex, t, index);
	}
}