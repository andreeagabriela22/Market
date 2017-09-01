package Market;

public class BookDepartment extends Department {
	
	public BookDepartment(int ID) {
		super(ID);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
}
