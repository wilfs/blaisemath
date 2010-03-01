/**
 * BasicMathUtils.java
 * Created on Dec 15, 2009
 */

package scio.coordinate.utils;

/**
 * <p>
 *    This class ...
 * </p>
 * @author Elisha Peterson
 */
public class BasicMathUtils {

    /**
     * Returns the negative and positive roots of a quadratic equation a*x^2+b*x+c=0 as a double array.<br>
     * Note that if a<0 then the negative root has the smallest x-value, and if a>0 then the positive root has the smallest x-value.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant coefficient
     * @return <li> an array [r_neg, r_pos], where r_neg is the negative root and r_pos is the positive root;
     *         <li> an array [NaN, NaN] if the roots are complex;
     *         <li> an array [r] if a=0 so the equation is a line and there is one root r;
     *         <li> null if a=0 and b=0 so that there are no solutions
     */
    public static double[] quadraticRoots(double a, double b, double c) {
        double disc = b*b-4*a*c;
        if (disc < 0)
            return new double[] { Double.NaN, Double.NaN };
        else if (a==0 && b==0)
            return null;
        else if (a==0)
            return new double[] { -c/b };
        else
            return new double[] { (-b-Math.sqrt(disc))/(2*a), (-b+Math.sqrt(disc))/(2*a) };
    }

    /**
     * Computes and returns intervals a quadratic equation a*x^2+b*x+c=0 is positive.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant coefficient
     * @return 1 or more intervals in the form of double arrays of the form [x_min, x_max];
     *   the min and max may be infinite
     */
    public static double[][] positiveIntervalsOfQuadratic(double a, double b, double c) {
        double disc = b*b-4*a*c;
        double[] roots = quadraticRoots(a, b, c);
        if (disc < 0)
            return a > 0 ? new double[][]{ {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY} } : new double[][]{{}};
        else if (a==0 && b==0)
            return c > 0 ? new double[][]{ {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY} } : new double[][]{{}};
        else if (a==0)
            return b > 0 ? new double[][]{ {-c/b, Double.POSITIVE_INFINITY} } : new double[][]{ {Double.NEGATIVE_INFINITY, -c/b} };
        else
            return a > 0 ? new double[][]{ {Double.NEGATIVE_INFINITY, roots[0]}, {roots[1], Double.POSITIVE_INFINITY} } :
                new double[][]{ {roots[0], roots[1]} };
    }

}