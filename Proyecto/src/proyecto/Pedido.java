package proyecto;

import javax.swing.JOptionPane;

public class Pedido {

    private String fecha, item;
    private int cantidad;
    private Estados estado;
    private Utilities util = new Utilities();
    private Servicio servicio = new Servicio();
    private Producto producto = new Producto();
    private static Pedido[] pedidos = new Pedido[20];
    private static int contadorPedidos = 0;

    public Pedido() {
    }

    public Pedido(String fecha, String item, int cantidad, Estados estado) {
        this.fecha = fecha;
        this.item = item;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public void agregarPedido() {
        if (contadorPedidos >= pedidos.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más pedidos (máximo 20).");
            return;
        }

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el tipo de pedido que desea realizar:",
                "Pedidos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"Servicio", "Producto"},
                "Servicio");

        switch (opcion) {
            case 0: {
                item = String.valueOf(seleccionarServicio());
                break;
            }
            case 1: {
                item = String.valueOf(seleccionarProducto());
                break;
            }
        }

        fecha = JOptionPane.showInputDialog(null, "Digite la fecha del pedido:");
        cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite la cantidad:"));
        estado = util.seleccionarEstado();

        if (fecha != null && !fecha.trim().isEmpty()) {
            pedidos[contadorPedidos] = new Pedido(fecha, item, cantidad, estado);
            contadorPedidos++;

            JOptionPane.showMessageDialog(null, "Pedido agregado exitosamente.");

        } else {
            JOptionPane.showMessageDialog(null, "Pedido no agregado.");
        }
    }

    public void editarPedido() {
        if (contadorPedidos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
            return;
        }
        consultarPedidos();
        int indice = Integer.parseInt(JOptionPane.showInputDialog("Digite el número de pedido que desea editar:")) - 1;
        if (indice < 0 || indice >= contadorPedidos) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
        } else {
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione el dato a editar:",
                    "Datos",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"Item", "Cantidad", "Fecha"},
                    "Item");
            switch (opcion) {
                case 0: {
                    if (item != null && !item.trim().isEmpty()) {
                        opcion = JOptionPane.showOptionDialog(
                                null,
                                "Seleccione el tipo de pedido que desea realizar:",
                                "Pedidos",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, new Object[]{"Servicio", "Producto"},
                                "Servicio");

                        switch (opcion) {
                            case 0: {
                                item = String.valueOf(seleccionarServicio());
                                pedidos[indice].setItem(item);
                                JOptionPane.showMessageDialog(null, "Item editado exitosamente.");
                                break;
                            }
                            case 1: {
                                item = String.valueOf(seleccionarProducto());
                                pedidos[indice].setItem(item);
                                JOptionPane.showMessageDialog(null, "Item editado exitosamente.");
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Item no editado.");
                    }
                    break;
                }
                case 1: {
                    cantidad = Integer.parseInt(JOptionPane.showInputDialog("Digite la nueva cantidad del pedido:"));
                    if (!String.valueOf(cantidad).trim().isEmpty()) {
                        pedidos[indice].setCantidad(cantidad);
                        JOptionPane.showMessageDialog(null, "Cantidad editada exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Cantidad no editada.");
                        return;
                    }
                }
                case 2: {
                    fecha = JOptionPane.showInputDialog("Digite la nueva fecha del pedido:");
                    if (fecha != null && !fecha.trim().isEmpty()) {
                        pedidos[indice].setFecha(fecha);
                        JOptionPane.showMessageDialog(null, "Fecha editada exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Fecha no editada.");
                        break;
                    }
                }
            }
        }
    }

    public void consultarPedidos() {
        if (contadorPedidos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
            return;
        }

        String lista = "Pedidos registrados:\n\n";
        for (int i = 0; i < contadorPedidos; i++) {
            lista += (i + 1) + ". " + pedidos[i].item + " (" + pedidos[i].estado + ") - " + pedidos[i].cantidad + " - " + pedidos[i].fecha + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void inactivarPedido() {
        if (contadorPedidos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
            return;
        }

        consultarPedidos();
        int indice = Integer.parseInt(JOptionPane.showInputDialog("Digite el número del pedido a cambiar estado:")) - 1;

        if (indice < 0 || indice >= contadorPedidos) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        if (pedidos[indice].estado == Estados.Activo) {
            pedidos[indice].estado = Estados.Inactivo;
        } else {
            pedidos[indice].estado = Estados.Activo;
        }

        JOptionPane.showMessageDialog(null, "Estado cambiado correctamente.");
    }

    public String seleccionarServicio() {

        String[] nombres = servicio.getNombresServicios();

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione un servicio:",
                "Servicios",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombres,
                nombres[0]
        );
        if (opcion >= 0) {
            return nombres[opcion];
        }
        return null;
    }

    public static boolean existePedidoActivoPorServicio(String nombreServicio) {
        if (nombreServicio == null || nombreServicio.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < contadorPedidos; i++) {
            Pedido p = pedidos[i];
            if (p != null
                    && p.getEstado() == Estados.Activo
                    && nombreServicio.equals(p.getItem())) {
                return true;
            }
        }
        return false;
    }

    public String seleccionarProducto() {

        String[] nombres = producto.getNombresProductos();

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione un producto:",
                "Productos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombres,
                nombres[0]
        );
        if (opcion >= 0) {
            return nombres[opcion];
        }
        return null;
    }

}
