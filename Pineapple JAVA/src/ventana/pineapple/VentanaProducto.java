package ventana.pineapple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaProducto {
    public static void mostrarVentana(String producto, String descripcion) {
        JFrame ventana = new JFrame(producto);
        ventana.setSize(1100, 700);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        String imagePath = "C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapple JAVA\\src\\main\\java\\ventana\\pineapple\\images\\";

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = null;
                switch (producto) {
                    case "Pine Phone" -> imagenFondo = new ImageIcon(imagePath + "PinePhone.png");
                    case "Pine Touch" -> imagenFondo = new ImageIcon(imagePath + "PineTouch.png");
                    case "Pine Mac" -> imagenFondo = new ImageIcon(imagePath + "PineMac.png");
                    default -> imagenFondo = new ImageIcon(imagePath + "pineapple_web_blurred.png");
                }
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelFondo.setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JLabel etiquetaProducto = new JLabel(producto);
        etiquetaProducto.setFont(new Font("Arial", Font.BOLD, 36));
        etiquetaProducto.setAlignmentX(Component.CENTER_ALIGNMENT);
        if ("Pine Touch".equals(producto)) {
            etiquetaProducto.setForeground(Color.WHITE);
        }

        panelPrincipal.add(etiquetaProducto);

        if (producto.equals("Pine Mac")) {
            JLabel textoAdicional = new JLabel("Tu espacio se ve mejor con una PineMac.");
            textoAdicional.setFont(new Font("Arial", Font.ITALIC, 18));
            textoAdicional.setAlignmentX(Component.CENTER_ALIGNMENT);
            if ("Pine Touch".equals(producto)) {
                textoAdicional.setForeground(Color.WHITE);
            }
            panelPrincipal.add(textoAdicional);
        }

        JLabel etiquetaDescripcion = new JLabel("<html><div style='width: 400px; text-align: center;'>" + descripcion + "</div></html>");
        etiquetaDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaDescripcion.setOpaque(false);
        if ("Pine Touch".equals(producto)) {
            etiquetaDescripcion.setForeground(Color.WHITE);
        }

        panelPrincipal.add(etiquetaDescripcion);

        panelPrincipal.add(Box.createVerticalGlue());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);

        JButton botonCarrito = new JButton("COMPRAR AHORA");
        estiloBoton(botonCarrito, Color.BLUE, Color.WHITE);
        botonCarrito.addActionListener((ActionEvent e) -> {
            VentanaDatosCliente.mostrarVentana();
        });

        JButton botonDescripcion = new JButton("Mostrar Descripción");
        estiloBoton(botonDescripcion, Color.GRAY, Color.WHITE);
        botonDescripcion.addActionListener((ActionEvent e) -> {
            String descripcionProducto = obtenerDescripcionProducto(producto);
            JOptionPane.showMessageDialog(ventana, descripcionProducto, "Descripción", JOptionPane.INFORMATION_MESSAGE);
        });

        panelBoton.add(botonCarrito);
        panelBoton.add(botonDescripcion);

        panelFondo.add(panelBoton, BorderLayout.SOUTH);
        panelFondo.add(panelPrincipal, BorderLayout.NORTH);

        ventana.add(panelFondo);
        ventana.setVisible(true);
    }

    private static void estiloBoton(AbstractButton boton, Color fondo, Color texto) {
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setPreferredSize(new Dimension(200, 40));
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private static String obtenerDescripcionProducto(String producto) {
        switch (producto) {
            case "Pine Phone" -> {
                return "¡Descubre la libertad en tus manos con PinePhone! Este revolucionario smartphone ofrece un control total sobre tu dispositivo, con un sistema operativo de código abierto que te permite personalizar cada detalle.";
            }
            case "Pine Touch" -> {
                return "Sumérgete en la innovación táctil con PineTouch, la solución perfecta para aquellos que buscan la combinación ideal entre estilo y rendimiento. Con su pantalla táctil intuitiva y capacidad de respuesta incomparable, PineTouch redefine la forma en que interactúas con la tecnología.";
            }
            case "Pine Mac" -> {
                return "Transforma tu espacio con PineMac, la joya de la corona en tecnología y diseño. Potencia tu productividad con un rendimiento excepcional y un diseño minimalista que encaja perfectamente en cualquier entorno.";
            }
            default -> {
                return "Descripción no disponible";
            }
        }
    }
}
