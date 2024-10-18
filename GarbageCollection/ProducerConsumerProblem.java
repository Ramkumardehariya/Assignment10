import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerProblem {

    // Shared buffer using ArrayBlockingQueue
    private static final int BUFFER_SIZE = 5;
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);

    // Producer class
    static class Producer implements Runnable {
        private int itemNumber = 1; // Item number to produce

        @Override
        public void run() {
            try {
                while (true) {
                    // Produce an item
                    int item = itemNumber++;
                    System.out.println("Producer produced: " + item);
                    // Add the item to the buffer (blocking if full)
                    buffer.put(item);
                    // Simulate production delay
                    Thread.sleep(500); // Sleep for 500 milliseconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer class
    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    // Take an item from the buffer (blocking if empty)
                    int item = buffer.take();
                    System.out.println("Consumer consumed: " + item);
                    // Simulate consumption delay
                    Thread.sleep(1000); // Sleep for 1 second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        // Start the threads
        producerThread.start();
        consumerThread.start();

        // Allow the threads to run for a while, then stop (optional for demo purposes)
        try {
            Thread.sleep(10000); // Run for 10 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Stop the threads (this will not actually stop the while loops in this simple example)
        producerThread.interrupt();
        consumerThread.interrupt();

        System.out.println("Main thread finished.");
    }
}

