//CSE 373 A
//Homework 4
//Fangzheng Sun
//05/13/2015
package shake_n_bacon;

import java.io.IOException;

import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.*;

//The test class, using the file the-new-atlantis as an example, times the
//SC and OA hashtable input process. Unit is ms.
public class time{
   private static final int NUM_TESTS = 50;
   private static final int NUM_WARMUP = 10;
   
   public static void main(String[] args) {
      Hasher h =  new StringHasher();
		StringComparator comp = new StringComparator();
      DataCounter counter1 = new HashTable_SC(comp, h);
      DataCounter counter2 = new HashTable_OA(comp, h);
      System.out.print("for hashtable_SC, average running time is : ");
      double totalTime = 0;
      countWords("hamlet.txt", counter1);
      for(int i=0; i<NUM_TESTS; i++) {
         countWords("hamlet.txt", counter1);
         long startTime = System.currentTimeMillis();
         //In the for loop, change the code to make different experiments. 
         for(int j = 0; j < 100; j++){
            counter2.incCount("the");
            counter2.incCount("" + i);
         }
         long endTime = System.currentTimeMillis();
         if(NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to encounter JVM warmup
            totalTime += (endTime - startTime);
         }
      }
      System.out.println(totalTime / (NUM_TESTS-NUM_WARMUP) + " ms"); // Return average runtime.
      System.out.print("for hashtable_OA, average running time is : ");
      totalTime = 0;
      for(int i=0; i<NUM_TESTS; i++) {
         countWords("hamlet.txt", counter2);
         long startTime = System.currentTimeMillis();
         //In the for loop, change the code to make different experiments.
         for(int j = 0; j < 100; j++){
            counter2.incCount("the");
            counter2.incCount("" + i);
         }
         long endTime = System.currentTimeMillis();
         if(NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to encounter JVM warmup
            totalTime += (endTime - startTime);
         }
      }
      System.out.println(totalTime / (NUM_TESTS-NUM_WARMUP) + " ms"); // Return average runtime.
   }
   
   //The private method countWords counts the appearence of the words in the 
   //given file using the given counter.
	private static void countWords(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
	}
}