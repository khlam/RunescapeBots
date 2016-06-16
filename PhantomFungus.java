package scripts;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import org.tribot.api.Camera;
import org.tribot.api.Constants;
import org.tribot.api.DynamicClicking;
import org.tribot.api.Game;
import org.tribot.api.GameTab;
import org.tribot.api.Login;
import org.tribot.api.Inventory;
import org.tribot.api.NPCChat;
import org.tribot.api.Player;
import org.tribot.api.ScreenModels;
import org.tribot.api.Textures;
import org.tribot.api.Timing;
import org.tribot.api.GameTab.TABS;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.ScreenModel;
import org.tribot.api.types.Texture;
import org.tribot.api.types.generic.CustomRet_0P;
import org.tribot.api.util.Restarter;
import org.tribot.api.EGW;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;


@ScriptManifest(authors = { "Phantom866" }, category = "SuperSecretScripts", name = "PhantomFungus" ,description =
"<html>"+
"<head>"+
"       <title>PHANTOM FUNGUS</title>"+
"</head>"+
"<body bgcolor=#cccccc>"+
"       <center><h1><font face=Impact size=25 color=purple >PHANTOM FUNGUS</font></h1></center>"+
"<center><p font face=Impact color=red size=16>"+
"				Not for public use!</p></center>"+
"               ACTION BAR SETUP:"+
"<p font face=Cambri size=12>"+
"               1:Ardy Cape"+
"<p font face=Cambri size=12>"+
"               2:Silver Sickle(b)"+
"<p font face=Cambri size=12>"+
"               3:Tokkul-Zo Ring</p>"+
"<p></p>"+
"               Enter Password&nbsp;&nbsp;<input maxlength=\"6\" name=\"pass\" type=\"text\" value=\"000000\" /></p>"+                  
"       <p font face=Cambri size=12>"+
"</body>"+
"</html>")


public class PhantomFungus extends Script implements Arguments {
	private final long[]
	Banker = {2662940447L,1215621947L},
 	FRing= {2281252820L},
 	FPlant= {3125270072L},
	DepoBox = {3077966565L},
	Log1 = {4095370234L},
	Altar = {2924055697L},
	AltarDecal = {597858809L},
	FairRing = {2281252820L},
	WrongPlace = {261812893L, 3407346170L, 1928202292L},
	SRing = {2489779212L};
	
	int password = 0;
	
	final long[] FungLog = {731439820L, 1488564670L, 3159124306L};
	
	final Point middleMS = new Point(261, 217);
	private final int
            openTexture = 36897,
			FairyTexture = 17275,
			logintexture = 17013;
	@Override
	public void passArguments(HashMap<String, String> m) {
		password = Integer.parseInt(m.get("pass"));
	}   
	  		

	private void TeleToBank() {
		while(!IsInTz()) {
			Keyboard.typeString("3");
			sleep(600,900);
		}
			}
			
	
	private void WalkToRing() {		
		Point[] path  = {new Point (4619,5136), new Point (4623,5144)};
		EGW.walkPath(path);  
			}
    	
    		
 
	private void WalkToLogs() {
			Point[] path  = {new Point (3453, 3428), new Point (3442, 3425), new Point (3426, 3424), new Point (3416, 3413), new Point(3406, 3407)};
				EGW.walkPath(path);	 
		}
	
	private void OpenRing() {
			ScreenModel[] ring = ScreenModels.findNearest(FairRing);
    	if (ring.length > 0){
        		ring[0].hover(new Point(0,3), new Point(0, 3));
    		if (Game.isUptext("Use")) {
    			Mouse.click(1);    	
    			sleep(600, 800);
    			}
    		}
    	}
    			
	private void ChooseFTele() { 
			if (seetext()) {
				sleep(100,150);
				Mouse.moveBox(25, 203, 56,236);
				sleep(100,150);
		    	if (Game.isUptext("anti")) {
		    		sleep(100,150);
		    	Mouse.click(1);
		    	}
		    	
		    	
		    	sleep(700, 900);	

		    	Mouse.moveBox(260, 157, 277, 176);
		    	sleep(100,150);
		    	if (Game.isUptext("clockwise")) {
		    		sleep(100,150);
		    	Mouse.click(1);
		    	sleep(100,150);
		    	Mouse.click(1);
		    	}
		    	
		    	sleep(100,150);
		    	
		    	Mouse.moveBox(412, 158, 435, 170);
		    	sleep(100,150);
		    	if (Game.isUptext("clockwise")) {
		    		sleep(100,150);
		    	Mouse.click(1);
		    	sleep(100,150);
		    	Mouse.click(1);
		    	
		    	sleep(100,150);
		    	
		    	Mouse.moveBox(255, 316, 322, 348);
		    	sleep(100,150);
		    	if (Game.isUptext("Teleport")) {
		    		sleep(100,150);
		    	Mouse.click(1);
	    	
		    	sleep(3500,4000);		    		
		    		}
		    	}
			}
		}
	

	public boolean IsInTz(){	
		ScreenModel[] Plant = ScreenModels.findNearest(Banker);
    	if (Plant.length > 0){
    		FTab(GameTab.TABS.EMOTES);
    		sleep(140,200);
    		FTab(GameTab.TABS.EMOTES);
    		return true;
    	}else{
    	return false; 
    	}
	}
	
    
    public boolean IsInFairy() {
    	ScreenModel[] Plant = ScreenModels.findNearest(FPlant);
    	if (Plant.length > 0){
    		return true;
    	}else{
    	return false; 
    			}
    }
	
    
    public boolean seetext(){
    	Texture[] open = Textures.find(FairyTexture);
        if(open.length > 0){
        	return true;
        }
        return false;
    }
    
    public boolean IsInSwamp() {
    	ScreenModel[] plant = ScreenModels.findNearest(SRing);
        	if(plant.length > 0) {
        		return true;
        	}
	return false;
    			}

    
    public boolean clickontile() {
    	if(!InSpot()) {
    		ScreenModel[] Log = ScreenModels.findNearest(Log1);
    	if (Log.length > 0){
        		Log[0].hover(new Point(0,26), new Point(0, 26));
        		Mouse.click(1);
     	
        		if(IsAtAltar()) {
        			HandelAltar();
        		
        		return true; 
        		}
    		}	
    	}
    	return false;
    }
	
    
    public boolean AreThereFungus() {
      	ScreenModel[] depo = ScreenModels.findNearest(FungLog);
    	if (depo.length > 0){
    		return true;
    		}
    		return false; 
    	}

    
    public boolean CheckPrayer() {
    	int Prayer = Game.getPrayerPoints();
    	if (Prayer == 0) {
    		return true;
    			}
    		if (Prayer > 0) {
    			return false;
    		}
    		return false;
    	}


	public boolean checkinv(){
		if(GameTab.getOpen() != TABS.INVENTORY){
			GameTab.open(TABS.INVENTORY);
		}
		   if (Inventory.isFull()){
			   return true;
	   }else {
		  return false;  
	   		} 
	   }

    private void ClickFungus() {
        DynamicClicking.clickScreenModel(
                new CustomRet_0P<ScreenModel>() {
                    @Override
                    public ScreenModel ret() {
                        ScreenModel[] m = ScreenModels.findNearest(FungLog);
                        if (m.length < 1)
                            return null;      
                        for (ScreenModel m1 : m) {
                                return m1;
                        }
                        return null;
                    }
                }, 1);
    		}

    
    private void HandelFungus() {
    	if(CheckPrayer()) {
    		if(!IsAtAltar()) {
    			HandelAltar();	
    		}   		
    	}
    	
    	while(!AreThereFungus()) {
    		if(!InSpot()) {	
    			while(Player.isMoving()) {
    				sleep(100,150);
    			}
    		clickontile();
    			sleep(400,500);
    		}
    		
    		if(CheckPrayer()) {
    			if(!IsAtAltar()) {
    				HandelAltar();
    			}	
	    	}
    		
    		if(Player.isMoving()) {
    			sleep(100,150);
    		}
    		if(InSpot()) {
    			if(!AreThereFungus()) {
    				sleep(1000,1200);
    				if(InSpot()) {
    					if(!Player.isMoving()) {
    						Keyboard.typeString("2");	
    				sleep(400,500);	
    							}
    						}
    					}
    				}
    			}
    	while(AreThereFungus()) {    		
    	if(!checkinv()) {
    		ClickFungus();
				sleep(100,300);	
    	}else {
    		if(checkinv()) {
    			HandelBank();
    				}
    			}
    		}
    	}
    				

    							
    
    private void HandelBank() {  	
    	clickontile();
    if(IsAtLogs()) {
    	Mouse.moveBox(570, 269, 585, 286);
    	sleep(300,450);
    	if (Game.isUptext("Mort")) {
    		sleep(300,450);
    Mouse.click(1);
    	}
    	if(checkinv()) {
    		 DynamicClicking.clickScreenModel(
                new CustomRet_0P<ScreenModel>() {
                    @Override
                    public ScreenModel ret() {
                        ScreenModel[] m = ScreenModels.findNearest(DepoBox);
                        if (m.length < 1)
                            return null;      
                        for (ScreenModel m1 : m) {
                                return m1;
                        }
                        return null;
                    }
                }, 1);
        				sleep(4000,4500);

        
						String name = NPCChat.getName();
						NPCChat.getName();
		    			if(name.contains("How many would you like to deposit?")) {
		    			sleep(100,150);

		    			Keyboard.typeString("4");
		    			sleep(100,150);

		    				}
		    			}
    				}
    if(!checkinv()) {
    	 if(!InSpot()) {
    	EGW.walkTo(3406, 3407);
    	sleep(1000,1500);
    } 	
    }
   		
		    		}
		    			    
    
    public boolean IsAtLogs() {
    	ScreenModel[] flog = ScreenModels.findNearest(DepoBox);
    	if (flog.length > 0){

    		if(Player.isMoving()) {
    			sleep(150,200);
    			}  		
    			return true;
    		}
    			return false;
    		}
    	
    
    public boolean IsAtAltar() {
    	ScreenModel[] altar = ScreenModels.findNearest(AltarDecal);
        if(altar.length > 0){
    		return true;	
    		}else {
    			return false;
    		}
    }
    
    public boolean WrongPlace() {
    	ScreenModel[] Place = ScreenModels.findNearest(WrongPlace);
        if(Place.length > 0){
        	FTab(GameTab.TABS.EMOTES);
    		sleep(140,200);
    		FTab(GameTab.TABS.EMOTES);
    		return true;	
    		}else {
    			return false;
    		}
    }
    
    private void HandelAltar() {
    	
    	if(!IsAtAltar()) {
    		Keyboard.typeString("1");
    		   while(!IsAtAltar()) {
    			   sleep(150,200);
    		   }
    	}else {
    		if(!InAltarArea()) {	
    			Keyboard.typeString("1");
                	  EGW.walkTo(2606,3210);
                  }
    				 
    				 
    				 DynamicClicking.clickScreenModel(
                    new CustomRet_0P<ScreenModel>() {
                        @Override
                        public ScreenModel ret() {
                            ScreenModel[] m = ScreenModels.findNearest(Altar);
                            if (m.length < 1)
                                return null;      
                            for (ScreenModel m1 : m) {
                                    return m1;
                            }
                            return null;
                        }
                    }, 1);
    				 sleep(400,500);
        		}
	    	    			if(!CheckPrayer()) {
	    	    				TeleToBank();
	    	    				if(!IsInTz()) {
	    	    					sleep(100,150);
	    	    				}
	    	    			}
	    	    		
    if(!CheckPrayer()) {
			TeleToBank();
			if(!IsInTz()) {
				sleep(100,150);
			}
		}
    }
    
    

	    	    	
    	

    	
    private boolean FTab(TABS Tab){
    	char FKey;
        switch(Tab){
                case EMOTES: FKey = (char)KeyEvent.VK_ESCAPE; break;
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
    
private void DoFungus() {
	
	while(!CheckPrayer()) {
		if(CheckArea()) {
			if(InSpot()) {
			if(!checkinv()){
					HandelFungus();	
		}else {
			if(checkinv()) {
				HandelBank();
				}
			}		
		}else {
			if(!InSpot()) {
         			sleep(500,600);
         			while(Player.isMoving()) {
         				sleep(100,150);
         			}
         			clickontile();
         		}
				
			}
		}else {
			EGW.walkTo(3406, 3407);
			while(Player.isMoving()) {
				sleep(100,150);
			}
 			clickontile();
		}
		
		}
	}

	

    
    private void mainloop() {

    	while(WrongPlace()) {
    		TeleToBank();
    	}
    	
    	if(IsInTz()) {
	       WalkToRing();
	        		DynamicClicking.clickScreenModel(
	                    new CustomRet_0P<ScreenModel>() {
	                         @Override
	    		                    public ScreenModel ret() {
	      	                        ScreenModel[] m = ScreenModels.findNearest(FRing);
	      		                        if (m.length < 1)
	     	                            return null;      
	        	                        for (ScreenModel m1 : m) {
	       	                            return m1;
	      	                        }
	                            return null;
	      	                    }
	       	                }, 1);
	      				}
	        			
    		while(IsInFairy()) {
    			if(!seetext()) {
    				OpenRing();
    			}else {
    				if(seetext()){
    					ChooseFTele();
    					}
    				}
    			}
    		
    		while(WrongPlace()) {
        		TeleToBank();
        	}
	        		
	        while(IsAtAltar()) {	
	        	HandelAltar();
	       	}
	        			
	        	if(IsInSwamp()) {
	        		while(!CanMove()) {
	        			sleep(300,450);
	        			}
	             	WalkToLogs();
	             	while(Player.isMoving()) {
	           			sleep(150,200);
	             		}
   					}
	        			
	        		while(IsAtLogs()) {	
	        			DoFungus();
	        				if(CheckPrayer()) {
	        				HandelAltar();
	        				}
	        			}		
	  	}

        		
    public boolean inArea(Point[] area, Point point){
        return (point.x > area[0].x && point.x < area[area.length-1].x) && (point.y < area[0].y && point.y > area[area.length-1].y);
	}

    public boolean CheckArea() {
    	Point[] Pen = {new Point( 3393,3420),new Point( 3414, 3403)}; 
		if (inArea(Pen,EGW.getPosition())){
			return true;
		}else{
			return false;
		}
    }
    
    
	public boolean InSpot(){	
		Point[] Pen = {new Point( 3405, 3408),new Point( 3407, 3405)}; 
		if (inArea(Pen,EGW.getPosition())){
			return true;
		}else{
			return false;
		}
	}

private Point ptInRect(Rectangle r){
        return new Point(r.x + Constants.RANDOM.nextInt(r.width), r.y + Constants.RANDOM.nextInt(r.height));
}
    
public boolean CanMove() {
    Texture[] open = Textures.find(openTexture);
    if(open.length > 0){
    	return false;
    }
    return true;
}

private boolean openActionBar(){
        Texture[] open = Textures.find(openTexture);
        if(open.length > 0){
                for(Texture t : open){
                        Rectangle r = new Rectangle(t.x, t.y, t.width, t.height);
                        Point p = ptInRect(r);
                        Mouse.move(p);
                        if(Timing.waitUptext("Expand", 500)){
                                Mouse.click(1);
                                sleep(500, 1000);
                                return true;
                        }    
                }
        }
        return false;
}              
   		 
private void setup() {
	 Mouse.setSpeed(165);
     Camera.setRotation(117);
     Camera.setPitch(true);
    	 openActionBar();
    		if(GameTab.getOpen() != TABS.INVENTORY){
    			GameTab.open(TABS.INVENTORY);
    		}
     }

public boolean loop() {
	Texture[] login = Textures.find(logintexture);
    if(login.length > 0){
    	return false;
    }
    return true;
}

public boolean security() {
	if(password == 259014) {
		println("Authorized. Script Starting");
		return true;
	}else {
		println("Wrong Password, script will now terminate.");
		return false;
	}
}

public boolean InAltarArea() {
	Point[] Pen = {new Point( 2600, 3218),new Point( 2609, 3207)}; 
	if (inArea(Pen,EGW.getPosition())){
		return true;
	}else{
		return false;
	}
}

        @Override
    public void run() {
        		if(security()) {
        			setup();
            			
            		while (loop()) {
            			if(!Login.login() && !Login.login()){
                Restarter.restart("PhantomFungus");
            }		
        			mainloop();
        				}
           	}       		
           }
        }	
		
        	
        	
        
	