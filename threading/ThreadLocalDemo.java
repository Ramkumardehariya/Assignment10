// Class to demonstrate thread-local variables
class ThreadLocalExample {
    // Creating a ThreadLocal variable to hold the thread-specific data
    private static ThreadLocal<Integer> threadLocalVariable = ThreadLocal.withInitial(() -> 0);

    // Method to increment the thread-local variable
    public void increment() {
        // Get the current value for the thread
        Integer currentValue = threadLocalVariable.get();
        currentValue++; // Increment the value
        threadLocalVariable.set(currentValue); // Update the value
        System.out.println(Thread.currentThread().getName() + " incremented value to: " + currentValue);
    }

    // Method to retrieve the current value of the thread-local variable
    public void printValue() {
        System.out.println(Thread.currentThread().getName() + " has value: " + threadLocalVariable.get());
    }
}

// Runnable class that uses the ThreadLocalExample
class MyRunnable implements Runnable {
    private final ThreadLocalExample example;

    public MyRunnable(ThreadLocalExample example) {
        this.example = example;
    }

    @Override
    public void run() {
        // Increment the thread-local variable a few times
        for (int i = 0; i < 5; i++) {
            example.increment();
            example.printValue();

            try {
                Thread.sleep(100); // Simulate some delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Main class to test thread-local variables
public class ThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocalExample example = new ThreadLocalExample();

        // Create multiple threads that will use the same ThreadLocalExample instance
        Thread thread1 = new Thread(new MyRunnable(example), "Thread-1");
        Thread thread2 = new Thread(new MyRunnable(example), "Thread-2");

        thread1.start();
        thread2.start();

        try {
            // Wait for both threads to finish
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished execution.");
    }
}
