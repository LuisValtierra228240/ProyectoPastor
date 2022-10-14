package mx.itson.pastor.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cuenta;
import mx.itson.pastor.entidades.Movimiento;
import mx.itson.pastor.enumeradores.TipoMovimiento;

public class MovimientoDAO {

    public static List<Movimiento> obtenerTodos() {
        List<Movimiento> movimientos = new ArrayList<>();
        try {
            Connection connection = Conexion.obtener();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT mo.id, mo.concepto, mo.fecha, mo.importe, mo.tipo, cu.id, cu.numero FROM movimiento mo INNER JOIN cuenta cu ON mo.idCuenta = cu.id ORDER BY mo.fecha;");
            while (resultSet.next()) {
                Movimiento m = new Movimiento();

                m.setId(resultSet.getInt(1));
                m.setConcepto(resultSet.getString(2));
                m.setFecha(resultSet.getDate(3));
                m.setImporte(resultSet.getDouble(4));
                m.setTipo(TipoMovimiento.valueOf(resultSet.getString(5).toUpperCase()));

                Cuenta c = new Cuenta();

                c.setId(resultSet.getInt(6));
                c.setNumero(resultSet.getString(7));

                movimientos.add(m);
            }
        } catch (Exception e) {
            System.err.print("Ocurrió un error: " + e.getMessage());
        }
        return movimientos;
    }

    public static boolean guardar(String concepto, String fecha, double importe, TipoMovimiento tipo, Cuenta cuenta) {
        boolean resultado = false;
        try {
            Connection connection = Conexion.obtener();
            String consulta = "INSERT INTO movimiento (concepto, fecha, importe, tipo, idCuenta) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, concepto);
            statement.setString(2, fecha);
            statement.setDouble(3, importe);
            statement.setString(4, tipo.toString());
            statement.setInt(5, cuenta.getId());

            statement.execute();

            resultado = statement.getUpdateCount() == 1;
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
        return resultado;
    }
}
