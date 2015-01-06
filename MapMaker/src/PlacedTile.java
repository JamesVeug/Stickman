import java.awt.Dimension;
import java.awt.Point;

import javax.swing.ImageIcon;


public class PlacedTile {
	public Point point;
	public Dimension size;
	public ImageIcon image;
	public String name;
	
	public PlacedTile(Point point, Dimension size, String name, ImageIcon image){
		this.name = name;
		this.point = point;
		this.size = size;
		this.image = image;
		//System.out.println(name + ": " + size);
	}

	public String getSave() {		
		String save = name + " " + point.x + " " + point.y + " " + size.width + " " + size.height;
		//System.out.println("Saving: " + save);
		//System.out.println(size);
		return save;
	}
}
