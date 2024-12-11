package controladores;

import modelos.Usuario;
import modelos.UsuarioDAO;
import java.util.List;

public class UsuarioController {

	private UsuarioDAO usuarioDAO;

	public UsuarioController() {
		this.usuarioDAO = new UsuarioDAO();
	}

	// Método para agregar un usuario
	public boolean agregarUsuario(String nombre, String ap1, String ap2, String dni, boolean activo) {
		Usuario usuario = new Usuario(nombre, ap1, ap2, dni, activo, false); // Borrado siempre es false al agregar
		return usuarioDAO.agregarUsuario(usuario);
	}

	// Método para obtener todos los usuarios activos
	public List<Usuario> obtenerUsuariosActivos() {
		return usuarioDAO.obtenerUsuariosActivos();
	}

	// Método para obtener todos los usuarios inactivos
	public List<Usuario> obtenerUsuariosInactivos() {
		return usuarioDAO.obtenerUsuariosInactivos();
	}

	// Método para obtener todos los usuarios borrados
	public List<Usuario> obtenerUsuariosBorrados() {
		return usuarioDAO.obtenerUsuariosBorrados();
	}

	// Método para buscar un usuario por su DNI
	public Usuario buscarUsuarioPorDni(String dni) {
		return usuarioDAO.buscarUsuarioPorDni(dni);
	}

	// Método para buscar usuarios por nombre
	public List<Usuario> buscarUsuariosPorNombre(String nombre) {
		return usuarioDAO.buscarUsuariosPorNombre(nombre);
	}

	// Método para eliminar un usuario (borrado lógico)
	public boolean eliminarUsuario(int idUsuario) {
		return usuarioDAO.eliminarUsuario(idUsuario);
	}

	// Método para actualizar los datos de un usuario
	public boolean actualizarUsuario(int idUsuario, String nombre, String ap1, String ap2, String dni, boolean activo,
			boolean borrado) {
		Usuario usuario = new Usuario(idUsuario, nombre, ap1, ap2, dni, activo, borrado);
		return usuarioDAO.actualizarUsuario(usuario);
	}

	public List<Usuario> obtenerTodosUsuarios() {
		return usuarioDAO.obtenerTodosUsuarios(); // Llamamos al método del DAO
	}

	public boolean verificarUsuarioExistentePorId(int idUsuario) {
		return usuarioDAO.existeUsuarioPorId(idUsuario); // Llamamos al método del DAO
	}
}