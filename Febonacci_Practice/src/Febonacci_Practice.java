import java.util.concurrent.*;

class Febornacci extends RecursiveTask <Long> {
    private long n;
    
    long F(long n) {
        if (n <= 1)
            return n;
        return F(n -1) + F(n -2);
    }
    public Febornacci(long n){
        this.n = n;
    }
    
    @Override
    protected Long compute() {
        long ans = 0;
        
        if (n <= 20) {
            ans = F(n);
        }
        else {
            Febornacci p1 = new Febornacci(n -2);
            Febornacci p2 = new Febornacci(n -3);
            Febornacci p3 = new Febornacci(n -4);
            
            p1.fork();
            p2.fork();
            p3.fork();
            ans = p1.join() + (p2.join() << 1) + p3.join();
        }
        return ans;
    }
}
public class Febonacci_Practice {
    public static long F(long n) {
        if (n <= 1)
            return n;
        return F(n -1) + F(n -2);
    }
    public static void main(String[] args) {
        long start_time = System.currentTimeMillis();
        long input = 60;
        long ans;
        /*
        ans = F(input);
        
        System.out.println("F(n) = " + ans);
        System.out.println("Process Timde : " + (System.currentTimeMillis() - start_time) + " ms");
        */
        start_time = System.currentTimeMillis();
        
        int poolSize = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(poolSize);

        Febornacci p1 = new Febornacci(input -2);
        Febornacci p2 = new Febornacci(input -3);
        Febornacci p3 = new Febornacci(input -4);
        
        pool.invoke(p1);
        pool.invoke(p2);
        pool.invoke(p3);
        
        ans = p1.join() + (p2.join() << 1)  + p3.join();
        
        System.out.println("F(n) = " + ans);
        System.out.println("Process Timde : " + (System.currentTimeMillis() - start_time) + " ms");
    }
    
}
