package dataaccess;

import imageprocessing.Util;

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
import java.sql.Statement;

import javax.imageio.ImageIO;

import test.Test;

public class ImageDownloadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection dbConnection = null;
		Statement statement = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
//		catch (ClassNotFoundException e) {
//			System.out.println("driver not found");
//			System.out.println(e.getMessage());
//
//		}
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/image_test", "imgmanager",
					"idefix");
			System.out.println("Connection successful");

			// read image and create input stream for blob

			pStatement = dbConnection.prepareStatement("SELECT * FROM images WHERE image_name='wb08.png'");
			//statement = dbConnection.createStatement();
			resultSet = pStatement.executeQuery();
			resultSet.next();
			
			Blob pixels = resultSet.getBlob("picture");
			InputStream imgIs = pixels.getBinaryStream();
			BufferedImage img = ImageIO.read(imgIs);
			Test.showImage(img, "wb08.png");
 
		} catch (SQLException e) {
			System.out.println("sql exception");
			System.out.println(e.getMessage());

		}  catch (IOException e) {
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
