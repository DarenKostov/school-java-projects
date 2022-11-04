/*
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 *
 * Later modified by Daren Kostov
 * 11/04/2022
 */

class CommandWords
{
    // the 0th index is the main command
    private static final String validCommands[][] = {
    		{"help", "h"}, {"features", "f"}, {"tips", "ti"}, {"go", "g", "goto"} , {"take", "t"},
            {"use", "u", "utilize"}, {"drop", "d"}, {"inventory", "i", "inv"}, {"quit", "q", "exit", "e", "leave", ":q"}, 
    		{"look", "l", "lookaround", "la", "list", "ls"}, {"win", "w", "autowin", "aw", "automaticwin"}
    };
    private static final String CommandsDescription[] = {
    		"this help", "gives a list of all the features", "gives you tips", "$ moves your character in a direction",
            "$ takes a single item with you", "$ uses an item you have on you", "$ leaves an item you have on the ground",
            "$ shows your inventory", "exits the game", "$ describes the room you are in once again",
            "you win automatically, no questions asked (this exists due to the requrements of this project)"
    };
    private static final String CommandsArgs[] = {
    		"none", "none", "none", "<direction>", "<item>", "<item>", "<item> [amount]", "none", "none", "none",
            "none"
    };
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }


    //returns the main command of an alias, returns null if the command or alias don't exist
    public String Simplify(String str) {
    	
    	if(str==null)
    		return null;
    	
	    for(int i = 0; i < validCommands.length; i++) {
	        for(int j=0; j<validCommands[i].length; j++) {
	            if(str.equals(validCommands[i][j]))
	            	return validCommands[i][0];
	        }
	    }
		return null;
    	
    	
    }
    
    
    
    
    
    /*
     * prints info+description of each command
     */
    public void showAll() 
    {
        for(int i = 0; i < validCommands.length; i++) {
        	
        	String Aliases="";
	        for(int j=1; j<validCommands[i].length; j++) 
	        	Aliases+=validCommands[i][j]+", ";
        	Aliases=Aliases.substring(0, Math.max(0,Aliases.length() - 2));
        	
            System.out.print("\n"+validCommands[i][0] + " (" + Aliases + ") : "+CommandsDescription[i]+"\n"+"Args: "+CommandsArgs[i]+"\n");
        }
        System.out.println();
    }
}
