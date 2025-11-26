package proyecto;

import javax.swing.JOptionPane;

public class SubMenus {

    Usuario u = new Usuario();
    Servicio s = new Servicio();
    Pedido p = new Pedido();
    Cliente c = new Cliente();
    Producto pr = new Producto();
    Factura f = new Factura();

    private int opcion;

    public void subMenuUsuarios() {

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "GESTIÓN DE USUARIOS\n\n"
                    + "1. Agregar usuario\n"
                    + "2. Consultar usuarios\n"
                    + "3. Inactivar/Activar usuario\n"
                    + "4. Gestionar clientes\n"
                    + "5. Volver al menú anterior\n"
                    + "6. Salir del sistema\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1: {
                    u.agregarUsuario();
                    break;
                }
                case 2: {
                    u.consultarUsuarios();
                    break;
                }
                case 3: {
                    u.inactivarUsuario();
                    break;
                }
                case 4: {

                    opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "GESTIÓN DE CLIENTES\n\n"
                            + "1. Consultar clientes\n"
                            + "2. Editar clientes\n"
                            + "3. Volver al menú anterior\n"
                            + "4. Salir del sistema\n\n"
                            + "Seleccione una opción:"));

                    switch (opcion) {
                        case 1: {
                            c.consultarClientes();
                            return;
                        }
                        case 2: {
                            c.editarCliente();
                            return;
                        }
                        case 3: {
                            return;
                        }
                        case 4: {
                            JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                            System.exit(0);
                            break;
                        }
                        default: {
                            JOptionPane.showMessageDialog(null, "Opción no válida");
                        }
                    }
                }
                case 5: {
                    return;
                }
                case 6: {
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            }

        } while (opcion != 5);
    }

    public void subMenuProductos() {

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "GESTIÓN DE PRODUCTOS\n\n"
                    + "1. Agregar producto\n"
                    + "2. Consultar producto\n"
                    + "3. Editar producto\n"
                    + "4. Inactivar/Activar producto\n"
                    + "5. Volver al menú anterior\n"
                    + "6. Salir del sistema\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1: {
                    pr.agregarProducto();
                    break;
                }
                case 2: {
                    pr.consultarProductos();
                    break;
                }
                case 3: {
                    pr.editarProducto();
                    break;
                }
                case 4: {
                    pr.inactivarProducto();
                    break;
                }
                case 5: {
                    return;
                }
                case 6: {
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            }

        } while (opcion != 6);
    }

    public void subMenuClientes() {

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "GESTIÓN DE ClIENTES\n\n"
                    + "1. Agregar cliente\n"
                    + "2. Editar cliente\n"
                    + "3. Inactivar/Activar cliente\n"
                    + "4. Volver al menú anterior\n"
                    + "5. Salir del sistema\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1: {

                    break;
                }
                case 2: {

                    break;
                }
                case 3: {

                    break;
                }
                case 4: {
                    return;
                }
                case 5: {
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            }

        } while (opcion != 5);
    }

    public void subMenuServicios() {

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "GESTIÓN DE SERVICIOS\n\n"
                    + "1. Agregar servicio\n"
                    + "2. Consultar servicios\n"
                    + "3. Editar servicio\n"
                    + "4. Inactivar/Activar servicio\n"
                    + "5. Volver al menú anterior\n"
                    + "6. Salir del sistema\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1: {
                    s.agregarServicio();
                    break;
                }
                case 2: {
                    s.consultarServicios();
                    break;
                }
                case 3: {
                    s.editarServicio();
                    break;
                }
                case 4: {
                    s.inactivarServicio();
                    break;
                }
                case 5: {
                    return;
                }
                case 6: {
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            }

        } while (opcion != 6);
    }

    public void subMenuPedidos() {

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "GESTIÓN DE PEDIDOS\n\n"
                    + "1. Agregar pedido\n"
                    + "2. Consultar pedidos\n"
                    + "3. Editar pedido\n"
                    + "4. Inactivar/Activar pedido\n"
                    + "5. Volver al menú anterior\n"
                    + "6. Salir del sistema\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1: {
                    p.agregarPedido();
                    break;
                }
                case 2: {
                    p.consultarPedidos();
                    break;
                }
                case 3: {
                    p.editarPedido();
                    break;
                }
                case 4: {
                    p.inactivarPedido();
                    break;
                }
                case 5: {
                    return;
                }
                case 6: {
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            }

        } while (opcion != 6);
    }

    public void subMenuFacturas() {

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "GESTIÓN DE FACTURAS\n\n"
                    + "1. Agregar factura\n"
                    + "2. Mostrar facturas\n"
                    + "3. Consultar factura\n"
                    + "4. Anular/Activar factura\n"
                    + "5. Volver al menú anterior\n"
                    + "6. Salir del sistema\n\n"
                    + "Seleccione una opción:"));

            switch (opcion) {
                case 1: {
                    f.Facturar();
                    break;
                }
                case 2: {
                    f.verFacturas();
                    break;
                }
                case 3: {
                    f.verDetalleFactura();
                    break;
                }
                case 4: {
                    f.anularFactura();
                    break;
                }
                case 5: {
                    return;
                }
                case 6: {
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            }

        } while (opcion != 6);
    }

}
