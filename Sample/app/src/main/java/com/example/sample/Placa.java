package com.example.sample;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Placa {

    private static final int NUM_ROWS = 16;
    private static final int NUM_COLS = 8;
    Pino[][] matrix = new Pino[16][8];
    public  List<Pino> lista;

    public Placa ( List<org.opencv.core.Point> pontos){
        List<Point> sortedCircles = pontos.stream()
                .sorted(Comparator.comparingDouble(point -> point.x))
                .map(point -> new Point(point.x, point.y)) // create Circle objects from Point objects
                .collect(Collectors.toList());

        List<Point> leftmost = eightLeftmostCircles(sortedCircles);
        Collections.sort(leftmost, Comparator.comparingDouble(Point::getY));

        List<Point> rightmost= eigthRightmostCircle(sortedCircles);
        Collections.sort(rightmost, Comparator.comparingDouble(Point::getY));

        for ( int i =0; i< rightmost.size(); i++)
        {
            Tuple eq= lineEquation(leftmost.get(i),rightmost.get(i));
            List<Integer> arr = getBestFitIndexes(eq,sortedCircles);
            List<Point> result = new ArrayList<>();
            for (int j : arr) {
                result.add(sortedCircles.get(j));
            }
            Collections.sort(result, Comparator.comparingDouble(Point::getX));

            for (int j = 0; j < result.size(); j++) {
                Point k= result.get(j);
                boolean flag = false;
                for (int l = 0; l < 8; l++) {
                    for (int m = 0; m < 16; m++) {
                        if(matrix[m][l] == null)
                        {
                            matrix[m][l]=new Pino( k.x, k.y, "green");
                            flag=true;
                            break;
                        }
                        if (flag==true)
                        {
                            break;
                        }
                    }
                }
            }
        }

    }

    public static boolean doCirclesCollide(Circles circle1, Circles circle2) {
        double x1 = circle1.x;
        double y1 =  circle1.y;
        double r1 = circle1.r;
        double x2 = circle2.x;
        double y2 = circle2.y;
        double r2 = circle2.r;
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        if (distance < r1 + r2) {
            return true;  // Circles collide
        } else {
            return false;  // Circles do not collide
        }
    }

    public List<Point> eightLeftmostCircles(List<Point> circles) {
        int margin = 300;
        List<Point> list = circles.stream()
                .sorted(Comparator.comparingDouble(Point::getX))
                .limit(8)
                .collect(Collectors.toList());
        int i = 9;
        while (checkLine(list, margin) != 0) {
            try {
                list.remove(checkLine(list, 10));
                list.add(circles.stream()
                        .sorted(Comparator.comparingDouble(Point::getX))
                        .skip(i)
                        .findFirst()
                        .orElseThrow(IndexOutOfBoundsException::new));
                i++;
            } catch (IndexOutOfBoundsException e) {
                margin += 100;
                list = eightLeftmostCircles(circles);
            }
        }
        return list;
    }

    public static List<Point> eigthRightmostCircle(List<Point> circles) {
        int margin = 300;
        List<Point> lista = new ArrayList<>();
        circles.sort(Comparator.comparingDouble(Point::getX));
        int n = circles.size();
        for (int i = n - 8; i < n; i++) {
            lista.add(circles.get(i));
        }
        int i = 9;
        while (checkLine(lista, margin) != 0) {
            try {
                int indexToRemove = checkLine(lista, 10);
                lista.remove(indexToRemove);
                circles.sort(Comparator.comparingDouble(Point::getX));
                lista.add(circles.get(i));
                i += 1;
            } catch (IndexOutOfBoundsException e) {
                margin += 100;
                lista = eigthRightmostCircle(circles);
            }
        }
        return lista;
    }
    public static int checkLine(List<Point> circles, int margin) {
        if (circles.size() < 3) {
            return 0;
        }
        double x1 = circles.get(0).getX();
        double y1 = circles.get(0).getY();
        double x2 = circles.get(1).getX();
        double slope;
        double y2 = circles.get(1).getY();
        if( x1-x2 ==0) { slope= 0;} else if ( y1==y2 ) {
             slope= 1;
        }
        else{
             slope = (y2 - y1) / (double) (x2 - x1);
        }
        for (int i = 2; i < circles.size(); i++) {
            double x = circles.get(i).getX();
            double y = circles.get(i).getY();
            if (Math.abs(y - y1 - slope * (x - x1)) > margin) {
                return 0;
            }
        }
        return 1;
    }


    public static List<Integer> getBestFitIndexes(Tuple lineEq, List<Point> points) {
        double a = lineEq.x;
        double b = lineEq.y;
        List<Tuple> distances = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            double x = point.x;
            double y = point.y;
            double dist = Math.abs(a * x - y + b) / Math.sqrt(a * a + 1);
            distances.add(new Tuple(i, dist));
        }
        distances.sort(Comparator.comparingDouble(Tuple::getY));
        List<Integer> bestFitIndexes = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Tuple tuple = distances.get(i);
            bestFitIndexes.add((int) tuple.x);
        }
        return bestFitIndexes;
    }

    public static Tuple lineEquation(Point point1, Point  point2) {
        double x1 = point1.x;
        double y1 = point1.y;
        double x2 = point2.x;
        double y2 = point2.y;
        double slope;
        if (x1 == x2) {
            slope = 0;
        } else if (y1 == y2) {
            slope = 1;
        } else {
            slope = (y2 - y1) / (x2 - x1);
        }
        double yIntercept = y1 - slope * x1;
        return new Tuple(slope, yIntercept);
    }

    public Pino[][] getMatrix() {
        return matrix;
    }


    public static class Tuple {
        private final double x;
        private final double y;

        public Tuple(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
