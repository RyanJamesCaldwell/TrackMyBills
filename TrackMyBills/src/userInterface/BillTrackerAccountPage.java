/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */

package userInterface;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import constants.ConstantVariables;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import linkedList.DoublyLinkedList;
import linkedList.Node;

//TODO
// - Modify bill
// - Remove bill

/**
 * Allows the user to log into their account, create a new account, or exit the program.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class BillTrackerAccountPage {

	// Main user interface component
	private JFrame frmTrackmybills;
	// Linked list of active user accounts
	private DoublyLinkedList listOfAccounts;
	// Combo box containing existing user accounts
	private JComboBox cboxAccountName;
	// Login button, directs user to account summary page
	private JButton btnlogin;
	// Button for creating an account
	private JButton btnCreateAccount;
	// JDialog box to be opened when user logs in (account summary page)
	private JDialog accountSummaryWindow;
	// JDialog box to be opened when user is creating new account
	private CreateAccountWindow createAccountWindow;
	// JButton for removing account
	private JButton btnRemoveAccount;
	
	/**
	 * Create TrackMyBills folder in %APPDATA%, load accounts and AccountSummaryWindow
	 */
	public BillTrackerAccountPage() {
		checkForExistingAppDataFolder();
		initialize();
		loadAccounts();
		this.frmTrackmybills.setVisible(true);
		frmTrackmybills.setResizable(false);
	}
	
	// Create TrackMyBills folder in APPDATA if it doesn't exist
	private void checkForExistingAppDataFolder() {
		if(ConstantVariables.APP_DATA_FILE.exists()) {
			if(!ConstantVariables.TRACK_MY_BILLS_FOLDER.exists()) {
				ConstantVariables.TRACK_MY_BILLS_FOLDER.mkdir();
			}
		}
		else {
			System.err.println("Fatal error: AppData folder does not exist.");
			this.frmTrackmybills.dispose();
		}
	}
	
	// Set up the user interface, add basic functionality to UI components
	private void initialize() {
		frmTrackmybills = new JFrame();
		frmTrackmybills.setTitle("TrackMyBills - Login");
		frmTrackmybills.setBounds(100, 100, 450, 340);
		frmTrackmybills.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrackmybills.getContentPane().setLayout(null);
		
		JLabel lblTrackmybills = new JLabel("TrackMyBills");
		lblTrackmybills.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrackmybills.setFont(new Font("Arial", Font.BOLD, 20));
		lblTrackmybills.setBounds(148, 48, 137, 28);
		frmTrackmybills.getContentPane().add(lblTrackmybills);
		
		JLabel lblSelectAccount = new JLabel("Select account:");
		lblSelectAccount.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSelectAccount.setBounds(85, 120, 103, 14);
		frmTrackmybills.getContentPane().add(lblSelectAccount);
		
		this.cboxAccountName = new JComboBox();
		this.cboxAccountName.setBounds(191, 118, 151, 20);
		frmTrackmybills.getContentPane().add(this.cboxAccountName);
		
		this.btnlogin = new JButton("View Account");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
				btnlogin.setEnabled(false);
				btnCreateAccount.setEnabled(false);
			}
		});
		this.btnlogin.setBounds(162, 162, 122, 23);
		frmTrackmybills.getContentPane().add(this.btnlogin);
		
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(162, 205, 122, 23);
		frmTrackmybills.getContentPane().add(btnCreateAccount);
		
		btnRemoveAccount = new JButton("Remove Account");
		btnRemoveAccount.setBounds(153, 247, 137, 23);
		frmTrackmybills.getContentPane().add(btnRemoveAccount);
		btnRemoveAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this account? This action cannot be undone.", "Remove account?",  JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					removeAccount();
				}
			}
		});	
		
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createNewAccount();
			}
		});
		
		//CREATE MENU BAR AND MENU ITEMS
		JMenuBar menuBar = new JMenuBar();
		frmTrackmybills.setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		// Close the program on "Exit" click
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTrackmybills.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frmTrackmybills.dispose();
			}
		});
	}
	
	/**
	 * Open account summary page with logged in user, hides login page
	 */
	public void login() {
		//create new JDialog, pass parameter of account name
		accountSummaryWindow = new AccountSummaryWindow(this.cboxAccountName.getSelectedItem().toString());
		accountSummaryWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frmTrackmybills.setVisible(true);
				btnlogin.setEnabled(true);
				btnCreateAccount.setEnabled(true);
				btnRemoveAccount.setEnabled(true);
			}
		});
	}
	
	/**
	 * Open CreateAccountWindow, allow user to create a new user account
	 */
	public void createNewAccount() {
		createAccountWindow = new CreateAccountWindow();
		createAccountWindow.getForm().addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        cboxAccountName.removeAllItems();
		        loadAccounts();
		        if(cboxAccountName.getItemCount() > 0) {
		        	btnlogin.setEnabled(true);
		        	btnRemoveAccount.setEnabled(true);
		        }
		    }
		});
		createAccountWindow.showDialog();
	}
	
	/**
	 * Removes an existing user account from the APPDATA folder
	 */
	public void removeAccount() {
		String accountToRemove = this.cboxAccountName.getSelectedItem().toString();
		int indexToRemoveFromCbox = this.cboxAccountName.getSelectedIndex();
		
		File fileToRemove = new File(ConstantVariables.TRACK_MY_BILLS_FOLDER_STRING + "/" + accountToRemove);
		
		fileToRemove.delete();
		this.cboxAccountName.removeItemAt(indexToRemoveFromCbox);
		if(this.cboxAccountName.getItemCount() == 0){
			this.btnlogin.setEnabled(false);
			this.btnRemoveAccount.setEnabled(false);
		}
	}
	
	// Loads existing user accounts into the JDialogBox
	private final void loadAccounts() {
		listOfAccounts = new DoublyLinkedList();
		
		// Existing user accounts are located in the "resources" folder
		File[] listOfFiles = ConstantVariables.TRACK_MY_BILLS_FOLDER.listFiles();
		
		if(listOfFiles.length == 0) {
			this.btnlogin.setEnabled(false);
			this.btnRemoveAccount.setEnabled(false);
		}
		else {
			//Add user accounts to doubly linked list and JComboBox
			for (int i = 0; i < listOfFiles.length; i++) {
				listOfAccounts.insertLast(new Node(listOfFiles[i].getName()));
				cboxAccountName.addItem(listOfFiles[i].getName());
			}
		}
	}
}