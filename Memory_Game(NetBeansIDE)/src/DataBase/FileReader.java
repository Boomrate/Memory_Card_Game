package DataBase;

//<editor-fold defaultstate="collapsed" desc="imports">
import GameFrame.MainBoard;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//</editor-fold>

public class FileReader {
      
      public void saveToDB(String data) {
            try {
                  File file = new File("C:\\Users\\Κωνσταντίνος\\Documents\\NetBeansProjects\\Memory_Game\\src\\DataBase\\Scor_DB.txt");
                  fr = new FileWriter(file, true);
                  br1 = new BufferedWriter(fr);
                  br1.write(data+"\n");
            } catch (IOException ex) {
                  Logger.getLogger(MainBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                  br1.close();
                  fr.close();
            } catch (IOException ex) {
                  Logger.getLogger(MainBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      
      public String readFromDB() {
            String data=new String();
            try {
                  br2 = new BufferedReader(new java.io.FileReader("C:\\Users\\Κωνσταντίνος\\Documents\\NetBeansProjects\\Memory_Game\\src\\DataBase\\Scor_DB.txt"));
                  String line;
                  while ((line = br2.readLine()) != null) {
                        data+=line+"\n";
                  }
            } catch (FileNotFoundException ex) {
                  Logger.getLogger(MainBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                  Logger.getLogger(MainBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            return data;
      }
      
      private FileWriter fr;
      private BufferedWriter br1;
      private BufferedReader br2;
}
