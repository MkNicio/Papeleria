/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria;

import java.sql.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author julia
 */
public class VerProovedores extends CenteredFrame {

    public void generarTable() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Papeleria";
            String user = "postgres";
            String password = "9656";
            Connection con = DriverManager.getConnection(url, user, password);

            // Cambiar el esquema activo
            Statement stmt = con.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            // Consulta SQL para obtener todos los empleados
            String sql = "SELECT * FROM proveedor";
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
            ProvTable.setModel(model);

            // Cerrar la conexión
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }

    private void borrarProv() {
        try {
            // Verificar si se ha seleccionado una fila
            int selectedRow = ProvTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor en la tabla.");
                return;
            }

            // Obtener el ID del proveedor seleccionado
            int id = (int) ProvTable.getValueAt(selectedRow, 0); // Suponiendo que el ID está en la primera columna

            // Mensaje de confirmación
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea borrar al proveedor?", "Confirmar Borrado", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Si el usuario selecciona "No", salir del método
            }

            String url = "jdbc:postgresql://localhost:5432/Papeleria";
            String user = "postgres";
            String password = "9656";
            Connection con = DriverManager.getConnection(url, user, password);

            // Cambiar el esquema activo
            Statement stmt = con.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            // Consulta SQL para borrar un proveedor por su ID
            String sql = "DELETE FROM proveedor WHERE id_prov = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            // Ejecutar la consulta
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado exitosamente.");
                generarTable(); // Actualiza la tabla después de borrar
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un proveedor con ese ID.");
            }

            pstmt.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor: " + e.getMessage());
        }
    }

    /**
     * Creates new form VerProovedores
     */
    public VerProovedores() {
        initComponents();
        generarTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProvTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        actualizarTable = new javax.swing.JButton();
        borrarProv = new javax.swing.JButton();
        Regresarbutt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ProvTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(ProvTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 440, 340));

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));

        actualizarTable.setBackground(new java.awt.Color(0, 102, 255));
        actualizarTable.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        actualizarTable.setForeground(new java.awt.Color(255, 255, 255));
        actualizarTable.setText("Actualizar");
        actualizarTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarTableActionPerformed(evt);
            }
        });

        borrarProv.setBackground(new java.awt.Color(0, 102, 255));
        borrarProv.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        borrarProv.setForeground(new java.awt.Color(255, 255, 255));
        borrarProv.setText("Borrar");
        borrarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarProvActionPerformed(evt);
            }
        });

        Regresarbutt.setBackground(new java.awt.Color(0, 102, 255));
        Regresarbutt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Regresarbutt.setForeground(new java.awt.Color(255, 255, 255));
        Regresarbutt.setText("Regresar");
        Regresarbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarbuttActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prov.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(borrarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(actualizarTable))
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(Regresarbutt)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(Regresarbutt)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(actualizarTable)
                .addGap(18, 18, 18)
                .addComponent(borrarProv)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarTableActionPerformed
        int selectedRow = ProvTable.getSelectedRow();
        if (selectedRow != -1) {
            // Convertir los valores obtenidos de la tabla a String de forma segura
            int id = (int) ProvTable.getValueAt(selectedRow, 0);
            String nombre = ProvTable.getValueAt(selectedRow, 1).toString();
            String telefono = ProvTable.getValueAt(selectedRow, 2).toString();
            String correo = ProvTable.getValueAt(selectedRow, 3).toString();
            String direccion = ProvTable.getValueAt(selectedRow, 4).toString();
            String rfc = ProvTable.getValueAt(selectedRow, 5).toString();

            // Abre el frame para actualizar proveedor
            new actualizarProovedor(id, nombre, telefono, correo, direccion, rfc).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla.");
        }
    }//GEN-LAST:event_actualizarTableActionPerformed

    private void RegresarbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarbuttActionPerformed
        agregarProovedores agP = new agregarProovedores();
        agP.setVisible(true); // Mostrar el frame de MenuGerente
        dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RegresarbuttActionPerformed

    private void borrarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarProvActionPerformed
        borrarProv();
    }//GEN-LAST:event_borrarProvActionPerformed

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
            java.util.logging.Logger.getLogger(VerProovedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerProovedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerProovedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerProovedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerProovedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ProvTable;
    private javax.swing.JButton Regresarbutt;
    private javax.swing.JButton actualizarTable;
    private javax.swing.JButton borrarProv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
