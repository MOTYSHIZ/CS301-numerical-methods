/**
 * Created by Justin Ordonez on 2/26/2017.
 */

import java.io.*;
import java.util.Scanner;

public class Main {
    private static float[] xArray;
    private static float[] yArray;

    public static void main(String args[]){
        File file = new File("input.txt");

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
            System.out.print(xArray[i] + " ");
        } System.out.println();

        for(int i = 0; i < yArray.length; i++){
            System.out.print(yArray[i] + " ");
        }System.out.println("\n");

        dividedDifferenceTablePrinter();
    }

    private static void dividedDifferenceTablePrinter(){
        String[] topLabel = {"x", "f[]", "f[,]", "f[,,]", "f[,,,]"};

        for(int i = 0; i < topLabel.length; i++){
            System.out.printf("%-7s ", topLabel[i]);
        }System.out.println("\n");

        for(int i = 0; i < xArray.length; i++){
            System.out.printf("%-7.3f %-7.3f\n\n", xArray[i], yArray[i]);
            System.out.printf("\n\n");
        }
    }
}
