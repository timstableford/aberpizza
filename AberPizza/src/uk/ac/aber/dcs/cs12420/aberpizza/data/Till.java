package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
/**
 * Responsible for tracking orders, items and discounts
 * @author tim
 *
 */
public class Till {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Discount> discounts = new ArrayList<Discount>();
	private File itemLoc;
	private File discountLoc;
	private File historyLoc;
	private File configFile;
	private BigDecimal vatPercent = new BigDecimal("20");
	/**
	 * @param f Config file to use
	 */
	public Till(File f){
		configFile = f;
		readConfig();
	}
	/**
	 * Loads the order history
	 */
	public void load(){
		FileInputStream os = null;
		File f = historyLoc;
		if(!f.exists()){ return; }
		try {
			os = new FileInputStream(historyLoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        XMLDecoder decoder = new XMLDecoder(os);
        int i = 0;
        try{
        	String j = (String)decoder.readObject();
        	i = Integer.parseInt(j);
        }catch(NoSuchElementException e){
        	System.out.println("Blank history file");
        }
        try{
        for(int k = 0; k<i; k++){
        	Order o = (Order)decoder.readObject();
        	if(o.isHasPaid()){
        		orders.add(o);
        	}
        }
        }catch(ArrayIndexOutOfBoundsException e){
        	e.printStackTrace();
        }
        if(decoder!=null){
        	decoder.close();
        }
	}
	/**
	 * Loads the list of items
	 */
	public void loadItems(){
		FileInputStream os = null;
		File f = itemLoc;
		if(!f.exists()){ return; }
		try {
			os = new FileInputStream(itemLoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
        XMLDecoder decoder = new XMLDecoder(os);
        String j;
        try{
        	j = (String)decoder.readObject();
        }catch(NoSuchElementException e){
        	System.out.println("Blank items file");
        	return;
        }
        
        int i = Integer.parseInt(j);
        for(int k = 0; k<i; k++){
        	ItemSuper o = (ItemSuper)decoder.readObject();
        	addItem(o);
        }
        if(decoder!=null){
        	decoder.close();
        }
	}
	/**
	 * Loads the discounts
	 */
	public void loadDiscounts(){
		FileInputStream os = null;
		File f = discountLoc;
		if(!f.exists()){ return; }
		try {
			os = new FileInputStream(discountLoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
        XMLDecoder decoder = new XMLDecoder(os);
        String j;
        try{
        	j = (String)decoder.readObject();
        }catch(NoSuchElementException e){
        	System.out.println("Empty discounts file");
        	return;
        }
        int i = Integer.parseInt(j);
        for(int k = 0; k<i; k++){
        	Discount o = (Discount)decoder.readObject();
        	discounts.add(o);
        }
        if(decoder!=null){
        	decoder.close();
        }
	}
	/**
	 * Saves the history
	 */
	public void save(){
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(historyLoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XMLEncoder encoder = new XMLEncoder(os);
		PersistenceDelegate pd=encoder.getPersistenceDelegate(Integer.class);
		encoder.setPersistenceDelegate(BigDecimal.class,pd );
		int j = orders.size();
		for(Order o: orders){
			if(!o.isHasPaid()){
				j--;
			}
		}
		String i = j+"";
		encoder.writeObject(i);
		for(Order o: orders){
			if(o.isHasPaid()){
				encoder.writeObject(o);
			}
		}
		if(encoder!=null){
			encoder.close();
		}
	}
	/**
	 * Saves the items
	 */
	public void saveItems(){
		FileOutputStream os = null;
		File f = itemLoc;
		if(f.exists()){
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XMLEncoder encoder = new XMLEncoder(os);
		String i = new String(items.size()+"");
		PersistenceDelegate pd=encoder.getPersistenceDelegate(Integer.class);
		encoder.setPersistenceDelegate(BigDecimal.class,pd );
		encoder.writeObject(i);
		for(Item o: items){
			encoder.writeObject(o);
		}
		if(encoder!=null){
			encoder.close();
		}
	}
	/**
	 * Saves the discounts
	 */
	public void saveDiscounts(){
		FileOutputStream os = null;
		File f = discountLoc;
		if(f.exists()){
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XMLEncoder encoder = new XMLEncoder(os);
		String i = new String(discounts.size()+"");
		PersistenceDelegate pd=encoder.getPersistenceDelegate(Integer.class);
		encoder.setPersistenceDelegate(BigDecimal.class,pd );
		encoder.writeObject(i);
		for(Discount o: discounts){
			encoder.writeObject(o);
		}
		if(encoder!=null){
			encoder.close();
		}
	}
	/**
	 * Reads the config containing other xml file locations and vat percent
	 * It also creates default config values if a file doesn't exist then saves
	 */
	public void readConfig(){
		FileInputStream os = null;
		if(!configFile.exists()){ 
			System.err.println("Config file doesn't exist");
			System.exit(0);
		}
		try {
			os = new FileInputStream(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
        XMLDecoder decoder = new XMLDecoder(os);
        try{
        	//check/create items file
        	String j = (String)decoder.readObject();
        	itemLoc = new File(j);
        }catch(NoSuchElementException e){
        	itemLoc = new File(configFile.getParent()+System.getProperty("file.separator")+"items.xml");
        }catch(ArrayIndexOutOfBoundsException e){
        	itemLoc = new File(configFile.getParent()+System.getProperty("file.separator")+"items.xml");
        }
        try{
        	//check/create discount file
        	String j = (String)decoder.readObject();
        	discountLoc = new File(j);
        }catch(NoSuchElementException e){
        	discountLoc = new File(configFile.getParent()+System.getProperty("file.separator")+"discounts.xml");
        }catch(ArrayIndexOutOfBoundsException e){
        	discountLoc = new File(configFile.getParent()+System.getProperty("file.separator")+"discounts.xml");
        }
        try{
        	//check/create history file
        	String j = (String)decoder.readObject();
        	historyLoc = new File(j);
        }catch(NoSuchElementException e){
        	historyLoc = new File(configFile.getParent()+System.getProperty("file.separator")+"history.xml");
        }catch(ArrayIndexOutOfBoundsException e){
        	historyLoc = new File(configFile.getParent()+System.getProperty("file.separator")+"history.xml");
        }
        try{
        	//check/create history file
        	BigDecimal j = (BigDecimal)decoder.readObject();
        	vatPercent = j;
        }catch(NoSuchElementException e){
        	vatPercent = new BigDecimal("20");
        }catch(ArrayIndexOutOfBoundsException e){
        	vatPercent = new BigDecimal("20");
        }catch(NumberFormatException e){
        	vatPercent = new BigDecimal("20");
        }
        /////////create the above files if they dont exist///////
        //items
        createFileAndDirs(itemLoc);
    	//discounts
        createFileAndDirs(discountLoc);
        //history
        createFileAndDirs(historyLoc);
        if(decoder!=null){
        	decoder.close();
        	saveConfig();
        }
	}
	/**
	 * Creates directories recursively and the file specified
	 * @param f file and its parent folders to create
	 */
	private void createFileAndDirs(File f){
		 File h = new File(f.getParent());
	    	if(!h.exists()){
	    		h.mkdirs();
	    	}
	    	if(!f.exists()){
	    		try {
					f.createNewFile();
				} catch (IOException e) {
					System.err.println("Could not create item file");
					System.exit(0);
				}
	    	}
	}
	/**
	 * Saves the config
	 */
	public void saveConfig(){
		FileOutputStream os = null;
		if(configFile.exists()){
			configFile.delete();
		}
		try {
			configFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			os = new FileOutputStream(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XMLEncoder encoder = new XMLEncoder(os);
		String i = itemLoc.toString();
		encoder.writeObject(i);
		i = discountLoc.toString();
		encoder.writeObject(i);
		i = historyLoc.toString();
		encoder.writeObject(i);
		PersistenceDelegate pd=encoder.getPersistenceDelegate(Integer.class);
		encoder.setPersistenceDelegate(BigDecimal.class,pd );
		encoder.writeObject(vatPercent);
		if(encoder!=null){
			encoder.close();
		}
	}
	/**
	 * Create a new order object
	 * @return the new order object
	 */
	public Order newOrder(){
		Order o = new Order(discounts, vatPercent);
		orders.add(o);
		return o;
	}
	/**
	 * @param i item to add to the item list
	 * @return true if added successfully
	 */
	public boolean addItem(Item i){
		for(Item it: items){
			if(it.equals(i)){
				return false;		
			}
		}
		items.add(i);
		return true;
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	/**
	 * @return only pizza items
	 */
	public ArrayList<Item> getPizza(){
		ArrayList<Item> p = new ArrayList<Item>();
		for(Item i: items){
			if(i instanceof ItemPizza){
				p.add(i);
			}
		}
		return p;
	}
	/**
	 * @return only sides
	 */
	public ArrayList<Item> getSide(){
		ArrayList<Item> p = new ArrayList<Item>();
		for(Item i: items){
			if(i instanceof ItemSide){
				p.add(i);
			}
		}
		return p;
	}
	/**
	 * @return only drinks
	 */
	public ArrayList<Item> getDrink(){
		ArrayList<Item> p = new ArrayList<Item>();
		for(Item i: items){
			if(i instanceof ItemDrink){
				p.add(i);
			}
		}
		return p;
	}
	/**
	 * @return the order currently being worked on or a new one
	 */
	public Order getCurrentOrder(){
		if(orders.size()<1){ 
			newOrder();
		}
		Order o = orders.get(orders.size()-1);
		if(o.isHasPaid()){
			o = newOrder();
		}
		return o;
	}
	/**
	 * @return the order that was last paid for
	 */
	public Order getLastPaidOrder(){
		Order returnVal = null;
		for(Order o: orders){
			if(o.isHasPaid()){
				returnVal = o;
			}
		}
		return returnVal;
	}
	public ArrayList<Discount> getDiscounts(){
		return discounts;
	}
	/**
	 * @param d discount to add
	 * @return true is successful
	 */
	public boolean addDiscount(Discount d){
		if(hasDiscount(d)){
			return false;
		}
		discounts.add(d);
		return true;
	}
	public boolean hasDiscount(Discount d){
		for(Discount i: discounts){
			if(d.equals(i)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Find discount by description
	 * @param description description of discount to find
	 * @return the discount or null
	 */
	public Discount findDiscount(String description){
		for(Discount d: discounts){
			if(d.getDescription().equals(description)){
				return d;
			}
		}
		return null;
	}
	public void removeDiscount(Discount d){
		if(hasDiscount(d)){
			discounts.remove(d);
		}
	}
	public void removeMostRecentOrder(){
		if(getCurrentOrder()==null){ return; }
		orders.remove(getCurrentOrder());
	}
	public void pay(){
		getCurrentOrder().setHasPaid(true);
	}
	/**
	 * @param start start date for selecting orders
	 * @param end end date for selecting orders
	 * @return All orders within the specified dates
	 */
	public ArrayList<Order> displayOrdersWithinDates(GregorianCalendar start, GregorianCalendar end){
		ArrayList<Order> returnOrders = new ArrayList<Order>();
		for(Order o: orders){
			if(o.getDate().after(start)&&o.getDate().before(end)&&o.isHasPaid()){
				returnOrders.add(o);
			}
		}
		return returnOrders;
	}
	/**
	 * Finds an item by name and returns a clone of it
	 * @param n item to find
	 * @return the items clone or null
	 */
	public Item findItem(String n){
		for(Item i: items){
			if(i.getDescription().equals(n)){
				try {
					return (Item)i.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	public void removeItem(Item i){
		Item toRemove = null;
		for(Item j: items){
			if(j.equals(i)){
				toRemove = j;
			}
		}
		if(toRemove!=null){
			items.remove(toRemove);
		}
	}
	/**
	 * Converts the list of items to a string array
	 * @param i arraylist to convert
	 * @return returns descriptions of all items
	 */
	public String[] itemListToStringArray(ArrayList<Item> i){
		if(i.size()<1){ return null; }
		String[] returnVal = new String[i.size()];
		for(int j = 0; j<i.size(); j++){
			returnVal[j] = i.get(j).toString();
		}
		return returnVal;
	}
	/**
	 * converts the list of discounts into a string array
	 * @param i arraylist to convert
	 * @return descriptions of all items
	 */
	public String[] discountListToStringArray(ArrayList<Discount> i){
		if(i.size()<1){ return null; }
		String[] returnVal = new String[i.size()];
		for(int j = 0; j<i.size(); j++){
			returnVal[j] = i.get(j).toString();
		}
		return returnVal;
	}
}
