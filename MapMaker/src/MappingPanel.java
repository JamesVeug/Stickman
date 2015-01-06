import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MappingPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private List<PlacedTile> placedTiles = new ArrayList<PlacedTile>();
	
	private ImageIcon currentTileImage = new ImageIcon("src/resources/brick.png");
	private String currentTileName = "brick";
	private Point mousePosition = new Point(0,0);
	private Dimension size = new Dimension(50,50);
	private boolean mouseOnScreen = false;
	
	public MappingPanel(){
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setPreferredSize(new Dimension(10000,10000));
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// Draw placed tiles
		for(int i = 0; i < placedTiles.size(); i++){
			PlacedTile tile = placedTiles.get(i); 
			g.drawImage(tile.image.getImage(), tile.point.x, tile.point.y, tile.size.width, tile.size.height, null);
		}
		
		// Draw Mouse's Image
		// Only if the mouse is on the panel
		if( mouseOnScreen ){
			int x = mousePosition.x;
			int y = mousePosition.y;
			g.drawImage(currentTileImage.getImage(),x,y, size.width, size.height, null);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point tempPosition = arg0.getPoint();		
		tempPosition.x -= tempPosition.x % 50;
		tempPosition.y -= tempPosition.y % 50;
		
		if( !tempPosition.equals(mousePosition) ){
			mousePosition = tempPosition;
			repaint();
		}		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseOnScreen = true;
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseOnScreen = false;
		repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = mousePosition.x;
		int y = mousePosition.y;
		placedTiles.add(new PlacedTile(new Point(x,y), new Dimension(size.width, size.height), currentTileName, currentTileImage));
		repaint();		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void changeSelectedTile(SelectionTile selection, Dimension size) {
		this.size = size;
		this.currentTileName = selection.fileName;
		this.currentTileImage = selection.imageIcon;
	}

	public List<PlacedTile> getPlacedTiles() {
		return placedTiles;
		
	}
	
	public void setPlacedTiles(List<PlacedTile> loadedMap) {
		this.placedTiles = loadedMap;
		repaint();
	}

	public void setTileDimension(int w, int h) {
		this.size.setSize(w, h);
	}
}
