import java.io.Serializable;

public class Link <T extends Appointment> implements Serializable{
	
	public T daten;
	public Link naechster;
	
	public Link(T daten, Link naechster)
	{
		assert (daten!=null);
		this.daten=daten;
		this.naechster=naechster;
	}
}
