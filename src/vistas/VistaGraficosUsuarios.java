package vistas;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modelos.Usuario;
import modelos.UsuarioDAO;

public class VistaGraficosUsuarios extends JFrame {

    private UsuarioDAO usuarioDAO;

    public VistaGraficosUsuarios(String tipoGrafico) {
        usuarioDAO = new UsuarioDAO();

        // Configuración de la ventana
        setTitle("Gráficos de Usuarios");
        setSize(900, 700);
        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // No cerrar la ventana anterior

        // Panel principal con fondo personalizado
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Ruta absoluta para la imagen de fondo (directorio root/img)
                ImageIcon background = new ImageIcon("img/fondo5.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new BorderLayout()); // Layout principal

        // Título del gráfico
        JLabel labelTitulo = new JLabel("Visualización de Gráficos", JLabel.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        labelTitulo.setForeground(Color.WHITE); // Texto blanco
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(labelTitulo, BorderLayout.NORTH);

        // Crear y añadir el gráfico según el tipo seleccionado
        JPanel panelGrafico = new JPanel(new BorderLayout());
        panelGrafico.setOpaque(false);
        switch (tipoGrafico.toLowerCase()) {
            case "tarta":
                panelGrafico.add(crearGraficoTarta(), BorderLayout.CENTER);
                break;
            case "barras":
                panelGrafico.add(crearGraficoBarras(), BorderLayout.CENTER);
                break;
            case "lineas":
                panelGrafico.add(crearGraficoLineas(), BorderLayout.CENTER);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de gráfico no soportado.", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
        }
        panel.add(panelGrafico, BorderLayout.CENTER);

        // Botón para regresar
        JButton btnRegresar = createStyledButton("Regresar", "img/atras.png");
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaSeleccionarGrafico().setVisible(true);
                dispose();
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnRegresar);

        panel.add(panelBoton, BorderLayout.SOUTH);
        add(panel);
    }

    private ChartPanel crearGraficoTarta() {
        // Obtener datos
        List<Usuario> usuariosActivos = usuarioDAO.obtenerUsuariosActivos();
        List<Usuario> usuariosInactivos = usuarioDAO.obtenerUsuariosInactivos();
        List<Usuario> usuariosBorrados = usuarioDAO.obtenerUsuariosBorrados();

        int totalActivos = usuariosActivos.size();
        int totalInactivos = usuariosInactivos.size();
        int totalBorrados = usuariosBorrados.size();
        int totalNoBorrados = totalActivos + totalInactivos;

        // Crear el conjunto de datos
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Activos", totalActivos);
        dataset.setValue("Inactivos", totalInactivos);
        dataset.setValue("Borrados", totalBorrados);
        dataset.setValue("No Borrados", totalNoBorrados);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createPieChart("Distribución de Usuarios", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Activos", new Color(102, 204, 0));
        plot.setSectionPaint("Inactivos", new Color(255, 51, 51));
        plot.setSectionPaint("Borrados", new Color(255, 153, 51));
        plot.setSectionPaint("No Borrados", new Color(0, 102, 255));

        return new ChartPanel(chart);
    }

    private ChartPanel crearGraficoBarras() {
        // Crear conjunto de datos para barras
        XYSeries series = new XYSeries("Usuarios");
        series.add(1, usuarioDAO.obtenerUsuariosActivos().size());
        series.add(2, usuarioDAO.obtenerUsuariosInactivos().size());
        series.add(3, usuarioDAO.obtenerUsuariosBorrados().size());
        series.add(4, usuarioDAO.obtenerUsuariosActivos().size() + usuarioDAO.obtenerUsuariosInactivos().size());

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución de Usuarios",
                "Categoría",
                false,
                "Cantidad",
                dataset
        );

        return new ChartPanel(chart);
    }

    private ChartPanel crearGraficoLineas() {
        // Crear conjunto de datos para líneas
        XYSeries series = new XYSeries("Usuarios");
        series.add(1, usuarioDAO.obtenerUsuariosActivos().size());
        series.add(2, usuarioDAO.obtenerUsuariosInactivos().size());
        series.add(3, usuarioDAO.obtenerUsuariosBorrados().size());
        series.add(4, usuarioDAO.obtenerUsuariosActivos().size() + usuarioDAO.obtenerUsuariosInactivos().size());

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución de Usuarios",
                "Categoría",
                "Cantidad",
                dataset
        );

        XYPlot plot = chart.getXYPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(0, 102, 255));

        return new ChartPanel(chart);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);

        // Cargar y escalar la imagen con ruta absoluta (img en la raíz)
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        button.setIcon(scaledIcon);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));

        button.setPreferredSize(new Dimension(200, 60));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
