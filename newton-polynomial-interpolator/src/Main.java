/**
 * Created by Justin Ordonez on 2/26/2017.
 */

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static float[] xArray;
    private static float[] yArray;
    private static float[][] coefArray;


    public static void main(String args[]){
        File file = new File("input.txt");
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        try {
            Scanner sc = new Scanner(file);

            String[] xStringarray = sc.nextLine().split(" ");
            String[] yStringarray = sc.nextLine().split(" ");
            xArray = new float[xStringarray.length];
            yArray = new float[yStringarray.length];

            for(int i = 0; i < xStringarray.length; i++){
                xArray[i] = Float.parseFloat(xStringarray[i]);
                yArray[i] = Float.parseFloat(yStringarray[i]);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Input File: ");
        for(int i = 0; i < xArray.length; i++){
            System.out.printf("%-7s", df.format(xArray[i]));
        } System.out.println();

        for(int i = 0; i < yArray.length; i++){
            System.out.printf("%-7s", df.format(yArray[i]));
        }System.out.println("\n");

        dividedDifferenceCoefficientComputation();
        dividedDifferenceTablePrinter();
        printNewtonPolynomial();
    }

    private static void dividedDifferenceCoefficientComputation(){
        coefArray = new float[xArray.length][xArray.length];

        //Copy the coeffArray from the yArray.
        for(int j = 0; j < coefArray.length; j++){
            coefArray[0][j] = yArray[j];
        }

        //For each step of div difference, calculate from end of array to current i.
        for(int i = 1; i < coefArray.length; i++){
            for(int j = coefArray.length-1 ; j >= i; j--){
                coefArray[i][j] = (coefArray[i-1][j] - coefArray[i-1][j-1])/(xArray[j] - xArray[j-i]);
            }
        }
    }

    //prints the polynomial in Newton=form
    private static void printNewtonPolynomial(){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        String polynomial = "" + df.format(coefArray[0][0]);

        for(int i = 1; i < coefArray.length; i++){
            if(coefArray[i][ i] != 0){
                if(coefArray[i][i] > 0)polynomial += " + " + df.format(coefArray[i][i]);
                else polynomial += " - " + df.format(Math.abs(coefArray[i][i]));

                for(int j=0; j < i; j++){
                    if(xArray[j] != 0)polynomial += "(x - " + df.format(xArray[j]) + ")";
                    else polynomial += "(x)";
                }
            }
        }

        System.out.println("Newton-Form Polynomial Rounded to two decimal places:\n" + polynomial);
    }

    //prints the divided difference table.
    private static void dividedDifferenceTablePrinter(){
        String[] topLabel = {"x", "f[]", "f[,]", "f[,,]", "f[,,,]", "..."};
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        for(int i = 0; i < topLabel.length; i++){
            System.out.printf("%-7s ", topLabel[i]);
        }System.out.println("\n");

        for(int i = 0; i < xArray.length; i++){
            System.out.printf("%-7s", df.format(xArray[i]));

            for(int j = 0; j < xArray.length; j++){
                if(j <= i)System.out.printf(" %-7s", df.format(coefArray[j][i]));
            }

            System.out.printf("\n\n");
        }
    }
}
