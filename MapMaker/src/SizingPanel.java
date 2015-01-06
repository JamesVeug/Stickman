import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JPanel;


public class SizingPanel extends JPanel implements ActionListener{

	private MappingPanel mp;
	private TextField width;
	private TextField height;

	public SizingPanel(MappingPanel mp) {
		this.mp = mp;
		
		width = new TextField("50");
		width.addActionListener(this);
		add(width);
		
		height = new TextField("50");
		height.addActionListener(this);
		add(height);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String wString = width.getText().replaceAll("\\D", "");
		String hString = height.getText().replaceAll("\\D", "");
		int w = Integer.parseInt(wString);
		int h = Integer.parseInt(hString);
		
		mp.setTileDimension(w,h);
	}

}
