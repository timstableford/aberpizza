package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

public abstract class ItemSuper implements Item{
	protected BigDecimal price;
	protected String description;
	public ItemSuper(BigDecimal price, String description){
		this.price = price;
		this.description = description;
	}
	public BigDecimal getPrice() {
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
	
}
