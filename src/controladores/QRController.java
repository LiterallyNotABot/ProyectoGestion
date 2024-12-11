package controladores;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRController {

	public static void generarQR(String textoQR, String pathname) {
		try {
			writeQR(textoQR, pathname);
			System.out.println("Código QR generado con éxito");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeQR(String text, String pathname) throws WriterException, IOException {
		int width = 600;
		int height = 400;
		String imageFormat = "png";

		BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		FileOutputStream outputStream = new FileOutputStream(new File(pathname));
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, outputStream);
	}
}
