package Market;

public class ShoppingCart extends ItemList implements Visitor {
	double budget;
	
	public ShoppingCart(Double budget) {
		super();
		this.budget = budget;
	}
	
	public boolean add(Item item) {
		if (item.getPrice() + getTotalPrice() > budget)
			return false;
		else
			return super.add(item);
	}

	@Override
	public void visit(BookDepartment bookDepartment) {
		ItemList.ItemIterator iterator = new ItemIterator();
		
		while (iterator.hasNext()) {
			Item current = iterator.next();
			if (bookDepartment.getItems().contains(current))
				current.setPrice(current.getPrice() - 0.1 * current.getPrice());
		}	
	}

	@Override
	public void visit(MusicDepartment musicDepartment) {
		double price = 0;
		
		for (int i = 0; i < musicDepartment.getItems().size(); i++)
			if (this.contains(musicDepartment.getItems().get(i)))
				price += musicDepartment.getItems().get(i).getPrice();
		
		budget += 0.1 * price;
	}

	@Override
	public void visit(VideoDepartment videoDepartment) {
		double maximumPrice = videoDepartment.getItems().get(0).getPrice();
		double price = 0;
		ItemList.ItemIterator iterator = new ItemIterator();
		
		for (int i = 1; i < videoDepartment.getItems().size(); i++)
			if (videoDepartment.getItems().get(i).getPrice() > maximumPrice) 
				maximumPrice = videoDepartment.getItems().get(i).getPrice();
		
		for (int i = 0; i < videoDepartment.getItems().size(); i++)
			if (this.contains(videoDepartment.getItems().get(i)))
				price += videoDepartment.getItems().get(i).getPrice();
		
		budget += 0.05 * price;
		
		if (price > maximumPrice) {
			while (iterator.hasNext()) {
				Item current = iterator.next();
				if (videoDepartment.getItems().contains(current))
					current.setPrice(current.getPrice() - 0.15 * current.getPrice());
			}
		}
	}

	@Override
	public void visit(SoftwareDepartment softwareDepartment) {
		double minimumPrice = softwareDepartment.getItems().get(0).getPrice();
		ItemList.ItemIterator iterator = new ItemIterator();
		
		for (int i = 1; i < softwareDepartment.getItems().size(); i++)
			if (softwareDepartment.getItems().get(i).getPrice() < minimumPrice) 
				minimumPrice = softwareDepartment.getItems().get(i).getPrice();
		
		if (budget < minimumPrice) 
			while (iterator.hasNext()) {
				Item current = iterator.next();
				if (softwareDepartment.getItems().contains(current))
					current.setPrice(current.getPrice() - 0.2 * current.getPrice());
			}
	}
	
	@Override
	public int compare(Item item1, Item item2) {
		if (item1.getPrice() < item2.getPrice())
			return 1;
		else 
			if (item1.getPrice() > item2.getPrice())
				return -1;
			else
				return item1.getName().compareTo(item2.getName());
	}
}
