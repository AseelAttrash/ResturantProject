package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Utils.Neighberhood;
//delivery area class
public class DeliveryArea  implements Serializable{
	/**
	 * 
	 */
	//class fields
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private int id;
	private String areaName;
	private HashSet<DeliveryPerson> delPersons;
	private HashSet<Delivery> delivers;
	private HashSet<Neighberhood> neighberhoods;
	private final int deliverTime;
	//constractur
	public DeliveryArea(String areaName, HashSet<Neighberhood> neighberhoods, int deliverTime) {
		super();
		this.id = idCounter++;
		this.areaName = areaName;
		this.neighberhoods = neighberhoods;
		this.deliverTime = deliverTime;
		delPersons = new HashSet<>();
		delivers = new HashSet<>();
	}
	//setters and getters
	public DeliveryArea(int id) {
		this.id = id;
		this.deliverTime = 0;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		DeliveryArea.idCounter = idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Set<DeliveryPerson> getDelPersons() {
		return Collections.unmodifiableSet(delPersons);
	}
	public void setNeighberhood(HashSet<Neighberhood> neighs)
	{
		this.neighberhoods=  neighs;
		
	}

	public Set<Delivery> getDelivers() {
		return Collections.unmodifiableSet(delivers);
	}

	public Set<Neighberhood> getNeighberhoods() {
		return Collections.unmodifiableSet(neighberhoods);
	}

	public int getDeliverTime() {
		return deliverTime;
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
		DeliveryArea other = (DeliveryArea) obj;
		if (id != other.id)
			return false;
		return true;
	}
    //to string method
	@Override
	public String toString() {
		return "DeliveryArea [id=" + id + ", areaName=" + areaName + ", neighberhoods=" + neighberhoods
				+ ", deliverTime=" + deliverTime + "]";
	}
	//add delivery person to hashSet on the class
	//methods
	public boolean addDelPerson(DeliveryPerson dp) {
		return delPersons.add(dp);
	}
	//remove delivery person from hashSet on the class

	public boolean removeDelPerson(DeliveryPerson dp) {
		return delPersons.remove(dp);
	}
	//add delivery to hashSet on the class

	public boolean addDelivery(Delivery d) {
		return delivers.add(d);
	}
	//remove delivery from hashSet on the class

	public boolean removeDelivery(Delivery d) {
		return delivers.remove(d);
	}
	//add neighberhood to hashSet on the class

	public boolean addNeighberhood(Neighberhood neighberhood) {
		return neighberhoods.add(neighberhood);
	}
	//remove neighberhood from hashSet on the class

	public boolean removeNeighberhood(Neighberhood neighberhood) {
		return neighberhoods.remove(neighberhood);
	}
}
