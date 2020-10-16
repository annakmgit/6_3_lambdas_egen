@FunctionalInterface
public interface Filter {
    Measurable[] apply(Measurable[] values, Predicate predicate);
}
