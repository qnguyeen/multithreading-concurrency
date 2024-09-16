import java.util.concurrent.locks.LockSupport;

public class Main {
    public static void main(String[] args) {
        // khai báo 1 thread -> state new
        final var firstThread = new Thread(() -> System.out.println("First thread"));
        final var secondThread = new Thread(() -> System.out.println("Second thread"));

        // khi start thread, nó được đưa vào ready queue
        firstThread.start();
        secondThread.start();

        //trạng thái waiting : trạng thái thread này chờ thread khác và không có thời gian cụ thể
        //object.wait();
        //thread.join();
        //LockSupport.park();

        // timed waiting : trạng thái của thread khi chờ thread khác nhưng có thời gian giới hạn -> nếu quá thời gian chờ, thread tiếp tục chạy và không chờ nữa

        // blocked :  khi các thread cùng truy cập vào shared resource, chỉ có duy nhất 1 thread thành công, các thread còn lại rơi vào trạng thái Blocked

        // data race : khi 2 thread cùng thay đổi gtri biến i, i được copy từ Ram -> L1,2,3 cache - processor register(cpu)
        // thread sẽ tương tác với registor(cpu) chứ k phải với Ram, sau khi thay đối mới copy lại về Ram
        // -> sử dụng critical section - đoạn code dùng để read/write shared resource
        // để giải quyết (nutual exclusion- kiểu nếu A thì B thôi, B thì A thôi, chỉ only 1 thread sdung key tại 1 thời điểm)

        // race condition : vấn đề sai sót về mặt thời gian hoặc thứ tự thực thi của các thread
    }
}
