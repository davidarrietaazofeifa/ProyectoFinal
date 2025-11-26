package proyecto;

import javax.swing.JOptionPane;

public class Pedido {

    private String fecha, item;
    private int cantidad;
    private double subtotal;
    private Cliente cliente;
    private Estados estado;
    private Utilities util = new Utilities();
    private Servicio servicio = new Servicio();
    private Producto producto = new Producto();
    private static Pedido[] pedidos = new Pedido[20];
    private static int contadorPedidos = 0;

    public Pedido() {
    }

    public Pedido(String fecha, String item, int cantidad, double subtotal, Cliente cliente, Estados estado) {
        this.fecha = fecha;
        this.item = item;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.cliente = cliente;
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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

    public void agregarPedido() {
        if (contadorPedidos >= pedidos.length) {
            JOptionPane.showMessageDialog(null, "No es posible agregar más pedidos (máximo 20).");
            return;
        }

        int indiceCliente = Cliente.seleccionarCliente();
        if (indiceCliente == -1) {
            JOptionPane.showMessageDialog(null, "Pedido no agregado (no seleccionó cliente).");
            return;
        }

        Cliente cli = Cliente.getCliente(indiceCliente);
        if (cli == null) {
            JOptionPane.showMessageDialog(null, "Pedido no agregado (cliente inválido).");
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

        if (opcion == 0) {
            String nombreServicio = seleccionarServicio();
            if (nombreServicio == null) {
                JOptionPane.showMessageDialog(null, "Pedido no agregado (no seleccionó servicio).");
                return;
            }

            Servicio servicioSeleccionado = Servicio.getServicioPorNombre(nombreServicio);
            if (servicioSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Error al obtener el servicio seleccionado.");
                return;
            }

            fecha = JOptionPane.showInputDialog(null, "Digite la fecha del pedido:");
            if (fecha == null || fecha.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pedido no agregado (fecha inválida).");
                return;
            }

            int cantSolicitada = Integer.parseInt(
                    JOptionPane.showInputDialog(null, "Digite la cantidad de servicios que desea:")
            );

            int cantDisponible = servicioSeleccionado.getCantidad();

            if (cantSolicitada > cantDisponible) {
                JOptionPane.showMessageDialog(
                        null,
                        "No hay suficientes servicios disponibles.\n"
                        + "Disponibles: " + cantDisponible
                );
                return;
            }

            cantidad = cantSolicitada;
            item = nombreServicio;
            estado = util.seleccionarEstado();

            subtotal = cantidad * servicioSeleccionado.getPrecio();

            servicioSeleccionado.setCantidad(cantDisponible - cantSolicitada);

            pedidos[contadorPedidos] = new Pedido(fecha, item, cantidad, subtotal, cli, estado);
            contadorPedidos++;

            JOptionPane.showMessageDialog(null,
                    "Pedido de servicio agregado exitosamente.\n");
        } else if (opcion == 1) {
            String nombreProducto = seleccionarProducto();
            if (nombreProducto == null) {
                JOptionPane.showMessageDialog(null, "Pedido no agregado (no seleccionó producto).");
                return;
            }

            Producto productoSeleccionado = Producto.getProductoPorNombre(nombreProducto);
            if (productoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Error al obtener el producto seleccionado.");
                return;
            }

            fecha = JOptionPane.showInputDialog(null, "Digite la fecha del pedido:");
            if (fecha == null || fecha.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pedido no agregado (fecha inválida).");
                return;
            }

            int cantSolicitada = Integer.parseInt(
                    JOptionPane.showInputDialog(null, "Digite la cantidad de productos que desea:")
            );

            int cantDisponible = productoSeleccionado.getCantidad();

            if (cantSolicitada > cantDisponible) {
                JOptionPane.showMessageDialog(
                        null,
                        "No hay suficientes productos disponibles.\n"
                        + "Disponibles: " + cantDisponible
                );
                return;
            }

            cantidad = cantSolicitada;
            item = nombreProducto;
            estado = util.seleccionarEstado();

            subtotal = cantidad * productoSeleccionado.getPrecio();

            productoSeleccionado.setCantidad(cantDisponible - cantSolicitada);

            pedidos[contadorPedidos] = new Pedido(fecha, item, cantidad, subtotal, cli, estado);
            contadorPedidos++;

            JOptionPane.showMessageDialog(null,
                    "Pedido de producto agregado exitosamente.\n");
        }
    }

    public void editarPedido() {
        if (contadorPedidos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
            return;
        }

        consultarPedidos();
        int indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número de pedido que desea editar:")
        ) - 1;

        if (indice < 0 || indice >= contadorPedidos) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Pedido p = pedidos[indice];

        int opcion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el dato a editar:",
                "Datos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"Item", "Cantidad", "Fecha", "Cliente"},
                "Item");

        switch (opcion) {

            case 0: {
                String itemActual = p.getItem();
                int cantidadActual = p.getCantidad();

                Servicio servicioActual = Servicio.getServicioPorNombre(itemActual);
                Producto productoActual = null;
                if (servicioActual == null) {
                    productoActual = Producto.getProductoPorNombre(itemActual);
                }

                int tipoNuevo = JOptionPane.showOptionDialog(
                        null,
                        "Seleccione el tipo de pedido que desea realizar:",
                        "Pedidos",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, new Object[]{"Servicio", "Producto"},
                        "Servicio");

                if (tipoNuevo != 0 && tipoNuevo != 1) {
                    JOptionPane.showMessageDialog(null, "Item no editado.");
                    return;
                }

                String nuevoNombreItem = null;
                boolean esServicioNuevo = (tipoNuevo == 0);

                if (esServicioNuevo) {
                    nuevoNombreItem = seleccionarServicio();
                } else {
                    nuevoNombreItem = seleccionarProducto();
                }

                if (nuevoNombreItem == null) {
                    JOptionPane.showMessageDialog(null, "Item no editado (no seleccionó nada).");
                    return;
                }

                int nuevaCantidad = Integer.parseInt(
                        JOptionPane.showInputDialog("Digite la nueva cantidad del pedido:")
                );
                if (nuevaCantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida. Item no editado.");
                    return;
                }

                if (esServicioNuevo) {
                    Servicio nuevoServicio = Servicio.getServicioPorNombre(nuevoNombreItem);
                    if (nuevoServicio == null) {
                        JOptionPane.showMessageDialog(null, "Error al obtener el servicio seleccionado.");
                        return;
                    }

                    int disponibleNuevo = nuevoServicio.getCantidad();
                    if (nuevaCantidad > disponibleNuevo) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No hay suficientes servicios disponibles.\n"
                                + "Disponibles: " + disponibleNuevo
                        );
                        return;
                    }

                    if (servicioActual != null) {
                        servicioActual.setCantidad(servicioActual.getCantidad() + cantidadActual);
                    } else if (productoActual != null) {
                        productoActual.setCantidad(productoActual.getCantidad() + cantidadActual);
                    }

                    nuevoServicio.setCantidad(disponibleNuevo - nuevaCantidad);

                    p.setItem(nuevoNombreItem);
                    p.setCantidad(nuevaCantidad);
                    p.setSubtotal(nuevaCantidad * nuevoServicio.getPrecio());

                } else {
                    Producto nuevoProducto = Producto.getProductoPorNombre(nuevoNombreItem);
                    if (nuevoProducto == null) {
                        JOptionPane.showMessageDialog(null, "Error al obtener el producto seleccionado.");
                        return;
                    }

                    int disponibleNuevo = nuevoProducto.getCantidad();
                    if (nuevaCantidad > disponibleNuevo) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No hay suficientes productos disponibles.\n"
                                + "Disponibles: " + disponibleNuevo
                        );
                        return;
                    }

                    if (servicioActual != null) {
                        servicioActual.setCantidad(servicioActual.getCantidad() + cantidadActual);
                    } else if (productoActual != null) {
                        productoActual.setCantidad(productoActual.getCantidad() + cantidadActual);
                    }

                    nuevoProducto.setCantidad(disponibleNuevo - nuevaCantidad);

                    p.setItem(nuevoNombreItem);
                    p.setCantidad(nuevaCantidad);
                    p.setSubtotal(nuevaCantidad * nuevoProducto.getPrecio());
                }

                JOptionPane.showMessageDialog(null, "Item editado exitosamente.");
                break;
            }

            case 1: {
                String itemActual = p.getItem();
                int cantidadActual = p.getCantidad();

                Servicio servicioItem = Servicio.getServicioPorNombre(itemActual);
                Producto productoItem = null;
                if (servicioItem == null) {
                    productoItem = Producto.getProductoPorNombre(itemActual);
                }

                int nuevaCantidad = Integer.parseInt(
                        JOptionPane.showInputDialog("Digite la nueva cantidad del pedido:")
                );
                if (nuevaCantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida. No se realizó el cambio.");
                    return;
                }

                if (servicioItem != null) {

                    int disponibleTotal = servicioItem.getCantidad() + cantidadActual;

                    if (nuevaCantidad > disponibleTotal) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No hay suficientes servicios disponibles para cambiar la cantidad.\n"
                                + "Disponibles para este pedido: " + disponibleTotal
                        );
                        return;
                    }

                    servicioItem.setCantidad(disponibleTotal - nuevaCantidad);

                    p.setCantidad(nuevaCantidad);
                    p.setSubtotal(nuevaCantidad * servicioItem.getPrecio());

                } else if (productoItem != null) {
                    int disponibleTotal = productoItem.getCantidad() + cantidadActual;

                    if (nuevaCantidad > disponibleTotal) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No hay suficientes productos disponibles para cambiar la cantidad.\n"
                                + "Disponibles para este pedido: " + disponibleTotal
                        );
                        return;
                    }

                    productoItem.setCantidad(disponibleTotal - nuevaCantidad);

                    p.setCantidad(nuevaCantidad);
                    p.setSubtotal(nuevaCantidad * productoItem.getPrecio());

                } else {
                    JOptionPane.showMessageDialog(null, "Error: el item no está registrado como servicio ni producto.");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Cantidad editada exitosamente.");
                break;
            }

            case 2: {
                String nuevaFecha = JOptionPane.showInputDialog("Digite la nueva fecha del pedido:", p.getFecha());
                if (nuevaFecha != null && !nuevaFecha.trim().isEmpty()) {
                    p.setFecha(nuevaFecha);
                    JOptionPane.showMessageDialog(null, "Fecha editada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Fecha no editada.");
                }
                break;
            }

            case 3: {
                int indiceCliente = Cliente.seleccionarCliente();
                if (indiceCliente >= 0) {
                    Cliente nuevoCliente = Cliente.getCliente(indiceCliente);
                    if (nuevoCliente != null) {
                        p.setCliente(nuevoCliente);
                        JOptionPane.showMessageDialog(null, "Cliente del pedido editado exitosamente.");
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

    public void consultarPedidos() {
        if (contadorPedidos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
            return;
        }

        String lista = "Pedidos registrados:\n\n";
        for (int i = 0; i < contadorPedidos; i++) {
            lista += (i + 1)
                    + ". Item:" + pedidos[i].item
                    + " - Estado:(" + pedidos[i].estado
                    + ") - Cantidad:" + pedidos[i].cantidad
                    + " - Subtotal:" + pedidos[i].subtotal
                    + " - Cliente:" + pedidos[i].cliente.getNombreCompleto()
                    + " - Fecha:" + pedidos[i].fecha + "\n";
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

    public static int getContadorPedidos() {
        return contadorPedidos;
    }

    public static Pedido getPedido(int index) {
        if (index >= 0 && index < pedidos.length) {
            return pedidos[index];
        }
        return null;
    }

    public static boolean existePedidoActivoPorCliente(Cliente cliente) {
        if (cliente == null) {
            return false;
        }

        for (int i = 0; i < contadorPedidos; i++) {
            Pedido p = pedidos[i];
            if (p != null
                    && p.getEstado() == Estados.Activo
                    && p.getCliente() == cliente) {
                return true;
            }
        }
        return false;
    }

}
