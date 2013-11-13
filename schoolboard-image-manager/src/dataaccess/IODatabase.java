package dataaccess;

import imagemanager.model.ImageRecord;
import imagemanager.model.LabelRecord;
import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


public class IODatabase {
	private String url;
	private String userName;
	private String password;

	private Connection connection;
	private PreparedStatement insert, select, getAllImages, getAllLabels, getAllImagesLabels;
	private CallableStatement putNewImage;
	private ResultSet resultSet;
	
	public IODatabase(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public void connect() {

		if(connection == null){
			try {
				connection = DriverManager.getConnection(url, userName, password);
			} catch (SQLException e) {
				System.out.println("[IODatabase] failed connecting to database "+e.getMessage());
			}
		}
	}
	
	public void disconnect(){
		if(connection != null){
			try { if(!connection.isClosed()) connection.close(); } catch (SQLException ignore) {}
			connection = null;
		}
	}
	
	public void exportImage(BufferedImage image, String name, long timestamp, BufferedImage thumbnail){
		if(connection == null ) return;
		try{
			if(connection.isClosed()) return;
			if(putNewImage == null){
				putNewImage = connection.prepareCall("{call put_new_source_image(?, ?, ?, ?, ?, ?, ?, ?)}");
			}
			
			putNewImage.setString("_name", name);
			putNewImage.setTimestamp("_tstmp", new Timestamp(timestamp));
			putNewImage.setBlob("_image_pixels", Util.getBlob(image, "png"));
			putNewImage.setInt("_image_width", image.getWidth());
			putNewImage.setInt("_image_height", image.getHeight());
			putNewImage.setBlob("_thumb_pixels", Util.getBlob(thumbnail, "png"));
			putNewImage.setInt("_thumb_width", thumbnail.getWidth());
			putNewImage.setInt("_thumb_height", thumbnail.getHeight());			
			putNewImage.execute();
			
			
		}catch (SQLException e) {
			System.out.println("[IODatabase] SQL exception "+e.getMessage());	
		} catch (IOException e) {
			System.out.println("[IODatabase] IO exception "+e.getMessage());	
		}
		
	}
	
	public Map<String, BufferedImage> importImages(String name){
		System.out.println(name);
		if(connection == null ) return null;
		try{
			if(connection.isClosed()) return null;
			if(select == null){
				select = connection.prepareStatement("SELECT * FROM images WHERE image_name='"+name+"'");
			}
			resultSet = select.executeQuery();
			Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
			while(resultSet.next()){
				Blob pixels = resultSet.getBlob("picture");
				InputStream imgIs = pixels.getBinaryStream();
				try {
					BufferedImage img = ImageIO.read(imgIs);
					String imgName = resultSet.getString("image_name");
					imageMap.put(imgName, img);
				} catch (IOException e) {
					System.out.println("[IODatabase] Error reading image "+e.getMessage());	
				}
			}
			return imageMap;

		}catch (SQLException e) {
			System.out.println("[IODatabase] SQL exception "+e.getMessage());	
		}
		return null;
	}
	
	public LinkedHashMap<Integer, ImageRecord> importAllImages(){
		if(connection == null ) return null;
		try{
			if(connection.isClosed()) return null;
			if(getAllImages == null){
				getAllImages = connection.prepareCall("{call get_images()}");
			}
			resultSet = getAllImages.executeQuery();
			LinkedHashMap<Integer, ImageRecord> allImages = new LinkedHashMap<Integer, ImageRecord>();
			while(resultSet.next()){
				Integer id = resultSet.getInt("idimage");
				String name = resultSet.getString("name");
				Blob blob = resultSet.getBlob("thumb_pixels");
				BufferedImage img = Util.getImage(blob);
				ImageRecord imgRecord = new ImageRecord(id, name, img);
				allImages.put(id, imgRecord);
			}
			return allImages;

		}catch (SQLException e) {
			System.out.println("[IODatabase importAllImages] SQL exception "+e.getMessage());	
		} catch (IOException e) {
			System.out.println("[IODatabase importAllImages] IO exception "+e.getMessage());	
		}
		return null;
	}
	
	public LinkedHashMap<Integer, LabelRecord> importAllLabels(){
		if(connection == null ) return null;
		try{
			if(connection.isClosed()) return null;
			if(getAllLabels == null){
				getAllLabels = connection.prepareCall("{call get_labels()}");
			}
			resultSet = getAllLabels.executeQuery();
			LinkedHashMap<Integer, LabelRecord> allLabels = new LinkedHashMap<Integer, LabelRecord>();
			while(resultSet.next()){
				Integer id = resultSet.getInt("idlabel");
				String name = resultSet.getString("title");
				LabelRecord labRecord = new LabelRecord(id, name);				
				allLabels.put(id, labRecord);
			}
			return allLabels;

		}catch (SQLException e) {
			System.out.println("[IODatabase importAllLabels] SQL exception "+e.getMessage());	
		}
		return null;
	}
	
	public LinkedHashMap<Integer, Collection<Integer>> importLabelsImages(){
		if(connection == null ) return null;
		try{
			if(connection.isClosed()) return null;
			if(getAllImagesLabels == null){
				getAllImagesLabels = connection.prepareCall("{call get_images_labels()}");
			}
			resultSet = getAllImagesLabels.executeQuery();
//			LinkedHashMap<Integer, Integer> imagesLabels = new LinkedHashMap<Integer, Integer>();
			LinkedHashMap<Integer, Collection<Integer>>  labelsImages = new LinkedHashMap<Integer, Collection<Integer>>();
			while(resultSet.next()){
				Integer id_label = resultSet.getInt("idlabel");
				Integer id_image = resultSet.getInt("idimage");
//				System.out.println("[IODatabase importImagesLabels] idlabel "+id_label+" idimage"+id_image);
				if(labelsImages.containsKey(id_label)){
					labelsImages.get(id_label).add(id_image);
				} else {
					Collection<Integer> images = new ArrayList<Integer>();
					images.add(id_image);
					labelsImages.put(id_label, images);
				}
			}
			for (Iterator iterator = labelsImages.entrySet().iterator(); iterator.hasNext();) {
				Entry<Integer, Collection<Integer>> entry = (Entry<Integer, Collection<Integer>>) iterator.next();
				System.out.println("idlabel "+entry.getKey());
				for (Iterator iterator2 = entry.getValue().iterator(); iterator2
						.hasNext();) {
					Integer idimage = (Integer) iterator2.next();
					System.out.println(" idimage "+idimage);
					
				}
			}
			return labelsImages;

		}catch (SQLException e) {
			System.out.println("[IODatabase importImagesLabels] SQL exception "+e.getMessage());	
		}
		return null;
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (connection != null)
			try { connection.close(); } catch (SQLException ignore) {}
		if (insert != null)
			try { insert.close(); } catch (SQLException ignore) {}
		if (select != null)
			try { select.close(); } catch (SQLException ignore) {}
		if (resultSet != null) 
			try { resultSet.close(); } catch (SQLException ignore) {}
	}
}
