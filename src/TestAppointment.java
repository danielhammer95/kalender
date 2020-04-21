
public class TestAppointment {
	public static void main(String[] args){
		Appointment test = new Appointment(null);
		test.setTitel("Anwalt");
		test.setAllDay(false);
		test.setDay((byte)10);
		test.setLength((byte)21);
		test.setMonth((byte)6);
		test.setYear((short)2016);
		test.setStart((short)600);
		
		Appointment testSchaltjahr = new Appointment(null);
		testSchaltjahr.setTitel("Schaltjahr");
		testSchaltjahr.setAllDay(true);
		testSchaltjahr.setDay((byte)28);
		testSchaltjahr.setLength((byte)0);
		testSchaltjahr.setMonth((byte)1);
		testSchaltjahr.setYear((short)2020);
		testSchaltjahr.setStart((short)0);
		
//		Appointment fehler = new Appointment(null);
//		fehler.setTitel("Egal");
//		fehler.setAllDay(true);
//		fehler.setDay((byte)28);
//		fehler.setLength((byte)0);
//		fehler.setMonth((byte)1);
//		fehler.setYear((short)2016);
//		fehler.setStart((short)600);
		
		Appointments verwaltung = new Appointments();
		verwaltung.add(test);
		verwaltung.add(testSchaltjahr);
	
		
		
		//verwaltung.print((byte)10, (byte)6, (short)2016);
		verwaltung.printAll();
		//verwaltung.removeAll((byte)10, (byte)6, (short)2016);
		//testSchaltjahr.setMonth((byte)5);
		System.out.println(verwaltung.check());
	}
}
