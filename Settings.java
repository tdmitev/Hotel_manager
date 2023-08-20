import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.TextRenderer;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.swing.JRadioButton;

public class Settings {

	public JFrame frame;
	private Properties properties;
    private File settingsFile;
    private JRadioButton rdbtnNewRadioButton;
    
    private PleaseApp mainApp;
   

    
	/**
	 * Launch the application.
	 */
	
	public Settings(PleaseApp mainApp) {
        this.mainApp = mainApp;
        initialize();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	
Database db = new Database();
iTextPDF iTxt = new iTextPDF();
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	String dateString2 = dateFormat2.format(new Date(System.currentTimeMillis()));

	
	 // Модул за осъществяване на връзка с базата данни чрез API JDBC driver................................................................... 
	Statement statement = db.connectToDatabase();
	
	
	
	private File getCurrentFolder() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String defaultDir = prefs.get("defaultDir", null);

        if (defaultDir != null) {
            String dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return new File(defaultDir, dateString);
        } else {
            return null;
        }
    }
	
	public void showCurDirectory() {
	    File currentFolder = getCurrentFolder();
	    if (currentFolder != null) {
	        File parentFolder = currentFolder.getParentFile();
	        File PDFFolder2 = currentFolder;

	        JOptionPane.showMessageDialog(null,"Директорията, в която се създават папките: "
	        		+ "\n " + parentFolder 
	        		+ "\n " 
	        		+ "\n Директорията, в която се създават PDF файловете днес: "
	        		+ "\n " + PDFFolder2);
	    } else {
	        JOptionPane.showMessageDialog(null, "Не е избрана директория!");
	    }
	}
	
	
	public void exportPDF(JFrame frame1) {
			
		
	    if (!getCurrentFolder().exists()) {
	        getCurrentFolder().mkdir();
	        JOptionPane.showMessageDialog(null, "NEW file has created successfully!");
	    }

	    // Избиране на подходящата опция спрямо часът на експортиране на файла
	    LocalTime time = LocalTime.now();
	    String option;
	    if (time.isBefore(LocalTime.of(12, 0))) {
	        option = "закуска";
	    } else if (time.isBefore(LocalTime.of(18, 0))) {
	        option = "обяд";
	    } else {
	        option = "вечеря";
	    }

	    // Запазване на PDF файла в папката, създадена за деня
	    File fileToSave = new File(getCurrentFolder().getPath() + "/" + option + ".pdf");

	    PdfWriter writer;
	    try {
	    	
	    	String tableName = "guests_" + dateString;
			 ResultSet rs1 = statement.executeQuery("SELECT * FROM " + tableName);
	    	
	        writer = new PdfWriter(new FileOutputStream(fileToSave));
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


         
         // Вмъкване на данните от таблицата в програмата в PDF файла 
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
         
         // Вмъкване на данните от таблицата в програмата в PDF файла 
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
        
        // Добавяне на заглавие
         float xTitlePosition = 150;
         float yTitlePosition = 780;
         String textContent = "That is the PDF file";
         Paragraph paragraph = new Paragraph(textContent)
        	        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
        	        .setFontSize(34)
        	        .setFixedPosition(xTitlePosition, yTitlePosition, 300);
        	document.add(paragraph);
         
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));	
         
         document.add(table1);
         
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));
         document.add(new Paragraph(" "));	
         
         document.add(table2);
         document.close();
         writer.close();
         
         File parentFolder = fileToSave.getParentFile();
         String folderName = parentFolder.getName();
         
         String message = "PDF файл <font color='green'> '" + option + "'</font> е запазен успешно в папка " + folderName + "!";
         JOptionPane.showMessageDialog(frame1, "<html>" + message + "</html>");
         
         mainApp.setPdfCreated(true);
        
         System.out.println("PDF saved to: " + fileToSave.getAbsolutePath());
	        
	        
	        
	    } catch (IOException | SQLException e1) {
	        e1.printStackTrace();
	    }
	}
	
	
	
	public void changeDefaultDirectory() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Choose new default directory to create container folders");
	    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	    int result = fileChooser.showSaveDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedDir = fileChooser.getSelectedFile();

	        // Запазване на избраната директория като нова default директория
	        Preferences prefs = Preferences.userNodeForPackage(getClass());
	        prefs.put("defaultDir", selectedDir.getAbsolutePath());
	        JOptionPane.showMessageDialog(null, "NEW directory has set successfully!");
	    }
	}

	public void showCurrentDirectory() {
	    JOptionPane.showMessageDialog(null, getCurrentFolder());
	    System.out.println("YESSSS");
	}

	public void resetDefaultDirectory() {
	    Preferences prefs = Preferences.userNodeForPackage(getClass());
	    prefs.remove("defaultDir");
	}
	
	
	public void print1PDF(File pdfFile) {
	    try {
	        // Зареждане на PDF файл като PDDocument
	        PDDocument document = PDDocument.load(pdfFile);

	        PrinterJob job = PrinterJob.getPrinterJob();
	        
	        // Задаване на PDF документ като източник на данни за принтера
	        job.setPageable(new PDFPageable(document));

	        // Показване на диалог за избор на принтер
	        if (job.printDialog()) {
	            // Ако потребителят приеме диалога и избере принтер, се изпраща сигнал за принтиране
	            job.print();
	        } else {
	            // Потребителят е отказал диалога
	            JOptionPane.showMessageDialog(null, "Принтирането беше отменено.");
	        }
	        
	        // Затваряне на PDF документа
	        document.close();
	    } catch (Exception e) {
	        // Показване на съобщение за грешка, ако нещо се обърка
	        JOptionPane.showMessageDialog(null, "Възникна грешка при принтирането: " + e.getMessage());
	    }
	}
	
	
	
	public void chooseAndPrintPDF() {
	    // Определя се папката с текущата дата
	    File currentFolder = getCurrentFolder();
	    
	    // Ако папката съществува, се показва JFileChooser
	    if (currentFolder != null && currentFolder.exists()) {
	        JFileChooser fileChooser = new JFileChooser(currentFolder);
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
	        
	        int returnValue = fileChooser.showOpenDialog(null);
	        
	        // Ако потребителят е избрал файл, се принтира
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            print1PDF(selectedFile);
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Папката не съществува!");
	    }
	}
	
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 387, 280);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		settingsFile = new File("settings.properties");
        properties = new Properties();
        loadSettings();

		
		if (getCurrentFolder() == null) {
		    JOptionPane.showMessageDialog(frame, "Моля, изберете директория, преди да продължите.");
		    changeDefaultDirectory();
		}
		
		if (!getCurrentFolder().exists()) {
			getCurrentFolder().mkdir();
		    JOptionPane.showMessageDialog(null, "NEW file has created successfully!");
		}
		
		rdbtnNewRadioButton = new JRadioButton("Export PDF with chosen from you direction and name");
		rdbtnNewRadioButton.setBounds(6, 213, 375, 23);
		 rdbtnNewRadioButton.setSelected(Boolean.parseBoolean(properties.getProperty("radioButtonSelected", "false")));
	        frame.getContentPane().add(rdbtnNewRadioButton);
	
		
		
		JButton btnNewButton = new JButton("Export PDF");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (rdbtnNewRadioButton.isSelected()) {
					iTxt.makeiTextPDFfile(frame);
                    System.out.println("Using method 2");
                    // makeiTextPDFfile(frame); // Извикване на втория метод
                } else {
                	exportPDF(frame);
                    System.out.println("Using method 1");
                    // createPdfAutomatically(); // Извикване на първия метод
                }
				
			}
		});
		btnNewButton.setBounds(39, 66, 140, 37);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnChangeDir = new JButton("Change dir");
		btnChangeDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeDefaultDirectory();
			}
		});
		btnChangeDir.setBounds(191, 66, 140, 37);
		frame.getContentPane().add(btnChangeDir);
		
		JButton btnResetDir = new JButton("Reset dir");
		btnResetDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				 int option = JOptionPane.showConfirmDialog(frame, "Наистина ли искате да изтриете сегашната директория, "
				 		+ "\n в която се създават файловете, в които се създават PDF файловете?", "Reset directory", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			        if (option == JOptionPane.YES_OPTION) {
			        	resetDefaultDirectory();			        	
			        } 
				
				
			}
		});
		btnResetDir.setBounds(39, 115, 140, 37);
		frame.getContentPane().add(btnResetDir);
		
		JButton btnShowCurrDir = new JButton("Show curr dir");
		btnShowCurrDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	 
				
				showCurDirectory();
			}
		});
		btnShowCurrDir.setBounds(191, 115, 140, 37);
		frame.getContentPane().add(btnShowCurrDir);	
		
		JLabel lblNewLabel = new JLabel("Настройки ");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBounds(126, 17, 128, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				chooseAndPrintPDF();
                
				
			}
		});
		btnPrint.setBounds(191, 164, 140, 37);
		frame.getContentPane().add(btnPrint);
		
		JButton btnImportCsv = new JButton("Import CSV");
		btnImportCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				
			}
		});
		btnImportCsv.setBounds(39, 164, 140, 37);
		frame.getContentPane().add(btnImportCsv);
		
		
		 frame.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	                saveSettings();
	            }
	        });
	    }

	private void loadSettings() {
	    if (settingsFile.exists()) {
	        try (FileInputStream fis = new FileInputStream(settingsFile)) {
	            properties.load(fis);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {

	        properties.setProperty("radioButtonSelected", "defaultValue");

	        // Запазване на default нсатройките за запазване на файла
	        saveSettings();
	    }
	}

	    private void saveSettings() {
	        properties.setProperty("radioButtonSelected", String.valueOf(rdbtnNewRadioButton.isSelected()));
	        try (FileOutputStream fos = new FileOutputStream(settingsFile)) {
	            properties.store(fos, null);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public boolean isRadioButtonSelected() {
	        return rdbtnNewRadioButton.isSelected();
	    }
	    
}
