import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Integer> {

    private int n;

    public FibonacciTask(int n){
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

        FibonacciTask fib_n_1 = new FibonacciTask(n-1);
        FibonacciTask fib_n_2 = new FibonacciTask(n-2);

        fib_n_1.fork();
        fib_n_2.fork();

        int f_n_1 = fib_n_1.join();
        int f_n_2 = fib_n_2.join();

        return f_n_1 + f_n_2;

    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask task = new FibonacciTask(7);
        int result = pool.invoke(task);
        System.out.println(result);
    }
}
