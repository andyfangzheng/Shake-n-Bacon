package shake_n_bacon;

import providedCode.Hasher;

/**
 * @author <Fangzheng Sun>
 * @UWNetID <fs23>
 * @studentID <1239942>
 * @email <fs23@uw.edu>
 *
 * The class StringHasher hasher strings.
 */
public class StringHasher implements Hasher {

	//The method hash is used to hash strings. It is based from the ACSII code. 
   //It returns the sum of all characters in the string.
	public int hash(String str) {
		int sum = 0;
      for(int i = 0; i < str.length(); i++){
         sum += str.charAt(i);
      }
      return sum;
	}
}
