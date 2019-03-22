# NQueens
=======
How to compile and run Project 2 from command line:
1) Extract source code from compressed file.
2) Make sure java jdk is installed correctly, "java -version" to check. (Version of java I am running: java version "1.8.0_171"
                                                                            java(TM) SE Runtime Environment (build 1.8.0_171-b11)
                                                                            Java HotSpot(TM) 64-Bit Server VM (build 25.171-b11, mixed mode))
3) Change current directory to directory where source code file is located in using "cd <path-to-directory>".                                                                             
3) To compile: "javac NQueens.java Chessboard.java GeneticAlgorithm.java Queen.java SimulatedAnnealing.java". // this may take a while
4) To run: "java NQueens".
5) In the source code, NQueens.java, at the main function, SA or GA will run, with the other command calling the function to be commented out
6) Feel free to test one algorithm or the other or both.
7) TESTING GENETIC ALGORITHM IS EXTREMELY LONGER THAN SA, LOWER NUMBER OF ITERATIONS OR N IF NEEDED.

NOTE: When testing high number of iterations, disable command that saves the final config of the board at the end of an iteration to prevent multiple files being made.