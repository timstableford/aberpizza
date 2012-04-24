package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
/**
 * Nothing special here same as drink
 * @author tim
 *
 */

public class ItemSide extends ItemSuper implements Cloneable{

	public ItemSide(BigDecimal price, String description) {
		super(price, description);
	}
	public ItemSide(){
		
	}

}
