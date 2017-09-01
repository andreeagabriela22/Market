package Market;

public class Singleton {
	private Singleton instance;
	
	public Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		
		return instance;
	}
	
	protected Singleton() {
		
	}
}
