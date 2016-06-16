package scripts;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

@ScriptManifest (authors = { "Neurology" }, name = "NeuroFighter", description = "The Most Efficient Combat Bot. Ever.", website = "http://www.powerbot.org/community/topic/843082-free-carnagefighter-universal-npc-and-food-support/", version = 1.1, vip = false)
public class AutoFighter extends ActiveScript implements PaintListener{

	private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }

    private final Color color1 = new Color(255, 255, 255);

    private final Font font1 = new Font("Arial", 1, 14);

    private final Image img1 = getImage("http://carnagescriptz.netau.net/scripts/Images/Fighter_Paint.png");
    
    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        realHp = Integer.parseInt(HP.getText());
        strLvl = Skills.getRealLevel(Skills.STRENGTH);
        defLvl = Skills.getRealLevel(Skills.DEFENSE);
        atkLvl = Skills.getRealLevel(Skills.ATTACK);
        HPLvl = Skills.getRealLevel(Skills.CONSTITUTION);
        gainedStr = Skills.getRealLevel(Skills.STRENGTH) -startStr;
        gainedAtk = Skills.getRealLevel(Skills.ATTACK) -startAtk;
        gainedDef = Skills.getRealLevel(Skills.DEFENSE) -startDef;
        gainedHP = Skills.getRealLevel(Skills.CONSTITUTION) -startHP;
        totalGainedLevels = gainedStr + gainedAtk + gainedDef + gainedHP;
        g.drawImage(img1, 0, 0, null);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("  " +runTime.toElapsedString(), 228, 422);
        g.drawString("  " +monsterName, 228, 451);
        g.drawString("  " +attackStyle, 227, 480);
        g.drawString("  " +totalGainedLevels, 228, 510);
        g.drawString("  " +strLvl +"(" +gainedStr +")", 446, 422);
        g.drawString("  " +defLvl +"(" +gainedDef +")", 446, 451);
        g.drawString("  " +atkLvl +"(" +gainedAtk +")", 446, 480);
        g.drawString("  " +HPLvl +"(" +gainedHP +")", 446, 510);
        g.drawLine(Mouse.getLocation().x - 3000, Mouse.getLocation().y,
        		Mouse.getLocation().x + 3000, Mouse.getLocation().y);
        		g.drawLine(Mouse.getLocation

        		().x, Mouse.getLocation().y - 3000,
        						

        		Mouse.getLocation().x, Mouse.getLocation().y + 3000);
    }
	
    private final Timer runTime = new Timer(0);
    
    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	public final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			jobsCollection.add(job);
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}
    
    private boolean check = false;
    private boolean prayers = false;
    private boolean usePrayers = false;
    private boolean protectMeele = false;
    private boolean protectRanged = false;
    private boolean protectMagic = false;
    private boolean changeAttackStyle;
    private boolean guiIsClosed = false;
    private boolean noEat;
    
    private final static WidgetChild HP = Widgets.get(748, 8);
    
    private int realHp;
    private int strLvl = 0;
    private int defLvl = 0;
    private int atkLvl = 0;
    private int HPLvl = 0;
    private int gainedStr = 0;
    private int gainedAtk = 0;
    private int gainedDef = 0;
    private int gainedHP = 0;
    private int totalGainedLevels = 0;
    private static int eatPercent;
    private int foodId;
    private int startStr;
    private int startAtk;
    private int startDef;
    private int startHP;
    private static int hpRaw;
    
    private String monsterName = "Not set yet...";
    private String attackStyle = "Not set yet...";
    
    @Override
    public void onStart(){
    	if(!Game.isLoggedIn()){
    		log.severe("[NeuroFighter] You are not logged in!! Please log in and then start the script. :]");
    		stop();
    	}
    	Task.sleep(10);
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    new CarnageFighterOption().setVisible(true);
            }
    });
    	hpRaw = Integer.parseInt(HP.getText());
    	startStr = Skills.getRealLevel(Skills.STRENGTH);
    	startAtk = Skills.getRealLevel(Skills.ATTACK);
    	startDef = Skills.getRealLevel(Skills.DEFENSE);
    	startHP = Skills.getRealLevel(Skills.CONSTITUTION);
    	provide(new Attack());
    	provide(new Eat());
    	provide(new Prayers());
    }
    
    public static int getHpPercent(){
    	int percent = (hpRaw * eatPercent) / 100;
    	return percent;
    }
    
    private class Attack extends Node{
    	@Override
		public boolean activate() {
    		return guiIsClosed && !usePrayers && realHp > AutoFighter.getHpPercent();
		}
		
		@Override
		public void execute() {
			if(Settings.get(172) == 1){
				final WidgetChild autoRetaliateButton = Widgets.get(884, 12);
				Tabs.ATTACK.open();
				Task.sleep(200);
				autoRetaliateButton.click(true);
				Task.sleep(300);
			}
			if(Players.getLocal().getHpPercent() < eatPercent){
				usePrayers = false;
			}
			if(attackStyle.equals("Strength (Aggressive)")){
				if(changeAttackStyle){
					final WidgetChild strengthClick = Widgets.get(884, 8);
					final WidgetChild strengthClick2 = Widgets.get(884, 21);
					Tabs.ATTACK.open();
					if(strengthClick.validate()){
						strengthClick.click(true);
						Tabs.INVENTORY.open();
					}
					if(strengthClick2.validate()){
						strengthClick.click(true);
						Tabs.INVENTORY.open();
					}
				}
			}else if(attackStyle.equals("Defensive (Block)")){
				if(changeAttackStyle){
					final WidgetChild defenseClick = Widgets.get(884, 10);
					final WidgetChild defenseClick2 = Widgets.get(884, 15);
					Tabs.ATTACK.open();
					if(defenseClick.validate()){
						defenseClick.click(true);
						Tabs.INVENTORY.open();
					}
					if(defenseClick2.validate()){
						defenseClick.click(true);
						Tabs.INVENTORY.open();
					}
				}
			}else if(attackStyle.equals("Attack  (Accurate)")){
				if(changeAttackStyle){
					final WidgetChild attackClick = Widgets.get(884, 7);
					final WidgetChild attackClick2 = Widgets.get(884, 7);
					Tabs.ATTACK.open();
					if(attackClick.validate()){
						attackClick.click(true);
						Tabs.INVENTORY.open();
					}
					if(attackClick2.validate()){
						attackClick.click(true);
						Tabs.INVENTORY.open();
					}
				}
			}
			if(Inventory.getCount(foodId) == 0 && noEat){
				int endHpLvl = Skills.getRealLevel(Skills.CONSTITUTION);
				int endStrLvl = Skills.getRealLevel(Skills.STRENGTH);
				int endDefLvl = Skills.getRealLevel(Skills.DEFENSE);
				int endAtkLvl = Skills.getRealLevel(Skills.ATTACK);
				log.severe("[NeuroFighter] Out of Food, the script will now stop.");
				log.info("|-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-|");
				log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				log.info("Your Levels are:");
				log.info("Constitution: " +endHpLvl);
				log.info("Strength: " +endStrLvl);
				log.info("Defense: " +endDefLvl);
				log.info("Attack: " +endAtkLvl);
				log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				log.info("|-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-|");
				if(protectMeele){
					Tabs.PRAYER.open();
					final WidgetChild meeleClick = Widgets.get(271).getChild(7).getChild(19);
					if(meeleClick.validate()){
						meeleClick.click(true);
					}
					Tabs.INVENTORY.open();
				}
				if(protectRanged){
					Tabs.PRAYER.open();
					final WidgetChild rangedClick = Widgets.get(271).getChild(7).getChild(18);
					if(rangedClick.validate()){
						rangedClick.click(true);
					}
					Tabs.INVENTORY.open();
				}
				if(protectMagic){
					Tabs.PRAYER.open();
					final WidgetChild magicClick = Widgets.get(271).getChild(7).getChild(17);
					if(magicClick.validate()){
						magicClick.click(true);
					}
					Tabs.INVENTORY.open();
				}
				Game.logout(true);
				if(Game.isLoggedIn()){
					Task.sleep(50);
				}else{
					stop();
				}
			}
			if (!Players.getLocal().isInCombat()){
				NPC monster = NPCs.getNearest(monsterName);
				if(monster.isOnScreen()){
					if(!monster.isInCombat() & !Players.getLocal().isInCombat()){
						monster.interact("Attack");
						Task.sleep(2500);
					}
				}else if(!monster.isOnScreen()){
					Camera.setPitch(75);
				}
			}
		}
    }
    
    private class Eat extends Node{

		@Override
		public boolean activate() {
			return guiIsClosed && !usePrayers && realHp < AutoFighter.getHpPercent() ;
		}

		@Override
		public void execute() {
			log.severe("reached eat");
			final Item food = Inventory.getItem(foodId);
			if(!noEat){
				log.severe("reached eating");
				food.getWidgetChild().interact("Eat");
				Task.sleep(3500);
			}
			if (check && noEat){
				log.severe("reached check");
				if (Inventory.getCount(foodId) == 0){
					Game.logout(true);
				}
			}	
		}
    }
    
    private class Prayers extends Node{

		@Override
		public boolean activate() {
			return guiIsClosed && prayers && usePrayers;
		}

		@Override
		public void execute() {
			if(prayers){
				if(protectMeele){
					Tabs.PRAYER.open();
					final WidgetChild meeleClick = Widgets.get(271).getChild(9).getChild(13);
					if(meeleClick.validate()){
						meeleClick.click(true);
					}
					Tabs.INVENTORY.open();
					usePrayers = false;
				}
				if(protectRanged){
					Tabs.PRAYER.open();
					final WidgetChild rangedClick = Widgets.get(271).getChild(9).getChild(12);
					if(rangedClick.validate()){
						rangedClick.click(true);
					}
					Tabs.INVENTORY.open();
					usePrayers = false;
				}
				if(protectMagic){
					Tabs.PRAYER.open();
					final WidgetChild magicClick = Widgets.get(271).getChild(9).getChild(11);
					if(magicClick.validate()){
						magicClick.click(true);
					}
					Tabs.INVENTORY.open();
					usePrayers = false;
				}
			}
		}
    }
    
	@Override
	public int loop() {
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		}
		return Random.nextInt(10, 50);
	}
	
	public class CarnageFighterOption extends javax.swing.JFrame {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
	     * Creates new form CarnageFighterOption
	     */
	    public CarnageFighterOption() {
	        initComponents();
	    }

	    /**
	     * This method is called from within the constructor to initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is always
	     * regenerated by the Form Editor.
	     */
	    @SuppressWarnings("unchecked")
	    private void initComponents() {

	        jDialog1 = new javax.swing.JDialog();
	        jLabel3 = new javax.swing.JLabel();
	        jLabel4 = new javax.swing.JLabel();
	        jLabel5 = new javax.swing.JLabel();
	        jLabel6 = new javax.swing.JLabel();
	        jLabel7 = new javax.swing.JLabel();
	        jLabel8 = new javax.swing.JLabel();
	        jLabel9 = new javax.swing.JLabel();
	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        jButton1 = new javax.swing.JButton();
	        jButton2 = new javax.swing.JButton();
	        jLabel10 = new javax.swing.JLabel();
	        jLabel11 = new javax.swing.JLabel();
	        jTextField1 = new javax.swing.JTextField();
	        jTextField2 = new javax.swing.JTextField();
	        jSlider1 = new javax.swing.JSlider();
	        jLabel12 = new javax.swing.JLabel();
	        jPanel1 = new javax.swing.JPanel();
	        jCheckBox1 = new javax.swing.JCheckBox();
	        jRadioButton1 = new javax.swing.JRadioButton();
	        jRadioButton2 = new javax.swing.JRadioButton();
	        jRadioButton3 = new javax.swing.JRadioButton();
	        jRadioButton4 = new javax.swing.JRadioButton();
	        jRadioButton5 = new javax.swing.JRadioButton();
	        jRadioButton6 = new javax.swing.JRadioButton();
	        jLabel13 = new javax.swing.JLabel();

	        jDialog1.setEnabled(false);

	        jLabel3.setText("Hello there, you're probably here because you do not know the ID of your food type. Find out your self by:");

	        jLabel4.setText("1. Open RSBot in DEV mode.");

	        jLabel5.setText("2. Log into Runescape.");

	        jLabel6.setText("3. Have your food ready in your Inventory.");

	        jLabel7.setText("4. Press the Settings icon, top left in RSBot.");

	        jLabel8.setText("5. Open View --> Inventory.");

	        jLabel9.setText("6. The green number above your food is the ID.");

	        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
	        jDialog1.getContentPane().setLayout(jDialog1Layout);
	        jDialog1Layout.setHorizontalGroup(
	            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jDialog1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel3)
	                    .addComponent(jLabel4)
	                    .addComponent(jLabel5)
	                    .addComponent(jLabel6)
	                    .addComponent(jLabel7)
	                    .addComponent(jLabel8)
	                    .addComponent(jLabel9))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        jDialog1Layout.setVerticalGroup(
	            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jDialog1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel3)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel4)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel5)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel6)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel7)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel8)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel9)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );

	        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
	        jLabel1.setText("Welcome To NeuroFighter v1.1");

	        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
	        jLabel2.setText("Settings:");

	        jButton1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
	        jButton1.setText("Start Fighting!");
	        jButton1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton1ActionPerformed(evt);
	            }
	        });

	        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
	        jButton2.setText("How to get food IDs");
	        jButton2.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton2ActionPerformed(evt);
	            }
	        });

	        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
	        jLabel10.setText("Monster Name:");

	        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
	        jLabel11.setText("Food ID:");

	        jTextField1.setText("Monster Name goes here :)");

	        jTextField2.setText("Food id goes here :)");

	        jSlider1.setMajorTickSpacing(20);
	        jSlider1.setMinorTickSpacing(5);
	        jSlider1.setPaintLabels(true);
	        jSlider1.setPaintTicks(true);
	        jSlider1.setValue(80);

	        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
	        jLabel12.setText("Eat Percent:");

	        jCheckBox1.setText("Use Prayers");

	        jRadioButton1.setText("Protect from Melee");
	        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButton1ActionPerformed(evt);
	            }
	        });

	        jRadioButton2.setText("Protect from Ranged");
	        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButton2ActionPerformed(evt);
	            }
	        });

	        jRadioButton3.setText("Protect from Magic");
	        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButton3ActionPerformed(evt);
	            }
	        });

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jCheckBox1)
	                    .addComponent(jRadioButton1)
	                    .addComponent(jRadioButton2)
	                    .addComponent(jRadioButton3))
	                .addGap(0, 93, Short.MAX_VALUE))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addComponent(jCheckBox1)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jRadioButton1)
	                .addGap(18, 18, 18)
	                .addComponent(jRadioButton2)
	                .addGap(18, 18, 18)
	                .addComponent(jRadioButton3)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );

	        jRadioButton4.setText("Strength (Aggressive)");
	        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButton4ActionPerformed(evt);
	            }
	        });

	        jRadioButton5.setText("Attack (Accurate)");
	        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButton5ActionPerformed(evt);
	            }
	        });

	        jRadioButton6.setText("Defense (Block)");
	        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButton6ActionPerformed(evt);
	            }
	        });

	        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
	        jLabel13.setText("Attack Style:");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addGap(0, 0, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addComponent(jButton2)
	                        .addGap(248, 248, 248))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addComponent(jLabel2)
	                        .addGap(283, 283, 283))))
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                    .addContainerGap()
	                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                                .addGroup(layout.createSequentialGroup()
	                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                        .addGroup(layout.createSequentialGroup()
	                                            .addContainerGap()
	                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                                                .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                                    .addComponent(jLabel10)
	                                                    .addGap(18, 18, 18)
	                                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                                    .addComponent(jLabel11)
	                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                                                    .addComponent(jTextField2))))
	                                        .addGroup(layout.createSequentialGroup()
	                                            .addGap(104, 104, 104)
	                                            .addComponent(jLabel12)))
	                                    .addGap(18, 18, 18)
	                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                    .addGap(18, 18, 18)
	                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                        .addComponent(jRadioButton4)
	                                        .addComponent(jRadioButton5)
	                                        .addComponent(jRadioButton6)
	                                        .addComponent(jLabel13))))
	                            .addGroup(layout.createSequentialGroup()
	                                .addGap(149, 149, 149)
	                                .addComponent(jLabel1)))
	                        .addGap(0, 0, Short.MAX_VALUE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                .addContainerGap())))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel1)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jLabel2)
	                .addGap(17, 17, 17)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jLabel10)
	                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addGap(18, 18, 18)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jLabel11)
	                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addGap(11, 11, 11)
	                        .addComponent(jLabel12)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(jLabel13)
	                        .addGap(12, 12, 12)
	                        .addComponent(jRadioButton4)
	                        .addGap(18, 18, 18)
	                        .addComponent(jRadioButton5)
	                        .addGap(18, 18, 18)
	                        .addComponent(jRadioButton6)))
	                .addGap(18, 18, 18)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(jButton2)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jButton1))
	        );

	        pack();
	    }// </editor-fold>

	    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
	        jDialog1.setEnabled(true);
	    }                                        

	    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                              
	       if(jRadioButton5.isSelected()){
		            jRadioButton5.setSelected(false);
		        }
		        if(jRadioButton6.isSelected()){
		            jRadioButton6.setSelected(false);
		        }
	    }                                             

	    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                              
	        if(jRadioButton4.isSelected()){
		            jRadioButton4.setSelected(false);
		        }
		        if(jRadioButton6.isSelected()){
		            jRadioButton6.setSelected(false);
		        }
	    }                                             

	    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                              
	        if(jRadioButton4.isSelected()){
		            jRadioButton4.setSelected(false);
		        }
		        if(jRadioButton5.isSelected()){
		            jRadioButton5.setSelected(false);
		        }
	    }                                             

	    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
	        if(jRadioButton4.isSelected()){
		        	attackStyle = "Strength (Aggressive)";
		        }else if (jRadioButton6.isSelected()){
		        	attackStyle = "Defensive (Block)";
		        }else if (jRadioButton5.isSelected()){
		        	attackStyle = "Attack  (Accurate)";
		        }
		        monsterName = jTextField1.getText();
		        prayers = jCheckBox1.isSelected();
		        protectMeele = jRadioButton1.isSelected();
		        protectRanged = jRadioButton2.isSelected();
		        protectMagic = jRadioButton3.isSelected();
		        eatPercent = jSlider1.getValue();
		        check = true;
		        if(!jTextField2.getText().equals("")){
		        	foodId = Integer.parseInt(jTextField2.getText());
		        	noEat = false;
		        }else if(jTextField2.getText().equals("")){
		        	noEat = true;
		        }
		        if(!jCheckBox1.isSelected()){
		        	usePrayers = false;
		        }else if(jCheckBox1.isSelected()){
		        	usePrayers = true;
		        }
		        guiIsClosed = true;
		        dispose();
	    }                                        

	    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
	        if(jRadioButton2.isSelected()){
		            jRadioButton2.setSelected(false);
		        }
		        if(jRadioButton3.isSelected()){
		            jRadioButton3.setSelected(false);
		        }
	    }                                             

	    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                              
	        if(jRadioButton1.isSelected()){
		            jRadioButton1.setSelected(false);
		        }
		        if(jRadioButton3.isSelected()){
		            jRadioButton3.setSelected(false);
		        }
	    }                                             

	    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                              
	        if(jRadioButton1.isSelected()){
		            jRadioButton1.setSelected(false);
		        }
		        if(jRadioButton2.isSelected()){
		            jRadioButton2.setSelected(false);
		        }
	    }

	

	    /**
	     * @param args the command line arguments
	     */
	    public void main(String args[]) {
	        /* Set the Nimbus look and feel */
	        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
	         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	         */
	        try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(CarnageFighterOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(CarnageFighterOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(CarnageFighterOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(CarnageFighterOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
	        //</editor-fold>

	        /* Create and display the form */
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new CarnageFighterOption().setVisible(true);
	            }
	        });
	    }
	    // Variables declaration - do not modify
	    private javax.swing.JButton jButton1;
	    private javax.swing.JButton jButton2;
	    private javax.swing.JCheckBox jCheckBox1;
	    private javax.swing.JDialog jDialog1;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel10;
	    private javax.swing.JLabel jLabel11;
	    private javax.swing.JLabel jLabel12;
	    private javax.swing.JLabel jLabel13;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel jLabel5;
	    private javax.swing.JLabel jLabel6;
	    private javax.swing.JLabel jLabel7;
	    private javax.swing.JLabel jLabel8;
	    private javax.swing.JLabel jLabel9;
	    private javax.swing.JPanel jPanel1;
	    private javax.swing.JPanel jPanel2;
	    private javax.swing.JRadioButton jRadioButton1;
	    private javax.swing.JRadioButton jRadioButton2;
	    private javax.swing.JRadioButton jRadioButton3;
	    private javax.swing.JRadioButton jRadioButton4;
	    private javax.swing.JRadioButton jRadioButton5;
	    private javax.swing.JRadioButton jRadioButton6;
	    private javax.swing.JSlider jSlider1;
	    private javax.swing.JTextField jTextField1;
	    private javax.swing.JTextField jTextField2;
	    // End of variables declaration
	}
}