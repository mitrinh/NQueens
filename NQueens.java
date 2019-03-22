/*  Name: Michael Trinh
    Course: CS 4200
    Professor: Atanasio
    Project 2: NQueens
    Description: Implement 1) Simulated Annealing and 2) Genetic Algorithm to solve N-Queen problem
*/

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class NQueens {
    public final int numOfQueens;
    public final Chessboard board;

    public NQueens(int boardSize) {
        this.numOfQueens = boardSize;
        this.board = new Chessboard(boardSize);
    }

    // performs simulated annealing algorithm
    public void testSimulatedAnnealing(SimulatedAnnealing sa, int n, int iterations) throws FileNotFoundException {
        int solutions = 0, searchCosts = 0;
        long startTime = System.currentTimeMillis();
        for(int i=0;i<iterations;i++){
            NQueens nq = new NQueens(n); // initialize
            Chessboard myBoard = sa.simulatedAnnealing(nq.board);
            if(myBoard.getAllAttackingPairs() == 0) solutions++;
            searchCosts += myBoard.iterations;
            //saveBoard(myBoard, i, false);
        }
        long timeSpent = System.currentTimeMillis()-startTime;
        getResults(solutions, iterations, searchCosts, timeSpent);
    }

    // performs genetic algorithm
    public void testGeneticAlgorithm(GeneticAlgorithm ga, int n, int iterations) throws FileNotFoundException {
        int solutions = 0, searchCosts = 0;
        long startTime = System.currentTimeMillis();
        for(int i=0;i<iterations;i++){
            Chessboard myBoard = ga.geneticAlgorithm(n);
            if(myBoard.getAllAttackingPairs() == 0) solutions++;
            searchCosts += myBoard.iterations;
            //saveBoard(myBoard, i, true);
        }
        long timeSpent = System.currentTimeMillis()-startTime;
        getResults(solutions, iterations, searchCosts, timeSpent);
    }

    public static void saveBoard(Chessboard board, int iteration, boolean genetic) throws FileNotFoundException { // genetic = true -> genetic algorithm, false -> simulated annealing
        board.printBoard();
        String fileName = genetic ? "GA_"+iteration+".txt" : "SA_"+iteration+".txt";
        try (PrintWriter pw = new PrintWriter(fileName)) {
            for(int i = 0; i < board.size; i++){
                for(int j = 0; j < board.size; j++){
                    pw.write(board.board[i][j].symbol + " ");
                }
                pw.write('\n');
            }
        }
    }

    public static void getResults(int solutions, int iterations, int searchCosts, long timeSpent){
        System.out.println("Percentage of solved problems: " + 
                ((double)solutions/(double)iterations)*100 + "%");
        System.out.println("Average search cost: " + searchCosts/iterations + " search cost.");
        System.out.println("Average run time: " + (double)timeSpent/((double)iterations*1000.0) + " seconds.");
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Michael Trinh's NQueens Project");
        int n = 25, iterations = 1000;
        SimulatedAnnealing sa = new SimulatedAnnealing();
        GeneticAlgorithm ga = new GeneticAlgorithm();
        NQueens nq = new NQueens(n);
        nq.testSimulatedAnnealing(sa, n, iterations);
        //nq.testGeneticAlgorithm(ga, n, iterations);
    }
}