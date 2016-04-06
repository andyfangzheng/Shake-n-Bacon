package shake_n_bacon;

import providedCode.*;

/**
 * @author <Fangzheng Sun>
 * @UWNetID <fs23>
 * @studentID <1239942>
 * @email <fs23@uw.edu>
 * 
 * The class HashTable_OA builds a hashertable to store the data using the
 * method of open addressing. More specifically , it uses quadratic probing
 * It provides several method to keep track of the state of the hasher table.
 */
public class HashTable_OA extends DataCounter {
   private DataCount[] hasherTable; //the hashertable
   private int index; //index of the PRIME array
   private int size; //the number of datacount in the table
	private Comparator<String> comp; //the comparator
	private Hasher hasher; //the hasherier
   //primes from 10 to 200000 with one no less than twice the previous one. 
   private static final int[] PRIME = 
		{11, 23, 47, 97, 199, 401, 809, 1667, 3307, 6833, 16993, 34403, 68891, 128591, 199999};
      
   //The constructor builds the initial with size 0 and size 11.
	public HashTable_OA(Comparator<String> c, Hasher h) {
		hasherTable = new DataCount[PRIME[index]];
      index = 0;
		size = 0;
      hasher = h;
      comp = c;
	}

	//The method incCount takes an String as parameter and check whether the
   //word is in the hasher tables. If so, increase the count of that word by 1.
   //If not, use quatratic probing to locate the word to a new place. If the 
   //load factor is larger than 1, resize the table to the next prime in PRIME.
	public void incCount(String data) {
		if((double)size / PRIME[index] > 0.7){ //resize if load factor > 0.7
         resize();
      }
      int position = hasher.hash(data) % PRIME[index];
      QProbing(data, position, 0); //Using quatratic probing        
	}
   
   //The private method resizeis used to enlarge the size of the table to the next
   //prime number in the prime array when the load facotr is larger than 1.
   private void resize(){
      index++;
		DataCount[] temp = new DataCount[PRIME[index]];
		SimpleIterator itr = getIterator();
		while (itr.hasNext()){
			DataCount nextData = itr.next();
			int position = hasher.hash(nextData.data) % PRIME[index];
			if (temp[position] == null) {
				temp[position] = nextData;
			}else{
				int i = 1;
				while (temp[(position + i * i) % temp.length] != null) {
					i++;
				}
				temp[(position + i * i) % PRIME[index]] = nextData;
			}
		}
      hasherTable = temp;
   }
   
   //This process is quadratic probing process and locate the new item
   //to the proper place in the hashertable according to quatratic probing.
   private void QProbing(String data, int position, int i){
      int newPosition = (position + i * i) % hasherTable.length;
		if(hasherTable[newPosition] == null){
			hasherTable[newPosition] = new DataCount(data, 1);
			size++;
      }else if(comp.compare(data, hasherTable[newPosition].data) == 0){
         hasherTable[newPosition].count++;
      }else{
         QProbing(data, position, i + 1);
      }
	}
   
	//The method getSize returns the number of elements in the hasher table.
	public int getSize() {
		return size;
	}

	//return the appearance of the given word. 
	public int getCount(String data) {
	   int position = hasher.hash(data) % PRIME[index];
      int i = 0;
      while(hasherTable[position + i * i] != null){
         if(comp.compare(data, hasherTable[position + i * i].data) == 0){
            return hasherTable[position + i * i].count;
         }else{
            i++;
         }
      }
      return 0;           
	}

	//This class returns an iterator of the open addressing hashertable.
	public SimpleIterator getIterator(){
		return new OAIterator();
	}
   
   //This is an inner class to build a new iterator.
   private class OAIterator implements SimpleIterator{
      private int itrIndex;
      private int itrSize;
      
      //The constructor constructsthe initial state of the iterator.
      public OAIterator(){
         itrIndex = -1;
         itrSize = 0;
      }
      
      //This method returns true if there exists next data in the iterator.
      public boolean hasNext(){ 
			return itrSize < size;
      }
      
      //The method next return the next Datacount value if exists. It returns
      //null if no next element. 
      public DataCount next(){
         if(!hasNext()){
			   return null;
			}else{
            itrIndex++;
            itrSize++;
			   while (hasherTable[itrIndex] == null) {
				   itrIndex++;
            }
			   return hasherTable[itrIndex];
         }
      }
   } 
}