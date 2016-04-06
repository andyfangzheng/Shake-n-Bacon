package shake_n_bacon;

import java.io.IOException;

import providedCode.*;

/**
 * @author <Fangzheng Sun>
 * @UWNetID <fs23>
 * @studentID <1239942>
 * @email <fs23@uw.edu>
 * 
 * The class correlator calculates the variance of frequencies of
 * the given words in two files. 
 */
public class Correlator {

	public static void main(String[] args){
		if (args.length != 3) {
			usage();
		}
      
		String firstArg = args[0].toLowerCase();
		DataCounter counter1 = null;
      DataCounter counter2 = null;
		if (firstArg.equals("-s")) {
			counter1 = new HashTable_SC(new StringComparator(),
					new StringHasher());
         counter2 = new HashTable_SC(new StringComparator(),
					new StringHasher());
		} else if (firstArg.equals("-o")) {
			counter1 = new HashTable_OA(new StringComparator(),
					new StringHasher());
         counter2 = new HashTable_OA(new StringComparator(),
					new StringHasher());
		} else {
			usage();
		}
      double variance = 0.0;
		int size1 = countWords(args[1], counter1);
		int size2 = countWords(args[2], counter2);
		SimpleIterator itr = counter1.getIterator();
      while(itr.hasNext()){
			DataCount word = itr.next();
			int count1 = counter1.getCount(word.data);
         double freq1 = (double)count1/size1;
			int count2 = counter2.getCount(word.data);
			double freq2 = (double)count2/size2;
			if(freq1 < .01 && freq1 > .0001 && freq2 < .01 && freq2 > .0001){
				variance += (freq2-freq1)*(freq2-freq1);
         }
		}
      System.out.print("variance is ");
		System.out.println(variance);
	}
   
   /*
	 * Print error message and exit
	 */
	private static void usage() {
		System.err
				.println("Usage: [-s | -o] <filename of document to analyze>");
		System.err.println("-s for hashtable with separate chaining");
		System.err.println("-o for hashtable with open addressing");
		System.exit(1);
	}
	
	//This method reads the file and puts the string elements into the counter. It
	//returns the total amount of words in the file. For invalid file, the method
   //will catch the error
	private static int countWords(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			int totalCounts = 0;
			while (word != null) {
				totalCounts++;
				counter.incCount(word);
				word = reader.nextWord();
			}
			return totalCounts;
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
         return 0;
		}
	}
}
