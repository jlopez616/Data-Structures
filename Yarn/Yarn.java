package Yarn;


public class Yarn implements YarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Strand[] items;
    private int size;
    private int uniqueSize;
    private final int MAX_SIZE = 100;
    
    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------
    Yarn () {
    	items = new Strand[MAX_SIZE];
    	size = 0;
    	uniqueSize = 0;
    }
    
    Yarn (Yarn other) {
    	items = other.items;
    	size = other.size;
    	uniqueSize = other.uniqueSize;
        
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        if (size == 0) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public int getSize () {
       return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    
    public boolean insert (String toAdd) {
    	if (size == MAX_SIZE) {
    		return false;
    	} else {
    		if (!contains(toAdd)) {
        		items[uniqueSize] = new Strand(toAdd, 1);
        		uniqueSize++;
    		} else {
    			items[findAndCompare(uniqueSize, toAdd)].count++;
    		}
    	size++;
    	return true;
    	}
    }
    
    public int remove (String toRemove) {
    	int remains = 1;
        if (!contains(toRemove)) {
        	return 0;
        } else {
        	items[findAndCompare(uniqueSize, toRemove)].count--;
        	size--;
        	remains = items[findAndCompare(uniqueSize, toRemove)].count;
				if (remains == 0) {
					for (int l= findAndCompare(uniqueSize, toRemove); l < uniqueSize - 1; l++) {
						items[l] = items [l+1];
					}
					uniqueSize--;
				
				}
			return remains;
        }
    }
				
        	
  
    public void removeAll (String toNuke) {
        if (contains(toNuke)) {
        	if (findAndCompare(uniqueSize, toNuke) < (MAX_SIZE + 1)) {
        		size = size - items[findAndCompare(uniqueSize, toNuke)].count;
        		for (int l= 0; l < uniqueSize - 1; l++) {
					items[l] = items [l+1];
				}
        	}
        	uniqueSize--;
        }
    }
    
    public int count (String toCount) {
    	if (findAndCompare(uniqueSize, toCount) < (MAX_SIZE + 1)) {
    		return items[findAndCompare(uniqueSize, toCount)].count;
    	} else {
    		return 0;
    	}
    }

    public boolean contains (String toCheck) {
    	if (findAndCompare(uniqueSize, toCheck) < (MAX_SIZE + 1)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public String getNth (int n) {
    	int sizeIndex = 0;
    	int uniqueSizeIndex = 0;
    	
    	if (n == size) {
    		n--;
    	}
    	while (sizeIndex < n) {
    		for (int k = 0; k < items[uniqueSizeIndex].count + 1; k++) {
    			sizeIndex++;
    		}
    		if (sizeIndex < n) {
    			uniqueSizeIndex++;
    		}
    	}
    	return items[uniqueSizeIndex].text;
    } 
    
    public String getMostCommon () {
    	String mostCommon = "";
    	if (isEmpty()) {
    		return null;
    	} else {
    		int index = 0;
    		
    		for (int k = 0; k < uniqueSize; k++) {
    			if (items[k].count > index) {
    				index = items[k].count;
    				mostCommon = items[k].text;
    			}
    		}
    		return mostCommon;
    	}
    }
    
    public void swap (Yarn other) {
        Yarn placeHolder = new Yarn(other);
        other.items = items;
        other.size = size;
        other.uniqueSize = uniqueSize;
        items = placeHolder.items;
        size= placeHolder.size;
        uniqueSize = placeHolder.uniqueSize;
    }
    
    public String toString () {
        String print = "{ ";
        for(int k=0; k < uniqueSize; k++) {
        	print += "\"" + items[k].text + "\": " + items[k].count;
        	if (k != (uniqueSize - 1)) {
        		print += ", ";
        	}
        }
        print += " }";
        return print;
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static Yarn knit (Yarn y1, Yarn y2) {
    	Yarn newYarn = new Yarn(y1);
    	for (int k = 0; k < y2.getUniqueSize(); k++) {
    		if (newYarn.contains(y2.items[k].text)) { 
        		for (int l = 0;  l < newYarn.getUniqueSize(); l++) {
        			if (newYarn.items[l].text.equals(y2.items[k].text)) {
        				newYarn.insert(y2.items[k].text);
        			}
        		}
    		} else {
    			newYarn.insert(y2.items[k].text);
    		}
    	} 
    	return newYarn;
    } 
    
    public static Yarn tear (Yarn y1, Yarn y2) {
    	Yarn newYarn = new Yarn();
    	for (int k = 0; k < y1.getUniqueSize(); k++) {
    		if (!y2.contains(y1.items[k].text)) {
    			for (int l = 0; l < y1.items[k].count; l++) {
    				newYarn.insert(y1.items[k].text);
    			} 
    		} else if ( (y2.contains(y1.items[k].text)) 
    				&& ( y1.items[k].count > y2.items[k].count) ) {
    			for (int l = y2.items[k].count; l < y1.items[k].count; l++) {
    				newYarn.insert(y1.items[k].text);
    			}
			}
    	}
        return newYarn;
    }
    
    public static boolean sameYarn (Yarn y1, Yarn y2) {
 
         if ((y1.getSize() == y2.getSize()) && (y1.getUniqueSize() == y2.getUniqueSize())) {
        	 for (int k = 0; k < y1.getSize() - 1; k++) {
        		 if (!y1.contains(y2.items[k].text)) {
        			 return false;
        		 }
        	 } 
         } else {
        	 return false;
         }
         return true;
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    // Add your own here!
    
    //The purpose of the below method is to find the index number where the
    //comparison string is equal to. If the condition cannot be met, then the
    //result is an integer which would be impossible to obtain, rendering the
    //method useless.
    public int findAndCompare(int index, String compareTo) {
    	for (int k = 0; k < index; k++) {
    		if (items[k].text.equals(compareTo)) {
    			return k;
    		}
    	} 
    	return MAX_SIZE + 1;
    }
    
 
    
    
    public static void main(String[] args) {
    	
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        System.out.println(ball.count("balls"));
        }
    
}

class Strand {
    String text;
    int count;
    
    Strand (String s, int c) {
        text = s;
        count = c;
    }
    

}
