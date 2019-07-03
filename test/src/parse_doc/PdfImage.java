package parse_doc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PdfImage {
	public static void main(String args[]) throws IOException
	{
		System.out.println("11");
		//load a pdf from a byte buffer
        File file = new File("C:\\Users\\user\\Desktop\\test\\cert.pdf");
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);

        // draw the first page to an image
        PDFPage page = pdffile.getPage(0);
        
        //get the width and height for the doc at the default zoom 
        Rectangle rect = new Rectangle(0,0,
                (int)page.getBBox().getWidth(),
                (int)page.getBBox().getHeight());
        
        //generate the image
        
        Image image = page.getImage(
                rect.width, rect.height, //width & height
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true  // block until drawing is done
                );
        
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        try
        {
            ImageIO.write(bi, "jpg", new File("C:\\Users\\user\\Desktop\\test.jpg"));
            System.out.println("�셿猷�");
        }
        catch(IOException ioe)
        {
            System.out.println("write: " + ioe.getMessage());
        }                
	}
}
