package GameFrame;

import java.awt.*;
import javax.swing.*;

//FrontBoard is the flippedcards, what player can see when opens the game.
public class FrontBoard{
    
    FrontBoard(int rows,int cols,int grid[][]){
        //create the picture grid 
        playingBoard=new JPanel(new GridLayout(rows,cols));
        label=new JLabel[rows][cols];
        
        //Initialize using the grid data from backBoard class
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/Images/"+ grid[i][j] +".png"));
                label[i][j]=new JLabel(icon,JLabel.CENTER);
                label[i][j].setPreferredSize(new java.awt.Dimension(160,160));
                label[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                playingBoard.add(label[i][j]);
            }   
        }  
    }
    
    public JPanel getPlayingBoard(){
        return playingBoard;
    }
    
    private JPanel playingBoard;
    public JLabel[][] label;
}
