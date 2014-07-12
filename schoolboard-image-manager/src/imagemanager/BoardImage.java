package imagemanager;

import imagemanager.model.SourceImage;
import imageprocessing.ImageProcessing;
import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.IODatabase;

public class BoardImage extends AbstractImage{
	
	private SourceImage source;
	private Polygon srcQuadrangle;
	private int bgColor;
	private int fgColor;
	
	private Map<Integer, TextRegion> textRegions = new HashMap<Integer, TextRegion>();  
	
	private BoardImage(){}
	
	public static BoardImage createBoardImage(SourceImage src, Polygon srcQuad) throws BoardImageCreateException{ 
		BoardImage inst = new BoardImage();
		inst.source = src;
		inst.srcQuadrangle = srcQuad;
		
		double x1 = (srcQuad.getPoint(0).x + srcQuad.getPoint(3).x) / 2.0;
		double x2 = (srcQuad.getPoint(1).x + srcQuad.getPoint(2).x) / 2.0;
		double y1 = (srcQuad.getPoint(0).y + srcQuad.getPoint(1).y) / 2.0;
		double y2 = (srcQuad.getPoint(2).y + srcQuad.getPoint(3).y) / 2.0;

		Polygon fixed = new Polygon(new Point(x1, y1), new Point(x2, y1),
				new Point(x2, y2), new Point(x1, y2));
		
		Color[] colors = new Color[2];
		inst.imagePixels = ImageProcessing.extractBoardRegion(src.getImagePixels(), srcQuad, fixed, colors);
		inst.thumbnailPixels = Util.resize(inst.imagePixels, THUMB_SCALE);
		
		inst.fgColor = colors[0].getRGB();
		inst.bgColor = colors[1].getRGB();
		
		// TODO znalezc regiony z tekstem
		List<Polygon> textRegions = ImageProcessing.findTextRegions(inst.imagePixels, colors[1], colors[0]);
		
		
		Integer id = IODatabase.getInstance().exportNewBoardImage(inst);
		if(id == null) throw new BoardImageCreateException("unable to export to databse");
		inst.id = id;
				
		inst.disposeContent();
		return inst;
		
	}
	
//	public BoardImage (Integer id, SourceImage source, Polygon srcQuadrangle, BufferedImage image){
//		this.id = id;
//		this.source = source;
//		this.srcQuadrangle = srcQuadrangle;
//		this.imagePixels = image;
//		this.thumbnailPixels = Util.resize(image, THUMB_SCALE);
//		
//	}
	
	public void setSource(SourceImage source){
		this.source = source;
	}
	
	public SourceImage getSource() {
		return source;
	}
	
	public Polygon getPerimeter() {
		return srcQuadrangle;
	}
		
	public Collection<TextRegion> getTextRegions(){
		return textRegions.values();
	}
	
	public void createTextRegions(){
		// TODO
//		textRegions = new HashMap<Integer, TextRegion>(textPolys.size());
//				
//		for (Polygon polygon : textPolys) {
//			TextRegion tr = new TextRegion(polygon);
//			tr.setColor(Color.ORANGE);
//			textRegions.put(key, value)
//		}
		
	}

	@Override
	public String toString() {
		return "BoardImage [id=" + id + ", sourceId=" + source.getId() + "]";
	}

	@Override
	public void importContent() {
		// TODO odczytac z bazy
	}

	@Override
	public void disposeContent() {
		this.imagePixels = null;
		this.textRegions = null;
	}
	 
	public static class BoardImageCreateException extends Exception{
		
		public String message;
		
		public BoardImageCreateException(String message){
			this.message = message;
		}
		
		@Override
		public String getMessage() {
			return message;
		}
	}
}
