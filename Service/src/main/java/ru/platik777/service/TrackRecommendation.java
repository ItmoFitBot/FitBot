package ru.platik777.service;

import java.util.Arrays;

public class TrackRecommendation {

    private double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public double calculateRecommendation(
            double distance, double elevationGain, double time, double avgSpeed, double avgHeartRate,
            double minDistance, double maxDistance,
            double minElevationGain, double maxElevationGain,
            double minTime, double maxTime,
            double minAvgSpeed, double maxAvgSpeed,
            double minAvgHeartRate, double maxAvgHeartRate,
            double[] weights
    ) {
        double normDistance = normalize(distance, minDistance, maxDistance);
        double normElevationGain = normalize(elevationGain, minElevationGain, maxElevationGain);
        double normTime = normalize(time, minTime, maxTime);
        double normAvgSpeed = normalize(avgSpeed, minAvgSpeed, maxAvgSpeed);
        double normAvgHeartRate = normalize(avgHeartRate, minAvgHeartRate, maxAvgHeartRate);

        double recommendation =
                weights[0] * normDistance +
                        weights[1] * normElevationGain +
                        weights[2] * normTime +
                        weights[3] * normAvgSpeed +
                        weights[4] * normAvgHeartRate;

        return recommendation * 10; // переводим в шкалу от 0 до 10
    }

    public double[] trainWeights(double[][] features, double[] labels) {
        int numFeatures = features[0].length;
        double[] weights = new double[numFeatures];
        double learningRate = 0.01;
        int numIterations = 1000;

        for (int iter = 0; iter < numIterations; iter++) {
            double[] gradients = new double[numFeatures];
            for (int i = 0; i < features.length; i++) {
                double predicted = 0.0;
                for (int j = 0; j < numFeatures; j++) {
                    predicted += weights[j] * features[i][j];
                }
                double error = predicted - labels[i];
                for (int j = 0; j < numFeatures; j++) {
                    gradients[j] += error * features[i][j];
                }
            }
            for (int j = 0; j < numFeatures; j++) {
                weights[j] -= learningRate * gradients[j] / features.length;
            }
        }
        return weights;
    }
}
