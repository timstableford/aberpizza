package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;


public class ItemSide extends ItemSuper implements Cloneable{

	public ItemSide(BigDecimal price, String description) {
		super(price, description);
	}
	public ItemSide(){
		
	}

}
