package vistas;

import modelos.Usuario;
import modelos.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaReportes extends JFrame {

    public VistaReportes() {
        setTitle("Gestión de Reportes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal con fondo de imagen
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(System.getProperty("user.dir") + "\\img\\fondo5.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new BorderLayout()); // Layout principal

        // Título (JLabel)
        JLabel label = new JLabel("Bienvenido a los reportes", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE); // Color de texto blanco
        panel.add(label, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));
        panelBotones.setOpaque(false); // Fondo transparente para los botones

        // Crear los botones
        JButton btnEmail = createButtonWithIcon("Email", "img/email8.png");
        JButton btnQR = createButtonWithIcon("QR", "img/qr6.png");
        JButton btnGraficos = createButtonWithIcon("Gráficos", "img/graficoTarta2.png");
        JButton btnAtras = createButtonWithIcon("Atrás", "img/atras.png");

        // Añadir los botones al panel
        panelBotones.add(btnEmail);
        panelBotones.add(btnQR);
        panelBotones.add(btnGraficos);
        panelBotones.add(btnAtras);
        panel.add(panelBotones, BorderLayout.CENTER);
        add(panel);

        // Acciones de los botones
        btnEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaEmail().setVisible(true);
                dispose(); // Cierra la ventana actual
            }
        });

        btnQR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                List<Usuario> listaDeUsuarios = usuarioDAO.obtenerUsuariosActivos();

                if (listaDeUsuarios != null && !listaDeUsuarios.isEmpty()) {
                    new ViewQR(listaDeUsuarios).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay usuarios activos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnGraficos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaSeleccionarGrafico().setVisible(true);
                dispose();
            }
        });

        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VistaPrincipal().setVisible(true);
            }
        });
    }

    // Método para crear botones con iconos y estilos personalizados
    private JButton createButtonWithIcon(String text, String relativePath) {
        JButton button = new JButton(text);

        // Construir la ruta absoluta basada en la raíz del proyecto
        String fullPath = System.getProperty("user.dir") + "\\" + relativePath;

        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(fullPath);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono desde la ruta: " + fullPath);
        }

        // Escalar la imagen si se cargó correctamente
        if (originalIcon != null) {
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            button.setIcon(scaledIcon);
        }

        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Ajustar el tamaño y color de fondo del botón
        button.setPreferredSize(new Dimension(200, 70));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto al pasar el mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaReportes().setVisible(true));
    }
}
