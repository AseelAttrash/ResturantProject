package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Utils.DishType;
import Utils.MyFileLogWriter;
//dish class
public class Dish  implements Serializable{
	
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private int id;
	private String dishName;
	private DishType type;
	private ArrayList<Component> componenets;
	private double price;
	private int timeToMake;
	
	// constructors 
	
	public Dish(String dishName, DishType type, ArrayList<Component> componenets, int timeToMake) {
		super();
		this.id = idCounter++;
		this.dishName = dishName;
		this.type = type;
		this.componenets = componenets;
		this.timeToMake = timeToMake;
		price = calcDishPrice();
	}
	// getters setters
	
	public Dish(int id) {
		this.id = id;
	}

	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		Dish.idCounter = idCounter;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public DishType getType() {
		return type;
	}
	public void setType(DishType type) {
		this.type = type;
	}
	public List<Component> getComponenets() {
		return Collections.unmodifiableList(this.componenets);
	}
	public void setComponents(List<Component> arr)
	{
		this.componenets=(ArrayList<Component>) arr;
		this.price=calcDishPrice();
		
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if(price > 0.0)
			this.price = price;
		else
			price = 0.0;
	}

	public int getTimeToMake() {
		return timeToMake;
	}
	public void setTimeToMake(int timeToMake) {
		this.timeToMake = timeToMake;
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
		Dish other = (Dish) obj;
		if (id != other.id)
			return false;
		return true;
	}
	//toString
	@Override
	public String toString() {
		return "" + id + ", dishName=" + dishName + ", type=" + type + ", price=" + price + ", timeToMake="
				+ timeToMake + "";
	}
	
	
	//calculates dish price
	
	public double calcDishPrice() {
		double price = 0.0;
		for(Component c : getComponenets()) {
			price += c.getPrice();
		}
		price = price*3;
	
		return price;
	}
	//add component to dish
	public boolean addComponent(Component c) {
		return this.componenets.add(c);
	}
	//remove component from class
	public boolean removeComponent(Component c) {

Component comp =c;
 return this.componenets.remove(comp);
	}
}
