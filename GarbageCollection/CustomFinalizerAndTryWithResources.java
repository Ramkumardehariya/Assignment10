public class CustomFinalizerAndTryWithResources {

    // Resource class that implements AutoCloseable for try-with-resources
    static class ResourceWithAutoCloseable implements AutoCloseable {
        // Constructor to simulate resource allocation
        public ResourceWithAutoCloseable() {
            System.out.println("Resource allocated using AutoCloseable.");
        }

        // Cleanup method when the resource is closed
        @Override
        public void close() {
            System.out.println("Resource cleaned up using try-with-resources.");
        }
    }

    public static void main(String[] args) {
        // Try-with-resources example
        System.out.println("Using ResourceWithAutoCloseable in try-with-resources:");
        try (ResourceWithAutoCloseable resource = new ResourceWithAutoCloseable()) {
            System.out.println("Inside try-with-resources block.");
        } // The close() method is automatically called at the end of the block

        System.out.println("End of program.");
    }
}
