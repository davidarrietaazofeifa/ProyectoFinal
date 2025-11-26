package proyecto;

import javax.swing.JOptionPane;

public class Producto {

    private String identificacion;
    private String nombre;
    private String tipo;
    private String material;
    private int cantidad;
    private double precio;
    private Estados estado;

    private static Producto[] productos = new Producto[20];
    private static int contadorProductos = 5;

    Utilities util = new Utilities();

    public Producto() {
    }

    public Producto(String identificacion, String nombre, String tipo, String material, int cantidad, double precio, Estados estado) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.material = material;
        this.cantidad = cantidad;
        this.precio = precio;
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
        cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite la cantidad de productos disponibles:"));
        precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite el precio del producto:"));

        Estados estado = util.seleccionarEstado();

        if (identificacion == null || identificacion.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Producto no agregado (datos incompletos).");
            return;
        }

        Producto nuevo = new Producto(identificacion, nombre, tipo, material, cantidad, precio, estado);
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
            lista += (i + 1) + ". "
                    + "ID: " + p.getIdentificacion()
                    + " - Nombre: " + p.getNombre()
                    + " - Tipo: " + p.getTipo()
                    + " - Material: " + p.getMaterial()
                    + " - Estado: " + p.getEstado()
                    + " - Cantidad:" + p.cantidad
                    + " - Precio:₡" + p.precio + "\n";
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

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el dato que desea editar",
                "Editar producto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Identificacion", "Nombre", "Tipo", "Material", "Cantidad", "Precio"},
                "Identificacion"
        );
        Producto p = productos[indice];
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
                if (String.valueOf(cantidad) != null && !String.valueOf(cantidad).trim().isEmpty()) {
                    productos[indice].setCantidad(Integer.parseInt(JOptionPane.showInputDialog("Digite la nueva cantidad del producto:")));
                    JOptionPane.showMessageDialog(null, "Cantidad editada exitosamente.");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad no editada.");
                }
            }
            case 5: {
                if (String.valueOf(precio) != null && !String.valueOf(precio).trim().isEmpty()) {
                    productos[indice].setPrecio(Double.parseDouble(JOptionPane.showInputDialog("Digite el nuevo precio del producto:")));
                    JOptionPane.showMessageDialog(null, "Precio editado exitosamente.");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Precio no editado.");
                }
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

    public static Producto getProductoPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && nombre.equals(productos[i].getNombre())) {
                return productos[i];
            }
        }
        return null;
    }

    public void productosIniciales() {
        productos[0] = new Producto("P001", "Anillo", "Accesorio", "Oro", 10, 8000, Estados.Activo);
        productos[1] = new Producto("P002", "Collar", "Accesorio", "Plata", 4, 20000, Estados.Activo);
        productos[2] = new Producto("P003", "Pulsera", "Accesorio", "Acero", 8, 12000, Estados.Activo);
        productos[3] = new Producto("P004", "Aretes", "Accesorio", "Plata", 15, 5000, Estados.Activo);
        productos[4] = new Producto("P005", "Personalizados", "Especial", "Material variado", 5, 25000, Estados.Activo);
    }
}
