package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * Simply displays html from a file using a jtextpane
 * @author tim
 *
 */
public class Viewer extends JFrame implements ActionListener{
	private static final long serialVersionUID = -3963181724601268727L;
	private File htmlFile;
	private JTextPane pane;
	public Viewer(File f){
		htmlFile = f;
		this.setSize(new Dimension(800,600));
		this.setTitle("View Receipt");
		pane = new JTextPane();
		pane.setContentType("text/html");
		String text = "";
		try {
			Scanner scanner = new Scanner(new FileInputStream(htmlFile));
			while(scanner.hasNextLine()){
				text=text+(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pane.setText(text);
		JScrollPane scroll = new JScrollPane(pane);
		this.add(scroll, BorderLayout.CENTER);
		JButton printButton = new JButton("Print");
		printButton.addActionListener(this);
		this.add(printButton, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			pane.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
		
	}
}
