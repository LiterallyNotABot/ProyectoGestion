package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame {

	public VistaPrincipal() {
		setTitle("Gestión de Usuarios");

		// Configuración de la ventana
		setSize(700, 500);
		setLocationRelativeTo(null); // Centra la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel principal
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout()); // Layout principal
		panel.setBackground(new Color(245, 245, 245)); // Fondo suave

		// Título
		JLabel label = new JLabel("Bienvenido a la Gestión de Usuarios", JLabel.CENTER);
		label.setFont(new Font("Segoe UI", Font.BOLD, 24));
		label.setForeground(new Color(40, 40, 40)); // Color oscuro para el título
		panel.add(label, BorderLayout.NORTH);

		// Panel para los botones
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(5, 1, 10, 10)); // 5 botones en una columna
		panelBotones.setBackground(new Color(245, 245, 245)); // Fondo suave

		// Botones
		JButton gestionUsuariosBtn = new JButton("Gestión de Usuarios");
		styleButton(gestionUsuariosBtn);

		JButton importarExportarBtn = new JButton("Importar/Exportar");
		styleButton(importarExportarBtn);

		JButton generarReportesBtn = new JButton("Generar Reportes");
		styleButton(generarReportesBtn);

		JButton revisarUsuariosBorradosBtn = new JButton("Revisar Usuarios Borrados");
		styleButton(revisarUsuariosBorradosBtn);

		JButton salirBtn = new JButton("Salir");
		styleButton(salirBtn);

		panelBotones.add(gestionUsuariosBtn);
		panelBotones.add(importarExportarBtn);
		panelBotones.add(generarReportesBtn);
		panelBotones.add(revisarUsuariosBorradosBtn);
		panelBotones.add(salirBtn);

		panel.add(panelBotones, BorderLayout.CENTER);

		add(panel);

		// Acción de los botones
		gestionUsuariosBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaGestionUsuarios().setVisible(true);
				dispose(); // Cierra la ventana actual
			}
		});

		importarExportarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaImportarExportar().setVisible(true);
				dispose();
			}
		});

		generarReportesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaReportes().setVisible(true);
				dispose();
			}
		});

		revisarUsuariosBorradosBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaUsuariosBorrados().setVisible(true);
				dispose();
			}
		});

		salirBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	// Método para estilizar los botones
	private void styleButton(JButton button) {
		button.setBackground(new Color(0, 123, 255));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button.setFocusPainted(false); // Quitar el borde de foco
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.setOpaque(true);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.WHITE);
				button.setForeground(Color.BLACK);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(0, 123, 255));
				button.setForeground(Color.WHITE);
			}
		});
	}
}
