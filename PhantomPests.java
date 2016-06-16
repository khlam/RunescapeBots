package scripts;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import org.tribot.api.Camera;
import org.tribot.api.Constants;
import org.tribot.api.DynamicClicking;
import org.tribot.api.EGW;
import org.tribot.api.Game;
import org.tribot.api.General;
import org.tribot.api.ScreenModels;
import org.tribot.api.TPS;
import org.tribot.api.Textures;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.ScreenModel;
import org.tribot.api.types.Texture;
import org.tribot.api.types.generic.CustomRet_0P;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;


@ScriptManifest(authors = { "Phantom866" }, category = "Minigames", name = "PhantomPests V1.2", description =
"<html>"+
"<head>"+
"       <title>Phantom's Pests V1.2</title>"+
"</head>"+
"<body bgcolor=#cccccc>"+
"       <center><h1><font face=Impact size=25 color=purple >PHANTOM PESTS</font></h1></center>"+
"       <p font face=Cambri size=12>"+
"               Start your character near the novice gangplank</p>"+
"       <p font face=Cambri size=12>"+
"               Enter Mouse Speed&nbsp;&nbsp;<input maxlength=\"3\" name=\"MouseSpeed\" type=\"text\" value=\"170\" /></p>"+                  
"       <p font face=Cambri size=12>"+
"		Special thanks to Emu (Stole your GUI :D) & JJ for his helpfull tuts<p>"+
"</body>"+
"</html>")

public class PhantomPests extends Script{
	final long[] gangplank= {3521208100L};
	final long[] boatsquire= {1549122183L, 457123206L};
	final long[] Pests= {2228551419L, 2292523003L, 2749219472L, 1288612606L, 2744481219L};
	final long[] novicesquire= {2592433466L, 1529869755L};
	final long[] pestknight= {2043405193L};
	
	private final int
    openTexture = 36897;
	
	int mspeed = 170;
	

	public void passArguments(HashMap<String, String> m) {
		mspeed = Integer.parseInt(m.get("MouseSpeed"));
	}
	
	private void WalkDownPlank() { 	
    	ScreenModel[] plank = ScreenModels.findNearest(gangplank);
    	if (plank.length > 0){
    		plank[0].hover(new Point(-5, 5), new Point(-5, 5));
    		sleep(100, 200);
    		if (Game.isUptext("Gangplank")){
    			sleep(100, 200);
    			Mouse.click(1);
    		}
    			sleep(300,500);
    		}
    	}	

	public void WalkToPurp() {
		println("Walking to Purple");
		Point[] pathPurp = {  new Point(251, 203), new Point(217, 235), new Point(193, 264), new Point(163, 267), new Point(152, 278)};
		WalkPath(pathPurp);
	}
	
	public void WalkToYell() {
		println("Walking to Yellow");
		Point[] pathYell = {  new Point(250, 191), new Point(252, 235), new Point(246, 269), new Point(246, 302), new Point(267, 315), new Point(297, 344)};
		WalkPath(pathYell);
	}
	
	public void WalkToBlue() {
		println("Walking to Blue");
		Point[] pathBlue = {  new Point(242, 187), new Point(284, 227), new Point(292, 259), new Point(325, 263), new Point(338, 288)};
		WalkPath(pathBlue);
	}
	
	public void WalkToRed() {
		println("Walking to Red");
		Point[] pathRed  = {new Point (244, 259), new Point (246, 275), new Point ( 246, 341), new Point(238, 300), new Point (205,354)} ;
		WalkPath(pathRed);
	}
	
	public void WalkToCenter() {
		println("Walking to Center");
	}

	
	private boolean WalkPath(Point[] Path){
        int I = 0;
        int H = 0;
        long T = 0;
        double D = 0;
        Point P = null;
        Point MM = new Point(0,0);
        boolean result = false;
        H = Path.length-1;
        T = System.currentTimeMillis()+20000+General.random(5000, 5200);
        while((!result)&&(System.currentTimeMillis()<T)){
            P = TPS.getPosition();
            for(I = H; I>0; I--){
                int MMCX = 0;
				MM.x = MMCX + Path[I].x - P.x;
                int MMCY = 0;
				MM.y = MMCY + Path[I].y - P.y;
                D = General.distanceTo(MM,new Point(MMCX,MMCY));    
                if(D<10)
                	sleep(100,150);
                else
                    if(D<70){
                        TPS.walkTo(Path[I]);
                        /*Needs a Flag Detection*/
                        T = System.currentTimeMillis()+20000+General.random(5000, 5200);
                        break;
                    }
            }
            if(I == H){ 
                result = true;
                return true;
            }
        }
        
        return false;
    }
	
	public boolean IsInGame() {
	ScreenModel[] squire = ScreenModels.findNearest(boatsquire);
    	if (squire.length > 0){
    		sleep(100, 200);
    		return true;
    	}else {
    		return false;
    	}
	}
	
	public boolean Loop() {
		return true;
	}
	
	public boolean OnDock(){	
		Point[] Pen = {new Point( 2656, 2646),new Point( 2660, 2638)}; 
		if (inArea(Pen,EGW.getPosition())){
			return true;
		}else{
			return false;
		}
	}
		
	
	public boolean inArea(Point[] area, Point point){
        return (point.x > area[0].x && point.x < area[area.length-1].x) && (point.y < area[0].y && point.y > area[area.length-1].y);
	}
	
	public boolean InBoat() {
		Point[] Pen = {new Point( 2660, 2643),new Point(2663, 2638)}; 
		if (inArea(Pen,EGW.getPosition())){
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	public void WhichPortal() {	
		switch (General.random(0, 3)) {
	    case 0:

	    WalkToBlue();

	    break;
	    case 1:

	    WalkToPurp();

	    break;
	    case 2:

	    WalkToRed();

	    break;
	    case 3:
	  
	    WalkToYell();

	    break;
		}
	}
		
		    
		    

	
	public void AttackPests() {
		
		 DynamicClicking.clickScreenModel(
	                new CustomRet_0P<ScreenModel>() {
	                    @Override
	                    public ScreenModel ret() {
	                        ScreenModel[] m = ScreenModels.findNearest(Pests);
	                        if (m.length < 1)
	                            return null;      
	                        for (ScreenModel m1 : m) {
	                                return m1;
	                        }
	                        return null;
	                    }
	                }, 1);
    			Keyboard.typeString("1");
                sleep(400,600);
                Keyboard.typeString("2");
                sleep(400,600);
                Keyboard.typeString("3");
                sleep(400,600);
    		}
    	

	
	private Point ptInRect(Rectangle r){
        return new Point(r.x + Constants.RANDOM.nextInt(r.width), r.y + Constants.RANDOM.nextInt(r.height));
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
	Mouse.setSpeed(mspeed);
	 Camera.setPitch(true);
    	 openActionBar();	 
     }
	
	
       
	public void mainloop() {
		while (OnDock()) {
    		WalkDownPlank();           		
		}
    	
    	while (InBoat()) {
    		while(!IsInGame()) {
    		sleep(100,150);
    		}            		
    	}
    	
    	while (IsInGame()) {
    		WhichPortal();
    		while(!OnDock()) {
    			sleep(100,150);
    		AttackPests();
    						}		
    					}
					}
	
	
	@Override
    public void run() {
		setup();
        	while(Loop()) {
        		mainloop();
        					}
						}
					}
        		
        	
		
	