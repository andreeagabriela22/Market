package Market;

public interface Subject {
	public void addObserver(Customer customer);
	public void removeObserver(Customer c);
	public void notifyAllObservers(Notification notification);
}
