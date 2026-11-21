package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Valores por defecto de la máquina virtual de clase; se pueden cambiar con variables de entorno.
    private static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://192.168.56.101:3306/employees");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "user");
    private static final String PASS = System.getenv().getOrDefault("DB_PASS", "user");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
