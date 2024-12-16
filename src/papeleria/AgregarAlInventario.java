/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author julia
 */
public class AgregarAlInventario extends CenteredFrame {

    /**
     * Creates new form AgregarAlInventario
     */
    public AgregarAlInventario() {
        initComponents();
        cargarInventario();
        cargarProductos();
    }

    // Método para cargar inventario (sin cambios)
    private void cargarInventario() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Papeleria";
            String user = "postgres";
            String password = "9656";
            Connection con = DriverManager.getConnection(url, user, password);

            // Cambiar el esquema activo
            Statement stmt = con.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            // Consulta SQL para obtener los datos del inventario
            String sql = "SELECT Inventario.Id_prod, Producto.Nombre, Inventario.Cantidad_inv, "
                    + "Inventario.Stock_minimo, Producto.Precio, Producto.Categoria FROM Inventario "
                    + "INNER JOIN Producto ON Inventario.Id_prod = Producto.Id_prod";
            ResultSet rs = stmt.executeQuery(sql);

            // Obtener los metadatos de las columnas
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un Vector para los nombres de las columnas
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Crear un Vector para las filas de la tabla
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            // Crear el modelo de la tabla y asignarlo al JTable
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            Tinventario.setModel(model);

            // Cerrar la conexión
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar inventario: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Papeleria";
            String user = "postgres";
            String password = "9656";
            Connection con = DriverManager.getConnection(url, user, password);

            // Cambiar el esquema activo
            Statement stmt = con.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            // Consulta SQL para obtener los nombres de los productos que no están en Inventario
            String sql = "SELECT Id_prod, Nombre FROM Producto WHERE Id_prod NOT IN (SELECT Id_prod FROM Inventario)";
            ResultSet rs = stmt.executeQuery(sql);

            // Limpiar el JComboBox antes de agregar nuevos elementos
            comboBoxProductos.removeAllItems();

            // Agregar los productos al JComboBox
            while (rs.next()) {
                int idProd = rs.getInt("Id_prod");
                String nombre = rs.getString("Nombre");
                comboBoxProductos.addItem(nombre + " (ID: " + idProd + ")"); // Mostrar el nombre y el ID
            }

            // Cerrar la conexión
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }

    // Método para agregar un producto (sin cambios)
    private void agregarProducto(int idProd, int cantidadInv, int stockMinimo) {
        String url = "jdbc:postgresql://localhost:5432/Papeleria";
        String user = "postgres";
        String password = "9656";

        String sql = "INSERT INTO Inventario (Id_prod, Cantidad_inv, Stock_minimo) VALUES (?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(url, user, password); PreparedStatement ps = conexion.prepareStatement(sql)) {

            Statement stmt = conexion.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            ps.setInt(1, idProd);
            ps.setInt(2, cantidadInv);
            ps.setInt(3, stockMinimo);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
        }
    }

    private void actualizarInventario(int idProd, int nuevaCantidad, int nuevoStockMinimo) {
        String url = "jdbc:postgresql://localhost:5432/Papeleria";
        String user = "postgres";
        String password = "9656";

        String sql = "UPDATE Inventario SET Cantidad_inv = ?, Stock_minimo = ? WHERE Id_prod = ?";

        try (Connection conexion = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            Statement stmt = conexion.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            pstmt.setInt(1, nuevaCantidad);
            pstmt.setInt(2, nuevoStockMinimo);
            pstmt.setInt(3, idProd);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Inventario actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un producto con ese ID.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el inventario: " + e.getMessage());
        }
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
        jPanel2 = new javax.swing.JPanel();
        agregarIvn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tinventario = new javax.swing.JTable();
        agregarIvn1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Regresarbutt = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboBoxProductos = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(690, 494));

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(871, 560));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(24, 143, 202));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        agregarIvn.setBackground(new java.awt.Color(0, 102, 255));
        agregarIvn.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        agregarIvn.setForeground(new java.awt.Color(255, 255, 255));
        agregarIvn.setText("AÑADIR");
        agregarIvn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarIvnActionPerformed(evt);
            }
        });
        jPanel2.add(agregarIvn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 470, -1, -1));

        jScrollPane1.setViewportView(Tinventario);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 630, 336));

        agregarIvn1.setBackground(new java.awt.Color(0, 102, 255));
        agregarIvn1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        agregarIvn1.setForeground(new java.awt.Color(255, 255, 255));
        agregarIvn1.setText("Actualizar");
        agregarIvn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarIvn1ActionPerformed(evt);
            }
        });
        jPanel2.add(agregarIvn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 470, -1, -1));

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("           Inventario");

        Regresarbutt.setBackground(new java.awt.Color(0, 102, 255));
        Regresarbutt.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Regresarbutt.setForeground(new java.awt.Color(255, 255, 255));
        Regresarbutt.setText("Regresar");
        Regresarbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarbuttActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Thepaim2_1.png"))); // NOI18N
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Regresarbutt)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Regresarbutt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 110));
        jPanel2.add(comboBoxProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegresarbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarbuttActionPerformed
        VerProductos vp = new VerProductos();
        vp.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RegresarbuttActionPerformed

    private void agregarIvn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarIvn1ActionPerformed
        int filaSeleccionada = Tinventario.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idProd = (int) Tinventario.getValueAt(filaSeleccionada, 0);
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva cantidad:"));
            int nuevoStockMinimo = Integer.parseInt(JOptionPane.showInputDialog(this, "Nuevo stock mínimo:"));

            actualizarInventario(idProd, nuevaCantidad, nuevoStockMinimo);
            cargarInventario();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla.");
        }
    }//GEN-LAST:event_agregarIvn1ActionPerformed

    private void agregarIvnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarIvnActionPerformed
       String selectedItem = (String) comboBoxProductos.getSelectedItem();
    if (selectedItem == null) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto.");
        return;
    }

    // Extraer el ID del producto del texto seleccionado
    int idProd = Integer.parseInt(selectedItem.split(" \\(ID: ")[1].replace(")", ""));
    int cantidadInv;
    int stockMinimo;

    try {
        cantidadInv = Integer.parseInt(JOptionPane.showInputDialog(this, "Cantidad:"));
        stockMinimo = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock mínimo:"));

        agregarProducto(idProd, cantidadInv, stockMinimo);
        cargarInventario();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Cantidad y stock mínimo deben ser valores numéricos.");
    }
    }//GEN-LAST:event_agregarIvnActionPerformed

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
            java.util.logging.Logger.getLogger(AgregarAlInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarAlInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarAlInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarAlInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarAlInventario().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresarbutt;
    private javax.swing.JTable Tinventario;
    private javax.swing.JButton agregarIvn;
    private javax.swing.JButton agregarIvn1;
    private javax.swing.JComboBox<String> comboBoxProductos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
