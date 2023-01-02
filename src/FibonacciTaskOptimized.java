import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciTaskOptimized extends RecursiveTask<Integer> {

    private int n;

    public FibonacciTaskOptimized(int n){
        this.n = n;
    }

    @Override
    protected Integer compute() {

        if(n == 0) {
            System.out.println(0);
            return 0;
        }
        if(n == 1) {
            System.out.println(1);
            return 1;
        }


        FibonacciTaskOptimized fib_n_2 = new FibonacciTaskOptimized(n-2);

        fib_n_2.fork();

        n=n-1;

        int f_n_1 = compute();
        int f_n_2 = fib_n_2.join();

        return f_n_1 + f_n_2;

    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTaskOptimized task = new FibonacciTaskOptimized(13);
        int result = pool.invoke(task);
        System.out.println(result);
    }
}
