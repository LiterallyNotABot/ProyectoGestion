package transversal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	/*
	 * ORIGINAL private final String URL="jdbc:mysql://localhost:3306/"; private
	 * final String BD="BDEJERCICIO1?serverTimezone=UTC"; private final String
	 * USER="root"; private final String PASSWORD="Adivinala1.";
	 */

	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String BD = "ejercicio1?serverTimezone=UTC";
	private final String USER = "root";
	private final String PASSWORD = "1234";
	public Connection conexion = null;

	public Connection conectar() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conexion = DriverManager.getConnection(URL + BD, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conexion != null) {
			System.out.println("Conexion OK");
		}

		return conexion;
	}
}
