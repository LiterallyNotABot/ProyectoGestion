package transversal;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.csvreader.CsvWriter;
import com.csvreader.CsvReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelos.Usuario;
import vistas.VistaPrincipal;
import controladores.CsvController;
import controladores.PdfController;
import controladores.UsuarioController;
import controladores.XmlController;

public class Principal {

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				VistaPrincipal vista = new VistaPrincipal();
				vista.setVisible(true);
			}
		});
	}

}
