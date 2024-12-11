package test;

import modelos.Usuario;
import modelos.UsuarioDAO;
import transversal.Conexion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest {
	private UsuarioDAO usuarioDAO;
	private Usuario testUsuario;
	// private Usuario testUsuario2;

	@BeforeEach
	public void setUp() {
		System.out.println("ENTRANDO");
		usuarioDAO = new UsuarioDAO();
		testUsuario = new Usuario("NombrePrueba", "Ap1Prueba", "Ap2Prueba", "333", true, false);
		usuarioDAO.agregarUsuario(testUsuario);

		Usuario testUsuario2 = new Usuario();
		testUsuario2 = usuarioDAO.buscarUsuarioPorDni("333");
		System.out.println(testUsuario2.toString());

		testUsuario.setIdUsuario(testUsuario2.getIdUsuario());
	}

	@AfterEach
	public void tearDown() {
		List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
		usuarios.stream().filter(u -> "333".equals(u.getIdUsuario()))
				.forEach(u -> usuarioDAO.eliminarUsuario(u.getIdUsuario()));
	}

	@Test
	public void testObtenerUsuarios() {
		List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
		assertNotNull(usuarios);
		assertTrue(usuarios.size() > 0, "La lista de usuarios debería tener al menos un elemento");
	}

	@Test
	public void testActualizarUsuario() {
		testUsuario.setNombre("NombreActualizado");
		usuarioDAO.actualizarUsuario(testUsuario);
		List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
		assertTrue(usuarios.stream().anyMatch(u -> "NombreActualizado".equals(u.getNombre())));

	}

	/*
	 * @Test public void testEliminarUsuario() {
	 * System.out.println("ENTRANDO BORRADO");
	 * usuarioDAO.eliminarUsuario(testUsuario.getIdUsuario()); List<Usuario>
	 * usuarios= usuarioDAO.obtenerTodosUsuarios();
	 * usuarios.forEach(u->System.out.println("Usuario en lista "+u.getDni()));
	 * assertFalse(usuarios.stream().anyMatch(u->"333".equals(u.getDni())),
	 * "El usuario con DNI 333 debería haber sido eliminado"); }
	 */

	@Test
	public void testEliminarUsuario() {
		System.out.println("ENTRANDO BORRADO");
		usuarioDAO.eliminarUsuario(testUsuario.getIdUsuario());

		// Obtenemos todos los usuarios, incluyendo los eliminados
		List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
		usuarios.forEach(u -> System.out.println("Usuario en lista " + u.getDni()));

		// Verificamos que el usuario con DNI "333" tenga el campo borrado como TRUE
		assertTrue(usuarios.stream().anyMatch(u -> "333".equals(u.getDni()) && u.isBorrado()),
				"El usuario con DNI 333 debería estar marcado como eliminado (borrado = TRUE)");
	}

	@Test
	public int obtenerIdPorDni(String dni) throws SQLException {
		String sql = "SELECT idUsuario FROM usuarios WHERE dni = ?";
		try (Connection conn = new Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, dni);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("idUsuario");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}

/*
 * package test;
 * 
 * import modelos.Usuario; import modelos.UsuarioDAO; import
 * transversal.Conexion;
 * 
 * import org.junit.jupiter.api.AfterEach; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; import
 * java.sql.*; import java.util.List; import static
 * org.junit.jupiter.api.Assertions.*;
 * 
 * public class UsuarioDAOTest { private UsuarioDAO usuarioDAO; private Usuario
 * testUsuario; private Usuario testUsuario2;
 * 
 * @BeforeEach public void setUp() { usuarioDAO = new UsuarioDAO(); testUsuario
 * = new Usuario("NombrePrueba", "Ap1Prueba", "Ap2Prueba", "222", true, false);
 * usuarioDAO.agregarUsuario(testUsuario);
 * 
 * 
 * testUsuario2 = usuarioDAO.buscarUsuarioPorDni("222");
 * 
 * testUsuario.setIdUsuario(testUsuario2.getIdUsuario()); }
 * 
 * 
 * @AfterEach public void tearDown() { List<Usuario>
 * usuarios=usuarioDAO.obtenerTodosUsuarios(); usuarios.stream()
 * .filter(u->"222".equals(u.getIdUsuario()))
 * .forEach(u->usuarioDAO.eliminarUsuario(u.getIdUsuario())); }
 * 
 * 
 * @Test public void testObtenerUsuarios() { List<Usuario> usuarios =
 * usuarioDAO.obtenerTodosUsuarios(); assertNotNull(usuarios);
 * assertTrue(usuarios.size()>0,
 * "La lista de usuarios debería tener al menos un elemento"); }
 * 
 * @Test public void testActualizarUsuario() {
 * testUsuario.setNombre("NombreActualizado");
 * usuarioDAO.actualizarUsuario(testUsuario2); List<Usuario> usuarios=
 * usuarioDAO.obtenerTodosUsuarios();
 * assertTrue(usuarios.stream().anyMatch(u->"NombreActualizado".equals(u.
 * getNombre())));
 * 
 * }
 * 
 * @Test public void testEliminarUsuario() {
 * usuarioDAO.eliminarUsuario(testUsuario.getIdUsuario()); List<Usuario>
 * usuarios= usuarioDAO.obtenerTodosUsuarios();
 * usuarios.forEach(u->System.out.println("Usuario en lista "+u.getDni()));
 * assertFalse(usuarios.stream().anyMatch(u->"222".equals(u.getDni())),
 * "El usuario con DNI 222 debería haber sido eliminado"); }
 * 
 * @Test public int obtenerIdPorDni(String dni) throws SQLException { String sql
 * = "SELECT idUsuario FROM usuarios WHERE dni = ?"; try (Connection conn = new
 * Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
 * 
 * stmt.setString(1, dni); ResultSet rs = stmt.executeQuery();
 * 
 * if (rs.next()) { return rs.getInt("idUsuario"); } } catch (SQLException e) {
 * e.printStackTrace(); } return -1; }
 * 
 * }
 */