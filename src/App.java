import algorithms.Algorithm;

import problems.tsp.TSPProblem;
import problems.tsp.TSPAntQ;
import problems.tsp.TSPRandomAlgorithm;
import problems.tsp.TSPACS;
import problems.tsp.TSPSimulatedAnnealing;
import problems.jssp.JSSPProblem;
import problems.jssp.JSSPAntQ;
import problems.jssp.JSSPRandomAlgorithm;
import problems.jssp.JSSPACS;
import problems.srpp.SRPPProblem;
import problems.srpp.SRPPAntQ;
import problems.srpp.SRPPRandomAlgorithm;
import problems.rnrp.RobustNextReleaseProblem;
import problems.rnrp.RobustNRPSimulatedAnnealing;

import instancereaders.InstanceChooser;

import java.io.File;

/**
 * Class used to run the application.
 * @author Matheus Paixao
 *
 * How to run:
 * java App <algorithm> <problem> <number of iterations>
 * Only the last parameter is optional. If not specified, will be runned 200 iterations
 */
public class App{
   public static void main(String[] args){
      String problem = null;
      String algorithm = null;
      int numberOfIterations = 0;
      Algorithm adaptedAlgorithm = null;

      InstanceChooser instanceChooser = new InstanceChooser();
      File instance = instanceChooser.getInstance(); //choose the instance to be used by the algorithm

      if(args.length >= 1){
         algorithm = args[0]; //first parameter is the algorithm to be used
         problem = args[1]; //second parameter is the problem to be solved

         if(args.length >= 3){ //third parameter is the number of iterations, it's optional
            numberOfIterations = Integer.parseInt(args[2]);
         }
         else{
            numberOfIterations = 200;
         }
      }

      if(algorithm.equals("antq")){
         if(problem.equals("tsp")){
            TSPProblem tspProblem = new TSPProblem(instance);
            adaptedAlgorithm = new TSPAntQ(tspProblem, numberOfIterations);
         }
         else if(problem.equals("jssp")){
            JSSPProblem jsspProblem = new JSSPProblem(instance);
            adaptedAlgorithm = new JSSPAntQ(jsspProblem, numberOfIterations);
         }
         else if(problem.equals("srpp")){
            SRPPProblem srppProblem = new SRPPProblem(instance);
            adaptedAlgorithm = new SRPPAntQ(srppProblem, numberOfIterations);
         }
      }
      else if(algorithm.equals("acs")){
         if(problem.equals("tsp")){
            TSPProblem tspProblem = new TSPProblem(instance);
            adaptedAlgorithm = new TSPACS(tspProblem, numberOfIterations);
         }
         else if(problem.equals("jssp")){
            JSSPProblem jsspProblem = new JSSPProblem(instance);
            adaptedAlgorithm = new JSSPACS(jsspProblem, numberOfIterations);
         }
      }
      else if(algorithm.equals("random")){
         if(problem.equals("tsp")){
            TSPProblem tspProblem = new TSPProblem(instance);
            adaptedAlgorithm = new TSPRandomAlgorithm(tspProblem, numberOfIterations);
         }
         else if(problem.equals("jssp")){
            JSSPProblem jsspProblem = new JSSPProblem(instance);
            adaptedAlgorithm = new JSSPRandomAlgorithm(jsspProblem, numberOfIterations);
         }
         else if(problem.equals("srpp")){
            SRPPProblem srppProblem = new SRPPProblem(instance);
            adaptedAlgorithm = new SRPPRandomAlgorithm(srppProblem, numberOfIterations);
         }
      }
      else if(algorithm.equals("sa")){
         if(problem.equals("tsp")){
            TSPProblem tspProblem = new TSPProblem(instance);
            adaptedAlgorithm = new TSPSimulatedAnnealing(tspProblem);
         }
         else if(problem.equals("rnrp")){ 
            RobustNextReleaseProblem robustNRP = new RobustNextReleaseProblem(instance);
            adaptedAlgorithm = new RobustNRPSimulatedAnnealing(robustNRP);
         }
      }

      System.out.println("Best Solution: "+adaptedAlgorithm.getSolution());
      System.out.println("Time elapsed: "+adaptedAlgorithm.getTotalTime());
      System.exit(0);
   }
}
