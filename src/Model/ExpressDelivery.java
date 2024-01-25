package Model;

import java.io.Serializable;
import java.time.LocalDate;
//express delivery class
//extends delivery
public class ExpressDelivery extends Delivery implements Serializable{
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private Order order;
	private double postage;
	private String type;
    //constractur 
	public ExpressDelivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered , Order order , double postage, LocalDate deliveredDate) {
		super(deliveryPerson, area, isDelivered, deliveredDate);
		this.order = order;
		this.postage = postage;
		setType("Express");
	}
	
	public ExpressDelivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered , Order order, LocalDate deliveredDate) {
		super(deliveryPerson, area, isDelivered,deliveredDate);
		this.order = order;
		this.postage = 5.0;
		setType("Express");

	}
	//setters and getters
	public ExpressDelivery(int id) {
		super(id);
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public double getPostage() {
		return postage;
	}
	
	public void setPostage(double postage) {
		this.postage = postage;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	//toString method
	@Override
	public String toString() {
		return "Express delivery [id=" + this.getId() + ", deliveryPerson=" + this.getDeliveryPerson() + ", area=" + this.getArea() + ", isDelivered="
				+ this.isDelivered()  + " postage=" + postage + "]";
	}

	
}
