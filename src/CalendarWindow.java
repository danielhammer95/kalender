import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class CalendarWindow {

	private JFrame fenster;
	private JButton button1, button2, button3;
	private JLabel label1;
	private JTable table1;
	private byte month;
	private short year;
	private JScrollPane scroll1;
	GridBagConstraints gbc = new GridBagConstraints();
	Appointments ap = null;

	public CalendarWindow(Appointments ap) {

		this.ap = ap;
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		month = (byte) (localDate.getMonthValue() - 1);
		year = (short) localDate.getYear();
		lesen();

		fenster = new JFrame("Kalender");
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setSize(600, 600);
		fenster.setLayout(new GridBagLayout());

		button1 = new JButton("<");
		button1.addActionListener(new zurueckButtonListener());
		gbc.gridx = 1;
		gbc.gridy = 0;
		fenster.add(button1, gbc);

		button2 = new JButton(">");
		button2.addActionListener(new vorButtonListener());
		gbc.gridx = 3;
		gbc.gridy = 0;
		fenster.add(button2, gbc);

		button3 = new JButton("Wähle...");
		button3.addActionListener(new waehleListener());
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weighty = 1;
		fenster.add(button3, gbc);

		label1 = new JLabel();
		label1.setText(DateUtil.monat[month] + " " + year);
		gbc.gridx = 2;

		gbc.gridy = 0;
		fenster.add(label1, gbc);

		createTable(ap);

		fenster.addWindowListener(new schließenListener());

		fenster.setVisible(true);

	}

	private class schließenListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent e) {
			ObjectOutputStream output = null;
			try {
				File f = new File("save.ser");
				output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
				output.writeObject(ap);
				output.writeByte(month);
				output.writeShort(year);
				output.close();

			} catch (IOException ex) {
				ex.printStackTrace();

			} finally {

				if (output != null) {
					try {
						output.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		}

		@Override
		public void windowClosing(WindowEvent e) {
			ObjectOutputStream output = null;
			try {
				File f = new File("save.ser");
				output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
				output.writeObject(ap);
				output.writeByte(month);
				output.writeShort(year);
				output.close();

			} catch (IOException ex) {
				ex.printStackTrace();

			} finally {

				if (output != null) {
					try {
						output.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent e) {

		}

	}

	private void lesen() {
		ObjectInputStream input = null;
		try {
			File f = new File("save.ser");
			if (f.exists()) {
				input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
				ap = (Appointments) input.readObject();
				
				month = input.readByte();
				year = input.readShort();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private class zurueckButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (year >= 1900) {
					if (month > 0) {
						month--;
					} else {
						month += 11;
						year--;
					}
					button2.setEnabled(true);

					if (year == 1900 && month == 0) {
						button1.setEnabled(false);
						button2.setEnabled(true);
					}
					label1.setText(DateUtil.monat[month] + " " + year);
					scroll1.repaint();
				}

			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "ungültiges Datum");
			}
		}
	}

	private class waehleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				JPanel panel1 = new JPanel();
				panel1.setLayout(new GridBagLayout());
				JLabel label10 = new JLabel("Monat");
				gbc.insets = new Insets(4, 4, 4, 4);
				gbc.gridx = 0;
				gbc.gridy = 0;
				panel1.add(label10, gbc);

				JTextField textField10 = new JTextField(5);
				gbc.gridx = 1;
				gbc.gridy = 0;
				panel1.add(textField10, gbc);

				JLabel label20 = new JLabel("Jahr");
				gbc.gridx = 0;
				gbc.gridy = 1;
				panel1.add(label20, gbc);

				JTextField textField20 = new JTextField(5);
				gbc.gridx = 1;
				gbc.gridy = 1;
				panel1.add(textField20, gbc);
				int value = JOptionPane.showConfirmDialog(null, panel1, "Datum ", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (value == JOptionPane.OK_OPTION) {
					if (DateUtil.checkDate((byte) 1, (byte) (Integer.parseInt(textField10.getText()) - 1),
							Short.parseShort(textField20.getText()))) {
						month = Byte.parseByte(textField10.getText());
						month -= 1;
						year = Short.parseShort(textField20.getText());
						label1.setText(DateUtil.monat[month] + " " + year);
						scroll1.repaint();
						if (month == 0 && year == 1900) {
							button1.setEnabled(false);
							button2.setEnabled(true);
						} else if (month == 11 && year == 2099) {
							button1.setEnabled(true);
							button2.setEnabled(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, "ungültiges Datum");
					}
				}
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "ungültiges Datum");
			}
		}
	}

	private class vorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (year < 2100) {
					if (month >= 11) {
						month = 0;
						year++;
					} else {
						month++;
					}
					button1.setEnabled(true);
					if (year == 2099 && month == 11) {
						button1.setEnabled(true);
						button2.setEnabled(false);
					}
					label1.setText(DateUtil.monat[month] + " " + year);
					scroll1.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "ungültiges Datum");
				}
			} catch (Exception exc1) {
				JOptionPane.showMessageDialog(null, "ungültiges Datum");
			}
		}
	}

	private void createTable(Appointments ap) {
		TableMonthModel model = new TableMonthModel();

		table1 = new JTable(model);
		table1.setFillsViewportHeight(true);
		table1.addMouseListener(new doppelKlickTabelleListener());
		scroll1 = new JScrollPane(table1);
		gbc.gridy = 2;
		fenster.add(scroll1, gbc);

	}

	private class TableMonthModel extends AbstractTableModel {
		private String[] columnNames = { "Tag", "Termine" };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return DateUtil.daysOfMonth(month, year);
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			if (col == 0) {
				return row + 1;
			}

			for (byte i = 0; i < DateUtil.daysOfMonth(month, year); i++) {
				if (row == i) {
					return (ap.count(i, month, year) != 0) ? ap.count(i, month, year) + " Termin/e" : "";
				}
			}
			return "";
		}

	}

	private class doppelKlickTabelleListener implements MouseListener {

		public void mousePressed(MouseEvent e) {
			Point p = e.getPoint();
			int row = table1.rowAtPoint(p);
			if (e.getClickCount() == 2) {
				DayWindow run = new DayWindow(ap, (byte) table1.getSelectedRow(), month, year);
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
