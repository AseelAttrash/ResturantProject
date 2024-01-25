package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import Exceptions.ConvertToExpressException;
import Exceptions.IllegalCustomerException;
import Exceptions.NoComponentsExceptions;
import Exceptions.SensitiveException;
import Utils.Expertise;
import Utils.Neighberhood;
//restaurant class
public class Restaurant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Restaurant restaurant = null;
    //class hashmaps
	private HashMap<Integer, Cook> cooks;
	private HashMap<Integer, DeliveryPerson> deliveryPersons;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Dish> dishes;
	private HashMap<Integer, Component> componenets;
	private HashMap<Integer, Order> orders;
	private HashMap<Integer, DeliveryArea> areas;
	private HashMap<Integer, ExpressDelivery> Expressdeliveries;
	private HashMap<Integer, RegularDelivery> Regulardeliveries;

	/* NEW */
	private HashMap<Customer, TreeSet<Order>> orderByCustomer;
	private HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea;
	private HashSet<Customer> blackList;
    //constractur  and getInstance
	public static Restaurant getInstance() {
		restaurant = new Restaurant();
            //ser file
		    try
	        {
	        	File serfile = new File("Rest.ser");
	        	if (serfile.isFile()==false || serfile.canRead()==false)
					return restaurant;
	            FileInputStream file = new FileInputStream(serfile);
	            ObjectInputStream input = new ObjectInputStream(file); 
	           
	            restaurant = (Restaurant)input.readObject(); 
	              
	            input.close(); 
	            file.close(); 
	            Component.setIdCounter(restaurant.getComponenets().size()+1);
	            Cook.setIdCounter(restaurant.getCooks().size()+1);
	            Customer.setIdCounter(restaurant.getCustomers().size()+1);
	            Delivery.setCounter(restaurant.getExpressdeliveries().size()+restaurant.getRegulardeliveries().size()+1);
	            DeliveryArea.setIdCounter(restaurant.getAreas().size()+1);
	            DeliveryPerson.setIdCounter(restaurant.getDeliveryPersons().size()+1);
	            Dish.setIdCounter(restaurant.getDishes().size()+1);
	            Order.setIdCounter(restaurant.getOrderByCustomer().size()+1);
	            
		}
		 catch(IOException e) 
	        { 
	            System.out.println("ser file not found"); 

	        } 
	          
	        catch(ClassNotFoundException e) 
	        { 
	            System.out.println("class exception"); 

	        }		
		    return restaurant;
	}

	private Restaurant() {
		cooks = new HashMap<>();
		deliveryPersons = new HashMap<>();
		customers = new HashMap<>();
		dishes = new HashMap<>();
		componenets = new HashMap<>();
		orders = new HashMap<>();
		Expressdeliveries = new HashMap<>();
		Regulardeliveries = new HashMap<>();

		areas = new HashMap<>();
		orderByCustomer = new HashMap<>();
		orderByDeliveryArea = new HashMap<>();
		blackList = new HashSet<>();
	}
 
	//setters getter
	public HashMap<Integer, Cook> getCooks() {
		return cooks;
	}

	public void setCooks(HashMap<Integer, Cook> cooks) {
		this.cooks = cooks;
	}

	public HashMap<Integer, DeliveryPerson> getDeliveryPersons() {
		return deliveryPersons;
	}

	public void setDeliveryPersons(HashMap<Integer, DeliveryPerson> deliveryPersons) {
		this.deliveryPersons = deliveryPersons;
	}

	public HashMap<Integer, Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(HashMap<Integer, Customer> customers) {
		this.customers = customers;
	}

	
	public HashMap<Integer, ExpressDelivery> getExpressdeliveries() {
		return Expressdeliveries;
	}

	public void setExpressdeliveries(HashMap<Integer, ExpressDelivery> expressdeliveries) {
		Expressdeliveries = expressdeliveries;
	}

	public HashMap<Integer, RegularDelivery> getRegulardeliveries() {
		return Regulardeliveries;
	}

	public void setRegulardeliveries(HashMap<Integer, RegularDelivery> regulardeliveries) {
		Regulardeliveries = regulardeliveries;
	}
	public HashMap<Integer, Dish> getDishes() {
		return dishes;
	}

	public void setDishes(HashMap<Integer, Dish> dishes) {
		this.dishes = dishes;
	}

	public HashMap<Integer, Component> getComponenets() {
		return componenets;
	}

	public void setComponenets(HashMap<Integer, Component> componenets) {
		this.componenets = componenets;
	}

	public HashMap<Integer, Order> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Integer, Order> orders) {
		this.orders = orders;
	}

	public HashMap<Integer, DeliveryArea> getAreas() {
		return areas;
	}

	public void setAreas(HashMap<Integer, DeliveryArea> areas) {
		this.areas = areas;
	}

	public HashMap<Customer, TreeSet<Order>> getOrderByCustomer() {
		return orderByCustomer;
	}

	public void setOrderByCustomer(HashMap<Customer, TreeSet<Order>> orderByCustomer) {
		this.orderByCustomer = orderByCustomer;
	}

	public HashMap<DeliveryArea, HashSet<Order>> getOrderByDeliveryArea() {
		return orderByDeliveryArea;
	}

	public void setOrderByDeliveryArea(HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea) {
		this.orderByDeliveryArea = orderByDeliveryArea;
	}

	public HashSet<Customer> getBlackList() {
		return blackList;
	}

	public void setBlackList(HashSet<Customer> blackList) {
		this.blackList = blackList;
	}
   //add cook method
	public boolean addCook(Cook cook) {
		if (cook == null || getCooks().containsKey(cook.getId()))
			return false;

		return getCooks().put(cook.getId(), cook) == null;
	}
      //add delivery person method
	public boolean addDeliveryPerson(DeliveryPerson dp, DeliveryArea da) {
		if (dp == null || getDeliveryPersons().containsKey(dp.getId()) || !getAreas().containsKey(da.getId()))
			return false;

		return deliveryPersons.put(dp.getId(), dp) == null && getAreas().get(da.getId()).addDelPerson(dp);
	}
     //add customer method
	public boolean addCustomer(Customer cust) {
		if (cust == null || getCustomers().containsKey(cust.getId()))
			return false;
		for (Customer co : this.customers.values()) {
			if (co.getUsername().equals(cust.getUsername())) {
				return false;
			}
		}
		return getCustomers().put(cust.getId(), cust) == null;
	}
     //add dish method
	public boolean addDish(Dish dish) {
		if (dish == null || getDishes().containsKey(dish.getId()))
			return false;
		for (Component c : dish.getComponenets()) {
			if (!getComponenets().containsKey(c.getId()))
				return false;
		}

		return getDishes().put(dish.getId(), dish) == null;
	}
    //add component method
	public boolean addComponent(Component comp) {
		if (comp == null || getComponenets().containsKey(comp.getId()))
			return false;

		return getComponenets().put(comp.getId(), comp) == null;
	}
     //add order method
	public boolean addOrder(Order order) {
		if(order == null) {
		}if( getOrders().containsKey(order.getId())) {
		}if(order.getCustomer() == null) {
		}if(!getCustomers().containsKey(order.getCustomer().getId())) {
		}

		try {
			if (order == null || getOrders().containsKey(order.getId()))
				return false;
			if (order.getCustomer() == null || !getCustomers().containsKey(order.getCustomer().getId()))
				return false;
			if (getBlackList().contains(order.getCustomer())) {
				throw new IllegalCustomerException();
			}
			for (Dish d : order.getDishes()) {
				
				if (!getDishes().containsKey(d.getId())) {
					return false;
				}
				for (Component c : d.getComponenets()) {
					if (order.getCustomer().isSensitiveToGluten() && c.isHasGluten()) {
						throw new SensitiveException(
								order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(),
								d.getDishName());
					} else if (order.getCustomer().isSensitiveToLactose() && c.isHasLactose()) {
						throw new SensitiveException(
								order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(),
								d.getDishName());
					}
				}
			}
			return getOrders().put(order.getId(), order) == null;
		} catch (SensitiveException e) {
			return false;
		} catch (IllegalCustomerException e) {
			return false;
		}
	}
	//order comperator
	private static class OrderComparator implements Comparator<Order>, Serializable {
	     
		private static final long serialVersionUID = 1L;   
		@Override
		public int compare(Order o1, Order o2) {
			// TODO Auto-generated method stub
			if(o1.getDelivery()==null || o2.getDelivery()==null)
			{
				return 1;
			}
			return o1.getDelivery().getDeliveredDate()
					.compareTo(o2.getDelivery().getDeliveredDate());
		}
		

		
	}
	//add delivery method
	public boolean addDelivery(Delivery delivery) {
		
		try {
			if (delivery == null || !getDeliveryPersons().containsKey(delivery.getDeliveryPerson().getId())) {
				return false;
			}
			if (delivery instanceof RegularDelivery) {
				RegularDelivery rd = (RegularDelivery) delivery;
				for (Order o : rd.getOrders()) {
					if (!getOrders().containsKey(o.getId()))
						return false;
					o.setDelivery(delivery);
				}
				if (rd.getOrders().size() == 1) {
					throw new ConvertToExpressException();
				}
				if (rd.getOrders().isEmpty())
					return false;
			} else {
				ExpressDelivery ed = (ExpressDelivery) delivery;
				if (!getOrders().containsKey(ed.getOrder().getId())) {
					return false;
				}
				ed.getOrder().setDelivery(delivery);
			}
		} catch (ConvertToExpressException e) {
			RegularDelivery rd = (RegularDelivery) delivery;
			delivery = new ExpressDelivery(rd.getDeliveryPerson(), rd.getArea(), rd.isDelivered(),
					rd.getOrders().first(), rd.getDeliveredDate());
		} finally {
			delivery.getArea().addDelivery(delivery);
			if (delivery instanceof RegularDelivery) {
				RegularDelivery rg = (RegularDelivery) delivery;
				for (Order o : rg.getOrders()) {
					TreeSet<Order> orders = orderByCustomer.get(o);
					if (orders == null)
						orders = new TreeSet<>(new OrderComparator());
					orders.add(o);
					orderByCustomer.put(o.getCustomer(), orders);

				}
				Regulardeliveries.put(rg.getId(), rg);

			} else {
				ExpressDelivery ex = (ExpressDelivery) delivery;
				TreeSet<Order> orders = orderByCustomer.get(ex.getOrder());
				if (orders == null)
					orders = new TreeSet<>(new OrderComparator());
				orders.add(ex.getOrder());
				orderByCustomer.put(ex.getOrder().getCustomer(), orders);
				Expressdeliveries.put(ex.getId(), ex);
			}
		}
		return true;
	}
    //add delivery area
	public boolean addDeliveryArea(DeliveryArea da) {
		if (da == null || getAreas().containsKey(da.getId()))
			return false;
		return getAreas().put(da.getId(), da) == null;
	}
    //add customer to black list
	public boolean addCustomerToBlackList(Customer c) {
		if (c == null)
			return false;
		return getBlackList().add(c);
	}
    //remove cook 
	public boolean removeCook(Cook cook) {
		if (cook == null || !getCooks().containsKey(cook.getId()))
			return false;
		return getCooks().remove(cook.getId()) != null;
	}
    //remove delivery person method
	public boolean removeDeliveryPerson(DeliveryPerson dp) {
		if (dp == null || !getDeliveryPersons().containsKey(dp.getId()))
			return false;
		for (Delivery d : getExpressdeliveries().values()) {
			if (d.getDeliveryPerson().equals(dp)) {
				d.setDeliveryPerson(null);
			}
		}
		for (Delivery d : getRegulardeliveries().values()) {
			if (d.getDeliveryPerson().equals(dp)) {
				d.setDeliveryPerson(null);
			}
		}
		return getDeliveryPersons().remove(dp.getId()) != null && dp.getArea().removeDelPerson(dp);
	}
    //remove customer method
	public boolean removeCustomer(Customer cust) {
		if (cust == null || !getCustomers().containsKey(cust.getId()))
			return false;
		return getCustomers().remove(cust.getId()) != null;
	}
    //remove dish method
	public boolean removeDish(Dish dish) {
		if (dish == null || !getDishes().containsKey(dish.getId()))
			return false;
		for (RegularDelivery d : getRegulardeliveries().values()) {
			if (!d.isDelivered()) {
				for (Order o : d.getOrders()) {
					o.removeDish(dish);
				}
			}
		}
		for (ExpressDelivery ed : getExpressdeliveries().values()) {
			if (!ed.isDelivered()) {
				ed.getOrder().removeDish(dish);

			}
		}

		return getDishes().remove(dish.getId()) != null;
	}
        //remove component method
	public boolean removeComponent(Component comp) {
		Dish removeDish = null;
		try {
			if (comp == null || !getComponenets().containsKey(comp.getId()))
				return false;
			for (Dish d : getDishes().values()) {
				d.removeComponent(comp);
			
				if (d.getComponenets().isEmpty()) {
					removeDish = d;
					throw new NoComponentsExceptions(d);
				}
			}
		} catch (NoComponentsExceptions e) {

			removeDish(removeDish);
		}
		return getComponenets().remove(comp.getId()) != null;
	}
     //remove order method
	public boolean removeOrder(Order order) {
		if (order == null || !getOrders().containsKey(order.getId()))
			return false;
		if (getOrders().remove(order.getId()) != null) {
			if (order.getDelivery() == null) {
				return true;
			}

			if (order.getDelivery() != null) {
				if (order.getDelivery() instanceof RegularDelivery) {
					RegularDelivery rd = (RegularDelivery) order.getDelivery();
					return rd.removeOrder(order);
				}
			} else {
				if (order.getDelivery() != null) {
					ExpressDelivery ed = (ExpressDelivery) order.getDelivery();
					ed.setOrder(null);
					return true;
				}
			}
		}
		return false;
	}
    //remove delivery method
	public boolean removeDelivery(Delivery delivery) {
		if (delivery == null && !getRegulardeliveries().containsKey(delivery.getId())&& !getExpressdeliveries().containsKey(delivery.getId()))
			return false;
		if (delivery instanceof RegularDelivery) {
			RegularDelivery rd = (RegularDelivery) delivery;
			for (Order o : rd.getOrders()) {
				o.setDelivery(null);
			}
			getRegulardeliveries().remove(delivery.getId());
		} else {
			ExpressDelivery ed = (ExpressDelivery) delivery;
			ed.getOrder().setDelivery(null);
			getExpressdeliveries().remove(delivery.getId());
		}
		return delivery.getArea().removeDelivery(delivery);
	}
    //remove delivery area method
	public boolean removeDeliveryArea(DeliveryArea oldArea, DeliveryArea newArea) {
		if (oldArea == null || newArea == null || !getAreas().containsKey(oldArea.getId())
				|| !getAreas().containsKey(newArea.getId()))
			return false;
		for (Neighberhood n : oldArea.getNeighberhoods()) {
			newArea.addNeighberhood(n);
		}
		for (Delivery d : oldArea.getDelivers()) {
			newArea.addDelivery(d);
		}
		for (DeliveryPerson dp : oldArea.getDelPersons()) {
			newArea.addDelPerson(dp);
		}
		for (DeliveryPerson dp : oldArea.getDelPersons()) {
			dp.setArea(newArea);
		}
		for (Delivery d : oldArea.getDelivers()) {
			d.setArea(newArea);
		}
		return getAreas().remove(oldArea.getId()) != null;
	}
	
    //get real methods
	
	
	public Cook getRealCook(int id) {
		return getCooks().get(id);
	}

	public DeliveryPerson getRealDeliveryPerson(int id) {
		return getDeliveryPersons().get(id);
	}

	public Customer getRealCustomer(int id) {
		return getCustomers().get(id);
	}

	public Dish getRealDish(int id) {
		return getDishes().get(id);
	}

	public Component getRealComponent(int id) {
		return getComponenets().get(id);
	}

	public Order getRealOrder(int id) {
		return getOrders().get(id);
	}

	public Delivery getRealDelivery(int id) {
		if (getExpressdeliveries().containsKey(id))
			return getExpressdeliveries().get(id);
		return getRegulardeliveries().get(id);

	}

	public DeliveryArea getRealDeliveryArea(int id) {
		return getAreas().get(id);
	}

	/* QUEREIES */
	public Collection<Dish> getReleventDishList(Customer c) {
		ArrayList<Dish> dishList = new ArrayList<>();
		if (!c.isSensitiveToGluten() && !c.isSensitiveToLactose())
			return getDishes().values();
		for (Dish d : getDishes().values()) {
			boolean isValid = true;
			for (Component comp : d.getComponenets()) {
				if (c.isSensitiveToGluten() && c.isSensitiveToLactose()) {
					if (comp.isHasGluten() || comp.isHasLactose())
						isValid = false;
				} else if (c.isSensitiveToGluten() && comp.isHasGluten()) {
					isValid = false;
				} else if (c.isSensitiveToLactose() && comp.isHasLactose()) {
					isValid = false;
				}
			}
			if (isValid)
				dishList.add(d);
		}
		return dishList;
	}

	public void deliver(Delivery d) {
		d.setDelivered(true);
		if (d instanceof RegularDelivery) {
			RegularDelivery rd = (RegularDelivery) d;
			for (Order o : rd.getOrders()) {
			}
		} else {
			ExpressDelivery ed = (ExpressDelivery) d;
		}

	}

	public ArrayList<Cook> GetCooksByExpertise(Expertise e) {
		ArrayList<Cook> cooks = new ArrayList<>();
		for (Cook c : getCooks().values()) {
			if (c.getExpert().equals(e))
				cooks.add(c);
		}
		return cooks;
	}

	/* NEW QUERIES */
	public TreeSet<Delivery> getDeliveriesByPerson(DeliveryPerson dp, int month) {
		TreeSet<Delivery> delivered = new TreeSet<>(new Comparator<Delivery>() {

			@Override
			public int compare(Delivery o1, Delivery o2) {
				if (o1.getDeliveredDate().getDayOfMonth() > o2.getDeliveredDate().getDayOfMonth())
					return 1;
				if (o1.getDeliveredDate().getDayOfMonth() < o2.getDeliveredDate().getDayOfMonth())
					return -1;
				return o1.getId() > o2.getId() ? 1 : -1;
			}
		});
		for (RegularDelivery d : getRegulardeliveries().values()) {
			if (d.getDeliveryPerson().equals(dp) && d.getDeliveredDate().getMonthValue() == month)
				delivered.add(d);
		}
		return delivered;
	}

	public HashMap<String, Integer> getNumberOfDeliveriesPerType() {
		HashMap<String, Integer> deliveriesPerType = new HashMap<>();
		deliveriesPerType.put("Regular delivery", 0);
		deliveriesPerType.put("Express delivery", 0);
		int amount;
		for (Delivery d : getRegulardeliveries().values()) {
			LocalDate today = LocalDate.now();
			if (d instanceof RegularDelivery && d.getDeliveredDate().getYear() == today.getYear()) {
				amount = deliveriesPerType.get("Regular delivery");
				deliveriesPerType.put("Regular delivery", amount + 1);
			}
		}

		for (Delivery d : getExpressdeliveries().values()) {
			LocalDate today = LocalDate.now();

			if (d.getDeliveredDate().getYear() == today.getYear()) {
				amount = deliveriesPerType.get("Express delivery");
				deliveriesPerType.put("Express delivery", amount + 1);

			}
		}

		return deliveriesPerType;
	}

	public double revenueFromExpressDeliveries() {
		HashSet<Customer> customers = new HashSet<>();
		double amountOfPostages = 0;
		for (Delivery d : getRegulardeliveries().values()) {
			if (d instanceof ExpressDelivery) {
				ExpressDelivery ed = (ExpressDelivery) d;
				amountOfPostages += ed.getPostage();
				customers.add(ed.getOrder().getCustomer());
			}
		}
		amountOfPostages += (30 * customers.size());
		return amountOfPostages;
	}

	public LinkedList<Component> getPopularComponents() {
		HashMap<Component, Integer> componentsandAmount = new HashMap<>();
		for (Dish d : getDishes().values()) {
			for (Component c : d.getComponenets()) {
				Integer numOfComponent = componentsandAmount.get(c);
				if (numOfComponent == null)
					numOfComponent = 0;
				componentsandAmount.put(c, numOfComponent + 1);
			}
		}
		LinkedList<Component> popularComponents = new LinkedList<>();
		for (Component c : componentsandAmount.keySet()) {
			popularComponents.add(c);
		}
		popularComponents.sort(new Comparator<Component>() {

			@Override
			public int compare(Component o1, Component o2) {
				int result = -1 * componentsandAmount.get(o1).compareTo(componentsandAmount.get(o2));
				if (result != 0)
					return result;
				if (o1.getId() > o2.getId())
					return -1;
				return 1;
			}
		});
		return popularComponents;
	}

	public TreeSet<Dish> getProfitRelation() {
		TreeSet<Dish> profit = new TreeSet<Dish>((Dish o1, Dish o2) -> {
			if ((o2.getPrice() / o2.getTimeToMake()) > (o1.getPrice() / o1.getTimeToMake()))
				return 1;
			else if ((o2.getPrice() / o2.getTimeToMake()) < (o1.getPrice() / o1.getTimeToMake()))
				return -1;
			return -1 * o1.getId().compareTo(o2.getId());
		});
		for (Dish d : getDishes().values()) {
			profit.add(d);
		}
		return profit;
	}

	public TreeSet<Delivery> createAIMacine(DeliveryPerson dp, DeliveryArea da, TreeSet<Order> orders) {
		TreeSet<Delivery> AIDecision = new TreeSet<>(new Comparator<Delivery>() {

			@Override
			public int compare(Delivery o1, Delivery o2) {
				if (o2 instanceof RegularDelivery && o1 instanceof ExpressDelivery)
					return -1;
				if (o2 instanceof ExpressDelivery && o1 instanceof RegularDelivery)
					return 1;
				return o2.getId() > o1.getId() ? -1 : 1;
			}
		});
		TreeSet<Order> toRegularDelivery = new TreeSet<>();
		if (orders.size() <= 2) {
			for (Order o : orders) {
				ExpressDelivery ed = new ExpressDelivery(dp, da, false, o, LocalDate.of(2021, 1, 1));
				AIDecision.add(ed);
			}
		} else {
			for (Order o : orders) {
				if (o.getCustomer().isSensitiveToGluten() || o.getCustomer().isSensitiveToLactose()) {
					ExpressDelivery ed = new ExpressDelivery(dp, da, false, o, LocalDate.of(2021, 1, 1));
					AIDecision.add(ed);
				} else
					toRegularDelivery.add(o);
			}
			if (toRegularDelivery.size() < 2) {
				ExpressDelivery ed = new ExpressDelivery(dp, da, false, toRegularDelivery.first(),
						LocalDate.of(2021, 1, 1));
				AIDecision.add(ed);
			} else {
				RegularDelivery rd = new RegularDelivery(toRegularDelivery, dp, da, false, LocalDate.of(2021, 1, 1));
				AIDecision.add(rd);
			}
		}
		return AIDecision;
	}
    //update ser file
	public static void updatefile() {
		try {
			FileOutputStream myfile = new FileOutputStream("Rest.ser");
			ObjectOutputStream output = new ObjectOutputStream(myfile);
			output.writeObject(restaurant);
			output.flush();
			output.close();
			myfile.close();

		}

		catch (IOException e) {
			System.out.println("error while updating file");
			e.printStackTrace();
		}

	}
}
