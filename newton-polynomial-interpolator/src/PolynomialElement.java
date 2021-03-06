import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Justin Ordonez on 3/13/2017.
 */
public class PolynomialElement implements Comparable<PolynomialElement>{
    public double coefficient;
    public int degree;
    public static DecimalFormat df = new DecimalFormat("#.###");

    PolynomialElement(double coefficient, int degree){
        this.degree = degree;
        this.coefficient = coefficient;
    }

    public String getStringRepresentation(){
        roundCoefficient();
        if(Math.abs(degree) > 1)return "" + df.format(coefficient) + "x^" + degree;
        else if(Math.abs(degree) == 1) return "" + df.format(coefficient*degree) + "x";
        else return "" + df.format(coefficient);
    }

    private void roundCoefficient(){
        if( Math.abs(Math.round(coefficient) - coefficient) <= .005)coefficient = Math.round(coefficient);
    }

    @Override
    public int compareTo(PolynomialElement other){
        return other.degree - this.degree;
    }
}
