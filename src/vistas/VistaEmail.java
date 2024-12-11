package vistas;

import controladores.EmailController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class VistaEmail extends JFrame {

    private JTextField emailField, subjectField;
    private JTextArea messageArea;
    private JButton sendButton, selectImageButton, backButton;
    private File selectedImageFile;
    private JLabel imageLabel, imageNameLabel;

    public VistaEmail() {
        // Configuración del JFrame
        setTitle("Enviar Correo");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 144, 255)); // Fondo azul
        add(mainPanel, BorderLayout.CENTER);

        // Configuración de GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ajustar ancho de los campos
        
     // Etiqueta y campo de texto para el correo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("Correo destinatario:");
        emailLabel.setForeground(Color.WHITE); // Texto en blanco
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // Etiqueta y campo de texto para el asunto
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel subjectLabel = new JLabel("Asunto:");
        subjectLabel.setForeground(Color.WHITE); // Texto en blanco
        mainPanel.add(subjectLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        subjectField = new JTextField(20);
        mainPanel.add(subjectField, gbc);

        // Etiqueta y área de texto para el mensaje
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        JLabel messageLabel = new JLabel("Mensaje:");
        messageLabel.setForeground(Color.WHITE); // Texto en blanco
        mainPanel.add(messageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        messageArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        mainPanel.add(scrollPane, gbc);

        // Etiqueta y campo para mostrar el nombre de la imagen seleccionada
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        imageLabel = new JLabel("Imagen seleccionada:");
        imageLabel.setForeground(Color.WHITE); // Texto en blanco
        mainPanel.add(imageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        imageNameLabel = new JLabel(""); // Para mostrar el nombre de la imagen
        mainPanel.add(imageNameLabel, gbc);

        // Vista previa de la imagen
        gbc.gridx = 1;
        gbc.gridy = 4;
        imageLabel = new JLabel(); // Donde se mostrará la imagen redimensionada
        mainPanel.add(imageLabel, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(30, 144, 255)); // Azul fuerte

        selectImageButton = new JButton("Seleccionar Imagen");
        styleButton(selectImageButton);
        sendButton = new JButton("Enviar Correo");
        styleButton(sendButton);
        backButton = new JButton("Regresar");
        styleButton(backButton);

        buttonPanel.add(selectImageButton);
        buttonPanel.add(sendButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners para los botones
        selectImageButton.addActionListener(e -> seleccionarImagen());

        sendButton.addActionListener(e -> enviarEmail());

        backButton.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new VistaReportes().setVisible(true); // Llama a la ventana anterior
        });
    }
    
    private void styleButton(JButton button) {
        button.setBackground(new Color(235, 160, 255));
        button.setForeground(Color.BLACK); // Texto blanco
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente moderna, negrita
        button.setFocusPainted(false); // Quitar borde de enfoque
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
        button.setPreferredSize(new Dimension(160, 40)); // Tamaño uniforme para los botones
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos al pasar el mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(218, 96, 250));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(235, 160, 255));
            }
        });
    }
    
    //--------------------------------------------------------------
    
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar una imagen");
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
//            JOptionPane.showMessageDialog(this, "Imagen seleccionada: " + selectedImageFile.getName());
            imageNameLabel.setText(selectedImageFile.getName()); // Muestra el nombre de la imagen
			mostrarImagenPrevia(selectedImageFile);  // Muestra una previa de la imagen
			
			// Mostrar vista previa dependiendo del tipo de archivo
	        if (isImage(selectedImageFile)) {
	            mostrarImagenPrevia(selectedImageFile); // Mostrar imágenes (JPG/PNG)
	        } else if (isGif(selectedImageFile)) {
	            mostrarGifPrevia(selectedImageFile); // Mostrar GIFs animados
	        } else {
	            JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen compatible.");
	        }
        }
    }
    
    // Método para verificar si el archivo es una imagen
    private boolean isImage(File file) {
        String filename = file.getName().toLowerCase();
        return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png");
    }

    // Método para verificar si el archivo es un GIF
    private boolean isGif(File file) {
        return file.getName().toLowerCase().endsWith(".gif");
    }
    
    //--------------------------------------------------------------
    
    // Método para mostrar una imagen redimensionada (JPG/PNG)
    private void mostrarImagenPrevia(File imageFile) {
        try {
            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            imageLabel.setIcon(icon); // Mostrar la imagen redimensionada
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    // Método para mostrar un GIF animado redimensionado
    private void mostrarGifPrevia(File gifFile) {
        try {
            ImageIcon gifIcon = new ImageIcon(gifFile.getAbsolutePath());
            Image gifImage = gifIcon.getImage().getScaledInstance(100, 80, Image.SCALE_DEFAULT);
            gifIcon = new ImageIcon(gifImage);
            imageLabel.setIcon(gifIcon); // Mostrar el GIF animado
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el GIF: " + e.getMessage());
        }
    }
    
    //--------------------------------------------------------------

    private void enviarEmail() {
        String receptor = emailField.getText();
        String asunto = subjectField.getText();
        String mensaje = messageArea.getText();

        if (receptor.isEmpty() || asunto.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        EmailController emailController = new EmailController();
        emailController.enviarEmail(receptor, asunto, mensaje, selectedImageFile);

        emailField.setText("");
        subjectField.setText("");
        messageArea.setText("");
        imageNameLabel.setText("");
        imageLabel.setIcon(null); // Limpia la imagen previa
    }
}
