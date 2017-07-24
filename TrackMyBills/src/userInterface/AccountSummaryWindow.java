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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import linkedList.DoublyLinkedList;

import parser.DocumentParser;

/**
 * Class creates a JDialog window and allows the user to view/modify different aspects of their bills.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class AccountSummaryWindow extends JDialog {
	
	// The XML file for the user's profile
	private File accountFile;
	// DocumentBuilder for opening and reading the user's profile (XML document)
	private DocumentBuilder documentBuilder;
	// DocumentBuilderFactory for opening and reading the user's profile (XML document)
	private DocumentBuilderFactory dbFactory;
	// The opened XML document to be parsed
	private Document xmlDocument;
	// The object instance that parses the user's profile (XML document)
	private DocumentParser docParser;
	// Doubly linked list of electric bills
	private DoublyLinkedList electricBills;
	// Doubly linked list of gas bills
	private DoublyLinkedList gasBills;
	// Doubly linked list of water bills
	private DoublyLinkedList waterBills;
	// JDialog window for adding a new bill
	private AddBillWindow addBillWindow;
	// String of the account's file name
	private String accountName;
	
	/**
	 * Create the dialog.
	 * 
	 * @param accountName The name of the user account file. Ex: RyanCaldwell.xml
	 */
	public AccountSummaryWindow(String accountName) {
		setResizable(false);
		setTitle("TrackMyBills - Account Information");
		setBounds(100, 100, 499, 412);
		getContentPane().setLayout(null);
		initialize();
		this.accountName = accountName;
		this.setVisible(true);
		
		//Setup for reading & writing to XML file
		dbFactory = DocumentBuilderFactory.newInstance();
		accountFile = new File(System.getenv("APPDATA") + "/TrackMyBills/" + accountName);
		try {
			documentBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println("Error building new document builder.");
		}
		try {
			xmlDocument = documentBuilder.parse(accountFile);
		} catch (Exception e) {
			System.err.println("Error parsing XML document.");
		}
		
		docParser = new DocumentParser(xmlDocument);
		this.getUtilityBills();
		
	}
	
	// Initializes the user interface, adds basic functionality to UI components
	private void initialize() {
		JLabel lblModifyBills = new JLabel("Manage Bills");
		lblModifyBills.setFont(new Font("Arial", Font.BOLD, 11));
		lblModifyBills.setBounds(10, 11, 76, 14);
		getContentPane().add(lblModifyBills);
		
		JLabel lblViewBillHistory = new JLabel("View Bill History");
		lblViewBillHistory.setFont(new Font("Arial", Font.BOLD, 11));
		lblViewBillHistory.setBounds(10, 83, 100, 14);
		getContentPane().add(lblViewBillHistory);
		
		JButton btnRefreshBills = new JButton("Refresh Bills");
		btnRefreshBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbFactory = DocumentBuilderFactory.newInstance();
				
				accountFile = new File(System.getenv("APPDATA") + "/TrackMyBills/" + accountName);
				try {
					documentBuilder = dbFactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					System.err.println("Error building new document builder.");
				}
				try {
					xmlDocument = documentBuilder.parse(accountFile);
				} catch (Exception e) {
					System.err.println("Error parsing XML document.");
				}
				
				docParser = new DocumentParser(xmlDocument);
				getUtilityBills();
			}
		});
		btnRefreshBills.setBounds(10, 172, 107, 23);
		getContentPane().add(btnRefreshBills);
		
		JButton btnAddNewBill = new JButton("Add Bill");
		btnAddNewBill.setBounds(10, 36, 100, 23);
		getContentPane().add(btnAddNewBill);
		btnAddNewBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewBill();
			}
		});
		
		JButton btnViewElectricBill = new JButton("Electric");
		btnViewElectricBill.setBounds(10, 108, 100, 23);
		getContentPane().add(btnViewElectricBill);
		btnViewElectricBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChartBuilder("Electric Bills", "Dates of Bills", "Cost (in dollars)", electricBills).populateAndDisplayGraph();
			}
		});
		
		JButton btnViewGasBill = new JButton("Gas");
		btnViewGasBill.setBounds(120, 108, 100, 23);
		getContentPane().add(btnViewGasBill);
		btnViewGasBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChartBuilder("Gas Bills", "Dates of Bills", "Cost (in dollars)", gasBills).populateAndDisplayGraph();
			}
		});
		
		JButton btnViewWaterBill = new JButton("Water");
		btnViewWaterBill.setBounds(230, 108, 100, 23);
		getContentPane().add(btnViewWaterBill);
		btnViewWaterBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChartBuilder("Water Bills", "Dates of Bills", "Cost (in dollars)", waterBills).populateAndDisplayGraph();
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 70, 473, 2);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 142, 473, 2);
		getContentPane().add(separator_1);
		
		JLabel lblRefreshBillList = new JLabel("Refresh Bill List");
		lblRefreshBillList.setFont(new Font("Arial", Font.BOLD, 11));
		lblRefreshBillList.setBounds(10, 151, 100, 14);
		getContentPane().add(lblRefreshBillList);
	}
	
	/**
	 * Populating the doubly linked lists with the bills located in the user's XML document
	 */
	public void getUtilityBills() {
		this.electricBills = docParser.getElectricBills();
		this.gasBills = docParser.getGasBills();
		this.waterBills = docParser.getWaterBills();
	}
	
	/**
	 * Adds a new bill to the user's account
	 */
	public void addNewBill() {
		this.addBillWindow = new AddBillWindow(this.accountFile);
		this.addBillWindow.readAccountFile();
	}
}
