package com.swing;

import javax.swing.*;

/**
 * Created by yuwt on 2016/12/8.
 */
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("TalbeDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComboBox<Integer> c = new JComboBox<>();
		for (int i = 1; i <= 5; i++) {
			c.addItem(i);
		}
		c.addActionListener(e -> {
			int item = (Integer) ((JComboBox) e.getSource()).getSelectedItem();
			System.out.println(item);
		});

		frame.getContentPane().add(c);
		System.out.println(c.getSelectedItem());

		frame.pack();
		frame.setVisible(true);
	}

//	Person.Builder;
}
