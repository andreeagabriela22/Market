package Market;

public class Notification {
	public enum NotificationType {ADD, REMOVE, MODIFY};
	public NotificationType notType;
	public int departmentID;
	public int itemID;
	
	public Notification(NotificationType notType, int departmentID, int itemID) {
		this.notType = notType;
		this.departmentID = departmentID;
		this.itemID = itemID;
	}
	
	public String toString() {
		return "" + this.notType + ";" + this.departmentID + ";" + this.itemID;
	}
}
