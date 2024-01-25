package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
import Utils.Vehicle;
//delivery person class
public class DeliveryPerson extends Person  implements Serializable{
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Vehicle vehicle;
	private DeliveryArea area;
	 //constractur
	public DeliveryPerson(String firstName, String lastName, LocalDate birthDay, Gender gender, Vehicle vehicle,
			DeliveryArea area) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.vehicle = vehicle;
		this.area = area;
	}
	//setters and getters
	public DeliveryPerson(int id) {
		super(id);
	}
	
	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		DeliveryPerson.idCounter = idCounter;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public DeliveryArea getArea() {
		return area;
	}
	public void setArea(DeliveryArea area) {
		this.area = area;
	}
	//to string method
	@Override
	public String toString() {
		return super.toString()+"  vehicle=" + vehicle + "";
	}

}
