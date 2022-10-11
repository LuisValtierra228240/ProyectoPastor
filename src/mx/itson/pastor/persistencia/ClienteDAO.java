package mx.itson.pastor.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cliente;

public class ClienteDAO {

    public static List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            Connection connection = Conexion.obtener();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, nombre, direccion, telefono, email FROM cliente;");
            while (resultSet.next()) {
                Cliente c = new Cliente();

                c.setId(resultSet.getInt(1));
                c.setNombre(resultSet.getString(2));
                c.setDireccion(resultSet.getString(3));
                c.setTelefono(resultSet.getString(4));
                c.setEmail(resultSet.getString(5));

                clientes.add(c);
            }
        } catch (Exception e) {
            System.err.print("Ocurrió un error: " + e.getMessage());
        }
        return clientes;
    }

    public static boolean guardar(String nombre, String direccion, String telefono, String email) {
        boolean resultado = false;
        try {
            Connection connection = Conexion.obtener();
            String consulta = "INSERT INTO cliente (nombre, direccion, telefono, email) values (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, email);

            statement.execute();

            resultado = statement.getUpdateCount() == 1;
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
        return resultado;
    }

    public static boolean verificarExistencia(String email) {
        boolean existencia = false;
        try {
            Connection connection = Conexion.obtener();
            String consulta = "SELECT email FROM cliente WHERE email = ?;";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            existencia = resultSet.next();
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
        return existencia;
    }

}
