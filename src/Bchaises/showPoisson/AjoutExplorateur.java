package Bchaises.showPoisson;

import Bchaises.DBManager;
import Bchaises.Explorateur;
import Bchaises.Poisson;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AjoutExplorateur extends JFrame{

    private JPanel PanelAjoutPersonnage;
    private JPanel PanelAjoutExplorateur;
    private JTextField textFieldName;
    private JButton saveFileButton;
    private JButton validerButton;
    private JLabel JLabelPATH;
    private JButton retourÀLAccueilButton;
    private JLabel JLabelMessage;
    private JLabel Image;
    private String PATH_is;
    private String PATH_out;

    public AjoutExplorateur(){

        add(PanelAjoutExplorateur);
        setTitle("BIENVENUE!");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final boolean[] unknown = {true};

        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // on récupère l'image en la reconstruisant
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Save File");

                int result = jFileChooser.showSaveDialog(null);

                File image = jFileChooser.getSelectedFile();

                if (image != null){
                    PATH_is = image.getAbsolutePath();

                    String PATH_split[] = PATH_is.split("\\\\");

                    PATH_out = "src/Bchaises/images/explorateurs/" + PATH_split[PATH_split.length - 1];
                    System.out.println(PATH_out);

                    JLabelPATH.setText(PATH_is);
                    unknown[0] = false;
                }else{
                    PATH_out = "src/Bchaises/images/Unknown.png";
                    JLabelPATH.setText("Unknown");
                    unknown[0] = true;
                }

            }
        });

        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /* On récupère le textfield qui
                 * servira de nom pour l'image
                 * et la base de données
                 */
            if(!textFieldName.getText().equals(" ")){
                DBManager bdd = new DBManager();
                bdd.connection();

                String nom = null;
                nom = textFieldName.getText();

                ArrayList<Explorateur> tab_e = new ArrayList<>();
                try {
                    tab_e = bdd.getAllExplorateur();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                boolean existe = false;

                for (int ii = 0; ii < tab_e.size() ; ii++){
                    if (tab_e.get(ii).getNom().equals(nom)){
                        JLabelMessage.setText("Le personnage existe déjà.");
                        existe = true;
                        break;
                    }
                }

                InputStream is = null;
                OutputStream os = null;

                if (!existe){
                    if (unknown[0]){
                        PATH_out = "src/Bchaises/images/Unknown.png";
                        JLabelPATH.setText("Unknown");
                        try {
                            bdd.addExplorateur(nom, PATH_out);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }else{
                        try {
                            is = new FileInputStream(new File(PATH_is));
                            os = new FileOutputStream(new File(PATH_out));

                            byte[] buffer = new byte[8192];
                            int length;
                            while ((length = is.read(buffer)) > 0) {
                                os.write(buffer, 0, length);
                            }

                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }finally {
                            try {
                                is.close();
                                os.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                        try {
                            bdd.addExplorateur(nom, PATH_out);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }

                if(!JLabelMessage.getText().equals("")){
                    JLabelMessage.setText("");
                }

            }else{
                JLabelMessage.setText("Aucun nom renseigné.");
            }

            }
        });

        retourÀLAccueilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e3) {
                    e3.printStackTrace();
                }
                fenetreDepart window = new fenetreDepart();
                window.setVisible(true);
            }
        });
    }
}
