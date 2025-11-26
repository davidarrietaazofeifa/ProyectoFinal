package proyecto;

import javax.swing.JOptionPane;

public class Servicio {

    private String nombre, descripcion, requisitos;
    private Estados estado;
    private int cantidad;
    private double precio;
    Utilities util = new Utilities();
    private static Servicio[] servicios = new Servicio[20];
    private static int contadorServicios = 3;

    public Servicio() {
    }

    public Servicio(String nombre, String descripcion, String requisitos, Estados estado, int cantidad, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.estado = estado;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void agregarServicio() {

        if (contadorServicios >= servicios.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más usuarios (máximo 20).");
            return;
        }

        nombre = JOptionPane.showInputDialog(null, "Digite el nombre del servicio:");
        descripcion = JOptionPane.showInputDialog(null, "Digite la descripción del servicio:");
        requisitos = JOptionPane.showInputDialog(null, "Digite los requisitos del servicio:");
        estado = util.seleccionarEstado();
        cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite la cantidad de servicios disponibles:"));
        precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite el precio del servicio:"));

        if (nombre != null && !nombre.trim().isEmpty()
                && descripcion != null && !descripcion.trim().isEmpty() && requisitos != null && !requisitos.trim().isEmpty()) {
            servicios[contadorServicios] = new Servicio(nombre, descripcion, requisitos, estado, cantidad, precio);
            contadorServicios++;

            JOptionPane.showMessageDialog(null, "Servicio agregado exitosamente.");

        } else {
            JOptionPane.showMessageDialog(null, "Servicio no agregado.");
        }
    }

    public void editarServicio() {
        if (contadorServicios == 0) {
            JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
            return;
        }
        consultarServicios();
        int indice = Integer.parseInt(JOptionPane.showInputDialog("Digite el número del servicio que desea editar:")) - 1;
        if (indice < 0 || indice >= contadorServicios) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
        } else {
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione el dato a editar:",
                    "Datos",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"Nombre", "Descripción", "Requisitos", "Cantidad", "Precio"},
                    "Nombre");
            switch (opcion) {
                case 0: {
                    if (nombre != null && !nombre.trim().isEmpty()) {
                        servicios[indice].setNombre(JOptionPane.showInputDialog("Digite el nuevo nombre del servicio:"));
                        JOptionPane.showMessageDialog(null, "Nombre editado exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Nombre no editado.");
                        return;
                    }
                }
                case 1: {
                    if (descripcion != null && !descripcion.trim().isEmpty()) {
                        servicios[indice].setDescripcion(JOptionPane.showInputDialog("Digite la nueva descripción del servicio:"));
                        JOptionPane.showMessageDialog(null, "Descripción editada exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Descripción no editada.");
                        return;
                    }
                }
                case 2: {
                    if (requisitos != null && !requisitos.trim().isEmpty()) {
                        servicios[indice].setRequisitos(JOptionPane.showInputDialog("Digite los nuevos requisitos del servicio:"));
                        JOptionPane.showMessageDialog(null, "Requisitos editados exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Requisitos no editados.");
                    }
                }
                case 3: {
                    if (String.valueOf(cantidad) != null && !String.valueOf(cantidad).trim().isEmpty()) {
                        servicios[indice].setCantidad(Integer.parseInt(JOptionPane.showInputDialog("Digite la nueva cantidad del servicio:")));
                        JOptionPane.showMessageDialog(null, "Cantidad editada exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Cantidad no editada.");
                    }
                }
                case 4: {
                    if (String.valueOf(precio) != null && !String.valueOf(precio).trim().isEmpty()) {
                        servicios[indice].setPrecio(Double.parseDouble(JOptionPane.showInputDialog("Digite el nuevo precio del servicio:")));
                        JOptionPane.showMessageDialog(null, "Precio editado exitosamente.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Precio no editado.");
                    }
                }
            }
        }
    }

    public void consultarServicios() {
        if (contadorServicios == 0) {
            JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
            return;
        }

        String lista = "Servicios registrados:\n\n";
        for (int i = 0; i < contadorServicios; i++) {
            lista += (i + 1)
                    + ". Nombre:" + servicios[i].nombre
                    + " (Estado:" + servicios[i].estado
                    + ") - Descripción:" + servicios[i].descripcion
                    + " - Requisitos:" + servicios[i].requisitos
                    + " - Cantidad:" + servicios[i].cantidad
                    + " - Precio:₡" + servicios[i].precio + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void inactivarServicio() {
        if (contadorServicios == 0) {
            JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
            return;
        }

        consultarServicios();
        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del servicio a cambiar estado:")
        ) - 1;

        if (indice < 0 || indice >= contadorServicios) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Servicio s = servicios[indice];

        if (s.getEstado() == Estados.Activo) {

            if (Pedido.existePedidoActivoPorServicio(s.getNombre())) {
                JOptionPane.showMessageDialog(null, "No se puede inactivar este servicio porque está vinculado a pedidos activos.");
                return;
            }
            s.setEstado(Estados.Inactivo);
        } else {
            s.setEstado(Estados.Activo);
        }
        JOptionPane.showMessageDialog(null, "Estado cambiado correctamente.");
    }

    public String[] getNombresServicios() {

        int contador = 0;

        for (int i = 0; i < servicios.length; i++) {
            if (servicios[i] != null) {
                contador++;
            }
        }

        String[] nombres = new String[contador];

        int j = 0;
        for (int i = 0; i < servicios.length; i++) {
            if (servicios[i] != null) {
                nombres[j] = servicios[i].getNombre();
                j++;
            }
        }
        return nombres;
    }

    public static Servicio getServicioPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < contadorServicios; i++) {
            if (servicios[i] != null && nombre.equals(servicios[i].getNombre())) {
                return servicios[i];
            }
        }
        return null;
    }

    public void serviciosIniciales() {
        servicios[0] = new Servicio("Reparacion", "Reparacion general", "La pieza a reparar", Estados.Activo, 10, 15000);
        servicios[1] = new Servicio("Grabado", "Grabado general", "La pieza a grabar", Estados.Activo, 10, 10000);
        servicios[2] = new Servicio("Empaques", "Grabado especiales", "La pieza a empacar", Estados.Activo, 10, 5000);
    }

}
