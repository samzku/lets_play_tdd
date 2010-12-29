package com.jamesshore.finances.ui;

import java.awt.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
import org.junit.experimental.theories.internal.*;
import com.jamesshore.finances.domain.*;

public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Financial Projector";
	public static final Point INITIAL_POSITION = new Point(400, 300);
	public static final Dimension INITIAL_SIZE = new Dimension(900, 400);

	private ApplicationModel applicationModel;

	public ApplicationFrame(ApplicationModel applicationModel) {
		super(TITLE);
		this.applicationModel = applicationModel;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(INITIAL_POSITION);
		setSize(INITIAL_SIZE);
		addComponents();
	}

	private void addComponents() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(BorderLayout.CENTER, forecastTable());
		contentPane.add(BorderLayout.NORTH, startingBalanceField());
	}

	public JTextField startingBalanceField() {
		final JTextField field = new JTextField();
		
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void removeUpdate(DocumentEvent e) { updateApplicationModel(); }
			@Override public void insertUpdate(DocumentEvent e) { updateApplicationModel(); }
			@Override public void changedUpdate(DocumentEvent e) { updateApplicationModel(); }
			private void updateApplicationModel() {
				try {
					int value = Integer.parseInt(field.getText());
					applicationModel.setStartingBalance(new Dollars(value));
				}
				catch (NumberFormatException e) {
					System.out.print("*");
				}
			}
		});
		
		return field;
	}
	
	private Component forecastTable() {
		return new JScrollPane(new ForecastTable(applicationModel.stockMarketTableModel()));
	}

}