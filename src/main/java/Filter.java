@FunctionalInterface
public interface Filter {

    Measurable[] apply(Measurable[] values, Predicate predicate);

    default Measurable [] filter(Measurable[] m1, Predicate p){

        int j = 0;
        int l = 0;
        int temp = m1.length;

        for (int i = 0; i < temp; i++){

            if (p.test(m1[i])){
                j++;
            }
        }

        Measurable[] m2 = new Measurable[j];

        for (int k = 0; k < temp; k++){
               if (p.test(m2[k])){
                   l++;
                   m2[l]=m2[k];
            }

        }

    return m2;
    }
}


//Filter with method filter should receive an array of Measurables and instance of Predicate.
//The returned array should contain elements for which the
//predikat is true.
