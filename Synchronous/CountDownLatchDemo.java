import java.util.concurrent.CountDownLatch;

// Task class that simulates a task being performed by a thread
class Task implements Runnable {
    private final CountDownLatch latch;
    private final String taskName;

    public Task(CountDownLatch latch, String taskName) {
        this.latch = latch;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println(taskName + " is starting...");
            // Simulate some work with sleep
            Thread.sleep((int) (Math.random() * 3000));
            System.out.println(taskName + " has completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown(); // Decrement the count of the latch
        }
    }
}

// Main class to demonstrate CountDownLatch
public class CountDownLatchDemo {
    public static void main(String[] args) {
        final int numberOfTasks = 5;
        CountDownLatch latch = new CountDownLatch(numberOfTasks); // Initialize the latch with the number of tasks

        // Create and start tasks
        for (int i = 1; i <= numberOfTasks; i++) {
            Thread taskThread = new Thread(new Task(latch, "Task-" + i));
            taskThread.start();
        }

        try {
            // Wait for all tasks to complete
            latch.await(); // This will block until the count reaches zero
            System.out.println("All tasks have completed. Proceeding to the next step.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

