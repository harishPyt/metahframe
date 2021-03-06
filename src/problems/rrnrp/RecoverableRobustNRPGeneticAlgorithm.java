package problems.rrnrp;

import algorithms.ga.GeneticAlgorithm;

import java.util.Random;
import java.util.Arrays;

/**
 * Class to implement the SimulatedAnnealing class to the Robust Next Release Problem.
 *
 * @author Matheus Paixao
 */
public class RecoverableRobustNRPGeneticAlgorithm extends GeneticAlgorithm{

   RecoverableRobustNextReleaseProblem recoverableRobustNRP;

   Random random;

   public RecoverableRobustNRPGeneticAlgorithm(RecoverableRobustNextReleaseProblem recoverableRobustNRP, int numberOfIterations){
      super(numberOfIterations);
      this.recoverableRobustNRP = recoverableRobustNRP;
      this.random = new Random();
   }

   protected double getCrossoverProbability(){
      return 0.9;
   }

   protected double getMutationProbability(){
      double numberOfRequirements = (double) recoverableRobustNRP.getNumberOfRequirements();
      return 1 / (10 * numberOfRequirements);
   }

   protected int getNumberOfEliteIndividuals(){
      //return (int) (0.2 * recoverableRobustNRP.getNumberOfRequirements());
      return 2;
   }

   protected int[][] getInitialPopulation(){
      int[][] initialPopulation = new int[recoverableRobustNRP.getNumberOfRequirements()][recoverableRobustNRP.getNumberOfRequirements()];
      int[] randomIndividual = null;

      for(int i = 0; i <= initialPopulation.length - 1; i++){
         randomIndividual = getRandomIndividual(initialPopulation[0].length, i);
         while(recoverableRobustNRP.isSolutionValid(randomIndividual) == false){
            randomIndividual = getRandomIndividual(initialPopulation[0].length, i);
         }

         initialPopulation[i] = randomIndividual;
      }

      return initialPopulation;
   }

   private int[] getRandomIndividual(int numberOfRequirements, int requirementToBeIncluded){
      int[] randomIndividual = new int[numberOfRequirements];

      for(int i = 0; i <= randomIndividual.length - 1; i++){
         randomIndividual[i] = random.nextInt(2);
      }
      randomIndividual[requirementToBeIncluded] = 1;

      return randomIndividual;
   }

   protected int[][] getParents(int[][] population, double[] individualsSolutionValues){
      return getParentsByRoulleteSelection(population, individualsSolutionValues);
   }

   private int[][] getParentsByRoulleteSelection(int[][] population, double[] individualsSolutionValues){
      int[][] parents = new int[2][population[0].length];

      for(int i = 0; i <= parents.length - 1; i++){
         parents[i] = getIndividual(population, individualsSolutionValues);
      }

      return parents;
   }

   /**
    * Method to get an individual using the roullete selection method.
    *
    * @author Matheus Paixao
    * @return the individual selected using the roullete selection method
    * @see getProbabilities
    * @see getRouletteValue
    */
   private int[] getIndividual(int[][] population, double[] individualsSolutionValues){
      int[] individual = null;
      double rouletteValue = 0;
      double[] probabilities = getIndividualsProbabilities(population, individualsSolutionValues);

      rouletteValue = getRouletteValue(probabilities);

      for(int i = 0; i <= probabilities.length - 1; i++){
         if(rouletteValue == probabilities[i]){
            individual = population[i];
            break;
         }
      }

      return individual;
   }

   /**
    * Method to calculate the probability of each individual
    *
    * @author Matheus Paixao
    * @return an array containing the selection's probability of each individual
    */
   private double[] getIndividualsProbabilities(int[][] population, double[] individualsSolutionValues){
      double[] probabilities = new double[population.length];
      double probabilityValue = 0;
      double individualsSolutionValuesSum = getIndividualsSolutionValuesSum(individualsSolutionValues);

      for(int i = 0; i <= probabilities.length - 1; i++){
         probabilityValue = individualsSolutionValues[i] / individualsSolutionValuesSum;
         if(Double.isNaN(probabilityValue)){
            probabilityValue = 0;
         }

         probabilities[i] = probabilityValue;
      }

      return probabilities;
   }

   private double getIndividualsSolutionValuesSum(double[] individualsSolutionValues){
      double individualsSolutionValuesSum = 0;

      for(int i = 0; i <= individualsSolutionValues.length - 1; i++){
         individualsSolutionValuesSum += individualsSolutionValues[i];
      }

      return individualsSolutionValuesSum;
   }

   /**
    * Method to get the value of the probability selected by the roulette.
    *
    * Higher the probability of a node, higher the chance to be choosen by the roulette.
    * For more information search for "roulette selection method".
    * @author Matheus Paixao
    * @param probabilities an array containing the probabilities for roulette selection.
    * @return the probability value choosen by the roulette.
    * @see getRandomNumber
    */
   private double getRouletteValue(double[] probabilities){
      double[] rouletteProbabilities = new double[probabilities.length];
      double neddle = 0;
      double neddleChecker = 0;
      double rouletteValue = 0;

      for(int i = 0; i <= rouletteProbabilities.length - 1; i++){
         rouletteProbabilities[i] = probabilities[i];
      }

      Arrays.sort(rouletteProbabilities);

      neddle = random.nextDouble();

      for(int i = 0; i <= rouletteProbabilities.length - 1; i++){
         neddleChecker += rouletteProbabilities[i];
         if(neddleChecker >= neddle){
            rouletteValue = rouletteProbabilities[i];
            break;
         }
      }

      return rouletteValue;
   }

   protected int[][] getChildsByCrossover(int[][] parents){
      int[][] childs = null;

      if(Arrays.equals(parents[0], parents[1]) == true){
         childs = parents;
      }
      else{
         childs = getChildsByOnePointCrossover(parents);
      }

      return childs;
   }

   private int[][] getChildsByOnePointCrossover(int[][] parents){
      int[][] childs = new int[parents.length][parents[0].length];
      int crossoverIndex = random.nextInt(childs[0].length);

      for(int i = 0; i <= childs.length - 1; i++){
         for(int j = 0; j <= crossoverIndex; j++){
            childs[i][j] = parents[i][j];
         }

         if(i == 0){
            for(int j = crossoverIndex + 1; j <= childs[0].length - 1; j++){
               childs[i][j] = parents[1][j];
            }
         }
         else if(i == 1){
            for(int j = crossoverIndex + 1; j <= childs[0].length - 1; j++){
               childs[i][j] = parents[0][j];
            }
         }

         if(recoverableRobustNRP.isSolutionValid(childs[i]) == false){
            repair(childs[i]);
         }
      }

      return childs;
   }

   protected void mutate(int[] individual, int indexToMutate){
      if(individual[indexToMutate] == 0){
         individual[indexToMutate] = 1;
      }
      else if(individual[indexToMutate] == 1){
         individual[indexToMutate] = 0;
      }

      if(recoverableRobustNRP.isSolutionValid(individual) == false){
         repair(individual);
      }
   }

   private void repair(int[] individual){
      removeRandomRequirement(individual);

      if(recoverableRobustNRP.isSolutionValid(individual) == false){
         repair(individual);
      }
   }

   private void removeRandomRequirement(int[] individual){
      boolean removeFlag = false;
      int randomRequirementToRemove = 0;

      while(removeFlag == false){
         randomRequirementToRemove = random.nextInt(individual.length);
         if(individual[randomRequirementToRemove] == 1){
            individual[randomRequirementToRemove] = 0;
            removeFlag = true;
         }
      }
   }

   protected double calculateSolutionValue(int[] individual){
      return recoverableRobustNRP.calculateSolutionValue(individual);
   }

   protected boolean isSolutionBetter(double solutionValue1, double solutionValue2){
      boolean result = false;

      if(solutionValue1 > solutionValue2){
         result = true;
      }

      return result;
   }

   protected boolean isMinimizationProblem(){
      return false;
   }
}
