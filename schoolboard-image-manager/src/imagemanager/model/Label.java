package imagemanager.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Label {
	
	@Id
	@GeneratedValue
	private Long id;
	private String title;
//	@ManyToMany
//	private Set<SourceImage> images = new HashSet<SourceImage>();
	
	public Label(){};
	
	public Label(String title){
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
//	public Set<SourceImage> getImages() {
//		return images;
//	}
//
//	public void setImages(Set<SourceImage> images) {
//		this.images = images;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Label [id=" + id + ", title=" + title + "]";
	}
}