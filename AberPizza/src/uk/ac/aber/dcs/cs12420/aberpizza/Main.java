package uk.ac.aber.dcs.cs12420.aberpizza;

import java.io.File;
import java.io.IOException;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
import uk.ac.aber.dcs.cs12420.aberpizza.gui.MainFrame;

public class Main {
	/**
	 * @param args just the config file, if not creates in working directory
	 */
	public static void main(String[] args) {
		File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"config.xml");
		if(args.length>0){
			f = new File(args[0]);	
		}
		System.out.println("Using config file: "+f.toString());
		if(!f.exists()){
			System.out.println("Attempting to create config file");
			try {
				File h = new File(f.getParent());
				if(!h.exists()){
					h.mkdirs();
					System.out.println("Directories to file created");
				}
				f.createNewFile();
				System.out.println("config.xml created");
			} catch (IOException e) {
				System.err.println("Could not access config file");
				System.exit(0);
			}
		}
		Till t = new Till(f);
		t.loadItems();
		t.loadDiscounts();
		t.load();
		MainFrame m = new MainFrame(t);
		m.setVisible(true);
	}

}
