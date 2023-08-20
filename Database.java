
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static Connection connection;
    private Statement statement;

    public Statement connectToDatabase() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Guests2", "root", "td13001300");
            // Create a statement
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
    
    
    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {  
			if (connection == null || connection.isClosed()) {
			    Class.forName("com.mysql.cj.jdbc.Driver");
			    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/guests2", "root", "td13001300");
			}
        return connection;
    }
    
    
}