package dataaccess;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageIODatabase {
	private String url;
	private String userName;
	private String password;

	private Connection connection;
	private PreparedStatement insert, select;
	private ResultSet resultSet;
	
	public ImageIODatabase(String url, String userName, String password) {
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
	
	public void exportImage(BufferedImage image, String name){
		if(connection == null ) return;
		try{
			if(connection.isClosed()) return;
			if(insert == null){
				insert = connection.prepareStatement("INSERT INTO images (image_name, picture) VALUES (?, ?)");
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			insert.setString(1, name);
			insert.setBlob(2, is);
			insert.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("[IODatabase] SQL exception "+e.getMessage());	
		}catch (IOException e) {
			System.out.println("[IODatabase] Error writing image to byte array "+e.getMessage());	
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
