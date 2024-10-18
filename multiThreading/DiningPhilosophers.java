import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    // Number of philosophers (and chopsticks)
    private static final int NUM_PHILOSOPHERS = 5;
    
    // Semaphores representing chopsticks (one for each philosopher)
    private Semaphore[] chopsticks = new Semaphore[NUM_PHILOSOPHERS];

    public DiningPhilosophers() {
        // Initialize semaphores for each chopstick
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            chopsticks[i] = new Semaphore(1);  // Each chopstick can only be held by one philosopher at a time
        }
    }

    // Philosopher class (each philosopher is a thread)
    class Philosopher extends Thread {
        private int id;
        private int leftChopstick;
        private int rightChopstick;

        public Philosopher(int id) {
            this.id = id;
            this.leftChopstick = id;  // Left chopstick is the philosopher's id
            this.rightChopstick = (id + 1) % NUM_PHILOSOPHERS;  // Right chopstick is the next chopstick
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    pickUpChopsticks();
                    eat();
                    putDownChopsticks();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking...");
            Thread.sleep((int) (Math.random() * 1000));  // Simulate thinking time
        }

        private void pickUpChopsticks() throws InterruptedException {
            // To avoid deadlock, philosophers pick up the chopstick with the lower id first
            if (id % 2 == 0) {
                chopsticks[leftChopstick].acquire();  // Pick up left chopstick
                System.out.println("Philosopher " + id + " picked up left chopstick.");
                chopsticks[rightChopstick].acquire();  // Pick up right chopstick
                System.out.println("Philosopher " + id + " picked up right chopstick.");
            } else {
                chopsticks[rightChopstick].acquire();  // Pick up right chopstick
                System.out.println("Philosopher " + id + " picked up right chopstick.");
                chopsticks[leftChopstick].acquire();  // Pick up left chopstick
                System.out.println("Philosopher " + id + " picked up left chopstick.");
            }
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating...");
            Thread.sleep((int) (Math.random() * 1000));  // Simulate eating time
        }

        private void putDownChopsticks() {
            chopsticks[leftChopstick].release();  // Put down left chopstick
            System.out.println("Philosopher " + id + " put down left chopstick.");
            chopsticks[rightChopstick].release();  // Put down right chopstick
            System.out.println("Philosopher " + id + " put down right chopstick.");
        }
    }

    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();

        // Create and start philosopher threads
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = diningPhilosophers.new Philosopher(i);
            philosophers[i].start();
        }
    }
}

