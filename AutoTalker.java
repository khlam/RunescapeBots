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
import org.tribot.api.types.InventoryItem;
import org.tribot.api.types.ScreenModel;
import org.tribot.api.types.TextChar;
import org.tribot.api.types.colour.ColourPoint;
import org.tribot.api.types.colour.Tolerance;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.api.interfaces.*;

 
@ScriptManifest(authors = { "Phantom866" }, category = "Misc", name = "PHTalker")
public class AutoTalker extends Script{

	private boolean loop() {
		return true;
	}
    

		@Override
    public void run() {
			while (loop()) {
				sleep (2000,3000);
				Keyboard.typeSend(" Archos Open @ [Y_Dyce] [58 x2] [Odd Seal x2] ");
				
				
				
			}
			}
			
			
			
        	}
		
