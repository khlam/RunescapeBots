package scripts;
 
import java.awt.Point;
import java.util.HashMap;

import org.tribot.api.EGW;
import org.tribot.api.Inventory;
import org.tribot.api.ScreenModels;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.InventoryItem;
import org.tribot.api.types.ScreenModel;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

 
@ScriptManifest(authors = { "Phantom866" }, category = "Mining", name = "PhantomLRC Miner", description =
"<html>"+
"<head>"+
"       <title>PhantomLRC Miner</title>"+
"</head>"+
"<body bgcolor=#cccccc>"+
"       <center><h1><font face=Impact size=25 color=purple >PHANTOM LRC Miner</font></h1></center>"+
"       <p font face=Cambri size=12>"+
"               Start your character near the LRC bank deposit box.</p>"+
"       <p font face=Cambri size=12>"+
"               Enter Mouse Speed&nbsp;&nbsp;<input maxlength=\"3\" name=\"MouseSpeed\" type=\"text\" value=\"150\" /></p>"+                  
"       <p font face=Cambri size=12>"+
"		Special thanks to Emu (Stole your GUI :D) & JJ for his helpfull tuts<p>"+
"       <center><font face=Impact style=Bold size=6 color=#ddaa00>Join <font color= #0088aa>Y_Dyce</font> Clan Chat "+
"		#1 F2P Gambling Clan</font></center"+
"</body>"+
"</html>")

public class PhantomLRC extends Script{
	final long[] rockids = {3799429695L};
	final long[] PlayerMining = {636110262L};
	final long[] DepositBank = {4390934L};
	final int[] goldID = {125105};
	final int[] goldicon = {};
	final int[] coalicon = {};
	
	final int[] loop = {1000};

	int mspeed = 150;	
	
	   private boolean checkinv(){
		   InventoryItem[] gold = Inventory.find(goldicon);
		   if (gold.length > 27){
			   return true;
	   }
	   return false;
	   }
	
	 public void passArguments(HashMap<String, String> m) {
			mspeed = Integer.parseInt(m.get("MouseSpeed"));
	   }   
	   
	private boolean IsMining(){
		  ScreenModel[] animating = ScreenModels.find(PlayerMining);
		 if (animating.length > 0){
			 return true;
		 }
		 return false;}	
	
	private boolean IsAtBank(){
		ScreenModel[] bank = ScreenModels.find(DepositBank);
		if (bank.length > 0){
			return true;
		}else {
			return false;
		}
		
	}

	//private boolean CanMineRock(){
//	ScreenModel[] rock = ScreenModels.find(rockids);
//	if (rock.length > 0){
//		rock[0].hover(new Point(-5, 5), new Point(-5, 5));
//		sleep(150, 200);		
//		if (Timing.waitUptext("Mine", 150))
//		    return true; 
//		}else{
//			
//		return false;
//		}
//	}
	
	public boolean Loop() {
		return true;
	}
		
	private void WalktoRock1(){

		while (!EGW.walkTo(3657, 5106)) {
	    	   EGW.walkTo(3657, 5106);	    	
	        }
		
		while (!EGW.walkTo(3657, 5092)) {
	    	   EGW.walkTo(3657, 5092);
	        	    	
	        }
		
		while (!EGW.walkTo(3663, 5084)) {
	    	   EGW.walkTo(3663, 5084);
	        	    	
	        }
		
		while (!EGW.walkTo(3669, 5076)) {
	    	   EGW.walkTo(3669, 5076);   	    	
	        }
		println("Walked to rock1");
		
		}
	
	
	private void WalktoBankFrom1(){

		while (!EGW.walkTo(3663, 5090)) {
	    	   EGW.walkTo(3663, 5090);	    	
	        }
		
		while (!EGW.walkTo(3658, 5102)) {
	    	   EGW.walkTo(3658, 5102);
	        	    		    	
	        }
		
		while (!EGW.walkTo(3655, 5114)) {
	    	   EGW.walkTo(3655, 5114);
	        	    		    	
	        }
		}

	private void HandleRock(){
		ScreenModel[] rock = ScreenModels.findNearest(rockids);
		if (rock.length > 0){
			rock[0].hover(new Point(-5, 5), new Point(-5, 5));
		//	if (!CanMineRock()){
				println("Can't Mine Rock");
			}else {
				MineGoldRock();
			while (IsMining()){
		        	sleep(3000,4000);
		        	};
				}
			}		
	//	}
	
	
	private boolean IsAtRock() {
		ScreenModel[] rock = ScreenModels.findNearest(rockids);
		if (rock.length > 0){
			println("We are at the rock.");
			return true;			
			}else{
				println("Can't find rock.");		
				return false;
			}
	}
					
	private void HandleBank(){		
		InventoryItem[] gold = Inventory.find(goldicon);
		gold[0].hover(new Point(-5, 5), new Point(-5, 5));
		sleep(50, 200);
		Mouse.click(1);
		
		ScreenModel[] Bank = ScreenModels.findNearest(DepositBank);
		if (Bank.length > 0) {				
		Bank[0].hover(new Point(-5, 5), new Point(-5, 5));
		sleep(50, 200);}
		if (Timing.waitUptext("Pulley", 500)){			
			Mouse.click(1);
			sleep(200,600);
			Keyboard.typeString("4");
			}
				}
    		
	
	
	private void MineGoldRock(){
		ScreenModel[] rock = ScreenModels.findNearest(rockids);
		if (rock.length > 0){
			rock[0].hover(new Point(-5, 5), new Point(-5, 5));
			sleep(50, 200);
			if (Timing.waitUptext("Mine", 150)){
			Mouse.click(1);
			sleep(2000, 3000);}
		    }else{	
			if (Timing.waitUptext("Walkhere", 150)){
			}
			}
	}
		
        @Override
    public void run() {
        	
        	Mouse.setSpeed(mspeed);
        	
        	while(Loop()) {
        		
        		while(IsAtBank()) {
        			if(checkinv()) {
        				HandleBank();
        					WalktoRock1();
        		}
        	}
        	
        	while(IsAtRock()){
        		if(!checkinv()){
        			HandleRock();
        		}else{
        			if(checkinv()){
        				WalktoBankFrom1();
        			}
        		}           		        	
        	}
        				        	
        				}			
        			}	
      										}
        	
           	
        	
      
        	
	
