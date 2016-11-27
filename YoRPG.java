// Team double u
// Gian Tricarico, Fabiola Radosav, James Zhang
// APCS1 pd4
// HW34 -- Ye Olde Role Playing Game, Unchained
// 2016-11-23

/*=============================================
  class YoRPG -- Driver file for Ye Olde Role Playing Game.
  Simulates monster encounters of a wandering adventurer.
  Required classes: Warrior, Monster
  =============================================*/

import java.io.*;
import java.util.*;

public class YoRPG 
{
    // ~~~~~~~~~~~ INSTANCE VARIABLES ~~~~~~~~~~~

    //change this constant to set number of encounters in a game
    public final static int MAX_ENCOUNTERS = 5;

    //each round, a Character and a Monster will be instantiated...
    private Character pat;   //Is it man or woman?
    private Monster smaug; //Friendly generic monster name?

    private int moveCount;
    private boolean gameOver;
    private int difficulty;

    private InputStreamReader isr;
    private BufferedReader in;
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    // ~~~~~~~~~~ DEFAULT CONSTRUCTOR ~~~~~~~~~~~
    public YoRPG() {
	moveCount = 0;
	gameOver = false;
	isr = new InputStreamReader( System.in );
	in = new BufferedReader( isr );
	newGame();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    // ~~~~~~~~~~~~~~ METHODS ~~~~~~~~~~~~~~~~~~~

    /*=============================================
      void newGame() -- facilitates info gathering to begin a new game
      pre:  
      post: according to user input, modifies instance var for difficulty 
      and instantiates a Warrior
      =============================================*/
    public void newGame() {

	String s;
	String name = "";
	s = "Welcome to Ye Olde RPG!\n";

	s += "\nChoose your difficulty: \n";
	s += "\t1: Easy\n";
	s += "\t2: Not so easy\n";
	s += "\t3: Beowulf hath nothing on me. Bring it on.\n";
	s += "Selection: ";
	System.out.print( s );

	try {
	    difficulty = Integer.parseInt( in.readLine() );
	}
	catch ( IOException e ) { }
	
	

	s = "Intrepid fighter, what doth thy call thyself? (State your name): ";
	System.out.print( s );

	try {
	    name = in.readLine();
	}
	catch ( IOException e ) { }
	
	

	//instantiate the player's character
	
	s = "Which path dost thou wish to take? (Choose your character ";
	s += "and enter the number preceding its name)\n";

	s += "\t1: Warrior\n";
	s += "\t2: Mage\n";
	s += "\t3: Tank\n";
    s += "\t4: Rogue\n";
	s += "\t5: Archer\n";
	s += "Selection: ";
	System.out.print(s);
	
	try {
	    int choice;
	    choice = Integer.parseInt( in.readLine() );
	    if ( choice == 5 ) {
	        pat = new Archer( name );
		System.out.println( ((Archer)pat).about() );
	    }
	    else if ( choice == 4 ) {
	        pat = new Rogue( name );
		System.out.println( ((Rogue)pat).about() );
	    }
	    else if ( choice == 3 ) {
	        pat = new Tank( name );
		System.out.println( ((Tank)pat).about() );
	    }
	    else if ( choice == 2 ) {
	        pat = new Mage( name );
		System.out.println( ((Mage)pat).about() );
	    }
	    else {
	        pat = new Warrior( name );
		System.out.println( ((Warrior)pat).about() );
	    }
	}
	catch ( IOException e) {
	    pat = new Warrior( name );
	    System.out.println( ((Warrior)pat).about() );
	}

    }//end newGame()


    /*=============================================
      boolean playTurn -- simulates a round of combat
      pre:  Warrior pat has been initialized
      post: Returns true if player wins (monster dies).
      Returns false if monster wins (player dies).
      =============================================*/
    public boolean playTurn() {

	int i = 1;
	int d1, d2;

	if ( Math.random() >= ( difficulty / 3.0 ) )
	    System.out.println( "\nNothing to see here. Move along!" );
	else {
	    System.out.println( "\nLo, yonder monster approacheth!" );

	    smaug = new Monster();

	    while( smaug.isAlive() && pat.isAlive() ) {

		// Give user the option of using a special attack:
		// If you land a hit, you incur greater damage,
		// ...but if you get hit, you take more damage.
		try {
		    System.out.println( "\nWhat will you do?" );
		    System.out.println( "\t1: Attack.\n\t2: Attack with Style!\n\t3: Drink up!" );
		    i = Integer.parseInt( in.readLine() );
		}
		catch ( IOException e ) { }

        
		if ( i == 2 )
		    pat.specialize();
        
		if ( i == 1 )
		    pat.normalize();

		d1 = pat.attack( smaug );

		d2 = smaug.attack (pat);

		if (i == 3)
		    pat.heal();
	   
		d2 = smaug.attack( pat );
        

	      
	       

		System.out.println( "\n" + pat.getName() + " dealt " + d1 +
				    " points of damage.");

		System.out.println( "\n" + "Ye Olde Monster smacked " + pat.getName() +
				    " for " + d2 + " points of damage.");
	        
		
	    }//end while

	    //option 1: you & the monster perish
	    if ( !smaug.isAlive() && !pat.isAlive() ) {
		System.out.println( "'Twas an epic battle, to be sure... " + 
				    "You cut ye olde monster down, but " +
				    "with its dying breath ye olde monster. " +
				    "laid a fatal blow upon thy skull." );
		return false;
	    }
	    //option 2: you slay the beast
	    else if ( !smaug.isAlive() ) {
		System.out.println( "HuzzaaH! Ye olde monster hath been slain!" );
		if (Monster.dropPotion())
		    {Character.gainPotion();
			System.out.println("A potion hath dropped from ye monster! ");}
		
		return true;
	    }
	    //option 3: the beast slays you
	    else if ( !pat.isAlive() ) {
		System.out.println( "Ye olde self hath expired. You got dead." );
		return false;
	    }
	}//end else

	return true;
    }//end playTurn()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    public static void main( String[] args ) {

	//As usual, move the begin-comment bar down as you progressively 
	//test each new bit of functionality...

	//loading...
	YoRPG game = new YoRPG();

	int encounters = 0;

	while( encounters < MAX_ENCOUNTERS ) {
	    if ( !game.playTurn() )
		break;
	    encounters++;
	    System.out.println();
	}

	System.out.println( "Thy game doth be over." );
	/*================================================
	  ================================================*/
    }//end main

}//end class YoRPG




/*=============================================
  =============================================*/
