package problems.rnrp;

import algorithms.sa.SimulatedAnnealing;

/**
 * Class to implement the SimulatedAnnealing class to the Robust Next Release Problem.
 *
 * @author Matheus Paixao
 */
public class RobustNRPSimulatedAnnealing extends SimulatedAnnealing{

   public RobustNRPSimulatedAnnealing(){

   }

   protected double getInitialTemperature(){
      return 0;
   }

   protected double getFinalTemperature(){
      return 0;
   }

   protected double getAlpha(){
      return 0;
   }

   protected int getNumberOfMarkovChains(){
      return 0;
   }

   protected int[] getInitialSolution(){
      return null;
   }

   protected int[] getNeighbourSolution(int[] solution){
      return null;
   }

   protected double calculateSolutionValue(int[] solution){
      return 0;
   }

   protected boolean isSolutionBest(double solutionValue1, double solutionValue2){
      return false;
   }
}
