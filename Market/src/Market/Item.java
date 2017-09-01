package Market;

import java.util.Iterator;

public class Item {
	private String name;
	private int ID;
	private double price;
	
	public Item (String name, int ID, double price) {
		this.name = new String (name);
		this.ID = ID;
		this.price = price;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setID (int ID) {
		this.ID = ID;
	}
	
	public int getID () {
		return this.ID;
	}
	
	public void setPrice (double price) {
		this.price = price;
	}
	
	public double getPrice () {
		return this.price;
	}
	
	public Department getDepartment() {
		for (Department department : Store.departments) {
			Iterator<Item> iterator = department.getItems().iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getID() == ID)
					return department;
			}
		}
		return null;
	}
	
	public String toString () {
		return "" + this.name + ";" + this.ID + ";" + this.price;
	}
}



