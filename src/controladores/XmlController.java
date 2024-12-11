package controladores;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import com.csvreader.CsvReader;

import transversal.Conexion;

public class XmlController {

	public static void crearXML() {
		String origen = "usuarios.csv";
		String destino = "usuarios.xml";

		try {
			boolean existe = new File(origen).exists();
			if (!existe) {
				System.out.println("Fin del proceso - El archivo no existe");
				return;
			} else {
				System.out.println("Añadiendo usuarios al archivo XML");
			}

			// lectura
			CsvReader csvr = new CsvReader(origen);
			csvr.readHeaders();

			FileWriter fp = null;
			PrintWriter pw = null;
			fp = new FileWriter(destino);
			pw = new PrintWriter(fp);

			pw.println("<?xml version=\"1.0\"?>");
			pw.println("<usuarios>");

			while (csvr.readRecord()) {
				String id = csvr.get("ID");
				String nombre = csvr.get("Nombre");
				String apellido1 = csvr.get("Apellido1");
				String apellido2 = csvr.get("Apellido2");
				String dni = csvr.get("DNI");
				String activo = csvr.get("Activo");
				String borrado = csvr.get("Borrado");
				System.out.println(" ID--" + id + " Nombre--" + nombre + " Apellido1--" + apellido1 + " Apellido2--"
						+ apellido2 + " DNI--" + dni + " Activo--" + activo + " Borrado--" + borrado);

				pw.println("<usuario>");
				pw.println("<id>" + id + "</id>");
				pw.println("<nombre>" + nombre + "</nombre>");
				pw.println("<apellido1>" + apellido1 + "</apellido1>");
				pw.println("<apellido2>" + apellido2 + "</apellido2>");
				pw.println("<dni>" + dni + "</dni>");
				pw.println("<activo>" + activo + "</activo>");
				pw.println("<borrado>" + borrado + "</borrado>");
				pw.println("</usuario>");

				System.out.println("\t" + id + "\t" + nombre + "\t" + apellido1 + "\t" + apellido2 + "\t" + dni + "\t"
						+ activo + "\t" + borrado);

			}
			pw.println("</usuarios>");
			csvr.close();
			pw.close();
			fp.close();

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} /*
			 * catch (JDOMException jdomex) { System.out.println(jdomex.getMessage()); }
			 */
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void cargarXML() {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("usuarios.xml");

		Conexion bd = new Conexion();
		Connection con = bd.conectar();
		PreparedStatement sentenciaP = null;

		try {
			// Carga el archivo XML y lo convierte en un documento JDOM
			Document document = builder.build(xmlFile);

			// Obtener el elemento raíz del documento (en este caso <usuarios>)
			Element rootNode = document.getRootElement();

			// Obtener la lista de nodos <usuario>
			List<Element> listaUsuarios = rootNode.getChildren("usuario");

			// Iterar sobre cada elemento <usuario> en el XML
			for (Element usuario : listaUsuarios) {
				// Extraer los valores de las etiquetas
				String id = usuario.getChildText("id");
				String nombre = usuario.getChildText("nombre");
				String apellido1 = usuario.getChildText("apellido1");
				String apellido2 = usuario.getChildText("apellido2");
				String dni = usuario.getChildText("dni");
				String activo = usuario.getChildText("activo");
				String borrado = usuario.getChildText("borrado");

				UsuarioController uc = new UsuarioController();

				if (con != null) {

					String cadSQL = "INSERT INTO usuarios (nombre, ap1, ap2, dni, activo, borrado) VALUES (?,?,?,?,?,?);";
					try {
						sentenciaP = con.prepareStatement(cadSQL);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					try {
						if (!uc.verificarUsuarioExistentePorId(Integer.parseInt(id))) {

							sentenciaP.setString(1, nombre);
							sentenciaP.setString(2, apellido1);
							sentenciaP.setString(3, apellido2);
							sentenciaP.setString(4, dni);
							sentenciaP.setBoolean(5, Boolean.parseBoolean(activo));
							sentenciaP.setBoolean(6, Boolean.parseBoolean(borrado));

							sentenciaP.executeUpdate();
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				sentenciaP.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

	}

}
