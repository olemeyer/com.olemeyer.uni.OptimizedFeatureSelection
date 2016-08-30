package com.olemeyer.uni.optfeatureselection.rga;

import com.olemeyer.uni.optfeatureselection.controller.Response;
import com.olemeyer.uni.optfeatureselection.featuremodel.FeatureModel;
import com.olemeyer.uni.optfeatureselection.rga.mutation.AlreadyMutatedException;

import java.util.*;

/**
 * @author Ole Meyer
 */
public class RGA {

    private List<Individual> individuals = new LinkedList<>();

    private FeatureModel featureModel;

    private double bestTotalScore = Double.MIN_VALUE;
    private List<String> bestTotalConfiguration;
    private double[] bestScores;
    private int iterations = 0;

    private int minAttractorLength = 5;
    private int equalScores = 0;
    private boolean attractor = false;

    public RGA(FeatureModel featureModel, int individualCount) {

        this.featureModel = featureModel;

        for (int i = 0; i < individualCount; ++i) individuals.add(new Individual(featureModel));


    }

    public Response optimize(double rgMutationProbability, double sgMutationProbability, double reproductionQuota,
                             int maxIterations, int minAttractorLength) {


        this.minAttractorLength = minAttractorLength;

        bestScores = new double[maxIterations];
        iterations = 0;
        attractor = false;

        mutateRegulatoryGenes(1);


        long startTime = System.currentTimeMillis();

        for (int i = 0; i < maxIterations; ++i) {

            try {
                int selectionCount = (int) (individuals.size() * reproductionQuota);
                if (selectionCount % 2 != 0) --selectionCount;
                List<Individual> selection = select(individuals, selectionCount);
                List<Individual> nextGen = createNextGeneration(selection);

                mutateRegulatoryGenes(rgMutationProbability);
                mutateStructuralGenes(nextGen, sgMutationProbability);

                for (Individual individual : individuals) individual.calcFitnessScore();
                for (Individual individual : nextGen) individual.calcFitnessScore();


                replace(individuals, nextGen);

                //Attraktor erkennung
                if (iterations > 0 && bestScores[iterations - 1] == bestScores[iterations]) {
                    ++equalScores;
                    attractor = true;
                    if (equalScores > this.minAttractorLength) break;

                } else {
                    equalScores = 0;
                }


                ++iterations;


            } catch (AlreadyMutatedException e) {
                e.printStackTrace();
            }

        }

        bestScores = Arrays.copyOfRange(bestScores, 0, iterations - equalScores+5);


        return new Response(bestTotalConfiguration, bestTotalScore, System.currentTimeMillis() - startTime, attractor, iterations, bestScores);
    }

    private void replace(List<Individual> parents, List<Individual> nextGen) {
        double bestScore = Double.MIN_VALUE;
        for (Individual individual : parents) {
            if (individual.getTotalFitnessScore() > bestScore) bestScore = individual.getTotalFitnessScore();
            if (individual.getTotalFitnessScore() > bestTotalScore) {
                bestTotalScore = individual.getTotalFitnessScore();
                bestTotalConfiguration = individual.getConfiguration();
            }
        }

        bestScores[iterations] = bestScore;


        Collections.sort(parents, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getTotalFitnessScore() > o2.getTotalFitnessScore()) return -1;
                if (o1.getTotalFitnessScore() < o2.getTotalFitnessScore()) return 11;
                return 0;
            }
        });

        for (Individual child : nextGen) {
            if (child.getTotalFitnessScore() > bestScore) {
                parents.remove(0);
                parents.add(child);
            }
        }
    }


    private List<Individual> select(List<Individual> individuals, int count) {
        Collections.sort(individuals, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitnessScore() > o2.getFitnessScore()) return 1;
                if (o1.getFitnessScore() < o2.getFitnessScore()) return -1;
                return 0;
            }
        });

        List<Individual> selection = new LinkedList<>();
        selection.addAll(individuals.subList(0, count));
        return selection;
    }

    private List<Individual> createNextGeneration(List<Individual> parents) throws AlreadyMutatedException {
        List<Individual> nextGen = new LinkedList<>();

        //Crossover und Rekombination
        Individual parent1 = parents.get(Individual.random.nextInt(parents.size()));
        parents.remove(parent1);
        Individual parent2 = parents.get(Individual.random.nextInt(parents.size()));
        parents.remove(parent2);

        nextGen.add(parent1.recombine(parent2));
        nextGen.add(parent2.recombine(parent1));

        return nextGen;
    }

    private void mutateRegulatoryGenes(double probability) {
        if (Math.random() < probability) {
            RegelatoryGene.active = featureModel.getSoftgoals().get(Individual.random.nextInt(featureModel.getSoftgoals().size()));
        }

    }

    private void mutateStructuralGenes(List<Individual> nextGen, double probability) {
        for (Individual individual : nextGen) {
            if (Math.random() < probability) {
                individual.mutate();
            }
        }
    }


}
