package com.example.sample;

public class Pino {
    String cor;
    double x;
    double y;
    public Pino(double x, double y, String cor ) {
        this.x= x;
        this.y =y;
        this.cor= cor;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
