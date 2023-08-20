import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Add_Swap_Guests {

	
	Database db = new Database();
	Statement statement = db.connectToDatabase();
	private static ResultSet resultSet2;
	private static ResultSet resultSet3;
	
	

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	
	
	
	// Модул за броенето на общия брой гости в таблицата от базата данни guests1...................................................................	
			public int showCommonNumberOfGuestsDB(int rowCount) {
			
			try {		 				
				String tableName = "guests_" + dateString;
					String query3 = "SELECT COUNT(*) FROM " + tableName;
					resultSet3 = statement.executeQuery(query3);
					
					if (resultSet3.next()) {
						rowCount = resultSet3.getInt(1);		              					
			        	} 
					
				} catch (Exception ex) {
	                ex.printStackTrace();
	            }
			return rowCount;
		}

			 String tableNameS = "selected_guests_" + dateString;

	// Модул за броенето на общия брой гости в таблицата от базата данни guests1...................................................................	
			public int showInNumberOfGuestsDB(int rowCount) {
		
		try {		 					
				String query3 = "SELECT COUNT(*) FROM " + tableNameS;
				resultSet3 = statement.executeQuery(query3);
				
				if (resultSet3.next()) {
					rowCount = resultSet3.getInt(1);		              					
		        	} 
				
			} catch (Exception ex) {
	            ex.printStackTrace();
	        }
		return rowCount;
	}
		
			
		
		// Модул за местене на избрания ред и инф. за госта в таблицата SELECTED_GUESTS................................................................
			public void shiftGuestsToSelected(JLabel inPeople, JLabel remPeople, JTable table, ListSelectionEvent e) {
			 try {
				 			
	    		 Connection connection = Database.getDBConnection();
	    		 
	    		 
	    		 int row = table.getSelectedRow();

	 	           if(row==-1 ) {
		            	System.out.println("YES_noRow");
		            	return;
		            } 

	 	            String roomNumber = (String) table.getValueAt(row, 0);
	 	            String name = (String) table.getValueAt(row, 1);
	 	            String gender = (String) table.getValueAt(row, 2);
	 	           String age = (String) table.getValueAt(row, 3);
	 	            String checkin = (String) table.getValueAt(row, 4);
	 	            String checkout = (String) table.getValueAt(row, 5);

	 	           
	 	            
	 	            // Тук е модула за местене на гости в SELECTED_GUESTS
	 	           Add_Shift_Guests_DB asgDB = new Add_Shift_Guests_DB();
	 	          asgDB.addGuestToSelectedGuestsDatabase(table, connection, roomNumber, name, gender, age, checkin, checkout);
	 	          
	 	          
	 	         int selectedRow = table.getSelectedRow();
	             
		            if (selectedRow != -1) {
		                // Изтриване на данните от съответния ред
		                ((DefaultTableModel)table.getModel()).removeRow(selectedRow);
		             	System.out.println("YES_2");
		             
		             	
		             	int c=showInNumberOfGuestsDB(0);		             	
		             	int b=showCommonNumberOfGuestsDB(0);
		             		             	
		             	c++;
		             	b--;
		             	System.out.println("NOO");
		             	
		             	if(c-1==1) {
		             		inPeople.setText(Integer.toString(c-1) + " човек");
		             	}else {
		             		inPeople.setText(Integer.toString(c-1) + " човека");             		
		             	}

		             	if(b+1==1) {
		             		remPeople.setText(Integer.toString(b+1) + " човек");
		             	}else {
		             		remPeople.setText(Integer.toString(b+1)+ " човека");
		             	}
		             	
		             	
		             		   
		             // Тук е метода за броенето на оставащите хора в табилцата с база данни 

		    	            
		    	            
		             }
	      
	 	        
			        
			        
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			        
		}
		
		
		// Модул за връщането на инф. за госта в таблицата за невлезли гости като взима избрания ред и ...............................................
			public void shiftSelectedToGuests(JLabel inPeople, JLabel remPeople, JTable table, ListSelectionEvent e) {
				
				 try {
					// Декларираме connection за последстващо осъществяване на връзка на таблицата с базата данни
			    		 Connection connection = Database.getDBConnection();
				       
			    		 // Взима избрания ред от таблицата 
				       
		 	           
			    		 int row = table.getSelectedRow();
			    		 
			    		 
		             	System.out.println("NOO");
		             	
		             	
		             	if(row==-1 ) {
			            	System.out.println("YES2_noRow");
			            	return;
			            } 
		             	
		 	            
		 	        // Взима стойностите от избрания ред
		 	            String roomNumber = (String) table.getValueAt(row, 0);
		 	            String name = (String) table.getValueAt(row, 1);
		 	            String gender = (String) table.getValueAt(row, 2);
		 	           String age = (String) table.getValueAt(row, 3);
		 	            String checkin = (String) table.getValueAt(row, 4);
		 	            String checkout = (String) table.getValueAt(row, 5);

		 	            
		 	            // Тук е Функцията за местене на гости в ИЗБРАНИ
		 	           Add_Shift_Guests_DB asgDB = new Add_Shift_Guests_DB();
		 	          asgDB.removeGuestFromSelectedGuestsDatabase(table, connection, roomNumber, name, gender, age, checkin, checkout);
		 	          
		 	          
		 	          
		 	         int selectedRow = table.getSelectedRow();
		             
			            if (selectedRow != -1) {
			                // Изтриване на данните от съответния ред
			                ((DefaultTableModel)table.getModel()).removeRow(selectedRow);
			             	System.out.println("YES_2");
			             	
			             	
			             	int c=showInNumberOfGuestsDB(0);		             	
			             	int b=showCommonNumberOfGuestsDB(0);
			             		             	
			             	c--;	
			             	System.out.println("NOO");
			             		   
			             // Тук е метода за броенето на оставащите хора в табилцата с база данни 
			    	    		asgDB.countNumber(remPeople, b++);		    	           
			    	    		asgDB.countNumberNull(inPeople, c);
			    	            
			    	            
			             
		 	          
			            
				       }
				        
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				       
			}
			
			
			// Модул за показването на гостите дори когато полето за въвеждане е празно .................................................
			public void displayPersonsInSelected_Guests(DefaultTableModel model2, JTable table2, JLabel inPeople, JLabel remPeople) {
				try {
					
							String query2 = "SELECT * FROM " + tableNameS;     		 					
	 					resultSet2 = statement.executeQuery(query2);
					
		 				while (resultSet2.next()) {
		 		            String name2 = resultSet2.getString("name1");
		 		            String gender = resultSet2.getString("gender");
		 		           String age = resultSet2.getString("age");
		 		            String room_number = resultSet2.getString("room_number");
		 		            String checkin = resultSet2.getString("check_in");
		 		            String checkout = resultSet2.getString("check_out");
		 		            
		 		           model2.addRow(new Object[] {room_number, name2, gender, age, checkin, checkout});	   
			           
		 		           
		 		          table2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		 		        	    @Override
		 		        	    public void valueChanged(ListSelectionEvent e) {
		 		        	        if (!e.getValueIsAdjusting()) {
		 		        	            // Разпознава, кога ред от таблицата е избран, чете инф. от нея и мести гостите в ИЗБРАНИ
		 		        	        	shiftSelectedToGuests(inPeople, remPeople, table2, e); 
		 		        	        }
		 		        	    }
		 		        	});
		 		           
					
	 		      

	 		        	} 
				
					} catch (Exception ex) {
		                ex.printStackTrace();
		            }
			}
			
	
}
