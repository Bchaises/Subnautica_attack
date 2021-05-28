package Bchaises.showPoisson;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AjoutExplorateur extends JFrame{

    private JPanel PanelAjoutPersonnage;
    private JTextField labelTextField;
    private JPanel PanelAjoutExplorateur;
    private JTextField textField1;
    private JButton saveFileButton;
    private JTextArea JTextAreaContent;
    private JFileChooser dialogue;

    public AjoutExplorateur(){

        add(PanelAjoutExplorateur);
        setTitle("BIENVENUE!");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Save File");
                int result = jFileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File file = jFileChooser.getSelectedFile();
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(JTextAreaContent.getText().getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }
}
