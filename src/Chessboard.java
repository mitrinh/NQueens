/*  Name: Michael Trinh
    Course: CS 4200
    Professor: Atanasio
    Project 2: NQueens
    Description: Implement 1) Simulated Annealing and 2) Genetic Algorithm to solve N-Queen problem
*/
import java.util.ArrayList;

public class Chessboard {
    public int size, iterations, attackingPairs, fitness;
    public Queen[][] board;
    public ArrayList<Queen> queenList, attackingQueensList;
    /**
     *  initializes chessboard with each queen mapped to a column 
     *  placement of queen on each column is random
     */
    public Chessboard(int numOfQueens) {
        this(numOfQueens,true);
        addQueensToBoard();
    }

    /* used to initialize empty chessboard */
    public Chessboard(int numOfQueens, boolean temp) {
        this.size = numOfQueens;
        this.board = new Queen[this.size][this.size];
        this.queenList = new ArrayList<>();
        this.attackingQueensList = new ArrayList<>();
        this.iterations = 0;
        this.attackingPairs = 0;
        this.fitness = 0;
        // fill board with empty tiles
        this.initializeBoard();
    }

    private void initializeBoard(){
        for(int i=0; i<this.size; i++) {
            for(int j=0; j<this.size; j++){
                this.board[i][j] = new Queen();
            }
        }
    }

    public void addQueensToBoard() {
        int randomNumber;
        Queen queen;
        for(int i=0; i<this.size; i++) {
            randomNumber = (int)(Math.random() * this.size);
            // select a number from 0 - chessboard size
            queen = new Queen(randomNumber, i);
            this.board[randomNumber][i] = queen;
            this.queenList.add(queen);
        }
    }

    public void replaceQueen(Queen newQueen, Queen oldQueen){
        this.board[oldQueen.x_position][oldQueen.y_position].x_position = -1; // removes queen from board
        this.queenList.remove(oldQueen);
        this.board[newQueen.x_position][newQueen.y_position] = newQueen;
        this.queenList.add(newQueen);
    }

    public Queen[][] getBoard(){
        return this.board;
    }

    public void setQueen(Queen queen){
        this.board[queen.x_position][queen.y_position] = queen;
        this.queenList.add(queen);
    }

    public final void printBoard() {
        for(int i=0; i<this.size; i++) {
            for(int j=0; j<this.size; j++){
                this.board[i][j].printQueen();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void setFitness(int fitness){
        this.fitness = fitness;
    }

    /* Will give inflated attacking pairs but doesn't matter in solving NQueens problem 
     * Duplicates can be removed with keeping track of attacking pairs 
     * Also sets the list of attacking queens */
    public final int getAllAttackingPairs() {
        this.attackingPairs = 0;
        int queenResult;
        this.attackingQueensList = new ArrayList<>();
        // O(n^2)
        for(Queen queen:queenList) {
            queenResult = queen.getAttackingPairs(queenList);
            this.attackingPairs += queenResult;
            if(queenResult > 0) this.attackingQueensList.add(queen);
        }
        this.attackingPairs /= 2;
        return this.attackingPairs;
    }
}