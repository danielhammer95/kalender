import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class DayWindow {
	private Appointments ap;
	private JFrame fenster;
	private JButton button1, button2;
	private JLabel label1;
	private JTable table1;
	private JScrollPane scroll1;
	private byte day, month;
	private short year;
	GridBagConstraints gbc = new GridBagConstraints();


	public DayWindow(Appointments ap, byte day, byte month, short year) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.ap = ap;
		fenster = new JFrame("Tagesansicht");
		fenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fenster.setSize(600, 600);
		fenster.setLayout(new GridBagLayout());
		
		label1 = new JLabel();
		label1.setText(day + 1 + ". " + DateUtil.monat[month] + " " + year);
		gbc.gridx = 2;
		gbc.gridy = 0;
		fenster.add(label1, gbc);

		TableDayModel model = new TableDayModel();
		table1 = new JTable(model);
		table1.setFillsViewportHeight(true);
		table1.addMouseListener(new doppelklickListener());
		scroll1 = new JScrollPane(table1);
		gbc.gridy = 1;
		fenster.add(scroll1, gbc);

		button1 = new JButton("Hinzufügen");
		button1.addActionListener(new hinzufuegenListener());
		gbc.gridx = 2;
		gbc.gridy = 2;
		fenster.add(button1, gbc);

		button2 = new JButton("Löschen");
		button2.addActionListener(new loeschenListener());
		gbc.gridx = 3;
		gbc.gridy = 2;
		fenster.add(button2, gbc);

		fenster.setVisible(true);
	}

	private class TableDayModel extends AbstractTableModel {
		private String[] columnNames = { "#", "Beginn", "Betreff" };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			int zaehler = 0;
			Link<Appointment> temp = ap.buckets[year - 1900][DateUtil.countDays(day, month, year)];
			while (temp != null) {
				zaehler++;
				temp = temp.naechster;
			}
			return zaehler;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			Link<Appointment> tmp = ap.buckets[year - 1900][DateUtil.countDays(day, month, year)];

			for (int i = 0; i < row && tmp != null; i++)
				tmp = tmp.naechster;
			if (col == 0) {
				return row + 1;
			}
			if (col == 1) {
				return tmp.daten.getStart();
			}
			if (col == 2) {
				return tmp.daten.getTitel();
			}
			return "";
		}

	}

	private class loeschenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (table1.getSelectedRow() != -1) {
					Link<Appointment> tmp = ap.buckets[year - 1900][DateUtil.countDays(day, month, year)];
					for (int i = 0; i < table1.getSelectedRow(); i++) {
						tmp = tmp.naechster;
						System.out.println(i);
					}
					ap.remove(tmp.daten);
					scroll1.repaint();

				}
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Bitte einen Termin auswählen");
			}

		}
	}

	private class hinzufuegenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				AppointmentWindow run = new AppointmentWindow();
				int value = JOptionPane.showConfirmDialog(null, run.fenster, "Neuer Termin",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (value == JOptionPane.OK_OPTION && DateUtil.checkDate(day, month, year)) {
					Appointment ap1 = new Appointment(null);
					ap1.setTitel(run.textField1.getText());
					ap1.setDay((byte) (Integer.parseInt(run.textField2.getText()) - 1));
					ap1.setMonth((byte) (Integer.parseInt(run.textField3.getText()) - 1));
					ap1.setYear(Short.parseShort(run.textField4.getText()));
					ap1.setAllDay(run.box1.isSelected());
					if (!run.box1.isSelected()) {

						ap1.setStart(Short.parseShort(run.textField5.getText()));
						ap1.setLength((short) (Integer.parseInt(run.textField6.getText())
								- Integer.parseInt(run.textField5.getText())));
					}
					ap.add(ap1);
					scroll1.repaint();

				}
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "ungültiger Eintrag");
			}
		}
	}

	private class doppelklickListener implements MouseListener {
		public void mousePressed(MouseEvent e) {
			try {
				Point p = e.getPoint();
				int row = table1.rowAtPoint(p);
				if (e.getClickCount() == 2) {

					AppointmentWindow run = new AppointmentWindow();
					Link<Appointment> temp = ap.buckets[year - 1900][DateUtil.countDays(day, month, year)];
					for (int i = 0; i < table1.getSelectedRow() && temp != null; i++) {
						temp = temp.naechster;
					}
					run.textField1.setText(temp.daten.getTitel());
					run.textField2.setText(String.valueOf(temp.daten.getDay() + 1));
					run.textField3.setText(String.valueOf(temp.daten.getMonth() + 1));
					run.textField4.setText(String.valueOf(temp.daten.getYear()));
					if (temp.daten.isAllDay()) {
						run.box1.doClick();
					}
					run.textField5.setText(String.valueOf(temp.daten.getStart()));
					run.textField6.setText(String.valueOf(temp.daten.getStart() + temp.daten.getLength()));

					int value = JOptionPane.showConfirmDialog(null, run.fenster, "Neuer Termin",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if (value == JOptionPane.OK_OPTION && DateUtil.checkDate(day, month, year)) {
						ap.remove(temp.daten);
						Appointment ap1 = new Appointment(null);
						ap1.setTitel(run.textField1.getText());
						ap1.setAllDay(run.box1.isSelected());
						ap1.setDay((byte) (Integer.parseInt(run.textField2.getText()) - 1));
						ap1.setMonth((byte) (Integer.parseInt(run.textField3.getText()) - 1));
						ap1.setYear(Short.parseShort(run.textField4.getText()));
						ap1.setStart(Short.parseShort(run.textField5.getText()));
						ap1.setLength((short) (Integer.parseInt(run.textField6.getText())
								- Integer.parseInt(run.textField5.getText())));
						ap.add(ap1);
						scroll1.repaint();

					}
				}
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "ungültiges Datum");
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
