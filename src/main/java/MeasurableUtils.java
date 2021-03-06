



public class MeasurableUtils {

    public static Measurable multiply(Measurable m1, Measurable m2) {
        return () -> m1.getValue() * m2.getValue();                         //returnerar en instans av interfacet measurable
    }

    public static boolean isNegative(Measurable measurable) {
        return measurable.getValue()<0;
    }

}
