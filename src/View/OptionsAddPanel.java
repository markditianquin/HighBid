package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import Model.Donor;
import Model.Item;

/**
 * Add JTabbedPane. This class contains components
 * and methods that are used
 * to add an item or a donor.
 * 
 * @author Long Nguyen
 * @version 6/3/2015
 */
@SuppressWarnings("serial")
public class OptionsAddPanel extends OptionsContext {
	
	/*************** add donor panel's components **************/
	/**
	 * Add donor's panel
	 */
	private JPanel _donorPanel;
	
	/**
	 * Add donor's container (null layout)
	 */
	private JPanel _DContainer;
	
	/**
	 * first name text field
	 */
	private JTextField _firstTF;
	
	/**
	 * Last name text field
	 */
	private JTextField _lastTF;
	
	/**
	 * email text field
	 */
	private JTextField _emailTF;
	
	/**
	 * address text field
	 */
	private JTextField _addressTF;
	
	/**
	 * phone text field
	 */
	private JTextField _phoneTF;
	
	/**
	 * Add donor button
	 */
	private JButton _donorBtn;
	
	
	/*************** add item panel's components ***************/
	/**
	 * add item's panel
	 */
	private JPanel _itemPanel;
	
	/**
	 * add item's container (null layout)
	 */
	private JPanel _IContainer;
	
	/**
	 * Item name text field
	 */
	private JTextField _itemNameTF;
	
	/**
	 * item description text field
	 */
	private JTextField _ItemDescriptionTF;
	
	/**
	 * Starting price text field
	 */
	private JTextField _startPriceTF;
	
	/**
	 * Minimum increment text field
	 */
	private JTextField _minIncrementTF;
	
	/**
	 * QR text field
	 */
	private JTextField _qrTF;
	
	/**
	 * Add item button
	 */
	private JButton _itemBtn; 
	
	/**
	 * A combo box for donors
	 */
	private JComboBox<String> _combo;
	
	/**
	 * Upload button
	 */
	private JButton _upload;
	
	/**
	 * image for item
	 */
	private ImageIcon _image;
	
	public OptionsAddPanel() {
		setAddPanel();	
	}
	
	/**
	 * Setting up add tab for adding donor/item
	 */
	private void setAddPanel() {
		setAddDonorPanel();
		setAddItemPanel();
		this.add("Add Donor", _DContainer);
		this.add("Add Item", _IContainer);
		OptionsMain.donorTF = new JTextField[]{_firstTF, _lastTF, _emailTF, _addressTF, _phoneTF};
		OptionsMain.itemTF = new JTextField[]{_itemNameTF, _ItemDescriptionTF, _startPriceTF, _minIncrementTF, _qrTF};
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK), "F key");
        getActionMap().put("F key", keyAction);
	}

	/**
	 * Set up add donor tab's form
	 */
	private void setAddDonorPanel() {
		_DContainer = new JPanel();
		_DContainer.setLayout(null);
		JLabel donorLabel = new JLabel("Donor Form");
		donorLabel.setFont(new Font("Tahoma", 0, 50));
		donorLabel.setBounds(370,30,500,100);
		donorLabel.setForeground(Color.BLUE);
		
		_donorPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(20,0,20,0);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		
		// Labels for donor form
		String[] labelName = {"First Name: ", "Last Name: "
				, "Email: ", "Address: ", "Phone: "};		
		for(int i = 0; i < labelName.length; i++) {
			JLabel temp = new JLabel(labelName[i]);
			temp.setFont(MainFrame.FORM_LABEL_FONT);
			_donorPanel.add(temp, gc);
			gc.gridy++;
		}

		gc.gridy = 0;
		gc.gridx = 1;
		
		// Text fields for donor form
		_firstTF = new JTextField(20);
		_lastTF = new JTextField(20);
		_emailTF = new JTextField(20);
		_addressTF = new JTextField(20);
		_phoneTF = new JTextField(20);
		JTextField[] temp = {_firstTF, _lastTF, _emailTF, _addressTF, _phoneTF};
		for(int i = 0; i < temp.length; i++) {
			temp[i].setPreferredSize(MainFrame.TF_DIMENSION);
			temp[i].setFont(MainFrame.FORM_TF_FONT);
			_donorPanel.add(temp[i],gc);
			gc.gridy++;
		}
		
		gc.gridy++;
		_donorBtn = new JButton("Add");
		_donorBtn.setPreferredSize(new Dimension(100,60));
		_donorBtn.setFont(OptionsMain._smallFont);
		_donorBtn.addActionListener(this);
		_donorPanel.add(_donorBtn, gc);
		
		_donorPanel.setBounds(250,120,500,500);
		
		OptionsMain._infoLabel = new JLabel();
		OptionsMain._infoLabel.setBounds(350,600,800,100);
		OptionsMain._infoLabel.setForeground(OptionsMain._labelColor);
		OptionsMain._infoLabel.setFont(OptionsMain._smallFont);
		
		_DContainer.add(donorLabel);
		_DContainer.add(_donorPanel);
		_DContainer.add(OptionsMain._infoLabel);
		
	}

	/**
	 * Set up add item tab's form
	 */
	private void setAddItemPanel() {
		
		JLabel itemLabel = new JLabel("Item Form");
		itemLabel.setFont(new Font("Tahoma", 0, 50));
		itemLabel.setBounds(370,30,500,100);
		itemLabel.setForeground(Color.BLUE);
		
		_itemPanel = new JPanel(new GridBagLayout());
		_IContainer = new JPanel();
		_IContainer.setLayout(null);
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10,0,10,0);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		
		// labels for item form
		String[] labelName = {"Choose a donor (Optional)", "Item Name: "
				, "Description: ", "Minimum Increment: "
				, "Starting Price: ", "Item's QR: ", "Upload Image (Optional)"};		
		for(int i = 0; i < labelName.length; i++) {
			JLabel temp = new JLabel(labelName[i]);
			temp.setFont(MainFrame.FORM_LABEL_FONT);
			_itemPanel.add(temp, gc);
			gc.gridy++;
		}
				
		gc.gridy = 0;
		gc.gridx = 1;
		
		_combo = new JComboBox<String>();
		_combo.setModel(OptionsMain._comboModel);
		OptionsMain._comboModel.addElement("");
		_combo.setPrototypeDisplayValue("    this controls combobox's width    ");
		_itemPanel.add(_combo,gc);

		// text fields for item form
		_itemNameTF = new JTextField(20);
		_ItemDescriptionTF = new JTextField(20);
		_minIncrementTF = new JTextField(20);
		_startPriceTF = new JTextField(20);
		_qrTF = new JTextField(20);
		JTextField[] temp = {_itemNameTF, _ItemDescriptionTF, _minIncrementTF, _startPriceTF, _qrTF};
		for(int i = 0; i < temp.length; i++) {
			temp[i].setPreferredSize(MainFrame.TF_DIMENSION);
			temp[i].setFont(MainFrame.FORM_TF_FONT);
			gc.gridy++;
			_itemPanel.add(temp[i], gc);
		}
	
		gc.gridy++;
		_upload = new JButton("Upload Image");
		_upload.addActionListener(this);
		_itemPanel.add(_upload, gc);
		
		gc.gridy++;
		_itemBtn = new JButton("Add");
		_itemBtn.setPreferredSize(new Dimension(100,60));
		_itemBtn.setFont(OptionsMain._smallFont);
		_itemBtn.addActionListener(this);
		_itemPanel.add(_itemBtn, gc);
		
		_itemPanel.setBounds(10,110,900,550);
		
		OptionsMain._IInfo = new JLabel();
		OptionsMain._IInfo.setBounds(220,650,800,65);
		OptionsMain._IInfo.setForeground(OptionsMain._labelColor);
		OptionsMain._IInfo.setFont(OptionsMain._smallFont);
		
		_IContainer.add(_itemPanel);
		_IContainer.add(itemLabel);
		_IContainer.add(OptionsMain._IInfo);
		
	}

	public void actionPerformed(ActionEvent e) {
		
		JButton src = (JButton) e.getSource();
		
		if (src == _donorBtn) {
			if(OptionsMain._helper.checkEmpty(OptionsMain.donorTF)) {
				OptionsMain._infoLabel.setText("Please enter all required fields.");
			} else {
				addDonorAction();
			}
		} else if (src == _upload) {
			
			//chage it to Image instead of bufferedImage
			//Mark Ditianquin 6/3/15
			Image img = OptionsMain._helper.uploadImage();
			
			if(img != null && img instanceof Image) {
				img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
				_image = new ImageIcon(img);
				OptionsMain._IInfo.setText("Image uploaded");
			} else {
				OptionsMain._IInfo.setText("Couldn't upload the image.");
			}
		} else if (src == _itemBtn) {
			if(OptionsMain._helper.checkEmpty(OptionsMain.itemTF)) {
				OptionsMain._IInfo.setText("Please enter all required fields.");
			} else {
				addItemAction();
			}
		}		
	}	
	
	/**
	 * Adding donor operation.  This method is called
	 * when add button in add donor tab is clicked.
	 * 
	 * @param donorTF An array of JTextField in add donor tab
	 */
	private void addDonorAction() {
		String first = _firstTF.getText();
		String last = _lastTF.getText();
		String email = _emailTF.getText();
		String address = _addressTF.getText();
		String phone = _phoneTF.getText();
		
		try {
			Donor donor = new Donor(first, last, email, address, phone);
			if(donor != null && !OptionsMain._helper.checkDonor(donor)) {
				// Add info to the list in auction
				MainFrame._auction.addDonor(donor);
				
				String fileWriteTo = "+," + first + "," + last + "," + phone + "," + email + "," + address;
				MainFrame._auction.writeToFile("Donors", fileWriteTo);
				
				// Add info to the combo box in item panel
				OptionsMain._comboModel.addElement(first + " " + last + " - " + email);
				
				// Add info to donor table
				DefaultTableModel model = (DefaultTableModel) OptionsMain._donorTable.getModel();
				model.addRow(new Object[]{first, last, email, address, phone});
				OptionsMain._helper.clearText(OptionsMain.donorTF);
				OptionsMain._infoLabel.setText(first + " " + last + " has been added.");
			} else {
				OptionsMain._infoLabel.setText("Donor already exists.");
			}

		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * Adding item operation. This method is called
	 * when add button in add item tab is clicked.
	 * 
	 * @param itemTF An array of JTextField in add item tab
	 */
	private void addItemAction() {
		
		String itemName = _itemNameTF.getText();
		String itemDescription = _ItemDescriptionTF.getText();
		int startPrice = 0;
		int minIncrement = 0;
		long qr = 0;
		// parse doesn't work if the string is empty so I gotta check it here
		// the strings are empty by default so it will give an error if not checked.
		try {
			startPrice = Integer.parseInt(_startPriceTF.getText());
			minIncrement = Integer.parseInt(_minIncrementTF.getText());
			qr = Long.parseLong(_qrTF.getText());
		} catch (Exception ex) {
			OptionsMain._IInfo.setText("Wrong input format!");
		}
		
		Item item = null;
		Donor donor = OptionsMain._helper.getDonorWithCombo(_combo);
		try {
			String writeToFile = "+,";
			
			// if no donor is selected
			if(_combo.getSelectedItem().equals("")) {
				item = new Item(itemName, itemDescription, minIncrement, startPrice, qr, _image);
				
			// if  no image is uploaded	
			} else if (_image == null) {
				item = new Item(itemName, itemDescription, minIncrement, startPrice, donor, qr);
				
			// if no image is uploaded and no donor is selected	
			} else if (_image == null && _combo.getSelectedItem().equals("")){
				item = new Item(itemName, itemDescription, minIncrement, startPrice, qr);
				
			// If everything is filled out, selected, or uploaded	
			} else {
				item = new Item(itemName, itemDescription, minIncrement, startPrice, donor, qr, _image);
			}
			
			
			if(item != null && minIncrement > 0 && !OptionsMain._helper.checkItem(item)) {
				// Add to the list of item in auction class
				MainFrame._auction.addItem(item);
				// Add the item the the item table
				DefaultTableModel model = (DefaultTableModel) OptionsMain._itemTable.getModel();
				model.addRow(new Object[]{itemName, qr, "$" + startPrice, "$" + minIncrement});
				if(donor != null) {
					// Add the item to a particular donor
					donor.add(item);
					OptionsMain._IInfo.setText(itemName + " has been added for " + donor.getFirstName() 
							+ " " + donor.getLastName() + ".");
					OptionsMain._helper.clearText(OptionsMain.itemTF);
					writeToFile += itemName + "," + itemDescription + "," + minIncrement + "," + startPrice + "," 
							+ donor.getFirstName() + " " + donor.getLastName() + "," + qr;
					
					MainFrame._auction.editFile("Donors", donor.getFirstName() + "," + donor.getLastName(), 0, 
						itemName, qr);
					
			    } else {
			    	OptionsMain._IInfo.setText(itemName + " has been added.");
			    	OptionsMain._helper.clearText(OptionsMain.itemTF);
			    	writeToFile += itemName + "," + itemDescription + "," + minIncrement + "," + startPrice + ",no donor added," + qr;
			    }
				MainFrame._auction.writeToFile("Items", writeToFile);
				OptionsMain._helper.clearText(OptionsMain.itemTF);
			} else {
				OptionsMain._IInfo.setText("Item already exists.");
			}
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	private Action keyAction = new AbstractAction() {
        public void actionPerformed(ActionEvent a) {
			if(OptionsAddPanel.this.getSelectedComponent() == _DContainer)
				OptionsMain._helper.whichForm("Donor",OptionsMain.donorTF);
			else if (OptionsAddPanel.this.getSelectedComponent() == _IContainer)
				OptionsMain._helper.whichForm("Item", OptionsMain.itemTF);
        }
    };
}
