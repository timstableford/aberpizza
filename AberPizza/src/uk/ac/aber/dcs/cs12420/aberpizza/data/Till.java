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

public class Till {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<DiscountSuper> discounts = new ArrayList<DiscountSuper>();
	public Till(){
		
	}
	public void load(String dir){
		FileInputStream os = null;
		File f = new File(dir);
		if(!f.exists()){ return; }
		try {
			os = new FileInputStream(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        XMLDecoder decoder = new XMLDecoder(os);
        String j = (String)decoder.readObject();
        int i = Integer.parseInt(j);
        for(int k = 0; k<i; k++){
        	Order o = (Order)decoder.readObject();
        	orders.add(o);
        }
        if(decoder!=null){
        	decoder.close();
        }
	}
	public void loadItems(String dir){
		FileInputStream os = null;
		File f = new File(dir);
		if(!f.exists()){ return; }
		try {
			os = new FileInputStream(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        XMLDecoder decoder = new XMLDecoder(os);
        String j = (String)decoder.readObject();
        int i = Integer.parseInt(j);
        for(int k = 0; k<i; k++){
        	ItemSuper o = (ItemSuper)decoder.readObject();
        	addItem(o);
        }
        if(decoder!=null){
        	decoder.close();
        }
	}
	public void loadDiscounts(String dir){
		FileInputStream os = null;
		File f = new File(dir);
		if(!f.exists()){ return; }
		try {
			os = new FileInputStream(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
        XMLDecoder decoder = new XMLDecoder(os);
        String j = (String)decoder.readObject();
        int i = Integer.parseInt(j);
        for(int k = 0; k<i; k++){
        	DiscountSuper o = (DiscountSuper)decoder.readObject();
        	discounts.add(o);
        }
        if(decoder!=null){
        	decoder.close();
        }
	}
	public void save(String dir){
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XMLEncoder encoder = new XMLEncoder(os);
		String i = new String(orders.size()+"");
		encoder.writeObject(i);
		for(Order o: orders){
			encoder.writeObject(o);
		}
		if(encoder!=null){
			encoder.close();
		}
	}
	public void saveItems(String dir){
		FileOutputStream os = null;
		File f = new File(dir);
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
	public void saveDiscounts(String dir){
		FileOutputStream os = null;
		File f = new File(dir);
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
	public Order newOrder(){
		Order o = new Order(this);
		orders.add(o);
		return o;
	}
	public void addItem(Item i){
		items.add(i);
	}
	public void printItems(){
		for(Item i: items){
			System.out.println(i.toString());
		}
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	public ArrayList<Item> getPizza(){
		ArrayList<Item> p = new ArrayList<Item>();
		for(Item i: items){
			if(i instanceof ItemPizza){
				p.add(i);
			}
		}
		return p;
	}
	public ArrayList<Item> getSide(){
		ArrayList<Item> p = new ArrayList<Item>();
		for(Item i: items){
			if(i instanceof ItemSide){
				p.add(i);
			}
		}
		return p;
	}
	public ArrayList<Item> getDrink(){
		ArrayList<Item> p = new ArrayList<Item>();
		for(Item i: items){
			if(i instanceof ItemDrink){
				p.add(i);
			}
		}
		return p;
	}
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
	public ArrayList<DiscountSuper> getDiscounts(){
		return discounts;
	}
	public void addDiscount(DiscountSuper d){
		discounts.add(d);
	}
	public void removeMostRecentOrder(){
		if(getCurrentOrder()==null){ return; }
		orders.remove(getCurrentOrder());
	}
	public void pay(){
		getCurrentOrder().setHasPaid(true);
	}
}
