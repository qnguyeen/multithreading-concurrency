package lamda;

import java.util.function.Predicate;

interface I{
    void m();// functional interface
}

public class BasicLamdas {
    public static void main(String[] args) {
        //pre java 8
        I i = new I() {
            @Override
            public void m() {
                System.out.println("I::m()");
            }
        };
        i.m(); // I::m()

        //java 8
        I lamdaI1 = () ->{ // () = m()
            System.out.println("lamda version 1");
        };
        I lamdaI2 = () -> System.out.println("lamda version 2");

        lamdaI1.m();
        lamdaI2.m();

        Evaluate<Integer> lamda = l -> l < 0;
        System.out.println(lamda.isNegative(-1)); // true
        System.out.println(lamda.isNegative(1)); // false


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
    }
    // dùng predicate với method
    public static <T> boolean check(T t, Predicate<T> lamda) {
        return lamda.test(t);
    }
}
