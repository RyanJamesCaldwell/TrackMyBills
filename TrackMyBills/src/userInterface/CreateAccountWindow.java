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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * Class creates a new JDialog window and allows the user to create a new account for 
 * monitoring bills.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 19-JUL-2017
 */
public class CreateAccountWindow extends JDialog {
	
	// JDialog box allowing for user to create new account
	private JDialog frmTrackmybillsCreate;
	// JTextField for user to enter their first name
	private JTextField txtFirstName;
	// JTextField for user to enter their last name
	private JTextField txtLastName;
	// XML file that is created
	private File newUserAccountFile;

	/**
	 * Create the application.
	 */
	public CreateAccountWindow() {
		initialize();
	}
	
	public JDialog getForm(){
		return this.frmTrackmybillsCreate;
	}
	// Initialize the user interface of the "Create User Account" window
	private void initialize() {
		frmTrackmybillsCreate = new JDialog();
		frmTrackmybillsCreate.setResizable(false);
		frmTrackmybillsCreate.setTitle("TrackMyBills - Create an Account");
		frmTrackmybillsCreate.setBounds(100, 100, 450, 236);
		frmTrackmybillsCreate.getContentPane().setLayout(null);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFirstName.setBounds(122, 74, 69, 14);
		frmTrackmybillsCreate.getContentPane().add(lblFirstName);
		
		JLabel lblCreateAnAccount = new JLabel("Create an Account");
		lblCreateAnAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateAnAccount.setFont(new Font("Arial", Font.BOLD, 20));
		lblCreateAnAccount.setBounds(122, 27, 190, 14);
		frmTrackmybillsCreate.getContentPane().add(lblCreateAnAccount);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLastName.setBounds(122, 113, 69, 14);
		frmTrackmybillsCreate.getContentPane().add(lblLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(188, 72, 110, 20);
		frmTrackmybillsCreate.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(188, 111, 110, 20);
		frmTrackmybillsCreate.getContentPane().add(txtLastName);
		
		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.setBounds(159, 154, 125, 23);
		frmTrackmybillsCreate.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = txtFirstName.getText();
				String lastName = txtLastName.getText();
				
				// If either names are empty, alert the user
				if (firstName.length() == 0 || lastName.length() == 0) {
					JOptionPane.showMessageDialog(null, "Please fill in both fields.");
				}
				else {
					newUserAccountFile = new File(System.getenv("APPDATA") + "/TrackMyBills/" + firstName + lastName + ".xml");
					
					try {
						newUserAccountFile.createNewFile();
						JOptionPane.showMessageDialog(null, firstName + lastName +".xml user account has been created.");
						finishAccountSetup();
					} catch (IOException e) {
						System.err.println("New user account could not be created.");
						//e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * Makes the CreateAccountWindow JDialog visible
	 */
	public void showDialog(){
		this.frmTrackmybillsCreate.setVisible(true);
	}
	
	// Writes to new user account file the basics of an XML user account
	private void finishAccountSetup() {
		try {
			PrintWriter outWriter = new PrintWriter(this.newUserAccountFile.getAbsolutePath());
			outWriter.println("<account>");
			outWriter.println("</account>");
			outWriter.close();
		} catch (FileNotFoundException e) {
			System.err.println("User's account file was not found.");
			//e.printStackTrace();
		}
	}
}
