import java.util.*;
import java.io.*;

public class WordCoumt_Practice {
    static HashMap <String, Integer> hmWordCount = new HashMap();

    static String CutSigned (String s) {
        String sub = s.toLowerCase();
        int iBegin, iEnd;
        char cBegin, cEnd;
        for (iBegin = 0; iBegin < sub.length(); iBegin++) {
            cBegin = sub.charAt(iBegin);
            if (('a' <= cBegin && cBegin <= 'z') ||
                    ('0' <= cBegin && cBegin <= '9'))
                break;
        }

        for (iEnd = sub.length() -1; iEnd >= 0; iEnd--) {
            cEnd = sub.charAt(iEnd);
            if (('a' <= cEnd && cEnd <= 'z') ||
                    ('0' <= cEnd && cEnd <= '9'))
                break;
        }
        sub = (iBegin > sub.length() || iEnd < 0) ? null : s.substring(iBegin, iEnd +1);
        
        return sub;
    }

    static String ChangeSigned (String s) {
        String sOldString = s.toLowerCase();
        char ch[] = sOldString.toCharArray();
        
         for (int i = 0; i < sOldString.length(); i++) 
         {
               ch[i] = sOldString.charAt(i);
               if(('a' <= ch[i] && ch[i] <= 'z') ||
                    ('0' <= ch[i] && ch[i] <= '9'))
                   ;
               else
                   ch[i] = ' ';
         }
         
         sOldString = String.valueOf(ch);
         
        return sOldString;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Github/Big-Data/WordCoumt_Practice/src/big.txt"));
        
        String sTemp;
        while ((sTemp = br.readLine()) != null) {
            sTemp = sTemp.trim();
            sTemp = ChangeSigned(sTemp);
            String [] sData = sTemp.split(" ");
            
            for (int i = 0; i < sData.length; i++) {
                if ((CutSigned(sData[i]) != null)) {
                    int k = (hmWordCount.get(sData[i]) != null ) ? hmWordCount.get(sData[i]) : 0;
                    hmWordCount.put(sData[i], ++k);
                }
            }
        }
        
        System.out.println(hmWordCount);
    }
    
}
