package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
//person class
public abstract class Person  implements Serializable{
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate birthDay;
	private Gender gender;
	//constractur
	public Person(int id, String firstName, String lastName, LocalDate birthDay, Gender gender) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.gender = gender;
	}
	//partial constractur
	public Person(int id) {
		this.id = id;
	}
	//setters and getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	 //hashcode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
    //equals method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}
    //to String method
	@Override
	public String toString() {
		return "firstName=" + firstName + ", lastName=" + lastName + "";
	}
	
}
