package Market;

public class SoftwareDepartment extends Department {

	public SoftwareDepartment(int ID) {
		super(ID);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
