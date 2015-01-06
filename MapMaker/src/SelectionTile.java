import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;


public class SelectionTile extends JToggleButton implements ActionListener{

	public String fileName;
	public ImageIcon imageIcon;
	private SelectionPanel selectionPanel;

	public SelectionTile(String fileName, ImageIcon imageIcon, SelectionPanel selectionPanel){
		this.fileName = fileName;
		this.imageIcon = imageIcon;
		this.selectionPanel = selectionPanel;
		this.addActionListener(this);
		setIcon(imageIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		selectionPanel.notifyMappingPanel(this);
	}
	
	
}
