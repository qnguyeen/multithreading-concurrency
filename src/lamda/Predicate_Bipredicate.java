package lamda;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

interface Evaluate<T>{
    boolean isNegative(T t);// for custom funtional interface
}

public class Predicate_Bipredicate {
    public static void main(String[] args) {

        // predicate : chỉ có 1 method test(), nhận đối tượng kiểu T và trả về boolean
        Predicate<Integer> isNegative = l -> l < 0; // = interface Evaluate<T>
        System.out.println(isNegative.test(-2));

        // dùng predicate với method
        int x = 4;
        System.out.println(x + "even? " + check(x,n -> n % 2 == 0));

        String name = "Mr. Nguyeen";
        String name1 = "Mhh. Nguyeen";
        System.out.println(name + " equal " + check(name, s -> s.startsWith("Mr."))); // true
        System.out.println(name1 + " equal " + check(name1, s -> s.startsWith("Mr."))); // false

        // bipredicate (khác kiểu T và Y)
        BiPredicate<String, Integer> checkLength = (str, len) -> str.length() == len;
        System.out.println(checkLength.test("Viet Nam",8)); // true
    }
    // dùng predicate với method (có chung kiển T)
    public static <T> boolean check(T t, Predicate<T> lamda) {
        return lamda.test(t);
    }
}
