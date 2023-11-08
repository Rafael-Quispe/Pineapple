package Conexion;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Conexion {
    
    String bd = "pineapple";
    String url = "jdbc:mysql://localhost:3306/";
    String user="root";   
    String password = "";
    String driver= "com.mysql.cj.jdbc.Driver";
    Connection cx;




    public Conexion(String bd) {
        this.bd=bd;
    }
    
    public Connection Conectar() {
        try {
            Class.forName(driver);
            cx=DriverManager.getConnection(url+bd, user, password);
            System.out.println("Se conecto");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
      return cx;  
    }
    
   public void desconectar() {
        try {
            cx.close();
            System.out.println("Se desconecto");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public static void main(String[] args) {
       Conexion conexion = new Conexion("pineapple");
       conexion.Conectar();
       
   }




   /*public void funcion(){
       try{
       
       } catch (SQLException e) {System.out.println(e);}
   }*/
   
    public PreparedStatement prepareStatement(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }




}
