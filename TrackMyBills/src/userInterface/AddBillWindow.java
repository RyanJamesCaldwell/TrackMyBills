/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Creates a new window for adding bills to an existing user account
 * 
 * @author Ryan Caldwell
 * @version Version 1, 19-JUL-2017
 */
public class AddBillWindow extends JDialog {

	// Panel containing the UI components
	private  JPanel contentPanel = new JPanel();
	// User's account file
	private File accountFile;
	// Text field that the user enters their bill amount in
	private JTextField txtBillAmount;
	// String read value of the user's account file
	private String readAccountFile;
	// JComboBox containing the bill's month
	private JComboBox<String> cboxBillMonth;
	// JComboBox containing the bill's year
	private JComboBox<String> cboxBillYear;
	// JTextField containing the type of bill the user is adding
	private JTextField txtBillType;
	
	/**
	 * Create the JDialog window and set up the UI components
	 */
	public AddBillWindow(File accountFile) {
		setTitle("TrackMyBills - Add New Bill");
		this.accountFile = accountFile;
		this.initialize();
		this.readAccountFile = null;
	}
	
	/**
	 * Set up the UI and add basic functionality to its components
	 */
	public void initialize() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblBillMonth = new JLabel("Bill Month:");
		lblBillMonth.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBillMonth.setBounds(123, 69, 56, 14);
		contentPanel.add(lblBillMonth);
		
		JLabel lblBillYear = new JLabel("Bill Year:");
		lblBillYear.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBillYear.setBounds(131, 111, 48, 14);
		contentPanel.add(lblBillYear);
		
		cboxBillMonth = new JComboBox<String>();
		cboxBillMonth.setBounds(181, 67, 118, 20);
		contentPanel.add(cboxBillMonth);
		
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		for(int i = 0; i < months.length; i++) {
			cboxBillMonth.addItem(months[i]);
		}
		
		cboxBillYear = new JComboBox<String>();
		cboxBillYear.setBounds(181, 109, 118, 20);
		contentPanel.add(cboxBillYear);
		cboxBillYear.addItem("2016");
		cboxBillYear.addItem("2017");
		cboxBillYear.addItem("2018");
		
		JLabel lblAddNewBill = new JLabel("Add New Bill");
		lblAddNewBill.setFont(new Font("Arial", Font.BOLD, 20));
		lblAddNewBill.setBounds(158, 28, 118, 14);
		contentPanel.add(lblAddNewBill);
		
		JLabel lblBillAmount = new JLabel("Bill amount:");
		lblBillAmount.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBillAmount.setBounds(112, 149, 65, 14);
		contentPanel.add(lblBillAmount);
		
		txtBillAmount = new JTextField();
		txtBillAmount.setHorizontalAlignment(SwingConstants.CENTER);
		txtBillAmount.setText("0.00");
		txtBillAmount.setToolTipText("");
		txtBillAmount.setBounds(181, 147, 118, 20);
		contentPanel.add(txtBillAmount);
		txtBillAmount.setColumns(10);
		
		JButton btnAddBill = new JButton("Add Bill");
		btnAddBill.setBounds(170, 227, 89, 23);
		contentPanel.add(btnAddBill);
		
		JLabel lblBillType = new JLabel("Bill type:");
		lblBillType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBillType.setBounds(131, 185, 48, 14);
		contentPanel.add(lblBillType);
		
		txtBillType = new JTextField();
		txtBillType.setHorizontalAlignment(SwingConstants.CENTER);
		txtBillType.setToolTipText("");
		txtBillType.setText("gas, electric, water");
		txtBillType.setColumns(10);
		txtBillType.setBounds(181, 183, 118, 20);
		contentPanel.add(txtBillType);
		this.setVisible(true);
		btnAddBill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this bill?", "Add bill?",  JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					addBill();
				}
			}
		});
	}
	
	/**
	 * Reads the contents of the file into readAccountFile and
	 * returns the contents of the user's account file as a string
	 * 
	 * @return The contents of the user's account file as a string. 
	 * 		   Returns null if there was an error opening the file.
	 */
	public String readAccountFile() {
		String content = null;
		
		try {
			this.readAccountFile = new String(Files.readAllBytes(Paths.get(this.accountFile.getAbsolutePath())));
			content = readAccountFile;
		} catch (IOException e) {
			System.err.println("Error reading the user's account file when getting ready to write a new bill.");
			//e.printStackTrace();
		}
		
		return content;
	}
	
	/**
	 * Returns the user's account profile (XML document) as a string
	 * 
	 * @return The user's account profile (XML document) as a string
	 */
	public String getReadAccountFile() {
		return this.readAccountFile;
	}
	
	/**
	 * Adds new bill to the user's account file (XML document)
	 */
	public void addBill() {
		String buildBill;
		PrintWriter out;
		String billType = this.txtBillType.getText();
		boolean validBill = billType.equals("gas") || billType.equals("water") || billType.equals("electric");
		
		// Check if the entered bill type is valid
		if(validBill) {
			System.out.println("Valid bill type");
			try {
				out = new PrintWriter(this.accountFile);
				buildBill = "\t<bill name=\"" + this.cboxBillMonth.getSelectedItem() + " " + this.cboxBillYear.getSelectedItem() + "\" type=\"" + this.txtBillType.getText() + "\">\n";
				buildBill += "\t\t<total-cost>" + this.txtBillAmount.getText() + "</total-cost>\n";
				buildBill += "\t\t<due-date>July 28 2017</due-date>\n";
				buildBill += "\t</bill>\n</account>";
				//System.out.println(buildBill);
				this.readAccountFile = this.readAccountFile.replace("</account>", buildBill);
				out.write(this.readAccountFile);
				System.out.println(this.readAccountFile);
				out.close();
				this.dispose();
			} catch (FileNotFoundException e) {
				System.err.println("There was a problem writing the new bill to the user's account.");
				//e.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Not a valid bill type. Please enter electric, water, or gas.");
		}
	}
}
