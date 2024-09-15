import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MutualExclusion {

    private static int COUNTER = 0;
    private static Lock LOCK = new ReentrantLock();// key để lock và unlock

    public static void main(String... args) throws Exception {

        // Runnable là interface đại diện cho một tác vụ có thể chạy trên một (thread)
        // Nó định nghĩa một phương thức duy nhất là run(), bất kỳ class nào implement Runnable đều có thể được chạy bởi một thread
        final Runnable increaseCounterFunc = () -> IntStream// tạo ra một luồng các số nguyên từ một dãy số
                .range(0, 100)
                .forEach(MutualExclusion::increaseCounter);//method reference - tham chiếu trực tiếp -> method

        // tạo thread thông qua lamda
//        Thread thread = new Thread(() -> {
//            System.out.println("Thread is running");
//        });
//        thread.start();

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();//đảm bảo rằng main thread sẽ chờ cả hai thread hoàn thành trước khi tiếp tục thực hiện

        System.out.println(COUNTER);
    }

    private static void increaseCounter(int i) {// đây là critical region
        LOCK.lock();// số lần lock phải tương ứng với unlock
        LOCK.lock();
        try {
            ++COUNTER;
        } finally {
            LOCK.unlock();
            LOCK.unlock();
        }
    }
}
