import java.lang.ref.Cleaner;

public class GCExample {
    // Cleaner instance to register cleanup tasks
    private static final Cleaner cleaner = Cleaner.create();

    // Inner static class to define the cleanup task
    private static class CleanupTask implements Runnable {
        @Override
        public void run() {
            System.out.println("GCExample object is being cleaned up.");
        }
    }

    // Constructor
    public GCExample() {
        System.out.println("GCExample object created.");
        // Registering the cleanup task with the Cleaner
        cleaner.register(this, new CleanupTask());
    }

    public static void main(String[] args) {
        // Create multiple instances of GCExample
        GCExample obj1 = new GCExample();
        GCExample obj2 = new GCExample();
        GCExample obj3 = new GCExample();

        // Nullify references to make them eligible for garbage collection
        obj1 = null;
        obj2 = null;
        obj3 = null;

        // Request garbage collection
        System.out.println("Requesting garbage collection...");
        System.gc(); // Suggest to the JVM to run the garbage collector

        // Sleep for a short time to allow garbage collection to occur
        try {
            Thread.sleep(1000); // Sleep for 1 second to wait for cleanup
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("End of main method.");
    }
}
