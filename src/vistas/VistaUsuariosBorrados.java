package vistas;

import controladores.UsuarioController;
import modelos.Usuario;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.List;

public class VistaUsuariosBorrados extends JFrame {

	private UsuarioController usuarioController;

	public VistaUsuariosBorrados() {
		usuarioController = new UsuarioController();
		setTitle("Usuarios Borrados");

		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel principal con fondo de imagen
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon background = new ImageIcon("C:\\img\\fondo5.jpg");
				g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};

		JLabel label = new JLabel("Usuarios Borrados", JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(label, BorderLayout.NORTH);

		// Obtenemos la lista de usuarios borrados
		List<Usuario> usuariosBorrados = usuarioController.obtenerUsuariosBorrados();

		// Creamos un panel para mostrar la lista de usuarios
		String[] columnNames = { "ID", "Nombre", "Apellido 1", "Apellido 2", "DNI" };
		Object[][] data = new Object[usuariosBorrados.size()][6];

		for (int i = 0; i < usuariosBorrados.size(); i++) {
			Usuario usuario = usuariosBorrados.get(i);
			data[i][0] = usuario.getIdUsuario();
			data[i][1] = usuario.getNombre();
			data[i][2] = usuario.getAp1();
			data[i][3] = usuario.getAp2();
			data[i][4] = usuario.getDni();
		}

		// Creamos la tabla para mostrar los usuarios
		JTable table = new JTable(data, columnNames);

		// Estilo para el encabezado
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(0, 123, 255)); // Fondo azul
		header.setForeground(Color.BLACK); // Texto blanco
		header.setFont(header.getFont().deriveFont(Font.BOLD));

		// Cambiar el color de fondo de las celdas de la tabla
		table.setBackground(new Color(160, 231, 255)); // Fondo de las celdas

		// Cambiar el color de fondo del área de la tabla
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200)); // Tamaño preferido del área de la tabla

		panel.add(scrollPane, BorderLayout.CENTER);

		// Botón para regresar a la vista principal
		JPanel panelBotones = new JPanel();

		JButton regresarBtn = new JButton("Volver a la Vista Principal");

		// Estilo del botón
		regresarBtn.setBackground(new Color(0, 123, 255)); // Color de fondo azul
		regresarBtn.setForeground(Color.WHITE); // Texto en blanco
		regresarBtn.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente moderna
		regresarBtn.setFocusPainted(false); // Quitar el borde de foco
		regresarBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Borde vacío con algo de padding
		regresarBtn.setOpaque(true); // Hacer que el color de fondo se aplique correctamente
		regresarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar el cursor al pasar por encima

		// Cambiar color al pasar el mouse
		regresarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				regresarBtn.setBackground(new Color(0, 102, 204)); // Color al pasar el mouse
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				regresarBtn.setBackground(new Color(0, 123, 255)); // Vuelve al color original
			}
		});

		// Botón para regresar a la vista principal
		regresarBtn.addActionListener(e -> {
			new VistaPrincipal().setVisible(true);
			dispose(); // Cierra la vista actual
		});

		panelBotones.add(regresarBtn);
		panel.add(panelBotones, BorderLayout.SOUTH);

		regresarBtn.addActionListener(e -> {
			new VistaPrincipal().setVisible(true);
			dispose(); // Cierra la vista actual
		});

		add(panel);
	}
}
