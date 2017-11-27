
import java.util.NoSuchElementException;

public class LinkedYarn implements LinkedYarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Node head;
    private int size, uniqueSize, modCount;
    
    
    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------
    LinkedYarn () {
        head = null;
        size = 0;
        uniqueSize = 0;
        modCount = 0;
        
    }
    
    LinkedYarn (LinkedYarn other) {

        for (Node curr = other.head; curr != null; curr = curr.next) {
        	for (int k = 0; k < curr.count; k++) {
            	insert(curr.text);	
        	}

        }

        size = other.size;
        uniqueSize = other.uniqueSize;
        modCount = other.modCount;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        return size == 0;
    }
    
    public int getSize () {
    	return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    public void insert (String toAdd) {
    	
    	if (searchFor(toAdd) == null) {
    		uniqueSize++;
    	}

    	if (isEmpty()) {
    		head = new Node (toAdd, 1);
    	} else {
    		insertOccurences(toAdd, 1);		
    	}

    	 size++;
    	 modCount++;
    	 
    	 
    	
    }
    
    public int remove (String toRemove) {
    	Node chosenOne = searchFor(toRemove);
    	if (chosenOne != null) {
    		modCount++;

    		if (chosenOne.count > 1) {
    			removeOccurences (toRemove, 1);
            	size--;
    			return chosenOne.count;
    		} 
    		removeOccurences (toRemove, 1);
    		size--;
    		uniqueSize--;
    	
    	}

    	return 0;
    	
    }

    public void removeAll (String toNuke) {

    	Node n = searchFor(toNuke);
    	if (n != null) {
    		removeOccurences (toNuke, n.count);
    		size -= n.count;
    		uniqueSize--;
    		modCount++;
    	}

    }
    
    public int count (String toCount) {

    	Node countThis = searchFor(toCount);
    	if (countThis != null) {
    		return countThis.count;
    	}
       return 0;
    }
    
    public boolean contains (String toCheck) {
    	return searchFor(toCheck) != null;
    }
    
    public String getMostCommon () {
    	if (isEmpty()) {
    		return null;
    	}
    	Node mostCommon = head;
        for (Node curr = head; curr != null; curr = curr.next) {
        	if (mostCommon.count < curr.count) {
        		mostCommon = curr;
        	}
        }
        return mostCommon.text;
    }
    
    public void swap (LinkedYarn other) {
    	LinkedYarn placeHolder = new LinkedYarn(other);
    	
    	other.head = head;
    	other.size = size;
    	other.uniqueSize = uniqueSize;
    	other.modCount = modCount;
    	
        head = placeHolder.head;
        size = placeHolder.size;
        uniqueSize = placeHolder.uniqueSize;
        modCount = placeHolder.modCount;
    	
    	
       modCount++;
       other.modCount++;
        
        
    }
    
    public LinkedYarn.Iterator getIterator () {
    	
    	if (isEmpty()) {
    		throw new IllegalStateException();
        }
       
    	Iterator it = new Iterator(this);
        return it;
        
    
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedYarn knit (LinkedYarn y1, LinkedYarn y2) {
       LinkedYarn sweater = new LinkedYarn(y1);
       for (Node curr = y2.head; curr != null; curr = curr.next) {
    	   if (sweater.searchFor(curr.text) == null) {
    		  sweater.uniqueSize++;
    	   }
    	   sweater.insertOccurences(curr.text, curr.count);
    		   
    	   }
       
       sweater.size += y2.size;
       sweater.modCount += y2.modCount;
       return sweater;
    }
    
    public static LinkedYarn tear (LinkedYarn y1, LinkedYarn y2) {

    	LinkedYarn myHeart = new LinkedYarn(y1);
        for (Node curr = y2.head; curr!=null; curr = curr.next) {
        	if (myHeart.searchFor(curr.text) != null) {
        		if (curr.count == (myHeart.searchFor(curr.text).count)) {
        			myHeart.uniqueSize--;
        		}
        		myHeart.removeOccurences(curr.text, curr.count);
        		myHeart.size -= curr.count;
        	}	
        }
    
    	return myHeart;
    }
    
    public static boolean sameYarn (LinkedYarn y1, LinkedYarn y2) {
    	 return tear(y1, y2).isEmpty() && tear(y2, y1).isEmpty();
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    
    // You should add some methods here!

    
    private void removeOccurences(String s, int c) {
    	
    	Node n = searchFor (s);
    	
    	if (c == n.count) {
        	if (n == head) {
        		head = n.next;
        	} 
        	if (n.prev != null) {
        		Node previousNode = n.prev;
        		previousNode.next = n.next;
        	}
        	if (n.next != null) {
        		Node nextNode = n.next;
        		nextNode.prev = n.prev;
        	}
    		
    	} else {
    		n.count -= c;
    	}
    }

    private void insertOccurences(String s, int c) {
    	Node current = searchFor(s);
    	if (current == null) {
    		Node newHead = new Node(s, c);
    		Node previousHead = head;
        	head = newHead;
        	newHead.next = previousHead;
        	previousHead.prev = newHead;
    		
    	} else {
    		current.count += c;
    		
    	}
    }
    
  
    private Node searchFor (String s) {
    	
    	for (Node curr = head; curr != null; curr = curr.next) {
    		if (curr.text.equals(s)) {
    			return curr;
    		}
    	}
    	return null;   
    }


    
    // -----------------------------------------------------------
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedYarnIteratorInterface {
        LinkedYarn owner;
        Node current;
        int itModCount;
        private int index;
        
        Iterator (LinkedYarn y) {
            owner = y;
            current = y.head;
            itModCount = y.modCount;
            index = 1;
        }
        
        public boolean hasNext () {
        	return ((index < current.count) || (current.next != null));

        }
        
        public boolean hasPrev () {
        	return ((index > 1) || (current.prev != null));

        }
        
        public boolean isValid () {
            return itModCount == owner.modCount;
        }
        
        public String getString () {
            if (isValid()) {
            	return current.text;
            }
            return null;
        }

        public void next () {
        	if (!isValid()) {
        	    throw new IllegalStateException();
        	}

        	
        	if ( (current.next == null) && (index == current.count)) {
        		throw new NoSuchElementException();
        	}
        	
          	index++;

        	if (index > current.count) {
        		current = current.next;
        		index = 1;

        		
        	}
            

        }
        
        public void prev () {
        	if (!isValid()) {
        		throw new IllegalStateException();	
        	}

        	if ( (index == 1) && (current.prev == null) ) {	
        		throw new NoSuchElementException();
        	}
        	
            index--;

        	if  ((index < 1) && (current.prev != null)){
        	 	current = current.prev;
        		index = current.count;


        	}
       
        }
        
        public void replaceAll (String toReplaceWith) {
        	if (!isValid()) {
        		throw new IllegalStateException(); 
        	}
 
        	current.text = toReplaceWith;
        	itModCount++;
        	owner.modCount++;

        }
       	 
    }
    
    class Node {
        Node next, prev;
        String text;
        int count;
        
        Node (String t, int c) {
            text = t;
            count = c;
        }
    }
}
