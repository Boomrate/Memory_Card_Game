package GameFrame;

import java.util.Random;

//BackBoard is what's under the cards, what player can't see.
public class BackBoard {

      BackBoard(int rows, int cols) {

            //create a data grid for the pictures
            picGrid = new int[rows][cols];

            //Initialize
            picNo = (cols * rows) / 2; //Number of pairs depends on the board size, so the difficulty
            pics = new int[picNo];

            //Randomly select the pictures in the game.
            selectPictures();

            //Randomly place the above selected Pictures (2 times each) in the Grid.
            PlacePictures(rows, cols);

            /*
             //BackBoard Data printer
             for (i = 0; i < rows; i++) {
             for (j = 0; j < cols; j++) {
             System.out.print(picGrid[i][j]+"   ");
             }
             System.out.println();
             }
            */
      }

      public int[][] getBackBoard() {
            //returns the grid
            return picGrid;
      }

      // <editor-fold defaultstate="collapsed" desc="SelectPictures Method">
      private void selectPictures() {
            //Select the first random Picture for the game
            rnd = (int) new Random().nextInt(20) + 1; //(Pics range 1-20)
            pics[0] = rnd;
            
            //Select the rest Pics and compare them to be unique
            for (i = 1; i < picNo; i++) {
                  rnd = (int) new Random().nextInt(20) + 1; //(Pics range 1-20)

                  for (j = i - 1; j >= 0; j--) {//Use selection algorithm to compare the pictures
                        if (pics[j] == rnd) {
                              i--; // if there is dublicate redo this loop
                              break;
                        }
                  }
                  if (j == -1) { //if there is unique save it into pics array
                        pics[i] = rnd;
                  }
            }
      }
    // </editor-fold>

      // <editor-fold defaultstate="collapsed" desc="PlacePictures Method">
      private void PlacePictures(int rows, int cols) {
            //Initialize all Grids to 0 (Pics range 1-20)
            for (i = 0; i < rows; i++) {
                  for (j = 0; j < cols; j++) {
                        picGrid[i][j] = 0;
                  }
            }

            //Select and position the Pistures in Grid where cell!=0 using the premade pics array with unique pics
            for (i = 0; i < picNo * 2; i++) {

                  //generate a position
                  x = (int) new Random().nextInt(rows);
                  y = (int) new Random().nextInt(cols);

                  //if the posistion in the grid is already taken redo the loop
                  if (picGrid[x][y] != 0) {
                        i--;
                  } else {//else save the picture data in grid
                        if (i >= picNo) { //if is the second copy, remove the start picNo 
                              int p = i - picNo;
                              picGrid[x][y] = pics[p];
                        } else {
                              picGrid[x][y] = pics[i];
                        }
                  }
            }
      }
    // </editor-fold>

      
      public int[][] picGrid;
      private int[] pics;
      private int rnd, picNo, i, j, x, y;

}
