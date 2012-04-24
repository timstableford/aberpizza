package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
/**
 * What an order is defined as
 * @author tim
 * Contains a date, if the customers paid, a list of items, vat percent
 * Also has the necessary yet unnecessary sets and gets for XML 
 */
public class Order {
	private GregorianCalendar date;
	private String customerName;
	private boolean hasPaid = false;
	private ArrayList<Discount> discounts;
	private BigDecimal vatPercent;
	private ArrayList<OrderItem> items = new ArrayList<OrderItem>();
	public Order(ArrayList<Discount> d, BigDecimal vat){
		date = new GregorianCalendar();
		discounts = d;
		vatPercent = vat;
	}
	public Order(){
		discounts = new ArrayList<Discount>();
	}
	public boolean isHasPaid() {
		return hasPaid;
	}
	public ArrayList<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(ArrayList<Discount> discounts) {
		this.discounts = discounts;
	}
	public void setHasPaid(boolean hasPaid) {
		this.hasPaid = hasPaid;
	}
	public ArrayList<OrderItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<OrderItem> items) {
		this.items = items;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * Adds the item if it isn't found, if it is increase quantity by 1
	 * @param item item to add to the order
	 * @param quantity quantity of the item to add
	 */
	public void addItem(Item item, int quantity){
		if(quantity<1){ return; }
		for(OrderItem i: items){
			if(i.getItem().equals(item)){
				i.setQuantity(i.getQuantity()+1);
				return;
			}
		}
		OrderItem o = new OrderItem(item, quantity);
		items.add(o);
	}
	/**
	 * Updates an items quantity by the change specified
	 * @param item item to affect
	 * @param quantityChange amount to change by ie -1 +1
	 */
	public void updateItemQuantity(Item item, int quantityChange){
		OrderItem oi = null;
		for(OrderItem i: items){
			if(i.getItem().equals(item)){
				oi = i;
			}
		}
		if(oi==null){ return; }
		oi.setQuantity(oi.getQuantity()+quantityChange);
		if(oi.getQuantity()<1){
			items.remove(oi);
		}
	}
	public ArrayList<OrderItem> getOrderItems(){
		return items;
	}
	/**
	 * @return item total before discounts and vat
	 */
	public BigDecimal getSubtotal(){
		BigDecimal returnVal = new BigDecimal("0.00");
		for(OrderItem i: items){
			returnVal = returnVal.add(i.getOrderItemTotal());
		}
		return returnVal;
	}
	/**
	 * @return big decimal of the best discount
	 */
	public BigDecimal getDiscount(){
		ArrayList<BigDecimal> discountsThatApply = new ArrayList<BigDecimal>();
		
		for(Discount d: discounts){
			if(d.discountApplies(this)){
				discountsThatApply.add(d.getDiscount(this));
			}
		}
		Collections.sort(discountsThatApply);
		if(discountsThatApply.size()<1){ return new BigDecimal("0.00"); }
		return discountsThatApply.get(discountsThatApply.size()-1);
	}
	public BigDecimal getVatPercent() {
		return vatPercent;
	}
	public void setVatPercent(BigDecimal vatPercent) {
		this.vatPercent = vatPercent;
	}
	public String getReceipt(){
		return "";
	}
	public GregorianCalendar getDate(){
		return date;
	}
	public void setDate(GregorianCalendar date){
		this.date = date;
	}
	public void printItems(){
		for(OrderItem i: items){
			System.out.println(i);
		}
	}
	/**
	 * @return subtotal minus discounts plus vat
	 */
	public BigDecimal getTotal(){
		BigDecimal returnVal = getSubtotal().subtract(getDiscount());
		returnVal = returnVal.add(returnVal.divide(new BigDecimal("100")).multiply(vatPercent));
		returnVal = returnVal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return returnVal;
	}
	/**
	 * @return an extra multiple of "vatPercent" of the order
	 */
	public BigDecimal getVAT(){
		BigDecimal returnVal = getSubtotal().subtract(getDiscount());
		returnVal = returnVal.divide(new BigDecimal("100")).multiply(vatPercent);
		returnVal = returnVal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return returnVal;
	}
	public void removeOrderItem(OrderItem o){
		items.remove(o);
	}
}
