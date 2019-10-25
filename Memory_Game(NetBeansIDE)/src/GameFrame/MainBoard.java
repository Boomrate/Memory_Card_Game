package GameFrame;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.awt.*;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import DataBase.FileReader;
//</editor-fold>

public class MainBoard extends JFrame implements MouseListener {

      public MainBoard(int rows, int cols, String name) {
          this.cols = cols;
          this.rows = rows;
          this.name = name;
          
          //pairs of cards
          pairsAvail=(rows*cols)/2;
          
          if(pairsAvail==6)
                dif="Easy";
          else if(pairsAvail==10)
                dif="Medium";
         else
                dif="Hard";
          
          // Component initialization
          initComponents();
      }

      private void initComponents() {

            //<editor-fold defaultstate="collapsed" desc="Frame Initialization">
            setTitle("Memory Game");

            //Set frame Icon
            String filepath = url + "gameIcon.png";
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(filepath)));

            //BackBoard Creation (What player cant see under the cards)
            backBoard = new BackBoard(rows, cols);
            //PlayingBoard Creation (What player see)
            frontBoard = new FrontBoard(rows, cols, backBoard.picGrid);

            //Center it to the middle of the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width / 2 - (cols * 80), dim.height / 2 - (rows * 90));

            //Add the board to the Frame
            add(frontBoard.getPlayingBoard());
            
            //Toolbar init
            tb=new JToolBar();
            
            min=((rows*cols)/2)/3;
            timerLabel=new JLabel("<html><b>Time Remaining:</b>[ 0"+min+":0"+sec+" ]</html>");
            scoreLabel=new JLabel("<html><b>Score:</b> ["+name+":Computer]</html>");
            infoLabel=new JLabel("<html><b>"+dif+"-Pairs:</b> ["+pairsAvail+"]</html>");
            
            //add items in toolbar
            tb.add(timerLabel);
            tb.addSeparator(new Dimension(120,0));
            tb.add(scoreLabel);
            tb.addSeparator(new Dimension(120,0));
            tb.add(infoLabel);
            
            tb.setLayout(new FlowLayout(FlowLayout.CENTER));
            tb.setFloatable(false);
            add(tb,BorderLayout.SOUTH);
            
            pack();
            setResizable(false);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            //</editor-fold>
            
            //By pressing escape a menu going to show up, with players options
            menu=new MenuDialog(this);
            
            //Create an instance of the file reader and writer class
            fr = new FileReader();

            //<editor-fold defaultstate="collapsed" desc="Second Thread(used to show off images and keep timer)">
            final CyclicBarrier gate = new CyclicBarrier(2);

            t = new Thread() {
                  public void run() {
                        try {
                              // thread t hits the barrier and waits
                              gate.await();
                              
                              //sleep specific time depends on difficulty
                              //(it waits so the player can take a look of the cards before turn upside down)
                              sleep(rows*cols*120);
                              
                              //Turn all pictures upside down
                              for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < cols; j++) {
                                          frontBoard.label[i][j].setIcon(new ImageIcon(getClass().getResource(url + "flipped.png")));
                                    }
                              }
                              
                              //Start timer
                              for(min-=1;min>=0;min--){
                                    for(sec=59;sec>=0;sec--){
                                          //sleep 1 sec to calculate the time units
                                          sleep(1000);
                                          if(sec>9)
                                                timerLabel.setText("<html><b>Time Remaining:</b>[ 0"+min+":"+sec+" ]</html>");
                                          else
                                                timerLabel.setText("<html><b>Time Remaining:</b>[ 0"+min+":0"+sec+" ]</html>");
                                    }
                              }
                              min=sec=0;
                              //if time runs out call the game draw
                              endGame("Draw");
                              
                        } catch (InterruptedException ex) {
                              System.out.println("Second thread issue");
                        } catch (BrokenBarrierException ex) {
                              System.out.println("Second thread issue");
                        }
                        
                  }
            };
            t.start();

            // main thread hits the barrier and wait( when both threads are in place the script unlocks and start running)
            try {
                  gate.await();
            } catch (InterruptedException ex) {
                  System.out.println("Second thread issue");
            } catch (BrokenBarrierException ex) {
                  System.out.println("Second thread issue");
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Add MouseListener to the labels">
            for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < cols; j++) {
                        frontBoard.label[i][j].addMouseListener(this);
                  }
            }
            //</editor-fold>
      }

      //<editor-fold defaultstate="collapsed" desc="MouseListener">
      @Override
      public void mouseClicked(MouseEvent e) {}
      @Override
      public void mousePressed(MouseEvent e) {
            //if computer's turn just ended
            if (computerTurn) {
                  paintBoard();
            }

            //active label (the one just clicked)
            active = (JLabel) e.getComponent();
            if (opened == 0) {
                  //save it as previus if its first of two cards selected
                  previus = active;
            }
            
            //search the labels to find the position of the active
            for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < cols; j++) {
                        if (active == frontBoard.label[i][j]) {
                              openCard(i, j); //Open the card at this position
                        }
                  }
            }
      }
      @Override
      public void mouseReleased(MouseEvent e) {
            //If the opened cards are 2
            if (opened == 2) {
                  //checks if the cards are the same or not and flips them
                  checkCards();
                  
                  //if there's at least on pair on the table 
                  if (pairsAvail >= 1) {
                        pcTurn();
                  }
            }

      }
      @Override
      public void mouseEntered(MouseEvent e) {}
      @Override
      public void mouseExited(MouseEvent e) {}
    //</editor-fold>

      
      //<editor-fold defaultstate="collapsed" desc="checkCards()">
      private void checkCards() {
            //turn the opened cards to 0
            opened = 0;
            
            //sleep one sec, so the player can see what just opened
            try {
                  sleep(1000);
            } catch (InterruptedException ex) {
                  Logger.getLogger(MainBoard.class.getName()).log(Level.SEVERE, null, ex);
            }

            //if the cards are the same
            if (backBoard.picGrid[img1_X][img1_Y] == backBoard.picGrid[img2_X][img2_Y]) {

                  //Remove them from game (make their data on grid=0) and replace the img with the empty one 
                  String newUrl = url + "empty2.png";
                  frontBoard.label[img1_X][img1_Y].setIcon(new ImageIcon(getClass().getResource(newUrl)));
                  backBoard.picGrid[img1_X][img1_Y] = 0;

                  frontBoard.label[img2_X][img2_Y].setIcon(new ImageIcon(getClass().getResource(newUrl)));
                  backBoard.picGrid[img2_X][img2_Y] = 0;
                  
                  //Pairs available - 1
                  pairsAvail--;
                  //Player gains 10 points
                  pScore+=10;
                  
                  //Refresh the information labels, for the scor and pairs available
                  infoLabel.setText("<html><b>"+dif+"-Pairs:</b> ["+pairsAvail+"]</html>");
                  scoreLabel.setText("<html><b>Score:</b> ["+pScore+":"+cScore+"]</html>");
                  
            } else {//If the cards are different, turn the upside down
                  String newUrl = url + "flipped.png";
                  frontBoard.label[img1_X][img1_Y].setIcon(new ImageIcon(getClass().getResource(newUrl)));
                  frontBoard.label[img2_X][img2_Y].setIcon(new ImageIcon(getClass().getResource(newUrl)));
            }
            
            //Check if the pairs available are 0 and end game
            if(pairsAvail==0){
                  endGame(name);
            }
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="openCard(row,col)">
      private void openCard(int row, int col) {
        //Check the data of the card to be !=0 and check if the opened cards are <2 
        // ( cause 0 data means opened card )
        if (backBoard.picGrid[row][col] != 0 && opened < 2) {
            //if the card is the second you open and you just click the same
            if (opened == 1 && active == previus); //do nothing
            
            //If its a different one
            else {
                //Use the data of the card in the grid and open it
                String newUrl = url + backBoard.picGrid[row][col] + ".png";
                frontBoard.label[row][col].setIcon(new ImageIcon(getClass().getResource(newUrl)));
                opened++; //Opened another one
                
                if (opened == 1) {//Store the data of the 1st card
                    img1_X = row;
                    img1_Y = col;
                }
                if (opened == 2) {//Store the data of the 2st card, to be able to compare them
                    img2_X = row;
                    img2_Y = col;
                }

            }
        }
    }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="pcTurn()">
      private void pcTurn() {
            //Create a 4position array for the pc data
            int[] pcArray = new int[4];
            //create a computer instance with the size of the board
            Player_Bot computer = new Player_Bot(rows, cols);

            //Take 2 random pictures (x1,x2,y1,y2) (bot selected, thats not been opened)
            pcArray = computer.play(backBoard.getBackBoard());

            //Image turn 1 ( x1 , _ , y1 , _ )
            openCard(pcArray[0],pcArray[2]);
                    
            //Image turn 2 ( _ , x2 , _ , y2 )
            openCard(pcArray[1],pcArray[3]);
            
            //If the cards are the same, make their backBoard data 0, so when the player's turn starts, going to be empty with the paintBoard()
            if (backBoard.picGrid[pcArray[0]][pcArray[2]] == backBoard.picGrid[pcArray[1]][pcArray[3]]) {
                  backBoard.picGrid[pcArray[0]][pcArray[2]] = 0;
                  backBoard.picGrid[pcArray[1]][pcArray[3]] = 0;
                  
                  //Pairs Available - 1
                  pairsAvail--;
                  //Pc gains 10 points
                  cScore+=10;
                  
                  //Refresh the information labels
                  infoLabel.setText("<html><b>"+dif+"-Pairs:</b> ["+pairsAvail+"]</html>");
                  scoreLabel.setText("<html><b>Score:</b> ["+pScore+":"+cScore+"]</html>");
            }
            //if the cards are different, dont change data and when player's turn start, going to be flipped
       
            //Opened cards are 0
            opened=0;
            //And the pc just played
            computerTurn=true;
            
            //Check if pairs avail = 0 and end game
            if(pairsAvail==0)
                  endGame(name);
      }
      //</editor-fold>
      
      //<editor-fold defaultstate="collapsed" desc="paintBoard()">
      private void paintBoard(){
            //computer turn ends
            computerTurn=false;
            
            //search the labels data in the grid
            for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < cols; j++) {
                        //if the data are 0, means they are opened, so replace them with empty
                        if(backBoard.picGrid[i][j]==0){
                              String newUrl = url + "empty2.png";
                              frontBoard.label[i][j].setIcon(new ImageIcon(getClass().getResource(newUrl)));
                        }
                        //if the data are not 0, means that they are still in game, so turn them upside down
                        else{
                              String newUrl = url + "flipped.png";
                              frontBoard.label[i][j].setIcon(new ImageIcon(getClass().getResource(newUrl)));
                        }
                  }
            }
      }
      //</editor-fold>
      
      
      //<editor-fold defaultstate="collapsed" desc="endGame()">
      private void endGame(String winner) {
            
            //If the winner="draw" means the t thread call it and the time ran out
            if(winner=="Draw"){
                  fr.saveToDB(">Winner: -"+winner+"-  |  Time: [00:00]  |  Score: ["+pScore+":"+cScore+"]  |  Difficulty: ["+dif+"]");
                  
                  JOptionPane.showMessageDialog(null, "Game Finished Draw!\n -Time ran out! ", "Memory Game", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                  //else check the highest score, that wins
                  if(pScore<cScore)
                        winner="Computer";
                  
                  if(sec>9)
                        fr.saveToDB(">Winner: ["+winner+"]  |  Time: [0"+min+":"+sec+"]  |  Score: ["+pScore+":"+cScore+"]  |  Difficulty: ["+dif+"]");
                  else
                        fr.saveToDB(">Winner: ["+winner+"]  |  Time: [0"+min+":0"+sec+"]  |  Score: ["+pScore+":"+cScore+"]  |  Difficulty: ["+dif+"]");
                  
                  JOptionPane.showMessageDialog(null, "Game Finished!\n -Winner is "+winner+"!! :) ", "Memory Game", JOptionPane.INFORMATION_MESSAGE);
            }
            
            //stop the thread
            t.stop();
            
            //open the menu dialog for the users options
            menu.dialog.setVisible(true);
      }
      //</editor-fold>
      
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private FrontBoard frontBoard;
    private BackBoard backBoard;
    private MenuDialog menu;
    private int opened=0,img1_X,img1_Y,img2_X,img2_Y,min,sec=0,pScore=0,cScore=0;
    private boolean computerTurn=false;
    private JFrame frame=this;
    private JLabel active,previus,timerLabel,scoreLabel,infoLabel;
    private JToolBar tb;
    private String dif;
    
    public int rows, cols,pairsAvail;
    public String name, url = "/Images/";
    public Thread t;
    public FileReader fr;
    //</editor-fold> 
}
