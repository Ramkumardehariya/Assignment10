import java.util.concurrent.CyclicBarrier;

// Task class that simulates a task being performed by a thread
class Task implements Runnable {
    private final CyclicBarrier barrier;
    private final String name;

    public Task(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is doing some work...");
            // Simulate some work with sleep
            Thread.sleep((int) (Math.random() * 3000));
            System.out.println(name + " has completed its work. Waiting at the barrier.");

            // Wait at the barrier
            barrier.await(); // This will block until all threads reach the barrier

            // After the barrier, all threads proceed
            System.out.println(name + " has crossed the barrier and is continuing work.");

        } catch (InterruptedException | java.util.concurrent.BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " was interrupted or broken at the barrier.");
        }
    }
}

// Main class to demonstrate CyclicBarrier
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        final int numberOfThreads = 3; // Total number of threads
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All threads have reached the barrier. The barrier is being released.");
        });

        // Create and start threads
        for (int i = 1; i <= numberOfThreads; i++) {
            Thread thread = new Thread(new Task(barrier, "Thread-" + i));
            thread.start();
        }
    }
}

