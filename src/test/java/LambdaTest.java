import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class LambdaTest {

    public static void main(String[] args) {
        System.out.println("This is from the test main.");

        testLambdas();
    }

    @Test
    static void testLambdas() {

        // As Measurable is a functional interface we can instantiate Measurables through lambdas instead
        // of actually implementing Measurables in a specific subclass
        // Disadvantage: Cannot implement toString(), equals(), etc for these instances
        Measurable f1 = () -> 1;
        Measurable f2 = () -> 1;
        Measurable f3 = () -> 2;
        Measurable f4 = () -> 3;
        Measurable f5 = () -> 5;
        Measurable f6 = () -> 8;
        Measurable f7 = () -> 13;

        Measurable[] array = {f1, f2, f3, f4, f5, f6, f7};

        BinaryFunction adder = null; // ToDo: implement lambda which adds two Measurables

        BinaryFunction subtractor = null; // ToDo: implement lambda which subtracts two Measurables

        BinaryFunction multiplier = null; // ToDo:  utilize method reference from MeasurableUtils

        UnaryFunction negator = null; // ToDo: implement lambda which negates a Measurable

        Measurable result = negator.apply(
                multiplier.apply(
                        adder.apply(f1, f2),
                        subtractor.apply(f3, f4)
                )
        );

        // EXPECTED result after applying lambdas: -((1+1)*(2-3)) => -(2*-1) => -(-2) => 2
        assertEquals(2, result.getValue());

        Filter filter = null;  // ToDo: implement lambda which filters the array according to a given predicate.
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
                        negativeValues[1].getValue()});

        Reducer addition = null; // ToDo: Implement lambda for adding all values together

        Measurable sumOfAllValues = addition.reduce(array);

        // EXPECTED sum: 1 -1 + 2 + 3 - 5 + 8 + 13 = 21
        assertEquals(21, sumOfAllValues.getValue());
    }
}