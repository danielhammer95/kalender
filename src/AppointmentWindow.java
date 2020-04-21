import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;

public class AppointmentWindow {
	public JPanel fenster;
	public JLabel label1, label2, label3, label4, label5, label6, label7;
	public JTextField textField1, textField2, textField3, textField4, textField5, textField6;
	public JCheckBox box1;
	public JButton button1, button2;
	GridBagConstraints gbc = new GridBagConstraints();

	public AppointmentWindow() {
		
		fenster = new JPanel();
		fenster.setSize(600, 600);
		fenster.setLayout(new GridBagLayout());

		label1 = new JLabel("Betreff ");
		gbc.insets = new Insets(4, 4, 4, 4);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = gbc.FIRST_LINE_START;
		fenster.add(label1, gbc);
		textField1 = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 0;
		fenster.add(textField1, gbc);
		label2 = new JLabel("Tag ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		fenster.add(label2, gbc);
		textField2 = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		fenster.add(textField2, gbc);
		label3 = new JLabel("Monat ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		fenster.add(label3, gbc);
		textField3 = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 2;
		fenster.add(textField3, gbc);
		label4 = new JLabel("Jahr ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		fenster.add(label4, gbc);
		textField4 = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 3;
		fenster.add(textField4, gbc);
		label5 = new JLabel("Ganztag ");
		gbc.gridx = 1;
		gbc.gridy = 4;
		fenster.add(label5, gbc);
		box1 = new JCheckBox();
		box1.addActionListener(new box1PressedListener());
		gbc.gridx = 0;
		gbc.gridy = 4;
		fenster.add(box1, gbc);
		label6 = new JLabel("Beginn ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		fenster.add(label6, gbc);
		textField5 = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 5;
		fenster.add(textField5, gbc);
		label7 = new JLabel("Ende ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		fenster.add(label7, gbc);
		textField6 = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 6;
		fenster.add(textField6, gbc);
	}

	private class box1PressedListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (box1.isSelected()) {
				textField5.setEnabled(false);
				textField6.setEnabled(false);
				textField5.setText("");
				textField6.setText("");
			} else {
				textField5.setEnabled(true);
				textField6.setEnabled(true);
			}
		}
	}
}
