package controladores;

import modelos.Usuario;
import modelos.UsuarioDAO;
import transversal.Conexion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileReader;

public class CsvController {

	public static void crearCsv() throws IOException {
		String cadSQL = "";
		Conexion bd = new Conexion();
		ResultSet resultado = null;
		Statement sentencia = null;
		Usuario usuario = new Usuario();
		cadSQL = "SELECT * FROM usuarios;";
		Connection con = bd.conectar();

		if (con != null) {
			try {
				sentencia = con.createStatement();
				resultado = sentencia.executeQuery(cadSQL);

				String archivoCsv = "usuarios.csv";
				CsvWriter csvWriter = new CsvWriter(new FileWriter(archivoCsv), ',');

				csvWriter.write("ID");
				csvWriter.write("Nombre");
				csvWriter.write("Apellido1");
				csvWriter.write("Apellido2");
				csvWriter.write("DNI");
				csvWriter.write("Activo");
				csvWriter.write("Borrado");
				csvWriter.endRecord();

				while (resultado.next()) {
					usuario.setIdUsuario(Integer.parseInt(resultado.getString("idUsuario")));
					usuario.setNombre(resultado.getString("nombre"));
					usuario.setAp1(resultado.getString("ap1"));
					usuario.setAp2(resultado.getString("ap2"));
					usuario.setDni(resultado.getString("dni"));
					usuario.setActivo(resultado.getBoolean("activo")); // Leer el campo activo
					usuario.setBorrado(resultado.getBoolean("borrado")); // Leer el campo borrado

					// Escribimos los datos en el CSV
					csvWriter.write(String.valueOf(usuario.getIdUsuario()));
					csvWriter.write(usuario.getNombre());
					csvWriter.write(usuario.getAp1());
					csvWriter.write(usuario.getAp2());
					csvWriter.write(usuario.getDni());
					csvWriter.write(String.valueOf(usuario.isActivo())); // Escribir estado activo
					csvWriter.write(String.valueOf(usuario.isBorrado())); // Escribir estado borrado
					csvWriter.endRecord();
				}

				csvWriter.close();
				System.out.println("El archivo CSV ha sido creado exitosamente: " + archivoCsv);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (resultado != null)
						resultado.close();
					if (sentencia != null)
						sentencia.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Ha habido un problema al conectar con la base de datos");
		}
	}

	public static void cargarCsv(String archivoCsv) {
		Conexion bd = new Conexion();
		Connection con = bd.conectar();
		PreparedStatement sentenciaP = null;

		File archivo = new File(archivoCsv);

		if (!archivo.exists()) {
			System.out.println("El archivo CSV no existe: " + archivoCsv);
			return;
		}

		if (con != null) {
			try {

				CsvReader csvReader = new CsvReader(new FileReader(archivo));
				csvReader.readHeaders();
				String cadSQL = "INSERT INTO usuarios (nombre, ap1, ap2, dni, activo, borrado) VALUES (?,?,?,?,?,?);";
				sentenciaP = con.prepareStatement(cadSQL);
				UsuarioController uc = new UsuarioController();

				while (csvReader.readRecord()) {
					String idUsuario = csvReader.get("ID");

					if (!uc.verificarUsuarioExistentePorId(Integer.parseInt(idUsuario))) {

						String nombre = csvReader.get("Nombre");
						String apellido1 = csvReader.get("Apellido1");
						String apellido2 = csvReader.get("Apellido2");
						String dni = csvReader.get("DNI");
						String activo = csvReader.get("Activo");
						String borrado = csvReader.get("Borrado");

						sentenciaP.setString(1, nombre);
						sentenciaP.setString(2, apellido1);
						sentenciaP.setString(3, apellido2);
						sentenciaP.setString(4, dni);
						sentenciaP.setBoolean(5, Boolean.parseBoolean(activo));
						sentenciaP.setBoolean(6, Boolean.parseBoolean(borrado));

						sentenciaP.executeUpdate();
					}

				}

				csvReader.close();
				System.out.println("El archivo CSV ha sido cargado exitosamente en la base de datos.");

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (sentenciaP != null)
						sentenciaP.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Ha habido un problema al conectar con la base de datos");
		}
	}
}