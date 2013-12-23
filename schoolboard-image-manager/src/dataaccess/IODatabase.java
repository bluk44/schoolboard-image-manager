package dataaccess;

import imagemanager.AbstractImage;
import imagemanager.BoardImage;
import imagemanager.Label;
import imagemanager.SourceImage;
import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class IODatabase {
	private static IODatabase instance;

	private static String DB_HOST_NAME = "127.0.0.1";
	private static String DB_PORT = "3306";
	private static String DB_NAME = "imagedb";

	private static String DB_LOGIN = "imgmanager";
	private static String DB_PASS = "idefix";

	private Connection connection;
	// private CallableStatement putNewImage, deleteImage;
	// private CallableStatement getImageRecord, getLabeledImageIds, getImage,
	// getAllImageRecords, getAllLabels, getAllImagesLabels;

	private final static Logger LOG = Logger.getLogger(IODatabase.class
			.getName());
	static {
		LOG.addAppender(new ConsoleAppender(new SimpleLayout()));
	}

	private IODatabase() {
	}

	public static IODatabase getInstance() {
		if (instance == null)
			instance = new IODatabase();
		return instance;
	}

	public void connect() throws SQLException {
		if(connection == null)
		connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST_NAME
				+ ":" + DB_PORT + "/" + DB_NAME, DB_LOGIN, DB_PASS);
	}

	public Integer exportNewSourceImage(SourceImage srcImage){
		CallableStatement call = null;
		Integer id = null;
		
		try {
				connect();
				call = connection.prepareCall("{call put_source_image(?, ?, ?, ?, ?)}");
				call.setString("_name", srcImage.getName());
				call.setTimestamp("_tstmp", new Timestamp(srcImage.getTimestamp()));
				call.setBlob("_image_pixels", Util.getBlob(srcImage.getImagePixels(), "png"));
				call.setBlob("_thumb_pixels", Util.getBlob(srcImage.getThumbnailPixels(), "png"));
				call.registerOutParameter("_idimage", java.sql.Types.INTEGER);
				
				call.execute();
				
				id = call.getInt("_idimage");
				
		} catch (IOException e) {
			LOG.error("IO excepiton " + e.getMessage());
		} catch (SQLException e){
			LOG.error("SQL exception " + e.getMessage());
		}  finally {
			if (call != null) {
				try {
					call.close();
				} catch (SQLException ignore) {
				}
			}
		}
		
		return id;
	}
	
	public Integer exportNewBoardImage(BoardImage image){
		CallableStatement call = null;
		Integer id = null;
		
		try{
			connect();
			call = connection.prepareCall("{call put_new_board_image(?,?,?,?)}");
			call.setInt("_idsource", image.getSource().getId());
			call.setBlob("_image_pixels", Util.getBlob(image.getImagePixels(), "png"));
			call.setBlob("_thumb_pixels", Util.getBlob(image.getThumbnailPixels(), "png"));
			call.registerOutParameter("_idimage", java.sql.Types.INTEGER);
			call.execute();
			
			id = call.getInt("_idimage");
			
		} catch (IOException e) {
			LOG.error("IO excepiton " + e.getMessage());
		} catch (SQLException e){
			LOG.error("SQL exception " + e.getMessage());
		}  finally {
			if (call != null) {
				try {
					call.close();
				} catch (SQLException ignore) {
				}
			}
		}
		
		return id;
	}
	
	public void exportImagesBatch(File[] imageFiles) {
		// TODO
	}

	public BufferedImage importImagePixels(int imageId) {
		CallableStatement call = null;
		BufferedImage image = null;

		try {
			connect();
			call = connection.prepareCall("{call get_image_pixels(?)}");

			call.setInt(1, imageId);
			ResultSet results = call.executeQuery();
			results.next();
			Blob blob = results.getBlob(1);
			image = Util.getImage(blob);

		} catch (IOException e) {
			LOG.error("IO exception " + e.getMessage());
		} catch (SQLException e) {
			LOG.error("SQL exeption " + e.getMessage());			
			e.printStackTrace();
		} finally {
			if (call != null) {
				try {
					call.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return image;
	}
	
	public void deleteImage(Integer id){
		CallableStatement call = null;
		
		try{
			System.out.println(id);
			connect();
			call = connection.prepareCall("{call delete_image(?)}");
			call.setInt("_idimage", id);
			call.execute();

		} catch (SQLException e) {
			LOG.error("SQL exeption " + e.getMessage());			
		} finally {
			if (call != null) {
				try {
					call.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}
	
	public Collection<SourceImage> importSourceThumbnails(){
		
		CallableStatement call = null;
		Collection<SourceImage> srcImages = null;
		
		try {
			connect();
			call = connection.prepareCall("{call get_source_images()}");

			ResultSet results = call.executeQuery();
			srcImages = new ArrayList<SourceImage>();
			
			while (results.next()) {
				Integer id = results.getInt("idimage");
				String name = results.getString("name");
				Long tstmp = results.getLong("tstmp");
				Blob blob = results.getBlob("thumb_pixels");
				BufferedImage thumb = Util.getImage(blob);
				
				SourceImage img = SourceImage.createThumb(id, name, tstmp, thumb);
				srcImages.add(img);
				
			}
//			call.close();
//
//			call = connection.prepareCall("{call get_board_images()}");
//			results = call.executeQuery();
//
//			while (results.next()) {
//				Integer id = results.getInt("idimage");
//				Integer sourceid = results.getInt("idsource");
//				Blob blob = results.getBlob("thumb_pixels");
//				BufferedImage thumb = Util.getImage(blob);
//
//				BoardImage img = new BoardImage();
//				img.setId(id);
//				img.setSource((SourceImage) images.get(sourceid));
//				img.setThumbnailPixels(thumb);
//
//				images.put(id, img);
//			}
		} catch (IOException e) {
			LOG.error("IO exception " + e.getMessage());

		} catch (SQLException e) {
			LOG.error("SQL exception " + e.getMessage());

		} finally {
			if (call != null) {
				try {
					call.close();
				} catch (SQLException ignore) {
				}
			}
		}
		
		return srcImages;	
	}
	
//	public Map<Integer, AbstractImage> importThumbnails() {
//
//		CallableStatement call = null;
//		Map<Integer, AbstractImage> images = new HashMap<Integer, AbstractImage>();
//
//		try {
//			connect();
//			call = connection.prepareCall("{call get_source_images()}");
//
//			ResultSet results = call.executeQuery();
//
//			while (results.next()) {
//				Integer id = results.getInt("idimage");
//				String name = results.getString("name");
//				Long tstmp = results.getLong("tstmp");
//				Blob blob = results.getBlob("thumb_pixels");
//				BufferedImage thumb = Util.getImage(blob);
//
//				SourceImage img = new SourceImage(id, name, tstmp, thumb);
//				
//				images.put(id, img);
//			}
//			call.close();
//
//			call = connection.prepareCall("{call get_board_images()}");
//			results = call.executeQuery();
//
//			while (results.next()) {
//				Integer id = results.getInt("idimage");
//				Integer sourceid = results.getInt("idsource");
//				Blob blob = results.getBlob("thumb_pixels");
//				BufferedImage thumb = Util.getImage(blob);
//
//				BoardImage img = new BoardImage();
//				img.setId(id);
//				img.setSource((SourceImage) images.get(sourceid));
//				img.setThumbnailPixels(thumb);
//
//				images.put(id, img);
//			}
//
//		} catch (IOException e) {
//			LOG.error("IO exception " + e.getMessage());
//
//		} catch (SQLException e) {
//			LOG.error("SQL exception " + e.getMessage());
//
//		} finally {
//			if (call != null) {
//				try {
//					call.close();
//				} catch (SQLException ignore) {
//				}
//			}
//		}
//		
//		return images;
//	}
	
//	public ImageRecord importImageRecord(String name) throws SQLException {
//		try {
//			if (connection == null) 
//				return null;
//
//			if (getImageRecord == null)
//				getImageRecord = connection
//						.prepareCall("{call get_thumbnail(?)}");
//			getImageRecord.setString("_name", name);
//
//			ResultSet results = getImageRecord.executeQuery();
//			results.first();
//
//			Integer id = results.getInt("idimage");
//			Integer sourceid = results.getInt("idsource");
//			String imgName = results.getString("name");
//			Blob blob = results.getBlob("thumb_pixels");
//			BufferedImage img = Util.getImage(blob);
//			return new ImageRecord(id, sourceid, imgName, img);
//
//		} catch (IOException e) {
//			LOG.error("IO exception " + e.getMessage());
//		}
//		return null;
//	}

//	public Map<Integer, Label> importAllLabels(){
//		CallableStatement call = null;
//		LinkedHashMap<Integer, Label> labels = new LinkedHashMap<Integer, Label>();
//
//		try{
//			connect();
//			call = connection.prepareCall("{call get_labels()}");
//			ResultSet results = call.executeQuery();
//			
//			while(results.next()){
//				Integer id = results.getInt("idlabel");
//				String title = results.getString("title");
//				
//				Label label = new Label(id, title);
//				labels.put(label.getId(), label);
//			}
//			
//			call.close();
//			
//		}catch(SQLException e){
//			LOG.error("SQL exception " + e.getMessage());			
//		} finally {
//			if (call != null) {
//				try {
//					call.close();
//				} catch (SQLException ignore) {
//				}
//			}
//		}
//		
//		return labels;
//
//	}

	// public LinkedHashMap<Integer, Collection<Integer>> importLabelsImages()
	// throws SQLException {
	// if(connection == null || connection.isClosed()) return null;
	//
	//
	// if (getAllImagesLabels == null) {
	// getAllImagesLabels = connection
	// .prepareCall("{call get_images_labels()}");
	// }
	// resultSet = getAllImagesLabels.executeQuery();
	// // LinkedHashMap<Integer, Integer> imagesLabels = new
	// // LinkedHashMap<Integer, Integer>();
	// LinkedHashMap<Integer, Collection<Integer>> labelsImages = new
	// LinkedHashMap<Integer, Collection<Integer>>();
	// while (resultSet.next()) {
	// Integer id_label = resultSet.getInt("idlabel");
	// Integer id_image = resultSet.getInt("idimage");
	// //
	// System.out.println("[IODatabase importImagesLabels] idlabel "+id_label+" idimage"+id_image);
	// if (labelsImages.containsKey(id_label)) {
	// labelsImages.get(id_label).add(id_image);
	// } else {
	// Collection<Integer> images = new ArrayList<Integer>();
	// images.add(id_image);
	// labelsImages.put(id_label, images);
	// }
	// }
	// for (Iterator iterator = labelsImages.entrySet().iterator(); iterator
	// .hasNext();) {
	// Entry<Integer, Collection<Integer>> entry = (Entry<Integer,
	// Collection<Integer>>) iterator
	// .next();
	// System.out.println("idlabel " + entry.getKey());
	// for (Iterator iterator2 = entry.getValue().iterator(); iterator2
	// .hasNext();) {
	// Integer idimage = (Integer) iterator2.next();
	// System.out.println(" idimage " + idimage);
	//
	// }
	// } 
	// return labelsImages;
	// }

//	public Collection<Integer> getLabeledImagesIds(Integer labelId)
//			throws SQLException {
//		if (connection == null || connection.isClosed())
//			return null;
//		if (getLabeledImageIds == null)
//			getLabeledImageIds = connection
//					.prepareCall("{call get_idimages_by_idlabel(?)}");
//		getLabeledImageIds.setInt("_idlabel", labelId);
//
//		ResultSet results = getLabeledImageIds.executeQuery();
//		Collection<Integer> ids = new ArrayList<Integer>();
//
//		while (results.next()) {
//			ids.add(results.getInt(1));
//		}
//
//		return ids;
//	}
	
	public void addLabel(String title){
		CallableStatement call = null;
		try{
			connect();		

			call = connection.prepareCall("{call out_test(?,?)}");
			
			call.setString(1, title);
			call.registerOutParameter(2, java.sql.Types.INTEGER);
			call.execute();
			System.out.println(call.getInt(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (call != null) {
				try {
					call.close();
				} catch (SQLException ignore) {
				}
			}
			
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException ignore) {
			}
	}
}
