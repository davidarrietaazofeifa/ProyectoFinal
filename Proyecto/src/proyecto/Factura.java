package proyecto;

import javax.swing.JOptionPane;

public class Factura {

    private String fechaHora;
    private Pedido pedidos;
    private Estados estado;
    private Cliente cliente;
    private double total;

    private static Factura[] facturas = new Factura[20];
    private static int contadorFacturas = 0;

    private Utilities util = new Utilities();

    public Factura() {
    }

    public Factura(String fechaHora, Pedido pedidos, Estados estado, Cliente cliente, double total) {
        this.fechaHora = fechaHora;
        this.pedidos = pedidos;
        this.estado = estado;
        this.cliente = cliente;
        this.total = total;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Pedido getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedido pedidos) {
        this.pedidos = pedidos;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void Facturar() {
        if (contadorFacturas >= facturas.length) {
            JOptionPane.showMessageDialog(null, "No es posible registrar más facturas (máximo 20).");
            return;
        }

        int indiceCliente = Cliente.seleccionarCliente();
        if (indiceCliente == -1) {
            JOptionPane.showMessageDialog(null, "Factura no generada (no seleccionó cliente).");
            return;
        }

        Cliente cli = Cliente.getCliente(indiceCliente);
        if (cli == null) {
            JOptionPane.showMessageDialog(null, "Factura no generada (cliente inválido).");
            return;
        }

        int totalPedidos = Pedido.getContadorPedidos();
        if (totalPedidos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pedidos registrados.");
            return;
        }

        int contadorPedidosCliente = 0;
        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = Pedido.getPedido(i);
            if (p != null && p.getCliente() == cli && p.getEstado() == Estados.Activo) {
                contadorPedidosCliente++;
            }
        }

        if (contadorPedidosCliente == 0) {
            JOptionPane.showMessageDialog(null, "El cliente seleccionado no tiene pedidos activos para facturar.");
            return;
        }

        int[] indicesPedidos = new int[contadorPedidosCliente];
        String lista = "Pedidos del cliente " + cli.getNombre() + ":\n\n";
        int pos = 0;

        for (int i = 0; i < totalPedidos; i++) {
            Pedido p = Pedido.getPedido(i);
            if (p != null && p.getCliente() == cli && p.getEstado() == Estados.Activo) {
                indicesPedidos[pos] = i;
                lista += (pos + 1) + ". "
                        + p.getItem()
                        + " - Cantidad: " + p.getCantidad()
                        + " - Subtotal: ₡" + p.getSubtotal()
                        + " - Fecha pedido: " + p.getFecha() + "\n";
                pos++;
            }
        }

        JOptionPane.showMessageDialog(null, lista);

        int opcionPedido;
        opcionPedido = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número del pedido que desea facturar:")
        ) - 1;

        if (opcionPedido < 0 || opcionPedido >= contadorPedidosCliente) {
            JOptionPane.showMessageDialog(null, "Opción inválida. Factura no generada.");
            return;
        }

        int indicePedidoReal = indicesPedidos[opcionPedido];
        Pedido pedidoSeleccionado = Pedido.getPedido(indicePedidoReal);

        if (pedidoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Error al obtener el pedido seleccionado.");
            return;
        }

        String fechaHora = JOptionPane.showInputDialog(
                null,
                "Digite la fecha y hora de la factura (ej: 2025-11-19 10:30):"
        );
        if (fechaHora == null || fechaHora.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Factura no generada (fecha/hora inválida).");
            return;
        }

        Estados estadoFactura = Estados.Activo;
        double totalFactura = pedidoSeleccionado.getSubtotal();

        Factura nueva = new Factura(fechaHora, pedidoSeleccionado, estadoFactura, cli, totalFactura);
        facturas[contadorFacturas] = nueva;
        contadorFacturas++;

        JOptionPane.showMessageDialog(null,
                "Factura generada exitosamente.\n"
                + "Cliente: " + cli.getNombre() + "\n"
                + "Pedido: " + pedidoSeleccionado.getItem() + "\n"
                + "Total: ₡" + totalFactura);
    }

    public void verFacturas() {
        if (contadorFacturas == 0) {
            JOptionPane.showMessageDialog(null, "No hay facturas registradas.");
            return;
        }

        String lista = "Facturas registradas:\n\n";
        for (int i = 0; i < contadorFacturas; i++) {
            Factura f = facturas[i];
            lista += (i + 1) + ". "
                    + "Cliente: " + f.getCliente().getNombre()
                    + " - Fecha/Hora: " + f.getFechaHora()
                    + " - Total: ₡" + f.getTotal()
                    + " - Estado: " + f.getEstado()
                    + "\n";
        }

        JOptionPane.showMessageDialog(null, lista);
    }

    public void verDetalleFactura() {
        if (contadorFacturas == 0) {
            JOptionPane.showMessageDialog(null, "No hay facturas registradas.");
            return;
        }

        verFacturas();

        int indice;
        indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número de la factura que desea consultar:")
        ) - 1;

        if (indice < 0 || indice >= contadorFacturas) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Factura f = facturas[indice];
        Pedido p = f.getPedidos();

        String detalle = "Detalle de la factura:\n\n"
                + "Cliente: " + f.getCliente().getNombre() + "\n"
                + "Fecha/Hora: " + f.getFechaHora() + "\n"
                + "Estado: " + f.getEstado() + "\n"
                + "---------------------------------\n"
                + "Pedido asociado:\n"
                + "Ítem: " + p.getItem() + "\n"
                + "Cantidad: " + p.getCantidad() + "\n"
                + "Subtotal pedido: ₡" + p.getSubtotal() + "\n"
                + "---------------------------------\n"
                + "TOTAL FACTURA: ₡" + f.getTotal();

        JOptionPane.showMessageDialog(null, detalle);
    }

    public void anularFactura() {
        if (contadorFacturas == 0) {
            JOptionPane.showMessageDialog(null, "No hay facturas registradas.");
            return;
        }

        verFacturas();

        int indice;
        indice = Integer.parseInt(
                JOptionPane.showInputDialog("Digite el número de la factura que desea anular:")
        ) - 1;

        if (indice < 0 || indice >= contadorFacturas) {
            JOptionPane.showMessageDialog(null, "Índice inválido.");
            return;
        }

        Factura f = facturas[indice];

        if (f.getEstado() == Estados.Inactivo) {
            JOptionPane.showMessageDialog(null, "La factura ya está anulada.");
            return;
        }

        f.setEstado(Estados.Inactivo);

        JOptionPane.showMessageDialog(null, "Factura anulada correctamente (estado: Inactivo).");
    }
}
