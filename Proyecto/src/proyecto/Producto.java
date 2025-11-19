package proyecto;

import javax.swing.JOptionPane;

public class Producto {

    private String identificacion;
    private String nombre;
    private String tipo;
    private String material;
    private Cliente cliente;
    private Estados estado;

    private static Producto[] productos = new Producto[20];
    private static int contadorProductos = 5;

    Utilities util = new Utilities();

    public Producto() {
    }

    public Producto(String identificacion, String nombre, String tipo, String material, Cliente cliente, Estados estado) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.material = material;
        this.cliente = cliente;
        this.estado = estado;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public void agregarProducto() {

        if (contadorProductos >= productos.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más productos (máximo 20).");
            return;
        }

        identificacion = JOptionPane.showInputDialog(null, "Digite el número identificador del producto:");
        nombre = JOptionPane.showInputDialog(null, "Digite el nombre del producto:");
        tipo = JOptionPane.showInputDialog(null, "Digite el tipo de producto:");
        material = JOptionPane.showInputDialog(null, "Digite el material del producto:");

        Estados estado = util.seleccionarEstado();

        int indiceCliente = Cliente.seleccionarCliente();
        if (indiceCliente == -1) {
            JOptionPane.showMessageDialog(null, "Producto no agregado (no seleccionó cliente).");
            return;
        }

        Cliente cli = Cliente.getCliente(indiceCliente);
        if (cli == null) {
            JOptionPane.showMessageDialog(null, "Producto no agregado (cliente inválido).");
            return;
        }

        if (identificacion == null || identificacion.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Producto no agregado (datos incompletos).");
            return;
        }

        Producto nuevo = new Producto(identificacion, nombre, tipo, material, cli, estado);
        productos[contadorProductos] = nuevo;
        contadorProductos++;

        JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
    }

    public void listarProductos() {
        if (contadorProductos == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            return;
        }

        String lista = "Productos registrados:\n\n";
        for (int i = 0; i < contadorProductos; i++) {
            Producto p = productos[i];
            lista += (i + 1) + ". " + p.getNombre()
                    + " - ID: " + p.getIdentificacion()
                    + " - Estado: " + p.getEstado() + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void consultarProductos() {
        if (contadorProductos == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            return;
        }
        String lista = "Productos registrados:\n\n";
        for (int i = 0; i < contadorProductos; i++) {
            Producto p = productos[i];
            String nombreCliente = (p.getCliente() != null)
                    ? p.getCliente().getNombre() + " " + p.getCliente().getApellidos()
                    : "Sin cliente asignado";

            lista += (i + 1) + ". "
                    + "ID: " + p.getIdentificacion()
                    + " - Nombre: " + p.getNombre()
                    + " - Tipo: " + p.getTipo()
                    + " - Material: " + p.getMaterial()
                    + " - Cliente: " + nombreCliente
                    + " - Estado: " + p.getEstado()
                    + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    public void editarProducto() {
        if (contadorProductos == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            return;
        }

        listarProductos();

        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del producto a editar:")
        ) - 1;

        if (indice < 0 || indice >= contadorProductos) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Producto p = productos[indice];

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el dato que desea editar",
                "Editar producto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Identificación", "Nombre", "Tipo", "Material", "Cliente"},
                "Nombre"
        );

        switch (opcion) {
            case 0: {
                String nuevaId = JOptionPane.showInputDialog("Identificación:", p.getIdentificacion());
                if (nuevaId != null && !nuevaId.trim().isEmpty()) {
                    p.setIdentificacion(nuevaId);
                    JOptionPane.showMessageDialog(null, "Identificación editada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Identificación no editada.");
                }
                break;
            }
            case 1: {
                String nuevoNombre = JOptionPane.showInputDialog("Nombre:", p.getNombre());
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    p.setNombre(nuevoNombre);
                    JOptionPane.showMessageDialog(null, "Nombre editado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre no editado.");
                }
                break;
            }
            case 2: {
                String nuevoTipo = JOptionPane.showInputDialog("Tipo:", p.getTipo());
                if (nuevoTipo != null && !nuevoTipo.trim().isEmpty()) {
                    p.setTipo(nuevoTipo);
                    JOptionPane.showMessageDialog(null, "Tipo editado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Tipo no editado.");
                }
                break;
            }
            case 3: {
                String nuevoMaterial = JOptionPane.showInputDialog("Material:", p.getMaterial());
                if (nuevoMaterial != null && !nuevoMaterial.trim().isEmpty()) {
                    p.setMaterial(nuevoMaterial);
                    JOptionPane.showMessageDialog(null, "Material editado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Material no editado.");
                }
                break;
            }
            case 4: {
                int indiceCliente = Cliente.seleccionarCliente();
                if (indiceCliente >= 0) {
                    Cliente nuevoCliente = Cliente.getCliente(indiceCliente);
                    if (nuevoCliente != null) {
                        p.setCliente(nuevoCliente);
                        JOptionPane.showMessageDialog(null, "Cliente del producto editado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no válido. No se realizó el cambio.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente no editado.");
                }
                break;
            }
        }
    }

    public void inactivarProducto() {
        if (contadorProductos == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            return;
        }

        listarProductos();

        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del producto a cambiar estado:")
        ) - 1;

        if (indice < 0 || indice >= contadorProductos) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Producto p = productos[indice];

        if (p.getEstado() == Estados.Activo) {
            p.setEstado(Estados.Inactivo);
        } else {
            p.setEstado(Estados.Activo);
        }

        JOptionPane.showMessageDialog(null, "Estado del producto cambiado correctamente.");
    }

    public String[] getNombresProductos() {

        int contador = 0;

        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                contador++;
            }
        }

        String[] nombres = new String[contador];

        int j = 0;
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                nombres[j] = productos[i].getNombre();
                j++;
            }
        }
        return nombres;
    }

    public static Producto getProducto(int index) {
        if (index >= 0 && index < productos.length) {
            return productos[index];
        }
        return null;
    }

    public static int getContadorProductos() {
        return contadorProductos;
    }

    public void productosIniciales() {
        productos[0] = new Producto("P001", "Anillo", "Accesorio", "Oro",
                new Cliente("Ana", "Lopez", "San José", "Calle 1", "8888-1111", "ana@gmail.com",
                        "anaLo", "12345678", Estados.Activo),
                Estados.Activo
        );

        productos[1] = new Producto(
                "P002", "Collar", "Accesorio", "Plata",
                new Cliente("Luis", "Ramírez", "Cartago", "Avenida 3", "8888-2222", "luis@gmail.com",
                        "luisRa", "12345678", Estados.Activo),
                Estados.Activo
        );

        productos[2] = new Producto(
                "P003", "Pulsera", "Accesorio", "Acero",
                new Cliente("María", "Gómez", "Heredia", "Boulevard 5", "8888-3333", "maria@gmail.com",
                        "mariaGo", "12345678", Estados.Activo),
                Estados.Activo
        );

        productos[3] = new Producto(
                "P004", "Aretes", "Accesorio", "Plata",
                new Cliente("Pedro", "Torres", "Alajuela", "Ruta 2", "8888-4444", "pedro@gmail.com",
                        "pedroTo", "12345678", Estados.Activo),
                Estados.Activo
        );

        productos[4] = new Producto(
                "P005", "Personalizados", "Especial", "Material variado",
                new Cliente("Sofía", "Vega", "Puntarenas", "Camino 9", "8888-5555", "sofia@gmail.com",
                        "sofiaVe", "12345678", Estados.Activo),
                Estados.Activo
        );
    }
}
