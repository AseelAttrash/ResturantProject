package Model;

import java.io.Serializable;
import java.time.LocalDate;
//delivery class
public abstract class Delivery  implements Serializable{
	/**
	 * 
	 */
	//delivery fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private int id;
	private DeliveryPerson deliveryPerson;
	private DeliveryArea area;
	private boolean isDelivered;
	private LocalDate deliveredDate;
	//to fill on table
	private String isdelivered;
	public String getIsdelivered() {
		return isdelivered;
	}
	public void setIsdelivered(String isdelivered) {
		this.isdelivered = isdelivered;
	}
	//delivery consractur
	public Delivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDate diliveredDate) {
		super();
		this.id = idCounter++;
		this.deliveryPerson = deliveryPerson;
		this.area = area;
		this.isDelivered = isDelivered;
		this.deliveredDate = diliveredDate;
		this.isdelivered=isDelivered?"YES":"No";
	}
	//setters and getters
	public static int getCounter() {
		return idCounter;
	}
	public Delivery(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DeliveryPerson getDeliveryPerson() {
		return deliveryPerson;
	}
	public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}
	public DeliveryArea getArea() {
		return area;
	}
	public void setArea(DeliveryArea area) {
		this.area = area;
	}
		public static void setCounter(int id) {
			idCounter=id;
		}
	public boolean isDelivered() {
		return isDelivered;
	}
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
		this.isdelivered=isDelivered?"YES":"No";
	}
	public LocalDate getDeliveredDate() {
		return deliveredDate;
	}
	public void setDeliveredDate(LocalDate deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	//hashcode method
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
		Delivery other = (Delivery) obj;
		if (id != other.id)
			return false;
		return true;
	}
    //to string method
	@Override
	public String toString() {
		return "Delivery [id=" + id + ", deliveryPerson=" + deliveryPerson + ", area=" + area + ", isDelivered="
				+ isDelivered + "]";
	}
}
