/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author julia
 */
public class MenuGerente extends CenteredFrame {

    /**
     * Creates new form MenuGerente
     */
    public MenuGerente() {
        initComponents();
        this.getContentPane().setBackground(new Color(0x86C7E7));
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
        Regresar = new javax.swing.JButton();
        RevHisPed = new javax.swing.JButton();
        AgregarE = new javax.swing.JButton();
        AgregarProv = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        AgregarPD1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setForeground(new java.awt.Color(0, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Regresar.setText("Regresar");
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });
        jPanel1.add(Regresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        RevHisPed.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        RevHisPed.setText("Historial de pedidos");
        RevHisPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RevHisPedActionPerformed(evt);
            }
        });
        jPanel1.add(RevHisPed, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, -1, -1));

        AgregarE.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        AgregarE.setText("Empleados");
        AgregarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarEActionPerformed(evt);
            }
        });
        jPanel1.add(AgregarE, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 269, -1, -1));

        AgregarProv.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        AgregarProv.setText("Proovedores");
        AgregarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProvActionPerformed(evt);
            }
        });
        jPanel1.add(AgregarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 269, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emp.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 110, 110));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ppa.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 100, 120));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prov.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 110, 120));

        AgregarPD1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        AgregarPD1.setText("Productos");
        AgregarPD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarPD1ActionPerformed(evt);
            }
        });
        jPanel1.add(AgregarPD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 269, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reloj.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProvActionPerformed
        agregarProovedores agp = new agregarProovedores();
        agp.setVisible(true); // Mostrar el frame agregarProovedores
        this.dispose(); // Cerrar el frame actual (MenuGerente)
    }//GEN-LAST:event_AgregarProvActionPerformed

    private void AgregarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarEActionPerformed
        agregarEmpleados age = new agregarEmpleados();
        age.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_AgregarEActionPerformed

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
        new NewJFrame().setVisible(true); // Mostrar el frame de Login
        this.dispose(); // Cerrar el frame actual (Empleado)
    }//GEN-LAST:event_RegresarActionPerformed

    private void RevHisPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RevHisPedActionPerformed
        VerPedGerente vf = new VerPedGerente();
        vf.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_RevHisPedActionPerformed

    private void AgregarPD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarPD1ActionPerformed
        agregarProductos vf = new agregarProductos();
        vf.setVisible(true); // Mostrar el frame de MenuGerente
        this.dispose(); // Cerrar el frame actual
    }//GEN-LAST:event_AgregarPD1ActionPerformed

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
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarE;
    private javax.swing.JButton AgregarPD1;
    private javax.swing.JButton AgregarProv;
    private javax.swing.JButton Regresar;
    private javax.swing.JButton RevHisPed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
