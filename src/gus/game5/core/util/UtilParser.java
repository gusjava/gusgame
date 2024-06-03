package gus.game5.core.util;

public class UtilParser {

	
	public static String C_POINT	= ".";
	public static String C_QUOTE	= "'";
	public static String C_DQUOTE = "\"";
	public static String C_ESCAPE = "\\";
	public static String C_PLUS	= "+";
	public static String C_MINUS	= "-";
	public static String C_STAR	= "*";
	public static String C_QUESTION	= "?";
	public static String C_COLON	= ":";
	public static String C_COMMA	= ",";
	public static String C_SHARP	= "#";
	public static String C_DOLLAR	= "$";

	/*
	 * CHARS
	 */
	
	public static boolean isSymbol(char c) {
		return c == '.' || c == ',' || c == ';' || c == '?' || c == '!' || c == ':' || c == '*' || c == '+' || c == '-' || c == '%' || c == '°' || c == '^' || c == '\\' || c == '/' || c == '|'
				|| c == '<' || c == '>' || c == '¨' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == '@' || c == '#' || c == '&' || c == '\"' || c == '\'' || c == '~'
				|| c == '$' || c == '£' || c == 'µ' || c == '§' || c == '¤' || c == '=';
	}

	public static boolean isLetter(char c) {
		int code = (int) c;
		return (code > 96 && code < 123) || (code > 64 && code < 91);
	}

	public static boolean isDigit(char c) {
		int code = (int) c;
		return code > 47 && code < 58;
	}
	
	/*
	 * STRINGS
	 */
	
	public static boolean isSymbol(String s) {
		return s.length()==1 && isSymbol(s.charAt(0));
	}
	
	public static boolean isDoubleSymbol(String s) {
		return s.length()==2 && isSymbol(s.charAt(0)) && isSymbol(s.charAt(1));
	}
	
	public static String stringAt(String s, int index) {
		return String.valueOf(s.charAt(index));
	}
	
	/*
	 * NUMBERS
	 */
	
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static int toInt(String s) {
		return Integer.parseInt(s);
	}
	
	public static Number toIntOrLongObj(Object n) {
		Number intObj = toIntObj(n);
		return intObj!=null ? intObj : toLongObj(n);
	}
	
	public static Integer toIntObj(Object n) {
		try {
			return Integer.valueOf(n.toString());
		} catch (NumberFormatException e) {}
		return null;
	}

	public static Long toLongObj(Object n) {
		try {
			return Long.valueOf(n.toString());
		} catch (NumberFormatException e) {}
		return null;
	}
}
