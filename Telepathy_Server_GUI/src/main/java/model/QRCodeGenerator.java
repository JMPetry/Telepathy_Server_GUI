package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Generates a QR code with a specific width and height, saves a string in it and returns a JavaFX image to show into the GUI
 * @author Jean
 */
public class QRCodeGenerator {
	
	private static Logger LOG = Logger.getLogger(QRCodeGenerator.class.getName());
	
	/**
	 * Generates a QR code JavaFX image with a specific width, height and string content 
	 * @param input String of the content which should be saved in the QR code
	 * @param width int width of the Image which should be created
	 * @param height int height of the Image which should be created
	 * @return JavaFX Image which is a QRCode with the input in it
	 */
	public static Image generateQRCode(String input, int width, int height){
		
		Image im = null;
		
        try {
            im = generateQRCodeFXImage(input, width, height);
        } catch (WriterException e) {
        	LOG.log(Level.SEVERE, "WriterException while generating QR-CodeImage", e);
            e.printStackTrace();
        } catch (IOException e) {
        	LOG.log(Level.SEVERE, "IOException while generating QR-CodeImage", e);
            e.printStackTrace();
        }
        
        return im;
    }
	
	/**
	 * Generates a QR code Image with a specific width, height and string content 
	 * @param input String of the content which should be saved in the QR code
	 * @param width int width of the Image which should be created
	 * @param height int height of the Image which should be created
	 * @return Image of the QRCode
	 * @throws WriterException 
	 * @throws IOException
	 */
    private static Image generateQRCodeFXImage(String input, int width, int height) throws WriterException, IOException {
    	
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(input, BarcodeFormat.QR_CODE, width, height);

        BufferedImage os = MatrixToImageWriter.toBufferedImage(bitMatrix);
        
        return SwingFXUtils.toFXImage(os, null);
    }

	
}
