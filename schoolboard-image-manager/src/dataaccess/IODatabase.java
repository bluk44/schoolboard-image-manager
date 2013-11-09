package dataaccess;

import imagemanager.model.ImageRecord;
import imagemanager.model.LabelRecord;

import java.awt.image.BufferedImage;
import java.io.File;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
	
	public void exportImage(File imageFile){
		if(connection == null ) return;
		try{
			if(connection.isClosed()) return;
			if(putNewImage == null){
				putNewImage = connection.prepareCall("{call put_new_source_image(?, ?)}");
			}
			
			putNewImage.setString("name_param", imageFile.getName());
			putNewImage.setTimestamp("creation_date_param", new Timestamp(imageFile.lastModified()));
			putNewImage.execute();
			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(image, "png", baos);
//			InputStream is = new ByteArrayInputStream(baos.toByteArray());
//			
//			insert.setString(1, name);
//			insert.setBlob(2, is);
//			insert.executeUpdate();
//			
//			catch (IOException e) {
//			System.out.println("[IODatabase] Error writing image to byte array "+e.getMessage());	
//		}
//			
		}catch (SQLException e) {
			System.out.println("[IODatabase] SQL exception "+e.getMessage());	
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
				ImageRecord imgRecord = new ImageRecord(id, name);
				System.out.println(imgRecord);
				
				allImages.put(id, imgRecord);
			}
			return allImages;

		}catch (SQLException e) {
			System.out.println("[IODatabase] SQL exception "+e.getMessage());	
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
				System.out.println(labRecord);
				
				allLabels.put(id, labRecord);
			}
			return allLabels;

		}catch (SQLException e) {
			System.out.println("[IODatabase] SQL exception "+e.getMessage());	
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
