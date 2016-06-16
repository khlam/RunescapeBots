package scripts;
 
import java.awt.Point;
import java.awt.event.KeyEvent;


import org.tribot.api.Banking;
import org.tribot.api.Constants;
import org.tribot.api.Game;
import org.tribot.api.GameTab;
import org.tribot.api.GameTab.TABS;
import org.tribot.api.Inventory;
import org.tribot.api.NPCChat;
import org.tribot.api.Player;
import org.tribot.api.ScreenModels;
//import org.tribot.api.Textures;
import org.tribot.api.TPS;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.InventoryItem;
import org.tribot.api.types.ScreenModel;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

 
@ScriptManifest(authors = { "Phantom866" }, category = "Runecrafting", name = "PhantomNatures",

description =

"<html>"+
"<head>"+
"       <title>Phantom's Nature Crafter *BETA NOT PUBLIC RELEASE*</title>"+
"</head>"+
"<body>"+
"       <h1>"+
"               Phantom's Nature Crafter ALPHA</h1>"+
"       <p>"+
"               Special thanks to JJ for teaching me how to script, " +
"				Trilez for making this awsome bot, and anyone else who" +
"				has contributed to this project.</p>"+
"       <p>"+
"               Setup:</p>"+
"       <ul>"+
"               <li>"+
"                      Start anywhere.</li>"+
"               <li>"+
"                      Have all pouches in inventory</li>"+
"               <li>"+
"                      Have the camera &#39;up&#39; as high as possible (Birds&#39; Eye View)</li>"+
"       </ul>"+
"       <p>"+
"               Enter Mouse Speed&nbsp;&nbsp;<input maxlength=\"3\" name=\"MouseSpeed\" type=\"text\" value=\"80\" /></p>"+
"       <p>"+
"               Number of Trips <input maxlength=\"9\" name=\"runtime\" type=\"text\" value=\"60\" /></p>"+
"       <hr />"+
"</body>"+
"</html>")

	public class PhantomNatures extends Script{
	final long[] Banker = {1059792154L}; 
	
	final long[] Jungle = {0};
	
	final long[] AltarOut = {1108256240L};
	final long[] AltarIn = {1115721844L};
	
	final int NatID = 84662;
	final int EssID = 90288;
	
	final int GSPouchID = 0;
	
	final int RingID = 1120454;
	
	final int GPID = 96811;
	final int GPDegID = 96811;
	final int SPID = 1218735;
	final int LPID = 260740;
	final int MPID = 969086;

	
	final int forever = 0;
	   
        private void WalkToAltar() {

        	TPS.walkTo( new Point(3150, 6778));
        	while (Player.isMoving())
                sleep(1000, 2000);	 
        	
        	TPS.walkTo(new Point(3219, 6778));        	
        	while (Player.isMoving())
        		sleep(1000, 2000);	

        	TPS.walkTo(new Point(3275, 6767));    	
        	while (Player.isMoving())
        		sleep(1000, 2000);	

        	TPS.walkTo(new Point(3344, 6763));
        	while (Player.isMoving())
        		sleep(1000, 2000);		 
        	
        	TPS.walkTo(new Point(3409, 6759));
        	while (Player.isMoving())
        		sleep(1000, 2000);		
        	
        	TPS.walkTo(new Point(3413, 6746));
        	while (Player.isMoving())
        		sleep(1000, 2000);		
    }
    
        
        private void WalkToObyAltar() {
        	
        }
    
    	private void OpenTzBank() { 	
    	ScreenModel[] Bank = ScreenModels.findNearest(Banker);
    	if (Bank.length > 0){
    		Bank[0].hover(new Point(-5, 5), new Point(-5, 5));
    		sleep(100, 200);	  			
    			Mouse.click(3);
    			sleep(300,1000);
				Timing.waitChooseOption("Bank", 150);
    		sleep(2000, 3000);
    		}
    	}	
    	  		
   // }
    
    	private boolean FTab(TABS Tab){
    	char FKey;

        switch(Tab){
                case MAGIC: FKey = (char)KeyEvent.VK_F4; break;
                case EQUIPMENT: FKey = (char)KeyEvent.VK_F2; break;  
                case INVENTORY: FKey = (char)KeyEvent.VK_F1; break;
                default: return false;
        }
       
        if (Character.isDefined(FKey)){
                Keyboard.pressKey(FKey);
                sleep(100, 200);
                Keyboard.releaseKey(FKey);
                sleep(50, 150);
                return true;
        }
       
        return false;
}
    
    	private void TeleToBank() {
    	FTab(GameTab.TABS.EQUIPMENT);
    	InventoryItem[] ring = Inventory.find(RingID);
    	if (ring.length > 0){
    		for (int i = 0; i < ring.length; i++){
    			Point P = new Point(ring[i].x + 5 + Constants.RANDOM.nextInt(21), ring[i].y + 5 + Constants.RANDOM.nextInt(21)); 
				Mouse.move(P);
				sleep(150,300);
				Mouse.click(3);
				sleep(150,300);
				Timing.waitChooseOption("Teleport", 150);
				sleep(2000,4000);
				Keyboard.typeString("2");
    		}
    	}
    }
    
    
    	private void TeleToJungle() {
    	Mouse.moveBox(701, 184, 714, 200);
			sleep(1000,1500);
			Mouse.click(1);
			sleep(2000,3000);
			Keyboard.typeString("2");
    }
    
    
    	private void WithdrawESS() {   	
    			sleep(30,100);	
				Mouse.moveBox(443, 239, 458, 253);
				sleep(150,300);
				if (Game.isUptext("Pure")){
				Mouse.click(3);
				sleep(150,300);
				Timing.waitChooseOption("Withdraw-All", 150);
				sleep(150,300);
				}
    }
    
  
		private void FillGP() {
		InventoryItem[] GP = Inventory.find(GPID);
		    	if (GP.length > 0){
		    		for (int i = 0; i < GP.length; i++){
		    			Point P = new Point(GP[i].x + 5 + Constants.RANDOM.nextInt(21), GP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
						Mouse.move(P);
						sleep(150,300);
						Mouse.click(3);
						sleep(150,300);
						Timing.waitChooseOption("Fill", 150);
		    		}	
		    	}
			}	    			    	
		
		private void FillSP() {
			InventoryItem[] SP = Inventory.find(SPID);
			if (SP.length > 0){
			    for (int i = 0; i < SP.length; i++){
			    	Point P = new Point(SP[i].x + 5 + Constants.RANDOM.nextInt(21), SP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
					Mouse.move(P);
					sleep(150,300);
					Mouse.click(3);
					sleep(150,300);
					Timing.waitChooseOption("Fill", 150);
			    	}
				} 
			}
		
		private void FillLP() {
		InventoryItem[] LP = Inventory.find(LPID);
		if (LP.length > 0){
		    for (int i = 0; i < LP.length; i++){
		    	Point P = new Point(LP[i].x + 5 + Constants.RANDOM.nextInt(21), LP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
				Mouse.move(P);
				sleep(150,300);
				Mouse.click(3);
				sleep(150,300);
				Timing.waitChooseOption("Fill", 500);
		    		}
				}
			}
		
		private void FillMP() {
		InventoryItem[] MP = Inventory.find(MPID);		
		if (MP.length > 0){
		    for (int i = 0; i < MP.length; i++){
		    	Point P = new Point(MP[i].x + 5 + Constants.RANDOM.nextInt(21), MP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
				Mouse.move(P);
				sleep(150,300);
				Mouse.click(3);
				sleep(150,300);
				Timing.waitChooseOption("Fill", 150);
		    		}
				}  
			}	
		
		private void EGP() {
			InventoryItem[] GP = Inventory.find(GPID);
	    	if (GP.length > 0){
	    		for (int i = 0; i < GP.length; i++){
	    			Point P = new Point(GP[i].x + 5 + Constants.RANDOM.nextInt(21), GP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
					Mouse.move(P);
					sleep(150,300);
					Mouse.click(1);
	    			}
	    		}
	  		}	    	

		private void ESP() {
			InventoryItem[] SP = Inventory.find(SPID);
	    	if (SP.length > 0){
	    		for (int i = 0; i < SP.length; i++){
	    			Point P = new Point(SP[i].x + 5 + Constants.RANDOM.nextInt(21), SP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
					Mouse.move(P);
					sleep(150,300);
					Mouse.click(1);
	    			}
	    		}
	  		}
		
		private void ELP() {
			InventoryItem[] LP = Inventory.find(LPID);
	    	if (LP.length > 0){
	    		for (int i = 0; i < LP.length; i++){
	    			Point P = new Point(LP[i].x + 5 + Constants.RANDOM.nextInt(21), LP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
					Mouse.move(P);
					sleep(150,300);
					Mouse.click(1);
	    			}
	    		}
	  		}	

		private void EMP() {
			InventoryItem[] MP = Inventory.find(MPID);
	    	if (MP.length > 0){
	    		for (int i = 0; i < MP.length; i++){
	    			Point P = new Point(MP[i].x + 5 + Constants.RANDOM.nextInt(21), MP[i].y + 5 + Constants.RANDOM.nextInt(21)); 
					Mouse.move(P);
					sleep(150,300);
					Mouse.click(1);
	    			}
	    		}
	  		}
	
		private void HandleBank() {
			WithdrawESS();
			FillGP();
			FillSP();
			WithdrawESS();
			FillLP();
			FillMP();
			WithdrawESS();
		}
		
		private void HandleRepair() {
			Mouse.moveBox(570, 311, 586, 322);
				sleep(150,300);
				Mouse.click(3);
				sleep(150,300);
				Timing.waitChooseOption("5", 150);

	    	sleep(500,1000);
				WithdrawCos();
				sleep(150,300);
				WithdrawAst();
			sleep(500,1000);
				Banking.closeBankScreen();
			sleep(500,1000);;
            	FTab(GameTab.TABS.MAGIC);
            sleep(150,300);
            	Mouse.moveBox(656, 271, 660, 283); 
            sleep(300,600);
            Mouse.click(1);
            
            sleep(1500,2000);
            Mouse.moveBox(486, 232, 490, 241);
            sleep(150,300);
            Mouse.click(1);
            
            sleep(150,300);
            Mouse.moveBox(415, 173, 452, 200);
            sleep(150,300);
            Mouse.click(1);
            sleep(1500,2000);           
            	
            
            
            String[] options = NPCChat.getOptions();
				if (options.length > 0)
					for (int i = 0; i < options.length; i++)
						if (options[i].contains("Repair")){
							NPCChat.selectOption("Repair", true);	
				 sleep(1500,2000);   
            	NPCChat.clickContinue(true); 
						}
			}
		
		
		private void WithdrawCos() {
			sleep(150,300);	
			Mouse.moveBox(444, 193, 455, 206);
			sleep(150,300);
			if (Game.isUptext("Cosmic")){
				sleep(150,300);
			Mouse.click(1);
			    }
			}
		
		
		private void WithdrawAst() {
			sleep(150,300);	
			Mouse.moveBox(400, 192, 413, 207);
			sleep(150,300);
			if (Game.isUptext("Astral")){
				sleep(150,300);
			Mouse.click(1);
			    }
			}
			
			
        private void EnterAltar() { 	
        	ScreenModel[] Altar = ScreenModels.findNearest(AltarOut);
        	if (Altar.length > 0){
        		Altar[0].hover(new Point(-5, 5), new Point(-5, 5));
        		sleep(50, 200);
        		if (Timing.waitUptext("Enter", 500)){
        			sleep(150,1000);
        			Mouse.click(1);        	
        			}
        		}  		             
        	}
    
        private void CraftNats() { 	
        	ScreenModel[] NatAltar = ScreenModels.findNearest(AltarIn);
        	if (NatAltar.length > 0){
        		NatAltar[0].hover(new Point(-5, 5), new Point(-5, 5));
        		sleep(50, 200);
        		if (Timing.waitUptext("Altar", 500)){
        			sleep(150,1000);
        			Mouse.click(1);        			
        		}
        	}  		
        }
        
        private void HandelCraftNats() {
        	
        	sleep(3000,4500);
        				CraftNats();
        				sleep(150,1000);
        				FTab(GameTab.TABS.INVENTORY);
        	sleep(150,1000);
        					EGP();
        	sleep(150,500);
        					ESP();
        	sleep(150,500);
        				CraftNats();
        	sleep(150,500);
        					ELP();
        	sleep(150,500);
        					EMP();
        	sleep(150,500);
        				CraftNats();
        	sleep(150,500);
        }
        
        boolean repeat = true;
        
        private void Depositnats() {			
        	Mouse.moveBox(571, 309, 582, 318);
        				sleep(150,300);
        				if (Game.isUptext("Nature")){
        				sleep(150,300);
        				Mouse.click(3);
        				sleep(200,300);
        				Timing.waitChooseOption("All", 150);
        		    }        		        					        
        }
      
        private boolean IsInTz() {
        	ScreenModel[] Bank = ScreenModels.findNearest(Banker);
        	if (Bank.length > 0){
        		return true;
        	}else{
        	return false; 
        			}
        }
        
        private boolean IsInJungle() {
        	ScreenModel[] Jun = ScreenModels.findNearest(Jungle);
        	if (Jun.length > 0){
        		return true;
        	}else{
        	return false; 
        			}
        }
        
        private boolean IsGPDeg() {
        	InventoryItem[] GPdeg = Inventory.find(GPDegID);
	    	if (GPdeg.length > 0){	    			
	    		return true;	    		
	    		}else { 
	    		return false;
	    	}
	  	}
        
        
        private boolean IsNextToAlt() {
        	ScreenModel[] Altar = ScreenModels.findNearest(AltarOut);
        	if (Altar.length > 0) {
        		return true;
        	}else { return false;
        	}
        }
        
        private void mainloop() {    
        	if (!IsInTz()) {
        		TeleToBank();
        	}
        	sleep(200,300);
	
  			while(!Banking.isBankScreenOpen()) {
  				OpenTzBank();
  			}
        				       				        				

			if (Banking.isBankScreenOpen()) {
				HandleBank();
				sleep(150,300);
				
			}
					if (IsGPDeg()) {
						HandleRepair();
						sleep(150,300);
						OpenTzBank();
						WithdrawESS();
					}
					
	
				sleep(150,450);
				TeleToJungle();
				sleep(3000,4500);
						
			
			if (IsInJungle()) {
				WalkToAltar();
				sleep(3000,4500);
			}
			
			if (!IsNextToAlt()) {
				TeleToJungle();
				sleep(3000,4500);
				WalkToAltar();				
			}

					EnterAltar();
				sleep(3000,4500);				
					HandelCraftNats();
				sleep(3000,4500);
			
			if (!IsInTz()) {
    			TeleToBank();
    		}
				sleep(3000,4500);
			if(!Banking.isBankScreenOpen()){
			OpenTzBank();
					}							
				sleep(3000,4500);
					Depositnats();
				sleep(3000,4500);
        	}
        

             
		@Override
    public void run() {	
			Mouse.setSpeed(200);
					mainloop();			
						}
		}