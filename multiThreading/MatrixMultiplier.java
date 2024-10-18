public class MatrixMultiplier extends Thread {
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int row;  // The row of C that this thread will calculate

    public MatrixMultiplier(int[][] A, int[][] B, int[][] C, int row) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.row = row;
    }

    @Override
    public void run() {
        int colsB = B[0].length;
        int colsA = A[0].length;
        // Calculate one row of the result matrix C
        for (int j = 0; j < colsB; j++) {
            C[row][j] = 0;
            for (int k = 0; k < colsA; k++) {
                C[row][j] += A[row][k] * B[k][j];
            }
        }
    }

    public static void main(String[] args) {
        // Sample Matrices
        int[][] A = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] B = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        
        // Result matrix
        int[][] C = new int[A.length][B[0].length];
        
        // Create and start threads for each row of matrix C
        int numThreads = A.length;
        Thread[] threads = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new MatrixMultiplier(A, B, C, i);
            threads[i].start();
        }
        
        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Print result matrix
        System.out.println("Result matrix C:");
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}
