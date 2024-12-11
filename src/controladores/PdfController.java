
package controladores;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFTextStripper;

import transversal.Conexion;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PdfController {

	public static void exportarPdf() {
		Conexion bd = new Conexion();
		Connection conn = null;
		PDDocument document = new PDDocument();

		try {
			conn = bd.conectar();
			String query = "SELECT idUsuario, nombre, ap1, ap2, dni, activo, borrado FROM usuarios";

			PDPage page = new PDPage();
			document.addPage(page);
			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.setNonStrokingColor(Color.BLACK);
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(100, 700); // Posición inicial en la página

			// Cabecera del PDF
			contentStream.drawString("ID Usuario    Nombre    Apellido1    Apellido2    DNI    Activo    Borrado");
			contentStream.moveTextPositionByAmount(0, -20); // Moverse a la siguiente línea

			int count = 0;
			try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {
					count++;
					int idUsuario = rs.getInt("idUsuario");
					String nombre = rs.getString("nombre");
					String apellido1 = rs.getString("ap1");
					String apellido2 = rs.getString("ap2");
					String dni = rs.getString("dni");
					boolean activo = rs.getBoolean("activo");
					boolean borrado = rs.getBoolean("borrado");

					// Formatear y escribir cada registro en el PDF
					String linea = String.format("%d    %s    %s    %s    %s    %b    %b", idUsuario, nombre, apellido1,
							apellido2, dni, activo, borrado);
					contentStream.drawString(linea);
					contentStream.moveTextPositionByAmount(0, -20); // Moverse a la siguiente línea

					// Comprobar si se necesita agregar una nueva página
					if (count % 25 == 0) { // Por ejemplo, cada 25 registros
						contentStream.endText(); // Finalizar el texto actual
						contentStream.close(); // Cerrar el flujo actual

						// Añadir una nueva página
						page = new PDPage();
						document.addPage(page);
						contentStream = new PDPageContentStream(document, page);
						contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
						contentStream.setNonStrokingColor(Color.BLACK);
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(100, 700); // Reestablecer la posición en la nueva página

						// Escribir nuevamente el encabezado en la nueva página
						contentStream.drawString(
								"ID Usuario    Nombre    Apellido1    Apellido2    DNI    Activo    Borrado");
						contentStream.moveTextPositionByAmount(0, -20); // Moverse a la siguiente línea
					}
				}
			} catch (SQLException e) {
				System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error al crear o guardar el archivo PDF: " + e.getMessage());
			}

			contentStream.endText();
			contentStream.close(); // Cerrar el flujo de contenido después de terminar
			try {
				document.save("usuarios.pdf");
			} catch (COSVisitorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("PDF guardado como: usuarios.pdf");
			System.out.println("Número de usuarios encontrados: " + count);

		} catch (IOException e) {
			System.out.println("Error al crear o guardar el archivo PDF: " + e.getMessage());
		} finally {
			// Cerrar la conexión y el documento
			try {
				if (conn != null) {
					conn.close();
				}
				document.close();
			} catch (IOException e) {
				System.out.println("Error al cerrar el documento PDF: " + e.getMessage());
			} catch (SQLException e) {
				System.out.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	public static void importarUsuariosDesdePdf() {
		Conexion bd = new Conexion();
		Connection conn = null;
		PDDocument document = null;

		try {
			conn = bd.conectar();
			File archivoPdf = new File("usuarios.pdf");
			document = PDDocument.load(archivoPdf);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String textoPdf = pdfStripper.getText(document);
			String[] lineas = textoPdf.split("\\r?\\n");

			UsuarioController uc = new UsuarioController();

			for (String linea : lineas) {
				// Filtrar las líneas que contienen el encabezado o que están vacías
				if (linea.trim().isEmpty() || linea.contains("ID Usuario") || linea.contains("Nombre")
						|| linea.contains("Apellido1") || linea.contains("Apellido2") || linea.contains("DNI")) {
					continue; // Salta las líneas que son encabezados o vacías
				}

				String[] datosUsuario = linea.split("\\s+");

				// Asegurarse de que hay suficientes datos en la línea
				if (datosUsuario.length >= 7) { // Al menos 7 datos por línea
					int idUsuario = Integer.parseInt(datosUsuario[0]);
					String nombre = datosUsuario[1];
					String apellido1 = datosUsuario[2];
					String apellido2 = datosUsuario[3];
					String dni = datosUsuario[4];
					boolean activo = Boolean.parseBoolean(datosUsuario[5]);
					boolean borrado = Boolean.parseBoolean(datosUsuario[6]);

					// Comprobar si el usuario ya existe en la base de datos
					if (!uc.verificarUsuarioExistentePorId(idUsuario)) {
						// Si no existe, insertar un nuevo usuario
						String insertQuery = "INSERT INTO usuarios (idUsuario, nombre, ap1, ap2, dni, activo, borrado) VALUES (?, ?, ?, ?, ?, ?, ?)";
						try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
							stmt.setInt(1, idUsuario);
							stmt.setString(2, nombre);
							stmt.setString(3, apellido1);
							stmt.setString(4, apellido2);
							stmt.setString(5, dni);
							stmt.setBoolean(6, activo);
							stmt.setBoolean(7, borrado);
							stmt.executeUpdate();
						} catch (SQLException e) {
							System.out.println("Error al insertar el nuevo usuario: " + e.getMessage());
						}
					} else {
						// Si existe, podrías actualizar los datos (si se desea)
						String updateQuery = "UPDATE usuarios SET nombre = ?, ap1 = ?, ap2 = ?, dni = ?, activo = ?, borrado = ? WHERE idUsuario = ?";
						try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
							stmt.setString(1, nombre);
							stmt.setString(2, apellido1);
							stmt.setString(3, apellido2);
							stmt.setString(4, dni);
							stmt.setBoolean(5, activo);
							stmt.setBoolean(6, borrado);
							stmt.setInt(7, idUsuario);
							stmt.executeUpdate();
						} catch (SQLException e) {
							System.out.println("Error al actualizar el usuario: " + e.getMessage());
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Error al cargar el archivo PDF: " + e.getMessage());
		} finally {
			// Cerrar la conexión y el documento
			try {
				if (conn != null) {
					conn.close();
				}
				if (document != null) {
					document.close();
				}
			} catch (IOException | SQLException e) {
				System.out.println("Error al cerrar el documento PDF o la conexión: " + e.getMessage());
			}
		}
	}

}
