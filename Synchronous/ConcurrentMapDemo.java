import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Class to demonstrate a synchronous map using ConcurrentHashMap
class SynchronousMap {
    private final ConcurrentMap<String, Integer> map; // Thread-safe map

    // Constructor to initialize the ConcurrentHashMap
    public SynchronousMap() {
        map = new ConcurrentHashMap<>();
    }

    // Method to put a key-value pair into the map
    public void put(String key, Integer value) {
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + " added: " + key + " -> " + value);
    }

    // Method to get a value by key from the map
    public Integer get(String key) {
        Integer value = map.get(key);
        System.out.println(Thread.currentThread().getName() + " retrieved: " + key + " -> " + value);
        return value;
    }

    // Method to remove a key-value pair from the map
    public void remove(String key) {
        Integer value = map.remove(key);
        System.out.println(Thread.currentThread().getName() + " removed: " + key + " -> " + value);
    }

    // Method to display the current map contents
    public void display() {
        System.out.println(Thread.currentThread().getName() + " current map: " + map);
    }
}

// Producer class that adds key-value pairs to the map
class Producer implements Runnable {
    private final SynchronousMap synchronousMap;

    public Producer(SynchronousMap synchronousMap) {
        this.synchronousMap = synchronousMap;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            synchronousMap.put("Key" + i, i); // Adding key-value pairs
            synchronousMap.display(); // Display current map contents

            try {
                Thread.sleep(500); // Simulate some delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Consumer class that retrieves and removes key-value pairs from the map
class Consumer implements Runnable {
    private final SynchronousMap synchronousMap;

    public Consumer(SynchronousMap synchronousMap) {
        this.synchronousMap = synchronousMap;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            synchronousMap.get("Key" + i); // Retrieving values
            synchronousMap.remove("Key" + i); // Removing key-value pairs
            synchronousMap.display(); // Display current map contents

            try {
                Thread.sleep(700); // Simulate some delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Main class to test the synchronous map with producer and consumer threads
public class ConcurrentMapDemo {
    public static void main(String[] args) {
        SynchronousMap synchronousMap = new SynchronousMap();

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(synchronousMap), "Producer-Thread");
        Thread consumerThread = new Thread(new Consumer(synchronousMap), "Consumer-Thread");

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

