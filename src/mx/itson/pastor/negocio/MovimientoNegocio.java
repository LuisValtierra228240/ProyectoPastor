package mx.itson.pastor.negocio;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import mx.itson.pastor.entidades.Cuenta;
import mx.itson.pastor.entidades.Movimiento;
import mx.itson.pastor.enumeradores.TipoMovimiento;
import mx.itson.pastor.persistencia.Conexion;
import mx.itson.pastor.persistencia.MovimientoDAO;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class MovimientoNegocio {

    public static boolean guardar(String concepto, String fecha, double importe, TipoMovimiento tipo, Cuenta cuenta) {
        boolean resultado = false;
        try {
            resultado = MovimientoDAO.guardar(concepto, fecha, importe, tipo, cuenta);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return resultado;
    }

    public static DefaultTableModel llenarTabla(Cuenta cuenta, DefaultTableModel modelo) {
        List<Movimiento> movimientos = MovimientoDAO.obtenerPorCuenta(cuenta);
        double subtotal = 0.0;
        for (Movimiento m : movimientos) {
            if (m.getTipo() == TipoMovimiento.ABONO) {
                subtotal += m.getImporte();
                modelo.addRow(new Object[]{m.getConcepto(), m.getFecha(), 0, m.getImporte(), subtotal});
            } else if (m.getTipo() == TipoMovimiento.CARGO) {
                subtotal -= m.getImporte();
                modelo.addRow(new Object[]{m.getConcepto(), m.getFecha(), m.getImporte(), 0, subtotal});
            }
        }
        return modelo;
    }
    
    public static void imprimir(Cuenta cuenta){
        try {
            Connection connection = Conexion.obtener();
           File reporte = new File("src/mx/itson/pastor/reportes/Reporte.jasper");
                Map parametros = new HashMap();
                parametros.put("id", cuenta.getId());
                JasperPrint print = JasperFillManager.fillReport(reporte.getPath(), parametros, connection);
                JasperViewer viewer = new JasperViewer(print, false);
                viewer.setVisible(true); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
