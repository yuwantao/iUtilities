package com.swing;

import javax.swing.*;

/**
 * Created by yuwt on 2016/12/8.
 */
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("TalbeDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TablePanel contentPane = new TablePanel();
		contentPane.setOpaque(true);
		frame.setContentPane(contentPane);

		frame.pack();
		frame.setVisible(true);
	}

//	Person.Builder;
}
