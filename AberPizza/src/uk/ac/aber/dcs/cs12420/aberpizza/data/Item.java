package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Specifies what an item needs
 * @author tim
 *
 */
public interface Item {
	public BigDecimal getPrice();
	public void setPrice(BigDecimal price);
	public String getDescription();
	public String getOrderString();
	public void setDescription(String description);
	public boolean equals(Item i);
	public Object clone() throws CloneNotSupportedException;
}
