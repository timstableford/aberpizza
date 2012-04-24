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

import uk.ac.aber.dcs.cs12420.aberpizza.data.Discount;
import uk.ac.aber.dcs.cs12420.aberpizza.data.DiscountSetPercent;
import uk.ac.aber.dcs.cs12420.aberpizza.data.DiscountSetValue;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * Displays a list of discounts and options for manipulating them
 * @author tim
 *
 */
public class DiscountEditor extends JFrame implements ActionListener{
	private static final long serialVersionUID = 2839706788383407154L;
	private Till till;
	private JList discountList;
	public DiscountEditor(Till t){
		till = t;
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
		String[] listData = till.discountListToStringArray(till.getDiscounts());
		String[] listBlank = {" "};
		if(till.getDiscounts().size()<1){
			listData = listBlank;
		}
		discountList = new JList(listData);
		discountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane discountScroll = new JScrollPane(discountList);
		discountList.ensureIndexIsVisible(discountList.getSelectedIndex());
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		mainPanel.add(discountScroll, c);
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
			Object[] options = {"Set value","Percent"};
			String rVal = (String)JOptionPane.showInputDialog(this,
					"Which discount type?", "Which discount type?",
					JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if(rVal!=null&&rVal.length()>0){
				Discount leDiscount = null;
				if(rVal.equals("Percent")){
					leDiscount = new DiscountSetPercent();
				}else if(rVal.equals("Set value")){
					leDiscount = new DiscountSetValue();
				}
				
				DiscountValueEditor v = new DiscountValueEditor(leDiscount,this,false,till);
				v.setVisible(true);
			}
		}else if("Edit".equals(actionCommand)){
			if(!discountList.isSelectionEmpty()){
				DiscountValueEditor v = new DiscountValueEditor(getDiscountFromList(),this,true,till);
				v.setVisible(true);
			}
		}else if("Delete".equals(actionCommand)){
			if(!discountList.isSelectionEmpty()){
				till.removeDiscount(getDiscountFromList());
				till.saveDiscounts();
				updateDiscountList();
			}
		}
	}
	
	public Discount getDiscountFromList(){
		String t = (String)discountList.getSelectedValue();
		String[] y = t.split("-");
		Discount i = till.findDiscount(y[0].trim());
		return i;
	}
	
	public boolean addDiscount(Discount i){
		if(till.addDiscount(i)){
			updateDiscountList();
			return true;
		}
		return false;
	}
	public void updateDiscountList(){
		String[] listData = till.discountListToStringArray(till.getDiscounts());
		String[] listBlank = {" "};
		if(till.getDiscounts().size()<1){
			listData = listBlank;
		}
		discountList.setListData(listData);
	}
	/**
	 * Saves and destroys
	 */
	public void quit(){
		till.saveDiscounts();
		this.dispose();
	}
}
