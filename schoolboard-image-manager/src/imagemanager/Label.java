package imagemanager;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Label {
	
	private Integer id;
	private String title;
	
	private Map<Integer, AbstractImage> labeledImages;
	
	public Label(Integer id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public void setImages(Collection<AbstractImage> images){	
		labeledImages = new HashMap<Integer, AbstractImage>();
		
		for (AbstractImage img : images) labeledImages.put(img.getId(), img);
	}
	
	public void addImage(AbstractImage image){
		labeledImages.put(image.getId(), image);
	}
	
	public AbstractImage getImage(Integer imageId){
		
		return labeledImages.get(imageId);
	}
	
	public void removeImage(Integer imageId){
		labeledImages.remove(imageId);
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
	}
	
	public void printAllImages(){
		
		for (AbstractImage image : labeledImages.values()) {
			System.out.println(image);
		}
		
		System.out.println();
	}
}
