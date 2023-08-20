import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Add_Shift_Guests_DB {
	
	public static ResultSet resultSet;
	
	

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	
	
	
	// Метод за броенето на оставащите хора в табилцата с база данни 
	public int countNumber(JLabel remPeople, int commonNumber) {
	
		commonNumber++;
		remPeople.setText(Integer.toString(commonNumber-1));
		return commonNumber;
		
	}
	
	
	public int countNumberNull(JLabel inPeople, int commonNumber) {
		
		commonNumber++;
		inPeople.setText(Integer.toString(commonNumber));
		return commonNumber;
		
	}
	
	
	 String tableName = "guests_" + dateString;
	 String tableNameS = "selected_guests_" + dateString;
	
	public void addGuestToSelectedGuestsDatabase(JTable table, Connection connection, String roomNumber, String name, String gender, String age, String checkin, String checkout) {
		
		 try {
			 
			
             
			 String insertSQL = "INSERT INTO " +tableNameS+ " (room_number, name1, gender, age, check_in, check_out) VALUES (?, ?, ?, ?, ?, ?)";
	          PreparedStatement preparedStatement = ((Connection) connection).prepareStatement(insertSQL);
	          preparedStatement.setString(1, roomNumber);
	          preparedStatement.setString(2, name);
	          preparedStatement.setString(3, gender);
	          preparedStatement.setString(4, age);
	          preparedStatement.setString(5, checkin);
	          preparedStatement.setString(6, checkout);

	          preparedStatement.executeUpdate();

	          // Премахване на избраният ред от таблицата
	          String deleteSQL = "DELETE FROM "+ tableName + " WHERE romm_number = ? AND name = ? AND gender = ? AND age = ? AND check_in = ? AND check_out = ?";
	          preparedStatement = ((Connection) connection).prepareStatement(deleteSQL);
	          preparedStatement.setString(1, roomNumber);
	          preparedStatement.setString(2, name);
	          preparedStatement.setString(3, gender);
	          preparedStatement.setString(4, age);
	          preparedStatement.setString(5, checkin);
	          preparedStatement.setString(6, checkout);
	          preparedStatement.executeUpdate();
               
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
		
		
	  }
	
	
	public void removeGuestFromSelectedGuestsDatabase(JTable table, Connection connection, String roomNumber, String name, String gender, String age, String checkin, String checkout) {
		
		 try {
            
			 String insertSQL = "INSERT INTO "+ tableName + " (romm_number, name, gender, age, check_in, check_out) VALUES (?, ?, ?, ?, ?, ?)";
	          PreparedStatement preparedStatement = ((Connection) connection).prepareStatement(insertSQL);
	          preparedStatement.setString(1, roomNumber);
	          preparedStatement.setString(2, name);
	          preparedStatement.setString(3, gender);
	          preparedStatement.setString(4, age);
	          preparedStatement.setString(5, checkin);
	          preparedStatement.setString(6, checkout);

	          preparedStatement.executeUpdate();

	          // Премахване на избраният ред от таблицата
	          String deleteSQL = "DELETE FROM " +tableNameS+ " WHERE room_number = ? AND name1 = ? AND gender = ? AND age = ? AND check_in = ? AND check_out = ?";
	          preparedStatement = ((Connection) connection).prepareStatement(deleteSQL);
	          preparedStatement.setString(1, roomNumber);
	          preparedStatement.setString(2, name);
	          preparedStatement.setString(3, gender);
	          preparedStatement.setString (4, age);
	          preparedStatement.setString(5, checkin);
	          preparedStatement.setString(6, checkout);
	          preparedStatement.executeUpdate();
	          
	          	        
	            
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
		
		
	  }
	
	

}
