package dataaccess;

import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

public class ImageUploadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection dbConnection = null;
		Statement statement = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/image_test", "imgmanager",
					"idefix");
			System.out.println("Connection successful");
			
			// read image and create input stream for blob
			BufferedImage image = Util.readFromFile("images/whiteboard/low_skewness/wb08.png");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());	
			
			pStatement = dbConnection.prepareStatement("INSERT INTO images (image_name, picture) VALUES (?, ?)");
			pStatement.setString(1, "wb08.png");
			pStatement.setBlob(2, is);
			System.out.println(pStatement.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		} catch (ClassNotFoundException e) {
			System.out.println("driver not found");
			System.out.println(e.getMessage());
			
		} catch (IOException e) {
			System.out.println("error during wrting image to byte array");
			System.out.println(e.getMessage());
		} finally {
			if (dbConnection != null)
				try {
					dbConnection.close();
				} catch (SQLException ignore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException ignore) {
				}
			if (pStatement != null)
				try {
					pStatement.close();
				} catch (SQLException ignore) {
				}
		}
	}

}
