
@FunctionalInterface
public interface Predicate{

        public boolean test(Measurable m);
}

//This method is supposed to return something but it need one, and only one,
// abstract method to be a functional interface.


    /*default boolean test(Measurable m){

        if (m.getValue() > 5) return true;
        return false;
    }*/