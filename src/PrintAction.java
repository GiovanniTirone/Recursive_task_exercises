import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PrintAction extends RecursiveAction {

    int [] nums;
    int start;
    int end;

    public PrintAction (int[] nums, int start, int end){
        this.nums = nums;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if(end-start == 0){
            System.out.println(nums[start]);
        }
        else{
            int middle = Math.floorDiv(end+start,2);
            System.out.println("Start: " + start + " Middle: " + middle + " End: " + end);
            PrintAction action1 = new PrintAction(nums,start,middle);
            PrintAction action2 = new PrintAction(nums,middle+1,end);
            action1.fork();
            action2.fork();
            action1.join();
            action2.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int nums [] = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        PrintAction printAction = new PrintAction(nums,0,nums.length-1);
        pool.invoke(printAction);
    }
}
