/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package parser;

import linkedList.DoublyLinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class pulls the relevant bill information out of the XML document.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class DocumentParser {
	
	// XML document provided by the user
	private Document document;
	// Linked list of electric bills
	private DoublyLinkedList electricBills;
	// Linked list of gas bills
	private DoublyLinkedList gasBills;
	// Linked list of water bills
	private DoublyLinkedList waterBills;
	
	/**
	 * 
	 * @param userDocument The XML document to be parsed.
	 */
	public DocumentParser(Document userDocument) {
		this.document = userDocument;
		electricBills = new DoublyLinkedList();
		gasBills = new DoublyLinkedList();
		waterBills = new DoublyLinkedList();
	}
	
	/**
	 * <b>Description</b>: Parses the electric bills from the XML document.
	 * @return A linked list of electric bills.
	 */
	public DoublyLinkedList getElectricBills() {
		getBillOfType("electric");
		return this.electricBills;
	}
	
	// Parses the user's profile (XML document) and populates the doubly linked lists with the existing bills
	// @param billType The type of bill to be parsed (electric, water, gas, etc.)
	private void getBillOfType(String billType) {
		NodeList nl;
		NodeList tempList;
		double tempTotalCost = -1;
		String tempDueDate = "";
		String tempDescription = "";
		String tempName = "";
		
		//Get all bills by user
		nl = document.getElementsByTagName("bill");
		
		for (int i = 0; i < nl.getLength(); i++) {
			
			Element e = (Element) nl.item(i);
			tempDescription = e.getAttribute("type");
			if (tempDescription.equals(billType)) {
				tempName = e.getAttribute("name");
				//Get descriptive nodes of each bill and add to linked list
				tempList = nl.item(i).getChildNodes();
				for (int j = 0; j < tempList.getLength() -1 ; j++) {
					if (tempList.item(j).getTextContent().contains(".")) {
						tempTotalCost = Double.parseDouble(tempList.item(j).getTextContent());
					}
					else if (!tempList.item(j).getTextContent().contains(".") && tempList.item(j).getTextContent().length() > 5) {
						tempDueDate = tempList.item(j).getTextContent();
					}	
				}
				
				//add bill to the appropriate linked list
				linkedList.Node newNode = new linkedList.Node(new Bill(tempName, billType, tempTotalCost, tempDueDate));
				if (tempDescription.equals("electric")) {
					this.electricBills.insertLast(newNode);
				}
				else if (tempDescription.equals("gas")) {
					this.gasBills.insertLast(newNode);
				}
				else if (tempDescription.equals("water")) {
					this.waterBills.insertLast(newNode);
				}
			}
		}
	}
	
	/**
	 * <b>Description</b>: Parses the gas bills from the XML document.
	 * @return A linked list of gas bills.
	 */
	public DoublyLinkedList getGasBills() {
		getBillOfType("gas");
		return this.gasBills;
	}
	
	/**
	 * <b>Description</b>: Parses the water bills from the XML document.
	 * @return A linked list of water bills.
	 */
	public DoublyLinkedList getWaterBills() {
		getBillOfType("water");
		return this.waterBills;
	}
}
