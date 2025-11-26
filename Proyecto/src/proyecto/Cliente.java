package proyecto;

import javax.swing.JOptionPane;

public class Cliente extends Usuario {

    private String nombre;
    private String apellidos;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;

    private static Cliente[] clientes = new Cliente[20];
    private static int contadorClientes = 0;

    Utilities util = new Utilities();

    public Cliente() {
    }

    public Cliente(String nombre, String apellidos, String ciudad, String direccion,
            String telefono, String correo,
            String nickname, String password, Estados estado) {


        super(nombre + " " + apellidos, nickname, password, estado, TiposUsuario.Cliente);

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public static boolean registrarCliente(Cliente c) {
        if (contadorClientes >= clientes.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más clientes (máximo 20).");
            return false;
        }
        clientes[contadorClientes] = c;
        contadorClientes++;
        return true;
    }

    public void agregarCliente() {
        if (contadorClientes >= clientes.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más clientes (máximo 20).");
            return;
        }

        String nombre = JOptionPane.showInputDialog("Digite el nombre del cliente:");
        String apellidos = JOptionPane.showInputDialog("Digite los apellidos del cliente:");
        String ciudad = JOptionPane.showInputDialog("Digite la ciudad del cliente:");
        String direccion = JOptionPane.showInputDialog("Digite la dirección del cliente:");
        String telefono = JOptionPane.showInputDialog("Digite el teléfono del cliente:");
        String correo = JOptionPane.showInputDialog("Digite el correo del cliente:");

        String nickname = JOptionPane.showInputDialog("Digite el nickname del usuario (cliente):");

        String password;
        boolean passwordValido;
        do {
            password = JOptionPane.showInputDialog("Digite un password mínimo de 8 caracteres:");
            passwordValido = validacionPasswordMinimo(password);
            if (!passwordValido) {
                JOptionPane.showMessageDialog(null, "El password no cumple con los 8 caracteres mínimos.");
            }
        } while (!passwordValido);

        Estados estado = util.seleccionarEstado();

        if (nombre != null && !nombre.trim().isEmpty()
                && apellidos != null && !apellidos.trim().isEmpty()
                && nickname != null && !nickname.trim().isEmpty()) {

            Cliente nuevo = new Cliente(
                    nombre, apellidos, ciudad, direccion,
                    telefono, correo,
                    nickname, password, estado
            );

            clientes[contadorClientes] = nuevo;
            contadorClientes++;

            JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Cliente no agregado (datos incompletos).");
        }
    }

    public void editarCliente() {
        if (contadorClientes == 0) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
            return;
        }

        listarClientes();

        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del cliente a editar:")
        ) - 1;

        if (indice < 0 || indice >= contadorClientes) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Cliente c = clientes[indice];

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el dato que desea editar",
                "Editar cliente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"Nombre", "Apellidos", "Ciudad", "Direccion", "Telefono", "Correo"},
                "Nombre"
        );

        switch (opcion) {
            case 0: {
                String nuevoNombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    c.setNombre(nuevoNombre);
                    c.setNombreCompleto(c.getNombre() + " " + c.getApellidos());
                    JOptionPane.showMessageDialog(null, "Nombre editado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre no editado.");
                }
                break;
            }
            case 1: {
                String nuevosApellidos = JOptionPane.showInputDialog("Apellidos:", c.getApellidos());
                if (nuevosApellidos != null && !nuevosApellidos.trim().isEmpty()) {
                    c.setApellidos(nuevosApellidos);
                    c.setNombreCompleto(c.getNombre() + " " + c.getApellidos());
                    JOptionPane.showMessageDialog(null, "Apellidos editados exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Apellidos no editados.");
                }
                break;
            }
            case 2: {
                String nuevaCiudad = JOptionPane.showInputDialog("Ciudad:", c.getCiudad());
                if (nuevaCiudad != null && !nuevaCiudad.trim().isEmpty()) {
                    c.setCiudad(nuevaCiudad);
                    JOptionPane.showMessageDialog(null, "Ciudad editada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Ciudad no editada.");
                }
                break;
            }
            case 3: {
                String nuevaDireccion = JOptionPane.showInputDialog("Dirección:", c.getDireccion());
                if (nuevaDireccion != null && !nuevaDireccion.trim().isEmpty()) {
                    c.setDireccion(nuevaDireccion);
                    JOptionPane.showMessageDialog(null, "Dirección editada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Dirección no editada.");
                }
                break;
            }
            case 4: {
                String nuevoTelefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
                if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Teléfono editado exitosamente.");
                    c.setTelefono(nuevoTelefono);
                } else {
                    JOptionPane.showMessageDialog(null, "Teléfono no editado.");
                }
                break;
            }
            case 5: {
                String nuevoCorreo = JOptionPane.showInputDialog("Correo:", c.getCorreo());
                if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
                    c.setCorreo(nuevoCorreo);
                    JOptionPane.showMessageDialog(null, "Correo editado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Correo no editado.");
                }
                break;
            }
        }
    }

    public void inactivarCliente() {
        if (contadorClientes == 0) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
            return;
        }

        listarClientes();

        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del cliente a cambiar estado:")
        ) - 1;

        if (indice < 0 || indice >= contadorClientes) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Cliente c = clientes[indice];

        if (c.getEstado() == Estados.Activo) {
            if (Pedido.existePedidoActivoPorCliente(c)) {
                JOptionPane.showMessageDialog(
                        null,
                        "No se puede inactivar el cliente porque tiene pedidos activos."
                );
                return;
            }
            c.setEstado(Estados.Inactivo);
        } else {
            c.setEstado(Estados.Activo);
        }

        JOptionPane.showMessageDialog(null, "Estado del cliente cambiado correctamente.");
    }

    public void listarClientes() {
        String lista = "Clientes registrados:\n\n";
        for (int i = 0; i < contadorClientes; i++) {
            Cliente c = clientes[i];
            lista += (i + 1) + ". " + c.getNombre() + " " + c.getApellidos()
                    + " (" + c.getEstado() + ")\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void consultarClientes() {
        if (contadorClientes == 0) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
            return;
        }

        String lista = "Clientes registrados:\n\n";
        for (int i = 0; i < contadorClientes; i++) {
            Cliente c = clientes[i];
            lista += (i + 1)
                    + ". " + c.getNombre() + " "
                    + c.getApellidos() + " - "
                    + c.getCiudad() + "," + c.getDireccion() + " - "
                    + c.getTelefono() + " - "
                    + c.getCorreo()
                    + " (" + c.getEstado() + ")\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public static Cliente getCliente(int index) {
        if (index >= 0 && index < contadorClientes) {
            return clientes[index];
        }
        return null;
    }

    public static int seleccionarCliente() {
        if (contadorClientes == 0) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
            return -1;
        }

        String lista = "Seleccione un cliente:\n\n";
        for (int i = 0; i < contadorClientes; i++) {
            Cliente c = clientes[i];
            lista += (i + 1) + ". " + c.getNombre() + " " + c.getApellidos() + "\n";
        }

        String input = JOptionPane.showInputDialog(lista + "\nDigite el número del cliente:");
        int opcion = Integer.parseInt(input);

        return opcion - 1;
    }
}
