import java.io.Serializable;

public class Appointments implements Serializable {

	public Link<Appointment>[][] buckets = (Link<Appointment>[][]) new Link[200][366];

	public boolean add(Appointment ap) {

		if (!ap.isValid())
			return false;

		Link<Appointment> zeiger = buckets[ap.getYear() - 1900][DateUtil.countDays(ap.getDay(), ap.getMonth(),
				ap.getYear())];

		buckets[ap.getYear() - 1900][DateUtil.countDays(ap.getDay(), ap.getMonth(), ap.getYear())] = new Link(ap,
				zeiger);
		return true;

	}

	public void remove(Appointment ap) {
		int x = ap.getYear() - 1900;
		int y = DateUtil.countDays(ap.getDay(), ap.getMonth(), ap.getYear());
		Link<Appointment> zeiger = buckets[ap.getYear() - 1900][DateUtil.countDays(ap.getDay(), ap.getMonth(),
				ap.getYear())];
		Link<Appointment> vorg�nger = null;
		zeiger = buckets[x][y];
		if (ap.isValid()) {
			while (zeiger != null) {
				if (zeiger.daten == ap) {

					if (vorg�nger == null) {
						buckets[x][y] = zeiger.naechster;
					} else {
						vorg�nger.naechster = zeiger.naechster;
					}

					return;
				}

				vorg�nger = zeiger;
				zeiger = zeiger.naechster;
			}

		}
	}

	public void removeAll(byte day, byte month, short year) {
		buckets[year - 1900][DateUtil.countDays(day, month, year)] = null;
	}

	public int count(byte tag, byte monat, short jahr) {
		Link<Appointment> zeiger = buckets[jahr - 1900][DateUtil.countDays(tag, monat, jahr)];
		int z�hler = 0;
		if (DateUtil.checkDate(tag, monat, jahr)) {
			while (zeiger != null) {
				z�hler++;
				zeiger = zeiger.naechster;
			}
			return z�hler;
		}
		return 0;
	}

	public void print(byte day, byte month, short year) {
		if (!DateUtil.checkDate(day, month, year))
			throw new IllegalArgumentException("Ung�ltiges Datum!");

		Link<Appointment> zeiger = buckets[year - 1900][DateUtil.countDays(day, month, year)];
		while (zeiger != null) {
			System.out.println(zeiger.daten);
			System.out.println("");
			zeiger = zeiger.naechster;
		}
	}

	public void printAll() {

		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[i].length; j++) {
				Link<Appointment> zeiger = buckets[i][j];
				while (zeiger != null) {
					System.out.println(zeiger.daten);
					System.out.println("");
					zeiger = zeiger.naechster;
				}
			}
		}
	}

	public boolean check() {
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[i].length; j++) {
				Link<Appointment> zeiger = buckets[i][j];
				while (zeiger != null) {
					if (zeiger.daten.getYear() - 1900 != i || DateUtil.countDays(zeiger.daten.getDay(),
							zeiger.daten.getMonth(), zeiger.daten.getYear()) != j) {
						return false;
					}
					zeiger = zeiger.naechster;
				}
			}
		}
		return true;
	}
}