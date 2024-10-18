public class Singleton {
    // The volatile keyword ensures visibility of changes across threads
    private static volatile Singleton instance = null;

    // Private constructor to prevent instantiation from other classes
    private Singleton() {
        System.out.println("Singleton instance created");
    }

    // Double-checked locking implementation of the getInstance() method
    public static Singleton getInstance() {
        if (instance == null) {  // First check (without locking)
            synchronized (Singleton.class) {
                if (instance == null) {  // Second check (with locking)
                    instance = new Singleton();  // Create the singleton instance
                }
            }
        }
        return instance;
    }

    // Example method in the singleton class
    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }

    public static void main(String[] args) {
        // Example usage of Singleton in a multi-threaded context
        Thread t1 = new Thread(() -> {
            Singleton singleton1 = Singleton.getInstance();
            singleton1.showMessage();
        });

        Thread t2 = new Thread(() -> {
            Singleton singleton2 = Singleton.getInstance();
            singleton2.showMessage();
        });

        t1.start();
        t2.start();
    }
}

