package shake_n_bacon;

import providedCode.*;

/**
 * @author <Fangzheng Sun>
 * @UWNetID <fs23>
 * @studentID <1239942>
 * @email <fs23@uw.edu>
 * 
 * The class HashTable_SC builds a hashhashTable to store the data using the
 * method of separate chaining. It provides several method to keep track 
 * of the state of the hash hashTable.
 */
public class HashTable_SC extends DataCounter {
   private HashNode[] hashTable; //the hashhashTable of hashnode array
   private int index; //index of the PRIME array
   private int size; //the number of datacount in the hashTable
	private Comparator<String> comp; //the comparator
	private Hasher hasher; //the hashier
   //primes from 10 to 200000 with one no less than twice the previous one. 
   private static final int[] PRIME = 
		{11, 23, 47, 97, 199, 401, 809, 1667, 3307, 6833, 16993, 34403, 68891, 128591, 199999};
   
   //The constructor builds the initial with size 0 and size 11.
	public HashTable_SC(Comparator<String> c, Hasher h) {
      hashTable = new HashNode[PRIME[index]];
      index = 0;
		size = 0;
      hasher = h;
      comp = c;
	}

	//The method incCount takes an String as parameter and check whether the
   //word is in the hash hashTables. If so, just increase its count. If not, create
   //a new node and put it to the front of the original one. If the load factor 
   //is larger than 1, increase the length of hashTable to the next prime. 
	public void incCount(String data) {
		if(size > hashTable.length){//load factor > 1, resize the hashTable
         resize();
	   }
      int position = hasher.hash(data) % PRIME[index]; 
      if(hashTable[position] == null) {
			hashTable[position] = new HashNode(new DataCount(data, 1));
			size++;
      }else{
         HashNode current = hashTable[position];
         while(current != null && comp.compare(current.data.data, data) != 0){
            current = current.next;
         }
         if(current == null){
            hashTable[position] = new HashNode(new DataCount(data, 1), hashTable[position]);
            size++;
         }else{
            current.data.count += 1;
         }
      }
	}
   
   //The private method resizeis used to enlarge the size of the hashTable to the next
   //prime number in the prime array when the load facotr is larger than 1.
   private void resize(){
      index++;
      HashNode[] temp = new HashNode[PRIME[index]];
      for(HashNode node : hashTable){
		   while(node != null){
				int position = hasher.hash(node.data.data) % PRIME[index];					
				if(temp[position] == null)
					temp[position] = new HashNode(node.data);
		      else{
				   temp[position] = new HashNode(node.data, temp[position]);
		      }
			   node = node.next;
		   }		
		}
      hashTable = temp;
   }

	//The method getSize returns the number of elements in the hash hashTable.
	public int getSize() {
		return size;
	}

	//return the appearance of the given word. 
	public int getCount(String data) {
		HashNode current = hashTable[hasher.hash(data) % PRIME[index]];
      while(current != null){
         if(comp.compare(current.data.data, data) == 0){
            return current.data.count;
         }
         current = current.next;
      }
      return 0;     
	}

	//This class returns an iterator of the separate chaining hashhashTable. 
	public SimpleIterator getIterator() {
		return new SCIterator();
	}
   
   //This is an inner class to build a new iterator.
   private class SCIterator implements SimpleIterator{
      private int itrSize; //number of values in the iterator
      private HashNode node; //current node of the iterator
      private int itrIndex; //the index of the iterator
      
      //The constructor constructsthe initial state of the iterator.
      public SCIterator(){
         itrSize = 0;
         itrIndex = 0;
         if(size > 0){
			   while(hashTable[itrIndex] == null){
					itrIndex++;
            }
            node = hashTable[itrIndex];
			}else{
            node = null;
         }
      }
      
      //This method returns true if there exists next node in the iterator.
      public boolean hasNext(){
         return itrSize < size;
      }
      
      //The method next return the next Datacount value if exists. It returns
      //null if no next element. 
      public DataCount next(){
			HashNode result = node;
         itrSize++;
			if(hasNext()){
				if(node.next != null){
               node = node.next;
				}else{
					itrIndex++;
					while(hashTable[itrIndex] == null){
						itrIndex++;
					}
					node = hashTable[itrIndex];
				}
			}
			return result.data;
      }
   }
 
   //This is a inner class to create new hashNode for the hashhashTable
   //of separate chaining. 
	private class HashNode{
		public DataCount data;
	   public HashNode next;
      
      //Create a hashNode with only the data provided
	   public HashNode(DataCount data){
	      this(data, null);
	   }
      
      //Create a hashNode if both the data and the next node are provided.
	   public HashNode(DataCount data, HashNode next){
	      this.data = data;
	      this.next = next;
	   }
	}
}
