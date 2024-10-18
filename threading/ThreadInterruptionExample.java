// Task class that will be interrupted
class InterruptibleTask implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " has started.");

        // Simulate a long-running task
        for (int i = 0; i < 5; i++) {
            // Check for thread interruption
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " was interrupted. Stopping the task.");
                break; // Exit the loop and stop the task if interrupted
            }

            try {
                // Simulate task workload with sleep
                System.out.println(Thread.currentThread().getName() + " is working on iteration " + i);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted during sleep.");
                // If interrupted during sleep, exit the task
                Thread.currentThread().interrupt(); // Re-interrupt the thread to handle it properly
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + " has finished.");
    }
}

// Main class to demonstrate thread interruption
public class ThreadInterruptionExample {
    public static void main(String[] args) {
        // Create and start the interruptible thread
        Thread thread = new Thread(new InterruptibleTask(), "Worker-Thread");
        thread.start();

        try {
            // Let the thread run for 3 seconds before interrupting it
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt the thread after 3 seconds
        System.out.println("Main thread is interrupting " + thread.getName());
        thread.interrupt(); // Signal the thread to stop

        try {
            // Wait for the worker thread to finish
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread has finished.");
    }
}

