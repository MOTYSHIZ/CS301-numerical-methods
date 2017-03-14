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
    private static float[] xArray;
    private static float[] yArray;
    private static float[][] coefArray;
    private static DecimalFormat df;


    public static void main(String args[]){
        File file = new File("input.txt");
        df = new DecimalFormat("#.##");
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
        for (float xVal : xArray) {
            System.out.printf("%-7s", df.format(xVal));
        }
        System.out.println();

        for (float yVal : yArray) {
            System.out.printf("%-7s", df.format(yVal));
        }
        System.out.println("\n");

        dividedDifferenceCoefficientComputation();
        dividedDifferenceTablePrinter();
        printNewtonPolynomial();
        //regexTest();
        regexFind("(x [-+] \\d*[.]?\\d*)|x",
                "3 + 0.5(x - 1) + 0.33(x - 1)(x - 1.5) - 2(x - 1)(x - 1.5)(x)");

        simplifyPolynomial();
    }

    private static void dividedDifferenceCoefficientComputation(){
        coefArray = new float[xArray.length][xArray.length];

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

    private static void simplifyPolynomial(){
        ArrayList<ArrayList<PolynomialElement>> polynomialExpressions = new ArrayList<>();

        ArrayList<PolynomialElement> aL;
        PolynomialElement pe = new PolynomialElement(1,1);

        for(int i = 0; i < xArray.length; i++){

        }
    }

    private static void regexTest(){
        Scanner kb = new Scanner(System.in);

        System.out.print("Enter your regex: ");
        Pattern pattern = Pattern.compile(kb.nextLine());

        System.out.print("Enter input string to search: ");
        Matcher matcher = pattern.matcher(kb.nextLine());

        while (matcher.find()) {
            System.out.printf("I found the text \"%s\" starting at " +
                            "index %d and ending at index %d.%n",
                    matcher.group(), matcher.start(), matcher.end());
        }
    }

    private static List<String> regexFind(String expression, String stringToSearch){
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(stringToSearch);
        ArrayList<String> stringList = new ArrayList<>();

        while (matcher.find()) {
            stringList.add(matcher.group());
        }

        for(String s : stringList){
            System.out.println(s);
        }

        return stringList;
    }
}
