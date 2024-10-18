import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Producer class that adds items to the queue
class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Producing: " + i);
                queue.put(i); // Blocks if the queue is full
                Thread.sleep(500); // Simulate some delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Consumer class that removes items from the queue
class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                Integer item = queue.take(); // Blocks if the queue is empty
                System.out.println("Consuming: " + item);
                Thread.sleep(1000); // Simulate some delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Main class to test the synchronous queue with producer and consumer
public class BlockingQueueDemo {
    public static void main(String[] args) {
        // Create a BlockingQueue with a capacity of 3
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(queue), "Producer-Thread");
        Thread consumerThread = new Thread(new Consumer(queue), "Consumer-Thread");

        // Start the threads
        producerThread.start();
        consumerThread.start();

        try {
            // Wait for both threads to finish
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks finished.");
    }
}

