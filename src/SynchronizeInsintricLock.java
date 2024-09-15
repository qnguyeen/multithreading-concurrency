import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SynchronizeInsintricLock {

    private static int COUNTER = 0;

    public static void main(String... args) throws Exception {

        final Runnable increaseCounterFunc = () -> IntStream
                .range(0, 100)
                .forEach(SynchronizeInsintricLock::increaseCounter);

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println(COUNTER);
    }

    //synchronized trong khai báo phương thức đảm bảo rằng toàn bộ phương thức sẽ được đồng bộ
    //nó sẽ lock trên đối tượng class SynchronizeIntrinsicLock trước khi thực hiện bất kỳ hành động nào bên trong
    private static synchronized void increaseCounter1(int i) {// synchronized method
        ++COUNTER;
    }


    private static void increaseCounter(int i) {// synchronized statement
        synchronized(SynchronizeInsintricLock.class) {
            ++COUNTER;
        }
    }
}
