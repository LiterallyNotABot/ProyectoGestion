package modelos;

import transversal.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

	// Método para agregar un usuario a la base de datos
	public boolean agregarUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuarios (nombre, ap1, ap2, dni, activo, borrado) VALUES (?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, usuario.getNombre());
			stmt.setString(2, usuario.getAp1());
			stmt.setString(3, usuario.getAp2());
			stmt.setString(4, usuario.getDni());
			stmt.setBoolean(5, usuario.isActivo());
			stmt.setBoolean(6, usuario.isBorrado());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println("Error al agregar usuario: " + e.getMessage());
			return false;
		} finally {
			// Cerrar recursos manualmente
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
	}

	// Obtener usuarios activos (no borrados)
	public List<Usuario> obtenerUsuariosActivos() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuarios WHERE activo = TRUE AND borrado = FALSE";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
						rs.getString("dni"), rs.getBoolean("activo"), rs.getBoolean("borrado"));
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener usuarios activos: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return usuarios;
	}

	// Obtener usuarios inactivos (no borrados)
	public List<Usuario> obtenerUsuariosInactivos() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuarios WHERE activo = FALSE AND borrado = FALSE";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
						rs.getString("dni"), rs.getBoolean("activo"), rs.getBoolean("borrado"));
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener usuarios inactivos: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return usuarios;
	}

	// Obtener usuarios borrados
	public List<Usuario> obtenerUsuariosBorrados() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuarios WHERE borrado = TRUE";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
						rs.getString("dni"), rs.getBoolean("activo"), rs.getBoolean("borrado"));
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener usuarios borrados: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return usuarios;
	}

	// Método para buscar usuarios por nombre
	public List<Usuario> buscarUsuariosPorNombre(String nombre) {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuarios WHERE nombre LIKE ? AND borrado = FALSE";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + nombre + "%");
			rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
						rs.getString("dni"), rs.getBoolean("activo"), rs.getBoolean("borrado"));
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error al buscar usuarios por nombre: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return usuarios;
	}

	// Método para buscar un usuario por su DNI
	public Usuario buscarUsuarioPorDni(String dni) {
		String sql = "SELECT * FROM usuarios WHERE dni = ? AND borrado = FALSE";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dni);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
						rs.getString("dni"), rs.getBoolean("activo"), rs.getBoolean("borrado"));
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				return usuario;
			}
		} catch (SQLException e) {
			System.out.println("Error al buscar usuario por DNI: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return null;
	}

	// Método para eliminar un usuario lógicamente (marcarlo como borrado)
	public boolean eliminarUsuario(int idUsuario) {
		String sql = "UPDATE usuarios SET borrado = TRUE WHERE idUsuario = ?";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idUsuario);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println("Error al eliminar usuario: " + e.getMessage());
			return false;
		} finally {
			// Cerrar recursos manualmente
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
	}

	// Método para actualizar un usuario en la base de datos
	public boolean actualizarUsuario(Usuario usuario) {
		String sql = "UPDATE usuarios SET nombre = ?, ap1 = ?, ap2 = ?, dni = ?, activo = ?, borrado = ? WHERE idUsuario = ?";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, usuario.getNombre());
			stmt.setString(2, usuario.getAp1());
			stmt.setString(3, usuario.getAp2());
			stmt.setString(4, usuario.getDni());
			stmt.setBoolean(5, usuario.isActivo());
			stmt.setBoolean(6, usuario.isBorrado());
			stmt.setInt(7, usuario.getIdUsuario());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println("Error al actualizar usuario: " + e.getMessage());
			return false;
		} finally {
			// Cerrar recursos manualmente
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
	}

	// Método para obtener todos los usuarios
	public List<Usuario> obtenerTodosUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuarios";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre"), rs.getString("ap1"), rs.getString("ap2"),
						rs.getString("dni"), rs.getBoolean("activo"), rs.getBoolean("borrado"));
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener todos los usuarios: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return usuarios;
	}

	// Método para verificar si existe un usuario por ID
	public boolean existeUsuarioPorId(int idUsuario) {
		String sql = "SELECT COUNT(*) FROM usuarios WHERE idUsuario = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conexion().conectar();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idUsuario);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.out.println("Error al verificar si el usuario existe por ID: " + e.getMessage());
		} finally {
			// Cerrar recursos manualmente
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar recursos: " + e.getMessage());
			}
		}
		return false;
	}
}
/*
 * package modelos;
 * 
 * import transversal.Conexion; import java.sql.*; import java.util.ArrayList;
 * import java.util.List;
 * 
 * public class UsuarioDAO {
 * 
 * // Método para agregar un usuario a la base de datos public boolean
 * agregarUsuario(Usuario usuario) { String sql =
 * "INSERT INTO usuarios (nombre, ap1, ap2, dni, activo, borrado) VALUES (?, ?, ?, ?, ?, ?)"
 * ; Connection conn = null; PreparedStatement stmt = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql);
 * stmt.setString(1, usuario.getNombre()); stmt.setString(2, usuario.getAp1());
 * stmt.setString(3, usuario.getAp2()); stmt.setString(4, usuario.getDni());
 * stmt.setBoolean(5, usuario.isActivo()); stmt.setBoolean(6,
 * usuario.isBorrado());
 * 
 * int rowsAffected = stmt.executeUpdate(); return rowsAffected > 0; } catch
 * (SQLException e) { System.out.println("Error al agregar usuario: " +
 * e.getMessage()); return false; } finally { try { if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } } }
 * 
 * // Obtener usuarios activos (no borrados) public List<Usuario>
 * obtenerUsuariosActivos() { List<Usuario> usuarios = new ArrayList<>(); String
 * sql = "SELECT * FROM usuarios WHERE activo = TRUE AND borrado = FALSE";
 * Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql); rs
 * = stmt.executeQuery();
 * 
 * while (rs.next()) { Usuario usuario = new Usuario( rs.getString("nombre"),
 * rs.getString("ap1"), rs.getString("ap2"), rs.getString("dni"),
 * rs.getBoolean("activo"), rs.getBoolean("borrado") );
 * usuario.setIdUsuario(rs.getInt("idUsuario")); usuarios.add(usuario); } }
 * catch (SQLException e) {
 * System.out.println("Error al obtener usuarios activos: " + e.getMessage()); }
 * finally { try { if (rs != null) { rs.close(); } if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } }
 * return usuarios; }
 * 
 * // Obtener usuarios inactivos (no borrados) public List<Usuario>
 * obtenerUsuariosInactivos() { List<Usuario> usuarios = new ArrayList<>();
 * String sql =
 * "SELECT * FROM usuarios WHERE activo = FALSE AND borrado = FALSE"; Connection
 * conn = null; PreparedStatement stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql); rs
 * = stmt.executeQuery();
 * 
 * while (rs.next()) { Usuario usuario = new Usuario( rs.getString("nombre"),
 * rs.getString("ap1"), rs.getString("ap2"), rs.getString("dni"),
 * rs.getBoolean("activo"), rs.getBoolean("borrado") );
 * usuario.setIdUsuario(rs.getInt("idUsuario")); usuarios.add(usuario); } }
 * catch (SQLException e) {
 * System.out.println("Error al obtener usuarios inactivos: " + e.getMessage());
 * } finally { try { if (rs != null) { rs.close(); } if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } }
 * return usuarios; }
 * 
 * // Obtener usuarios borrados public List<Usuario> obtenerUsuariosBorrados() {
 * List<Usuario> usuarios = new ArrayList<>(); String sql =
 * "SELECT * FROM usuarios WHERE borrado = TRUE"; Connection conn = null;
 * PreparedStatement stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql); rs
 * = stmt.executeQuery();
 * 
 * while (rs.next()) { Usuario usuario = new Usuario( rs.getString("nombre"),
 * rs.getString("ap1"), rs.getString("ap2"), rs.getString("dni"),
 * rs.getBoolean("activo"), rs.getBoolean("borrado") );
 * usuario.setIdUsuario(rs.getInt("idUsuario")); usuarios.add(usuario); } }
 * catch (SQLException e) {
 * System.out.println("Error al obtener usuarios borrados: " + e.getMessage());
 * } finally { try { if (rs != null) { rs.close(); } if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } }
 * return usuarios; }
 * 
 * // Método para buscar usuarios por nombre public List<Usuario>
 * buscarUsuariosPorNombre(String nombre) { List<Usuario> usuarios = new
 * ArrayList<>(); String sql =
 * "SELECT * FROM usuarios WHERE nombre LIKE ? AND borrado = FALSE"; Connection
 * conn = null; PreparedStatement stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql);
 * stmt.setString(1, "%" + nombre + "%"); rs = stmt.executeQuery();
 * 
 * while (rs.next()) { Usuario usuario = new Usuario( rs.getString("nombre"),
 * rs.getString("ap1"), rs.getString("ap2"), rs.getString("dni"),
 * rs.getBoolean("activo"), rs.getBoolean("borrado") );
 * usuario.setIdUsuario(rs.getInt("idUsuario")); usuarios.add(usuario); } }
 * catch (SQLException e) {
 * System.out.println("Error al buscar usuarios por nombre: " + e.getMessage());
 * } finally { try { if (rs != null) { rs.close(); } if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } }
 * return usuarios; }
 * 
 * // Método para buscar un usuario por su DNI public Usuario
 * buscarUsuarioPorDni(String dni) { String sql =
 * "SELECT * FROM usuarios WHERE dni = ? AND borrado = FALSE"; Connection conn =
 * null; PreparedStatement stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql);
 * stmt.setString(1, dni); rs = stmt.executeQuery();
 * 
 * if (rs.next()) { Usuario usuario = new Usuario( rs.getString("nombre"),
 * rs.getString("ap1"), rs.getString("ap2"), rs.getString("dni"),
 * rs.getBoolean("activo"), rs.getBoolean("borrado") );
 * usuario.setIdUsuario(rs.getInt("idUsuario")); return usuario; } } catch
 * (SQLException e) { System.out.println("Error al buscar usuario por DNI: " +
 * e.getMessage()); } finally { try { if (rs != null) { rs.close(); } if (stmt
 * != null) { stmt.close(); } if (conn != null) { conn.close(); } } catch
 * (SQLException e) { System.out.println("Error al cerrar recursos: " +
 * e.getMessage()); } } return null; }
 * 
 * // Método para eliminar un usuario lógicamente (marcarlo como borrado) public
 * boolean eliminarUsuario(int idUsuario) { String sql =
 * "UPDATE usuarios SET borrado = TRUE WHERE idUsuario = ?"; Connection conn =
 * null; PreparedStatement stmt = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql);
 * stmt.setInt(1, idUsuario);
 * 
 * int rowsAffected = stmt.executeUpdate(); return rowsAffected > 0; } catch
 * (SQLException e) { System.out.println("Error al eliminar usuario: " +
 * e.getMessage()); return false; } finally { try { if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } } }
 * 
 * // Método para actualizar un usuario en la base de datos public boolean
 * actualizarUsuario(Usuario usuario) { String sql =
 * "UPDATE usuarios SET nombre = ?, ap1 = ?, ap2 = ?, dni = ?, activo = ?, borrado = ? WHERE idUsuario = ?"
 * ; Connection conn = null; PreparedStatement stmt = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql);
 * stmt.setString(1, usuario.getNombre()); stmt.setString(2, usuario.getAp1());
 * stmt.setString(3, usuario.getAp2()); stmt.setString(4, usuario.getDni());
 * stmt.setBoolean(5, usuario.isActivo()); stmt.setBoolean(6,
 * usuario.isBorrado()); stmt.setInt(7, usuario.getIdUsuario());
 * 
 * int rowsAffected = stmt.executeUpdate(); return rowsAffected > 0; } catch
 * (SQLException e) { System.out.println("Error al actualizar usuario: " +
 * e.getMessage()); return false; } finally { try { if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } } }
 * 
 * // Método para obtener todos los usuarios public List<Usuario>
 * obtenerTodosUsuarios() { List<Usuario> usuarios = new ArrayList<>(); String
 * sql = "SELECT * FROM usuarios"; Connection conn = null; PreparedStatement
 * stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql); rs
 * = stmt.executeQuery();
 * 
 * while (rs.next()) { Usuario usuario = new Usuario( rs.getString("nombre"),
 * rs.getString("ap1"), rs.getString("ap2"), rs.getString("dni"),
 * rs.getBoolean("activo"), rs.getBoolean("borrado") );
 * usuario.setIdUsuario(rs.getInt("idUsuario")); usuarios.add(usuario); } }
 * catch (SQLException e) {
 * System.out.println("Error al obtener todos los usuarios: " + e.getMessage());
 * } finally { try { if (rs != null) { rs.close(); } if (stmt != null) {
 * stmt.close(); } if (conn != null) { conn.close(); } } catch (SQLException e)
 * { System.out.println("Error al cerrar recursos: " + e.getMessage()); } }
 * return usuarios; }
 * 
 * // Método para verificar si existe un usuario por ID public boolean
 * existeUsuarioPorId(int idUsuario) { String sql =
 * "SELECT COUNT(*) FROM usuarios WHERE idUsuario = ?"; Connection conn = null;
 * PreparedStatement stmt = null; ResultSet rs = null;
 * 
 * try { conn = new Conexion().conectar(); stmt = conn.prepareStatement(sql);
 * stmt.setInt(1, idUsuario); rs = stmt.executeQuery();
 * 
 * if (rs.next()) { return rs.getInt(1) > 0; } } catch (SQLException e) {
 * System.out.println("Error al verificar si el usuario existe por ID: " +
 * e.getMessage()); } finally { try { if (rs != null) { rs.close(); } if (stmt
 * != null) { stmt.close(); } if (conn != null) { conn.close(); } } catch
 * (SQLException e) { System.out.println("Error al cerrar recursos: " +
 * e.getMessage()); } } return false; } }
 */
