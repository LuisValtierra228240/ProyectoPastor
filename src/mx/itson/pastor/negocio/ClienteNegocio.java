package mx.itson.pastor.negocio;

import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cliente;
import mx.itson.pastor.persistencia.ClienteDAO;

public class ClienteNegocio {

    public static boolean guardar(String nombre, String direccion, String telefono, String email) {
        boolean resultado = false;
        try {
            if (!ClienteDAO.verificarExistencia(email)) {
                resultado = ClienteDAO.guardar(nombre, direccion, telefono, email);
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return resultado;
    }

    public static List<Cliente> cargar() {
        boolean resultado = false;
        List<Cliente> clientes = new ArrayList<>();
        try {
            clientes = ClienteDAO.obtenerTodos();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return clientes;
    }
}
