import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Deadlock {//các thread đang tranh chấp resource với nhau, đều các chờ thread còn lại để thực thi tiếp

    private static final Lock wKey = new ReentrantLock();
    private static final Lock eKey = new ReentrantLock();

    private static int openCaseCount = 0;

    public static void main(String... args) {

        //try catch critical section và đưa các method release lock vào finally
        var xThread = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                try {
                    eKey.lock();
                    wKey.lock();
                    openCase();
                } finally {
                    wKey.unlock();
                    eKey.unlock();
                }
            });
        });

        var yThread = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                try {
                    eKey.lock();
                    wKey.lock();
                    openCase();
                } finally {
                    wKey.unlock();
                    eKey.unlock();
                }
            });
        });

        xThread.start();
        yThread.start();
    }

    private static void openCase() {
        ++openCaseCount;
        System.out.println("Opened case! Count: " + openCaseCount);
    }

}
