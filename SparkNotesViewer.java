import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SparkNotesViewer extends JFrame{

	private ArrayList<String> titles = new ArrayList<String>();
	private Styler robot = null;
	private JList sectionList = null;
	private JPanel mainContainer;
	private JPanel exportPanel; 
	private String search = null;
	private JComboBox dropdown;
	private JEditorPane previewBox;
	
	/**
	 * Constructor 
	 * 
	 */
	public SparkNotesViewer()
	{
		robot = Styler.getInstance();
		setPreferredSize(new Dimension(1000, 1000));
		setTitle("SparkNotes Condenser-O-Matic");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));
		mainContainer.add(initializeList());
		mainContainer.add(initializeExportPanel());
		add(mainContainer);
		setVisible(true);
		pack();
	}

	/**
	 * initializes a jpanel with a jlist of titles and a label
	 * @return
	 */
	public JPanel initializeList()
	{
		JPanel listPanel = new JPanel();  // whole panel that contains title list
		listPanel.setLayout(new BorderLayout());

		robot.scrapeBooks();
		titles = robot.getTitles();
		final JList titleList = new JList(titles.toArray());
		titleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // only one item can be selected at a time
		titleList.setLayoutOrientation(JList.VERTICAL); 
		titleList.addListSelectionListener(new ListSelectionListener() { 

			@Override
			public void valueChanged(ListSelectionEvent arg0) {  
				final String search = (String)titleList.getSelectedValue();  // gets value that user selected 
				setSearch(search);
				robot.parseBookContents(search);  // preloading book contents 
				dropdown.setModel(new DefaultComboBoxModel(robot.getSectTitles(search).toArray())); // sets dropdown model 
				dropdown.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String sect = (String)dropdown.getSelectedItem();  // 
						previewBox.setContentType("text/html");  // allows html formatting to show up 
						previewBox.setText(robot.getSectDisplay(search, sect));
						previewBox.revalidate();
					}
				});
				exportPanel.revalidate();
				exportPanel.repaint();
			}
		});
		JScrollPane titleScroll = new JScrollPane(titleList);
		titleScroll.setPreferredSize(new Dimension(300,500));
		JLabel listLabel = new JLabel("Select a title:"); // label
		listPanel.add(listLabel, BorderLayout.NORTH);
		listPanel.add(titleScroll, BorderLayout.CENTER);
		return listPanel;
	}

	/**
	 * returns a jpanel with section list and preview
	 * @return
	 */
	public JPanel initializeExportPanel()
	{
	
		exportPanel = new JPanel(); // contains the section list and preview
		exportPanel.setLayout(new BorderLayout());
		JPanel sectionPanel = new JPanel(); // holds section list and label
		if (search == null)
		{
			String[] blanks = {"You must first select a book."};  // Default view before user selects a book 
			dropdown = new JComboBox();
			dropdown.setModel(new DefaultComboBoxModel(blanks));  
		}
		exportPanel.add(dropdown, BorderLayout.NORTH);
		
		previewBox = new JEditorPane();
		previewBox.setEditable(false);
		previewBox.setText("Preview of selected section:\nPlease select a book first.");  // Default view 
		
		JButton exportButton = new JButton("Export your book");
		exportButton.setPreferredSize(new Dimension(100, 100));  // bigger button 
		exportButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event)
			{
				try {
					String filename = search + "Notes.txt"; // format of filename 
					FileWriter notes = new FileWriter(filename);
					String allNotes = "";
					ArrayList<String> sectTitles = new ArrayList<String>();
					sectTitles = robot.getSectTitles(search);
					for (int i = 0; i < sectTitles.size(); i++)
					{
						allNotes = allNotes + robot.getSectExport(search, sectTitles.get(i)) + "\n\n\n";  // String that has all of section text
					}
					notes.write(allNotes);
					System.out.println(allNotes);
					JOptionPane.showMessageDialog(mainContainer, "File was written to: " + filename);
				} catch (IOException e) {  // catches exception 
					System.out.println("Error: Could not be written.");
					e.printStackTrace();
				}
			}
		});
		JScrollPane previewScroll = new JScrollPane(previewBox);
		exportPanel.add(previewScroll, BorderLayout.CENTER);
		exportPanel.add(exportButton, BorderLayout.SOUTH);
		return exportPanel;
	}
	
	/**
	 * work-around to set a new search within listener
	 * @param newSearch
	 */
	public void setSearch(String newSearch)
	{
		search = newSearch;
	}

}
