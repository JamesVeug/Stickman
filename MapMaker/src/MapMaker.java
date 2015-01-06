import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MapMaker {
	MappingPanel mappingPanel;
	SelectionPanel selectionPanel;
	private SizingPanel sizingPanel;
	private JTextArea noticeBar;

	public MapMaker(){
		final JFrame frame = new JFrame();
		frame.setSize(800,600);
		
		// Mapping Panel
		mappingPanel = new MappingPanel();
		JScrollPane scrollMappingPanel = new JScrollPane(mappingPanel);
		scrollMappingPanel.setPreferredSize(new Dimension(800,400));
		frame.add(scrollMappingPanel, BorderLayout.CENTER);
		
		// EAST
		JPanel EASTPANEL = new JPanel();
		EASTPANEL.setPreferredSize(new Dimension(100,100));
		
		// Sizing Panel
		sizingPanel = new SizingPanel(mappingPanel);
		EASTPANEL.add(sizingPanel, BorderLayout.NORTH);
		
		// Selection Panel
		selectionPanel = new SelectionPanel(mappingPanel);
		JScrollPane scroll = new JScrollPane(selectionPanel);
		scroll.setPreferredSize(new Dimension(100,475));
		EASTPANEL.add(scroll, BorderLayout.SOUTH);
		
		frame.add(EASTPANEL, BorderLayout.EAST);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenuItem newMap = new JMenuItem("New Map");
		newMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newMap();
			}
			
		});
		menu.add(newMap);
		
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				load();
			}
			
		});
		menu.add(load);
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
			
		});
		menu.add(save);
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
			
		});
		menu.add(quit);
		
		frame.setJMenuBar(menuBar);
		
		// NOTICE BAR
		noticeBar = new JTextArea("Welcome!");
		noticeBar.setEditable(false);
		frame.add(noticeBar, BorderLayout.SOUTH);
		
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	protected void newMap() {
		// HACK HACK HACK
		mappingPanel.setPlacedTiles(new ArrayList<PlacedTile>());
		displayMessage("New map loaded");
	}

	public void displayMessage(String message){
		noticeBar.setText(message);
	}
	
	
	protected void load() {
		
		JFileChooser fileChooser = new JFileChooser("./src/saves/");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue != JFileChooser.APPROVE_OPTION) {
        	return;
        }
        
        
        File selectedFile = fileChooser.getSelectedFile();
        try {
        	Scanner scan = new Scanner(selectedFile);
			List<PlacedTile> placedTiles = new ArrayList<PlacedTile>();
			while(scan.hasNext()){
				
				//brick 100 100 50 50
				String name = scan.next();
				Point point = new Point(scan.nextInt(), scan.nextInt());
				Dimension size = new Dimension(scan.nextInt(), scan.nextInt());
				ImageIcon image = selectionPanel.getImageFromName(name);
				
				System.out.println(point + " " +  size + " " + name + " " + image);
				placedTiles.add(new PlacedTile(point, size, name, image));
				
			}
			
			
			
			
			mappingPanel.setPlacedTiles(placedTiles);

			
			
			displayMessage("Successfully loaded " + placedTiles.size() + " elements from " + selectedFile.getName());
			scan.close();
			
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			displayMessage("Failed to load " + selectedFile.getName());
		}
		
	}



	protected void save() {
		
		JFileChooser fileChooser = new JFileChooser("./src/saves/");
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue != JFileChooser.APPROVE_OPTION) {
        	return;
        }
        
        
        File selectedFile = fileChooser.getSelectedFile();
        try {
			PrintStream print = new PrintStream(selectedFile);

			List<PlacedTile> placedTiles = mappingPanel.getPlacedTiles();
			for(PlacedTile tile : placedTiles){
				print.println(tile.getSave());
			}
			
			
			displayMessage("Successfully saved " + placedTiles.size() + " elements to " + selectedFile.getName());
			print.close();
			
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			displayMessage("Failed to save " + selectedFile.getName());
		}
	}



	public static void main(String[] args){
		new MapMaker();
	}
}
