package proyecto;

import javax.swing.JOptionPane;

public class Usuario {

    private String nombreCompleto, nickname, password;
    private Estados estado;
    private TiposUsuario tiposUsuario;
    private TiposUsuario[] tiposUsuarios = TiposUsuario.values();
    Utilities util = new Utilities();

    private static Usuario[] usuarios = new Usuario[20];
    private static int contadorUsuarios = 0;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String nickname, String password,
            Estados estado, TiposUsuario tiposUsuario) {
        this.nombreCompleto = nombreCompleto;
        this.nickname = nickname;
        this.password = password;
        this.estado = estado;
        this.tiposUsuario = tiposUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public TiposUsuario getTiposUsuario() {
        return tiposUsuario;
    }

    public void setTiposUsuario(TiposUsuario tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }

    public void agregarUsuario() {

        if (contadorUsuarios >= usuarios.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más usuarios (máximo 20).");
            return;
        }

        tiposUsuario = seleccionarTipoUsuario();

        if (tiposUsuario == TiposUsuario.Cliente) {

            nombreCompleto = JOptionPane.showInputDialog(null, "Digite el nombre completo del cliente:");

            if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre completo obligatorio.");
                return;
            }

            nombreCompleto = nombreCompleto.trim();

            int posEspacio = nombreCompleto.indexOf(" ");
            String nombre;
            String apellidos;

            if (posEspacio == -1) {
                nombre = nombreCompleto;
                apellidos = "";
            } else {
                nombre = nombreCompleto.substring(0, posEspacio);
                apellidos = nombreCompleto.substring(posEspacio + 1);
            }

            String ciudad = JOptionPane.showInputDialog(null, "Digite la ciudad del cliente:");
            String direccion = JOptionPane.showInputDialog(null, "Digite la dirección del cliente:");
            String telefono = JOptionPane.showInputDialog(null, "Digite el teléfono del cliente:");
            String correo = JOptionPane.showInputDialog(null, "Digite el correo del cliente:");

            nickname = JOptionPane.showInputDialog(null, "Digite el nickname del usuario:");

            boolean passwordValido;
            do {
                password = JOptionPane.showInputDialog("Digite un password mínimo de 8 caracteres:");
                passwordValido = validacionPasswordMinimo(password);

                if (!passwordValido) {
                    JOptionPane.showMessageDialog(null, "El password no cumple con los 8 caracteres mínimos.");
                }

            } while (!passwordValido);

            estado = util.seleccionarEstado();

            if (nombre != null && !nombre.trim().isEmpty()
                    && nickname != null && !nickname.trim().isEmpty()) {

                Cliente nuevoCliente = new Cliente(
                        nombre, apellidos, ciudad, direccion,
                        telefono, correo,
                        nickname, password, estado
                );

                usuarios[contadorUsuarios] = nuevoCliente;

                boolean registrado = Cliente.registrarCliente(nuevoCliente);

                if (!registrado) {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el cliente (límite de clientes alcanzado).");
                    usuarios[contadorUsuarios] = null;
                    return;
                }

                contadorUsuarios++;

                JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.");

            } else {
                JOptionPane.showMessageDialog(null, "Usuario no agregado (datos incompletos).");
            }

            return;
        }

        nombreCompleto = JOptionPane.showInputDialog(null, "Digite el nombre completo del usuario:");
        nickname = JOptionPane.showInputDialog(null, "Digite el nickname del usuario:");

        boolean passwordValido;
        do {
            password = JOptionPane.showInputDialog("Digite un password mínimo de 8 caracteres:");
            passwordValido = validacionPasswordMinimo(password);

            if (!passwordValido) {
                JOptionPane.showMessageDialog(null, "El password no cumple con los 8 caracteres mínimos.");
            }

        } while (!passwordValido);

        estado = util.seleccionarEstado();

        if (nombreCompleto != null && !nombreCompleto.trim().isEmpty()
                && nickname != null && !nickname.trim().isEmpty()) {

            usuarios[contadorUsuarios] = new Usuario(
                    nombreCompleto, nickname, password, estado, tiposUsuario
            );
            contadorUsuarios++;

            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.");

        } else {
            JOptionPane.showMessageDialog(null, "Usuario no agregado.");
        }
    }

    public void consultarUsuarios() {
        if (contadorUsuarios == 0) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados.");
            return;
        }

        String lista = "Usuarios registrados:\n\n";
        for (int i = 0; i < contadorUsuarios; i++) {
            lista += (i + 1) + ". " + usuarios[i].nombreCompleto
                    + " (" + usuarios[i].estado + ") - "
                    + usuarios[i].tiposUsuario + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void inactivarUsuario() {
        if (contadorUsuarios == 0) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados.");
            return;
        }

        consultarUsuarios();
        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del usuario a cambiar estado:")
        ) - 1;

        if (indice < 0 || indice >= contadorUsuarios) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Usuario u = usuarios[indice];

        if (u.estado == Estados.Activo) {

            if (u.DatosRelacionados()) {
                JOptionPane.showMessageDialog(null,"No se puede inactivar este usuario porque tiene datos relacionados en otros catálogos del sistema.");
                return;
            }
            u.estado = Estados.Inactivo;
        } else {
            u.estado = Estados.Activo;
        }

        JOptionPane.showMessageDialog(null, "Estado cambiado correctamente.");
    }

    public boolean validacionPasswordMinimo(String password) {
        return password != null && password.length() >= 8;
    }

    public boolean DatosRelacionados() {

        if (this.tiposUsuario == TiposUsuario.Cliente) {

            for (int i = 0; i < Pedido.getContadorPedidos(); i++) {
                Pedido p = Pedido.getPedido(i);

                if (p != null && p.getCliente() != null) {

                    if (p.getCliente().getNickname().equals(this.nickname)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public TiposUsuario seleccionarTipoUsuario() {
        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el tipo de usuario:",
                "Tipo de Usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tiposUsuarios,
                tiposUsuarios[0]
        );
        if (opcion >= 0 && opcion < tiposUsuarios.length) {
            return tiposUsuarios[opcion];
        }
        return TiposUsuario.Cliente;
    }
}
