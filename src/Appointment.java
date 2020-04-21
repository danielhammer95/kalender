import java.io.Serializable;

public class Appointment implements Serializable {

	private String titel;
	private byte day;
	private byte month;
	private short year;
	private boolean allDay;
	private short start;
	private short length;

	public Appointment(Appointment ap) {
		if (ap != null){
		this.titel = ap.titel;
		this.day = ap.day;
		this.month = ap.month;
		this.year = ap.year;
		this.allDay = ap.allDay;
		this.start = ap.start;
		this.length = ap.length;
		}
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public byte getDay() {
		return day;
	}

	public void setDay(byte day) throws IllegalArgumentException {
		if (day <= 0 || day >= DateUtil.daysOfMonth(month, year)) {
			throw new IllegalArgumentException("Der Tag ist ung�ltig");
		}
		else
		this.day = day;
	}

	public byte getMonth() {
		return month;
	}

	public void setMonth(byte month) throws IllegalArgumentException {
		if (month < 0 || month > 11) {
			throw new IllegalArgumentException("Der Monat ist ung�ltig");
		}
		else
		this.month = month;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) throws IllegalArgumentException{
		if (year<1900||year>=2100){
			throw new IllegalArgumentException("Das Jahr ist ung�ltig");
		}
		else
		this.year = year;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public short getStart() {
		return start;
	}

	public void setStart(short start) throws IllegalStateException{
		if (allDay&&start!=0){
			throw new IllegalStateException("Es kann bei einem ganzt�gigen Termin kein Start festgelegt werden!");
		}
		this.start = start;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) throws IllegalStateException{
		if (allDay&&length!=0){
			throw new IllegalStateException("Der Termin ist ganzt�gig und kann keine L�nge besitzen!");
		}
		this.length = length;
	}
	public boolean isValid(){
		return DateUtil.checkDate(day, month, year)&&(start==0 && length==0) || !allDay;
	}
	public String toString() {
		return titel + "\n" + day + "  " + month + "  " + year + "\n" + start + "  " + length;
	}
}
