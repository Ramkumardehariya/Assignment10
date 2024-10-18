import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Thread-safe dictionary class
class ThreadSafeDictionary<K, V> {
    private final ConcurrentMap<K, V> map; // ConcurrentHashMap as the underlying data structure

    // Constructor to initialize the ConcurrentHashMap
    public ThreadSafeDictionary() {
        map = new ConcurrentHashMap<>();
    }

    // Method to put a key-value pair into the dictionary
    public void put(K key, V value) {
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + " added: " + key + " -> " + value);
    }

    // Method to get a value by key from the dictionary
    public V get(K key) {
        V value = map.get(key);
        System.out.println(Thread.currentThread().getName() + " retrieved: " + key + " -> " + value);
        return value;
    }

    // Method to remove a key-value pair from the dictionary
    public void remove(K key) {
        V value = map.remove(key);
        System.out.println(Thread.currentThread().getName() + " removed: " + key + " -> " + value);
    }

    // Method to display the current dictionary contents
    public void display() {
        System.out.println(Thread.currentThread().getName() + " current dictionary: " + map);
    }
}

// Producer class that adds key-value pairs to the dictionary
class Producer implements Runnable {
    private final ThreadSafeDictionary<String, Integer> dictionary;

    public Producer(ThreadSafeDictionary<String, Integer> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            dictionary.put("Key" + i, i); // Adding key-value pairs
            try {
                Thread.sleep(500); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Consumer class that retrieves and removes key-value pairs from the dictionary
class Consumer implements Runnable {
    private final ThreadSafeDictionary<String, Integer> dictionary;

    public Consumer(ThreadSafeDictionary<String, Integer> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            dictionary.get("Key" + i); // Retrieving values
            dictionary.remove("Key" + i); // Removing key-value pairs
            try {
                Thread.sleep(700); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Main class to test the thread-safe dictionary with producer and consumer threads
public class ThreadSafeDictionaryExample {
    public static void main(String[] args) {
        ThreadSafeDictionary<String, Integer> dictionary = new ThreadSafeDictionary<>();

        Thread producerThread = new Thread(new Producer(dictionary), "Producer-Thread");
        Thread consumerThread = new Thread(new Consumer(dictionary), "Consumer-Thread");

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

