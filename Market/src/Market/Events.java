package Market;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

class Event {
	String[] args;
	
	public void readEvent(Scanner scanner) {
		String def = scanner.nextLine();
		args = def.split(";");
	}
	
	public String getType() {
		return args[0];
	}
}

public class Events {
	File eventsFile;
	Scanner scanner;
	FileWriter writer;
	
	public Events() {
		eventsFile = new File("events.txt");
		try {
			scanner = new Scanner(eventsFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new FileWriter(new File("result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		if (scanner != null)
			scanner.close();
		if (writer != null)
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public int numberOfEvents(Scanner s) {
		return Integer.parseInt(s.nextLine());
	}
	
	public void executeEvents() {
		int nr = numberOfEvents(scanner);
		for (int i = 0; i < nr; i++) {
			Event currentEvent = new Event();
			currentEvent.readEvent(scanner);
			handleEvent(currentEvent);
		}
	}
	
	private void handleEvent(Event e) {
		if (e.getType().equals("addItem")) {
			addItem(e);
		} else if (e.getType().equals("delItem")) {
			delItem(e);
		} else if (e.getType().equals("addProduct")) {
			addProduct(e);
		} else if (e.getType().equals("modifyProduct")) {
			modifyProduct(e);
		} else if (e.getType().equals("delProduct")) {
			delProduct(e);
		} else if (e.getType().equals("getItems")) {
			getItems(e);
		} else if (e.getType().equals("getTotal")) {
			getTotal(e);
		} else if (e.getType().equals("accept")) {
			accept(e);
		} else if (e.getType().equals("getObservers")) {
			getObservers(e);
		} else if (e.getType().equals("getNotifications")) {
			getNotifications(e);
		}
	}
	
	private void addItem(Event e) {
		int itemID = Integer.parseInt(e.args[1]);
		String list = e.args[2];
		String customerName = e.args[3];
		Department dep = null;
		
		Item item = null;
		for (Department department : Store.departments) {
			for (Item x : department.getItems()) {
				if (x.getID() == itemID) {
					item = x;
					dep = department;
				}
			}
		}
		
		Customer customer = null;
		
		for (Customer x : Store.clients) {
			if (x.name.equals(customerName))
				customer = x;
		}
		
		if (item == null || customer == null)
			return;
		
		if (list.equals("WishList")) {
			customer.wishL.add(item);
			dep.addObserver(customer);
		}
		else {
			boolean hasMoney = customer.shoppingC.add(item);
			if (!hasMoney)
			{
				System.out.println("SARACIE!");
			}
		}
	}
	
	private void delItem(Event e) {
		int itemID = Integer.parseInt(e.args[1]);
		String list = e.args[2];
		String customerName = e.args[3];
		Item item = null;
		
		Customer customer = null;
		
		for (Customer x : Store.clients) {
			if (x.name.equals(customerName))
				customer = x;
		}
		
		ItemList itemList = null;
		if (list.equals("WishList")) {
			itemList = customer.wishL;
		}
		else {
			itemList = customer.shoppingC;
		}
		
		ItemList.ItemIterator iterator = itemList.getIterator();
		
		while (iterator.hasNext()) {
			item = iterator.next();
			if (item.getID() == itemID) {
				customer.shoppingC.remove(item);
				break;
			}
		}
		
		boolean removeObserver = true;
		Department department = item.getDepartment();
		Iterator<Item> iterator2 = customer.wishL.getIterator();
		while (iterator2.hasNext()) {
			if (iterator2.next().getDepartment() == department) {
				removeObserver = false;
				break;
			}
		}
		
		if (removeObserver) {
			department.removeObserver(customer);
		}
	}

	private void addProduct(Event e) {
		int departmentID = Integer.parseInt(e.args[1]);
		int itemID = Integer.parseInt(e.args[2]);
		double price = Double.parseDouble(e.args[3]);
		String name = e.args[4];
		
		Item item = new Item(name, itemID, price);
		for (Department department : Store.departments) {
			if (department.getID() == departmentID) {
				department.addProduct(item);
			}
		}
	}
	
	private void modifyProduct(Event e) {
		int departmentID = Integer.parseInt(e.args[1]);
		int itemID = Integer.parseInt(e.args[2]);
		double price = Double.parseDouble(e.args[3]);
		
		for (Department department : Store.departments) {
			if (department.getID() == departmentID)
				for (Item item : department.getItems()) 
					if (item.getID() == itemID)
						department.modifyProduct(item, price);
		}
	}
	
	private void delProduct(Event e) {
		int itemID = Integer.parseInt(e.args[1]);
		
		for (Department department : Store.departments) {
			Vector<Item> items = department.getItems();
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getID() == itemID)
					department.delProduct(items.get(i));
			}
		}
		for (Customer customer : Store.clients) {
			ItemList.ItemIterator iterator = customer.wishL.getIterator();
			while (iterator.hasNext()) {
				Item item = iterator.next();
				if (item.getID() == itemID)
					iterator.remove();
			}
			iterator = customer.shoppingC.getIterator();
			while (iterator.hasNext()) {
				Item item = iterator.next();
				if (item.getID() == itemID)
					iterator.remove();
			}
		}
	}
	
	private void getItems(Event e) {
		String list = e.args[1];
		String customerName = e.args[2];
		
		Customer customer = null;
		
		for (Customer x : Store.clients) {
			if (x.name.equals(customerName))
				customer = x;
		}
		
		ItemList itemList = null;
		
		if (list.equals("WishList")) {
			itemList = customer.wishL;
		}
		else {
			itemList = customer.shoppingC;
		}
		
		ItemList.ItemIterator iterator = itemList.getIterator();
		ItemList items = new ItemList();
		
		while (iterator.hasNext()) {
			items.add(iterator.next());
		}
		
		String toPrint = "[";
		
		for (int i = 0; i < items.getSize(); i++) {
			toPrint = toPrint.concat(items.getItem(i).toString());
			if (i < items.getSize() - 1)
				toPrint = toPrint.concat(", ");
		}
		
		toPrint = toPrint.concat("]");
			
		try {
			writer.write(toPrint + "\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void getTotal(Event e) {
		String list = e.args[1];
		String customerName = e.args[2];
		
		Customer customer = null;
		
		for (Customer x : Store.clients) {
			if (x.name.equals(customerName))
				customer = x;
		}
		
		ItemList itemList = null;
		
		if (list.equals("WishList")) {
			itemList = customer.wishL;
		}
		else {
			itemList = customer.shoppingC;
		}
		
		ItemList.ItemIterator iterator = itemList.getIterator();
		ItemList items = new ItemList();
		
		while (iterator.hasNext()) {
			items.add(iterator.next());
		}
		
		double totalPrice = items.getTotalPrice();
		
		try {
			writer.write(totalPrice + "\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	
	public void accept(Event e) {
		int departmentID = Integer.parseInt(e.args[1]);
		String customerName = e.args[2];
		
		Customer customer = null;
		
		for (Customer x : Store.clients) {
			if (x.name.equals(customerName))
				customer = x;
		}
		
		Department department = null;
		
		for (Department x : Store.departments) {
			if (x.getID() == departmentID)
				department = x;
		}
		
		department.accept(customer.shoppingC);	
	}
	
	public void getObservers(Event e) {
		int departmentID = Integer.parseInt(e.args[1]);
		
		Vector<Customer> observers = null;
		
		for (Department department : Store.departments) {
			if (department.getID() == departmentID)
				observers = department.getObservers();
		}
		
		String toPrint = "[";
		
		for (int i = 0; i < observers.size(); i++) {
			toPrint = toPrint.concat(observers.get(i).toString());
			if (i < observers.size() - 1)
				toPrint = toPrint.concat(", ");
		}
		
		toPrint = toPrint.concat("]");
			
		try {
			writer.write(toPrint + "\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void getNotifications(Event e) {
		String customerName = e.args[1];
		
		Customer customer = null;
		
		for (Customer x : Store.clients) {
			if (x.name.equals(customerName))
				customer = x;
		}
		
		String toPrint = "[";
		
		for (int i = 0; i < customer.getNotifications().size(); i++) {
			Notification notification = customer.getNotifications().get(i);
			toPrint = toPrint.concat(notification.toString());
			if (i < customer.getNotifications().size() - 1) {
				toPrint = toPrint.concat(", ");
			}
		}
		
		toPrint = toPrint.concat("]");
		
		try {
			writer.write(toPrint + "\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
