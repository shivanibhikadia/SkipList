
import java.util.*;

public class SkipListTest {

    private static class StopWatch {
        private long startTime;
        private long stopTime;
        private long cumulativeTime;
        private boolean started;

        public void start() {
            if (started) {
                return;
            }
            startTime = System.currentTimeMillis();
            started = true;
        }

        public void stop() {
            if (!started) {
                return;
            }
            stopTime = System.currentTimeMillis();
            cumulativeTime += (stopTime  - startTime);
            startTime = stopTime = 0;
            started = false;
        }

        public void reset() {
            started = false;
            cumulativeTime = 0;
        }

        public long cumulativeTime() {
            return cumulativeTime;
        }

    }

    public static int [] INPUT_SIZES = new int [] {1000,10000,30000,50000,100000};
    public static int NUM_TRIALS = 100;
    public static int INSERT_TIME_INDEX = 0;
    public static int DELETE_TIME_INDEX = 1;
    public static int FIND_TIME_INDEX = 2;
    public static int CLOSESTKEYAFTER_TIME_INDEX = 3;

    private static int [] keys = new int[INPUT_SIZES[INPUT_SIZES.length-1]+NUM_TRIALS];
    
    private static double [][] cumTimes = new double[5][INPUT_SIZES.length];

  
    @SuppressWarnings("unused")
	private static void populate(skiplistMain dict,int nextInputSize) {
    	
        Integer s = dict.size;
        
        if(s==null) {
        	s=0;
        }
      
        while(s<nextInputSize) {
        	dict.insert(keys[s], keys[s]);
        	s++;
        }
        
    }

    // Pick randomly a sample of keys from sortedKeyArr
    private static List<Integer> keysForSuccessfulFind(int [] sortedKeyArr, int sampleSize) {
        List<Integer> lst = new ArrayList<>();
        Random random = new Random();
        int i=0;
        while (i < sampleSize) {
            int idx = random.nextInt(sortedKeyArr.length);
            int key = sortedKeyArr[idx];
            if (lst.contains(key)) {
                continue;
            }
            lst.add(key);
            i++;
        }
        return lst;
    }

    // Pick randomly a sample of keys that fall in the interval between 2 keys from sortedKeyAr
    private static List<Integer> keysForUnsuccessfulFind(int [] sortedKeyArr, int sampleSize) {
        List<Integer> temp = new ArrayList<>();
        
        Random random = new Random();
        int i=0;
        
        while(i < sampleSize) {
        	
        	int idx = random.nextInt(sortedKeyArr.length);
        	int key = sortedKeyArr[idx]-1;
        	if(temp.contains(key)) {
        		continue;
        	}
        	temp.add(key);
        	i++;
        }
        
        return temp;
    }


    public static void dumpPerformanceStats(double[][] cumTimes) {
    	System.out.print("            Insert     delete      find       closestKey ");
      
        System.out.println();
        	for(int i=0;i<INPUT_SIZES.length;i++) {
        		System.out.print(INPUT_SIZES[i]+"        ");
        	   for(int j=0;j<4;j++) {
        		   System.out.print(cumTimes[j][i]+"       ");
        	   }
        	   System.out.println();
           }
        }
    	

    	
        
    

    public static void measurePerformance(skiplistMain dict) {
        List<Integer> lst = new ArrayList<>();
        
        for(int i=2;i<=2*keys.length;i=i+2) {
        	lst.add(i);
        }
        
        Collections.shuffle(lst);
        
        for (int i=0; i < keys.length; i++) {
            keys[i] = lst.get(i);
        }
        
        StopWatch watch = new StopWatch();

        
        int[] temparr = new int[INPUT_SIZES[INPUT_SIZES.length-1]+NUM_TRIALS];
        
      
        int x=0;
        
        int index;
        
        for (int i=0; i < INPUT_SIZES.length; i++) {
        	
        	populate(dict,INPUT_SIZES[i]);
        	
        	if(i!=0) {
        		x=INPUT_SIZES[i-1];
        	}
        
        	for(int ind=x;ind<INPUT_SIZES[i];ind++) {
        		temparr[ind]=keys[ind];
        	}
        	
        	Arrays.sort(temparr);
        	
        	List<Integer> successKeys = keysForSuccessfulFind(temparr,NUM_TRIALS);
        	List<Integer> unsuccessKeys = keysForUnsuccessfulFind(temparr,NUM_TRIALS);
        	
        	watch.start();
        	for(index=0;index<successKeys.size();index++) {
        		dict.search(successKeys.get(index));
        	}
        	watch.stop();
        	
        	long finds = watch.cumulativeTime();
        	watch.reset();
        	
        	watch.start();
        	for(index=0;index<unsuccessKeys.size();index++) {
        		dict.search(unsuccessKeys.get(index));
        	}
        	watch.stop();
        	long unfinds = watch.cumulativeTime();
        	watch.reset();
        	
        	cumTimes[FIND_TIME_INDEX][i] = (finds + unfinds)/2;
        	
        	watch.start();
        	for(index=INPUT_SIZES[i];index<INPUT_SIZES[i]+NUM_TRIALS;index++) {
        		dict.delete(keys[index]);
        	}
        	watch.stop();
        	long dele = watch.cumulativeTime();
        	watch.reset();
        	
        	cumTimes[DELETE_TIME_INDEX][i] = dele;
        	
        	watch.start();
        	for(index=INPUT_SIZES[i];index<INPUT_SIZES[i]+NUM_TRIALS;index++) {
        		dict.insert(keys[index], keys[index]);
        	}
        	watch.stop();
        	long insert = watch.cumulativeTime();
        	watch.reset();
        	
        	cumTimes[INSERT_TIME_INDEX][i] = insert;
        	
        	watch.start();
        	for(index=0;index<successKeys.size();index++) {
        		dict.closest(successKeys.get(index));
        	}
        	watch.stop();
        	long closest = watch.cumulativeTime();
        	watch.reset();
        	
        	watch.start();
        	for(index=0;index<unsuccessKeys.size();index++) {
        		dict.closest(unsuccessKeys.get(index));
        	}
        	watch.stop();
        	long closestf = watch.cumulativeTime();
        	watch.reset();
        	
        	cumTimes[CLOSESTKEYAFTER_TIME_INDEX][i] = (closest + closestf)/2;
        	
        	
        	
        }
        dumpPerformanceStats(cumTimes);
    }

    public static void main(String [] args)  {
       
    	skiplistMain dict = new skiplistMain();
        measurePerformance(dict);
    }

}
