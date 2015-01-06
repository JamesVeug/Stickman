import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class SelectionPanel extends JPanel{

	private MappingPanel mappingPanel;
	private Dimension size = new Dimension(50,50);
	private HashMap<String,ImageIcon> imageNameToImage = new HashMap<String, ImageIcon>();
	
	
	public SelectionPanel(MappingPanel mappingPanel){
		
		this.mappingPanel = mappingPanel;
		setFocusable(true);
		
		int height = 0;
		
		File folder = new File("src/resources");
		File[] images = folder.listFiles();
		
		ButtonGroup group = new ButtonGroup();
		for(File file : images){
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.indexOf("."));
			
			SelectionTile tile = new SelectionTile(fileName, new ImageIcon(file.getAbsolutePath()), this);
			group.add(tile);
			add(tile);
			

			height += tile.imageIcon.getIconHeight();
			imageNameToImage.put(tile.fileName,  tile.imageIcon);
		}
		setPreferredSize(new Dimension(80,height+images.length*20));
	}
	
	public void notifyMappingPanel(SelectionTile selection){
		mappingPanel.changeSelectedTile(selection, size);
	}
	
	public ImageIcon getImageFromName(String name){
		ImageIcon image = imageNameToImage.get(name);
		if( image == null ){
			throw new RuntimeException("Unknown name: " + name);
		}
		
		return image;
	}
}
