package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;


public class ItemDrink extends ItemSuper implements Cloneable{

	public ItemDrink(BigDecimal price, String description) {
		super(price, description);
	}
	public ItemDrink(){
		
	}

}
