package vistas;

import controladores.UsuarioController;
import modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaGestionUsuarios extends JFrame {

	private JTable tablaUsuarios;
	private DefaultTableModel modeloTabla;
	private UsuarioController usuarioController;

	public VistaGestionUsuarios() {
		// Título de la ventana
		setTitle("Gestión de Usuarios");

		// Configuración de la ventana
		setSize(600, 400);
		setLocationRelativeTo(null); // Centra la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Panel principal
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// Título
		JLabel label = new JLabel("Gestión de Usuarios", JLabel.CENTER);

		// Panel para el formulario de creación/edición de usuario
		JPanel panelFormulario = new JPanel();
		panelFormulario.setLayout(new GridLayout(5, 2, 10, 10)); // 5 filas, 2 columnas

		// Campos del formulario
		panelFormulario.add(new JLabel("Nombre:"));
		JTextField txtNombre = new JTextField();
		panelFormulario.add(txtNombre);

		panelFormulario.add(new JLabel("Apellido 1:"));
		JTextField txtApellido1 = new JTextField();
		panelFormulario.add(txtApellido1);

		panelFormulario.add(new JLabel("Apellido 2:"));
		JTextField txtApellido2 = new JTextField();
		panelFormulario.add(txtApellido2);

		panelFormulario.add(new JLabel("DNI:"));
		JTextField txtDNI = new JTextField();
		panelFormulario.add(txtDNI);

		panelFormulario.add(new JLabel("Activo:"));
		JCheckBox chkActivo = new JCheckBox();
		panelFormulario.add(chkActivo);

		// Botones
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setBackground(new Color(240, 248, 255));

		JButton btnAgregar = new JButton("Agregar");
		styleButton(btnAgregar);

		JButton btnActualizar = new JButton("Actualizar");
		styleButton(btnActualizar);

		JButton btnEliminar = new JButton("Eliminar");
		styleButton(btnEliminar);

		JButton btnBuscar = new JButton("Buscar");
		styleButton(btnBuscar);

		JButton btnRegresar = new JButton("Regresar");
		styleButton(btnRegresar); // Agregamos el botón al panel

		panelBotones.add(btnAgregar);
		panelBotones.add(btnActualizar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnBuscar);
		panelBotones.add(btnRegresar);

		// Tabla de usuarios
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Apellido 1");
		modeloTabla.addColumn("Apellido 2");
		modeloTabla.addColumn("DNI");
		modeloTabla.addColumn("Activo");

		tablaUsuarios = new JTable(modeloTabla);

		// Estilo para el encabezado
		JTableHeader header = tablaUsuarios.getTableHeader();
		header.setBackground(new Color(0, 123, 255)); //
		header.setForeground(Color.WHITE);
		header.setFont(header.getFont().deriveFont(Font.BOLD));

		// Cambiar el color de fondo de las celdas de la tabla
		tablaUsuarios.setBackground(new Color(160, 231, 255));

		// Cambiar el color de fondo del área de la tabla
		JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

		scrollPane.setPreferredSize(new Dimension(500, 200));

		// Agregar paneles a la ventana
		panel.add(panelFormulario, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelBotones, BorderLayout.SOUTH);

		add(panel);

		// Inicializamos el controlador
		usuarioController = new UsuarioController();

		// Cargar los usuarios activos en la tabla
		cargarUsuariosPorNombre(""); // Cargar solo usuarios activos

		// Funcionalidades de los botones
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText();
				String apellido1 = txtApellido1.getText();
				String apellido2 = txtApellido2.getText();
				String dni = txtDNI.getText();
				boolean activo = chkActivo.isSelected();

				// Agregar el usuario usando el controlador
				boolean agregado = usuarioController.agregarUsuario(nombre, apellido1, apellido2, dni, activo);

				if (agregado) {
					cargarUsuariosPorNombre(""); // Recargamos solo usuarios activos
					txtNombre.setText("");
					txtApellido1.setText("");
					txtApellido2.setText("");
					txtDNI.setText("");
					chkActivo.setSelected(false);
				} else {
					JOptionPane.showMessageDialog(null, "Error al agregar usuario.");
				}
			}
		});

		btnActualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tablaUsuarios.getSelectedRow();
				if (selectedRow >= 0) {
					int idUsuario = (int) modeloTabla.getValueAt(selectedRow, 0);
					String nombre = txtNombre.getText();
					String apellido1 = txtApellido1.getText();
					String apellido2 = txtApellido2.getText();
					String dni = txtDNI.getText();
					boolean activo = chkActivo.isSelected();

					boolean actualizado = usuarioController.actualizarUsuario(idUsuario, nombre, apellido1, apellido2,
							dni, activo, false);

					if (actualizado) {
						cargarUsuariosPorNombre("");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
					}

					txtNombre.setText("");
					txtApellido1.setText("");
					txtApellido2.setText("");
					txtDNI.setText("");
					chkActivo.setSelected(false);
				} else {
					JOptionPane.showMessageDialog(null, "Selecciona un usuario para actualizar.");
				}
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tablaUsuarios.getSelectedRow();
				if (selectedRow >= 0) {
					int idUsuario = (int) modeloTabla.getValueAt(selectedRow, 0);

					boolean eliminado = usuarioController.eliminarUsuario(idUsuario);

					if (eliminado) {
						cargarUsuariosPorNombre("");
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar usuario.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecciona un usuario para eliminar.");
				}
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText();
				String dni = txtDNI.getText();

				if (!nombre.isEmpty()) {
					cargarUsuariosPorNombre(nombre);
				} else if (!dni.isEmpty()) {
					cargarUsuariosPorDNI(dni);
				} else {
					cargarUsuariosPorNombre("");
				}
			}
		});

		btnRegresar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra la ventana actual
				new VistaPrincipal().setVisible(true); // Abre la ventana principal
			}
		});
	}

	// Método para estilizar los botones con un color azul
	private void styleButton(JButton button) {
		button.setBackground(new Color(0, 123, 255)); // Color de fondo azul
		button.setForeground(Color.WHITE); // Texto en blanco
		button.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente moderna
		button.setFocusPainted(false); // Quitar el borde de foco
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Borde vacío con algo de padding
		button.setOpaque(true); // Hacer que el color de fondo se aplique correctamente
		button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar el cursor al pasar por encima
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(0, 102, 204)); // Color al pasar el mouse
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(0, 123, 255)); // Vuelve al color original
			}
		});
	}

	private void cargarUsuariosPorNombre(String nombre) {
		List<Usuario> usuarios = usuarioController.buscarUsuariosPorNombre(nombre);
		modeloTabla.setRowCount(0);
		for (Usuario usuario : usuarios) {
			modeloTabla.addRow(new Object[] { usuario.getIdUsuario(), usuario.getNombre(), usuario.getAp1(),
					usuario.getAp2(), usuario.getDni(), usuario.isActivo() });
		}
	}

	private void cargarUsuariosPorDNI(String dni) {
		Usuario usuario = usuarioController.buscarUsuarioPorDni(dni);
		modeloTabla.setRowCount(0);
		if (usuario != null) {
			modeloTabla.addRow(new Object[] { usuario.getIdUsuario(), usuario.getNombre(), usuario.getAp1(),
					usuario.getAp2(), usuario.getDni(), usuario.isActivo() });
		} else {
			JOptionPane.showMessageDialog(null, "No se encontró un usuario con ese DNI.");
			cargarUsuariosPorNombre("");
		}
	}
}
