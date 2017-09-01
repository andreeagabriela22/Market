package Market;

import java.util.Vector;

import Market.Notification.NotificationType;

abstract class Department implements Subject {
	private Vector<Item> items;
	private Vector<Customer> clients;
	private Vector<Customer> observers;
	int ID;
	
	public Department(int ID) {
		clients = new Vector<Customer>();
		items = new Vector<Item>();
		observers = new Vector<Customer>();
		this.ID = ID;
	}
	
	public void enter(Customer x) {
		clients.add(x);
	}
	
	public void exit(Customer x) {
		clients.remove(x);
	}
	
	public Vector<Customer> getCustomers() {
		return clients;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void addProduct(Item x) {
		items.add(x);
		
		Notification notification = new Notification(NotificationType.ADD, ID, x.getID());
		notifyAllObservers(notification);
	}
	
	public void delProduct(Item x) {
		if (items.contains(x))
			items.remove(x);
		
		Notification notification = new Notification(NotificationType.REMOVE, ID, x.getID());
		notifyAllObservers(notification);
	}
	
	public void modifyProduct(Item x, double price) {
		if (items.contains(x)) {
			int index = items.indexOf(x);
			Item item = items.get(index);
			item.setPrice(price);
			
			Notification notification = new Notification(NotificationType.MODIFY, ID, item.getID());
			notifyAllObservers(notification);
		}
	}
	
	public Vector<Item> getItems() {
		return items;
	}
	
	@Override
	public void addObserver(Customer x) {
		if (!observers.contains(x))
			observers.add(x);
	}
	
	@Override
	public void removeObserver(Customer x) {
		observers.remove(x);
	}
	
	@Override
	public void notifyAllObservers(Notification notification) {
		for (int i = 0; i < observers.size(); i++) {
			Customer ct = observers.get(i);
			ct.update(notification);
		}
	}
	
	abstract void accept (Visitor visitor);
	
	public Vector<Customer> getObservers() {
		return observers;
	}
}

