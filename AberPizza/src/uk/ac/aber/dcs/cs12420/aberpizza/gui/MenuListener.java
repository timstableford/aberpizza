package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JFileChooser;

import uk.ac.aber.dcs.cs12420.aberpizza.data.OrdersToHtml;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * Listens to the menu
 * @author tim
 *
 */
public class MenuListener implements ActionListener{
	private MainPanel mainPanel;
	private Till till;
	public MenuListener(MainPanel mP, Till t){
		mainPanel = mP;
		till = t;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if("Current Receipt".equals(actionCommand)){
			till.getCurrentOrder().setCustomerName(mainPanel.getCustomerName());
			OrdersToHtml r = new OrdersToHtml(till.getCurrentOrder());
			File f;
			try {
				f = File.createTempFile("cur_receipt", "html");
				r.writeToFile(f, false);
				new Viewer(f);
			} catch (IOException e) {
				System.err.println("Could not create temp html file");
			}
		}else if("Last Receipt".equals(actionCommand)){
			Order lastPaid = till.getLastPaidOrder();
			if(lastPaid==null){ return; }
			OrdersToHtml r = new OrdersToHtml(lastPaid);
			File f;
			try {
				f = File.createTempFile("last_receipt", "html");
				r.writeToFile(f, false);
				new Viewer(f);
			} catch (IOException e) {
				System.err.println("Could not create temp html file");
			}
		}else if("View Todays Orders".equals(actionCommand)){
			GregorianCalendar now = new GregorianCalendar();
			GregorianCalendar yesterday = new GregorianCalendar();
			yesterday.set(GregorianCalendar.HOUR_OF_DAY, 0);
			yesterday.set(GregorianCalendar.MINUTE, 0);
			ArrayList<Order> orders = till.displayOrdersWithinDates(yesterday, now);
			OrdersToHtml disp = new OrdersToHtml(orders);
			File dispPlace;
			try {
				dispPlace = File.createTempFile("todays_orders", "html");
				disp.writeToFile(dispPlace, false);
				new Viewer(dispPlace);
			} catch (IOException e) {
				System.err.println("Could not create temp html file");
			}
			
		}else if("Items".equals(actionCommand)){
			ItemEditor i = new ItemEditor(till, mainPanel);
			i.setVisible(true);
		}else if("Discounts".equals(actionCommand)){
			DiscountEditor d = new DiscountEditor(till);
			d.setVisible(true);
		}else if("Export Orders".equals(actionCommand)){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = chooser.showSaveDialog(mainPanel);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				GregorianCalendar start = new GregorianCalendar();
				start.set(GregorianCalendar.YEAR,0);
				GregorianCalendar end = new GregorianCalendar();
				OrdersToHtml d = new OrdersToHtml(till.displayOrdersWithinDates(start, end));
				d.writeToFile(file, true);
			}
		}else if("Quit".equals(actionCommand)){
			till.save();
			System.exit(0);
		}
		
	}
}
