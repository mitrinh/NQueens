/*  Name: Michael Trinh
    Course: CS 4200
    Professor: Atanasio
    Project 2: NQueens
    Description: Implement 1) Simulated Annealing and 2) Genetic Algorithm to solve N-Queen problem
*/

import java.util.ArrayList;

public class GeneticAlgorithm {
    public ArrayList<Integer> fitnessList;
    public int mutationRate;
    public int numOfQueens;
    public GeneticAlgorithm() { fitnessList = new ArrayList<>();}

    public Chessboard geneticAlgorithm(int numOfQueens) {
        final int initialPopulation = 10*numOfQueens;// population size is 10*n
        final int iterations = 100000; // max generations
        this.numOfQueens = numOfQueens;
        this.mutationRate = 5; // 5% to mutate a random queen in child
        Chessboard solution;
        ArrayList<Chessboard> parents = generateInitialBoards(initialPopulation);
        for(int i = iterations; i>0; i--) {
            solution = testForSolution(parents);
            if(solution.attackingPairs == 0)
                return found(iterations, i, solution);
            ArrayList<Chessboard> children = new ArrayList<>();
            // sets fitness of a population
            ArrayList<Chessboard> fitnessLottery = getFitnessLottery(parents); // size = totalFitness
            children = generateChildren(children, initialPopulation, fitnessLottery, parents);
            parents = children; // children become the new parents
        }
        return notFound(iterations, parents);
    }
    
    /* generates a list of boards to choose from */
    private ArrayList<Chessboard> generateInitialBoards(int kStates){
        ArrayList<Chessboard> result = new ArrayList<>();
        for(int i=0; i < kStates; i++){
            Chessboard board = new Chessboard(numOfQueens);
            result.add(board);
        }
        return result;
    }

    // set fitness for all boards
    private void setAllFitness(int numOfQueens,ArrayList<Chessboard> boards) {
        int maxFitness = numOfQueens * (numOfQueens-1) / 2;
        for(Chessboard board:boards){ // sets fitness for each board and add to totalFitness
            // fitness = number of non-attacking queen pairs
            int fitness = board.getAllAttackingPairs();
            fitness = maxFitness - fitness;
            board.setFitness(fitness);
        }
    }

    /* adds corresponding queen to lottery, for number of times = fitness
     * total boards = size of lottery, each fitness amount in boards, added to lottery */
    private ArrayList<Chessboard> getFitnessLottery(ArrayList<Chessboard> boards){
        setAllFitness(numOfQueens, boards);
        ArrayList<Chessboard> result = new ArrayList<>();
        for(Chessboard board:boards){
            for(int j = 0; j < board.fitness; j++){
                result.add(board);
            }
        }
        return result;
    }

    /* creates children = initialPopulation */
    private ArrayList<Chessboard> generateChildren(ArrayList<Chessboard> children,
            int initialPopulation,ArrayList<Chessboard> fitnessLottery, ArrayList<Chessboard> parents){
        while (children.size() != initialPopulation){
            Chessboard parent1 = selectRandomParent(fitnessLottery);
            Chessboard parent2 = selectRandomParent(fitnessLottery);
            addChildren(children,parent1,parent2, fitnessLottery, parents);
            addChildren(children,parent2,parent1, fitnessLottery, parents);
        }
        return children;
    }

    /* Selection */
    private Chessboard selectRandomParent(ArrayList<Chessboard> fitnessLottery) {
        int randNum = (int)(Math.random()*fitnessLottery.size());
        Chessboard parent = fitnessLottery.get(randNum);
        return parent;
    }

    /* Performs crossover and mutation, and adds child to population */
    private ArrayList<Chessboard> addChildren(ArrayList<Chessboard> children,Chessboard parent1,
            Chessboard parent2, ArrayList<Chessboard> fitnessLottery, ArrayList<Chessboard> parents) {
        Chessboard child = new Chessboard(numOfQueens,true); // create empty chessboard
        int crossoverPoint = (int)(Math.random()*numOfQueens);
        child = crossover(parent1,parent2,crossoverPoint);
        child = mutate(child);
        children.add(child);
        return children;
    }

    /* crossover */
    private Chessboard crossover(Chessboard parent1,Chessboard parent2,int crossoverPoint){
        Chessboard child = new Chessboard(numOfQueens,true);
        for(int i=0; i<crossoverPoint; i++){
            setParentToChild(child, parent1, i);
        }
        for(int i=crossoverPoint; i<numOfQueens; i++){
            setParentToChild(child, parent2, i);
        }
        return child;
    }

    /* helper */
    private Chessboard setParentToChild(Chessboard child,Chessboard parent,int i){
        int xPos = parent.queenList.get(i).x_position;
        Queen queen = (parent.getBoard()[xPos][i]);
        child.setQueen(queen);
        return child;
    }

    /* mutate */
    private Chessboard mutate(Chessboard child) {
        int randPercent = (int)(Math.random()*100 + 1);
        if(this.mutationRate >= randPercent){
            Chessboard child2 = new Chessboard(numOfQueens,true);    
            /* randNum = random queen, randPosition = random x Position */
            int randNum = (int)(Math.random()*numOfQueens);
            int randPosition = (int)(Math.random()*numOfQueens);
            Queen queen;
            for(int i=0; i<numOfQueens;i++){
                if(randNum != i) {
                    int temp = child.queenList.get(i).x_position;
                    queen = new Queen(temp,i);   
                }
                else 
                    queen = new Queen(randPosition,i);
                child2.setQueen(queen);
            }
            return child2;
        }
        return child;
    }

    /* test if solution for each chessboard */
    private Chessboard testForSolution(ArrayList<Chessboard> boards){
        Chessboard result = new Chessboard(numOfQueens,true);
        for(Chessboard board:boards){
            int attackingPairs = board.getAllAttackingPairs();
            if(attackingPairs == 0) {
                return board;}
            result = board;
        }
        return result;
    }

    private Chessboard found(int iterations, int i, Chessboard solution) {
        iterations = iterations-i;
        solution.iterations = iterations;
        System.out.println("Solution found within " + iterations + " search cost.");
        return solution;
    }

    private Chessboard notFound(int iterations, ArrayList<Chessboard> parents) {
        Chessboard solution = testForSolution(parents);
        solution.iterations = iterations;
        System.out.println("Solution not found within " + iterations + " search cost and " 
                + solution.attackingPairs + " attacking pairs.");
        return solution;
    }
}