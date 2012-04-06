package uk.ac.aber.dcs.cs12420.aberpizza.data.htmlgenerator;

import java.util.ArrayList;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;

public class Receipt {
	private Order order;
	public Receipt(Order o){
		order = o;
	}
	private ArrayList<String> getHeader(){
		ArrayList<String> rV = new ArrayList<String>();
		rV.add("<html>");
		rV.add("<head>");
		rV.add("<title>Aber Pizza</title>");
		rV.add("</head>");
		rV.add("<body>");
		rV.add("<h1>Aber Pizza</h1>");
		return rV;
	}
	private ArrayList<String> getBody(){
		ArrayList<String> rV = new ArrayList<String>();
		rV.add("<p>Date: "+order.getDate()+"</p>");
		rV.add("<p>Customer: "+order.getCustomerName()+"</p>");
		rV.add("<table border=\"1\">");
		rV.add("<tr>");
		rV.add("<th>Product</th><th>Unit Price</th><th>Quantity</th><th>Total Price</th>");
		rV.add("</tr>");
		for(OrderItem i: order.getOrderItems()){
			rV.add("<tr><td>"+i.getItem().getDescription()+"</td><td>£"+i.getItem().getPrice()+"</td><td>"+i.getQuantity()+"</td><td>£"+i.getOrderItemTotal()+"</td></tr>");
		}
		rV.add("</table>");
		rV.add("<p>");
		rV.add("Subtotal: £"+order.getSubtotal());
		rV.add("<br />Discounts: £"+order.getDiscount());
		rV.add("<br />VAT: £"+order.getVAT());
		rV.add("<br />Total: £"+order.getTotal());
		rV.add("</p>");
		return rV;
	}
	private ArrayList<String> getFooter(){
		ArrayList<String> rV = new ArrayList<String>();
		rV.add("</body>");
		rV.add("</html>");
		return rV;
	}
	public ArrayList<String> getReceipt(){
		ArrayList<String> rV = new ArrayList<String>();
		for(String s: getHeader()){
			rV.add(s);
		}
		for(String s: getBody()){
			rV.add(s);
		}
		for(String s: getFooter()){
			rV.add(s);
		}
		return rV;
	}
	
}
