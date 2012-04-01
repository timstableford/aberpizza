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
	private final String xmlFileLocation = "/home/tim/etc/till.xml";
	private final String itemLocation = "/home/tim/etc/items.xml";
	public Till(){
		
	}
	public void load(){
		FileInputStream os = null;
		try {
			os = new FileInputStream(xmlFileLocation);
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
	public void loadItems(){
		FileInputStream os = null;
		try {
			os = new FileInputStream(itemLocation);
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
	public void save(){
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(xmlFileLocation);
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
	public void saveItems(){
		FileOutputStream os = null;
		File f = new File(itemLocation);
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
	public Order newOrder(){
		Order o = new Order();
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
}
