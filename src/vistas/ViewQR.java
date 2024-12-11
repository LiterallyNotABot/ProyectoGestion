package vistas;
import modelos.Usuario;
import controladores.QRController;
import controladores.VisorQR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewQR extends JFrame {

    private JComboBox<String> comboBoxUsuarios;
    private JButton botonGenerarQR;
    private List<Usuario> usuariosActivos;

    public ViewQR(List<Usuario> usuarios) {
        this.usuariosActivos = usuarios;

        // Configuración de la ventana
        configurarVentana();

        // Crear componentes
        comboBoxUsuarios = crearComboBoxUsuarios();
        botonGenerarQR = crearBotonGenerarQR();
        JButton botonAtras = crearBotonAtras();

        // Crear panel con fondo de imagen
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("img/fondo5.jpg"); // Ruta de la imagen
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // Usamos GridLayout para alinear los componentes

        // Agregar componentes al panel
        panel.add(new JLabel("Selecciona un usuario:") {{
            setFont(new Font("Segoe UI", Font.BOLD, 20)); // Fuente más grande y negrita
            setForeground(Color.WHITE); // Color blanco
            setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        }});
        panel.add(comboBoxUsuarios);
        panel.add(botonGenerarQR);
        panel.add(botonAtras);

        // Añadir el panel al JFrame
        add(panel);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Configuración de la ventana
    private void configurarVentana() {
        setTitle("Seleccionar Usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);  // Centra la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // No cierra la aplicación completa
    }

    // Crear JComboBox con los usuarios
    private JComboBox<String> crearComboBoxUsuarios() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (Usuario usuario : usuariosActivos) {
            comboBox.addItem(usuario.getNombre() + " " + usuario.getAp1() + " " + usuario.getAp2());
        }
        comboBox.setBackground(new Color(255, 255, 255)); // Fondo blanco para el combo box
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Fuente moderna
        return comboBox;
    }

    // Crear botón "Generar QR"
    private JButton crearBotonGenerarQR() {
        JButton boton = new JButton("Generar QR");
        styleButton(boton);  // Aplicar estilo
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarYMostrarQR();
            }
        });
        return boton;
    }

    // Crear botón "Atrás"
    private JButton crearBotonAtras() {
        JButton boton = new JButton("Atrás");
        styleButton(boton);  // Aplicar estilo
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new VistaReportes().setVisible(true); // Regresa a la ventana de reportes
            }
        });
        return boton;
    }

    // Método para generar y mostrar el QR
    private void generarYMostrarQR() {
        // Obtener el usuario seleccionado
        String nombreCompleto = (String) comboBoxUsuarios.getSelectedItem();
        Usuario usuarioSeleccionado = obtenerUsuarioPorNombre(nombreCompleto);

        if (usuarioSeleccionado != null) {
            // Obtener fecha y hora actual
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String fechaFormateada = fechaHoraActual.format(formatter);

            String textoQR = usuarioSeleccionado.getNombre() + " " +
                             usuarioSeleccionado.getAp1() + " " + usuarioSeleccionado.getAp2() + " " +
                             "Fecha y Hora " + fechaFormateada;

            // Crear la ruta relativa para almacenar el archivo QR
            File outputDir = new File("output"); // Carpeta al mismo nivel que el proyecto
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // Crear la carpeta si no existe
            }

            String pathname = "output/usuario_" + usuarioSeleccionado.getIdUsuario() + ".png";
            QRController.generarQR(textoQR, pathname);

            try {
                new VisorQR(pathname);  // Muestra el QR generado
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    // Obtener el usuario seleccionado a partir del nombre completo
    private Usuario obtenerUsuarioPorNombre(String nombreCompleto) {
        for (Usuario usuario : usuariosActivos) {
            String nombreUsuario = usuario.getNombre() + " " + usuario.getAp1() + " " + usuario.getAp2();
            if (nombreUsuario.equals(nombreCompleto)) {
                return usuario;
            }
        }
        return null;
    }

    // Método para estilizar los botones con colores y efectos
    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255)); // Color de fondo azul
        button.setForeground(Color.WHITE); // Texto en blanco
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Fuente moderna
        button.setFocusPainted(false); // Quitar el borde de foco
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Borde vacío con algo de padding
        button.setOpaque(true); // Hacer que el color de fondo se aplique correctamente
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar el cursor al pasar por encima
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); // Cambiar el fondo a blanco
                button.setForeground(new Color(0, 123, 255)); // Cambiar el texto a azul
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255)); // Vuelve al color azul
                button.setForeground(Color.WHITE); // Vuelve al texto blanco
            }
        });
    }
}
