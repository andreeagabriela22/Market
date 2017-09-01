package Market;

import java.util.Vector;

public class Store {
	private static Store newStore;
	
	public String name;
	public static Vector<Department> departments;
	public static Vector<Customer> clients;
	
	public void enter(Customer x) {
		Store.clients.add(x);
	}
	
	public void exit(Customer x) {
		Store.clients.remove(x);
	}
	
	public ShoppingCart getShoppingCart(Double x) {
		ShoppingCart shoppingC = new ShoppingCart(x);
		return shoppingC;
	}
	
	public Vector<Customer> getCustomers() {
		return clients;
	}
	
	public Vector<Department> getDepartments() {
		return departments;
	}
	
	public void addDepartment(Department x) {
		departments.add(x);
	}
	
	public Department getDepartment(int x) {
		for (int i = 0; i < departments.size(); i++) {
			if (departments.get(i).getID() == x)
				return departments.get(i);
		}
		
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static Store getInstance() {
		if (newStore != null)
			return newStore;
		else {
			newStore = new Store();
			departments = new Vector<Department>();
			clients = new Vector<Customer>();
			return newStore;
		}
	}
}

