package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTest {
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
//			statement = dbConnection.createStatement();
//			statement
//					.execute("CREATE TABLE books (id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), author VARCHAR(100), publisher VARCHAR(100))");
//			statement.execute("INSERT INTO books (name, author, publisher) VALUES ('Harry Potter and Big dicks', 'JK lolling', 'Apple Corp')");
//			resultSet = statement.executeQuery("SELECT * FROM books");
			
//			while(resultSet.next()){
//				Long id = resultSet.getLong("id");
//				String name = resultSet.getString("name");
//				String author = resultSet.getString("author");
//				String publisher = resultSet.getString("publisher");
//				
//				System.out.println(" "+id+" "+name+" "+author+" "+publisher);
//			}
			
			pStatement = dbConnection.prepareStatement("INSERT INTO books (id, name, author, publisher) VALUES (?, ?, ?, ?)");
			pStatement.setInt(1, 1);
			pStatement.setString(2, "Biblia Szatana");
			pStatement.setString(3, "Julian Tuwim");
			pStatement.setString(4, "Wyd. szkolne i pedagogiczne");
			
			System.out.println(pStatement.executeUpdate());
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		} catch (ClassNotFoundException e) {
			System.out.println("driver not found");
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
