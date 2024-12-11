package modelos;

public class Usuario {
	private int idUsuario;
	private String nombre;
	private String ap1;
	private String ap2;
	private String dni;
	private boolean activo;
	private boolean borrado;

	public Usuario() {
		super();
	}

	public Usuario(String nombre, String ap1, String ap2, String dni, boolean activo, boolean borrado) {
		super();
		this.nombre = nombre;
		this.ap1 = ap1;
		this.ap2 = ap2;
		this.dni = dni;
		this.activo = activo;
		this.borrado = borrado;
	}

	public Usuario(int idUsuario, String nombre, String ap1, String ap2, String dni, boolean activo, boolean borrado) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.ap1 = ap1;
		this.ap2 = ap2;
		this.dni = dni;
		this.activo = activo;
		this.borrado = borrado;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAp1() {
		return ap1;
	}

	public void setAp1(String ap1) {
		this.ap1 = ap1;
	}

	public String getAp2() {
		return ap2;
	}

	public void setAp2(String ap2) {
		this.ap2 = ap2;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isBorrado() {
		return borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", ap1=" + ap1 + ", ap2=" + ap2 + ", dni="
				+ dni + ", activo=" + activo + ", borrado=" + borrado + "]";
	}

}
