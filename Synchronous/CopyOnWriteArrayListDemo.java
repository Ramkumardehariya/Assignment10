import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Class to demonstrate a synchronous list using CopyOnWriteArrayList
class SynchronousList {
    private final List<Integer> list; // Thread-safe list

    // Constructor to initialize the CopyOnWriteArrayList
    public SynchronousList() {
        list = new CopyOnWriteArrayList<>();
    }

    // Method to add an item to the list
    public void add(Integer value) {
        list.add(value);
        System.out.println(Thread.currentThread().getName() + " added: " + value);
    }

    // Method to get an item from the list by index
    public Integer get(int index) {
        Integer value = list.get(index);
        System.out.println(Thread.currentThread().getName() + " retrieved: " + value);
        return value;
    }

    // Method to remove an item from the list by index
    public void remove(int index) {
        Integer value = list.remove(index);
        System.out.println(Thread.currentThread().getName() + " removed: " + value);
    }

    // Method to display the current list contents
    public void display() {
        System.out.println(Thread.currentThread().getName() + " current list: " + list);
    }

    // Method to check if the list is not empty
    public boolean isEmpty() {
        return list.isEmpty();
    }
}

// Producer class that adds items to the list
class Producer implements Runnable {
    private final SynchronousList synchronousList;

    public Producer(SynchronousList synchronousList) {
        this.synchronousList = synchronousList;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            synchronousList.add(i); // Adding items to the list
            synchronousList.display(); // Display current list contents

            try {
                Thread.sleep(500); // Simulate some delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Consumer class that retrieves and removes items from the list
class Consumer implements Runnable {
    private final SynchronousList synchronousList;

    public Consumer(SynchronousList synchronousList) {
        this.synchronousList = synchronousList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            // Check if the list is not empty before getting and removing
            if (!synchronousList.isEmpty()) {
                synchronousList.get(0); // Retrieve the first item
                synchronousList.remove(0); // Remove the first item
                synchronousList.display(); // Display current list contents
            }

            try {
                Thread.sleep(700); // Simulate some delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Main class to test the synchronous list with producer and consumer threads
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        SynchronousList synchronousList = new SynchronousList();

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(synchronousList), "Producer-Thread");
        Thread consumerThread = new Thread(new Consumer(synchronousList), "Consumer-Thread");

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
