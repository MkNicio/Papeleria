/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author julia
 */
public class CenteredFrame extends JFrame {

    public CenteredFrame() {
        // Configurar el JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Tamaño predeterminado

        // Centrar el JFrame después de establecer el tamaño
        centerFrame();
    }

    private void centerFrame() {
        // Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;

        // Calcular la posición central del JFrame
        int x = (width - getWidth()) / 2;
        int y = (height - getHeight()) / 2;

        // Establecer la ubicación del JFrame en el centro de la pantalla
        setLocation(x, y);
    }
}
