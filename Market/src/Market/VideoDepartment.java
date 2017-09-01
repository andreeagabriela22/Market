package Market;

public class VideoDepartment extends Department {

	public VideoDepartment(int ID) {
		super(ID);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
