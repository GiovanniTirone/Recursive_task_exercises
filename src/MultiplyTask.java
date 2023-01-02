import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MultiplyTask extends RecursiveTask<Double> {

    private double num;

    public MultiplyTask (double num){
        this.num = num;
    }


    @Override
    protected Double compute() {
        if(num<=100){
            System.out.println("Small num... " + num);
            return num*2;
        }
        else{
            System.out.println("Split the number: " +num);

            MultiplyTask task1 = new MultiplyTask(num/2);
            MultiplyTask task2 = new MultiplyTask(num/2);

            task1.fork();
            task2.fork();

            double partialSolution = 0;

            partialSolution += task1.join();
            partialSolution += task2.join();

            return partialSolution;
        }
    }

    public static void main(String[] args) {
        MultiplyTask task = new MultiplyTask(900);
        ForkJoinPool pool = new ForkJoinPool();
        double result = pool.invoke(task);
        System.out.println(result);
    }
}