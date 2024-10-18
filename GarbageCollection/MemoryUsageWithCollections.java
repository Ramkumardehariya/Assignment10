import java.util.ArrayList;
import java.util.List;

public class MemoryUsageWithCollections {
    
    // Class with a large data structure (e.g., a large byte array)
    static class LargeData {
        private byte[] data = new byte[10 * 1024 * 1024]; // 10 MB array
    }

    // Method to print current memory usage
    private static void printMemoryUsage(String stage) {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.println(stage + " - Used memory: " + (usedMemory / (1024 * 1024)) + " MB");
    }

    public static void main(String[] args) {
        // List to hold instances of LargeData
        List<LargeData> largeDataList = new ArrayList<>();

        // Print memory usage before creating objects
        printMemoryUsage("Before creating objects");

        // Create and add 10 instances of LargeData to the list
        for (int i = 0; i < 10; i++) {
            largeDataList.add(new LargeData());
        }

        // Print memory usage after creating objects
        printMemoryUsage("After creating objects");

        // Clear the list to make LargeData objects eligible for garbage collection
        largeDataList.clear();

        // Print memory usage after clearing the list
        printMemoryUsage("After clearing the list");

        // Suggest garbage collection
        System.out.println("Requesting garbage collection...");
        System.gc();

        // Give garbage collector some time to run
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Print memory usage after garbage collection
        printMemoryUsage("After garbage collection");

        System.out.println("End of program.");
    }
}

