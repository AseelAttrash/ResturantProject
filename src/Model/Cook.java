package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Expertise;
import Utils.Gender;
//cook class 
//extends person
public class Cook extends Person implements Serializable{
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Expertise expert;
	private boolean isChef;
	//to update on table
	private String chef;
	 //cook constractur
	public Cook(String firstName, String lastName, LocalDate birthDay, Gender gender, Expertise expert,
			boolean isChef) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.expert = expert;
		this.isChef = isChef;
		//chef to fill in table
		setChef(isChef?"true":"false");
	}
	//setters and getters
	public Cook(int id) {
		super(id);
	}
	public Expertise getExpert() {
		return expert;
	}
	public void setExpert(Expertise expert) {
		this.expert = expert;
	}
	public boolean isChef() {
		return isChef;
	}
	public void setChef(boolean isChef) {
		setChef(isChef?"true":"false");
		this.isChef = isChef;
	}
	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		Cook.idCounter = idCounter;
	}
    //to string method
	@Override
	public String toString() {
		return super.toString()+" Cook [expert=" + expert + "]";
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}
}
