import org.junit.jupiter.api.*; // This is Junit 5
//import org.junit.Test;  // This is Junit 4. Do not use.

import static org.junit.jupiter.api.Assertions.*;

class LambdaTest {

    public static void main(String[] args) {
        System.out.println("This is from the test main.");

        // testLambdas();
    }

    @Test
    public void nilsTest() {
        assertEquals(2,1+1);
        System.out.println("nilsTest");
    }

    @Test
    public void testLambdas() {

        // As Measurable is a functional interface we can instantiate Measurables through lambdas instead
        // of actually implementing Measurables in a specific subclass
        // Disadvantage: Cannot implement toString(), equals(), etc for these instances
        Measurable f1 = () -> 1;  //f1.getValue  means it returns 1.
        Measurable f2 = () -> -1;
        Measurable f3 = () -> 2;  //since it's a functional interface there is only ONE method, creates anonymous class, () calls the only method
        Measurable f4 = () -> 3;
        Measurable f5 = () -> -5;
        Measurable f6 = () -> 8;
        Measurable f7 = () -> 13;

        Measurable[] array = {f1, f2, f3, f4, f5, f6, f7};

        //BinaryFunction adder = null; // ToDo: implement lambda which adds two Measurables
        BinaryFunction adder = (Measurable m1, Measurable m2) -> () -> m1.getValue() + m2.getValue();   //first parenthise will be the only function in a binary function : apply
        //after the first arrow, the only function what it will return. Second level lambda.
        //after second arrow, get value in the measurable som man håller på att returna i sin binary function.apply

        //BinaryFunction subtractor = null; // ToDo: implement lambda which subtracts two Measurables
        BinaryFunction subtractor = (Measurable m3, Measurable m4) -> () -> m3.getValue() - m4.getValue();

        //BinaryFunction multiplier = null; // ToDo:  utilize method reference from MeasurableUtils
        //multiplier.apply(Measurable m1, Measurable m2);

        BinaryFunction Class1 = (Measurable m5, Measurable m6) -> {
            double result = multiply(m5, m6);

            Measurable n = new Measurable() {
                @Override
                public double getValue() {
                    return result;
                }
            };
            return n;
        }
    };

        /*UnaryFunction negator = null; // ToDo: implement lambda which negates a Measurable

        Measurable result = negator.apply(
                multiplier.apply(
                        adder.apply(f1, f2),
                        subtractor.apply(f3, f4)
                )
        );

        // EXPECTED result after applying lambdas: -((1+1)*(2-3)) => -(2*-1) => -(-2) => 2
        assertEquals(2, result.getValue());

        Filter filter = (m1, p) -> { //Is this really a Lambda expression?

            int j = 0;
            int l = 0;
            int temp = m1.length;

            for (int i = 0; i < temp; i++) {

                if (p.test(m1[i])) {
                    j++;
                }
            }

            Measurable[] m2 = new Measurable[j];

            for (int k = 0; k < temp; k++) {
                if (p.test(m2[k])) {
                    l++;
                    m2[l] = m2[k];
                }
            }
            return m2;
        };


        // ToDo: implement lambda which filters the array according to a given predicate.
        // As this will be a general-purpose method you could also put the
        // implementation in MeasurableUtils and utilize a method reference here

        Measurable[] moreThan5 = filter.apply(array, null); // ToDo: implement predicate lambda which accepts
        // Measurables with values > 5

        // EXPECTED array after filtering: [f6, f7]
        assertArrayEquals(new Measurable[] {f6, f7}, moreThan5);

        // Negate f2 and f5
        array[1] = negator.apply(array[1]);
        array[4] = negator.apply(array[4]);
        Measurable[] negativeValues = filter.apply(array, null); // ToDo: utilize method reference from MeasurableUtils
        // to only keep the negative values

        // EXPECTED array after filtering: [-f2, -f5]
        assertEquals(2, negativeValues.length);
        assertArrayEquals(new double[] {-f2.getValue(),-f5.getValue()},
                new double[] { negativeValues[0].getValue(),
                        negativeValues[1].getValue()});*/


      /*  class AdditionExplicitClass implements Reducer {

            @Override
            public Measurable reduce(Measurable[] arrayM) {
                double sum = 0;

                for (int i = 0; i < arrayM.length; i++) {
                    Measurable m = array[i];
                    sum = (sum) + (m.getValue());
                    System.out.println("sum: " + sum);
                }

                final double sum2 = sum;

                class n implements Measurable {
                    @Override
                    public double getValue() {
                        return sum2;
                    }
                };
                n m = new n();
                return m;
            }
        };*/

        /*Reducer AdditionExplicitClass2 = new Reducer() {    //this is a version with an anonymous class
            public Measurable reduce(Measurable[] arrayM) {
                double sum = 0;

                for (int i = 0; i < arrayM.length; i++) {
                    Measurable m = array[i];
                    sum = sum + m.getValue();

                }

                final double sum2 = sum;

                Measurable n = new Measurable() {
                    @Override
                    public double getValue() {
                        return sum2;
                    }
                };
                return n;
            }
        };*/

        Reducer AdditionExplicitClass3 = (Measurable[] arrayM) ->
            {   double sum = 0;
                for (int i = 0; i < arrayM.length; i++) {
                    Measurable m = array[i];
                    sum = sum + m.getValue();
                }
                final double sum2 = sum;

                Measurable n = new Measurable() {
                    @Override
                    public double getValue() {
                        return sum2;
                    }
                };
                return n;
            };

        //Reducer addition = new AdditionExplicitClass();                    // ToDo: Implement lambda for adding all values together
        Measurable sumOfAllValues = AdditionExplicitClass3.reduce(array); //do not need to use addition here
        //EXPECTED sum: 1 -1 + 2 + 3 - 5 + 8 + 13 = 21
        assertEquals(21, sumOfAllValues.getValue());
    }
}