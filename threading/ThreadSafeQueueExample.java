import java.util.LinkedList;
import java.util.Queue;

// Thread-safe queue class
class ThreadSafeQueue<T> {
    private final Queue<T> queue = new LinkedList<>(); // Shared queue
    private final int capacity; // Maximum capacity of the queue

    // Constructor to set the capacity of the queue
    public ThreadSafeQueue(int capacity) {
        this.capacity = capacity;
    }

    // Synchronized method to add an item to the queue
    public synchronized void enqueue(T item) throws InterruptedException {
        // If the queue is full, wait for space to become available
        while (queue.size() == capacity) {
            System.out.println(Thread.currentThread().getName() + " is waiting to enqueue...");
            wait(); // Release the lock and wait
        }

        // Add the item to the queue
        queue.add(item);
        System.out.println(Thread.currentThread().getName() + " enqueued " + item);

        // Notify other threads that an item has been added
        notifyAll(); // Wake up threads that are waiting to dequeue
    }

    // Synchronized method to remove and return an item from the queue
    public synchronized T dequeue() throws InterruptedException {
        // If the queue is empty, wait for items to be enqueued
        while (queue.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " is waiting to dequeue...");
            wait(); // Release the lock and wait
        }

        // Remove and return the first item from the queue
        T item = queue.poll();
        System.out.println(Thread.currentThread().getName() + " dequeued " + item);

        // Notify other threads that space has become available
        notifyAll(); // Wake up threads that are waiting to enqueue

        return item;
    }
}

// Producer class that produces items and enqueues them in the queue
class Producer implements Runnable {
    private final ThreadSafeQueue<Integer> queue;

    public Producer(ThreadSafeQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                queue.enqueue(i); // Enqueue items 1 to 5
                Thread.sleep(1000); // Simulate some delay between enqueues
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Consumer class that consumes items by dequeuing them from the queue
class Consumer implements Runnable {
    private final ThreadSafeQueue<Integer> queue;

    public Consumer(ThreadSafeQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                queue.dequeue(); // Dequeue 5 items
                Thread.sleep(1500); // Simulate some delay between dequeues
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Main class to test the thread-safe queue with producer and consumer threads
public class ThreadSafeQueueExample {
    public static void main(String[] args) {
        ThreadSafeQueue<Integer> queue = new ThreadSafeQueue<>(3); // Queue with capacity 3

        Thread producerThread = new Thread(new Producer(queue), "Producer-Thread");
        Thread consumerThread = new Thread(new Consumer(queue), "Consumer-Thread");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks finished.");
    }
}

