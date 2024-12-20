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
public class VerPedidos extends CenteredFrame{

    /**
     * Creates new form VerPedidos
     */
    public VerPedidos() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        pedidoTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        Regresarbutt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pedidoTable.setBorder(new javax.swing.border.MatteBorder(null));
        pedidoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(pedidoTable);

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));

        Regresarbutt.setBackground(new java.awt.Color(0, 102, 255));
        Regresarbutt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Regresarbutt.setForeground(new java.awt.Color(255, 255, 255));
        Regresarbutt.setText("Regresar");
        Regresarbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarbuttActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Regresarbutt)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(Regresarbutt)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void RegresarbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarbuttActionPerformed
        PedidoF agE = new PedidoF();
        agE.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RegresarbuttActionPerformed

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
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerPedidos().setVisible(true);
            }
        });
    }

    public void generarTable() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Papeleria";
            String user = "postgres";
            String password = "9656";
            Connection con = DriverManager.getConnection(url, user, password);

            // Cambiar el esquema activo
            Statement stmt = con.createStatement();
            stmt.execute("SET search_path TO ciber_action");

            // Consulta SQL para obtener los nombres de proveedor y producto
            String sql = "SELECT p.Id_pedido, prov.Nombre AS Proveedor, p.Estado, p.Fecha_P, "
                    + "prod.Nombre AS Producto, dp.Cantidad, dp.Monto_total "
                    + "FROM Pedido p "
                    + "JOIN Proveedor prov ON p.Id_prov = prov.Id_prov "
                    + "JOIN Detalle_Pedido dp ON p.Id_pedido = dp.Id_pedido "
                    + "JOIN Producto prod ON dp.Id_prod = prod.Id_prod";

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
            // Suponiendo que tienes un JTable llamado 'pedidoTable'
            pedidoTable.setModel(model);

            // Cerrar la conexión
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresarbutt;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable pedidoTable;
    // End of variables declaration//GEN-END:variables
}
