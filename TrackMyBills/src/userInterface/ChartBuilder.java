/*
 * COPYRIGHT: Copyright (c) 2017 Ryan Caldwell
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 * PROJECT NAME: TrackMyBills
 */
package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import linkedList.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import parser.Bill;

/**
 * When the argumented constructor is executed, a chart is generated comparing the bills within the linked list.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 18-JUL-2017
 */
public class ChartBuilder {
	
	// Title of the chart
	private String chartTitle;
	// Label for the X axis
	private String xAxisLabel;
	// Label for the Y axis
	private String yAxisLabel;	
	// Dataset that values will be added to for chart
	private DefaultCategoryDataset dataset;
	// Linked list holding the information on bills
	private DoublyLinkedList data;
	// Popup window for the chart
	private JFrame chartPopup;
	// File chooser for selecting where to save chart image
	private JFileChooser fileChooser;
	// Type of bills being held within the linked list
	private String typeOfLL;
	
	/**
	 * 
	 * @param chartTitle - Title for the chart
	 * @param xAxisLabel - Label for the X axis
	 * @param yAxisLabel - Label for the Y axis
	 * @param data		 - DoublyLinkedList containing bills
	 */
	public ChartBuilder(String chartTitle, String xAxisLabel, String yAxisLabel, DoublyLinkedList data) {
		this.chartTitle = chartTitle;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		this.dataset = new DefaultCategoryDataset();
		this.data = data;
		this.chartPopup = new JFrame();
		this.chartPopup.setLayout(new java.awt.BorderLayout());
	}
	
	//TODO make sure that you can only add UP TO TWELVE past bills (the last year)
	/**
	 * Sets up the graph with the data sent in through the doubly linked list, also displays the graph.
	 */
	public void populateAndDisplayGraph() {
		int llSize = this.data.getSize();
		Node currentNode;
		Bill currentBill;
		ChartPanel CP;
		JButton btnSave;
		final int EMPTY_LIST = 0;
		
		if (llSize > EMPTY_LIST) {
			currentNode = this.data.getHead().getNext();			
			currentBill = (Bill) currentNode.getNodeValue();
			typeOfLL = currentBill.getType();
			
			//Populate the dataset for the chart
			while (currentNode != this.data.getTail()) {
				dataset.addValue(currentBill.getTotalCost(), currentBill.getType(), currentBill.getBillName());
				currentNode = currentNode.getNext();
				currentBill = (Bill) currentNode.getNodeValue();
			}
			
			
			//User interface work
			JFreeChart barChart = ChartFactory.createBarChart3D(this.chartTitle, this.xAxisLabel, this.yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
			int width = 640;
			int height = 640;
			CP = new ChartPanel(barChart);
			btnSave = new JButton("Save Chart");
			
			//Add a file chooser to the save button, allowing the user to save the chart wherever they want
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = fileChooser.showOpenDialog(chartPopup);
					
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFolder = fileChooser.getSelectedFile();
						String folderPath = selectedFolder.getAbsolutePath();
						
						//Keeps window structure that uses \, needs to be replaced with /
						folderPath = folderPath.replace("\\", "/");
						try {
							//File barChart3D = new File(folderPath + "/hello.jpg");
							File barChart3D = new File(folderPath + "/" + typeOfLL + "-bills-" + System.currentTimeMillis() + ".jpg");
							ChartUtilities.saveChartAsJPEG(barChart3D, barChart, width, height);
						} catch (IOException e) {
							System.err.println("Error creating bar chart.");
						}
					}
				}
			});
			CP.setPreferredSize(new Dimension(width, height));
			this.chartPopup.getContentPane().add(CP);
			this.chartPopup.getContentPane().add(btnSave, BorderLayout.SOUTH);
			this.chartPopup.pack();
			this.chartPopup.setVisible(true);
		}
		//If the doubly linked list of bills for a certain type are empty, notify the user and display no chart
		else {
			JOptionPane.showMessageDialog(null, "You don't have any of these bills yet. Add your past bills to view your history.");
		}
	}
}
