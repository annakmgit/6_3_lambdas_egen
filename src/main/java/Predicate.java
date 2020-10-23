
@FunctionalInterface
public interface Predicate {
    boolean test(Measurable m);
}

//This method is supposed to return something but it need one, and only one,
// abstract method to be a functional interface.
