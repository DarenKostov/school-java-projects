import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 *
 * Later modified by Daren Kostov
 * 11/04/2022
 */

class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private final int maxNumOfItems=4;
    private Inventory inventory= new Inventory(3,maxNumOfItems);  //stores a local inventory to the room
    private int darkness=0; //how dark is the room
    private int marking=0;
    
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public boolean hasExit(String direction){
    	return exits.get(direction)!=null;
    	
    	
    }
    
    //adds items to the inventory
    public void addItems(String name, int amount) {
		for(int i=0; i<amount; i++)
			inventory.addItem(name);
    }
    
    
    //returns weather there is enough space in the room for a particular item
    public boolean enoughSpace(String name, int amount) {
    	return inventory.enoughSpace(name, amount);
    }
   
    
    
    
    //removes items from the inventory
    public void removeItems(String name, int amount) {
    	for(int i=0; i<amount; i++)
    		inventory.removeItem(name);
    }
    
    
    //returns weather the item exists in the inv
    public boolean hasItem(String name) {
    	return inventory.hasItem(name);
    }
    
    
    
    
    //set the mark if its not marked already
    public boolean SetMarking(int n) {
    	if(marking==0) {
    		marking=n;
    		return true;
    	}else
    		return false;
    	
    }
    //returns the marking of the room
    public int GetMarking() {
    	return marking;
    }
    
    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     This room is marked with the number: 5
     */    
    public String getLongDescription()
    {
    	if(marking==0)
    		return "You are " + description + "\n" + getExitString() + "\n" + getItemsString();
    	else
            return "You are " + description + "\nThe room is marked with the number: " + marking + "\n" + getExitString() + "\n" + getItemsString();

    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        String returnString = "Exits:\n";
        Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += iter.next() + ", ";
        //removes last 2 characters (", ")
        return returnString.substring(0, Math.max(0,returnString.length() - 2));
    }

    //get all of the items in a single string
    private String getItemsString()
    {
        String returnString = "Items:\n";
        returnString+=inventory.getItemsWithAmounts();
        return returnString;
    }

    
    //sets the room darkness (the darker the more dangerous)
    public void setDarkness(int darkness) {
    	this.darkness=darkness;
    }
    //gets the room darkness 
    public int getDarkness() {
    	return darkness;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction) 
    {
        return (Room)exits.get(direction);
    }
}

