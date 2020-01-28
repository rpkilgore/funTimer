import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PixelCanvas extends JLabel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final int WIDTH = 400;
//    private static final int HEIGHT = 400;
    private static final Random random = new Random();
    //private static Image img1;
    private static BufferedImage img2;
    private static BufferedImage currentImage;
    private Vector pixels;
    
    public int getPixels(){
    	if(pixels!=null)
    	return pixels.size();
    	else
    		return 0;
    }
    
    public void resetImage(){
  		setIcon(null);
  		repaint();
    	pixels = new Vector();
        for(int x = 0; x < this.getWidth(); x++) {
        	for(int y = 0; y < this.getHeight(); y++) {
        		int[] intArr = {x,y};
        		pixels.add(intArr);
        	}
        }	
    }
    public void setSecondImage(Image img){
    	img2 = toBufferedImage(img);
    	// now lets create 2 arrays... 1 for x 1 for y
    	pixels = new Vector();
        for(int x = 0; x < this.getWidth(); x++) {
        	for(int y = 0; y < this.getHeight(); y++) {
        		int[] intArr = {x,y};
        		pixels.add(intArr);
        	}
        }
    }
    public void setImage(Image img){
    	//img1 = img;
    	img2 = toBufferedImage(img);
    	// create our temp image
    	currentImage = new BufferedImage(img2.getWidth(), img2.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    	// now lets create 2 arrays... 1 for x 1 for y
    	pixels = new Vector();
        for(int x = 0; x < this.getWidth(); x++) {
        	for(int y = 0; y < this.getHeight(); y++) {
        		int[] intArr = {x,y};
        		pixels.add(intArr);
        	}
        }
        System.out.println("pixels " + pixels.size());
        // test first one..
        int[] t = (int[]) pixels.elementAt(4);
        System.out.println("x:" + t[0] + " y" + t[1]);
    }
    
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    //moved to a different method to see if that works better
    public void updateImage(long pixelCount){
    	
    	if(pixels.size() <= 0){
  			System.out.println("- number?");
  			pixels = null;
  			return;
  		}
    	
        if(img2 !=null && currentImage!=null && pixels!=null){ 
      	//  while(pixels.size() != 0){
	  		
        	for(int x=0;x<pixelCount;x++){
      		 //int c = 0;
    	  		// get a random element
    	  		int r = random.nextInt(pixels.size());
    	  		// now lets pull out our x/y values
    	  		int[]values = (int[]) pixels.elementAt(r);
    	  		Color tmp = new Color(img2.getRGB(values[0], values[1]));
    	  		currentImage.setRGB(values[0], values[1], tmp.getRGB());
    	  		// now just make it our icon
    	  		setIcon(new ImageIcon(currentImage));
    	  		repaint();
    	  		// get that color from the existing image
    	  		//g.setColor(new Color(img2.getRGB(values[0], values[1])));
    	  		// now write out that newe image
    	  		//g.drawLine(values[0], values[1], values[0], values[1]);
    	  		// now remove that element
    	  		pixels.removeElementAt(r);

      	  }
        }
    }
    
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        
////        if (img1 != null) {
////            g.drawImage(img2, 0, 0, this);
////        }
//
//    		  
//    	  }
//    	  	System.out.println("one pixel drawn");
////    	  	System.out.println(pixels.size());
//   //     }        
//        //g.drawImage(img1, 0, 0, this);
////        if(img2 !=null){ 
////        	for(int x = 0; x < this.getWidth(); x++) {
////        		for(int y = 0; y < this.getHeight(); y++) {
////          			//g.setColor(randomColor());
////      				g.setColor(new Color(img2.getRGB(x, y)));
////          			g.drawLine(x, y, x, y);
////      			}
////	        }
////        }
//    }

    private Color randomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
    
}
