/*  Name: Michael Trinh
    Course: CS 4200
    Professor: Atanasio
    Project 2: NQueens
    Description: Implement 1) Simulated Annealing and 2) Genetic Algorithm to solve N-Queen problem
*/

public class SimulatedAnnealing {

    public SimulatedAnnealing() {}

    public Chessboard simulatedAnnealing(Chessboard node) {
        int totalAttackingPairs = node.getAllAttackingPairs(); // used to check if solution, temperature
        int iterations = 4000*node.size; // number of iterations until failure, increase for high board sizes
        double acceptanceRate = iterations; // initial % chance to perform increased cost moves
        // more iterations = more % to find solution
        for (int i = iterations; i > 0; i--) {
            if (totalAttackingPairs != 0) { // "intelligently" moves a queen until solution is found
                Queen current = getRandomAttackingQueen(node);
                Queen successor = getRandomSuccessor(current, node); // random successor
                if (getAttackingPairDifference(successor, current, node) < 0) // accept move if value[successor] < value[current]
                    totalAttackingPairs = moveQueen(successor, current, node);
                else { // else check if move can be accepted with probability
                    double randNum = (Math.random() * iterations + 1);
                    if (acceptanceRate >= randNum) { // check if random num in probability, else not accepted
                        totalAttackingPairs = moveQueen(successor, current, node);
                        acceptanceRate -= 16; // decay acceptance rate by 16
                    }
                }
            } 
            else return found(iterations, i, node);
        }
        return notFound(iterations, totalAttackingPairs, node);
    }

    private int getAttackingPairDifference(Queen successor, Queen current, Chessboard node){
        return successor.attackingPairs - current.attackingPairs;
    }

    private Chessboard found(int iterations, int i, Chessboard node) {
        node.iterations = iterations-i;
        System.out.println("Solution found within " + node.iterations + " iterations.");
        return node;
    }

    private Chessboard notFound(int iterations, int totalAttackingPairs, Chessboard node) {
        node.iterations = iterations;
        System.out.println("Solution not found within " + node.iterations + " iterations and " 
                + totalAttackingPairs + " attacking pairs.");
        return node;
    }

    private Queen getRandomAttackingQueen(Chessboard node) {
        int queenIndex = (int) (Math.random() * node.attackingQueensList.size());
        return node.attackingQueensList.get(queenIndex);
    }

    private Queen getRandomSuccessor(Queen queen, Chessboard node) {
        Queen resultQueen = new Queen(queen.x_position, queen.y_position);
        while (resultQueen.x_position == queen.x_position) {
            resultQueen.x_position = (int) (Math.random() * node.size);
        }
        resultQueen.getAttackingPairs(node.queenList);
        return resultQueen;
    }

    /*
     * replaces current queen position with successor; 
     * updates all queens with new board changes
     */
    private int moveQueen(Queen successor, Queen current, Chessboard node) {
        node.replaceQueen(successor, current);
        return node.getAllAttackingPairs();
    }
}