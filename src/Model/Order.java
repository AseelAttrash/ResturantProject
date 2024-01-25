package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Utils.MyFileLogWriter;
//order class
public class Order implements Comparable<Order>,Serializable{
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private Integer id;
	private Customer customer;
	private ArrayList<Dish> dishes;
	private Delivery delivery;
	
	// constructors 
	
	public Order(Customer customer, ArrayList<Dish> dishes, Delivery delivery) {
		super();
		this.id = idCounter++;
		this.customer = customer;
		this.dishes = dishes;
		this.delivery = delivery;
	}
	

	
	// getters setters
	public Order(int id) {
		this.id = id;
	}
	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Order.idCounter = idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Dish> getDishes() {
		return Collections.unmodifiableList(dishes);
	}
	public void SetDishes(ArrayList<Dish> dishes)
	{
		this.dishes=dishes;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
    //hashcode method
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	//to String method
	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + "]";
	}
	
	// methods
     //calculate revenue
	public double calcOrderRevenue() {
		double revenue = 0.0;
		for(Dish d : getDishes()) {
			double price = d.calcDishPrice();
			double cost = 0.0;
			for(Component c : d.getComponenets()) {
				cost += c.getPrice();
			}
			revenue += (price - cost);
		}
		MyFileLogWriter.println("Order Revenue = "+revenue);
		return revenue;
	}
	//order waitin time method
	public int orderWaitingTime(DeliveryArea da) {
		int time = 0;
		time += da.getDeliverTime();
		for(Dish d : getDishes()) {
			time += d.getTimeToMake();
		}
		MyFileLogWriter.println("Time for order: "+this+" is "+time+" minutes");
		return time;
	}

	//add dish to the class method
	public boolean addDish(Dish d) {
		return dishes.add(d);
	}
	//remove dish from order method
	public boolean removeDish(Dish d) {
		return dishes.remove(d);
	}
	//compare to method
	@Override
	public int compareTo(Order o) {
		return this.id.compareTo(o.getId());
	}

}
