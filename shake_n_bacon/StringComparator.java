package shake_n_bacon;

import providedCode.*;

/**
 * @author <Fangzheng Sun>
 * @UWNetID <fs23>
 * @studentID <1239942>
 * @email <fs23@uw.edu>
 
 * The class StringComparator will returns a integer: a negative number when the
 * first argument to compare comes first alphabetically, 0 if two arguments are 
 * the same and a positive number if the second argument comes alphabetically.
 * The comparing is based from the ASCII character codes. 
 */
public class StringComparator implements Comparator<String> {

	public int compare(String s1, String s2) {
		int minLength = Math.min(s1.length(), s2.length());
      for(int i = 0; i < minLength; i++){
         if(s1.charAt(i) != s2.charAt(i)){
            return s1.charAt(i) - s2.charAt(i);
         }
      }
		if(s1.length() == s2.length()){ 
		  	return 0;
		}else if (s1.length() > s2.length()){
			return 1;
		}else{
			return -1;
	   }
   }
}
