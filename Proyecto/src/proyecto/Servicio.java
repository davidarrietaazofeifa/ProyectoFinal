package proyecto;

import javax.swing.JOptionPane;

public class Servicio {

    private String nombre, descripcion, requisitos;
    private Estados estado;
    Utilities util = new Utilities();
    private static Servicio[] servicios = new Servicio[20];
    private static int contadorServicios = 3;

    public Servicio() {
    }

    public Servicio(String nombre, String descripcion, String requisitos, Estados estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.estado = estado;
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

    public void agregarServicio() {

        if (contadorServicios >= servicios.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más usuarios (máximo 20).");
            return;
        }

        nombre = JOptionPane.showInputDialog(null, "Digite el nombre del servicio:");
        descripcion = JOptionPane.showInputDialog(null, "Digite la descripción del servicio:");
        requisitos = JOptionPane.showInputDialog(null, "Digite los requisitos del servicio:");
        estado = util.seleccionarEstado();

        if (nombre != null && !nombre.trim().isEmpty()
                && descripcion != null && !descripcion.trim().isEmpty() && requisitos != null && !requisitos.trim().isEmpty()) {
            servicios[contadorServicios] = new Servicio(nombre, descripcion, requisitos, estado);
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
                    null, new Object[]{"Nombre", "Descripción", "Requisitos"},
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
            lista += (i + 1) + ". " + servicios[i].nombre + " (" + servicios[i].estado + ") - " + servicios[i].descripcion + " - " + servicios[i].requisitos + "\n";
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

    public void serviciosIniciales() {
        servicios[0] = new Servicio("Reparacion", "Reparacion general", "La pieza a reparar", Estados.Activo);
        servicios[1] = new Servicio("Grabado", "Grabado general", "La pieza a grabar", Estados.Activo);
        servicios[2] = new Servicio("Empaques", "Grabado especiales", "La pieza a empacar", Estados.Activo);
    }

}
