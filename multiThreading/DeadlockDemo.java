public class DeadlockDemo {

    // Two resources (locks)
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + ": Acquired lock1, waiting for lock2");
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + ": Acquired lock2, executing method1");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName() + ": Acquired lock2, waiting for lock1");
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + ": Acquired lock1, executing method2");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockDemo deadlockDemo = new DeadlockDemo();

        // Thread 1 will try to acquire lock1 first, then lock2
        Thread t1 = new Thread(() -> deadlockDemo.method1(), "Thread-1");

        // Thread 2 will try to acquire lock2 first, then lock1
        Thread t2 = new Thread(() -> deadlockDemo.method2(), "Thread-2");

        t1.start();
        t2.start();
    }
}

