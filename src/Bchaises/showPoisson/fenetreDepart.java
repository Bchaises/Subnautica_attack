package Bchaises.showPoisson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fenetreDepart extends JFrame{

    private JPanel PanelFenetreDepart;
    private JButton ArèneButton;
    private JButton ajoutExplorateurButton;
    private JButton ajoutDUnPoissonButton;
    private JButton captureDUnPoissonButton;

    public fenetreDepart(){


        add(PanelFenetreDepart);
        setTitle("BIENVENUE!");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArèneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e3) {
                    e3.printStackTrace();
                }
                interfacePoisson window = new interfacePoisson();
                window.setVisible(true);

            }
        });

        ajoutExplorateurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e3) {
                    e3.printStackTrace();
                }
                AjoutExplorateur AjoutExplorateur = new AjoutExplorateur();
                AjoutExplorateur.setVisible(true);
            }
        });
    }

}
