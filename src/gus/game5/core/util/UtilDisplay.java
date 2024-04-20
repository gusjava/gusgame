package gus.game5.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class UtilDisplay {

	
	public static String dec2(double data) {
		DecimalFormat df = new DecimalFormat("0.00",DecimalFormatSymbols.getInstance(Locale.US));
		return df.format(data);
	}
	
	public static String dec3(double data) {
		DecimalFormat df = new DecimalFormat("0.000",DecimalFormatSymbols.getInstance(Locale.US));
		return df.format(data);
	}
}
