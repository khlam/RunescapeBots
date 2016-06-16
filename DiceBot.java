package scripts;
 
import java.awt.Color;
import java.util.Random;

import org.tribot.api.Text;

import org.tribot.api.input.Keyboard;

import org.tribot.api.types.TextChar;

import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;


 
@ScriptManifest(authors = { "Phantom866" }, category = "Misc", name = "PDice")
public class DiceBot extends Script{
    Random dice = new Random();
    private String sender = "";
   

    public void messageReceived() {
		//String[] msg = clanMessageRecieved();
		//int index = getId();
		//sender = Message.getSender();
		//if ((msg.startsWith("!Dice")) ) {
			System.out.println(sender + " sent roll request.");
			
		}
	//}
    
    private boolean seetext(){

        Color gray = new Color(127, 255, 255),
                  yellow = new Color(252, 252, 100);
       
       // ColourPoint[] grayColors = Screen.findColours(gray, worldRectangle.x, worldRectangle.y, worldRectangle.x+worldRectangle.width, worldRectangle.y+worldRectangle.height, new Tolerance(20)),
      //                        yellowColors = Screen.findColours(yellow, worldRectangle.x, worldRectangle.y, worldRectangle.x+worldRectangle.width, worldRectangle.y+worldRectangle.height, new Tolerance(20));
        TextChar[] text = Text.findCharsInArea(9, 486, 499, 509, true);
        String sentence = "";
        if (text.length > 0){
                for (int i = 0; i < text.length; i++)
                        sentence = sentence + String.valueOf(text[i].character);
                //println(sentence);
                if (sentence.contains("!Ydyce")){                     
                                        return true;
                                }
                                       
                        }
        return false;
    }
    
    private void getname() {
    	TextChar[] text = Text.findCharsInArea(9, 486, 499, 509, true);
    	String sentence = "";
    }
    
    private boolean loop() {
    	return true;
    }
    
private void sendRoll() {
    int number; 
    for (int counter = 1; counter <=1; counter++) {
    	number = dice.nextInt(100);
    	Keyboard.typeSend( ""+ sender +"" + "You have rolled a [ " + number + " ] ");
    }
}    

    

		@Override
    public void run() {
			while (loop()) {
				sleep (2000);
				if (seetext()) {	
				sendRoll();
			}
			}
			
			
			
        	}
		}
