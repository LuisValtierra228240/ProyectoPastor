package mx.itson.pastor.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cliente;
import mx.itson.pastor.entidades.Cuenta;

public class CuentaDAO {

    public static List<Cuenta> obtenerTodos() {
        List<Cuenta> cuentas = new ArrayList<>();
        try {
            Connection connection = Conexion.obtener();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT cu.id, cu.numero, cl.id, cl.nombre, cl.direccion, cl.telefono, cl.email FROM cuenta cu INNER JOIN cliente cl ON cu.idCliente = cl.id;");
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();

                cuenta.setId(resultSet.getInt(1));
                cuenta.setNumero(resultSet.getString(2));

                Cliente c = new Cliente();

                c.setId(resultSet.getInt(3));
                c.setNombre(resultSet.getString(4));
                c.setDireccion(resultSet.getString(5));
                c.setTelefono(resultSet.getString(6));
                c.setEmail(resultSet.getString(7));

                cuenta.setCliente(c);

                cuentas.add(cuenta);
            }
        } catch (Exception e) {
            System.err.print("Ocurrió un error: " + e.getMessage());
        }
        return cuentas;
    }

    public static boolean guardar(String numero, Cliente cliente) {
        boolean resultado = false;
        try {
            Connection connection = Conexion.obtener();
            String consulta = "INSERT INTO cuenta (numero, idCliente) values (?, ?);";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, numero);
            statement.setInt(2, cliente.getId());

            statement.execute();

            resultado = statement.getUpdateCount() == 1;
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
        return resultado;
    }

    public static boolean verificarExistencia(String numero) {
        boolean existencia = false;
        try {
            Connection connection = Conexion.obtener();
            String consulta = "SELECT numero FROM cuenta WHERE numero = ?;";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, numero);

            ResultSet resultSet = statement.executeQuery();
            existencia = resultSet.next();
        } catch (Exception e) {
            System.err.print("Ha ocurrido un error: " + e.getMessage());
        }
        return existencia;
    }
}
