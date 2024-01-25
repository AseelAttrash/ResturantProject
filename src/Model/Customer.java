package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
import Utils.Neighberhood;
//customer class
//extends person
public class Customer extends Person  implements Serializable{
	
	/**
	 * 
	 */
	//customer fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Neighberhood neighberhood;
	//user name ,uniqe field for each customer
	private String username;
	//password field
	private String password;
	private boolean isSensitiveToLactose;
	private boolean isSensitiveToGluten;
	//to fill in table
	private String isLac;
	private String isGlu;
   //class constractur
	public Customer(String username,String pass,String firstName, String lastName, LocalDate birthDay, Gender gender,
			Neighberhood neighberhood,	boolean isSensitiveToLactose, boolean isSensitiveToGluten) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.password=pass;
		this.username=username;
		this.neighberhood = neighberhood;
		this.isSensitiveToLactose = isSensitiveToLactose;
		this.isSensitiveToGluten = isSensitiveToGluten;
		//fill on table
		setIsLac(isSensitiveToLactose?"True":"False");
		setIsGlu(isSensitiveToGluten?"True":"False");
	}
	//setters and getters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Customer(int id) {
		super(id);
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Customer.idCounter = idCounter;
	}

	public Neighberhood getNeighberhood() {
		return neighberhood;
	}

	public void setNeighberhood(Neighberhood neighberhood) {
		this.neighberhood = neighberhood;
	}

	public boolean isSensitiveToLactose() {
		return isSensitiveToLactose;
	}

	public void setSensitiveToLactose(boolean isSensitiveToLactose) {
		setIsLac(isSensitiveToLactose?"True":"False");
		
		this.isSensitiveToLactose = isSensitiveToLactose;
	}

	public boolean isSensitiveToGluten() {
		return isSensitiveToGluten;
	}

	public void setSensitiveToGluten(boolean isSensitiveToGluten) {
		setIsGlu(isSensitiveToGluten?"True":"False");
		this.isSensitiveToGluten = isSensitiveToGluten;
	}
    //toString method
	@Override
	public String toString() {
		return super.toString()+" Customer [isSensitiveToLactose=" + isSensitiveToLactose + ", isSensitiveToGluten=" + isSensitiveToGluten
				+ "]";
	}

	public String getIsLac() {
		return isLac;
	}

	public void setIsLac(String isLac) {
		this.isLac = isLac;
	}

	public String getIsGlu() {
		return isGlu;
	}

	public void setIsGlu(String isGlu) {
		this.isGlu = isGlu;
	}
}
