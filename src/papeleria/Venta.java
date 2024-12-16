/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julia
 */
public class Venta extends CenteredFrame {

    private List<ProductoSeleccionado> carrito;
    private int idEmpleado; // Variable para almacenar el ID del empleado

    public Venta(int idEmpleado) { // Constructor que recibe el ID del empleado
        initComponents();
        this.idEmpleado = idEmpleado; // Almacenar el ID del empleado
        cargarProductos();
        ticketTextArea.setEditable(false);
        carrito = new ArrayList<>();
    }

    private Connection obtenerConexion() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/Papeleria";
        String user = "postgres";
        String password = "9656";
        Connection conexion = DriverManager.getConnection(url, user, password);
        try (Statement stmt = conexion.createStatement()) {
            stmt.execute("SET search_path TO ciber_action");
        }
        return conexion;
    }

    public void realizarVenta(List<ProductoSeleccionado> carrito, int idEmpleado) {
        try (Connection conexion = obtenerConexion()) {
            conexion.setAutoCommit(false);  // Iniciar la transacción

            // Registrar la venta en la tabla Venta
            String ventaSql = "INSERT INTO Venta (Fecha, Id_em) VALUES (NOW(), ?)";
            try (PreparedStatement ventaPs = conexion.prepareStatement(ventaSql, Statement.RETURN_GENERATED_KEYS)) {
                ventaPs.setInt(1, idEmpleado);
                ventaPs.executeUpdate();

                ResultSet rs = ventaPs.getGeneratedKeys();
                if (rs.next()) {
                    int folioVenta = rs.getInt(1);

                    List<String> productosBajos = new ArrayList<>(); // Lista para productos con stock bajo

                    for (ProductoSeleccionado item : carrito) {
                        int idProducto = item.getProducto().getId_prod();
                        int cantidadVendida = item.getCantidad();
                        double montoTotal = cantidadVendida * item.getPrecio();

                        String consultaSql = "SELECT Cantidad_inv, Stock_minimo FROM Inventario WHERE Id_prod = ?";
                        try (PreparedStatement ps = conexion.prepareStatement(consultaSql)) {
                            ps.setInt(1, idProducto);
                            ResultSet rsInventario = ps.executeQuery();

                            if (rsInventario.next()) {
                                int cantidadActual = rsInventario.getInt("Cantidad_inv");
                                int stockMinimo = rsInventario.getInt("Stock_minimo");

                                if (cantidadVendida > cantidadActual) {
                                    JOptionPane.showMessageDialog(this, "No hay suficiente inventario para el producto: "
                                            + item.getProducto().getNombre());
                                    conexion.rollback();
                                    return;
                                }

                                // Verificar si el stock actual después de la venta es menor que el stock mínimo
                                if (cantidadActual - cantidadVendida < stockMinimo) {
                                    productosBajos.add(item.getProducto().getNombre()); // Agregar a la lista
                                }

                                // Actualizar inventario
                                String updateSql = "UPDATE Inventario SET Cantidad_inv = ? WHERE Id_prod = ?";
                                try (PreparedStatement updatePs = conexion.prepareStatement(updateSql)) {
                                    updatePs.setInt(1, cantidadActual - cantidadVendida);
                                    updatePs.setInt(2, idProducto);
                                    updatePs.executeUpdate();
                                }

                                // Registrar el detalle de la venta
                                String detalleVentaSql = "INSERT INTO Detalle_Venta (Folio_v, Id_prod, Cantidad, Monto_total) VALUES (?, ?, ?, ?)";
                                try (PreparedStatement detallePs = conexion.prepareStatement(detalleVentaSql)) {
                                    detallePs.setInt(1, folioVenta);
                                    detallePs.setInt(2, idProducto);
                                    detallePs.setInt(3, cantidadVendida);
                                    detallePs.setDouble(4, montoTotal);
                                    detallePs.executeUpdate();
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Producto no encontrado: " + item.getProducto().getNombre());
                                conexion.rollback();
                                return;
                            }
                        }
                    }

                    // Si hay productos con stock bajo, mostrar un solo mensaje
                    if (!productosBajos.isEmpty()) {
                        StringBuilder mensaje = new StringBuilder("Los siguientes productos tienen stock bajo:\n");
                        for (String producto : productosBajos) {
                            mensaje.append("- ").append(producto).append("\n");
                        }
                        int respuesta = JOptionPane.showConfirmDialog(this,
                                mensaje.toString() + "¿Desea realizar un pedido?",
                                "Stock Mínimo",
                                JOptionPane.YES_NO_OPTION);

                        if (respuesta == JOptionPane.YES_OPTION) {
                            // Abrir el frame de pedidos
                            PedidoF pedidoFrame = new PedidoF();
                            pedidoFrame.setVisible(true);
                            this.dispose();
                        }
                    }

                    conexion.commit();
                    JOptionPane.showMessageDialog(this, "Venta realizada con éxito.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la venta: " + e.getMessage());
        }
    }
// Ejemplo para cargar productos reutilizando `obtenerConexion`

    private void cargarProductos() {
        try (Connection conexion = obtenerConexion(); Statement stmt = conexion.createStatement()) {
            // Consulta SQL para obtener los productos que ya están en el inventario
            String sql = "SELECT p.Id_prod, p.Nombre FROM Producto p "
                    + "INNER JOIN Inventario i ON p.Id_prod = i.Id_prod";
            ResultSet rs = stmt.executeQuery(sql);

            DefaultComboBoxModel<Producto> modelo = new DefaultComboBoxModel<>();
            while (rs.next()) {
                int idProd = rs.getInt("Id_prod");
                String nombre = rs.getString("Nombre");
                modelo.addElement(new Producto(idProd, nombre));
            }
            comboBoxProductos.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }

    private void agregarAlCarrito() {
        Producto productoSeleccionado = (Producto) comboBoxProductos.getSelectedItem();

        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto.");
            return;
        }

        String cantidadStr = cantidadTextField.getText();
        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese la cantidad a vender.");
            return;
        }

        try {
            int cantidadVendida = Integer.parseInt(cantidadStr);

            try (Connection conexion = obtenerConexion(); PreparedStatement ps = conexion.prepareStatement(
                    "SELECT Cantidad_inv, Precio FROM Inventario i JOIN Producto p ON i.Id_prod = p.Id_prod WHERE i.Id_prod = ?")) {

                ps.setInt(1, productoSeleccionado.getId_prod());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int cantidadActual = rs.getInt("Cantidad_inv");
                    double precio = rs.getDouble("Precio");

                    if (cantidadVendida > cantidadActual) {
                        JOptionPane.showMessageDialog(this, "No hay suficiente inventario para este producto.");
                        return;
                    }

                    // Verificar si el producto ya está en el carrito
                    boolean productoExistente = false;
                    for (ProductoSeleccionado item : carrito) {
                        if (item.getProducto().getId_prod() == productoSeleccionado.getId_prod()) {
                            // Si el producto ya existe, sumar la cantidad
                            item.setCantidad(item.getCantidad() + cantidadVendida);
                            productoExistente = true;
                            JOptionPane.showMessageDialog(this, "Cantidad actualizada en el carrito.");
                            break;
                        }
                    }

                    // Si el producto no existe en el carrito, agregarlo
                    if (!productoExistente) {
                        carrito.add(new ProductoSeleccionado(productoSeleccionado, cantidadVendida, precio));
                        JOptionPane.showMessageDialog(this, "Producto agregado al carrito.");
                    }

                    cantidadTextField.setText("");
                    verCarrito();
                    actualizarComboBoxCarrito();
                } else {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado en el inventario.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
        }
    }

    private void eliminarDelCarrito() {
        ProductoSeleccionado productoSeleccionado = (ProductoSeleccionado) comboBoxCarrito.getSelectedItem();

        if (productoSeleccionado != null) {
            if (carrito.remove(productoSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado del carrito.");
            } else {
                JOptionPane.showMessageDialog(this, "El producto no se encontró en el carrito.");
            }
            verCarrito(); // Actualiza la vista del carrito después de eliminar
            actualizarComboBoxCarrito(); // Actualiza el JComboBox
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto del carrito.");
        }
    }

    private void actualizarComboBoxCarrito() {
        comboBoxCarrito.removeAllItems(); // Limpiar el JComboBox
        for (ProductoSeleccionado item : carrito) {
            comboBoxCarrito.addItem(item); // Agregar cada producto del carrito
        }
    }

    private void generarTicket() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        StringBuilder ticket = new StringBuilder();
        ticket.append("********** TICKET DE VENTA **********\n");
        double totalGeneral = 0.0;

        for (ProductoSeleccionado item : carrito) {
            double subtotal = item.getCantidad() * item.getPrecio();
            totalGeneral += subtotal;

            ticket.append("Producto: ").append(item.getProducto().getNombre()).append("\n");
            ticket.append("Cantidad: ").append(item.getCantidad()).append("\n");
            ticket.append("Precio Unitario: $").append(String.format("%.2f", item.getPrecio())).append("\n");
            ticket.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
            ticket.append("-------------------------------------\n");
        }

        ticket.append("TOTAL: $").append(String.format("%.2f", totalGeneral)).append("\n");
        ticket.append("Gracias por su compra!\n");
        ticket.append("*************************************");

        ticketTextArea.setText(ticket.toString());

        // Procesar venta en la base de datos usando el ID del empleado
        realizarVenta(carrito, idEmpleado); // Asegúrate de pasar el ID del empleado

        // Limpiar el carrito después de generar el ticket
        carrito.clear();
    }

    private void verCarrito() {
        if (carrito.isEmpty()) {
            ticketTextArea.setText("El carrito está vacío.");
            return;
        }

        StringBuilder carritoTexto = new StringBuilder();
        carritoTexto.append("********** CARRITO DE COMPRAS **********\n");
        double totalGeneral = 0.0;

        for (ProductoSeleccionado item : carrito) {
            double subtotal = item.getCantidad() * item.getPrecio();
            totalGeneral += subtotal;

            carritoTexto.append("Producto: ").append(item.getProducto().getNombre()).append("\n");
            carritoTexto.append("Cantidad: ").append(item.getCantidad()).append("\n");
            carritoTexto.append("Precio Unitario: $").append(String.format("%.2f", item.getPrecio())).append("\n");
            carritoTexto.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
            carritoTexto.append("-------------------------------------\n");
        }

        carritoTexto.append("TOTAL: $").append(String.format("%.2f", totalGeneral)).append("\n");
        carritoTexto.append("*************************************");

        ticketTextArea.setText(carritoTexto.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblProducto = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        Regresarbutt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        AgregarCarr = new javax.swing.JButton();
        comboBoxProductos = new javax.swing.JComboBox<>();
        cantidadTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ticketTextArea = new javax.swing.JTextArea();
        VenderButton = new javax.swing.JButton();
        comboBoxCarrito = new javax.swing.JComboBox<>();
        EliminarCarr = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProducto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProducto.setText("Producto:");
        jPanel3.add(lblProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 163, 140, 32));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Thepaim2_1.png"))); // NOI18N
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        lblCantidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("Cantidad a vender:");
        jPanel3.add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 213, 140, 32));

        Regresarbutt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Regresarbutt.setText("Regresar");
        Regresarbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarbuttActionPerformed(evt);
            }
        });
        jPanel3.add(Regresarbutt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(24, 143, 202));

        AgregarCarr.setBackground(new java.awt.Color(0, 102, 255));
        AgregarCarr.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        AgregarCarr.setForeground(new java.awt.Color(255, 255, 255));
        AgregarCarr.setText("Agregar al carrito");
        AgregarCarr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarCarrActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        jLabel6.setBackground(new java.awt.Color(0, 102, 255));
        jLabel6.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Vender");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        ticketTextArea.setColumns(20);
        ticketTextArea.setRows(5);
        ticketTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ticket de Venta", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        jScrollPane1.setViewportView(ticketTextArea);

        VenderButton.setBackground(new java.awt.Color(0, 102, 255));
        VenderButton.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        VenderButton.setForeground(new java.awt.Color(255, 255, 255));
        VenderButton.setText("Vender");
        VenderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VenderButtonActionPerformed(evt);
            }
        });

        EliminarCarr.setBackground(new java.awt.Color(0, 102, 255));
        EliminarCarr.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        EliminarCarr.setForeground(new java.awt.Color(255, 255, 255));
        EliminarCarr.setText("Eliminar del carrito");
        EliminarCarr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarCarrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboBoxProductos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cantidadTextField)
                    .addComponent(VenderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AgregarCarr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboBoxCarrito, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EliminarCarr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(74, 74, 74))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(comboBoxProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(cantidadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(VenderButton)
                                .addGap(48, 48, 48))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(AgregarCarr)
                        .addGap(39, 39, 39)
                        .addComponent(comboBoxCarrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(EliminarCarr)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 800, 420));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegresarbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarbuttActionPerformed
        MenuEmpleado agP = new MenuEmpleado();
        agP.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RegresarbuttActionPerformed

    private void AgregarCarrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarCarrActionPerformed
        agregarAlCarrito();
    }//GEN-LAST:event_AgregarCarrActionPerformed

    private void VenderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VenderButtonActionPerformed
        generarTicket();
    }//GEN-LAST:event_VenderButtonActionPerformed

    private void EliminarCarrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarCarrActionPerformed
        eliminarDelCarrito(); // Llama al método para eliminar el producto
        actualizarComboBoxCarrito();
    }//GEN-LAST:event_EliminarCarrActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        int idEmpleado = 1;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Venta(idEmpleado).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarCarr;
    private javax.swing.JButton EliminarCarr;
    private javax.swing.JButton Regresarbutt;
    private javax.swing.JButton VenderButton;
    private javax.swing.JTextField cantidadTextField;
    private javax.swing.JComboBox<ProductoSeleccionado> comboBoxCarrito;
    private javax.swing.JComboBox<Producto> comboBoxProductos;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JTextArea ticketTextArea;
    // End of variables declaration//GEN-END:variables
}

class Producto {

    private int id_prod;
    private String nombre;

    public Producto(int id_prod, String nombre) {
        this.id_prod = id_prod;
        this.nombre = nombre;
    }

    public int getId_prod() {
        return id_prod;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre; // Para mostrar el nombre en el combo box
    }
}

class ProductoSeleccionado {

    private Producto producto;
    private int cantidad;
    private double precio;

    public ProductoSeleccionado(Producto producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Método para establecer la cantidad
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad; // Asigna la nueva cantidad
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " (Cantidad: " + cantidad + ")"; // Muestra el nombre del producto y la cantidad
    }
}
