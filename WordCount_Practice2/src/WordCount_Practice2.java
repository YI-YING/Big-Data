import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ForkJoinPool;

class DivideTask extends RecursiveAction {
    String [] word_list;
    int start, end, threshold;
    
    public DivideTask(String [] word_list, int start, int end, int threshold)  {
        this.word_list = word_list;
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }
    public void mergeArrays(int mid)  {
        String [] mergedArray = new String[end - start];
        int i = start, j = mid, k = 0;
        while((i < mid) && (j < end))  {
            if (word_list[i].compareTo(word_list[j]) < 0)
                mergedArray[k] = word_list[i++];
            else     mergedArray[k] = word_list[j++];
            k++;
        }
        if (i == mid)   {
            for (int a = j; a < end; a++)
                mergedArray[k++] = word_list[a];
        }
        else      {
            for (int a = i; a < mid; a++)
                mergedArray[k++] = word_list[a];
        }
        for(int m = start; m < end; m++)
            word_list[m] = mergedArray[m - start];
    }
    
    @Override
    protected void compute()  {
        if (end - start <= threshold)
            Arrays.sort(word_list, start, end);
        else   {
            int mid = start + (end-start) / 2;
            invokeAll(new DivideTask(word_list, start, mid, threshold),
                    new DivideTask(word_list, mid, end, threshold));

            mergeArrays(mid);
        }
    }
}
public class WordCount_Practice2 {
    static ArrayList txt = new ArrayList();
    static String[] word_list;
    public static void mergeArrays(int start, int end, int mid) {
        String [] mergedArray = new String[end-start];

        int i = start, j = mid, k = 0;

        while((i < mid) && (j < end)) {
            if (word_list[i].compareTo(word_list[j]) < 0) 
                mergedArray[k] = word_list[i++];
            else
                mergedArray[k] = word_list[j++];

            k++;
        }

        if (i == mid) {
            for (int a = j; a < end; a++)
                mergedArray[k++] = word_list[a];    
        } 
        else {
            for (int a = i; a < mid; a++)
                mergedArray[k++] = word_list[a];
        }

        for(int m = start; m < end; m++)
            word_list[m] = mergedArray[m-start];
    }    
    public static void merge_sort_s(int start, int end)  {
        if (end - start > 1)   {
            int mid = start + (end-start) / 2;
            merge_sort_s(start, mid);
            merge_sort_s(mid, end);

            mergeArrays(start, end, mid);
        }
    }    
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	BufferedReader br = new BufferedReader(
    		new FileReader("D:/Github/Big-Data/WordCount_Practice2/src/big5.txt"));
    		
    	String temp;
    	while((temp = br.readLine()) != null)
    	{
    		String [] data = temp.split(" ");
    		
    		for(int i = 0; i < data.length; i++) {
                    temp = data[i].trim();
                    if (temp.length() != 0)
                        txt.add(data[i]);
                }
    	}
    		
    	br.close();
    	
    	System.out.println(txt.size());
        
        word_list = new String[txt.size()];
     
         long Each_Time = 0, Total_Time = 0;
     
         for(int z = 0; z < 1; z++) {
            txt.toArray(word_list);

            long start_time = System.currentTimeMillis();

            //Collections.sort(txt);     
            //Arrays.sort(word_list);
            //Arrays.parallelSort(word_list);

            /*
            int start = 0;
            int end = word_list.length;

            merge_sort_s(start, end);
            */


            int threshold = word_list.length / (Runtime.getRuntime().availableProcessors() * 10);

            DivideTask rootTask = new DivideTask(word_list, 0, word_list.length, threshold);
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(rootTask);
      
      
            Each_Time = System.currentTimeMillis() - start_time;
            Total_Time += Each_Time;
      
            System.out.println("Process Timde : " + Each_Time + " ms");
        }
     
        System.out.println("\nAverage Process Timde : " + Total_Time / 10.0 + " ms");

        BufferedWriter bw = new BufferedWriter(new FileWriter("D:/Github/Big-Data/WordCount_Practice2/src/result1.txt"));

        int length = word_list.length;
        
        String w = word_list[0];
        int count = 1;
        
        for (int i = 1; i < length; i++) {
            if (w.equals(word_list[i]))
                count++;
            else {
                bw.write(w + " : " + count);
                bw.newLine();
                w = word_list[i];
                count = 1;
            }
        }
        bw.write(w + " : " + count + "\n");

        bw.close();     
    }
    
}
