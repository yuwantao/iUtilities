package com.swing;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwt on 2016/12/8.
 */
public class MyTableModel extends AbstractTableModel {
	private String[] columnNames = {"name", "phone", "select"};
	private Object[][] data = {
			{"yuwantao", "101", false},
			{"michael", "102", true},
			{"jack", "103", false}
	};
	private Map<String, JButton> buttonMap = new HashMap<>();

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}


	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 2) {
//			return getButton1(rowIndex);
			return getButton2(rowIndex);
		} else {
			return data[rowIndex][columnIndex];
		}
	}

	private Object getButton1(int rowIndex) {
		System.out.println("new button");
		JButton button = new JButton("AddToCart" + rowIndex);
		JButton finalButton = button;
		button.addActionListener(e -> {
			System.out.println("event happend");
			JButton source = (JButton) e.getSource();
//					System.out.println(source.getClass());
//				finalButton.setText("clicked" + rowIndex);
//				button.setEnabled(false);

			source.setText("clicked");
//					source.setEnabled(false);
				Frame frameForComponent = JOptionPane.getFrameForComponent(source);
//					frameForComponent.pack();
//					frameForComponent.repaint();
				JOptionPane.showMessageDialog(frameForComponent,
						"Button clicked: "+ data[rowIndex][0]);
		});
		return button;
	}

	private Object getButton2(int rowIndex) {
		JButton button = buttonMap.get(String.valueOf(rowIndex));
		if (button == null) {
			System.out.println("map size:" + buttonMap.size());
			button = new JButton("AddToCart" + rowIndex);
			buttonMap.put(String.valueOf(rowIndex), button);
			JButton finalButton = button;
			button.addActionListener(e -> {
				System.out.println("event happend");
				JButton source = (JButton) e.getSource();
					System.out.println(source.getText());
//				finalButton.setText("clicked" + rowIndex);
//				button.setEnabled(false);

				source.setText("clicked");
					source.setEnabled(false);
//				Frame frameForComponent = JOptionPane.getFrameForComponent(source);
////					frameForComponent.pack();
////					frameForComponent.repaint();
//				JOptionPane.showMessageDialog(frameForComponent,
//						"Button clicked: "+ data[rowIndex][0]);
			});
		}
		return button;
	}

	@Override
	public Class getColumnClass(int c) {
		if (c == 2) {
			return JButton.class;
		} else {
			return getValueAt(0, c).getClass();
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		if (col == 2) {
			return true;
		} else {
			return false;
		}
	}
//
//	public void setValueAt(Object value, int row, int col) {
//		data[row][col] = value;
//		fireTableCellUpdated(row, col);
//	}
}
