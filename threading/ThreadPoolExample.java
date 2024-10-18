import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Task class implementing Runnable interface
class Task implements Runnable {
    private String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is executing task: " + taskName);
        try {
            // Simulating task duration
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " has finished task: " + taskName);
    }
}

// Main class containing the thread pool example
public class ThreadPoolExample {
    public static void main(String[] args) {
        // Creating a ThreadPoolExecutor with a fixed pool size of 3 threads
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // Cast the ExecutorService to ThreadPoolExecutor for more control
        ThreadPoolExecutor executor = (ThreadPoolExecutor) threadPool;

        // Submit 5 tasks to the pool
        for (int i = 1; i <= 5; i++) {
            Task task = new Task("Task " + i);
            executor.execute(task);
        }

        // Shutdown the thread pool after tasks complete
        executor.shutdown();

        try {
            // Wait for all tasks to complete
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("All tasks finished.");
    }
}

