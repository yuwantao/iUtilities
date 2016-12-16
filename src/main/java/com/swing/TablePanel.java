package com.swing;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * Created by yuwt on 2016/12/8.
 */
public class TablePanel extends JPanel {
	public TablePanel() {
		super(new GridLayout(1, 0));

		JTable table = new JTable(new MyTableModel());
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		ButtonEditor buttonEditor = new ButtonEditor(new JTextField());
		ButtonRenderer2 buttonRenderer2 = new ButtonRenderer2();
		ButtonEditor2 buttonEditor2 = new ButtonEditor2(new JTextField());

		table.getColumn("select").setCellRenderer(buttonRenderer);
		table.getColumn("select").setCellEditor(buttonEditor);

//		table.addMouseListener(new JTableButtonMouseListener(table));

		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);

		add(scrollPane);
	}

	private static class JTableButtonMouseListener extends MouseAdapter {
		private final JTable table;

		public JTableButtonMouseListener(JTable table) {
			this.table = table;
		}

		public void mouseClicked(MouseEvent e) {
			int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
			int row    = e.getY()/table.getRowHeight(); //get the row of the button

                    /*Checking the row or column is valid or not*/
			if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
				Object value = table.getValueAt(row, column);
				if (value instanceof JButton) {
                    /*perform a click event*/
					((JButton)value).doClick();
				}
			}
		}
	}

	private static class JTableButtonRenderer implements TableCellRenderer {
		@Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			 JButton button = (JButton)value;
			System.out.println("renderer:" + ((JButton) value).getText());
			return button;
		}
	}

	private static class ButtonEditor  extends DefaultCellEditor{
		public ButtonEditor(JTextField textField) {
			super(textField);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			JButton button = (JButton) value;
			System.out.println("editor:" + ((JButton) value).getText());
//			if (isSelected) {
//				button.setForeground(table.getSelectionForeground());
//				button.setBackground(table.getSelectionBackground());
//			} else {
//				button.setForeground(table.getForeground());
//				button.setBackground(table.getBackground());
//			}
			if (isSelected) {
			}
//			JButton button = new JButton();
//			button.setText("clicked");
			return button;
		}

		@Override
		public boolean isCellEditable(EventObject anEvent) {
			return true;
		}

	}


	class ButtonEditor2 extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;

		public ButtonEditor2(JTextField checkBox) {
			super(checkBox);
			this.setClickCountToStart(1);
			button = new JButton();
			button.setOpaque(true);

		}

		public Component getTableCellEditorComponent(final JTable table, Object value,
		                                             boolean isSelected,int row, int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);

			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				//
				//
				// JOptionPane.showMessageDialog(button, label + ": Ouch!");
				// System.out.println(label + ": Ouch!");
			}
			isPushed = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			System.out.println(1);
			return super.shouldSelectCell(anEvent);
		}

	}

	class ButtonRenderer2 extends JButton implements TableCellRenderer {

		public ButtonRenderer2() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
		                                               boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("UIManager"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}


}
