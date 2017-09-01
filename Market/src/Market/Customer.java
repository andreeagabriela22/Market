package Market;

import java.util.Vector;

public class Customer implements Observer {
	public String name;
	public double budget;
	
	public ShoppingCart shoppingC;
	public WishList wishL;
	private Vector<Notification> notifications;
	
	public Customer(String name, double budget) {
		this.name = new String(name);
		this.budget = budget;
		
		shoppingC = new ShoppingCart(budget);
		wishL = new WishList();
		notifications = new Vector<Notification>();
	}
	
	@Override
	public void update(Notification n) {
		notifications.add(n);
	}
	
	public Vector<Notification> getNotifications() {
		return notifications;
	}
	
	public String toString() {
		return name;
	}
}
