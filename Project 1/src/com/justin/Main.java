package com.justin;

public class Main {

    public static void main(String[] args) {
        System.out.println(function1(2));
        System.out.println(function2(2));
    }

    private static double bisectionMethod(int a, int b) {

    }

    private static double function1(int x){
        return 2*Math.pow(x, 3) - 11.7*Math.pow(x,2) + 17.7*x - 5;
    }

    private static double function2(int x){
        return Math.exp(-x) - x;
    }
}
