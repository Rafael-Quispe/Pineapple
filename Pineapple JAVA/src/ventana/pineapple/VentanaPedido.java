package ventana.pineapple;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaPedido extends JFrame {
    private final Map<String, Integer> stockActual = new HashMap<>();
    private final Map<String, Double> precios = new HashMap<>();
    private final List<String> productosSeleccionados = new ArrayList<>();
    private int idPago;

    public static void mostrarVentana(int dni) {
        VentanaPedido ventanaPedido = new VentanaPedido();

        JFrame ventana = new JFrame("Pedido");
        ventana.setTitle("Pedido de Dispositivos");
        ventana.setSize(600, 300);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pineapple", "root", "")) {
            ventanaPedido.cargarDatos(connection);
            if (!ventanaPedido.metodoDePagoSeleccionado) {
                ventanaPedido.idPago = ventanaPedido.obtenerIdPago(ventanaPedido.obtenerTiposDePago()[0], connection);
                ventanaPedido.metodoDePagoSeleccionado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(ventanaPedido, "Error al conectar a la base de datos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    JPanel panelFondo = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon imagenFondo = new ImageIcon(
                    "C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapple JAVA\\src\\main\\java\\ventana\\pineapple\\images\\VentanaPedido.png");
            g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };

    JLabel label = new JLabel("Seleccione los productos que desea comprar:");
    var productos = new JComboBox<String>(ventanaPedido.precios.keySet().toArray(String[]::new));
    JTextField cantidad = new JTextField(5);
    JComboBox<String> mediosDePago = new JComboBox<>(ventanaPedido.obtenerTiposDePago());
    JButton agregar = new JButton("Agregar al Pedido");
    JButton finalizar = new JButton("Finalizar Pedido");

    agregar.addActionListener((ActionEvent e) -> {
        String productoSeleccionado = (String) productos.getSelectedItem();
        int cantidadSeleccionada = Integer.parseInt(cantidad.getText());

        if (ventanaPedido.verificarStock(productoSeleccionado, cantidadSeleccionada)) {
            ventanaPedido.productosSeleccionados.add(productoSeleccionado);
            JOptionPane.showMessageDialog(ventanaPedido, "Producto agregado al pedido", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(ventanaPedido,
                    "Lo sentimos. No hay suficiente stock de " + productoSeleccionado, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    });

    finalizar.addActionListener((ActionEvent e) -> {
        if (ventanaPedido.productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(ventanaPedido, "Por favor, seleccione al menos un producto", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            ventana.dispose();
            VentanaPresupuesto ventanaPresupuesto = new VentanaPresupuesto(ventanaPedido.productosSeleccionados,
                    ventanaPedido.precios);
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pineapple", "root",
                    "")) {
                for (String producto : ventanaPedido.productosSeleccionados) {
                    ventanaPedido.insertarPedido(connection, producto, cantidad, dni);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    panelFondo.add(label, gbc);

    gbc.gridy++;
    panelFondo.add(productos, gbc);

    gbc.gridy++;
    panelFondo.add(new JLabel("Cantidad:"), gbc);

    gbc.gridx++;
    panelFondo.add(cantidad, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    panelFondo.add(new JLabel("Método de Pago:"), gbc);

    gbc.gridx++;
    panelFondo.add(mediosDePago, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    panelFondo.add(agregar, gbc);

    gbc.gridx++;
    panelFondo.add(finalizar, gbc);

    ventana.add(panelFondo);
    ventana.setVisible(true);
}
    private boolean metodoDePagoSeleccionado;


    private void cargarDatos(Connection conexion) {
        try {
            PreparedStatement statement = conexion.prepareStatement(
                    "SELECT P.Modelo, P.Precio, S.CANTIDAD_ST AS Stock " +
                            "FROM Productos P " +
                            "JOIN Stock S ON P.Numero_serie = S.Num_serie_prod"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String modelo = resultSet.getString("Modelo");
                double precio = resultSet.getDouble("Precio");
                int stock = resultSet.getInt("Stock");

                stockActual.put(modelo, stock);
                precios.put(modelo, precio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarStock(String producto, int cantidad) {
        return stockActual.getOrDefault(producto, 0) >= cantidad;
    }

    private String[] obtenerTiposDePago() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pineapple", "root", "")) {
            String query = "SELECT Tipo_pago FROM Medios_de_pago";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                List<String> tiposDePago = new ArrayList<>();
                while (resultSet.next()) {
                    tiposDePago.add(resultSet.getString("Tipo_pago"));
                }
                return tiposDePago.toArray(new String[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    private int obtenerIdPago(String tipoPago, Connection connection) {
        int idPago = 0;
        try {
            String query = "SELECT ID_PAGO FROM Medios_de_pago WHERE Tipo_pago = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, tipoPago);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    idPago = resultSet.getInt("ID_PAGO");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPago;
    }

    private void insertarPedido(Connection connection, String producto, JTextField cantidad1, int dni) {
        try {
            int numeroCliente = obtenerNumeroCliente(connection, dni);
            int codigoStock = obtenerCodigoStock(connection);

            double precioUnitario = precios.get(producto);
            int cantidad = Integer.parseInt(cantidad1.getText());
            double monto = precioUnitario * cantidad;

            java.util.Date utilDate = new java.util.Date();
            java.sql.Date fechaActual = new java.sql.Date(utilDate.getTime());

            String query = "INSERT INTO Presupuestos (nro_cli_comp, COD_ST_comp, ID_pago_comp, monto, fecha, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                System.out.println("Número de Cliente: " + numeroCliente);
                System.out.println("Código de Stock: " + codigoStock);
                System.out.println("ID de Pago: " + idPago);
                System.out.println("Monto: " + monto);
                System.out.println("Fecha Actual: " + fechaActual);
                System.out.println("Cantidad: " + cantidad);
                
                statement.setInt(1, numeroCliente);
                statement.setInt(2, codigoStock);
                statement.setInt(3, idPago);
                statement.setDouble(4, monto);
                statement.setDate(5, fechaActual);
                statement.setInt(6, cantidad);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el pedido en la base de datos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerNumeroCliente(Connection connection, int dni) {
        int numeroCliente = 0;
        try {
            String query = "SELECT ID FROM Clientes WHERE DNI = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, dni);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    numeroCliente = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numeroCliente;
    }

    private int obtenerCodigoStock(Connection connection) {
        int codigoStock = 0;
        try {
            String query = "SELECT COD_ST FROM Stock WHERE CANTIDAD_ST > 0 LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    codigoStock = resultSet.getInt("COD_ST");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoStock;
    }
}
