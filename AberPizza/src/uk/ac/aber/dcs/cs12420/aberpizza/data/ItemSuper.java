package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Superclass for drink, side and pizza
 * @author tim
 * Contains the descriptions and price
 */
public abstract class ItemSuper implements Item, Cloneable{
	protected transient BigDecimal price = new BigDecimal("0.00");;
	protected String description;
	public ItemSuper(BigDecimal price, String description){
		this.price = price;
		this.description = description;
	}
	public BigDecimal getPrice() {
		price = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString(){
		price = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return description+" - £"+price;
	}
	public ItemSuper(){
		
	}
	/**
	 * Necessary due to XML >_<
	 * (I would have just used the getDescription() method
	 */
	public String getOrderString(){
		return description;
	}
	/**
	 * Checks if the item descriptions are equal
	 */
	public boolean equals(Item i){
		if(i.getDescription().equals(description)){
			return true;
		}else{
			return false;
		}
	}
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
}
