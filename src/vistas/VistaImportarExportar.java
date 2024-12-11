package vistas;

import controladores.CsvController;
import controladores.JsonController;
import controladores.PdfController;
import controladores.UsuarioController;
import controladores.XmlController;
import modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class VistaImportarExportar extends JFrame {

    public VistaImportarExportar() {
        setTitle("Importar/Exportar Datos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal con fondo de imagen
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Layout principal

        // Título (JLabel)
        JLabel label = new JLabel("Importar y Exportar Datos", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.BLACK); // Color de texto negro
        panel.add(label, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 2, 10, 10));
        panelBotones.setOpaque(false); // Fondo transparente para los botones

        // Crear los botones con iconos escalados
        JButton importarCSVBtn = createButtonWithIcon("Importar CSV", "img/csv3.png");
        JButton exportarCSVBtn = createButtonWithIcon("Exportar CSV", "img/csv4.png");
        JButton importarXMLBtn = createButtonWithIcon("Importar XML", "img/xml3.png");
        JButton exportarXMLBtn = createButtonWithIcon("Exportar XML", "img/xml3.png");
        JButton exportarJSONBtn = createButtonWithIcon("Exportar JSON", "img/json.png");
        JButton importarPDFBtn = createButtonWithIcon("Importar PDF", "img/pdf2.png");
        JButton exportarPDFBtn = createButtonWithIcon("Exportar PDF", "img/pdf2.png");

        // Añadir los botones al panel
        panelBotones.add(importarCSVBtn);
        panelBotones.add(exportarCSVBtn);

        panelBotones.add(importarXMLBtn);
        panelBotones.add(exportarXMLBtn);

        panelBotones.add(importarPDFBtn);
        panelBotones.add(exportarPDFBtn);

        panelBotones.add(new JLabel()); // Espacio vacío
        panelBotones.add(exportarJSONBtn);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Botón para regresar a la vista principal
        JButton regresarBtn = createButtonWithIcon("Regresar", "img/atras.png");
        panel.add(regresarBtn, BorderLayout.SOUTH);

        add(panel);

        // Acciones de los botones
        importarCSVBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar archivo CSV");
                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    String rutaArchivo = fileChooser.getSelectedFile().getPath();
                    CsvController.cargarCsv(rutaArchivo);
                    JOptionPane.showMessageDialog(null, "Datos importados exitosamente desde CSV.");
                }
            }
        });

        exportarCSVBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CsvController.crearCsv();
                    JOptionPane.showMessageDialog(null, "Datos exportados exitosamente a CSV.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al exportar datos a CSV: " + ex.getMessage());
                }
            }
        });

        importarXMLBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    XmlController.cargarXML();
                    JOptionPane.showMessageDialog(null, "Datos importados exitosamente desde XML.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al importar datos desde XML: " + ex.getMessage());
                }
            }
        });

        exportarXMLBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    XmlController.crearXML();
                    JOptionPane.showMessageDialog(null, "Datos exportados exitosamente a XML.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al exportar datos a XML: " + ex.getMessage());
                }
            }
        });

        exportarJSONBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UsuarioController uc = new UsuarioController();
                    List<Usuario> usuarios = uc.obtenerTodosUsuarios();
                    JsonController jsonController = new JsonController();
                    jsonController.generarJson(usuarios);
                    JOptionPane.showMessageDialog(null, "Datos exportados exitosamente a JSON.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al exportar datos a JSON: " + ex.getMessage());
                }
            }
        });

        importarPDFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PdfController.importarUsuariosDesdePdf();
                    JOptionPane.showMessageDialog(null, "Datos importados exitosamente desde el PDF.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al importar datos desde el PDF: " + ex.getMessage());
                }
            }
        });

        exportarPDFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PdfController.exportarPdf();
                    JOptionPane.showMessageDialog(null, "Datos exportados exitosamente al PDF.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al exportar datos al PDF: " + ex.getMessage());
                }
            }
        });

        regresarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VistaPrincipal().setVisible(true);
            }
        });
    }

    // Método para crear botones con iconos escalados
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
}
