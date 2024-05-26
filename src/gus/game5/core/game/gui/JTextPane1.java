package gus.game5.core.game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JTextPane1 extends JTextPane {
	private static final long serialVersionUID = 1L;

	private StyledDocument doc;
	private SimpleAttributeSet attr;

	public JTextPane1() {
		super();
		doc = getStyledDocument();
		attr = new SimpleAttributeSet();

		setEditable(false);
		setMargin(new Insets(10, 10, 10, 10));
	}
	
	/*
	 * MARGIN
	 */
	
	public void setMargin(int top, int left, int bottom, int right) {
		super.setMargin(new Insets(top, left, bottom, right));
	}
	
	public void setMargin(int gap) {
		super.setMargin(new Insets(gap, gap, gap, gap));
	}
	
	/*
	 * FONT FAMILY
	 */

	public void initFontFamily(String val) {
		StyleConstants.setFontFamily(attr, val);
	}
	
	public String fontFamily() {
		return StyleConstants.getFontFamily(attr);
	}
	
	/*
	 * FONT SIZE
	 */

	public void initFontSize(int val) {
		StyleConstants.setFontSize(attr, val);
	}
	
	public int fontSize() {
		return StyleConstants.getFontSize(attr);
	}
	
	/*
	 * BOLD
	 */

	public void initBold(boolean val) {
		StyleConstants.setBold(attr, val);
	}
	
	/*
	 * ITALIC
	 */

	public void initItalic(boolean val) {
		StyleConstants.setItalic(attr, val);
	}
	
	/*
	 * UNDERLINE
	 */

	public void initUnderline(boolean val) {
		StyleConstants.setUnderline(attr, val);
	}
	
	/*
	 * FOREGROUND
	 */

	public void initForeground(Color color) {
		StyleConstants.setForeground(attr, color);
	}

	public Color foreground() {
		return StyleConstants.getForeground(attr);
	}
	
	/*
	 * BACKGROUND
	 */

	public void initBackground(Color color) {
		StyleConstants.setBackground(attr, color);
	}

	public Color background() {
		return StyleConstants.getBackground(attr);
	}
	
	/*
	 * APPEND TEXT
	 */

	public void appendText(String text) {
		try {
			doc.insertString(doc.getLength(), text, attr);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendText(int size, String text) {
		int size0 = fontSize();
		initFontSize(size);
		appendText(text);
		initFontSize(size0);
	}

	public void appendText(String text, Color color, boolean isBold, boolean isItalic) {
		Color fg = foreground();
		if (isBold) initBold(true);
		if (isItalic) initItalic(true);
		initForeground(color);
		appendText(text);
		initForeground(fg);
		initBold(false);
		initItalic(false);
	}
	
	/*
	 * APPEND BOLD TEXT
	 */

	public void appendBoldText(String text) {
		initBold(true);
		appendText(text);
		initBold(false);
	}

	public void appendBoldText(int size, String text) {
		int size0 = fontSize();
		initBold(true);
		initFontSize(size);
		appendText(text);
		initBold(false);
		initFontSize(size0);
	}
	
	/*
	 * APPEND LINE
	 */

	public void appendLine(String text) {
		appendText(text+"\n");
	}

	public void appendLine(int size, String text) {
		appendText(size, text+"\n");
	}
	
	/*
	 * APPEND BOLD LINE
	 */

	public void appendBoldLine(String text) {
		appendBoldText(text+"\n");
	}

	public void appendBoldLine(int size, String text) {
		appendBoldText(size, text+"\n");
	}
	
	/*
	 * CARET
	 */

	public void setCaretToEnd() {
		setCaretPosition(doc.getLength());
	}

	public void setSize(Dimension d) {
		if (d.width < getParent().getSize().width)
			d.width = getParent().getSize().width;
		super.setSize(d);
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
}
