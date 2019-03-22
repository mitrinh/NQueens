/*  Name: Michael Trinh
    Course: CS 4200
    Professor: Atanasio
    Project 2: NQueens
    Description: Implement 1) Simulated Annealing and 2) Genetic Algorithm to solve N-Queen problem
*/
import java.util.ArrayList;

public class Queen {
    public int x_position;
    public int y_position;
    public int attackingPairs;
    public char symbol;
    /* if no position specified, give it negative coordinate */
    public Queen(){
        this(-1,-1);
    }

    /* creates a Queen object with an x and y coordinate */
    public Queen(int x_position,int y_position) {
        this.attackingPairs = 0;
        this.x_position = x_position;
        this.y_position = y_position;
    }

    /* prints 'Q' if Queen was made, else prints - */
    public final void printQueen(){
        if(this.x_position == -1) {
            this.symbol = '.'; 
            System.out.print('.');
        }
        else { 
            this.symbol = 'Q';
            System.out.print('Q');
        }
    }

    public int getAttackingPairs(ArrayList<Queen> queenList) {
        int tempX,tempY; 
        this.attackingPairs = 0;
        for(Queen queen:queenList) {
            if(queen != this){
                // // horizontal pairs
                if(this.x_position == queen.x_position)                     
                    this.attackingPairs++;
                // vertical pairs, not needed since they are always at different columns
                // if(this.y_position == queen.y_position) 
                //     numOfAttackingPairs++;
                // diagonal pairs if the difference between the two queens are a slope of |1| 
                tempX = Math.abs(this.x_position - queen.x_position);
                tempY = Math.abs(this.y_position - queen.y_position);
                if(tempX == tempY)
                    this.attackingPairs++;                
            }
        }
        return this.attackingPairs;
    }
}