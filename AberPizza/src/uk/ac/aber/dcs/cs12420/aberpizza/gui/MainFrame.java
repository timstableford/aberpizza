package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * The main jframe which creates the menu and main panel
 * @author tim
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 4076991635240155077L;
	private Till till = null;
	private JMenuBar menubar;
	private MainPanel mainPanel;
	private MenuListener menuListener;;
	public MainFrame(Till t){
		till = t;
		mainPanel = new MainPanel(till);
		menuListener = new MenuListener(mainPanel, till);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				till.save();
				System.exit(0);
			}
		});
		this.setTitle("AberPizza Till");
		//menu bar
		JMenu file = new JMenu("File"),admin = new JMenu("Admin");
		menubar = new JMenuBar();
		////////file////////////
		menubar.add(file);
		JMenuItem exportAllOrders = new JMenuItem("Export Orders");
		exportAllOrders.addActionListener(menuListener);
		file.add(exportAllOrders);
		JMenuItem quitItem = new JMenuItem("Quit");
		file.add(quitItem);
		quitItem.addActionListener(menuListener);
		////////admin//////////////
		menubar.add(admin);
		JMenuItem itemEditor = new JMenuItem("Items");
		admin.add(itemEditor);
		itemEditor.addActionListener(menuListener);
		JMenuItem discountEditor = new JMenuItem("Discounts");
		discountEditor.addActionListener(menuListener);
		admin.add(discountEditor);
		//////receipts///////////////
		JMenu orderMenu = new JMenu("Receipts");
		JMenuItem curRec = new JMenuItem("Current Receipt"), lastRec = new JMenuItem("Last Receipt"), todaysOrders = new JMenuItem("View Todays Orders");
		menubar.add(orderMenu);
		orderMenu.add(curRec);
		orderMenu.add(lastRec);
		orderMenu.add(todaysOrders);
		curRec.addActionListener(menuListener);
		lastRec.addActionListener(menuListener);
		todaysOrders.addActionListener(menuListener);
		////////help/////////////
		//JMenu help = new JMenu("Help");
		//menubar.add(help);
		this.add(menubar, BorderLayout.NORTH);
		//main panel
		this.add(mainPanel, BorderLayout.CENTER);
		mainPanel.updateItemList();
		this.pack();
	}
}
