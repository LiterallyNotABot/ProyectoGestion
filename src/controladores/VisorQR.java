package controladores;

import javax.swing.*;
import java.awt.image.BufferedImage;//
import java.io.File;//
import javax.imageio.ImageIO;//
import java.io.IOException;//

public class VisorQR extends JFrame {

	public VisorQR(String pathname) throws IOException {
		// Cargar la imagen del archivo
		BufferedImage QRimage = ImageIO.read(new File(pathname));

		// Configurar el JFrame
		setTitle("Visor de Código QR");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Crear un panel para mostrar la imagen
		JLabel label = new JLabel(new ImageIcon(QRimage));
		add(label);

		// Hacer visible el JFrame
		setVisible(true);
	}
}
