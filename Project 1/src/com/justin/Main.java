package com.justin;

public class Main {

    public static void main(String[] args) {
        bisectionMethod(0.0,1.0,100, 0.000006, 1);
    }

    private static void bisectionMethod(double a, double b, int nmax, double errorThreshold, int functionToUse) {
        double error = 0;
        double c;
        double fa, fb, fc;

        //Initialize function values,
        if(functionToUse == 1){
            fa = function1(a);
            fb = function1(b);
        }
        else {
            fa = function2(a);
            fb = function2(b);
        }

        //If the results of f(a) and f(b) have the same signs, there is no root between, so return.
        if(Math.signum(fa) == Math.signum(fb)){
            return;
        }

        error = b-a;
        for(int i = 0; i < nmax; i++){
            error = error/2;
            c = a + error;
            if(functionToUse == 1)fc = function1(c);
            else fc = function2(c);
            System.out.println(i + " " + c + " " + fc + " " + error);

            if(Math.abs(error) <= errorThreshold){
                System.out.println("Convergence.");
                return;
            }
            if(Math.signum(fa) != Math.signum(fc)){
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }
        }
    }

    private static void newtonMethod(double x, int nmax, double errorThreshold, double delta, int functionToUse){
        double fx, fp;

        //Initialize function values,
        if(functionToUse == 1){
            fx = function1(x);
            fp = function1Derived(x);
        }
        else {
            fx = function2(x);
            fp = function2Derived(x);
        }

    }

    private static double function1(double x){
        return 2*Math.pow(x, 3) - 11.7*Math.pow(x,2) + 17.7*x - 5;
    }

    private static double function1Derived(double x){
        return 6*Math.pow(x, 2) - 23.4*x + 17.7;
    }

    private static double function2(double x){
        return Math.exp(-x) - x;
    }

    private static double function2Derived(double x){
        return -Math.exp(-x) - 1;
    }
}
