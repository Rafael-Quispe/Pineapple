package ventana.pineapple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import Conexion.Conexion;

public class Pineapple {
    public static void main(String[] args) {
        
        
        JFrame ventana = new JFrame("PineApple");

        ventana.setSize(1280, 720);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        
        // Crear el panel de fondo con la imagen de fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("C:\\Users\\gabi\\Documents\\NetBeansProjects\\Pineapple JAVA\\src\\main\\java\\ventana\\pineapple\\images\\pineapple_web.png");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelFondo.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 15, 20, 0);

        JLabel etiquetaTitulo = new JLabel("PINEAPPLE");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaTitulo.setForeground(Color.WHITE);
        panelFondo.add(etiquetaTitulo, gbc);

        gbc.gridy++;
        JLabel etiquetaSubtitulo = new JLabel("Elige una categoría");
        etiquetaSubtitulo.setFont(new Font("Arial", Font.PLAIN, 20));
        etiquetaSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaSubtitulo.setForeground(Color.WHITE);
        panelFondo.add(etiquetaSubtitulo, gbc);
        
        JButton botonCarrito = new JButton("🛒");
        botonCarrito.addActionListener((ActionEvent e) -> {
            VentanaDatosCliente.mostrarVentana();
        });
        panelFondo.add(botonCarrito);
        
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(2, 3));

        String[] nombresProductos = {"Pine Phone", "Pine Touch", "Pine Mac", "Pine Time", "Pods", "Teclados"};

        HashMap<String, String> descripcionesProductos = new HashMap<>();
        descripcionesProductos.put("Pine Phone", "Un potente teléfono inteligente Pineapple con características increíbles.");
        descripcionesProductos.put("Pine Touch", "Una tableta Pineapple con una pantalla táctil de alta resolución.");
        descripcionesProductos.put("Pine Mac", "La computadora portátil Pineapple para profesionales creativos y entusiastas de la tecnología.");
        descripcionesProductos.put("Pine Time", "Pine Time es un elegante reloj inteligente con seguimiento de actividad, notificaciones y una batería de larga duración.");
        descripcionesProductos.put("Pods", "Pods son auriculares inalámbricos con cancelación de ruido, calidad de sonido excepcional y un estuche de carga compacto.");
        descripcionesProductos.put("Teclados", "Los teclados Pineapple son teclados mecánicos premium con retroiluminación RGB, diseño duradero y teclas personalizables para una experiencia de escritura superior.");

        for (String producto : nombresProductos) {
            JButton botonProducto = new JButton(producto);
            botonProducto.addActionListener((ActionEvent e) -> {
                if (producto.equals("Pine Phone") || producto.equals("Pine Touch") || producto.equals("Pine Mac")) {
                    VentanaProducto.mostrarVentana(producto, descripcionesProductos.get(producto));
                } else {
                    String descripcion = descripcionesProductos.get(producto);
                    JOptionPane.showMessageDialog(ventana, "Descripción del producto: " + descripcion);
                }
            });
            panelProductos.add(botonProducto);
        }

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFondo.add(panelProductos, gbc);

        ventana.add(panelFondo);
        ventana.setVisible(true);
        
        //conexiondb.funcion();
    }
}
