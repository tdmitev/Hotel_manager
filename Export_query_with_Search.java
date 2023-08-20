import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Export_query_with_Search {
	
	public JTextField Search;
	
	private static ResultSet resultSet;
	private static ResultSet resultSet4;
	
	
	Database db = new Database();
	Statement statement = db.connectToDatabase();
	
	

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	
	
	// Модул за търсенето на гости чрез въвеждането на стая и създаването на редовете с гостите .................................................
		public void addGuestsByroom(DefaultTableModel model, JTextField Search) {
			
			try {		 					
					String room = Search.getText();					
					String tableName = "guests_" + dateString;
					
					String query = "SELECT * FROM " + tableName + " WHERE romm_number LIKE '%" + room + "%'";
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
			            String name = resultSet.getString("name");
			            String gender = resultSet.getString("gender");
			            String age = resultSet.getString("age");
			            String room_number = resultSet.getString("romm_number");
			            String checkin = resultSet.getString("check_in");
			            String checkout = resultSet.getString("check_out");
			            
			           model.addRow(new Object[] {room_number, name, gender, age, checkin, checkout});	   
			                		 		           

			        	} 
					
				} catch (Exception ex) {
	                ex.printStackTrace();
	            }
			
		}
		
		
		 String tableNameS = "selected_guests_" + dateString;
		
		// Модул за търсенето на гости чрез въвеждането на стая и създаването на редовете с гостите за втората таблца ................................
		public void addGuestsByroomT2(DefaultTableModel model, JTextField Search) {
					
					try {	
						
							String room = Search.getText();
							String query = "SELECT * FROM " + tableNameS + " WHERE room_number LIKE '%" + room + "%'";
							resultSet = statement.executeQuery(query);

							while (resultSet.next()) {
					            String name = resultSet.getString("name1");
					            String gender = resultSet.getString("gender");
					            String age = resultSet.getString("age");
					            String room_number = resultSet.getString("room_number");
					            String checkin = resultSet.getString("check_in");
					            String checkout = resultSet.getString("check_out");
					            
					           model.addRow(new Object[] {room_number, name, gender, age, checkin, checkout});	   
					                		 		           

					        	} 
							
						} catch (Exception ex) {
			                ex.printStackTrace();
			            }
					
				}
				
				
				
				// Модул за търсенето на гости чрез въвеждането на име и създаването на редовете с гостите .................................................
				public void displayNamesOfPeople(DefaultTableModel model, JTextField Search) {
					
					
					Database db = new Database();
					Statement statement = db.connectToDatabase();
					
					try {				
						String name = Search.getText();
						 String query = "SELECT * FROM "+ tableNameS +" WHERE name1 LIKE '%" + name + "%'";			 
						 resultSet4 = statement.executeQuery(query);

						 
				        while (resultSet4.next()) {
				        	Object[] row = new Object[6];
				            row[0] = resultSet4.getString("room_number");
				            row[1] = resultSet4.getString("name1");
				            row[2] = resultSet4.getString("gender");
				            row[3] = resultSet4.getString("age");
				            row[4] = resultSet4.getString("check_in");
				            row[5] = resultSet4.getString("check_out");
				            model.addRow(row);
				
				        }



				    } catch (SQLException e) {
				        e.printStackTrace();
				    }
				}
	
	
				
				

}
