package scripts;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.tribot.api.Constants;
import org.tribot.api.Inventory;
import org.tribot.api.NPCChat;
import org.tribot.api.Screen;
import org.tribot.api.ScreenModels;
import org.tribot.api.Text;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.GeneralItem;
import org.tribot.api.types.InventoryItem;
import org.tribot.api.types.ScreenModel;
import org.tribot.api.types.TextChar;
import org.tribot.api.types.colour.ColourPoint;
import org.tribot.api.types.colour.Tolerance;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.api.interfaces.*;

 
@ScriptManifest(authors = { "Phantom866" }, category = "Adverts", name = "Y_SPAMMER", description =
"<html>"+
"<head>"+
"       <title>SPAMMER</title>"+
"</head>"+
"<body bgcolor=#cccccc>"+
"       <center><h1><font face=Impact size=25 color=purple >Y SPAMMER</font></h1></center>"+       
"       <center><font face=Impact style=Bold size=6 color=#ddaa00><font color= #0088aa>CREATED FOR YDYCE ONLY</font>"+
"&nbsp;&nbsp;<input maxlength=\"99\" name=\"inputmsg\" type=\"text\" value=\"Enter Message Here")


public class Y_Spammer extends Script{


private void AddEveryone1() {

	Mouse.click(583, 305, 1);
	Mouse.click(583, 320, 1);
	Mouse.click(583, 340, 1);
	Mouse.click(583, 360, 1);
	Mouse.click(583, 380, 1);
	Mouse.click(583, 400, 1);
	Mouse.click(583, 420, 1);
	Mouse.click(583, 440, 1);
	Mouse.click(583, 460, 1);
	Mouse.click(583, 480, 1);		
            }

private void ScrollDown() {

	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
	Mouse.click(729, 470, 1);
}
    
	private void msganddelete() {
		Mouse.click(582,304, 1);
		Keyboard.typeSend("inputmsg");
		
		Mouse.click(582,304, 3);
		Timing.waitChooseOption("Delete", 150);
	}


	private boolean seetext(){
        Color gray = new Color(164, 153, 125);
       
        ColourPoint[] grayColors = Screen.findColours(gray, 153, 289, 656, 323, new Tolerance(10));

        while(grayColors.length > 0){
                return true;
                }
               
        return false;
}
	
	private boolean friendcount(){
		TextChar[] text = Text.findCharsInArea(585, 500, 598, 515, true);
		String sentence = "";
       // if (text.length > 0){
           //     for (int i = 0; i < text.length; i++)
           //             sentence = sentence + String.valueOf(text[i].character);
               // println(sentence);
                if (sentence.contains("00/220000")){  
                	println("no friends ");
                                        return true;
                                }
                                       
                      //  }
        return false;
    }
	
	private boolean FCTabOpen() {
		TextChar[] text = Text.findCharsInArea(585, 500, 598, 515, true);
		String sentence = "";
        if (text.length > 0){
                for (int i = 0; i < text.length; i++)
                        sentence = sentence + String.valueOf(text[i].character);
                println(sentence);
                if (sentence.contains("00/220000")){  
                	println("no friends ");
                                        return true;
                                }
                                       
                        }
        return false;
    }
	
	
        @Override
    public void run() {
        	Mouse.setSpeed(800);
        	
        	
        	//		AddEveryone1();
        //			ScrollDown();
        		
        	
        	
        	
        	//Mouse.click(566,531,1);

        	while (!friendcount()){
        		msganddelete();
        	}
        	
        	}
		}
        		
        	
		
	