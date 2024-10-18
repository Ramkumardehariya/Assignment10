import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class ProducerConsumerProblem {

    private static final int BUFFER_SIZE = 5;  // Maximum size of the buffer
    private static Queue<Integer> buffer = new LinkedList<>();  // Shared buffer

    // Semaphores for synchronization
    private static Semaphore emptySlots = new Semaphore(BUFFER_SIZE);  // Tracks empty slots
    private static Semaphore filledSlots = new Semaphore(0);            // Tracks filled slots
    private static Semaphore mutex = new Semaphore(1);                  // Ensures mutual exclusion on buffer

    // Producer class
    static class Producer extends Thread {
        private String name;

        public Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int item = produceItem();
                    
                    emptySlots.acquire();  // Wait for an empty slot
                    mutex.acquire();       // Ensure exclusive access to buffer

                    // Add produced item to the buffer
                    buffer.add(item);
                    System.out.println(name + " produced: " + item + " (Buffer size: " + buffer.size() + ")");

                    mutex.release();       // Release exclusive access
                    filledSlots.release(); // Signal that a filled slot is available

                    Thread.sleep(1000);    // Simulate time to produce an item
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Simulates item production
        private int produceItem() {
            return (int) (Math.random() * 100);  // Produces a random number
        }
    }

    // Consumer class
    static class Consumer extends Thread {
        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    filledSlots.acquire();  // Wait for a filled slot
                    mutex.acquire();        // Ensure exclusive access to buffer

                    // Remove consumed item from the buffer
                    int item = buffer.remove();
                    System.out.println(name + " consumed: " + item + " (Buffer size: " + buffer.size() + ")");

                    mutex.release();        // Release exclusive access
                    emptySlots.release();   // Signal that an empty slot is available

                    Thread.sleep(1500);     // Simulate time to consume an item
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Start producer threads
        Producer producer1 = new Producer("Producer-1");
        Producer producer2 = new Producer("Producer-2");

        // Start consumer threads
        Consumer consumer1 = new Consumer("Consumer-1");
        Consumer consumer2 = new Consumer("Consumer-2");

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }
}

