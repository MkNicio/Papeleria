/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria;

import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author julia
 */
public class VerEmpleados extends CenteredFrame{

    /**
     * Creates new form VerEmpleados
     */
    public VerEmpleados() {
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
        Regresarbutt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        EmTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        actualizarEmp = new javax.swing.JButton();
        borrarEmp = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Regresarbutt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Regresarbutt.setText("Regresar");
        Regresarbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarbuttActionPerformed(evt);
            }
        });
        jPanel1.add(Regresarbutt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        EmTable.setBorder(new javax.swing.border.MatteBorder(null));
        EmTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(EmTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 440, 230));

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));

        actualizarEmp.setBackground(new java.awt.Color(0, 102, 255));
        actualizarEmp.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        actualizarEmp.setForeground(new java.awt.Color(255, 255, 255));
        actualizarEmp.setText("Actualizar");
        actualizarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarEmpActionPerformed(evt);
            }
        });

        borrarEmp.setBackground(new java.awt.Color(0, 102, 255));
        borrarEmp.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        borrarEmp.setForeground(new java.awt.Color(255, 255, 255));
        borrarEmp.setText("Borrar");
        borrarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarEmpActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emp.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(15, 15, 15))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(actualizarEmp)
                    .addComponent(borrarEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39)
                .addComponent(actualizarEmp)
                .addGap(18, 18, 18)
                .addComponent(borrarEmp)
                .addGap(29, 29, 29))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            String sql = "SELECT * FROM empleado";
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
            EmTable.setModel(model);

            // Cerrar la conexión
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }

    private void borrarEmp() {
        try {
            // Verificar si se ha seleccionado una fila
            int selectedRow = EmTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado en la tabla.");
                return;
            }

            // Obtener el ID del empleado seleccionado
            int id = (int) EmTable.getValueAt(selectedRow, 0); // Suponiendo que el ID está en la primera columna

            // Mensaje de confirmación
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea borrar al empleado?", "Confirmar Borrado", JOptionPane.YES_NO_OPTION);
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

            // Consulta SQL para borrar un empleado por su ID
            String sql = "DELETE FROM empleado WHERE id_em = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            // Ejecutar la consulta
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.");
                generarTable(); // Actualiza la tabla después de borrar
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un empleado con ese ID.");
            }

            pstmt.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado: " + e.getMessage());
        }
    }

    private void actualizarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarEmpActionPerformed
        int selectedRow = EmTable.getSelectedRow();
        if (selectedRow != -1) {
            // Convertir los valores obtenidos de la tabla a String de forma segura
            int id = (int) EmTable.getValueAt(selectedRow, 0);
            String nombre = EmTable.getValueAt(selectedRow, 1).toString();
            String telefono = EmTable.getValueAt(selectedRow, 2).toString();
            String correo = EmTable.getValueAt(selectedRow, 3).toString();
            String fechac = EmTable.getValueAt(selectedRow, 4).toString();

            // Abre el frame para actualizar proveedor
            new actualizarEmpleado(id, nombre, telefono, correo, fechac).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla.");
        }
    }//GEN-LAST:event_actualizarEmpActionPerformed

    private void RegresarbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarbuttActionPerformed
        agregarEmpleados agE = new agregarEmpleados();
        agE.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RegresarbuttActionPerformed

    private void borrarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarEmpActionPerformed
        borrarEmp();
    }//GEN-LAST:event_borrarEmpActionPerformed

    private void EmTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_EmTableMouseClicked

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
            java.util.logging.Logger.getLogger(VerEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable EmTable;
    private javax.swing.JButton Regresarbutt;
    private javax.swing.JButton actualizarEmp;
    private javax.swing.JButton borrarEmp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
