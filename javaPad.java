
// Import libraries
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class javaPad extends WindowAdapter implements ActionListener
{
    // declaring static variables
    static int fsize = 20;
    static String fontfamily = "Calibri";
    static String fileName = "";
    static Font f1 = new Font(fontfamily,Font.PLAIN, fsize);
    static JTextArea textArea;
    static boolean isSaved = true;
    static JButton button2;
    static File thisFile;
    static JFrame frame;
    static JMenuItem cut,copy,paste,selectAll,newfile,open,save,saveAs,credits, popupCut, popupCopy,popupPaste,popupSelect; 
    static JCheckBoxMenuItem wordWrap, statusBar;
    static JLabel test,text2;
    static JDialog creditsDialog;

    @SuppressWarnings({ "unchecked", "rawtypes"})

    
    public static void createAndShowGUI() 
    { 
        // set up main 
        javaPad main = new javaPad();
        String fontSizes[] = {"8", "10", "11", "14", "16", "20","26","30","36","42","50","64","72"};
        String fonts[] = {"Calibri", "Arial", "Consolas", "Times New Roman","Serif", "Futura","Verdana","Georgia"};
        JMenu file,edit,help; 
        JMenuBar toolbar = new JMenuBar();  
       
        frame = new JFrame("JavaPad");  
        // file menu
        newfile=new JMenuItem("New");   
        open=new JMenuItem("Open");   
        save=new JMenuItem("Save");   
        saveAs=new JMenuItem("Save As");    
        
        file=new JMenu("File");   
        file.setMaximumSize(new Dimension(45, 50));
        edit=new JMenu("Edit");   
        edit.setMaximumSize(new Dimension(45, 50)); 
        help=new JMenu("View");   
        help.setMaximumSize(new Dimension(60,50));  
        
        //add to file menu
        file.add(newfile);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        // add button checker
        newfile.addActionListener(main);
        open.addActionListener(main);
        save.addActionListener(main);
        saveAs.addActionListener(main);
        
        // edit menu
        cut=new JMenuItem("Cut");    
        copy=new JMenuItem("Copy");    
        paste=new JMenuItem("Paste");   
        selectAll=new JMenuItem("Select All");   
        popupCut=new JMenuItem("Cut");    
        popupCopy=new JMenuItem("Copy");    
        popupPaste=new JMenuItem("Paste");   
        popupSelect=new JMenuItem("Select All");  
        
        // add to edit menu
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        
        // add buttton checker
        cut.addActionListener(main);    
        copy.addActionListener(main);    
        paste.addActionListener(main);    
        selectAll.addActionListener(main); 
        
        // help menu
        credits = new JMenuItem("Credits");
        wordWrap = new JCheckBoxMenuItem("Word Wrap");
        statusBar = new JCheckBoxMenuItem("Status Bar");
        
        // credits box
        creditsDialog = new JDialog(frame, "Credits");
        JLabel creditJLabel = new JLabel();
        creditJLabel.setText("    NotePad in Java: Made by OliwierJ 2024");
        creditsDialog.setSize(280,130);
        creditsDialog.add(creditJLabel);
        creditsDialog.setResizable(false);
        
        // add to help menu
        help.add(credits);
        help.add(wordWrap);
        help.add(statusBar);
        statusBar.setState(true);  // checked box at the start
        
        credits.addActionListener(main); 
        wordWrap.addActionListener(main);
        statusBar.addActionListener(main);
        
        // font size and family selector
        JComboBox comboBox = new JComboBox(fontSizes);
        JComboBox comboBox2 = new JComboBox(fonts);
        
        JLabel label = new JLabel("Font: ");
        label.setSize(new Dimension(50, 30));
        
        comboBox.setMaximumSize(new Dimension(80, 30));
        comboBox.setSelectedIndex(5);
        JLabel empty = new JLabel("      ");
        empty.setSize(new Dimension(100, 30));
        JLabel empty2 = new JLabel("   ");
        empty2.setSize(new Dimension(100, 30));
        text2 = new JLabel("Ln: 0 Col: 0");
        comboBox2.setMaximumSize(new Dimension(100, 30));
        comboBox2.setSelectedIndex(0);
        
        // add everything to toolbar
        toolbar.add(empty2);
        toolbar.add(file);
        toolbar.add(edit);
        toolbar.add(help);
        toolbar.add(label);
        toolbar.add(comboBox);
        toolbar.add(empty); 
        toolbar.add(comboBox2);
        
        
        // Display the window.  
        frame.setSize(600, 500);
        frame.setMinimumSize(new Dimension(450,350));  
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
        
        // left panel  
        JPanel p = new JPanel();
        p.setSize(new Dimension(5,1000));
        
        // right click pop up
        JPopupMenu popupmenu = new JPopupMenu("Edit");  
        popupmenu.add(popupCut); 
        popupmenu.add(popupCopy); 
        popupmenu.add(popupPaste); 
        popupmenu.addSeparator(); 
        popupmenu.add(popupSelect);        
        popupCut.addActionListener(main);    
        popupCopy.addActionListener(main);    
        popupPaste.addActionListener(main);    
        popupSelect.addActionListener(main);   
        
        // set up place to write text
        textArea = new JTextArea();
        textArea.setAlignmentX(100);
        textArea.setFont(f1);  
        
        // allow the text area to scroll to fit extra characters off screen
        JScrollPane scrollableTextArea = new JScrollPane(textArea);  
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
        
        // check which fonts are selected
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                fsize = Integer.parseInt(""+comboBox.getItemAt(comboBox.getSelectedIndex()));
                textArea.setFont(new Font(fontfamily, Font.PLAIN, fsize));
            }
            
        });
        comboBox2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                fontfamily = comboBox2.getItemAt(comboBox2.getSelectedIndex()).toString();
                textArea.setFont(new Font(fontfamily, Font.PLAIN, fsize));
            }
            
        });
        // check if word wrap is enabled
        wordWrap.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {                 
                if(wordWrap.isSelected()) {
                    textArea.setLineWrap(true);
                }
                else {
                    textArea.setLineWrap(false);
                }
            }    
        });    
        // check if the right mouse button was pressed
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON3) {
                    popupmenu.show(frame , MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y); 
                } 
            } 
        });
        
        // caret listener to see which line and column the caret is on
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int linenum = 1;
                int columnnum = 1;
                int caretpos = textArea.getCaretPosition();
                try {
                    linenum = textArea.getLineOfOffset(caretpos);
                    
                    // We subtract the offset of where our line starts from the overall caret position.
                    // So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
                    // we know that we must be on column 6 of line 5.
                    columnnum = caretpos - textArea.getLineStartOffset(linenum);
                    
                    // We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
                    linenum += 1;
                } catch (Exception ex) {
                    
                }
                String loc = "Ln: " + linenum + " Col: " + columnnum;
                text2.setText(loc); 
                
            }
        });
        // listens for any updates to the text area
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            // if any text is removed, set the file to "Not saved" and add a marker in the title to show the change
            public void removeUpdate(DocumentEvent e) {
                isSaved = false;
                if (fileName.equals("")) {
                    frame.setTitle("JavaPad ●");
                } 
                else {
                    frame.setTitle("JavaPad - " + fileName + " ●");  // will display the files name
                }
                String str = "| Characters: " + textArea.getText().length();
                test.setText(str);
                
            }
            
            @Override
            // same thing but checks for any updates added instead
            public void insertUpdate(DocumentEvent e) {
                isSaved = false;
                if (fileName.equals("")) {
                    frame.setTitle("JavaPad ●");
                } 
                else {
                    frame.setTitle("JavaPad - " + fileName + " ●");
                } 
                String str = "| Characters: " + textArea.getText().length();
                test.setText(str);
                
            }
            
            @Override  // added unused method
            public void changedUpdate(DocumentEvent arg0) {
                
            }
        });

        // status bar down at the bottom
        JPanel thestatusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        test = new JLabel("| Characters: 0");
        
        thestatusBar.add(text2);
        thestatusBar.add(test);
        
        // check if status bar is on or not
        statusBar.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (statusBar.isSelected()) {
                    thestatusBar.setVisible(true);
                }
                else {
                    thestatusBar.setVisible(false);
                }
            }
        });

        // finish displaying the frame
        frame.getContentPane().add(p, BorderLayout.WEST);
        frame.addWindowListener(main);
        frame.setJMenuBar(toolbar);
        frame.getContentPane().add(scrollableTextArea);  
        frame.getContentPane().add(thestatusBar, BorderLayout.SOUTH);
    }
    // On window close, check if anything needs saving
    public void windowClosing(WindowEvent e) 
    {  
        savePopUp();
    }  
    // main function
    public static void main(String[] args) {  
        // continue running the app
        javax.swing.SwingUtilities.invokeLater(new Runnable() {  
            
            public void run() {  
                createAndShowGUI();  
            }  
        });  
    }  
    
    @Override // looks through all button presses
    public void actionPerformed(ActionEvent e)  
    {  
        
        String com = e.getActionCommand();  
        JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());  // file selector
        
        // open file
        if (com.equals("Open")) {  
            openFile(jf);
        }  
        // save as file
        if (com.equals("Save As")) 
        {
           saveAsFile(jf);
        }
        // only save normally if the text area isnt clear
        else if (com.equals("Save") && !textArea.getText().equals("")) {  
            saveFile(jf);
        }
        // clear and open new file if the previous was saved
        if (com.equals("New"))
        {
     
            if(savePopUp() != 2) {
                textArea.setText("");
                thisFile = null;
                fileName = "";
            }
            isSaved = true;
            frame.setTitle("JavaPad");  // reset name
        }

        // check for cut copy paste and select from both the tool bar AND the pop up bar
        if(e.getSource()==cut || e.getSource()==popupCut )    
        textArea.cut();    
        if(e.getSource()==paste || e.getSource()==popupPaste )    
        textArea.paste();    
        if(e.getSource()==copy || e.getSource()==popupCopy )    
        textArea.copy();    
        if(e.getSource()==selectAll || e.getSource()==popupSelect)    
        textArea.selectAll(); 
        if(e.getSource()==credits) 
        creditsDialog.setVisible(true);
        
    }
    // opens a scanner object to read through a selected file
    // returns a string of the file
    public static String myReadFile(String file)
    {
        String myStr = "";
        try
        {
            File myFile = new File(file);
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine()) 
                {
                    String responses = myScanner.nextLine();
      
                    myStr += responses + "\n";
                }
            myScanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }       
        return myStr;
    }

    // opens a file, saves the text area as a string and writes it to the open file
    public static void writeToFile(String myStr, String myStr2) throws IOException
    {
        FileWriter writeToOpenedFile = new FileWriter(myStr, false);
        BufferedWriter writingToFile = new BufferedWriter(writeToOpenedFile);

        writingToFile.write(myStr2);
        writingToFile.newLine();
        writingToFile.close();
    }
    // open a file from file select menu
    public static void openFile(JFileChooser jf)
    {
        int r = jf.showOpenDialog(null);  
        if (r == JFileChooser.APPROVE_OPTION)  
        {  
            thisFile = jf.getSelectedFile();
            textArea.setText(myReadFile(jf.getSelectedFile().getAbsolutePath()));  
            isSaved = true;
        }         
        fileName = thisFile.getName();
        frame.setTitle("JavaPad - " + fileName);  // add file name to frame title
    }

    // save file
    public static void saveFile(JFileChooser jf) 
    {
        // if a file is currently opened, save normally 
        if (thisFile != null) {
            try {
                writeToFile(thisFile.getAbsolutePath(), textArea.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            isSaved = true;
            fileName = thisFile.getName();
            frame.setTitle("JavaPad - " + fileName);
        }
        // HOWEVER if NO file is currently opened, display save AS pop file menu
        else {
            saveAsFile(jf);
        }
    }
    // save AS file
    public static void saveAsFile(JFileChooser jf) 
    {
        int r = jf.showSaveDialog(null);  
        if (r == JFileChooser.APPROVE_OPTION)  
        {  
            isSaved = true;
            thisFile = jf.getSelectedFile();
            try {
                writeToFile(thisFile.getAbsolutePath(), textArea.getText());
            } catch (IOException e1) { 
                e1.printStackTrace();
            }  
            fileName = thisFile.getName();
            frame.setTitle("JavaPad - " + fileName);
        }  
    }

    // save pop up if file is not currently saved
    public static int savePopUp()
    {
        JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());  

        // if not saved
        if (!isSaved) {
            // show pop up
            int a = JOptionPane.showConfirmDialog(frame,"Would you like to save?");  
            if(a==JOptionPane.YES_OPTION) {  
                saveFile(jf);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                return 0; // save normally and exit
            }
            if (a == JOptionPane.NO_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                return 1; // dont save and exit
            }
            if (a == JOptionPane.CANCEL_OPTION) {
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                return 2; // dont save and cancel exit
            }
        }  
        else {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return -1; // file is already saved
        }
        return -1;  // default case to prevent accidental closes if trying to save
    }
}
