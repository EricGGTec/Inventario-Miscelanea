/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author lore0
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    //Funciona para Zaid
    private static final String URL = "jdbc:mysql://localhost:3306/miselanea?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "5691323Zvbcjc";

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver actualizado
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConexion();
        if (conn != null) {
            System.out.println("Conexión lista para usarse.");
        } else {
            System.out.println("Error al conectar.");
        }
    }
}
