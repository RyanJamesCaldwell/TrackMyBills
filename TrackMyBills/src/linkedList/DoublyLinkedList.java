
/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package linkedList;

/**
 * Class is an implementation of the doubly linked list data structure.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class DoublyLinkedList {
	
	// Head buffer for the doubly linked list (as to not point to empty address space)
	private Node headBuffer;
	// Tail buffer for the doubly linked list (as to not point to empty address space)
	private Node tailBuffer;
	// The size of the linked list
	private int listSize;
	// Represents number of nodes in an empty doubly linked list
	final private int EMPTY_LIST = 0;
	
	/**
	 * Sets the head buffer and tail buffer to null, calls setHeadTail(), sets list size to zero
	 */
	public DoublyLinkedList() {
		this.headBuffer = new Node(null);
		this.tailBuffer = new Node(null);
		setHeadTail();
		this.listSize = EMPTY_LIST;
	}
	
	// Points the head and tail buffers toward one another
	private final void setHeadTail() {
		this.headBuffer.setNext(tailBuffer);
		this.headBuffer.setPrevious(null);
		this.tailBuffer.setNext(null);
		this.tailBuffer.setPrevious(headBuffer);
	}
	
	/**
	 * Returns the head buffer
	 * @return The head buffer
	 */
	public Node getHead() {
		return this.headBuffer;
	}
	
	/**
	 * Returns the tail buffer
	 * @return The tail buffer
	 */
	public Node getTail() {
		return this.tailBuffer;
	}
	
	/**
	 * Returns the current size of the doubly linked list
	 * @return
	 */
	public int getSize() {
		return this.listSize;
	}
	
	/**
	 * Inserts a Node directly after the head buffer
	 * @param newNode The node to be inserted
	 */
	public void insertFirst(Node newNode) {
		newNode.setNext(getHead().getNext());
		newNode.setPrevious(getHead());
		getHead().getNext().setPrevious(newNode);
		getHead().setNext(newNode);
		this.listSize++;
	}
	
	/**
	 * Inserts a Node directly before the tail buffer
	 * @param newNode The node to be inserted
	 */
	public void insertLast(Node newNode) {
		newNode.setNext(getTail());
		newNode.setPrevious(getTail().getPrevious());
		getTail().getPrevious().setNext(newNode);
		getTail().setPrevious(newNode);
		this.listSize++;
	}
	
	/**
	 * Removes the node directly after the head buffer
	 */
	public void removeFirst() {
		if (listSize > EMPTY_LIST) {
			Node removal = getHead().getNext();
			
			removal.getNext().setPrevious(removal.getPrevious());
			getHead().setNext(removal.getNext());
			removal.setNext(null);
			removal.setPrevious(null);
			removal.setNodeValue(null);
			this.listSize--;
		}
		else {
			System.err.println("Cannot remove item from empty list.");
		}
	}
	
	/**
	 * Removes the node directly before the tail buffer
	 */
	public void removeLast() {
		if (listSize > EMPTY_LIST) {
			Node removal = getTail().getPrevious();
			
			removal.getPrevious().setNext(getTail());
			getTail().setPrevious(removal.getPrevious());
			removal.setNext(null);
			removal.setNodeValue(null);
			removal.setPrevious(null);
			this.listSize--;
		}
		else {
			System.err.println("Cannot remove item from empty list.");
		}
	}
	
	/**
	 * Returns the string version of the doubly linked list
	 * @return The strig version of the doubly linked list
	 */
	public String toString() {
		String returnString = "";
		
		if (this.listSize == EMPTY_LIST) {
			returnString = "<HEAD> --> <TAIL>";
		}
		
		else {
			returnString += "<HEAD> --> ";
			Node pointer = this.getHead();
			
			while (pointer.getNext() != this.tailBuffer) {
				pointer = pointer.getNext();
				returnString += "<" + pointer.getNodeValue() + "> --> ";
			}
			returnString += "<TAIL>";
		}
		
		return returnString;
	}
}
