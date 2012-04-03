package uk.ac.aber.dcs.cs12420.aberpizza;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
import uk.ac.aber.dcs.cs12420.gui.MainFrame;

public class Main {
	private static final String dir = "/home/tim/etc/";
	private static final String itemloc = "items.xml";
	private static final String disloc = "discounts.xml";
	private static final String histloc = "history.xml";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Till t = new Till();
		t.loadItems(dir+itemloc);
		t.loadDiscounts(dir+disloc);
		t.load(histloc);
		MainFrame m = new MainFrame(t);
		m.setVisible(true);
	}

}
