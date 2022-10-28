import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Daren Kostov
 * inventory class that keeps track of items
 * sources used:
 * 
 * https://stackoverflow.com/questions/4157972/how-to-update-a-value-given-a-key-in-a-hashmap
 * https://www.geeksforgeeks.org/hashmap-remove-method-in-java/
 * https://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string
 * 
 * 
 * 
 * 10/12/2022
 */



public class Inventory {
	private HashMap<String, Integer> items;
	private int maxUniqueItems;
	private int maxNumOfItems;
	
	
	public Inventory(int maxUniqueItems, int maxNumOfItems) {
		this.maxUniqueItems=maxUniqueItems;
		this.maxNumOfItems=maxNumOfItems;
		items=new HashMap<String, Integer>();
	}
	
	//adds 1 item
	public void addItem(String name) {
		if(items.get(name)==null)
			items.put(name, 1);
		else {
			if(items.get(name)<maxNumOfItems)
				items.put(name, items.get(name)+1);
		}

	}
	//removes 1 item
	public void removeItem(String name) {
		items.put(name, items.get(name)-1);
		if(items.get(name)<=0)
			items.remove(name);
	}
	
	//gets the amount of item
	public int getItemamount(String name) {
		if(!hasItem(name))
			return 0;
		return items.get(name);
	}
	
	//returns weather there is enough space for an item
	public boolean enoughSpace(String name) {
		if(items.get(name)!=null)
			return items.get(name)<maxNumOfItems;
		return true;
	}
	
	//returns weather there is enough space for a new an item
	public boolean enoughSpaceForNew() {
		return items.size()<maxUniqueItems;
	}
	
	//returns weather the item is in the inventory
	public boolean hasItem(String name) {
		return items.get(name)!=null;
	}
	
	
	public String getItemsWithAmounts() {
		String returnString="";
		
		Set keys= items.keySet();
		
        for(Iterator iter = keys.iterator(); iter.hasNext(); ) {
        	
        	String currentKey=(String)iter.next();
        	//the item name + its amount

            returnString += currentKey+": "+items.get(currentKey)+", ";
        }
        //removes the last ", " from the string, and make sure not to remove non existent characters (-1, -2)
        return returnString.substring(0, Math.max(0,returnString.length() - 2));
        
	}
	
	
}
