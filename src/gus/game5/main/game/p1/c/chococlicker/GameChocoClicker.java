package gus.game5.main.game.p1.c.chococlicker;

import static gus.game5.core.util.UtilGui.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GBool;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.main.game.p1.c.chococlicker.ChocoIcon.Kind;

public class GameChocoClicker extends Game1 {

	public static final long SLEEP = 100;

	public static final long START_CHOCOLATE_G = 1000;
	public static final long UNIT_G = 1000;
	public static final long START_SELL_PRICE_CT = 100;
	public static final long START_BUY_PRICE_CT = 90;
	public static final long UPGRADE_SELL_PRICE_CT = 120;
	public static final long UPGRADE_BUY_PRICE_CT = 80;
	public static final long UPGRADE_COST_CT = 1000;
	public static final long UPGRADE_QTY_COST_CT = 1000;
	public static final long UPGRADE_SELL_QTY_G = 2000;
	public static final long UPGRADE_BUY_QTY_G = 2000;
	public static final long SELLER_BASE_COST_CT = 2000;
	public static final long SELLER_COST_NUM = 3;
	public static final long SELLER_COST_DEN = 2;
	public static final long SELLER_UNIT_G = 500;
	public static final long AUTO_TURNS_PER_CYCLE = 10;
	public static final long AUTOCLICK_TURNS = 2;
	public static final long SMART_COST_CT = 3000;

	public static final long BAKERY_COST_CT = 5000;
	public static final long CAKE_CHOCOLATE_G = 250;
	public static final long CAKE_PRICE_CT = 150;
	public static final long BAKER_BASE_COST_CT = 8000;
	public static final long BAKER_COST_NUM = 3;
	public static final long BAKER_COST_DEN = 2;
	public static final long BAKER_CAKE_PER_CYCLE = 1;
	public static final long SELLER_CAKE_UNIT = 1;

	public static final long MARGIN_COST_CT = 500;
	public static final long RESTOCK_COST_CT = 2500;
	public static final long RESTOCK_MIN_G = 1000;
	public static final long RESTOCK_SECONDS = 2;
	public static final long NEGOTIATION_BASE_COST_CT = 4000;
	public static final long NEGOTIATION_COST_NUM = 2;
	public static final long NEGOTIATION_COST_DEN = 1;
	public static final long NEGOTIATION_RATE = 10;
	public static final long NEGOTIATION_FLOOR_CT = 40;

	public static final Color CREAM = new Color(0xF5EBDC);
	public static final Color TILE_CREAM = new Color(0xFBF4E9);
	public static final Color COCOA = new Color(0x3E2512);
	public static final Color MILK = new Color(0x7B4B2A);
	public static final Color SOFT = new Color(0xEADBC6);
	public static final Color MUTED = new Color(0x8A7259);
	public static final Color LINE = new Color(0xD8C4A8);
	public static final Color OFF_FILL = new Color(0xE3D7C5);
	public static final Color OFF_LINE = new Color(0xD2C3AC);
	public static final Color PRICE_ON_DARK = new Color(0x8FE3A8);
	public static final Color PRICE_ON_LIGHT = new Color(0x1E7A3C);
	public static final Color PRICE_OFF = new Color(0x6E8A76);

	public static final int ARC = 16;
	public static final int GAP = 10;
	public static final int ROW_WIDTH = 560;
	public static final int ROW_HEIGHT = 29;
	public static final int ROW_GAP = 6;
	public static final int ROW_ICON = 20;
	public static final int STATE_ICON = 42;
	public static final int SCROLL_WIDTH = 16;
	public static final int CONTENT_WIDTH = ROW_WIDTH+SCROLL_WIDTH;
	public static final int MAX_ROWS = 9;
	public static final int MAX_ROWS_HEIGHT = MAX_ROWS*(ROW_HEIGHT+ROW_GAP);

	public static final String SEPARATOR = "   ·   ";


	private long chocolateG;
	private long moneyCt;
	private long sellPriceCt;
	private long buyPriceCt;
	private long sellUnitG;
	private long buyUnitG;
	private long sellerCount;
	private long sellerCostCt;
	private long autoTurn;
	private long smartLevel;
	private boolean marginVisible;
	private boolean autoRestock;
	private long negotiationCostCt;
	private long cakeCount;
	private long bakerCount;
	private long bakerCostCt;
	private boolean bakeryOpen;
	private boolean autoClick;
	private long clickTurn;

	private JFrame frame;
	private List<StateTile> tiles;
	private List<StateLabel> labels;
	private List<ActionButton> buttons;


	public static void main(String[] args) {
		GameChocoClicker main = new GameChocoClicker();
		main.frame = main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Choco clicker");
		s.setSleep(SLEEP);
	}

	protected void initialize1() {
	}

	protected void turn() {
		if(autoClick) autoClickTurn();

		if(sellerCount==0 && bakerCount==0 && !autoRestock) return;

		autoTurn++;
		if(autoTurn < AUTO_TURNS_PER_CYCLE) return;
		autoTurn = 0;

		boolean changed = sellAuto();
		changed |= bakeAuto();
		changed |= restockAuto();

		if(changed) refreshAll();
	}


	/*
	 * AUTOMATION
	 */

	private void autoClickTurn() {
		clickTurn++;
		if(clickTurn < AUTOCLICK_TURNS) return;
		clickTurn = 0;

		ActionButton hovered = hoveredButton();
		if(hovered!=null) hovered.perform();
	}

	private ActionButton hoveredButton() {
		if(frame==null || !frame.isActive()) return null;

		PointerInfo info = MouseInfo.getPointerInfo();
		if(info==null) return null;

		Point point = new Point(info.getLocation());
		SwingUtilities.convertPointFromScreen(point, frame.getContentPane());
		Component target = SwingUtilities.getDeepestComponentAt(frame.getContentPane(), point.x, point.y);

		for(ActionButton button : buttons) if(button.button==target) return button;
		return null;
	}

	private boolean bakeAuto() {
		long bakedCakes = Math.min(bakerCount*BAKER_CAKE_PER_CYCLE, chocolateG/CAKE_CHOCOLATE_G);
		if(bakedCakes==0) return false;

		chocolateG -= bakedCakes*CAKE_CHOCOLATE_G;
		cakeCount += bakedCakes;
		return true;
	}

	private boolean sellAuto() {
		long soldCakes = Math.min(cakeCount, sellerCount*SELLER_CAKE_UNIT);
		long soldG = Math.min(sellableG(), freeSellers(soldCakes)*SELLER_UNIT_G);
		if(soldCakes==0 && soldG==0) return false;

		cakeCount -= soldCakes;
		moneyCt += soldCakes*CAKE_PRICE_CT;
		chocolateG -= soldG;
		moneyCt += soldG*sellPriceCt/1000;
		return true;
	}

	private long sellableG() {
		return Math.max(0, chocolateG - bakerCount*BAKER_CAKE_PER_CYCLE*CAKE_CHOCOLATE_G);
	}

	private long freeSellers(long soldCakes) {
		return sellerCount - (soldCakes+SELLER_CAKE_UNIT-1)/SELLER_CAKE_UNIT;
	}

	private boolean restockAuto() {
		if(!autoRestock) return false;

		long thresholdG = restockThresholdG();
		long costCt = buyUnitG*buyPriceCt/1000;
		boolean bought = false;

		while(chocolateG < thresholdG && moneyCt >= costCt) {
			moneyCt -= costCt;
			chocolateG += buyUnitG;
			bought = true;
		}
		return bought;
	}

	private long restockThresholdG() {
		long perSecondG = sellerCount*SELLER_UNIT_G + bakerCount*BAKER_CAKE_PER_CYCLE*CAKE_CHOCOLATE_G;
		return Math.max(RESTOCK_MIN_G, perSecondG*RESTOCK_SECONDS);
	}

	private long cakeMarginCt() {
		return CAKE_PRICE_CT - CAKE_CHOCOLATE_G*buyPriceCt/1000;
	}

	private long netCtPerSecond() {
		long bakedCakes = bakerCount*BAKER_CAKE_PER_CYCLE;
		long soldCakes = Math.min(bakedCakes, sellerCount*SELLER_CAKE_UNIT);
		long soldG = freeSellers(soldCakes)*SELLER_UNIT_G;

		long earned = soldCakes*CAKE_PRICE_CT + soldG*sellPriceCt/1000;
		long spent = (bakedCakes*CAKE_CHOCOLATE_G + soldG)*buyPriceCt/1000;
		return earned - spent;
	}

	private long nextBuyPriceCt() {
		return Math.max(NEGOTIATION_FLOOR_CT, buyPriceCt - Math.max(1, buyPriceCt/NEGOTIATION_RATE));
	}

	private List<String> statusParts() {
		List<String> parts = new ArrayList<>();
		if(sellerCount>0) parts.add(sellerCount+(sellerCount>1 ? " commerciaux" : " commercial"));
		if(bakerCount>0) parts.add(bakerCount+(bakerCount>1 ? " patissiers" : " patissier"));
		if(smartLevel>0) parts.add("mode smart niveau "+smartLevel);
		if(marginVisible) parts.add("marge "+(bakeryOpen ? "chocolat " : "")+fmtEur(sellPriceCt-buyPriceCt)+" EUR/Kg");
		if(marginVisible && bakeryOpen) parts.add("marge gateau "+fmtEur(cakeMarginCt())+" EUR");
		if(marginVisible) parts.add("gain auto "+fmtEur(netCtPerSecond())+" EUR/s");
		if(autoRestock) parts.add("reappro sous "+fmtKg(restockThresholdG())+" Kg");
		return parts;
	}


	/*
	 * CONTENT PANE
	 */

	protected Container buildContentPane() {
		chocolateG = START_CHOCOLATE_G;
		moneyCt = 0;
		sellPriceCt = START_SELL_PRICE_CT;
		buyPriceCt = START_BUY_PRICE_CT;
		sellUnitG = UNIT_G;
		buyUnitG = UNIT_G;
		sellerCount = 0;
		sellerCostCt = SELLER_BASE_COST_CT;
		autoTurn = 0;
		smartLevel = 0;
		marginVisible = false;
		autoRestock = false;
		negotiationCostCt = NEGOTIATION_BASE_COST_CT;
		cakeCount = 0;
		bakerCount = 0;
		bakerCostCt = BAKER_BASE_COST_CT;
		bakeryOpen = false;
		autoClick = false;
		clickTurn = 0;

		tiles = new ArrayList<>();
		labels = new ArrayList<>();
		buttons = new ArrayList<>();

		GBool smart1 = () -> smartLevel>=1;
		GBool bakery = () -> bakeryOpen;

		JPanel north = panelY();
		JPanel resources = new JPanel(new GridLayout(1, 0, GAP, 0));
		resources.setOpaque(false);
		resources.setAlignmentX(Component.CENTER_ALIGNMENT);
		addTile(resources, Kind.CHOCOLATE, "CHOCOLAT", () -> fmtKg(chocolateG)+" Kg");
		addTile(resources, Kind.MONEY, "ARGENT", () -> fmtEur(moneyCt)+" EUR");
		addTile(resources, Kind.CAKE, bakery, "GATEAUX", () -> String.valueOf(cakeCount));
		north.add(resources);
		addStatus(north, this::statusParts);

		JPanel center = panelY();
		center.setAlignmentX(Component.CENTER_ALIGNMENT);

		addRow(center, Kind.CHOCOLATE,
			() -> "Vendre du chocolat",
			() -> "rapporte "+fmtEur(sellUnitG*sellPriceCt/1000)+" EUR",
			() -> fmtKg(sellUnitG)+" Kg",
			() -> chocolateG >= sellUnitG,
			() -> {
				chocolateG -= sellUnitG;
				moneyCt += sellUnitG*sellPriceCt/1000;
			});

		addRow(center, Kind.CAKE,
			() -> "Vendre un gateau",
			() -> "rapporte "+fmtEur(CAKE_PRICE_CT)+" EUR",
			() -> "1 gateau",
			() -> cakeCount >= 1,
			() -> {
				cakeCount--;
				moneyCt += CAKE_PRICE_CT;
			});

		addRow(center, Kind.MONEY,
			() -> "Acheter "+fmtKg(buyUnitG)+" Kg de chocolat",
			() -> "",
			() -> fmtEur(buyUnitG*buyPriceCt/1000)+" EUR",
			() -> moneyCt >= buyUnitG*buyPriceCt/1000,
			() -> {
				moneyCt -= buyUnitG*buyPriceCt/1000;
				chocolateG += buyUnitG;
			});

		addUpgrade(center, Kind.MONEY, UPGRADE_COST_CT,
			"Meilleur prix de vente",
			"le kilo se vend "+fmtEur(UPGRADE_SELL_PRICE_CT)+" EUR",
			() -> {
				sellPriceCt = Math.max(sellPriceCt, UPGRADE_SELL_PRICE_CT);
			});

		addUpgrade(center, Kind.MONEY, UPGRADE_COST_CT,
			"Meilleur prix d'achat",
			"le kilo s'achete "+fmtEur(UPGRADE_BUY_PRICE_CT)+" EUR",
			() -> {
				buyPriceCt = Math.min(buyPriceCt, UPGRADE_BUY_PRICE_CT);
			});

		addUpgrade(center, Kind.CHOCOLATE, UPGRADE_QTY_COST_CT,
			"Plus gros lot de vente",
			fmtKg(UPGRADE_SELL_QTY_G)+" Kg vendus par clic",
			() -> {
				sellUnitG = UPGRADE_SELL_QTY_G;
			});

		addUpgrade(center, Kind.CHOCOLATE, UPGRADE_QTY_COST_CT,
			"Plus gros lot d'achat",
			fmtKg(UPGRADE_BUY_QTY_G)+" Kg achetes par clic",
			() -> {
				buyUnitG = UPGRADE_BUY_QTY_G;
			});

		addRow(center, Kind.SELLER,
			() -> "Recruter un commercial",
			() -> bakeryOpen
				? "vend 1 gateau ou "+fmtKg(SELLER_UNIT_G)+" Kg par seconde"
				: "vend "+fmtKg(SELLER_UNIT_G)+" Kg par seconde",
			() -> fmtEur(sellerCostCt)+" EUR",
			() -> moneyCt >= sellerCostCt,
			() -> {
				moneyCt -= sellerCostCt;
				sellerCount++;
				sellerCostCt = sellerCostCt*SELLER_COST_NUM/SELLER_COST_DEN;
			});

		addUpgrade(center, Kind.CAKE, BAKERY_COST_CT,
			"Ouvrir une patisserie",
			"les patissiers transforment le chocolat en gateaux",
			() -> {
				bakeryOpen = true;
			});

		addRow(center, Kind.BAKER,
			() -> "Recruter un patissier",
			() -> "transforme "+fmtKg(CAKE_CHOCOLATE_G)+" Kg en 1 gateau par seconde",
			() -> fmtEur(bakerCostCt)+" EUR",
			() -> bakeryOpen && moneyCt >= bakerCostCt,
			() -> {
				moneyCt -= bakerCostCt;
				bakerCount++;
				bakerCostCt = bakerCostCt*BAKER_COST_NUM/BAKER_COST_DEN;
			});

		addUpgrade(center, Kind.MONEY, SMART_COST_CT,
			"Reflechir a la strategie",
			"passage en mode smart niveau 1",
			() -> {
				smartLevel = 1;
			});

		addUpgrade(center, Kind.MONEY, smart1, MARGIN_COST_CT,
			"Calculer sa marge",
			"affiche la marge et le gain automatique",
			() -> {
				marginVisible = true;
			});

		addUpgrade(center, Kind.CHOCOLATE, smart1, RESTOCK_COST_CT,
			"Consigne de reapprovisionnement",
			"rachete tout seul quand le stock baisse",
			() -> {
				autoRestock = true;
			});

		addRow(center, Kind.MONEY,
			() -> "Negocier avec le fournisseur",
			() -> "prix d'achat "+fmtEur(buyPriceCt)+" a "+fmtEur(nextBuyPriceCt())+" EUR/Kg",
			() -> fmtEur(negotiationCostCt)+" EUR",
			() -> smartLevel>=1 && moneyCt >= negotiationCostCt,
			() -> buyPriceCt <= NEGOTIATION_FLOOR_CT,
			() -> {
				moneyCt -= negotiationCostCt;
				buyPriceCt = nextBuyPriceCt();
				negotiationCostCt = negotiationCostCt*NEGOTIATION_COST_NUM/NEGOTIATION_COST_DEN;
			});

		refreshAll();

		Dimension bannerSize = new Dimension(CONTENT_WIDTH, resources.getPreferredSize().height);
		resources.setPreferredSize(bannerSize);
		resources.setMaximumSize(bannerSize);

		JPanel root = new JPanel(new BorderLayout());
		root.setBackground(CREAM);
		root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
		root.add(north, BorderLayout.NORTH);
		root.add(new ActionScroll(center), BorderLayout.CENTER);
		return root;
	}


	/*
	 * MENU BAR
	 */

	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Partie",
			action("Quitter", this::exit)
		);
		menuBar.add("Test", (Component) autoClickItem());
	}

	private JCheckBoxMenuItem autoClickItem() {
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Auto clic");
		item.addActionListener(e -> autoClick = item.isSelected());
		return item;
	}


	/*
	 * DECLARATION
	 */

	private void addTile(JPanel panel, Kind kind, String name, G<String> value) {
		addTile(panel, kind, () -> true, name, value);
	}

	private void addTile(JPanel panel, Kind kind, GBool visible, String name, G<String> value) {
		tiles.add(new StateTile(panel, kind, visible, name, value));
	}

	private void addStatus(JPanel panel, G<List<String>> parts) {
		StateLabel stateLabel = new StateLabel(parts);
		labels.add(stateLabel);
		panel.add(stateLabel.label);
	}

	private void addRow(JPanel panel, Kind kind, G<String> title, G<String> detail, G<String> cost, GBool usable, Runnable effect) {
		addRow(panel, kind, title, detail, cost, usable, () -> false, effect);
	}

	private void addRow(JPanel panel, Kind kind, G<String> title, G<String> detail, G<String> cost, GBool usable, GBool exhausted, Runnable effect) {
		addButton(panel, new ActionButton(kind, true, title, detail, cost, usable, false, exhausted, effect));
	}

	private void addUpgrade(JPanel panel, Kind kind, long costCt, String title, String detail, Runnable effect) {
		addUpgrade(panel, kind, () -> true, costCt, title, detail, effect);
	}

	private void addUpgrade(JPanel panel, Kind kind, GBool unlocked, long costCt, String title, String detail, Runnable effect) {
		String costText = fmtEur(costCt)+" EUR";
		addButton(panel, new ActionButton(kind, false,
			() -> title,
			() -> detail,
			() -> costText,
			() -> unlocked.gBool() && moneyCt >= costCt,
			true,
			() -> false,
			() -> {
				moneyCt -= costCt;
				effect.run();
			}));
	}

	private void addButton(JPanel panel, ActionButton actionButton) {
		buttons.add(actionButton);
		panel.add(actionButton.button);
	}


	/*
	 * DISPLAY
	 */

	private void refreshAll() {
		boolean layoutChanged = false;
		for(StateTile tile : tiles) layoutChanged |= tile.refresh();
		for(StateLabel label : labels) layoutChanged |= label.refresh();
		for(ActionButton button : buttons) layoutChanged |= button.refresh();

		if(layoutChanged && frame!=null) frame.pack();
	}

	private static JPanel panelY() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		return panel;
	}

	private static JLabel label(String text, int size, int style, Color color) {
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(style, size));
		label.setForeground(color);
		return label;
	}

	private static void round(Graphics2D g2, Color fill, Color line, int width, int height) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(fill);
		g2.fillRoundRect(0, 0, width-1, height-1, ARC, ARC);
		g2.setColor(line);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(1, 1, width-3, height-3, ARC, ARC);
	}

	private static String hex(Color color) {
		return String.format("#%06X", color.getRGB() & 0xFFFFFF);
	}

	private static String fmtKg(long grams) {
		return String.format(Locale.FRANCE, "%.2f", grams/1000.0);
	}

	private static String fmtEur(long centimes) {
		return String.format(Locale.FRANCE, "%.2f", centimes/100.0);
	}


	/*
	 * COMPONENTS
	 */

	private class StateTile {

		private final JPanel container;
		private final JPanel panel;
		private final JLabel value;
		private final G<String> text;
		private final GBool visible;

		private StateTile(JPanel container, Kind kind, GBool visible, String name, G<String> text) {
			this.container = container;
			this.text = text;
			this.visible = visible;

			value = label("", 26, Font.BOLD, COCOA);
			value.setAlignmentX(Component.LEFT_ALIGNMENT);

			JLabel nameLabel = label(name, 12, Font.BOLD, MUTED);
			nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

			JPanel texts = panelY();
			texts.add(nameLabel);
			texts.add(value);

			panel = new RoundPanel(TILE_CREAM);
			panel.setLayout(new BorderLayout(12, 0));
			panel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
			panel.add(new JLabel(new ChocoIcon(kind, STATE_ICON)), BorderLayout.WEST);
			panel.add(texts, BorderLayout.CENTER);
		}

		private boolean refresh() {
			value.setText(text.g());

			boolean visibleNow = visible.gBool();
			boolean attached = panel.getParent()!=null;
			if(visibleNow==attached) return false;

			if(visibleNow) container.add(panel);
			else container.remove(panel);
			return true;
		}
	}

	private class StateLabel {

		private final JLabel label;
		private final G<List<String>> parts;

		private StateLabel(G<List<String>> parts) {
			this.parts = parts;

			label = label("", 14, Font.PLAIN, MUTED);
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
		}

		private boolean refresh() {
			List<String> values = parts.g();
			int heightBefore = label.getPreferredSize().height;
			if(!values.isEmpty()) label.setText(wrapped(values));

			boolean visibleNow = !values.isEmpty();
			boolean layoutChanged = visibleNow!=label.isVisible()
				|| label.getPreferredSize().height!=heightBefore;
			label.setVisible(visibleNow);
			return layoutChanged;
		}

		private String wrapped(List<String> values) {
			FontMetrics metrics = label.getFontMetrics(label.getFont());
			List<String> lines = new ArrayList<>();
			StringBuilder line = new StringBuilder();

			for(String value : values) {
				String candidate = line.length()==0 ? value : line+SEPARATOR+value;
				if(line.length()>0 && metrics.stringWidth(candidate)>ROW_WIDTH) {
					lines.add(line.toString());
					line = new StringBuilder(value);
				}
				else line = new StringBuilder(candidate);
			}
			lines.add(line.toString());

			return "<html><center>"+String.join("<br>", lines).replace(" ", "&nbsp;")+"</center></html>";
		}
	}

	private class ActionButton {

		private final TileButton button;
		private final G<String> title;
		private final G<String> detail;
		private final G<String> cost;
		private final GBool usable;
		private final GBool exhausted;
		private final Runnable effect;
		private final boolean once;
		private final boolean strong;
		private boolean everUsable;
		private boolean used;

		private ActionButton(Kind kind, boolean strong,
				G<String> title, G<String> detail, G<String> cost, GBool usable, boolean once, GBool exhausted, Runnable effect) {
			this.title = title;
			this.detail = detail;
			this.cost = cost;
			this.usable = usable;
			this.once = once;
			this.exhausted = exhausted;
			this.effect = effect;
			this.strong = strong;

			button = new TileButton(ROW_WIDTH, ROW_HEIGHT);
			button.setIcon(new ChocoIcon(kind, ROW_ICON));
			button.addActionListener(e -> perform());
		}

		private void perform() {
			if(used || !usable.gBool() || exhausted.gBool()) return;

			effect.run();
			if(once) used = true;
			refreshAll();
		}

		private boolean refresh() {
			boolean usableNow = usable.gBool();
			if(usableNow) everUsable = true;

			Color priceColor = !usableNow ? PRICE_OFF : strong ? PRICE_ON_DARK : PRICE_ON_LIGHT;
			button.setText("<html><b>"+title.g()+"</b>&nbsp;&nbsp;<small>"+detail.g()+"</small>"
				+"&nbsp;&nbsp;<font color='"+hex(priceColor)+"' size='4'><b>"+cost.g()+"</b></font></html>");
			if(!usableNow) button.colors(OFF_FILL, OFF_LINE, MUTED);
			else if(strong) button.colors(MILK, COCOA, CREAM);
			else button.colors(SOFT, LINE, COCOA);
			button.setCursor(Cursor.getPredefinedCursor(usableNow ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));

			boolean visibleNow = everUsable && !used && !exhausted.gBool();
			boolean layoutChanged = visibleNow!=button.isVisible();
			button.setVisible(visibleNow);
			return layoutChanged;
		}
	}

	private static class ActionScroll extends JScrollPane {

		private static final long serialVersionUID = 1L;

		private ActionScroll(Component view) {
			super(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
			setOpaque(false);
			getViewport().setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
			getVerticalScrollBar().setUnitIncrement(ROW_HEIGHT+ROW_GAP);
		}

		public Dimension getPreferredSize() {
			Insets insets = getInsets();
			int height = getViewport().getView().getPreferredSize().height;
			return new Dimension(CONTENT_WIDTH, Math.min(height, MAX_ROWS_HEIGHT)+insets.top+insets.bottom);
		}
	}

	private static class RoundPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private RoundPanel(Color fill) {
			setOpaque(false);
			setBackground(fill);
			setAlignmentX(Component.LEFT_ALIGNMENT);
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			round(g2, getBackground(), LINE, getWidth(), getHeight());
			g2.dispose();
			super.paintComponent(g);
		}
	}

	private static class TileButton extends JButton {

		private static final long serialVersionUID = 1L;

		private Color fill = SOFT;
		private Color line = LINE;

		private TileButton(int width, int height) {
			setOpaque(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setFocusPainted(false);
			setHorizontalAlignment(SwingConstants.LEFT);
			setIconTextGap(10);
			setBorder(BorderFactory.createEmptyBorder(3, 12, 3+ROW_GAP, 12));
			setAlignmentX(Component.CENTER_ALIGNMENT);

			Dimension size = new Dimension(width, height+ROW_GAP);
			setPreferredSize(size);
			setMaximumSize(size);
			setMinimumSize(size);
		}

		private void colors(Color fill, Color line, Color text) {
			this.fill = fill;
			this.line = line;
			setForeground(text);
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			round(g2, fill, line, getWidth(), getHeight()-ROW_GAP);
			g2.dispose();
			super.paintComponent(g);
		}
	}
}
