///////////////////////////////////////////////////////////////
// AUFGABE 9:												 //
// Es sind jeweils alle Methoden "static", damit das		 //
// Programm innerhalb der Klasse diese ausführen kann, ohne  //
// dass vorher ein Objekt erzeugt wurde.					 //
// Ansonsten müsste vorher ein Objekt existieren, worüber	 //
// die Methoden ausgeführt werden.							 //
///////////////////////////////////////////////////////////////

import java.util.Scanner;

public class Kalender {
	public static final String[] monat = { "Januar", "Februar", "März", "April", 
											"Mai", "Juni", "Juli", "August",
											"September", "Oktober", "November", "Dezember" };

	public static final String[] tag = { "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So" };

	public static boolean isLeapYear(short year) {
		return (year % 400 == 0) ? true : (year % 4 == 0) ? true : false;
	}

	public static int daysOfMonth(byte month, short year) {
		assert month <= 11 && month >= 0;
		assert year >= 0;
		switch (month) {
		case 1:
			return (isLeapYear(year)) ? 29 : 28;
		case 3:
		case 5:
		case 8:
		case 10:
			return 30;
		default:
			return 31;
			//keine "breaks", da mit return sowieso die Methode sofort verlassen wird.
		}

	}

	public static int firstDayOfYear(short year) {

		return (5 * ((year - 1) % 4) + 4 * ((year - 1) % 100) + 6 * ((year - 1) % 400)) % 7;
	}

	public static int firstDayOfMonth(byte month, short year) {

		short temp = 0;
		for (byte i = 0; i <= month; i++) {
			temp += daysOfMonth(i, year);
		}
		return (temp + firstDayOfYear(year)) % 7;
	}

	public static void printMonth(byte month, short year) {

		System.out.println(monat[month] + " " + year);

		for (int i = 0; i < tag.length; i++) {
			System.out.print(tag[i] + "\t");
		}
		System.out.println("");

		byte zähler = 1;
		for (int i = 1; i <= firstDayOfMonth(month, year); i++) {
			System.out.print("\t");
			zähler++;
		}
		for (int i = 1; i <= daysOfMonth(month, year); i++) {
			System.out.print(i + "\t");

			if (zähler % 7 == 0)
				System.out.println("");
			zähler++;
		}
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Monat: ");
		byte month = sc.nextByte();
		System.out.print("\nJahr: ");
		short year = sc.nextShort();

		if (month < 12 && month >= 0) {
			if (year >= 0) {
				printMonth(month, year);
			}
		} else
			System.out.println("Einhabe fehlerhaft. Bitte Programm neu laden!");

	}

}
