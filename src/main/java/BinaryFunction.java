@FunctionalInterface
public interface BinaryFunction {

    Measurable apply(Measurable m1, Measurable m2);


}



//A functional interface has only one abstract method
// but it can have multiple default methods.