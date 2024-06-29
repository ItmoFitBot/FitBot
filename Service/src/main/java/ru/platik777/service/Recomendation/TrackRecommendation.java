package ru.platik777.service.Recomendation;

import org.FitBot.DtoTrackInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class TrackRecommendation {

    public static double findMin(double[][] routes, int index) {
        double min = Double.MAX_VALUE;
        for (double[] route : routes) {
            if (route[index] < min) {
                min = route[index];
            }
        }
        return min;
    }

    // Функция для нахождения максимального значения характеристики
    public static double findMax(double[][] routes, int index) {
        double max = Double.MIN_VALUE;
        for (double[] route : routes) {
            if (route[index] > max) {
                max = route[index];
            }
        }
        return max;
    }

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

    public double getRecommendationInfo(ArrayList<DtoTrackInfoWithReview> dtoTracksInfo, DtoTrackInfo dtoTrackInfo) {
        double[][] tracks = new double[5][];
        double[] reviews = new double[0];
        int iterator = 0;
        for (DtoTrackInfoWithReview dtoTrackInfoWithReview : dtoTracksInfo) {
            tracks[iterator] = new double[]{dtoTrackInfoWithReview.getDtoTrackInfo().getTotalDistance(), dtoTrackInfoWithReview.getDtoTrackInfo().getTotalElevationGain(), dtoTrackInfoWithReview.getDtoTrackInfo().getTotalTime(), dtoTrackInfoWithReview.getDtoTrackInfo().getTotalDistance()/dtoTrackInfoWithReview.getDtoTrackInfo().getTotalTime(), dtoTrackInfoWithReview.getDtoTrackInfo().getHeartRate()};
            reviews[iterator] = (double) (dtoTrackInfoWithReview.getGeneral() + dtoTrackInfoWithReview.getGeneraDifficult() + dtoTrackInfoWithReview.getPsychicalDifficult()) / 3;
        }

        double minDistance = findMin(tracks, 0);
        double maxDistance = findMax(tracks, 0);
        double minElevationGain = findMin(tracks, 1);
        double maxElevationGain = findMax(tracks, 1);
        double minTime = findMin(tracks, 2);
        double maxTime = findMax(tracks, 2);
        double minAvgSpeed = findMin(tracks, 3);
        double maxAvgSpeed = findMax(tracks, 3);
        double minAvgHeartRate = findMin(tracks, 4);
        double maxAvgHeartRate = findMax(tracks, 4);

        double[][] normalizedRoutes = new double[tracks.length][5];
        for (int i = 0; i < tracks.length; i++) {
            normalizedRoutes[i][0] = normalize(tracks[i][0], minDistance, maxDistance);
            normalizedRoutes[i][1] = normalize(tracks[i][1], minElevationGain, maxElevationGain);
            normalizedRoutes[i][2] = normalize(tracks[i][2], minTime, maxTime);
            normalizedRoutes[i][3] = normalize(tracks[i][3], minAvgSpeed, maxAvgSpeed);
            normalizedRoutes[i][4] = normalize(tracks[i][4], minAvgHeartRate, maxAvgHeartRate);
        }

        // Обучение весов
        double[] weights = trainWeights(normalizedRoutes, reviews);
        System.out.println("Trained Weights: " + Arrays.toString(weights));

        return calculateRecommendation(
                dtoTrackInfo.getTotalDistance(), dtoTrackInfo.getTotalElevationGain(), dtoTrackInfo.getTotalTime(), dtoTrackInfo.getTotalDistance() / dtoTrackInfo.getTotalTime(), dtoTrackInfo.getHeartRate(),
                minDistance, maxDistance,
                minElevationGain, maxElevationGain,
                minTime, maxTime,
                minAvgSpeed, maxAvgSpeed,
                minAvgHeartRate, maxAvgHeartRate,
                weights
        );

    }

}
