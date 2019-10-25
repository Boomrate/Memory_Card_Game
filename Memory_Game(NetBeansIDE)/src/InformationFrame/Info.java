package InformationFrame;

import GameFrame.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Info extends JFrame {

      //static instance, Singleton design pattert
      private static Info instance = new Info();

      private Info() {
            initComponents();
      }

      private void initComponents() {
            
        // <editor-fold defaultstate="collapsed" desc="Panel Initialize">
            //frame initialization
            setLayout(null);
            setPreferredSize(new Dimension(480, 260));
            setTitle("Memory Game");
            setResizable(false);
            
            //Set frame Icon
            String filepath = "/Images/gameIcon.png";
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(filepath)));
            
            //Open in the middle of screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width / 2 - 240, dim.height / 2 - 130);

            // Importing name textfield
            JLabel requestName = new JLabel("Player Name");
            requestName.setBounds(180, 10, 100, 30);
            requestName.setHorizontalAlignment(SwingConstants.CENTER);
            requestName.setFont(new Font("Vardana", Font.BOLD, 12));

            name = new JTextField();
            name.setBounds(120, 45, 220, 30);

            // Importing game difficulty labels
            JLabel requestDiff = new JLabel("Difficulty");
            requestDiff.setBounds(178, 95, 100, 30);
            requestDiff.setHorizontalAlignment(SwingConstants.CENTER);
            requestDiff.setFont(new Font("Vardana", Font.BOLD, 12));

            // Initialize Radio Buttons
            JRadioButton easy = new JRadioButton("Easy");
            easy.setBounds(80, 120, 120, 45);
            JRadioButton medium = new JRadioButton("Medium");
            medium.setBounds(195, 120, 120, 45);
            JRadioButton hard = new JRadioButton("Hard");
            hard.setBounds(325, 120, 120, 45);

            // Grouping the difficulty Radio buttons 
            ButtonGroup diff = new ButtonGroup();
            diff.add(easy);
            diff.add(medium);
            diff.add(hard);

            // Default difficulty selection
            easy.setSelected(true);

            //button play creation and initialization
            JButton play = new JButton("Play");
            play.setBounds(190, 180, 80, 25);
            play.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        if (easy.isSelected()) {
                              MainBoard game = new MainBoard(3, 4, name.getText());  // Depends on the radio button selection
                              game.setVisible(true);                                 //creates an instance of Mainboard and sets the frame visible
                        } else if (medium.isSelected()) {
                              MainBoard game = new MainBoard(4, 5, name.getText());
                              game.setVisible(true);
                        } else if (hard.isSelected()) {
                              MainBoard game = new MainBoard(6, 6, name.getText());
                              game.setVisible(true);
                        }
                        dispose();
                  }
            });

            //Add items to the frame
            add(requestName);
            add(name);
            add(requestDiff);
            add(easy);
            add(medium);
            add(hard);
            add(play);

            
            pack();
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // </editor-fold>
            
      }

      public static Info getInstace() {
            return instance;
      }

      public void Start_Game() {
            //set this frame visible
            this.setVisible(true);
      }

      private JTextField name;
}
