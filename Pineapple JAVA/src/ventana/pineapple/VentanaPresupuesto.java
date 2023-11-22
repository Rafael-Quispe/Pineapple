package ventana.pineapple;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class VentanaPresupuesto extends JFrame {
    private final List<String> productos;
    private final Map<String, Double> precios;

    public VentanaPresupuesto(List<String> productos, Map<String, Double> precios) {
        this.productos = productos;
        this.precios = precios;

        setTitle("Presupuesto");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Detalles del Pedido:");
        JTextPane textPane = new JTextPane();

        StyleContext context = new StyleContext();
        Style style = context.addStyle("Estilo", null);
        Font font = new Font("Arial", Font.PLAIN, 16);
        StyleConstants.setFontFamily(style, font.getFamily());
        StyleConstants.setFontSize(style, font.getSize());

        textPane.setEditable(false);
        textPane.setBackground(panel.getBackground());
        textPane.setCharacterAttributes(style, true);

        double costoTotal = 0.0;
        for (String producto : productos) {
            double precioUnitario = precios.get(producto);
            costoTotal += precioUnitario;
            textPane.setText(textPane.getText() + producto + ": $" + precioUnitario + "\n");
        }
        textPane.setText(textPane.getText() + "\nCosto Total: $" + costoTotal);

        panel.add(label);
        panel.add(textPane);

        JButton realizarPedidoButton = new JButton("Realizar pedido");
        realizarPedidoButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Pedido realizado con éxito");
            dispose();
        });

        JButton rechazarPedidoButton = new JButton("Rechazar presupuesto");
        rechazarPedidoButton.addActionListener((ActionEvent e) -> {
            rechazarPedido();
        });

        panel.add(realizarPedidoButton);
        panel.add(rechazarPedidoButton);

        add(panel);

        setVisible(true);
    }

    private void rechazarPedido() {
        String sql = "DELETE FROM Presupuestos WHERE cod_presupuesto = (SELECT MAX(cod_presupuesto) FROM Presupuestos)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pineapple", "root", "");
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Pedido rechazado correctamente.");
            } else {
                System.out.println("No se encontraron pedidos para rechazar.");
            }
            JOptionPane.showMessageDialog(this, "Pedido rechazado con éxito");
            dispose();
        }
        catch (SQLException e) {
                e.printStackTrace();
            }
    }


    private int obtenerNumeroCliente() {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pineapple", "root", "")) {
                String consulta = "SELECT ID FROM Clientes WHERE DNI = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
                    pstmt.setInt(1, 12345678);
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getInt("ID");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        }

        private int obtenerCodigoStock() {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pineapple", "root", "")) {
                String consulta = "SELECT COD_ST FROM Stock WHERE Num_serie_prod = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
                    pstmt.setInt(1, 8319131); // Número de serie de un producto de ejemplo, debes cambiarlo por el valor real
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getInt("COD_ST");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        }

        private int obtenerIdMedioPago() {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pineapple", "root", "")) {
                String consulta = "SELECT ID_PAGO FROM Medios_de_pago WHERE Tipo_pago = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
                    pstmt.setString(1, "Tarjeta de credito"); // Tipo de pago de ejemplo, debes cambiarlo por el valor real
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getInt("ID_PAGO");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        }


    private double calcularMontoTotal() {
        double montoTotal = 0.0;
        for (String producto : productos) {
            double precioUnitario = precios.get(producto);
            montoTotal += precioUnitario;
        }
        return montoTotal;
    }



    public static void mostrarVentana(List<String> productos, Map<String, Double> precios) {
        SwingUtilities.invokeLater(() -> {
            VentanaPresupuesto ventanaPresupuesto = new VentanaPresupuesto(productos, precios);
        });
    }

    public static void main(String[] args) {
        List<String> productosEjemplo = List.of("Producto1", "Producto2", "Producto3");
        Map<String, Double> preciosEjemplo = Map.of("Producto1", 10.0, "Producto2", 20.0, "Producto3", 30.0);
        mostrarVentana(productosEjemplo, preciosEjemplo);
    }
}
