package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
//regular delivery class
public class RegularDelivery extends Delivery implements Serializable {
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private TreeSet<Order> orders;
	private String type;
	private transient String postage="";
	//constracturs
	public RegularDelivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDate deliveredDate) {
		super(deliveryPerson, area, isDelivered, deliveredDate);
		this.orders = new TreeSet<>();
		setType("Regular");
	}

	public RegularDelivery(TreeSet<Order> orders, DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDate deliveredDate) {
		super(deliveryPerson, area, isDelivered, deliveredDate);
		this.orders = orders;
		setType("Regular");

	}
	
	public RegularDelivery(int id) {
		super(id);
	}
//setters and getters
	public SortedSet<Order> getOrders() {
		return Collections.unmodifiableSortedSet(orders);
	}
	
	public void setOrders(TreeSet<Order> orders) {
		this.orders = orders;
	}
	
	//Methods
	//add order to class method
	public boolean addOrder(Order o) {
		return orders.add(o);
	}
	//remover order from delivery method
	public boolean removeOrder(Order o) {
		return orders.remove(o);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

}
