package imagemanager.model;

import imageprocessing.Util;
import imageprocessing.boundsdetection.QuadrangleTransformation;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;
import imageprocessing.segmentation.BackgroundCleaner;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class SourceImage{

	static float ICON_SCALE = 0.1f;
	@Id
	@GeneratedValue
	Long id;	
	String name;
	Long date;
	
	@Transient
	BufferedImage icon;
	@Transient
	BufferedImage pixels;
	
	Set<ImageLabel> labels = new HashSet<ImageLabel>();
	
	Set<BoardRegion> boardImages = new HashSet<BoardRegion>();
	
	public SourceImage(){};
	
	public SourceImage(File file) {
		String[] tab = file.getName().split("/");
		this.name = tab[tab.length - 1];
		this.date = file.lastModified();
		this.pixels = Util.readFromFile(file);
		this.icon = Util.resize(pixels, ICON_SCALE);
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	public BufferedImage getPixels() {
		return pixels;
	}

	public void setPixels(BufferedImage pixels) {
		this.pixels = pixels;
	}

	public Set<ImageLabel> getImageLabels() {
		return labels;
	}

	public void setImageLabels(Set<ImageLabel> labels) {
		this.labels = labels;
	}
	
	public Set<BoardRegion> getBoardImages() {
		return boardImages;
	}

	public void setBoardImages(Set<BoardRegion> boardImages) {
		this.boardImages = boardImages;
	}

	public BoardRegion extractBoardRegion(Polygon quadrangle){
		
		// extract board region from source image
		
		double x1 = (quadrangle.getPoint(0).x + quadrangle.getPoint(3).x) / 2;
		double x2 = (quadrangle.getPoint(1).x + quadrangle.getPoint(2).x) / 2;

		double y1 = (quadrangle.getPoint(0).y + quadrangle.getPoint(1).y) / 2;
		double y2 = (quadrangle.getPoint(2).y + quadrangle.getPoint(3).y) / 2;
		
		Polygon fixed = new Polygon(new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2));
		
     	BufferedImage trans = QuadrangleTransformation.transform(pixels, quadrangle, fixed);
		BufferedImage bRegion = Util.subImage(trans, (int) fixed.getPoint(0).x,
				(int) fixed.getPoint(0).y, (int) fixed.getPoint(2).x,
				(int) fixed.getPoint(2).y);
		Color[] colors = new Color[2];
		
		BufferedImage binarized = BackgroundCleaner.run(bRegion, colors);
		
		// create new board image
		
		BoardRegion bimg = new BoardRegion();
		
		bimg.setIcon(Util.resize(binarized, ICON_SCALE));
		bimg.setPixels(binarized);
		bimg.setSource(this);
		bimg.setSrcQuadrangle(quadrangle);
		
		if(colors[1] == Color.BLACK){
			bimg.setType(BoardRegion.BoardType.BLACK);
		} else if(colors[2] == Color.WHITE){
			bimg.setType(BoardRegion.BoardType.WHITE);
		}
				
		return bimg;
	}
	
	@Override
	public String toString() {
		return "SourceImage [id=" + id + ", name=" + name + ", date=" + date
				+ "]";
	}

}