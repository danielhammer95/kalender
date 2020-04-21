import javax.swing.JFrame;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Calendar {
	private static final long serialVersionUID = 12345L;

	private static Appointments testDaten() {
		Appointments testdaten = new Appointments();

		

		return testdaten;

	}

	public static void main(String[] args) {
		

		CalendarWindow run = new CalendarWindow(testDaten());

	}
}
