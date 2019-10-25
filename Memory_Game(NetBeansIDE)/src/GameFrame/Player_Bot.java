package GameFrame;

import java.util.Random;

public class Player_Bot {

      Player_Bot(int rows, int cols) {
            this.cols = cols;
            this.rows = rows;
            grid = new int[rows][cols];
      }

      int[] play(int[][] grid) {

            //rnd[0] x1 rnd[1] x2
            //rnd[2] y1 rnd[3] y2
            
            //while check method returns false, continue loop
            while (!check()) {
                  this.grid = grid;

                  //Generate x1 and x2
                  rnd[0] = (int) new Random().nextInt(rows);
                  rnd[1] = (int) new Random().nextInt(rows);

                  //Generate y1 and y2
                  for (int i = 0; i < 1; i++) {
                        rnd[2] = (int) new Random().nextInt(cols);

                        rnd[3] = (int) new Random().nextInt(cols);

                        //Check if x1==x2 and y1==y2 that means are the same selected pictures
                        if ((rnd[0] == rnd[1]) && (rnd[2] == rnd[3])) {
                              i--; //Then redo the loop
                        }
                  }
            }
            
            /*
            //Computer pictures selected
            System.out.println("Pos1:" + rnd[0] + " ," + rnd[2] + " Pos2:" + rnd[1] + " ," + rnd[3]);
            System.out.println();
            */
            
            return rnd;
      }

      private boolean check() {
            //Check if the 2 pictures that pc selected are already empty
            if ((grid[rnd[0]][rnd[2]] == 0) || (grid[rnd[1]][rnd[3]] == 0)) {
                  return false;
            } else {
                  return true;
            }
      }

      private int rows, cols;
      private int[] rnd = new int[4];
      private int[][] grid;
}
