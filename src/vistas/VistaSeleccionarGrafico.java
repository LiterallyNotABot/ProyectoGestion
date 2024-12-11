package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaSeleccionarGrafico extends JFrame {

    public VistaSeleccionarGrafico() {
        setTitle("Seleccionar Tipo de Gráfico");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal con fondo de imagen
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Ruta relativa para la imagen de fondo (directorio root/img)
                ImageIcon background = new ImageIcon("img/fondo5.jpg"); // Usar ruta relativa
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new BorderLayout()); // Layout principal

        // Título (JLabel)
        JLabel label = new JLabel("Selecciona el Tipo de Gráfico", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE); // Color de texto blanco
        panel.add(label, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));
        panelBotones.setOpaque(false); // Fondo transparente para los botones

        // Crear botones con iconos
        JButton btnTarta = createButtonWithIcon("Gráfico de Tarta", "img/graficoTarta2.png"); // Ruta relativa
        JButton btnBarras = createButtonWithIcon("Gráfico de Barras", "img/graficoBarras8.png"); // Ruta relativa
        JButton btnLineas = createButtonWithIcon("Gráfico de Líneas", "img/graficoLineal6.png"); // Ruta relativa
        JButton btnAtras = createButtonWithIcon("Volver Atrás", "img/atras.png"); // Ruta relativa

        // Añadir botones al panel
        panelBotones.add(btnTarta);
        panelBotones.add(btnBarras);
        panelBotones.add(btnLineas);
        panelBotones.add(btnAtras);
        panel.add(panelBotones, BorderLayout.CENTER);

        // Agregar panel al JFrame
        add(panel);

        // Acción de cada botón
        btnTarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaGraficosUsuarios("tarta").setVisible(true);
                dispose();
            }
        });

        btnBarras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaGraficosUsuarios("barras").setVisible(true);
                dispose();
            }
        });

        btnLineas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaGraficosUsuarios("lineas").setVisible(true);
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
    private JButton createButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);

        // Cargar y escalar la imagen a un tamaño más grande
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Tamaño más grande para el ícono
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        button.setIcon(scaledIcon); // Establece el icono escalado
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Ajustar el tamaño y color de fondo del botón
        button.setPreferredSize(new Dimension(250, 70));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false); // Quitar borde de foco
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar el cursor al pasar el mouse

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
}
