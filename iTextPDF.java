import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;




public class iTextPDF {	
	
	private PleaseApp mainApp;
	
	Database db = new Database();
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	String dateString2 = dateFormat2.format(new Date(System.currentTimeMillis()));

	
	 // Модул за осъществяване на връзка с базата данни чрез API JDBC driver................................................................... 
	Statement statement = db.connectToDatabase();
	
	
	 // Модул за създаване на pdf file съдържащ инф. на гостите от двете таблици чрез iText API ............................................... 
	public void makeiTextPDFfile(JFrame frame1){
		
		 JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Save PDF");
	        int userSelection = fileChooser.showSaveDialog(null);

	        if (userSelection == JFileChooser.APPROVE_OPTION) {
	            File fileToSave = fileChooser.getSelectedFile();            
	            String fileName = fileToSave.getName();

	            
	            
	            
	            String message = "PDF file <font color='green'> '" + fileName + "'</font> has been saved successfully";
           JOptionPane.showMessageDialog(frame1, "<html>" + message + "</html>");
	            
	            
	
	try {		
		String tableName = "guests_" + dateString;
		 ResultSet rs1 = statement.executeQuery("SELECT * FROM " + tableName);

         PdfWriter writer = new PdfWriter(new FileOutputStream(fileToSave + " " + dateString2 + ".pdf"));
         PdfDocument pdfDoc = new PdfDocument(writer);
         Document document = new Document(pdfDoc);
         
         
         Add_Swap_Guests AsG = new Add_Swap_Guests();
	        
	        
	        int c=AsG.showInNumberOfGuestsDB(0);		             	
      	int b=AsG.showCommonNumberOfGuestsDB(0);      		             	
      	c++;
      	b--;
      	System.out.println("NOO");
      	
      	String inPeople = Integer.toString(c-1) + " people are IN! ";     
      	String remPeople = Integer.toString(b+1)+ " people are REMAINING! ";      
      	String m = "                                                                        ";
      	String sum = "  "+ inPeople + m + remPeople;


         
         Table table1 = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1,1,1,1}));
         
         PdfFont f = PdfFontFactory.createFont(StandardFonts.HELVETICA);
         Cell cell = new Cell(6,6)
                 .add(new Paragraph("Guests" + " " + dateString2))
                 .setFont(f)
                 .setFontSize(13)
                 .setFontColor(DeviceGray.WHITE)
                 .setBackgroundColor(DeviceGray.BLACK)
                 .setTextAlignment(TextAlignment.CENTER);
         
         Cell cell3 = new Cell(6,6)
                 .add(new Paragraph(sum).setTextAlignment(TextAlignment.LEFT))
                 .setFont(f)
                 .setFontSize(13)
                 .setFontColor(DeviceGray.WHITE)
                 .setBackgroundColor(DeviceGray.BLACK)
                 .setTextAlignment(TextAlignment.LEFT);
                
       
         table1.addHeaderCell(cell);
         table1.addHeaderCell(cell3);
    
         
         table1.setWidth(UnitValue.createPercentValue(100));
         table1.setHeight(UnitValue.createPercentValue(20));
         table1.setFontSize(10);
         
         table1.addCell(new Cell().add(new Paragraph("Room number")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table1.addCell(new Cell().add(new Paragraph("Name")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table1.addCell(new Cell().add(new Paragraph("Gender")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table1.addCell(new Cell().add(new Paragraph("Age")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table1.addCell(new Cell().add(new Paragraph("Check-in")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table1.addCell(new Cell().add(new Paragraph("Check-out")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         
    
         table1.setBackgroundColor(DeviceGray.makeLighter(DeviceGray.GRAY));
         
         while (rs1.next()) {

             table1.addCell(rs1.getString("romm_number")); 
             table1.addCell(rs1.getString("name"));
             table1.addCell(rs1.getString("gender"));
             table1.addCell(rs1.getString("age"));
             table1.addCell(rs1.getString("check_in"));
             table1.addCell(rs1.getString("check_out"));
         }
         
         				         
 		String tableName2 = "selected_guests_" + dateString;
         ResultSet rs2 = statement.executeQuery("SELECT * FROM " + tableName2);
         
         Table table2 = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1,1,1,1}));
         
         Cell cell2 = new Cell(6,6)
                 .add(new Paragraph("Selected guests" + " " + dateString2))
                 .setFont(f)
                 .setFontSize(13)
                 .setFontColor(DeviceGray.WHITE)
                 .setBackgroundColor(DeviceGray.BLACK)
                 .setTextAlignment(TextAlignment.CENTER);
         							            
         table2.addHeaderCell(cell2);
         						            
         
         table2.setWidth(UnitValue.createPercentValue(100));
         table2.setHeight(UnitValue.createPercentValue(40));
         table2.setFontSize(10);
         
         table2.addCell(new Cell().add(new Paragraph("Room number")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table2.addCell(new Cell().add(new Paragraph("Name")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table2.addCell(new Cell().add(new Paragraph("Gender")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table2.addCell(new Cell().add(new Paragraph("Age")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table2.addCell(new Cell().add(new Paragraph("Check-in")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         table2.addCell(new Cell().add(new Paragraph("Check-out")).setBackgroundColor(DeviceGray.GRAY).setTextAlignment(TextAlignment.CENTER));
         
    
         table2.setBackgroundColor(DeviceGray.makeLighter(DeviceGray.GRAY));
         
         while (rs2.next()) {
             table2.addCell(rs2.getString("room_number"));
             table2.addCell(rs2.getString("name1"));
             table2.addCell(rs2.getString("gender"));
             table2.addCell(rs2.getString("age"));
             table2.addCell(rs2.getString("check_in"));
             table2.addCell(rs2.getString("check_out"));
         }
         

         document.add(table1);
         
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));	
         
         document.add(table2);
         document.close();
         writer.close();
         
         mainApp.setPdfCreated(true);
         
         
         System.out.println("PDF saved to: " + fileToSave.getAbsolutePath());
		
} catch (Exception ex) {
ex.printStackTrace();
}
	
}
	}
}



