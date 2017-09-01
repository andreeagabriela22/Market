package Market;

public class Test {
	public static void main(String[] args) {
		InputFiles inputFiles = new InputFiles();
		inputFiles.MakeStore();
		
		Events events = new Events();
		events.executeEvents();
		
		events.finalize();
	}
}
