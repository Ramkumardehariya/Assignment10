public class LivelockDemo {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private volatile boolean lock1Released = false;
    private volatile boolean lock2Released = false;

    public void thread1Method() {
        while (!lock1Released) {
            synchronized (lock1) {
                if (lock2Released) {
                    System.out.println(Thread.currentThread().getName() + ": Acquired lock1 and proceeding");
                    break;
                } else {
                    System.out.println(Thread.currentThread().getName() + ": Waiting for lock2 to be released, releasing lock1");
                    lock1Released = true;  // Release lock1 and try again
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }
            }
        }
    }

    public void thread2Method() {
        while (!lock2Released) {
            synchronized (lock2) {
                if (lock1Released) {
                    System.out.println(Thread.currentThread().getName() + ": Acquired lock2 and proceeding");
                    break;
                } else {
                    System.out.println(Thread.currentThread().getName() + ": Waiting for lock1 to be released, releasing lock2");
                    lock2Released = true;  // Release lock2 and try again
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }
            }
        }
    }

    public static void main(String[] args) {
        LivelockDemo livelockDemo = new LivelockDemo();

        Thread t1 = new Thread(() -> livelockDemo.thread1Method(), "Thread-1");
        Thread t2 = new Thread(() -> livelockDemo.thread2Method(), "Thread-2");

        t1.start();
        t2.start();
    }
}

