package ventana.pineapple;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VentanaDatosCliente {
    public static void mostrarVentana() {
        JFrame ventana = new JFrame("Datos del cliente");
        ventana.setSize(400, 600);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panelFondo = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapple JAVA\\src\\main\\java\\ventana\\pineapple\\images\\pineapple_web_blurred.png");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        panelFondo.setLayout(new java.awt.GridLayout(9, 2, 5, 5));
        
        JLabel dniLabel = new JLabel("DNI:");
        JTextField dniTextField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreTextField = new JTextField();
        JLabel apellidoLabel = new JLabel("Apellido:");
        JTextField apellidoTextField = new JTextField();
        JLabel mailLabel = new JLabel("Mail:");
        JTextField mailTextField = new JTextField();
        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionTextField = new JTextField();
        JLabel cpostalLabel = new JLabel("Código Postal:");
        JTextField cpostalTextField = new JTextField();
        JLabel telefonoLabel = new JLabel("Teléfono:");
        JTextField telefonoTextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBackground(new Color(40, 100, 255));
        guardarButton.setForeground(Color.WHITE);
        guardarButton.addActionListener((ActionEvent e) -> {
            int dni = Integer.parseInt(dniTextField.getText());
            String nombre = nombreTextField.getText();
            String apellido = apellidoTextField.getText();
            String mail = mailTextField.getText();
            String direccion = direccionTextField.getText();
            int cpostal = Integer.parseInt(cpostalTextField.getText());
            int telefono = Integer.parseInt(telefonoTextField.getText());

            if (clienteRegistrado(dni)) {
                JOptionPane.showMessageDialog(null, "Cliente ya registrado. Puede realizar otro pedido.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pineapple", "root", "")) {
                    String query = "INSERT INTO Clientes (DNI, Nombre, Apellido, Mail, Direccion, C_Postal, Telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, dni);
                        preparedStatement.setString(2, nombre);
                        preparedStatement.setString(3, apellido);
                        preparedStatement.setString(4, mail);
                        preparedStatement.setString(5, direccion);
                        preparedStatement.setInt(6, cpostal);
                        preparedStatement.setInt(7, telefono);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Cliente guardado en la base de datos.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el cliente en la base de datos.");
                }
            }

            ventana.dispose();
            VentanaPedido.mostrarVentana(dni);
        });

        panelFondo.add(guardarButton, gbc);

        dniLabel.setForeground(Color.WHITE);
        nombreLabel.setForeground(Color.WHITE);
        apellidoLabel.setForeground(Color.WHITE);
        mailLabel.setForeground(Color.WHITE);
        direccionLabel.setForeground(Color.WHITE);
        cpostalLabel.setForeground(Color.WHITE);
        telefonoLabel.setForeground(Color.WHITE);

        panelFondo.add(dniLabel);
        panelFondo.add(dniTextField);
        panelFondo.add(nombreLabel);
        panelFondo.add(nombreTextField);
        panelFondo.add(apellidoLabel);
        panelFondo.add(apellidoTextField);
        panelFondo.add(mailLabel);
        panelFondo.add(mailTextField);
        panelFondo.add(direccionLabel);
        panelFondo.add(direccionTextField);
        panelFondo.add(cpostalLabel);
        panelFondo.add(cpostalTextField);
        panelFondo.add(telefonoLabel);
        panelFondo.add(telefonoTextField);
        panelFondo.add(guardarButton);

        panelFondo.add(dniLabel);
        panelFondo.add(dniTextField);
        panelFondo.add(nombreLabel);
        panelFondo.add(nombreTextField);
        panelFondo.add(apellidoLabel);
        panelFondo.add(apellidoTextField);
        panelFondo.add(mailLabel);
        panelFondo.add(mailTextField);
        panelFondo.add(direccionLabel);
        panelFondo.add(direccionTextField);
        panelFondo.add(cpostalLabel);
        panelFondo.add(cpostalTextField);
        panelFondo.add(telefonoLabel);
        panelFondo.add(telefonoTextField);
        panelFondo.add(guardarButton);
        
        ventana.add(panelFondo);
        ventana.setVisible(true);
    }

    private static boolean clienteRegistrado(int dni) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pineapple", "root", "")) {
            String query = "SELECT * FROM Clientes WHERE DNI = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, dni);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar si el cliente está registrado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> mostrarVentana());
    }
}
