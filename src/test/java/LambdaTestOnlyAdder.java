import org.junit.jupiter.api.*; // This is Junit 5
//import org.junit.Test;  // This is Junit 4. Do not use.

import static org.junit.jupiter.api.Assertions.*;

class LambdaTestNils {
    
    @Test
    public void testLambdas() {

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

        // This does NOT use anonymous classes or Lambda. This creates an explicit and named new class that implements
        // the interface BinaryFunction.
        class AdderExplicitClass implements BinaryFunction {

            @Override
            public Measurable apply(Measurable m1, Measurable m2) { //override the abstract function
                double sum = m1.getValue() + m2.getValue();

                class MeasurableExplicitClass implements Measurable {

                    @Override
                    public double getValue() {
                        return sum;
                    }
                }

                MeasurableExplicitClass measurableExplicitInstance = new MeasurableExplicitClass();
                return measurableExplicitInstance;
            }
        }

        // Now that we have defined a new class we have to create an instance of it.
        AdderExplicitClass adderExplicitInstance = new AdderExplicitClass();

        // Adder example without any lambdas, this is the function I was asking about /Anna
        // This is the same as above but as an anonymous class.
        BinaryFunction adderNoLambdaButAnonymousClass = new BinaryFunction() {
            public Measurable apply(Measurable a, Measurable b) { //
                double sum = a.getValue() + b.getValue();

                Measurable m = new Measurable() {
                    @Override
                    public double getValue() {
                        return sum;
                    }
                };

                return m;
            }
        };

        // Adder example with one layer of lambda
        BinaryFunction adderOneLayerOfLambdas = (a, b) -> {
            double sum = a.getValue() + b.getValue();

            Measurable m = new Measurable() {
                @Override
                public double getValue() {
                    return sum;
                }
            };

            return m;
        };

        BinaryFunction adderTwoLayersOfLambdas = (a, b) -> () -> a.getValue() + b.getValue();

        // Subtractor example with two layers of lambdas
        BinaryFunction subtractor = (a, b) -> () -> a.getValue() - b.getValue();


        //BinaryFunction multiplier = null; // ToDo:  utilize method reference from MeasurableUtils
        BinaryFunction multiplier = (a, b) -> MeasurableUtils.multiply(a,b);


        //UnaryFunction negator = null; // ToDo: implement lambda which negates a Measurable

        UnaryFunction negator = (a) -> () -> -a.getValue();


        Measurable resultExplicitAdder = negator.apply(
                multiplier.apply(
                        adderExplicitInstance.apply(f1, f2),
                        subtractor.apply(f3, f4)
                )
        );

        Measurable resultAnonymousClassAdder = negator.apply(
                multiplier.apply(
                        adderNoLambdaButAnonymousClass.apply(f1, f2),
                        subtractor.apply(f3, f4)
                )
        );

        Measurable resultOneLayerOfLambdaAdder = negator.apply(
                multiplier.apply(
                        adderOneLayerOfLambdas.apply(f1, f2),
                        subtractor.apply(f3, f4)
                )
        );

        Measurable resultTwoLayersOfLambdas = negator.apply(
                multiplier.apply(
                        adderTwoLayersOfLambdas.apply(f1, f2),
                        subtractor.apply(f3, f4)
                )
        );


        // EXPECTED result after applying lambdas: -((1+1)*(2-3)) => -(2*-1) => -(-2) => 2

///----------------
        System.out.println("First test equals (explicit class) = " + resultExplicitAdder.getValue() );
        System.out.println("First test equals (Anonymous class) = " + resultAnonymousClassAdder.getValue() );
        System.out.println("First test equals (1 Layer of Lambda) = " + resultOneLayerOfLambdaAdder.getValue() );
        System.out.println("First test equals (2 layers of lambdas) = " + resultTwoLayersOfLambdas.getValue() );

        assertEquals(2, resultExplicitAdder.getValue()); // Added test for the "explicit class"
        assertEquals(2, resultAnonymousClassAdder.getValue()); // Same for "anonymous class"
        assertEquals(2, resultOneLayerOfLambdaAdder.getValue()); // Same for "1 layer lambda"
        assertEquals(2, resultTwoLayersOfLambdas.getValue()); // Final form with two layer of lambdas
///-----------------





        //Filter filter = null;  // ToDo: implement lambda which filters the array according to a given predicate.
        // As this will be a general-purpose method you could also put the
        // implementation in MeasurableUtils and utilize a method reference here
        Filter filter = (incomingArray, predicate) -> {
            Measurable[] filteredArray = {};
            for(Measurable currentMeasurable : incomingArray) {
                if (predicate.test(currentMeasurable)) {
                    Measurable[] newFilteredArray = new Measurable[filteredArray.length +1 ];
                    System.arraycopy(filteredArray, 0, newFilteredArray, 0, filteredArray.length);
                    newFilteredArray[filteredArray.length] = currentMeasurable;
                    filteredArray = newFilteredArray;
                }
            }

            return filteredArray;
        };


        //Measurable[] moreThan5 = filter.apply(array, null); // ToDo: implement predicate lambda which accepts
        // Measurables with values > 5
        Measurable[] moreThan5 = filter.apply(array, (m) -> m.getValue() > 5);




        // EXPECTED array after filtering: [f6, f7]
        assertArrayEquals(new Measurable[] {f6, f7}, moreThan5);

        // Negate f2 and f5
        array[1] = negator.apply(array[1]);
        array[4] = negator.apply(array[4]);
        //Measurable[] negativeValues = filter.apply(array, null); // ToDo: utilize method reference from MeasurableUtils
        // to only keep the negative values
        Measurable[] negativeValues = filter.apply(array, (m) -> MeasurableUtils.isNegative(m));

        // EXPECTED array after filtering: [-f2, -f5]
        assertEquals(2, negativeValues.length);
        assertArrayEquals(new double[] {-f2.getValue(),-f5.getValue()},
                new double[] { negativeValues[0].getValue(),
                        negativeValues[1].getValue()});

        //Reducer addition = null; // ToDo: Implement lambda for adding all values together
        Reducer addition = (arrayToSum) -> {
            double sumDouble = 0;
            for (Measurable currentMeasurable : arrayToSum) {
                sumDouble += currentMeasurable.getValue();
            }

            double finalSumDouble = sumDouble; // We are not allowed to reference something we have modified. So create a copy that is final...
            Measurable sumMeasurable = () -> finalSumDouble;

            return sumMeasurable;
        };


        Measurable sumOfAllValues = addition.reduce(array);

        // EXPECTED sum: 1 -1 + 2 + 3 - 5 + 8 + 13 = 21
        assertEquals(21, sumOfAllValues.getValue());
    }
}