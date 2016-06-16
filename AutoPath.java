package scripts;
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.api.TPS;
import org.tribot.script.interfaces.Painting;
 
@ScriptManifest(authors = { "Eska" }, category = "Tools", name = "Eska AutoPath", version = 1.0,
                        description = ("Eska AutoPath automaticaly record where you walk and make a path out of it."))
public class AutoPath extends Script implements Painting {
        ArrayList<Point> path= new ArrayList<Point>();
        Point lastPoint = new Point();
        int PointDistance = 25;
        boolean autoPath;
       
        GUI gui;
        private final String Version = "1.0";
    private final Color textAreaColor = new Color(0, 255, 0, 100);      //Green-ish w/ transparency
    private final Color borderColor = new Color(0, 0, 0);                               //Black
    private final Color textColor = new Color(255, 255, 255);                   //White
    private final Color minimapColor = new Color(0, 255, 255);                  //Cyan
    private final BasicStroke stroke1 = new BasicStroke(1);
    private final Font font1 = new Font("Arial", 1, 11);
    private final Font font2 = new Font("Arial", 0, 11);
   
        @Override
         public void onPaint(Graphics g) {
                int x= 20;
                int y= 330;
 
             g.setColor(textAreaColor);
             g.fillRect(x, y, x+180, y-280);
             g.setColor(borderColor);
             ((Graphics2D) g).setStroke(stroke1);
             g.drawRect(x, y, x+180, y-280);
             g.setFont(font1);
             g.setColor(textColor);
             g.drawString("Eska AutoPath V." + Version, x+5, y+15);
             g.setFont(font2);
             g.drawString("# of points in newPath: " + String.valueOf(path.size()), x+5, y+30);
             Point pos= TPS.getPosition();
             g.drawString("TPS position: X: " + pos.x + ", Y: " + pos.y, x+5, y+45);
             g.setColor(minimapColor);
             
             Ellipse2D.Double ellipse1 = new Ellipse2D.Double(550, 59, 152, 152);
               
             Point previousPoint= new Point();
             
             for (int i= 0; i != path.size(); ++i){
                 if (i != 0){
                         previousPoint= TPS.posToMM(path.get(i-1));
                 }
 
                 g.setClip(ellipse1);
                 Point currentPoint= TPS.posToMM(path.get(i));
                 
                 if (currentPoint.distance(new Point(626, 133)) <= 85){
                         g.fillOval(currentPoint.x-4, currentPoint.y-4, 8, 8);
                         
                         if (previousPoint.distance(new Point(626, 133)) <= 110){
                                 g.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);
                         }
                 }       
             }
         g.fillOval(624, 132, 5, 5);
         if (path.size() != 0){
                 Point lastPoint = TPS.posToMM(path.get(path.size()-1));
                 g.drawLine(627, 134, lastPoint.x, lastPoint.y);         
         }
         
             g.setClip(null);
        }
       
        @Override
        public void run() {
                 try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            gui = new GUI();
                            gui.setVisible(true);
                            gui.setTitle("Eska AutoPath - GUI by ExZo");
                        }
                    });
        }
                catch (Exception e) {
                        e.printStackTrace();
            }
       
        while (gui.isVisible()) {
            sleep(50);
 
                        if(autoPath){
                                if(path.size() == 0) {
                                        lastPoint = TPS.getPosition();
                                        gui.setText();
                                }
                               
                                Point curPoint = TPS.getPosition();
                               
                                if(autoPath && lastPoint.distance(curPoint.getX(), curPoint.getY()) >= PointDistance
                                                &&lastPoint.distance(curPoint.getX(), curPoint.getY()) <= 60) {
                                        lastPoint = curPoint;
                                        gui.setText();
                                 }                                     
                        }                  
        }
        }
       
        public class GUI extends JFrame {
                private static final long serialVersionUID = 58625468327622985L;
                private JPanel contentPane;
                final JTextArea textArea = new JTextArea();
 
                public void setText() {
                        path.add(TPS.getPosition());
                        if (textArea.getText().contains("new Point"))
                                textArea.setText(textArea.getText().replaceAll("};",", new Point(" + path.get(path.size()-1).x + ", " + path.get(path.size()-1).y + ")};"));
                        else
                                textArea.setText(textArea.getText().replaceAll("};"," new Point(" + path.get(path.size()-1).x + ", " + path.get(path.size()-1).y + ")};"));
                }
               
                public GUI() {
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        setBounds(100, 100, 400, 361);
                        contentPane = new JPanel();
                        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                        setContentPane(contentPane);
                       
                        JButton btnAddPoint = new JButton("Add Point");
                        btnAddPoint.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent arg0) {        
                                                setText();     
                                }
                        });
                        btnAddPoint.setBounds(13, 5, 110, 23);
                       
                        JButton btnCopyPath = new JButton("Copy Path");
                        btnCopyPath.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                           Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                           clipboard.setContents(new StringSelection(textArea.getText()), new StringSelection(textArea.getText()));
                                           JOptionPane.showMessageDialog(null, "Copied!");
                                }
                        });
                        btnCopyPath.setBounds(133, 5, 110, 23);
                       
                        JButton btnClearPath = new JButton("Clear");
                        btnClearPath.setBounds(253, 5, 110, 23);
                        btnClearPath.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent arg0) {
                                        textArea.setText("Point[] newPath = { };");
                                        path = new ArrayList<Point>();
                                }
                        });
                       
                        textArea.setText("Point[] newPath = { };");
                        textArea.setLineWrap(true);
                        textArea.setWrapStyleWord(true);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPane.setPreferredSize(textArea.getSize());
                        scrollPane.setBounds(15, 46, 348, 211);
                        contentPane.setLayout(null);
                        contentPane.add(btnAddPoint);
                        contentPane.add(btnCopyPath);
                        contentPane.add(btnClearPath);
                        contentPane.add(scrollPane);                   
                        final JCheckBox autoCheckBox = new JCheckBox("Automatically add points to path");
                        autoCheckBox.setBounds(13, 264, 210, 23);
                        contentPane.add(autoCheckBox);
 
                        autoCheckBox.addItemListener(new ItemListener() {
                                public void itemStateChanged(ItemEvent arg0) {
                                        autoPath = autoCheckBox.isSelected();
                                }                      
                        });
                }      
        }
}