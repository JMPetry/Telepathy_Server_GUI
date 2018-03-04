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

public class QRCodeGenerator {
	
	private static Logger LOG = Logger.getLogger(QRCodeGenerator.class.getName());
	
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

    private static Image generateQRCodeFXImage(String input, int width, int height) throws WriterException, IOException {
    	
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(input, BarcodeFormat.QR_CODE, width, height);

        BufferedImage os = MatrixToImageWriter.toBufferedImage(bitMatrix);
        
        return SwingFXUtils.toFXImage(os, null);
    }

	
}
