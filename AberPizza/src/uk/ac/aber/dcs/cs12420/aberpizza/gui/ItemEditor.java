package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemDrink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * Displays a list of items and options for manipulating them
 * @author tim
 *
 */
public class ItemEditor extends JFrame implements ActionListener{
	private static final long serialVersionUID = 2839706788383407154L;
	private Till till;
	private JList itemList;
	private MainPanel mainPanel;
	public ItemEditor(Till t, MainPanel mP){
		till = t;
		mainPanel = mP;
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		this.add(mainPanel, BorderLayout.CENTER);
		this.setLocationRelativeTo(mainPanel);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt){
				quit();
			}
		});
		String[] listData = till.itemListToStringArray(till.getItems());
		String[] listBlank = {" "};
		if(till.getItems().size()<1){
			listData = listBlank;
		}
		itemList = new JList(listData);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane itemScroll = new JScrollPane(itemList);
		itemList.ensureIndexIsVisible(itemList.getSelectedIndex());
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		mainPanel.add(itemScroll, c);
		JButton add = new JButton("Add"), edit = new JButton("Edit"), delete = new JButton("Delete");
		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridy = 1;
		mainPanel.add(add,c);
		c.gridx = 1;
		mainPanel.add(edit,c);
		c.gridx = 2;
		mainPanel.add(delete,c);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if("Add".equals(actionCommand)){
			Object[] options = {"Pizza","Side","Drink"};
			String rVal = (String)JOptionPane.showInputDialog(this,
					"Which item type?", "Which item type?",
					JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if(rVal!=null&&rVal.length()>0){
				Item leItem;
				if(rVal.equals("Side")){
					leItem = new ItemSide();
				}else if(rVal.equals("Drink")){
					leItem = new ItemDrink();
				}else{
					leItem = new ItemPizza();
				}
				ItemValueEditor v = new ItemValueEditor(leItem,this,false);
				v.setVisible(true);
			}
		}else if("Edit".equals(actionCommand)){
			if(!itemList.isSelectionEmpty()){
				ItemValueEditor v = new ItemValueEditor(getItemFromList(),this,true);
				v.setVisible(true);
			}
		}else if("Delete".equals(actionCommand)){
			if(!itemList.isSelectionEmpty()){
				till.removeItem(getItemFromList());
				till.saveItems();
				updateItemList();
				mainPanel.updateItemList();
			}
		}
	}
	public Item getItemFromList(){
		String t = (String)itemList.getSelectedValue();
		String[] y = t.split("-");
		Item i = till.findItem(y[0].trim());
		return i;
	}
	public boolean addItem(Item i){
		if(till.addItem(i)){
			updateItemList();
			return true;
		}
		return false;
	}
	public void updateItemList(){
		String[] listData = till.itemListToStringArray(till.getItems());
		String[] listBlank = {" "};
		if(till.getItems().size()<1){
			listData = listBlank;
		}
		itemList.setListData(listData);
	}
	public void quit(){
		till.saveItems();
		mainPanel.updateItemList();
		this.dispose();
	}
}
