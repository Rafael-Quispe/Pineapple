package ventana.pineapple;


import javax.swing.*;
import java.awt.*;


public class VentanaProducto {


    public static void mostrarVentana(String producto, String descripcion) {
        JFrame ventana = new JFrame(producto);
        ventana.setSize(1100, 700);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);


        // Crear el panel de fondo con la imagen de fondo correspondiente al producto
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = null;
                switch (producto) {
                    case "Pine Phone" -> imagenFondo = new ImageIcon("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapplee\\src\\main\\java\\ventana\\pineapple\\images\\PinePhone.png");
                    case "Pine Touch" -> imagenFondo = new ImageIcon("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapplee\\src\\main\\java\\ventana\\pineapple\\images\\PineTouch.png");
                    case "Pine Mac" -> imagenFondo = new ImageIcon("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapplee\\src\\main\\java\\ventana\\pineapple\\images\\PineMac.png");
                    default -> imagenFondo = new ImageIcon("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Pineapplee\\src\\main\\java\\ventana\\pineapple\\images\\pineapple_web_blurred.png");
                }
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };


        panelFondo.setLayout(new BorderLayout());


        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(false); // Hacer el panel principal transparente
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));


        JLabel etiquetaProducto = new JLabel(producto);
        etiquetaProducto.setFont(new Font("Arial", Font.BOLD, 36));
        etiquetaProducto.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Establecer color de fuente en blanco si el producto es "Pine Touch"
        if ("Pine Touch".equals(producto)) {
            etiquetaProducto.setForeground(Color.WHITE);
        }


        panelPrincipal.add(etiquetaProducto);


        if (producto.equals("Pine Mac")) {
            JLabel textoAdicional = new JLabel("Tu espacio se ve mejor con una PineMac.");
            textoAdicional.setFont(new Font("Arial", Font.ITALIC, 18));
            textoAdicional.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Establecer color de fuente en blanco si el producto es "Pine Touch"
            if ("Pine Touch".equals(producto)) {
                textoAdicional.setForeground(Color.WHITE);
            }


            panelPrincipal.add(textoAdicional);
        }


        JLabel etiquetaDescripcion = new JLabel("<html><div style='width: 400px; text-align: center;'>" + descripcion + "</div></html>");
        etiquetaDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaDescripcion.setHorizontalAlignment(SwingConstants.CENTER); // Centrar horizontalmente el texto
        etiquetaDescripcion.setOpaque(false); // Hacer la etiqueta de descripción transparente
        // Establecer color de fuente en blanco si el producto es "Pine Touch"
        if ("Pine Touch".equals(producto)) {
            etiquetaDescripcion.setForeground(Color.WHITE);
        }


        JButton botonComprar = new JButton("Conocé los modelos");
        botonComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonComprar.setOpaque(false); // Hacer el botón transparente


        panelPrincipal.add(etiquetaDescripcion);
        panelPrincipal.add(botonComprar);


        panelFondo.add(panelPrincipal, BorderLayout.NORTH);


        ventana.add(panelFondo);
        ventana.setVisible(true);
    }
}
