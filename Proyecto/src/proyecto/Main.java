package proyecto;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        Servicio s = new Servicio();
        s.serviciosIniciales();
        Producto p = new Producto();
        p.productosIniciales();
        
        SubMenus sb = new SubMenus();
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "BRILLO NATURAL - II Avance\n"
                    +"MENU PRINCIPAL\n\n"
                    + "1. Gestión de Usuario\n"
                    + "2. Gestión de Productos\n"
                    + "3. Gestión de Servicios\n"
                    + "4. Gestión de Pedidos\n"
                    + "5. Salir\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1:
                    sb.subMenuUsuarios();
                    break;
                case 2:
                    sb.subMenuProductos();
                    break;
                case 3:
                    sb.subMenuServicios();
                    break;
                case 4:
                    sb.subMenuPedidos();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }

        } while (opcion != 5);
    }
}
