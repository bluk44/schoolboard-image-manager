package imagemanager.model;


public class LabelRecord {
	
	private Integer id;
	private String title;
	
	public LabelRecord(Integer id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "LabelRecord [id=" + id + ", title=" + title + "]";
		//return title;
	}
		
}
