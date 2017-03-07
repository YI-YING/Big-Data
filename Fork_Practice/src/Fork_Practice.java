import java.util.concurrent.*;

class Prime extends RecursiveTask <Integer> {
    private int begin;
    private int end;
    
    public Prime(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
    
    @Override
    protected Integer compute() {
        int count = 0, i, j;
        
        if ((end - begin) < 500000) {
            for (i = begin; i <= end; i++) {
                for (j = 2; j <= (int)Math.sqrt(i); j++)
                    if (i % j == 0)
                        break;
                
                if (j > (int)Math.sqrt(i))
                    count++;
            }
        }
        else {
            int mid = begin + (end - begin) / 2;
            
            Prime p1 = new Prime(begin, mid);
            Prime p2 = new Prime(mid +1, end);
            
            p1.fork();
            p2.fork();
            count = p2.join() + p1.join();
        }
        
        return count;
    }
}

public class Fork_Practice {
    public static void main(String[] args) {
        long start_time = System.currentTimeMillis();
        
        int count = 0, i, j;
        for (i = 2; i <= 80000000; i++) {
            for (j = 2; j <= (int)Math.sqrt(i); j++)
                if (i % j == 0)
                    break;
            if (j > (int)Math.sqrt(i))
                count++;
        }
        
        System.out.println("Num : " + count);
        System.out.println("Process Timde : " + (System.currentTimeMillis() - start_time) + " ms\n");
        
        start_time = System.currentTimeMillis();
        
        int poolSize = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(poolSize);
        
        Prime p1 = new Prime(2, 20000000);
        Prime p2 = new Prime(20000001, 40000000);
        Prime p3 = new Prime(40000001, 60000000);
        Prime p4 = new Prime(60000001, 80000000);
        
        pool.invoke(p1);
        pool.invoke(p2);
        pool.invoke(p3);
        pool.invoke(p4);
        
        count = p1.join() + p2.join() + p3.join() + p4.join();
        
        System.out.println("Num : " + count);
        System.out.println("Process Timde : " + (System.currentTimeMillis() - start_time) + " ms\n");

    }
    
}
