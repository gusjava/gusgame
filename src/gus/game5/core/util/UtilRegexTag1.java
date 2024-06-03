package gus.game5.core.util;

import java.util.List;

import gus.game5.core.features.t.T;

public class UtilRegexTag1 {

	/*
	 * BUILD REGEX
	 */

	public static String buildRegex(String tagName) {
		String tagNameQuoted = UtilRegex.quote(tagName);
		return "<"+tagNameQuoted+"[\\session_>].*?</"+tagNameQuoted+">";
	}
	
	public static String buildRegexInside(String tagName) {
		String tagNameQuoted = UtilRegex.quote(tagName);
		return "<"+tagNameQuoted+"[^>]*>(.*?)</"+tagNameQuoted+">";
	}

	/*
	 * EXTRACT ALL
	 */

	public static List<String> extractAll(String text, String tagName) {
		String regex = buildRegex(tagName);
		return UtilRegex.extractAll(text, regex);
	}
	
	/*
	 * EXTRACT FIRST
	 */

	public static String extractFirst(String text, String tagName) {
		String regex = buildRegex(tagName);
		return UtilRegex.extractFirst(text, regex);
	}
	
	/*
	 * EXTRACT LAST
	 */

	public static String extractLast(String text, String tagName) {
		String regex = buildRegex(tagName);
		return UtilRegex.extractLast(text, regex);
	}

	
	
	/*
	 * EXTRACT ALL INSIDE
	 */
	
	public static List<String> extractAllInside(String text, String tagName) {
		String regex = buildRegexInside(tagName);
		return UtilRegex.extractAll(text, regex);
	}
	
	/*
	 * EXTRACT FIRST INSIDE
	 */

	public static String extractFirstInside(String text, String tagName) {
		String regex = buildRegexInside(tagName);
		return UtilRegex.extractFirst(text, regex);
	}
	
	/*
	 * EXTRACT LAST INSIDE
	 */

	public static String extractLastInside(String text, String tagName) {
		String regex = buildRegexInside(tagName);
		return UtilRegex.extractLast(text, regex);
	}
	
	
	
	
	
	/*
	 * REPLACE ALL
	 */
	
	public static String replaceAll(String text, String tagName, T<String, String> t) {
		String regex = buildRegex(tagName);
		return UtilRegex.replaceAll(text, regex, t);
	}
	
	/*
	 * REPLACE FIRST
	 */
	
	public static String replaceFirst(String text, String tagName, T<String, String> t) {
		String regex = buildRegex(tagName);
		return UtilRegex.replaceFirst(text, regex, t);
	}
	
	/*
	 * REPLACE LAST
	 */
	
	public static String replaceLast(String text, String tagName, T<String, String> t) {
		String regex = buildRegex(tagName);
		return UtilRegex.replaceLast(text, regex, t);
	}
	
	
	
	
	/*
	 * REPLACE ALL INSIDE
	 */
	
	public static String replaceAllInside(String text, String tagName, T<String, String> t) {
		String regex = buildRegexInside(tagName);
		return UtilRegex.replaceAll(text, regex, t);
	}
	
	/*
	 * REPLACE FIRST INSIDE
	 */
	
	public static String replaceFirstInside(String text, String tagName, T<String, String> t) {
		String regex = buildRegexInside(tagName);
		return UtilRegex.replaceFirst(text, regex, t);
	}
	
	/*
	 * REPLACE LAST INSIDE
	 */
	
	public static String replaceLastInside(String text, String tagName, T<String, String> t) {
		String regex = buildRegexInside(tagName);
		return UtilRegex.replaceLast(text, regex, t);
	}
}