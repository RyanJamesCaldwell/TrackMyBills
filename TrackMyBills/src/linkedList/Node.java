/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package linkedList;

/**
 * Class is a Node for a DoublyLinkedList.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class Node {
	
	// Value being held within the node
	private Object nodeValue;
	// Pointer to the next node in the doubly linked list
	private Node next;
	// Pointer to the previous node in the doubly linked list
	private Node previous;
	
	/**
	 * Sets nodeValue, next pointer, and previous pointer to null
	 */
	public Node() {
		this.nodeValue = null;
		this.next = null;
		this.previous = null;
	}
	
	/**
	 * Value is assigned to node, next pointer and previous pointer are set to null
	 * @param value - Object value for the node to hold
	 */
	public Node(Object value) {
		this.nodeValue = value;
		this.previous = null;
		this.next = null;
	}
	
	/**
	 * Points the next pointer to the next node in the doubly linked list
	 * @param nextNode
	 */
	public void setNext(Node nextNode) {
		this.next = nextNode;
	}
	
	/**
	 * Returns the next node in the doubly linked list
	 * @return The next node in the doubly linked list
	 */
	public Node getNext() {
		return this.next;
	}
	
	/**
	 * Points the previous pointer to the previous node in the doubly linked list
	 * @param previousNode The previous pointer in the doubly linked list
	 */
	public void setPrevious(Node previousNode) {
		this.previous = previousNode;
	}
	
	/**
	 * Returns the previous node in the linked list
	 * @return The previous node in the linked list
	 */
	public Node getPrevious() {
		return this.previous;
	}
	
	/**
	 * Returns the object being held within the node
	 * @return The object being held within the node
	 */
	public Object getNodeValue() {
		return this.nodeValue;
	}
	
	/**
	 * Sets the value being held within the node
	 * @param newValue The value to be held within the node
	 */
	public void setNodeValue(Object newValue) {
		this.nodeValue = newValue;
	}
}
