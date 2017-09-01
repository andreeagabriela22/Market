package Market;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputFiles {
	File storeFile;
	File customersFile;
	Store market;
	
	public InputFiles() {
		storeFile = new File("store.txt");
		customersFile = new File("customers.txt");
		market = Store.getInstance();
	}
	
	public void SetStoreName(Scanner s) {
		market.name = s.nextLine();
	}
	
	public Department DepartmentReader(Scanner s) {
		String department[] = s.nextLine().split(";");
		String departmentName = department[0];
		int departmentID = Integer.parseInt(department[1]);
		
		Department readDepartment = null;
		
		switch (departmentName) {
		case "MusicDepartment": 
			readDepartment = new MusicDepartment(departmentID);
			break;
		case "BookDepartment":
			readDepartment = new BookDepartment(departmentID);
			break;
		case "SoftwareDepartment":
			readDepartment = new SoftwareDepartment(departmentID);
			break;
		case "VideoDepartment":
			readDepartment = new VideoDepartment(departmentID);
			break;
		}
		
		return readDepartment;
	}
	
	public Item ItemReader(Scanner s) {
		String item[] = s.nextLine().split(";");
		String itemName = item[0];
		int itemID = Integer.parseInt(item[1]);
		double itemPrice = Double.parseDouble(item[2]);
		
		return new Item(itemName, itemID, itemPrice);
	}
	
	public Department addItemsToDepartment(Scanner s) {
		Department newDepartment = DepartmentReader(s);
		int numberOfItems = Integer.parseInt(s.nextLine());
		
		for (int i = 0; i < numberOfItems; i++) 
			newDepartment.addProduct(ItemReader(s));
		
		return newDepartment;
	}
	
	public Customer CustomerReader(Scanner s) {
		String customer[] = s.nextLine().split(";");
		String customerName = customer[0];
		double customerBudget = Double.parseDouble(customer[1]);
		@SuppressWarnings("unused")
		String customerStrategy = customer[2];
		
		return new Customer(customerName, customerBudget);
	}
	
	public void addCustomers(Scanner s) {
		int numberOfCustomers = Integer.parseInt(s.nextLine());
		
		for (int i = 0; i < numberOfCustomers; i++) 
			market.enter(CustomerReader(s));
	}
	
	public void MakeStore() {
		Scanner s1;
		Scanner s2;
		
		try {
			s1 = new Scanner(storeFile);
			s2 = new Scanner(customersFile);
			
			SetStoreName(s1);
			
			while (s1.hasNextLine())
				market.addDepartment(addItemsToDepartment(s1));
			
			addCustomers(s2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
