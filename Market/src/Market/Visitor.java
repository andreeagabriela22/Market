package Market;

public interface Visitor {
	public void visit(BookDepartment bookDepartment);
	public void visit(MusicDepartment musicDepartment);
	public void visit(VideoDepartment videoDepartment);
	public void visit(SoftwareDepartment softwareDepartment);
}
