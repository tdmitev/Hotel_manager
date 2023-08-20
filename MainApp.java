import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.itextpdf.io.exceptions.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import KentHipos.Kensoft;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Container;



public class PleaseApp {
	
	JFrame frame1;
	int selectedGuestCount = 0;
	 int remGuestCount=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PleaseApp window = new PleaseApp();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PleaseApp() {
		
		
			initialize();
	
	}
	private JTextField Search;
	
    private Settings settingsWindow;
	
    private boolean pdfCreated = false;
    
    public void setPdfCreated(boolean pdfCreated) {
        this.pdfCreated = pdfCreated;
    }
    
	Export_query_with_Search expSearch = new Export_query_with_Search();
	Add_Swap_Guests AsG = new Add_Swap_Guests();
	Database db = new Database();
	iTextPDF iTxt = new iTextPDF();
	importCSV iprtCSV = new importCSV();
	
	Settings set = new Settings(this);
	
	Kensoft animate = new Kensoft();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	String dateString = dateFormat.format(new Date(System.currentTimeMillis()));
	
	 // Модул за осъществяване на връзка с базата данни чрез API JDBC driver................................................................... 
	Statement statement = db.connectToDatabase();
	
	
	// Модул за часовник...................................................................................................................... 
	private void clock( JLabel date, JLabel Time) {
		
		Calendar cal=new GregorianCalendar();
		int day=cal.get(Calendar.DATE);
		int month=cal.get(Calendar.MONTH);
		int year=cal.get(Calendar.YEAR);
		
		int minutes=cal.get(Calendar.MINUTE);
		int hours=cal.get(Calendar.HOUR);
		Time.setText(" Дата " + day + "/" + month + "/" + year);
		date.setText("Час " + hours + ":" + minutes);
	
	}
	
	// Модул за специфичната ширина на колоните в таблиците ......................................................................................
	private void TableColumnWidth(JTable table) {
		TableColumn column = null;
			for (int i = 0; i < 5; i++) {
			    column = table.getColumnModel().getColumn(i);
			    if (i == 1) {
			        column.setPreferredWidth(120); //second column is bigger	 
			    } else if (i == 4) {
				        column.setPreferredWidth(90);  				    			     				     
			    } else {
			        column.setPreferredWidth(30);
			    }
			}
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 * @param b 
	 */
		
		
//============================================================= Тук свършват готовите методи ===================================================\\			
//========================================================= ПОЧВА ИНИЦИАЛИЗАЦИЯТА НА ПРОГРАМАТА  ================================================\\	
		
	
	
	
	
	private void initialize() {
		frame1 = new JFrame();
		frame1.setBounds(450, 200, 747, 427);
		frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		
		frame1.addWindowListener((WindowListener) new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	
		    	if (!pdfCreated) {
		        // Показва диалогов прозорец, питащ потребителя дали иска да запази файла преди да затвори програмата
		        int option = JOptionPane.showConfirmDialog(frame1, "Do you want to save the PDF file before closing?", "Save PDF", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		        if (option == JOptionPane.YES_OPTION) {
		        	
		        	
		            // В случай, че потребителят избере да запази файла
		        	if (settingsWindow != null && settingsWindow.isRadioButtonSelected()) {
	                    System.out.println("Using method 2");
	                    iTxt.makeiTextPDFfile(frame1);
	                } else {
	                    System.out.println("Using method 1");
	                    set.exportPDF(frame1);
	                }
		        	
		        	
		            System.exit(0);
		        } else if (option == JOptionPane.NO_OPTION) {
		            // User chose not to save the file, exit without saving
		            System.exit(0);
		        }
		        
		    	}else {
		    		System.exit(0);
		    	}
		    }
		});

		
		iprtCSV.tableExists(frame1);
		
		
		//Първа таблица с невлезнали гости 
		JTable table = new JTable();
		table.setForeground(Color.BLACK);
		table.setBackground(Color.LIGHT_GRAY);
	        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        table.setBorder(null);
	        table.setEnabled(true);
	        table.setRowSelectionAllowed(true);
	        table.setColumnSelectionAllowed(false);
	        table.setRowHeight(30);
	        table.setFont(new Font("Tahoma", Font.PLAIN, 18));
	   

	        
	      //Втора таблица с влезнали/селектирани гости
	    	JTable table2 = new JTable();
	        table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        table2.setBorder(null);
	        table2.setEnabled(true);
	        table2.setRowSelectionAllowed(true);
	        table2.setColumnSelectionAllowed(false);
	        table2.setRowHeight(30);
	        table2.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        
	
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(25, 135, 681, 194);
	        frame1.getContentPane().add(scrollPane);	
	        
	        JScrollPane scrollPane2 = new JScrollPane(table2); 
	        
	   	 
			 JPanel panel = new JPanel();
		        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 128, 0), 1, true), "\u0412\u043B\u0435\u0437\u043B\u0438 \u0445\u043E\u0440\u0430 ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 0)));
		        panel.setBounds(530, 55, 155, 58);
		        frame1.getContentPane().add(panel);
		        panel.setLayout(null);
	        
	        JPanel panel_1 = new JPanel();
	        panel_1.setLayout(null);
	        panel_1.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "\u041E\u0441\u0442\u0430\u0432\u0430\u0449\u0438 \u0445\u043E\u0440\u0430 ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
	        panel_1.setBounds(61, 55, 155, 58);
	        frame1.getContentPane().add(panel_1);
	        
	        JLabel remPeople = new JLabel("");
	        remPeople.setBounds(6, 14, 135, 38);
	        panel_1.add(remPeople);
	        remPeople.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
	        remPeople.setText(Integer.toString(AsG.showCommonNumberOfGuestsDB(0)));
	        
	        JLabel inPeople = new JLabel("");
	        inPeople.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
	        inPeople.setBounds(10, 10, 135, 38);
	        panel.add(inPeople);
	        
	       
	       
	     // Тук е модулът за броенето и показването на общия брой гости в таблицата от базата данни guests1 
	        // Модулът е в общото пространство, за да се избегне объркване с последстващо създадената таблица от въведента стая
	        
	        
	        inPeople.setText(Integer.toString(AsG.showInNumberOfGuestsDB(0)));

	        
	        
	        // ИНТЕРАКЦИЯ ЗА ИЗВЕЖДАНЕТО НА ГОСТИ ОТ ДАДЕНА СТАЯ, ВЪВЕДЕНА В КУТИЯТА
		Search = new JTextField();
		Search.setHorizontalAlignment(SwingConstants.CENTER);
		Search.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 26));
		Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				 DefaultTableModel model = new DefaultTableModel();
					model.addColumn("Room Number");
					model.addColumn("Guest Name");
					model.addColumn("Gender");
					model.addColumn("Age");
					model.addColumn("Check-in Date");
					model.addColumn("Check-out Date");
					table.setModel(model);
					  // Тук е функцията за ширината на колоните 
					TableColumnWidth(table);
					
						//Тук е функцията за търсенето на гости чрез въвеждането на стая 
					
					expSearch.addGuestsByroom(model,Search);
					
					
			
					table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					    @Override
					    public void valueChanged(ListSelectionEvent e) {
					        if (!e.getValueIsAdjusting()) {
					            // Разпознава, кога ред от таблицата е избран, чете инф. от нея и мести гостите в ИЗБРАНИ
					            AsG.shiftGuestsToSelected(inPeople, remPeople, table, e);
					        }
					    }
					});

						
		 		       
			}
		});
        Search.setBounds(292, 55, 142, 70);
        frame1.getContentPane().add(Search);
        Search.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Въведете номер на стая ");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
        lblNewLabel.setBounds(215, 10, 317, 44);
        frame1.getContentPane().add(lblNewLabel);
        
        
        JButton Rem = new JButton("Изчити");
        Rem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Search.setText("");      
        		DefaultTableModel model = (DefaultTableModel) table.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }       		
        	}
        });
        Rem.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        Rem.setBounds(531, 341, 120, 44);
        frame1.getContentPane().add(Rem);
        
       
        JButton btnImport = new JButton("Import CSV");
        btnImport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		// Тук се избира CSV file от място в системата, създават се 2 нови таблици, идентични на 
        		// основните образци и в таблицата guests_някаква_дата се вмъква този csv файл 
        		// таблицата selected_guests_някаква_дата остава празна и със всяко ново създаване и   
        		// вмъкване на 2 нови таблици всички queries трябва да си променят коя таблица използват       		
        		// Това ще стане чрез настоящата дата, която е в края на всяка таблица 
        		
        		
        		iprtCSV.importCSVfile(inPeople, remPeople, frame1);   		
        		
        	}
        });
        btnImport.setBounds(61, 24, 120, 29);
        frame1.getContentPane().add(btnImport);
        
        
        JButton btnExport = new JButton("Export PDF");
        btnExport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		
        		
        // Модул за създаване на pdf file съдържащ инф. на гостите от двете таблици чрез iText API
        		if (settingsWindow != null && settingsWindow.isRadioButtonSelected()) {
                    System.out.println("Using method 2");
                    iTxt.makeiTextPDFfile(frame1);
                } else {
                    System.out.println("Using method 1");
                    set.exportPDF(frame1);
                }
		 
     	
        	}           	
        });
        btnExport.setBounds(549, 24, 136, 29);
        frame1.getContentPane().add(btnExport);
        
        
        JButton Sett = new JButton("");
        Sett.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if (settingsWindow == null) {
                    settingsWindow = new Settings(PleaseApp.this);
                }
                settingsWindow.frame.setVisible(true);
        		
        	}
        });
        Sett.setBounds(651, 341, 55, 43);
        frame1.getContentPane().add(Sett);
        
       
        JButton showT2 = new JButton("Покажи таблицата с вече селектираните гости ");
        JButton showT1 = new JButton("Покажи таблицата за селектиране на гости");
        showT2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		animate.jPanelXLeft(61, 25, 3, 1, panel_1);
        		animate.jPanelXRight(530, 550, 3, 1, panel);
        		animate.jButtonXLeft(61, 20, 3, 1, btnImport);
        		animate.jButtonXRight(549, 575, 3, 1, btnExport);
        		
        		 frame1.setBounds(450, 200, 747, 570);              		 
          		frame1.getContentPane().remove(scrollPane);
          		lblNewLabel.setText("Въведете стая или име на човек");
          		 lblNewLabel.setBounds(155, 10, 521, 44);
          		 Search.setBounds(190, 55, 348, 70);
          		showT1.setBounds(28, 480, 504, 44);
          		 Rem.setBounds(531, 480, 120, 44);
          		 Sett.setBounds(651, 480, 55, 44);
          		frame1.getContentPane().remove(showT2);
                frame1.getContentPane().add(showT1);
                            
        		Search.setText("");
                table.setVisible(false);
                table2.setVisible(true);
                
                
    	        scrollPane2.setBounds(25, 135, 681, 334);
    	        frame1.getContentPane().add(scrollPane2);	
    	        
    	        
         			 				DefaultTableModel model2 = new DefaultTableModel();
            			 				model2.addColumn("Room Number");
            			 				model2.addColumn("Guest Name");
            			 				model2.addColumn("Gender");
            			 				model2.addColumn("Age");
            			 				model2.addColumn("Check-in Date");
            			 				model2.addColumn("Check-out Date");
            			 				table2.setModel(model2);
            			 				
            			 				// Тук е функцията за ширината на колоните 
            			 				TableColumnWidth(table2);
          			 				
            			 				AsG.displayPersonsInSelected_Guests(model2, table2, inPeople, remPeople);
            			 				
        		 				
   //============================================ Тук почва пускането на търсачката на хора =====================================================\\      		 				
        		 				
        		 				
        		 				
        		 				Search.addKeyListener(new KeyAdapter() {
        		 					@Override
        		 					public void keyReleased(KeyEvent e) {
        		 						
					
        		 						 DefaultTableModel model3 = new DefaultTableModel();
        		 							model3.addColumn("Room Number");
        		 							model3.addColumn("Guest Name");
        		 							model3.addColumn("Gender");
        		 							model3.addColumn("Age");
        		 							model3.addColumn("Check-in Date");
        		 							model3.addColumn("Check-out Date");
        		 							table2.setModel(model3);
        		 							  // Тук е функцията за ширината на колоните 
        		 							TableColumnWidth(table2);
        		 							
        		 							expSearch.addGuestsByroomT2(model3, Search);
        		 							
        		 								//Тук е функцията за търсенето на гости чрез въвеждането на име 
        		 							expSearch.displayNamesOfPeople(model3, Search);
        		 							
        		 							
        		 							
        		 							 table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        		 		 		        	    @Override
        		 		 		        	    public void valueChanged(ListSelectionEvent e) {
        		 		 		        	        if (!e.getValueIsAdjusting()) {
        		 		 		        	            // Разпознава, кога ред от таблицата е избран, чете инф. от нея и мести гостите в ИЗБРАНИ
        		 		 		        	        	AsG.shiftGuestsToSelected(inPeople, remPeople, table2, e);
        		 		 		        	        		 		 		        	        	
        		 		 		        	        	 int rowCount2 = model3.getRowCount();
      	        		 				 		       if (rowCount2==0) {
      	        		 				 		    	 AsG.displayPersonsInSelected_Guests(model2, table2, inPeople, remPeople);
      	        		 				 		       }
        		 		 		        	        	
        		 		 		        	        }
        		 		 		        	    }
        		 		 		        	});
  	 				 		      
      		 				 		               		 				 
        		 					}
        		 				});	
        
        			}
                });      
       
        showT2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        showT2.setBounds(28, 342, 504, 43);
        frame1.getContentPane().add(showT2);
        
        JLabel date = new JLabel("");
        date.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        date.setBounds(663, 0, 78, 29);
        frame1.getContentPane().add(date);
        
        JLabel Time = new JLabel("Час 1:32 Дата 15/1/2023");
        Time.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        Time.setBounds(0, 0, 205, 29);
        frame1.getContentPane().add(Time);
        
        // Модул за показване на часа и датата 
        clock(date, Time);
       
        
        
     
        showT1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		animate.jPanelXRight(25, 61, 3, 1, panel_1);
        		animate.jPanelXLeft(550, 530, 3, 1, panel);
        		animate.jButtonXRight(20, 61, 3, 1, btnImport);
        		animate.jButtonXLeft(575, 549, 3, 1, btnExport);
        		
        		frame1.setBounds(450, 200, 747, 426);
         		frame1.getContentPane().remove(scrollPane2);
         		frame1.getContentPane().add(scrollPane);
          		 scrollPane.setBounds(25, 135, 681, 194);
          		lblNewLabel.setText("Въведете номер на стая");     		 
          		 lblNewLabel.setBounds(215, 10, 317, 44);
          		showT1.setBounds(28, 342, 504, 44);
          		 Rem.setBounds(531, 342, 120, 44);
          		 Sett.setBounds(651, 342, 55, 44);
          		 Search.setBounds(292, 55, 142, 70);
          		 frame1.getContentPane().remove(showT1); 
                 frame1.getContentPane().add(showT2);
                 table2.setVisible(false);
                 table.setVisible(true);
        	}
        });
        showT1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        showT1.setBounds(47, 339, 504, 43);

			        
	}
}
