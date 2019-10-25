package memory_game;

import InformationFrame.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Memory_Game {

      public static void main(String[] args) {

            //<editor-fold defaultstate="collapsed" desc=" Look and feel (Nimbus) ">
            {
                  try {
                        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                              if ("Nimbus".equals(info.getName())) {
                                    UIManager.setLookAndFeel(info.getClassName());
                                    break;
                              }
                        }
                  } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(Memory_Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                  } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(Memory_Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                  } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Memory_Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                  } catch (UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(Memory_Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                  }
            }//</editor-fold>
            
            //Dimiourgw ena instance tou information_frame
            Info game = Info.getInstace();

            //Mesw autou kalw th methodo start_game
            game.Start_Game();
      }
}
