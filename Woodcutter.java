package scripts;
 
import java.awt.Point;

import org.tribot.api.Constants;
import org.tribot.api.Inventory;
import org.tribot.api.ScreenModels;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.InventoryItem;
import org.tribot.api.types.ScreenModel;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
 
@ScriptManifest(authors = { "Phantom866" }, category = "Woodcutting", name = "Woodcutter")
public class Woodcutter extends Script{
    final long[] treeids = {3243412105L,2036050167L,784785426L};
    final int logID = 242430;
    final long animatingID = 2977913099L;

    private void chopTree() { 	
    	ScreenModel[] tree = ScreenModels.findNearest(treeids);
    	if (tree.length > 0){
    		tree[0].hover(new Point(-5, 5), new Point(-5, 5));
    		sleep(50, 200);
    		if (Timing.waitUptext("Chop down tree", 500)){
    			Mouse.click(1);
    		sleep(2000, 3000);
    		}
    	}  		
    }
    
    private void droplogs() {
    	InventoryItem[] logs = Inventory.find(logID);
    	if (logs.length > 0){
    		for (int i = 0; i < logs.length; i++){
    			Point P = new Point(logs[i].x + 5 + Constants.RANDOM.nextInt(21), logs[i].y + 5 + Constants.RANDOM.nextInt(21)); 
				Mouse.move(P);
				Mouse.click(3);
				Timing.waitChooseOption("Drop", 500);
    		}
    	}
    }
    	
   private boolean fullinv(){
	   InventoryItem[] all = Inventory.getAll();
	   if (all.length > 27){
		   return true;
   }
   return false;
   }
    
  private boolean isanimating(){
	  ScreenModel[] animating = ScreenModels.find(animatingID);
	 if (animating.length > 0){
		 
		 return true;
	 }
	 return false;}
  
   

		@Override
    public void run() {
        	
        while(!fullinv())
        if (isanimating()){
        	println("Character is chopping");
        	sleep(2000,4000);
        	}else{
        chopTree();
        }
        println("Dropping Logs");
        droplogs();
		}}