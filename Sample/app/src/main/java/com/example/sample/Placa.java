package com.example.sample;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Placa {

    private static final int NUM_ROWS = 16;
    private static final int NUM_COLS = 8;
    Pino[][] matrix = new Pino[16][8];
    public  List<Pino> lista;

    public Placa ( List<org.opencv.core.Point> pontos){


        // cluster the points into rows
        List<List<Point>> clusters = clusterPoints(pontos);

        // sort the clusters from top to bottom
        Collections.sort(clusters, new Comparator<List<Point>>() {
            @Override
            public int compare(List<Point> o1, List<Point> o2) {
                double avgY1 = getAverageY(o1);
                double avgY2 = getAverageY(o2);
                return Double.compare(avgY1, avgY2);
            }
        });

        // sort the points within each cluster from left to right
        for (List<Point> cluster : clusters) {
            Collections.sort(cluster, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    return Double.compare(o1.x, o2.y);
                }
            });
        }

    }

    private static List<List<Point>> clusterPoints(List<Point> points) {
        List<List<Point>> clusters = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS; i++) {
            clusters.add(new ArrayList<>());
        }


        for (Point point : points) {
            int row = (int) Math.round(point.y * NUM_ROWS / NUM_COLS);
            if (row < 0) row = 0;
            if (row >= NUM_ROWS) row = NUM_ROWS - 1;
            clusters.get(row).add(point);
        }
        return clusters;
    }
    private static double getAverageY(List<Point> points) {
        double sumY = 0;
        for (Point point : points) {
            sumY += point.y;
        }
        return sumY / points.size();
    }



    public Pino[][] getMatrix() {
        return matrix;
    }
}
