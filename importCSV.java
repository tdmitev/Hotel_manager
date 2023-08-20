import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.itextpdf.io.exceptions.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class importCSV {
	
	Database db = new Database();
	Statement statement = db.connectToDatabase();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	
	 String guestsTable = "guests" + "_" + dateString;
	    String selectedGuestsTable = "selected_guests"+ "_" + dateString;
	    
	    
	    
	    public void createDropTables(JFrame frame1, boolean tableExists, boolean tableExists1) {
	    	try {
	    	if (!tableExists && !tableExists1) {
		           
	            statement.execute("CREATE TABLE " + guestsTable  +  " LIKE Guests3");
	            statement.execute("CREATE TABLE " + selectedGuestsTable + " LIKE selected_guests3");
	            
	            JOptionPane.showMessageDialog(frame1, "Tables are created,");
	            
	        }else {
	        	
	        	statement.execute("DROP TABLE " + guestsTable);
	        	statement.execute("DROP TABLE " + selectedGuestsTable);
	        	
	        	statement.execute("CREATE TABLE " + guestsTable  +  " LIKE Guests3");
	            statement.execute("CREATE TABLE " + selectedGuestsTable + " LIKE selected_guests3");
	        	
	        }
	    	
	    	} catch (IOException | SQLException e1) {
		        e1.printStackTrace();
		    }
	    	
	    }
	    
	    public void tableExists(JFrame frame1) {
	    	
	    	try {
	    		
	    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Guests2", "root", "td13001300");
		        DatabaseMetaData dbmd = con.getMetaData();
		        ResultSet rs = dbmd.getTables(null, null, guestsTable, null);
		        ResultSet rs1 = dbmd.getTables(null, null, selectedGuestsTable, null);
		        boolean tableExists = rs.next();
		        boolean tableExists1 = rs1.next();
		        rs.close();
		        rs1.close();
	    		
	    	// Създаване на таблица ако не съществува вече
	        if (!tableExists && !tableExists1) {
	           
	            statement.execute("CREATE TABLE " + guestsTable  +  " LIKE Guests3");
	            statement.execute("CREATE TABLE " + selectedGuestsTable + " LIKE selected_guests3");
	            
	            JOptionPane.showMessageDialog(frame1, "Tables are created,");
	        	
	        	
	        }
	        
	    	} catch (IOException | SQLException e1) {
		        e1.printStackTrace();
		    }
	    	
	    }
	    
		public void importCSVfile(JLabel inPeople, JLabel remPeople, JFrame frame1){
			
			
			  try {			    

					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Guests2", "root", "td13001300");
			        DatabaseMetaData dbmd = con.getMetaData();
			        ResultSet rs = dbmd.getTables(null, null, guestsTable, null);
			        ResultSet rs1 = dbmd.getTables(null, null, selectedGuestsTable, null);
			        boolean tableExists = rs.next();
			        boolean tableExists1 = rs1.next();
			        rs.close();
			        rs1.close();
			        
			        // Импортиране на данните от CSV file
			        JFileChooser fileChooser = new JFileChooser();
			        fileChooser.setDialogTitle("Choose CSV File");
			        int userSelection = fileChooser.showOpenDialog(fileChooser);
			        
			        if (userSelection == JFileChooser.APPROVE_OPTION) {		            
			            File csvFile = fileChooser.getSelectedFile();
			            String fileName = csvFile.getName();

			            String message = "CSV file <font color='green'>'" + fileName + "'</font> has been added successfully";

			            
			            createDropTables(frame1, tableExists, tableExists1);
			            
			            
			           
			            String query = "INSERT INTO " + guestsTable + " (romm_number, name, age, gender, check_in, check_out) VALUES (?, ?, ?, ?, ?, ?)";
			            PreparedStatement pst = con.prepareStatement(query);
			            CSVReader reader = new CSVReader(new FileReader(csvFile));
			            String[] nextLine;
			            reader.readNext(); // skip header line
			            while ((nextLine = reader.readNext()) != null) {
			                pst.setInt(1, Integer.parseInt(nextLine[0]));
			                pst.setString(2, nextLine[1]);
			                pst.setInt(3, Integer.parseInt(nextLine[2]));
			                pst.setString(4, nextLine[3]);
			                pst.setDate(5, Date.valueOf(nextLine[4]));
			                pst.setDate(6, Date.valueOf(nextLine[5]));
			                pst.executeUpdate();
			            }
			            
			            
			            
			            
			            JOptionPane.showMessageDialog(frame1, "<html>" + message + "</html>");
			            
			        }else {
			        	
			        	JOptionPane.showMessageDialog(frame1, "CSV file hasn't been added!");
			        	
			        }
			        			        
			        
			        Add_Swap_Guests AsG = new Add_Swap_Guests();
			        
			        
			        int c=AsG.showInNumberOfGuestsDB(0);		             	
	             	int b=AsG.showCommonNumberOfGuestsDB(0);
	             		             	
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
			        
	             	
	             	
			    } catch (IOException | java.io.IOException | CsvValidationException | SQLException e1) {
			        e1.printStackTrace();
			    }
			
			

		}
		
		
		
}
		
	    
	    
	
	    
	
