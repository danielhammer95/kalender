import java.util.Scanner;

public class DateUtil {
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

		return (5 * ((year -1) % 4) + 4 * ((year -1) % 100) + 6 * ((year -1) % 400)) % 7;
	}

	public static int firstDayOfMonth(byte month, short year) {

		int temp = firstDayOfYear(year);
		for (int i = 0; i < month; i++) {
			temp += daysOfMonth((byte)i, year);
		}
		return temp%7;
	}

	public static void printMonth(byte month, short year) {

		System.out.println(monat[month] + " " + year);

		for (int i = 0; i < tag.length; i++) {
			System.out.print(tag[i] + " ");
		}
		System.out.println("");

		byte zähler = 1;
		for (int i = 1; i <= firstDayOfMonth(month, year); i++) {
			System.out.print("   ");
			zähler++;
		}
		for (int i = 1; i < daysOfMonth(month, year)+1; i++) {
			if (i >= 10)
				System.out.print(i + " ");
			else
				System.out.print(" " + i + " ");

			if (zähler % 7 == 0)
				System.out.println("");
			zähler++;
		}
	}

	public static boolean checkDate(byte day, byte month, short year) {
		return (day >= 0 && day < daysOfMonth(month, year) && month >= 0 && month <=11  && year >= 1900 && year <= 2099);
	}

	public static int countDays(byte day, byte month, short year){
		short temp=0;
		for (int i=0; i<month; i++){
			temp+=daysOfMonth((byte)i, year);
		}
		return temp+day;
	}
	
}

