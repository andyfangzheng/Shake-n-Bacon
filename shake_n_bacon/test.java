//CSE 373 A
//Homework 4
//Fangzheng Sun
//05/13/2015
package shake_n_bacon;

import java.io.IOException;

import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.*;

//The test class, using the file the-new-atlantis as an example, tests whether the
//SC and OA hashtable works well. It compares the data provided by running WordCount.java
//and the data from the two hashtables.
public class test{
   
   public static void main(String[] args) {
      boolean fail = false;
      Hasher h =  new StringHasher();
		StringComparator comp = new StringComparator();
      //Test SC
      System.out.println("SC test:");
      DataCounter counter = new HashTable_SC(comp, h);
      //Test OA (uncomment the following 2 lines and comment the above 2 lines)
      //System.out.println("OA test:");
      //DataCounter counter = new HashTable_OA(comp, h);
      
      //This part tests getCount.
      System.out.println("getCount tests:");
      //From WordCount.java, we get that "the" appear for 886 times, "of" appear
      //for 882 times, "perhaps" appear for 1 time. And "CSE373" and 
      //"javaprogramming" appear for 0 time since they did not exist during
      //Shakespare's time. If the results of the test are exaclty the same
      //as described above, the test is successful.
      countWords("the-new-atlantis.txt", counter);
      System.out.println("for the following words, the appearing time is:");
      int the = counter.getCount("the");
      System.out.println("the: " + the);
      int of = counter.getCount("of");
      System.out.println("of: " + of);
      int perhaps = counter.getCount("perhaps");
      System.out.println("perhaps: " + perhaps);
      int CSE373 = counter.getCount("CSE373");
      System.out.println("CSE373: " + CSE373);
      int javaprogramming = counter.getCount("javaprogramming");
      System.out.println("javaprogramming: " + javaprogramming);
      if(the != 886 || of != 882 || perhaps != 1){
         fail = true;
         System.out.println("Unsuccessful test");
      }else if(CSE373 != 0 || javaprogramming != 0){
         fail = true;
         System.out.println("Unsuccessful test because this is an article written by " 
                            + "some CSE instructors");
      }else{
         System.out.println("getCount tests successful!");
      }
      //This part tests size and incCount;
      System.out.println("getCount & size tests:");
      int oldSize = counter.getSize();
      counter.incCount("the");
      counter.incCount("the");
      counter.incCount("the");
      counter.incCount("the");
      counter.incCount("the");
      counter.incCount("perhaps");
      counter.incCount("perhaps");
      counter.incCount("CSE373");
      int newSize = counter.getSize();
      System.out.println("after add 7 existing and 1 new items, size should increase by 1");
      System.out.println("old size = " + oldSize + ", new size = " + newSize);
      if(counter.getCount("the") != 891 || counter.getCount("perhaps") != 3
         || counter.getCount("CSE373") != 1 || newSize - oldSize != 1){
         fail = true;
      }else{
         System.out.println("getCount & size tests successful!");
      }
      if(!fail){
         System.out.println("all tests successful!");
      }
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

