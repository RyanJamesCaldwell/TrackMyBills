/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package parser;

//TODO make sure that bill names can't include punctuation
//TODO make a field for if the bill has been paid or not

/**
 * This class describes the different values of a bill that may need to be kept track of.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class Bill {
	
	// Water, gas, electric, rent, etc.
	private String type;
	// The monetary cost that is owed to the utility company
	private double totalCost;
	// The name given to the bill by the user
	private String billName;
	// The due date of the bill
	private String dueDate;	

	/**
	 * 
	 * @param billName - The name given to the bill by the user
	 * @param type - Water, gas, electric, etc.
	 * @param totalCost - The monetary cost that is owed to the utility company
	 * @param dueDate - The due date of the bill
	 */
	public Bill(String billName, String type, double totalCost, String dueDate) {
		this.billName = billName;
		this.type = type;
		this.totalCost = totalCost;
		this.dueDate = dueDate;
	}
	
	/**
	 * 
	 * @return The type of bill (String).
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type Sets the type of bill.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return The monetary cost owed to the utility company (double).
	 */
	public double getTotalCost() {
		return totalCost;
	}
	
	/**
	 * 
	 * @param totalCost Sets the total cost of the bill.
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	/**
	 * 
	 * @return The name given to the bill the user (String).
	 */
	public String getBillName() {
		return billName;
	}
	
	/**
	 * 
	 * @param billName Sets the name of the bill.
	 */
	public void setBillName(String billName) {
		this.billName = billName;
	}
	
	/**
	 * 
	 * @return The due date of the bill (String).
	 */
	public String getDueDate() {
		return dueDate;
	}
	
	/**
	 * 
	 * @param dueDate Sets the due date of the bill.
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}
