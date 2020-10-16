import org.junit.jupiter.api.*; // This is Junit 5
//import org.junit.Test;  // This is Junit 4. Do not use.

import static org.junit.jupiter.api.Assertions.*;

class LambdaTestNilsOnlyAdder {

    @Test
    public void testAdderLambdas() {

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

// ------------------------------

        // Example 1.
        // This is how you are used to handle an interface, you create a new class that implements the
        // interface and then that class overrides the abstract function from the interface.
        //
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
                return measurableExplicitInstance; //returns object of type Measurable that contains a double
            }
        }

        // Now that we have defined a new class we have to create an instance of it.
        AdderExplicitClass adderExplicitInstance = new AdderExplicitClass();

        // --------

        // Example 2.
        //
        // Adder example without any lambdas, this is the function I was asking about /Anna
        // This is the same as above but as an anonymous class.
        // See that we directly get an instance of a class without writing the word 'class' as above.
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

        // --------

        // Example 3.
        // Adder example with one layer of lambda
        // We replace the anonymous class with a Lambda.
        BinaryFunction adderOneLayerOfLambdas = (a, b) -> {
            double sum = a.getValue() + b.getValue();

            Measurable m = new Measurable() {   // See we still use an anonymous class here!
                @Override
                public double getValue() {
                    return sum;
                }
            };

            return m;
        };

        // --------

        // Example 4.
        // Go one step further and replace the inner Measurable class with another Lambad.
        // Everything is contained in one line!
        BinaryFunction adderTwoLayersOfLambdas = (a, b) -> () -> a.getValue() + b.getValue();

        // -----------------------------------------------------

        // Now lets implement the other functions in the form of a two layer Lambda

        BinaryFunction subtractor = (a, b) -> () -> a.getValue() - b.getValue();

        BinaryFunction multiplier = (a, b) -> MeasurableUtils.multiply(a, b);

        UnaryFunction negator = (a) -> () -> -a.getValue();

// ------------------------------

        // Now use each version of the Adder functions to see that they do the same

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


// ------------------------------
        // And finally print the results and then check them with a Junit test

        // EXPECTED result after applying lambdas: -((1+1)*(2-3)) => -(2*-1) => -(-2) => 2

        System.out.println("First test equals (explicit class) = " + resultExplicitAdder.getValue());
        System.out.println("First test equals (Anonymous class) = " + resultAnonymousClassAdder.getValue());
        System.out.println("First test equals (1 Layer of Lambda) = " + resultOneLayerOfLambdaAdder.getValue());
        System.out.println("First test equals (2 layers of lambdas) = " + resultTwoLayersOfLambdas.getValue());

        assertEquals(2, resultExplicitAdder.getValue()); // Added test for the "explicit class"
        assertEquals(2, resultAnonymousClassAdder.getValue()); // Same for "anonymous class"
        assertEquals(2, resultOneLayerOfLambdaAdder.getValue()); // Same for "1 layer lambda"
        assertEquals(2, resultTwoLayersOfLambdas.getValue()); // Final form with two layer of lambdas
// ------------------------------
    }
}
