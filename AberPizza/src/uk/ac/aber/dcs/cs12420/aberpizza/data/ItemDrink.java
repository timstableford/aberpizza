package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * Drink type item
 * @author tim
 * Nothing special here just instantiates the super
 */
public class ItemDrink extends ItemSuper implements Cloneable{

	public ItemDrink(BigDecimal price, String description) {
		super(price, description);
	}
	public ItemDrink(){
		
	}

}
