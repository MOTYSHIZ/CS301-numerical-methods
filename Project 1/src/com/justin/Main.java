package com.justin;

public class Main {

    public static void main(String[] args) {
        double errorThreshold =.01;

        bisectionMethod(-1,1,100, errorThreshold, 1);
        falsePositionMethod(-1,1,100, errorThreshold, 1);
        newtonMethod(-1, 100, errorThreshold, .01,1);
        secantMethod(-1,1,100, errorThreshold, 1);
        secantMethodModified(-1,0.01,100, errorThreshold, 1);
    }

    private static void bisectionMethod(double a, double b, int nmax, double errorThreshold, int functionToUse) {
        double error = 0;
        double c, lastError;
        double fa, fb, fc;

        System.out.println("Bisection Method:");
        //Initialize function values,
        fa = functionApplicator(a, functionToUse);
        fb = functionApplicator(b, functionToUse);

        //If the results of f(a) and f(b) have the same signs, there is no root between, so return.
        if(Math.signum(fa) == Math.signum(fb)){
            return;
        }

        error = b-a;
        lastError = Double.MAX_VALUE;
        int divergeCount = 0;
        for(int i = 0; i <= nmax; i++){
            error = error/2;
            c = a + error;

            //If the current error is greater than the last error for 3 times in a row, solution is divergent.
            if(Math.abs(error) > Math.abs(lastError)){
                divergeCount++;
            }else if(divergeCount >= 3){
                System.out.println("Solution is divergent.");
                return;
            }else divergeCount = 0;

            fc = functionApplicator(c, functionToUse);
            System.out.printf("n: %2d  a: %6.3f  b: %6.3f  c: %6.3f  " +
                    "f(a): %6.3f  fb: %6.3f  fc: %6.3f  error: %6.3f\n", i,a,b,c,fa,fb,fc,error);

            if(Math.abs(error) <= errorThreshold){
                System.out.println("Convergence at " + c + ".\n");
                return;
            }
            if(Math.signum(fa) != Math.signum(fc)){
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }

            lastError = error;
        }
        System.out.println("Does not converge after " + nmax + " iterations.\n");
    }

    private static void falsePositionMethod(double a, double b, int nmax, double errorThreshold, int functionToUse) {
        double error = 0;
        double c;
        double fa, fb, fc;

        System.out.println("False-Position Method:");
        //Initialize function values,
        fa = functionApplicator(a, functionToUse);
        fb = functionApplicator(b, functionToUse);

        //If the results of f(a) and f(b) have the same signs, there is no root between, so return.
        if(Math.signum(fa) == Math.signum(fb)){
            return;
        }

        double lastc = 0;
        double lastError = Double.MAX_VALUE;
        int divergeCount = 0;
        for(int i = 0; i <= nmax; i++){
            c = (a*fb - b*fa)/(fb - fa);
            error = (c-lastc)/c;

            //If the current error is greater than the last error for 3 times in a row, solution is divergent.
            //If solution is NaN, it does not converge.
            if(Math.abs(error) > Math.abs(lastError)){
                divergeCount++;
            }else if(divergeCount >= 3) {
                System.out.println("Solution is divergent.");
                return;
            }else if(Double.isNaN(c)){
                System.out.println("Solution does not converge.");
                return;
            }else divergeCount = 0;

            fc = functionApplicator(c, functionToUse);
            System.out.printf("n: %2d  a: %6.3f  b: %6.3f  c: %6.3f  " +
                    "f(a): %6.3f  fb: %6.3f  fc: %6.3f  error: %6.3f\n", i,a,b,c,fa,fb,fc,error);

            if(Math.abs(error) <= errorThreshold){
                System.out.println("Convergence at " + c + ".\n");
                return;
            }

            lastc = c;
            if(Math.signum(fa) != Math.signum(fc)){
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }

            lastError = error;
        }
        System.out.println("Does not converge after " + nmax + " iterations.\n");
    }

    private static void newtonMethod(double x, int nmax, double errorThreshold, double delta, int functionToUse){
        double fx, fp, error;

        System.out.println("Newton-Raphson Method:");
        //Initialize function value,
        fx = functionApplicator(x, functionToUse);
        if(functionToUse == 1) fp = function1Derived(x);
        else fp = function2Derived(x);

        System.out.printf("n: %2d  x: %6.3f  f(x): %6.3f  f'(x): %6.3f\n", 0,x,fx,fp);

        double lastError = Double.MAX_VALUE;
        int divergeCount = 0;
        for(int i= 1; i <= nmax; i++){
            if(functionToUse == 1) fp = function1Derived(x);
            else fp = function2Derived(x);

            if(Math.abs(fp) < delta){
                System.out.println("Small Derivative");
                return;
            }

            error = fx/fp;
            x = x-error;
            fx = functionApplicator(x, functionToUse);
            System.out.printf("n: %2d  x: %6.3f  f(x): %6.3f  f'(x): %6.3f  error: %6.3f\n", i,x,fx,fp, error);

            //If the current error is greater than the last error for 3 times in a row, solution is divergent.
            //If solution is NaN, it does not converge.
            if(Math.abs(error) > Math.abs(lastError)){
                divergeCount++;
            }else if(divergeCount >= 3) {
                System.out.println("Solution is divergent.");
                return;
            }else if(Double.isNaN(x)){
                System.out.println("Solution does not converge.");
                return;
            }else divergeCount = 0;

            if(Math.abs(error) < errorThreshold) {
                System.out.println("Convergence at " + x + ".\n");
                return;
            }
            lastError = error;
        }
        System.out.println("Does not converge after " + nmax + " iterations.\n");
    }

    private static void secantMethod(double a, double b, int nmax, double errorThreshold, int functionToUse){
        double fa, fb, error;

        System.out.println("Secant Method:");
        //Initialize function values,
        fa = functionApplicator(a, functionToUse);
        fb = functionApplicator(b, functionToUse);

        //If the absolute value of fa > absolute value of fb, swap a with b and fa with fb.
        if(Math.abs(fa) > Math.abs(fb)){
            double temp = a; double ftemp = fa;
            a = b; fa = fb;
            b = temp; fb = ftemp;
        }
        System.out.printf("n: %2d  a: %6.3f  f(a): %6.3f\n" +
                "n: %2d  b: %6.3f  f(b): %6.3f\n", 0,a,fa,1,b,fb);

        double lastError = Double.MAX_VALUE;
        int divergeCount = 0;
        for(int i = 2; i <= nmax; i++){
            //If the absolute value of fa > absolute value of fb, swap a with b and fa with fb.
            if(Math.abs(fa) > Math.abs(fb)){
                double temp = a; double ftemp = fa;
                a = b; fa = fb;
                b = temp; fb = ftemp;
            }
            error = fa *((b-a)/(fb - fa));
            b = a;
            fb = fa;

            //If the current error is greater than the last error for 3 times in a row, solution is divergent.
            //If solution is NaN, it does not converge.
            if(Math.abs(error) > Math.abs(lastError)){
                divergeCount++;
            }else if(divergeCount >= 3) {
                System.out.println("Solution is divergent.");
                return;
            }else if(Double.isNaN(b)){
                System.out.println("Solution does not converge.");
                return;
            }else divergeCount = 0;

            if(Math.abs(error) <= errorThreshold){
                System.out.println("Convergence at " + b + ".\n");
                return;
            }
            a = a-error;
            fa = functionApplicator(a, functionToUse);
            System.out.printf("n: %2d  a: %6.3f  f(a): %6.3f  error: %6.3f\n", i,a,fa,error);
            lastError = error;
        }
        System.out.println("Does not converge after " + nmax + " iterations.\n");
    }

    private static void secantMethodModified(double x, double delta, int nmax, double errorThreshold, int functionToUse){
        double fx, fdeltaxPlusx, error;

        System.out.println("Modified Secant Method:");
        //Initialize function values,
        fx = functionApplicator(x, functionToUse);
        fdeltaxPlusx = functionApplicator(x+ (delta*x),functionToUse);

        System.out.printf("n: %2d  x: %6.3f  f(x): %6.3f  delta: %6.3f  f(x + delta*x): %6.3f\n",0,x,fx,delta,fdeltaxPlusx);

        double lastError = Double.MAX_VALUE;
        int divergeCount = 0;
        for(int i = 1; i <= nmax; i++){
            error = fx *((delta*x)/(fdeltaxPlusx - fx));

            //If the current error is greater than the last error for 3 times in a row, solution is divergent.
            //If solution is NaN, it does not converge.
            if(Math.abs(error) > Math.abs(lastError)){
                divergeCount++;
            }else if(divergeCount >= 3) {
                System.out.println("Solution is divergent.");
                return;
            }else if(Double.isNaN(x)){
                System.out.println("Solution does not converge.");
                return;
            }else divergeCount = 0;

            if(Math.abs(error) <= errorThreshold){
                System.out.println("Convergence at " + x + ".\n");
                return;
            }
            x = x-error;
            fx = functionApplicator(x, functionToUse);
            fdeltaxPlusx = functionApplicator(x+ (delta*x),functionToUse);
            System.out.printf("n: %2d  x: %6.3f  f(x): %6.3f  error: %6.3f\n",i,x,fx,error);
            lastError = error;
        }
        System.out.println("Does not converge after " + nmax + " iterations.\n");
    }

    //Helps decide which function to use, of the two hardcoded functions.
    private static double functionApplicator(double value, int functionToUse){
        if(functionToUse == 1)return function1(value);
        else return function2(value);
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

    private static void printDoubleArray(double[] array){
        System.out.println("Errors for copying: ");
        for(double i : array){
            System.out.println(i);
        }
        System.out.println();
    }
}
