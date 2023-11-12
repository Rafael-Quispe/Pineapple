package ventana.pineapple;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        // Establecer el estilo del texto
        StyleContext context = new StyleContext();
        Style style = context.addStyle("Estilo", null);
        Font font = new Font("Arial", Font.PLAIN, 16); // Puedes cambiar la fuente y el tamaño
        StyleConstants.setFontFamily(style, font.getFamily());
        StyleConstants.setFontSize(style, font.getSize());

        textPane.setEditable(false); // Deshabilitar la edición
        textPane.setBackground(panel.getBackground()); // Hacer el fondo transparente
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
            realizarPedido();
        });

        JButton rechazarPedidoButton = new JButton("Rechazar presupuesto");
        rechazarPedidoButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(VentanaPresupuesto.this, "Pedido cancelado con éxito");
            dispose(); // Cierra la ventana
        });

        panel.add(realizarPedidoButton);
        panel.add(rechazarPedidoButton);

        add(panel);

        setVisible(true);
    }

    private void realizarPedido() {
    try {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pineapple", "usuario", "contraseña")) {
            String insertSql = "INSERT INTO presupuestos (nro_cli_comp, COD_ST_comp, ID_pago_comp, monto, fecha, cantidad) VALUES (?, ?, ?, ?, CURDATE(), ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
                int nroCliComp = 1;
                int codStComp = 1;
                int idPagoComp = 1;
                double monto = 50.0;
                int cantidad = 3;
                
                pstmt.setInt(1, nroCliComp);
                pstmt.setInt(2, codStComp);
                pstmt.setInt(3, idPagoComp);
                pstmt.setDouble(4, monto);
                pstmt.setInt(5, cantidad);
                
                pstmt.executeUpdate();
            }
            // Cerrar la conexión
        }

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, "Pedido realizado con éxito");

        // Cerrar la ventana
        dispose();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar el pedido");
    }
}


    public static void mostrarVentana(List<String> productos, Map<String, Double> precios) {
        SwingUtilities.invokeLater(() -> {
            VentanaPresupuesto ventanaPresupuesto = new VentanaPresupuesto(productos, precios);
        });
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        List<String> productosEjemplo = List.of("Producto1", "Producto2", "Producto3");
        Map<String, Double> preciosEjemplo = Map.of("Producto1", 10.0, "Producto2", 20.0, "Producto3", 30.0);
        mostrarVentana(productosEjemplo, preciosEjemplo);
    }
}
