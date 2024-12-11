package controladores;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import modelos.Usuario;

public class JsonController {
	public void generarJson(List<Usuario> usuarios) {
		Gson gson = new Gson();
		String json = gson.toJson(usuarios);

		try (FileWriter filewriter = new FileWriter("usuarios.json")) {
			filewriter.write(json);
			// JOptionPane.showMessageDialog(this, "Archivo JSON generado correctamente",
			// "Éxito", JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(this, "Error al escribit el archivo JSON",
			// "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
