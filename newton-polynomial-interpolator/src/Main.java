/**
 * Created by Justin Ordonez on 2/26/2017.
 */

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Main {
    private static double[] xArray;
    private static double[] yArray;
    private static double[][] coefArray;
    private static DecimalFormat df;


    public static void main(String args[]){
        File file = new File("input.txt");
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        try {
            Scanner sc = new Scanner(file);

            String[] xStringarray = sc.nextLine().split(" ");
            String[] yStringarray = sc.nextLine().split(" ");
            xArray = new double[xStringarray.length];
            yArray = new double[yStringarray.length];

            for(int i = 0; i < xStringarray.length; i++){
                xArray[i] = Float.parseFloat(xStringarray[i]);
                yArray[i] = Float.parseFloat(yStringarray[i]);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printInputFile();
        dividedDifferenceCoefficientComputation();
        dividedDifferenceTablePrinter();
        printNewtonPolynomial();
        simplifyPolynomial();
    }

    private static void printInputFile(){
        System.out.println("Input File: ");
        for (double xVal : xArray) {
            System.out.printf("%-7s", df.format(xVal));
        }
        System.out.println();

        for (double yVal : yArray) {
            System.out.printf("%-7s", df.format(yVal));
        }
        System.out.println("\n");
    }

    //Compute the coefficients in the Divided Difference table.
    private static void dividedDifferenceCoefficientComputation(){
        coefArray = new double[xArray.length][xArray.length];

        //Copy the coeffArray from the yArray.
        System.arraycopy(yArray, 0, coefArray[0], 0, coefArray.length);

        //For each step of div difference, calculate from end of array to current i.
        for(int i = 1; i < coefArray.length; i++){
            for(int j = coefArray.length-1 ; j >= i; j--){
                coefArray[i][j] = (coefArray[i-1][j] - coefArray[i-1][j-1])/(xArray[j] - xArray[j-i]);
            }
        }
    }

    //prints the polynomial in Newton=form
    private static void printNewtonPolynomial(){
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

        for (String topLabelElem : topLabel) {
            System.out.printf("%-7s ", topLabelElem);
        }
        System.out.println("\n");

        for(int i = 0; i < xArray.length; i++){
            System.out.printf("%-7s", df.format(xArray[i]));

            for(int j = 0; j < xArray.length; j++){
                if(j <= i)System.out.printf(" %-7s", df.format(coefArray[j][i]));
            }

            System.out.printf("\n\n");
        }
    }

    //Generates and prints the simplified polynomial.
    private static void simplifyPolynomial(){
        ArrayList<ArrayList<PolynomialElement>> polynomialExpressions = new ArrayList<>();
        ArrayList<PolynomialElement> currentExpression = new ArrayList<>();
        currentExpression.add(new PolynomialElement(1,0));
        String resultingPolynomial = "";

        //Form the polyExpressions.
        for(int i = 1; i < xArray.length; i++){
            //For all polyElems in the expression, increment degree, then subtract by self*xArray[i]
            ArrayList<PolynomialElement> nextExpressionMultiplied = new ArrayList<>();
            ArrayList<PolynomialElement> nextExpression = new ArrayList<>();
            for(PolynomialElement pe : currentExpression){
                PolynomialElement incrementedDegreeElem =
                        new PolynomialElement(pe.coefficient, pe.degree);
                incrementedDegreeElem.degree += 1;

                PolynomialElement multipliedElem =
                        new PolynomialElement(pe.coefficient*-xArray[i-1], pe.degree);

                nextExpression.add(incrementedDegreeElem);
                nextExpression.add(multipliedElem);

                //The expression multipled by the coefficient obtained from the Divided difference table.
                nextExpressionMultiplied.add( new PolynomialElement(
                        incrementedDegreeElem.coefficient*coefArray[i][i], incrementedDegreeElem.degree));
                nextExpressionMultiplied.add(new PolynomialElement(
                        multipliedElem.coefficient*coefArray[i][i], multipliedElem.degree));
            }
            polynomialExpressions.add(nextExpressionMultiplied);
            currentExpression = nextExpression;
        }

        //Take the expressions and put them into one expression collection for simpler manipulation.
        currentExpression.clear();
        currentExpression.add(new PolynomialElement(coefArray[0][0],0));
        for(List<PolynomialElement> expression: polynomialExpressions){
            currentExpression.addAll(expression);
        }

        //Add all like terms of the currentExpression.
        for(int i = 0; i < currentExpression.size(); i++){
            for(int j = 0; j < currentExpression.size(); j++){
                if(currentExpression.get(i).degree == currentExpression.get(j).degree && i != j){
                    currentExpression.get(i).coefficient += currentExpression.get(j).coefficient;
                    currentExpression.remove(j);
                    j--;
                }
            }
        }

        currentExpression.sort(PolynomialElement::compareTo);

        //Add all elements of the currentExpression to the resulting string.
        for(PolynomialElement pe: currentExpression){
            if(resultingPolynomial == "")resultingPolynomial += pe.getStringRepresentation();
            else if(pe.coefficient > 0)resultingPolynomial += " + " + pe.getStringRepresentation();
            else if(pe.coefficient < 0) resultingPolynomial += " + (" + pe.getStringRepresentation() + ")";
        }

        System.out.println("Simplified Polynomial: \n" + resultingPolynomial);
    }
}
