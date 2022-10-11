package mx.itson.pastor.negocio;

import mx.itson.pastor.entidades.Cliente;
import mx.itson.pastor.persistencia.CuentaDAO;

public class CuentaNegocio {

    public static boolean guardar(String numero, Cliente cliente) {
        boolean resultado = false;
        try {
            if (CuentaDAO.verificarExistencia(numero)) {
                resultado = CuentaDAO.guardar(numero, cliente);
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return resultado;
    }
}
