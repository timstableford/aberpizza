package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Turns orders into html
 * @author tim
 * Displays orders as html and allows writing to a file
 */
public class OrdersToHtml {
	private ArrayList<Order> orders;
	/**
	 * For multiple orders
	 * @param o arraylist of orders to display
	 */
	public OrdersToHtml(ArrayList<Order> o){
		orders = o;
	}
	/**
	 * To display one order
	 * @param o order to display
	 */
	public OrdersToHtml(Order o){
		orders = new ArrayList<Order>();
		orders.add(o);
	}
	/**
	 * @param meta does it want to have metadata included speicfying utf-8 encoding
	 * @return arraylist of strings each line being a different line of html
	 */
	private ArrayList<String> getHeader(boolean meta){
		ArrayList<String> rV = new ArrayList<String>();
		rV.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n\"http://www.w3.org/TR/html4/strict.dtd\">");
		rV.add("<html>");
		rV.add("<head>");
		if(meta){ rV.add("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"); }
		rV.add("<title>Aber Pizza</title>");
		rV.add("</head>");
		rV.add("<body>");
		rV.add("<h1>Aber Pizza</h1>");
		rV.add("<h2>Number of orders: "+orders.size());
		rV.add("<h2>Total Taken: £"+totalAmountTaken());
		return rV;
	}
	/**
	 * @param order to generate the viewable html from
	 * @return arraylist of string of html
	 */
	private ArrayList<String> getBody(Order order){
		ArrayList<String> rV = new ArrayList<String>();
		rV.add("<div>");
		rV.add("<p>Date: "+formatCalendar(order.getDate())+"</p>");
		rV.add("<p>Customer: "+order.getCustomerName()+"</p>");
		rV.add("<table border=\"1\">");
		rV.add("<tr>");
		rV.add("<th>Product</th><th>Unit Price</th><th>Quantity</th><th>Total Price</th>");
		rV.add("</tr>");
		for(OrderItem i: order.getOrderItems()){
			rV.add("<tr><td>"+i.getItem().getOrderString()+"</td><td>£"+i.getItem().getPrice()+"</td><td>"+i.getQuantity()+"</td><td>£"+i.getOrderItemTotal()+"</td></tr>");
		}
		rV.add("</table>");
		rV.add("<p>");
		rV.add("Subtotal: £"+order.getSubtotal());
		rV.add("<br />Discounts: £"+order.getDiscount());
		rV.add("<br />VAT: £"+order.getVAT());
		rV.add("<br />Total: £"+order.getTotal());
		rV.add("</p>");
		rV.add("</div>");
		return rV;
	}
	/**
	 * @return just a finish to the html
	 */
	private ArrayList<String> getFooter(){
		ArrayList<String> rV = new ArrayList<String>();
		rV.add("</body>");
		rV.add("</html>");
		return rV;
	}
	/**
	 * This throws them all together into one list
	 * @param meta do you want meta data specifying utf-8
	 * @return arraylist of html
	 * It gets the header and then for each of the orders the html for them, and finally the footer
	 */
	public ArrayList<String> getAll(boolean meta){
		ArrayList<String> rV = new ArrayList<String>();
		for(String s: getHeader(meta)){
			rV.add(s);
		}
		for(Order o: orders){
			if(o.getCustomerName()!=null){
				for(String s: getBody(o)){
					rV.add(s);
				}
			}
		}
		for(String s: getFooter()){
			rV.add(s);
		}
		return rV;
	}
	/**
	 * Writes the orders to file
	 * @param file file to write to
	 * @param meta meta for utf-8?
	 * @return boolean of success
	 */
	public boolean writeToFile(File file, boolean meta){
		FileWriter fstream = null;
		BufferedWriter out = null;
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(!file.canWrite()){
			return false;
		}
		try {
			fstream = new FileWriter(file);
			out = new BufferedWriter(fstream);
			for(String s: getAll(meta)){
				out.write(s+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * @param g gregorian calendar to format
	 * @return date and time how i wanted them formatted
	 */
	public String formatCalendar(GregorianCalendar g){
		return g.get(GregorianCalendar.HOUR_OF_DAY)+":"
	+g.get(GregorianCalendar.MINUTE)+" "
	+g.get(GregorianCalendar.DAY_OF_MONTH)+"/"
	+g.get(GregorianCalendar.MONTH)+"/"
	+g.get(GregorianCalendar.YEAR);
	}
	/**
	 * @return total for all the orders given
	 */
	public BigDecimal totalAmountTaken(){
		BigDecimal returnVal = new BigDecimal("0.00");
		for(Order o: orders){
			returnVal = returnVal.add(o.getTotal());
		}
		return returnVal;
	}
}
