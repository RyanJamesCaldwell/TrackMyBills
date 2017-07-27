/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package parser;

import linkedList.DoublyLinkedList;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
	// User's account file name
	private String accountName;
	
	/**
	 * 
	 * @param userDocument The XML document to be parsed.
	 */
	public DocumentParser(Document userDocument, String accountName) {
		this.document = userDocument;
		this.accountName = accountName;
		electricBills = new DoublyLinkedList();
		gasBills = new DoublyLinkedList();
		waterBills = new DoublyLinkedList();
	}
	
	/**
	 * Parses the electric bills from the XML document.
	 * 
	 * @return A linked list of electric bills.
	 */
	public DoublyLinkedList getElectricBills() {
		this.electricBills = new DoublyLinkedList();
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
	 * Removes bill by a given name. Returns true if bill found and removed, false otherwise.
	 * 
	 * @param billName
	 */
	public boolean removeBillByName(String billName) {
		NodeList nl = this.document.getElementsByTagName("bill");
		Element tempElement;
		String tempDescription;
		boolean foundAndRemoved = false;
		Node parent;
		
		for(int i = 0; i < nl.getLength(); i++) {
			tempElement = (Element) nl.item(i);
			tempDescription = tempElement.getAttribute("name");
			
			// If the description of the current element matches what the user wants to remove, remove it
			if(tempDescription.equals(billName)){
				parent = tempElement.getParentNode();
				tempElement.getParentNode().removeChild(nl.item(i));

				try {
					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					Result output = new StreamResult(new File(System.getenv("APPDATA") + "/TrackMyBills/" + this.accountName));
					Source input = new DOMSource(this.document);
					transformer.transform(input, output);
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerFactoryConfigurationError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				foundAndRemoved = true;
			}
		}
		return foundAndRemoved;
	}
	
	/**
	 * <b>Description</b>: Parses the gas bills from the XML document.
	 * @return A linked list of gas bills.
	 */
	public DoublyLinkedList getGasBills() {
		this.gasBills = new DoublyLinkedList();
		getBillOfType("gas");
		return this.gasBills;
	}
	
	/**
	 * <b>Description</b>: Parses the water bills from the XML document.
	 * @return A linked list of water bills.
	 */
	public DoublyLinkedList getWaterBills() {
		this.waterBills = new DoublyLinkedList();
		getBillOfType("water");
		return this.waterBills;
	}
}
