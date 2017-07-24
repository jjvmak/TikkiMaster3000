import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageHandler {

	public Image myImage;
	public BufferedImage bImg;
	

	public ImageIcon getImage(String s) {
		try {
			myImage = ImageIO.read(getClass().getResource(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    myImage = myImage.getScaledInstance(59, 79, java.awt.Image.SCALE_SMOOTH);
	    ImageIcon myImageIcon = new ImageIcon(myImage);
	    return myImageIcon;
	    //JLabel jl = new JLabel();
	    //JLabel label = new JLabel(myImageIcon);
	    //return label;
	}
	
	
	
}

