import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FindMaxTask extends RecursiveTask<Long> {

    long[] nums ;
    int lowIndex;
    int maxIndex;

    public FindMaxTask (long[] nums, int lowIndex, int maxIndex) {
        this.nums = nums;
        this.lowIndex = lowIndex;
        this.maxIndex = maxIndex;
    }

    @Override
    protected Long compute() {
        if(maxIndex-lowIndex<5000){
            return findMaxSequential();
        }
        int middle = (lowIndex+maxIndex)/2;
        this.maxIndex = middle;
        ForkJoinTask<Long> secondMaxTask = new FindMaxTask(nums,middle+1,maxIndex).fork();
        long firstMax = this.compute();
        long secondMax = secondMaxTask.join();
        return Math.max(firstMax,secondMax);
    }

    public Long findMaxSequential () {
        long max = nums[lowIndex];
        for(int i=lowIndex+1; i<maxIndex; i++){
            if(nums[i] > max) max=nums[i];
        }
        return max;
    }

    public static void main(String[] args) {
        long [] nums = createArr(300000000);
        ForkJoinPool pool = new ForkJoinPool();
        FindMaxTask task = new FindMaxTask(nums,0,nums.length);

        long startTime = System.currentTimeMillis();

        System.out.println("Max: " + task.findMaxSequential());
        System.out.println("Time sequential: " + (System.currentTimeMillis()-startTime));


        startTime = System.currentTimeMillis();


        System.out.println(pool.invoke(task));
        System.out.println("Time parallel: " + (System.currentTimeMillis()-startTime));

    }

    public static long[] createArr (int n) {
        Random random = new Random();
        long arr[] = new long[n];
        for(int i=0; i<n; i++)
            arr[i] = random.nextInt(1000);
        return arr;
    }
}
