package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemDrink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
/**
 * Edits the guts of an item
 * @author tim
 *
 */
public class ItemValueEditor extends JFrame implements ActionListener{
	private Item item;
	private ItemEditor itemEditor;
	private boolean isEditing;
	private JTextField descriptionField, priceField; 
	private static final long serialVersionUID = -722669284621653074L;
	public ItemValueEditor(Item i, ItemEditor ie, boolean editing){
		item = i;
		itemEditor = ie;
		isEditing = editing;
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		///////////////
		c.gridx = 0;
		c.gridy = 0;
		JLabel l1 = new JLabel("Item Type:");
		mainPanel.add(l1,c);
		c.gridx = 1;
		String itemType = "Pizza";
		if(item instanceof ItemSide){
			itemType = "Side";
		}else if(item instanceof ItemDrink){
			itemType = "Drink";
		}
		JLabel l2 = new JLabel(itemType);
		mainPanel.add(l2,c);
		////////////////
		c.gridy = 1;
		c.gridx = 0;
		JLabel l3 = new JLabel("Description");
		mainPanel.add(l3,c);
		c.gridx = 1;
		String descriptionInfo = "";
		if(item.getDescription()!=null&&item.getDescription().equals("")==false){
			descriptionInfo = item.getDescription();
		}
		descriptionField = new JTextField(descriptionInfo);
		mainPanel.add(descriptionField,c);
		//////////////
		c.gridy = 2;
		c.gridx = 0;
		JLabel l4 = new JLabel("Price");
		mainPanel.add(l4,c);
		c.gridx = 1;
		String priceInfo = "";
		if(item.getPrice()!=null){
			priceInfo = item.getPrice().toPlainString();
		}
		priceField = new JTextField(priceInfo);
		mainPanel.add(priceField,c);
		/////////////
		c.gridy = 3;
		c.gridx = 0;
		JButton done = new JButton("Done"), cancel = new JButton("Cancel");
		done.addActionListener(this);
		cancel.addActionListener(this);
		mainPanel.add(cancel,c);
		c.gridx = 1;
		mainPanel.add(done,c);
		////////////
		this.add(mainPanel,BorderLayout.CENTER);
		this.setAlwaysOnTop(true);
		this.setSize(300,140);
		this.setLocationRelativeTo(itemEditor);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String aC = arg0.getActionCommand();
		if("Done".equals(aC)){
			if(priceField.getText().equals("")){
				JOptionPane.showMessageDialog(this, "No Price Set", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				try{
					new BigDecimal(priceField.getText());
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this, "Invalid number", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			item.setPrice(new BigDecimal(priceField.getText()));
			if(descriptionField.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Description must not be empty", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			item.setDescription(descriptionField.getText());
			if(!isEditing&&!itemEditor.addItem(item)){
				JOptionPane.showMessageDialog(this, "Item exists", "Error", JOptionPane.WARNING_MESSAGE);
			}else{
				this.dispose();
			}
		}else if("Cancel".equals(aC)){
			this.dispose();
		}
	}
	
}
