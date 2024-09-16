package multithreading_concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RaceCondition {

    public static void main(String... args) throws InterruptedException {
        final var shoppers = IntStream.range(0, 6)
                .mapToObj(Shopper::new)//gán shopper = 0 -> 5
                .collect(Collectors.toList());// chuyển luồng các đối tượng multithreading_concurrency.Shopper thành một list shoppers
        // Chạy toàn bộ các thread
        shoppers.forEach(Thread::start);
        // Chờ tất cả thread hoàn thành
        for (var shopper : shoppers) {
            shopper.join();
        }
        System.out.println("Total packs: " + Shopper.MASK_PACK_COUNT);
    }
}

class Shopper extends Thread {

    static int MASK_PACK_COUNT = 1;

    static CyclicBarrier BARRIER = new CyclicBarrier(6);//6 thread phải đợi nhau tại barrier
    static CountDownLatch CDL = new CountDownLatch(3);
    //CyclicBarrier sẽ giải phóng các thread đang chờ khi tất cả các thread đang chờ đó đạt đến một giá trị nhất định - có thể reset
    //CountDownLatch giải phóng các thread đang chờ khi giá trị biến đếm trở về 0 - disposable

    Shopper(int i) {
        setName(i % 2 == 0 ? "Husband" : "Wife");
    }

    @Override
    public void run() {
        addMask(getName());
    }

    static void addMask(String threadName) {
        if ("Husband".equalsIgnoreCase(threadName)) {
            synchronized (Shopper.class) {
                MASK_PACK_COUNT += 1;
                System.out.println("Husband adds 1 pack");
            }
            // thread sẽ đợi ở barrier cho đến khi tất cả các thread còn lại cũng gọi phương thức này
            waitAtBarrierCB();//sau khi husband thêm mask chúng cần chờ tất cả các thread khác (bao gồm cả "Wife") hoàn thành công việc
            CDL.countDown();// trừ biến đếm(3) cho 1
            return;
        }
        waitAtBarrierCB();//trước khi wife * mask - đảm bảo rằng tất cả các thread đã đến barrier
        waitAtBarrierCDL();//chờ cho đến khi biến đếm đó về mức 0
        synchronized (Shopper.class) {
            MASK_PACK_COUNT *= 3;
            System.out.println("Wife multiple 3 times");
        }
    }

    static void waitAtBarrierCB() {
        try {
            BARRIER.await();//chờ các thread đều gọi tới await()
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    static void waitAtBarrierCDL() {
        try {
            BARRIER.await();//chờ cho đến khi biến đếm đó về mức 0
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
