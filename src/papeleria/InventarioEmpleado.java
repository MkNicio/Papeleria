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
public class InventarioEmpleado extends CenteredFrame {

    private int idProductoSeleccionado = -1; // Variable para almacenar el ID del producto seleccionado

    public InventarioEmpleado() {
        initComponents();
        cargarInventario();
    }

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

    private void actualizarInventario(int idProd, int cantidadAAgregar) {
        String url = "jdbc:postgresql://localhost:5432/Papeleria";
        String user = "postgres";
        String password = "9656";

        // Consulta para obtener la cantidad actual del inventario
        String consultaSql = "SELECT Cantidad_inv FROM ciber_action.Inventario WHERE Id_prod = ?";
        int cantidadActual = 0;

        // Obtener la cantidad actual del inventario
        try (Connection conexion = DriverManager.getConnection(url, user, password); Statement stmt = conexion.createStatement()) {

            // Cambiar el esquema activo
            stmt.execute("SET search_path TO ciber_action");

            // Preparar la consulta
            try (PreparedStatement consultaPstmt = conexion.prepareStatement(consultaSql)) {
                consultaPstmt.setInt(1, idProd);
                ResultSet rs = consultaPstmt.executeQuery();

                if (rs.next()) {
                    cantidadActual = rs.getInt("Cantidad_inv");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un producto con ese ID.");
                    return; // Salir si no se encuentra el producto
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener la cantidad actual: " + e.getMessage());
            return; // Salir en caso de error
        }

        // Calcular la nueva cantidad total
        int nuevaCantidad = cantidadActual + cantidadAAgregar;

        // Actualizar el inventario con la nueva cantidad total
        String sql = "UPDATE ciber_action.Inventario SET Cantidad_inv = ? WHERE Id_prod = ?";
        try (Connection conexion = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, nuevaCantidad);
            pstmt.setInt(2, idProd);

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
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Regresarbutt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(765, 555));

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(24, 143, 202));

        agregarIvn.setBackground(new java.awt.Color(0, 102, 255));
        agregarIvn.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        agregarIvn.setForeground(new java.awt.Color(255, 255, 255));
        agregarIvn.setText("AÑADIR");
        agregarIvn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarIvnActionPerformed(evt);
            }
        });

        Tinventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TinventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tinventario);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Inventario");

        Regresarbutt.setBackground(new java.awt.Color(0, 102, 255));
        Regresarbutt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Regresarbutt.setForeground(new java.awt.Color(255, 255, 255));
        Regresarbutt.setText("Regresar");
        Regresarbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarbuttActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Thepaim2_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Regresarbutt)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Regresarbutt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(agregarIvn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(agregarIvn)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 285, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(771, 771, 771))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregarIvnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarIvnActionPerformed
        if (idProductoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla.");
            return; // Salir si no se ha seleccionado un producto
        }

        int cantidadInv;

        try {
            cantidadInv = Integer.parseInt(JOptionPane.showInputDialog(this, "Cantidad para el producto ID: " + idProductoSeleccionado));
            if (cantidadInv <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                return; // Salir si la cantidad no es válida
            }
            actualizarInventario(idProductoSeleccionado, cantidadInv);
            cargarInventario(); // Recargar el inventario para reflejar los cambios
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un valor numérico.");
        }
    }//GEN-LAST:event_agregarIvnActionPerformed

    private void RegresarbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarbuttActionPerformed
        MenuEmpleado menuE = new MenuEmpleado();
        menuE.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RegresarbuttActionPerformed

    private void TinventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TinventarioMouseClicked
        // Verificar si se ha hecho clic en una fila
        int selectedRow = Tinventario.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener el ID del producto seleccionado (suponiendo que está en la primera columna)
            idProductoSeleccionado = (int) Tinventario.getValueAt(selectedRow, 0); // Cambia el índice si el ID no está en la primera columna
        }
    }//GEN-LAST:event_TinventarioMouseClicked

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
            java.util.logging.Logger.getLogger(InventarioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventarioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventarioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventarioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventarioEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresarbutt;
    private javax.swing.JTable Tinventario;
    private javax.swing.JButton agregarIvn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
