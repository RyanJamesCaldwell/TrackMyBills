/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package userInterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import linkedList.DoublyLinkedList;
import linkedList.Node;
import parser.Bill;
import parser.DocumentParser;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JComboBox;

/**
 * Class creates a new JDialog window and allows the user to create a new account for 
 * monitoring bills.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 19-JUL-2017
 */
public class RemoveBillWindow {
	
	// JDialog box allowing for user to create new account
	private JDialog frmTrackmybillsCreate;
	// JTextField for user to enter their first name
	private JTextField txtFirstName;
	// JTextField for user to enter their last name
	private JTextField txtLastName;
	// XML file that is created
	private File newUserAccountFile;
	// JComboBox for displaying user's electric bills
	private JComboBox<String> cboxElectricBills;
	// JComboBox for displaying user's water bills
	private JComboBox<String> cboxWaterBills;
	// JComboBox for displaying user's gas bills
	private JComboBox<String> cboxGasBills;
	// DocumentParser for parsing the user's XML document (account file)
	private DocumentParser docParser;
	// DoublyLinkedList for electric bill storage
	private DoublyLinkedList electricBillsLL;
	// DoublyLinkedList for gas bill storage
	private DoublyLinkedList gasBillsLL;
	// DoublyLinkedList for water bill storage
	private DoublyLinkedList waterBillsLL;
	// JButton for removing electric bills
	private JButton btnRemoveElectricBill;
	// JButton for removing gas bills
	private JButton btnRemoveGasBill;
	// JButton for removing water bills
	private JButton btnRemoveWaterBill;
	
	
	/**
	 * Initialize the UI, load bills into the JComboBoxes, allow users to select which bills to remove
	 */
	public RemoveBillWindow(DocumentParser docParser) {
		this.docParser = docParser;
		initialize();
		this.frmTrackmybillsCreate.setVisible(true);
		loadBills();
	}
	
	/**
	 * Returns the JDialog for removing bills
	 * @return The JDialog for removing bills
	 */
	public JDialog getForm(){
		return this.frmTrackmybillsCreate;
	}
	
	// Initialize the user interface of the "Create User Account" window
	private void initialize() {
		frmTrackmybillsCreate = new JDialog();
		frmTrackmybillsCreate.setResizable(false);
		frmTrackmybillsCreate.setTitle("TrackMyBills - Remove Bill");
		frmTrackmybillsCreate.setBounds(100, 100, 535, 203);
		frmTrackmybillsCreate.getContentPane().setLayout(null);
		
		JLabel lblRemoveBill = new JLabel("Remove Bill");
		lblRemoveBill.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveBill.setFont(new Font("Arial", Font.BOLD, 20));
		lblRemoveBill.setBounds(198, 28, 132, 14);
		frmTrackmybillsCreate.getContentPane().add(lblRemoveBill);
		
		cboxElectricBills = new JComboBox<String>();
		cboxElectricBills.setBounds(10, 93, 132, 20);
		frmTrackmybillsCreate.getContentPane().add(cboxElectricBills);
		
		cboxWaterBills = new JComboBox<String>();
		cboxWaterBills.setBounds(387, 93, 132, 20);
		frmTrackmybillsCreate.getContentPane().add(cboxWaterBills);
		
		cboxGasBills = new JComboBox<String>();
		cboxGasBills.setBounds(198, 93, 132, 20);
		frmTrackmybillsCreate.getContentPane().add(cboxGasBills);
		
		JLabel lblElectricBill = new JLabel("Electric Bills");
		lblElectricBill.setBounds(10, 74, 89, 14);
		frmTrackmybillsCreate.getContentPane().add(lblElectricBill);
		
		JLabel lblGasBills = new JLabel("Gas Bills");
		lblGasBills.setBounds(198, 74, 66, 14);
		frmTrackmybillsCreate.getContentPane().add(lblGasBills);
		
		JLabel lblWaterBills = new JLabel("Water Bills");
		lblWaterBills.setBounds(387, 74, 76, 14);
		frmTrackmybillsCreate.getContentPane().add(lblWaterBills);
		
		btnRemoveElectricBill = new JButton("Remove Bill");
		btnRemoveElectricBill.setBounds(20, 124, 112, 23);
		frmTrackmybillsCreate.getContentPane().add(btnRemoveElectricBill);
		
		// Button functionality calls removeBill()
		btnRemoveElectricBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				removeBill(cboxElectricBills.getSelectedItem().toString(), cboxElectricBills, cboxElectricBills.getSelectedIndex(), btnRemoveElectricBill);
			}
		});
		
		btnRemoveGasBill = new JButton("Remove Bill");
		btnRemoveGasBill.setBounds(208, 124, 108, 23);
		frmTrackmybillsCreate.getContentPane().add(btnRemoveGasBill);
		
		// Button functionality calls removeBill()
		btnRemoveGasBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				removeBill(cboxGasBills.getSelectedItem().toString(), cboxGasBills, cboxGasBills.getSelectedIndex(), btnRemoveGasBill);
			}
		});
		
		btnRemoveWaterBill = new JButton("Remove Bill");
		btnRemoveWaterBill.setBounds(397, 124, 113, 23);
		frmTrackmybillsCreate.getContentPane().add(btnRemoveWaterBill);
		
		// Button functionality calls removeBill()
		btnRemoveWaterBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				removeBill(cboxWaterBills.getSelectedItem().toString(), cboxWaterBills, cboxWaterBills.getSelectedIndex(), btnRemoveWaterBill);
			}
		});
	}
	
	// Loads bills into the JComboBoxes
	private void loadBills() {
		this.electricBillsLL = docParser.getElectricBills();
		this.gasBillsLL = docParser.getGasBills();
		this.waterBillsLL = docParser.getWaterBills();
		Node currentNode;
		
		// Populate electric bills JComboBox
		if(this.electricBillsLL.getSize() >= 1) {
			currentNode = this.electricBillsLL.getHead().getNext();
			for(int i = 0; i < this.electricBillsLL.getSize(); i++) {
				this.cboxElectricBills.addItem(((Bill)currentNode.getNodeValue()).getBillName());
				currentNode = currentNode.getNext();
			}
		}
		else {
			this.btnRemoveElectricBill.setEnabled(false);
		}
		
		// Populate gas bills JComboBox
		if(this.gasBillsLL.getSize() >= 1) {
			currentNode = this.gasBillsLL.getHead().getNext();
			for(int i = 0; i < this.gasBillsLL.getSize(); i++) {
				this.cboxGasBills.addItem(((Bill)currentNode.getNodeValue()).getBillName());
				currentNode = currentNode.getNext();
			}
		}
		else {
			this.btnRemoveGasBill.setEnabled(false);
		}
		
		// Populate water bills JComboBox
		if(this.waterBillsLL.getSize() >= 1) {
			currentNode = this.waterBillsLL.getHead().getNext();
			for(int i = 0; i < this.waterBillsLL.getSize(); i++) {
				this.cboxWaterBills.addItem(((Bill)currentNode.getNodeValue()).getBillName());
				currentNode = currentNode.getNext();
			}
		}
		else {
			this.btnRemoveWaterBill.setEnabled(false);
		}
		
	}
	
	// Removes selected bill, disables button if JComboBox is made empty
	public void removeBill(String selectedBill, JComboBox removeFrom, int selectedBillIndex, JButton associatedButton) {
		this.docParser.removeBillByName(selectedBill);
		removeFrom.removeItemAt(selectedBillIndex);
		
		if(removeFrom.getItemCount() == 0) {
			associatedButton.setEnabled(false);
		}
	}
}
