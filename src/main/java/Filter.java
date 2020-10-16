@FunctionalInterface
public interface Filter {

    Measurable[] apply(Measurable[] values, Predicate predicate);

}


//Filter with method filter should receive an array of Measurables and instance of Predicate.
//The returned array should contain elements for which the
//predikat is true.
