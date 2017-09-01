package Market;

public class MusicDepartment extends Department {
	
	public MusicDepartment(int ID) {
		super(ID);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
