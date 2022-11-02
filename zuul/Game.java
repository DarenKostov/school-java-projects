import java.util.ArrayList;
import java.util.List;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 * 
 * Later modified by Daren Kostov
 * 10/10/2022
 * Modifications inspired by the backrooms series, specifically level 0, but the original does not have items at level 0
 * http://backrooms-wiki.wikidot.com/level-0
 * 
 * 
 * Recources used:
 * https://www.w3schools.com/java/ref_string_charat.asp
 * 
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    
    final int maxTries=100;
    
	int sanity=100;
	int danger=0;
	int time=(int)(Math.random()*1024);
    Inventory myInventory=new Inventory(3, 20);
    int markingNumber=1;
    /** 
     * Create the game and initialise its internal map.
     */
    public Game() 
    {

   
        createRooms();
        parser = new Parser();
    }

    public static void main(String[] args) {
    	
    	Game mygame=new Game();
    	mygame.play();
    }
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {



    	myInventory.addItem("almond-water");

    	myInventory.addItem("q");
    	myInventory.addItem("q");

    	myInventory.addItem("q");

    	myInventory.addItem("h");
    	myInventory.addItem("h");
    	myInventory.addItem("h");
    	myInventory.addItem("h");
    	myInventory.addItem("h");
    	myInventory.addItem("h");
    	myInventory.addItem("h");

    	myInventory.addItem("q");
    	myInventory.addItem("q");
    	myInventory.addItem("q");

    	
    	myInventory.addItem("a");
    	myInventory.addItem("a");
    	myInventory.addItem("a");
    	myInventory.addItem("a");
    	myInventory.addItem("a");
    	myInventory.addItem("a");
    	myInventory.addItem("a");
    	myInventory.addItem("a");

    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("c");
    	myInventory.addItem("p");
    	myInventory.addItem("p");
    	myInventory.addItem("p");
    	myInventory.addItem("p");
    	myInventory.addItem("p");
    	myInventory.addItem("p");
    	myInventory.addItem("p");
    	myInventory.addItem("p");

    	myInventory.addItem("q");
    	myInventory.addItem("q");
    	myInventory.addItem("q");
    	myInventory.addItem("q");
    	myInventory.addItem("q");

        //all possible names for level 0 rooms
        String[] level0Names={"in a very large room.", "in a big room.", "in a small room.", "in a very small room.", "in a familiar room?", "in a peculiar room.", "in a medium sized room.",
        						"in a very familiar room, were you here before?", "in yet an room with yellowish wall paper and soaked carped."};
        
        //"The fluorescent lights are flickering aggressively.";
        
        //$1 is between 1 and 5; $2 is between 1 and 2, $3 is between 2 and 3;
        String[] level0DarknessNames= {" There is/are $1 fluorescent light(s).", " $2 fluorescent light(s) is/are working and $3 is/are broken, its dark in here... you should probably leave soon",
        		" It's very dark in here, it's best to leave imediatly", " It's pitch black in here! Leaving this room is top priority!"};
        
        
        
        String[] exitTypes= {"west", "east", "south", "north", "northeast", "northwest", "southwest", "southeast"};
        String[] itemTypes= {"almond-water", "marker", "bandages", "axe", "compass"};
        
        
        
    	Room[] level0 = new Room[50];
    	
    	//make all rooms
    	for(int i=0; i<level0.length;i++) {
    		
    		String customName=level0Names[(int)(Math.random()*(level0Names.length))];
    		
    		int rand=(int)(Math.random()*(level0DarknessNames.length));
    		
    		//makes it more likely for a room to be well lit
    		if(Math.random()<0.5f)
    			rand=0;
    			
    		String darknessName=level0DarknessNames[rand];
    		
    		
    		//add the room darkness message
    		for(int j=0; j<darknessName.length(); j++) {
    			if(darknessName.charAt(j)!='$'){
    				customName+=darknessName.charAt(j);
    				continue;
    			}
    			j++;
    			
    			switch(darknessName.charAt(j)) {
    			case '1':
    				customName+=(int)(Math.random()*5+1);
    				break;
    			case '2':
    				customName+=(int)(Math.random()*1+1);
    				break;	
    			case '3':
    				customName+=(int)(Math.random()*1+3);
    				break;		
    			}
    		}
    		
    			
    		
    		level0[i]= new Room(customName);
    		level0[i].setDarkness(rand);
    		
    		
    	}
    	//wire all rooms, or add exits to all rooms (and add all of the items)
     	for(int i=0; i<level0.length;i++) {
				
			//randomly add a random number of items of random items in random amounts
			if(Math.random()<0.9f) {
				int items=(int)(Math.random()*3);
				for(int j=0; j<items; j++)
					level0[i].addItems(itemTypes[(int)(Math.random()*(itemTypes.length))], (int)(Math.random()*4));
			}
     		
    		int numberOfExits=(int)(Math.random()*3)+1;//the number of exits (backexits increase that numbers, so its not very accurate)
    		
    		//loop through all exits
    		for(int j=0; j<numberOfExits; j++) {
	    		int exitNameID=(int)(Math.random()*(exitTypes.length));//the name of the exit
	    		int exitingTo=(int)(Math.random()*(level0.length));//the room that the exit leads to
	    		
	    		int tries=0; //there is a chance any of the while loops will cause an infinite loop so we limit how many times they are allowed to loop

	    		
				//set a random exit name and if the exit is already present in the room, re-roll (cant have 2 west exits)
	    		while(level0[i].hasExit(exitTypes[exitNameID]) && tries++<maxTries) {
	    			exitNameID=(int)(Math.random()*(exitTypes.length));
	    		}
	    		tries=0;
				//set a random exit room and if the exit is the room itself, re-roll (eliminates the very rare but possible possibility to hard lock yourself in 1 room upon starting the game)
	    		while(exitingTo==i && tries++<maxTries) {
	    			exitingTo=(int)(Math.random()*(level0.length));
	    		}		
	    		tries=0;
	    				
				//add the room to the exit list
				level0[i].setExit(exitTypes[exitNameID], level0[exitingTo]);
				
				
				
				//add the back exit to the exit room we just added (eliminates the accidental local room cluster locking)
				int backexit=0;
				while(level0[exitingTo].hasExit(exitTypes[backexit]) && tries++<maxTries) {//no need for the exit to be in the opposite direction as its in the non-euclidean space
					backexit=(int)(Math.random()*(exitTypes.length));
				}
				level0[exitingTo].setExit(exitTypes[backexit], level0[i]);

    		}       		
    	}
    		
    	
    	
    	

        
        currentRoom = level0[0];  //starting point
        
        //game starts with danger
        danger+=currentRoom.getDarkness()*30;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand(time, sanity, danger);
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    
    private void printRoomInfo() {
        System.out.println("\n"+currentRoom.getLongDescription()+"\n");
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to The Backrooms: Level 0!");
        System.out.println("You are stranded here forever! Please enjoy your time at level 0");
        System.out.println("Type 'help', 'tips', and 'features' if you need help.");
        printRoomInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go")) 
            goRoom(command);
        else if (commandWord.equals("inventory"))
        	showInventory();
        else if (commandWord.equals("take"))
        	Take(command);
        else if (commandWord.equals("drop"))
        	Drop(command);
        else if (commandWord.equals("tips"))
        	printTips();
        else if (commandWord.equals("features"))
        	printFeatures();
        else if (commandWord.equals("use"))
        	Use(command);
        else if (commandWord.equals("look")) {
        	printRoomInfo();
         	incTime();
        }
        else if (commandWord.equals("quit")) 
            wantToQuit = quit(command);
        
                
        return wantToQuit;
    }

    // implementations of user commands:

    
    //use an item
    private void Use(Command command) {
	
	if(!command.hasSecondWord()) {
        System.out.println("Use what item?");
        return;
	}
	
	String item=command.getSecondWord();
	if(!myInventory.hasItem(item)) 
        System.out.println("You don;t have such item.");
	else {
		

		switch(item) {
		
		case "almond-water":
			sanity-=20;
			myInventory.removeItem(item);
			System.out.println("You drank 1 bottle of almond water");
			break;
			
		case "marker":
			//currentRoom.SetMarking(markingNumber);
			//System.out.print("You marked this room with the number "+currentRoom.GetMarking(markingNumber++));

			if(Math.random()<0.5f) {
				myInventory.removeItem(item);
				System.out.println(" but you used up the marker.");

			}else
				System.out.println(" and you didn't use up the marker.");
			break;
		case "bandages":
			System.out.println("You don't have any injuries.");
			break;
			
		case "axe":
			System.out.println("On what will you use this, there are no trees, and it would look bad if you messed up the walls.");
			break;
		case "compass":
			System.out.println("The compass is spinning in random direcions... is north really north?");
			break;			
		default:
			System.out.println("How did you get this item???");
		
		}    	
    	incTime();
    	}

    }

    
    
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You nocliped into a wall or perhaps into the ground, you dont remeber, and now you are stuck here, in the backrooms: level 0. You haven't seen a single person in a while.");
        System.out.println("You are completely alone, in this endless maze of yellowed wallpaper, damp carpet, and inconsistently placed fluorescent lighting.\n");
        System.out.println("Your command words are:\n");
        System.out.println("{command} ({aliases}): {description}");
        System.out.println("Args: {arguments}");
        parser.showCommands();
        System.out.println("When a command has a \"$\" at the beginning of its description it means it increases the time upon executing it successfully.\n\n\n");
        System.out.println("       sanit");
        System.out.println("time     |     danger");
       System.out.println("  \\      |      /");
        System.out.println("   v     v     v");
      //System.out.println("(00:00)[100%]{0%}>");
        
        
    }

    
    
    private void showInventory() {
    	System.out.println("Inventory:");
    	System.out.println(myInventory.getItemsWithAmounts());
    	incTime();
    }
    
    
    //drops an item in the current room, default amount of items dropped is 1
    private void Drop(Command command) {
    	
    	if(!command.hasSecondWord()) {
            System.out.println("Drop what item?");
            return;
    	}
    	
    	String item=command.getSecondWord();
    	
    	int amount=isInt(command.getThirdWord())? Integer.parseInt(command.getThirdWord()) : 1;
    	
    	if(!myInventory.hasItem(item)) 
            System.out.println("You don't have such item.");
    	else if(myInventory.getItemamount(item)<amount)
            System.out.println("You don't have that much of this item");
    	else if(!currentRoom.enoughRoomForItem(item, amount))
			System.out.println("There isn't enough room in this room for this item.");    	
    	else {
    		for(int i = 0; i<amount; i++) 
    			myInventory.removeItem(item);
    		currentRoom.addItems(item, amount);
        	System.out.println("dropped "+amount+" "+ item+"(s)");

         	incTime();
    		
    	}
    	
    	
    }
    
    
    //takes only item from the current room
    private void Take(Command command) {
    	
    	if(!command.hasSecondWord()) {
            System.out.println("Take what item?");
            return;
    	}
    	
    	String item=command.getSecondWord();
    	if(!currentRoom.hasItem(item)) 
            System.out.println("There is no such item in this room.");
    	else if(!(myInventory.enoughSpace(item) || myInventory.enoughSpaceForNew()))
            System.out.println("There is not enough space in your inventory for more items.");
    	else {
    		myInventory.addItem(item);
    		currentRoom.removeItems(item, 1);
        	System.out.println("took "+ item);
        	
        	incTime();
        	}



    }
    
    
    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no exit there!");
        else {
            currentRoom = nextRoom;
            
            //resets danger because we moved
            danger=0;    
        	//moving decreases sanity
        	sanity-=5;

        	incTime();
        	
        	
            System.out.println(currentRoom.getLongDescription());
        }

        
    }

    
    //prints tips on how to play the game
    private void printTips() {
    	System.out.println("Your sanity decreses when you execute an action successfully and move between rooms.");
    	System.out.println("Your in danger level increases with time when in dark rooms.");
    	System.out.println("Almond water restores sanity.");
    	System.out.println("Compasses, bandages, and axes are useless.");
    	System.out.println("Markers can mark a room with a numberm starting from 1, can be usefull when navigating through level 0");
    	System.out.println("In order to escape level 0 you need to no-clip through a wall ceiling or floor, which is quite rare.");
    }
    
    
    //prints the features of the game
    private void printFeatures() {
    	int i=0;
    	//System.out.println(++i+"; ");
    	System.out.println("This game includes:");
    	System.out.println(++i+"; command alias system (commands can have aliaces like 'help' and 'h')");
    	System.out.println(++i+"; command argument system (commands can have multiple arguments)");
    	System.out.println(++i+"; inventory system (class that stores items)");
    	System.out.println(++i+"; inventory management system (add/remove items)");
    	System.out.println(++i+"; all rooms lead to random rooms consistently");
    	System.out.println(++i+"; rooms with somewhat uniqe descriptions");
    	System.out.println(++i+"; room darkness mechanic");
    	System.out.println(++i+"; rooms cannot lead to themselves");
    	System.out.println(++i+"; rooms can lead to the same room but with different exits");
    	System.out.println(++i+"; rooms cannot have 2 or more of the same exit direction");
    	System.out.println(++i+"; rooms can randomly have a random amount of random items");
    	System.out.println(++i+"; two rooms will lead to eachother (a room will lead to a room that leads to it)");
    	System.out.println(++i+"; item usage system (items can be taken, droped, used and stored)");
    	System.out.println(++i+"; danger and sanity system (if one is too high or the other is too low it's game over)");
    	System.out.println(++i+"; no win condition");
    }
    	
    	
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
    //checks weather a string can be an int
    private boolean isInt(String str) {
    	try {
    		Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return false;
		}
    	return true;
    }
    
    //increases time, danger and lowers sanity
    private void incTime() {
        danger+=currentRoom.getDarkness()*30;
        time++;
        sanity--;
    }
    
    
}
