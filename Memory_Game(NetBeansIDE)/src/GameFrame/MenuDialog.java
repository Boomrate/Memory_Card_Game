package GameFrame;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.awt.*;
import java.awt.event.*;
import memory_game.Memory_Game;
import javax.swing.*;
//</editor-fold>

public class MenuDialog {
      
      MenuDialog(MainBoard ins){
            
            //<editor-fold defaultstate="collapsed" desc="Dialog init">
            dialog = new EscapeDialog(ins, "MENU"); //initialize dialog to be an EscapeDialog()
            dialog.setLayout(null);

            //Button creation
            
            //<editor-fold defaultstate="collapsed" desc="NewGame Button">
            JButton newG = new JButton("New Game");
            ActionListener innerActionListener1 = new ActionListener() {
                  public void actionPerformed(ActionEvent actionEvent) {
                        Memory_Game newgame = new Memory_Game();  //Creates an instance of the main class again, stop thread t
                        ins.t.stop();                             //and dispoce old data
                        ins.dispose();
                        newgame.main(null);                       //run the newgame
                  }
            };
            newG.addActionListener(innerActionListener1);
            newG.setBounds(45, 20, 100, 30);
            dialog.getContentPane().add(newG);
                        //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Restart Button">
            JButton restart = new JButton("Restart");
            ActionListener innerActionListener2 = new ActionListener() {
                  public void actionPerformed(ActionEvent actionEvent) {
                        MainBoard game = new MainBoard(ins.rows, ins.cols, ins.name); 
                        ins.t.stop();                                         // Creates instance of the MainBoard again with same data
                        ins.dispose();                                        //stops the thread t and dispoce old data
                        game.setVisible(true);                                //Last sets the frame visible 
                  }
            };
            restart.addActionListener(innerActionListener2);
            restart.setBounds(45, 60, 100, 30);
            dialog.getContentPane().add(restart);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="History Button">
            JButton history = new JButton("History");
            ActionListener innerActionListener3 = new ActionListener() {
                  public void actionPerformed(ActionEvent actionEvent) {
                        //Takes the data string from the Db file
                        String data = ins.fr.readFromDB(); 
                        
                        //Dialog msg for the games history
                        JOptionPane.showMessageDialog(ins, "Previus Games data:\n\n" + data, "MemoryGame History", JOptionPane.PLAIN_MESSAGE);
                  }
            };
            history.addActionListener(innerActionListener3);
            history.setBounds(45, 100, 100, 30);
            dialog.getContentPane().add(history);
                        //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="About Button">
            JButton about = new JButton("About");
            ActionListener innerActionListener4 = new ActionListener() {
                  public void actionPerformed(ActionEvent actionEvent) {
                        JOptionPane.showMessageDialog(null, "Java Memory Game\nSummer Semester Project 2019\n "
                                + "\nDeveloped by:\nKostas_Alexandris & Thanos_Kalpourtzis ", "Memory Game", JOptionPane.INFORMATION_MESSAGE);
                  }
            };
            about.addActionListener(innerActionListener4);
            about.setBounds(45, 140, 100, 30);
            dialog.getContentPane().add(about);
                        //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Exit Button">
            JButton exit = new JButton("Exit Game");
            ActionListener innerActionListener5 = new ActionListener() {
                  public void actionPerformed(ActionEvent actionEvent) {
                        ins.dispose(); //dispose data
                        ins.t.stop();  //stop thread t
                  }
            };
            exit.addActionListener(innerActionListener5);
            exit.setBounds(45, 180, 100, 30);
            dialog.getContentPane().add(exit);
            //</editor-fold>

            //dialog initializaton
            dialog.setSize(200, 260);
            dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/gameIcon.png")));
            dialog.show();

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setLocation(dim.width / 2 - 100, dim.height / 2 - 100);
            dialog.setVisible(false);
            dialog.setResizable(false);
            //</editor-fold>
            
            //Get keystroke on escape
            JPanel content = (JPanel) ins.getContentPane();
            KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

            //when stroke, open the dialog
            InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            inputMap.put(stroke, "OPEN");
            content.getActionMap().put("OPEN", new AbstractAction() {
                  public void actionPerformed(ActionEvent actionEvent) {
                        dialog.setVisible(true);
                  }
            });
      }
      
      //static dialog so it will created one time
      static JDialog dialog;
}

//<editor-fold defaultstate="collapsed" desc="EscapeDialog">

class EscapeDialog extends JDialog {

      //Dialog constructors
  public EscapeDialog(Frame owner, String title) {
    this(owner, title, false);
  }
  public EscapeDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
  }

      //In case of opened Menu and escape hits again, close window (not visible)
  protected JRootPane createRootPane() {
        
    JRootPane rootPane = new JRootPane();
    KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
    
    Action actionListener = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
      }
    };
    
    InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(stroke, "ESCAPE");
    rootPane.getActionMap().put("ESCAPE", actionListener);

    return rootPane;
  }
}
//</editor-fold>