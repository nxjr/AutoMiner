import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
@Manifest(authors = { "Nxjr" }, name = "Powerminer" )
public class NxPowerMiner extends ActiveScript implements PaintListener  {
	
	//gui stuff
	gui g = new gui();
	boolean hide = false; 
	Point p;
	private boolean guiWait = true;
	public static long startTime;
	public int startOres = 0;
	public String version = "v1.0";
	public static String Status = "0";
	public int oreshour = 0;
	Timer runTime = new Timer(0);
	public int expGained = 0;
	public int expHour = 0;
	public boolean bank;
	public boolean drop;
	public String Minned;
	Font font = new Font("Verdana", 0, 12);
	private final static Tile IRON_TILE = new Tile(2692, 3329, 0);
	
	public static int[] depositBox = {34752};
	public static int[] IRON_ID = {2092, 2093};
	public static int[] IRON_ID1 = {440};
	public static int[] GEM = {1623, 1621, 1619, 1617};
	public static Filter<SceneObject> Iron_Filter = new Filter<SceneObject>(){
		@Override
		public boolean accept(SceneObject arg0) {

		return (Calculations.distance(arg0, IRON_TILE) <= 2) && (arg0.getId() == 2092||arg0.getId() == 2093);


		}
	};
	

	
		private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
        private Tree jobContainer = null;

        public synchronized final void provide(final Node... jobs) {
                for (final Node job : jobs) {
                        if(!jobsCollection.contains(job)) {
                                jobsCollection.add(job);
                        }
                }
                jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
        }

        public synchronized final void revoke(final Node... jobs) {
                for (final Node job : jobs) {
                        if(jobsCollection.contains(job)) {
                                jobsCollection.remove(job);
                        }
                }
                jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
        }

        public final void submit(final Job... jobs) {
                for (final Job job : jobs) {
                        getContainer().submit(job);
                }
        }

        public void onStart() {
        		startTime = System.currentTimeMillis();
        		g.setVisible(true);
        		while(guiWait == true) sleep(500);
                provide(new Minning());
                
                

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
        public final static Area AREA_OF_BANK = new Area(new Tile[] {
        		new Tile(2644, 3290, 0), new Tile(2644, 3279, 0),
        		new Tile(2659, 3279, 0), new Tile(2658, 3289, 0), 
        		new Tile(2655,3286, 0),new Tile(2656,3287, 0),new Tile(2655,3287, 0),
        		new Tile(2654,3287, 0),new Tile(2653,3287, 0),new Tile(2652,3287, 0),
        		new Tile(2652,3287, 0),new Tile(2652,3288, 0),new Tile(2652,3289, 0),new Tile(2652,3290, 0)
        });
    	
    	public final static Tile[] toBank = {
    			new Tile(2693, 3330, 0), new Tile(2697, 3327, 0),
    			new Tile(2696, 3322, 0), new Tile(2694, 3317, 0),
    			new Tile(2692, 3312, 0), new Tile(2691, 3307, 0),
    			new Tile(2686, 3306, 0), new Tile(2681, 3305, 0),
    			new Tile(2678, 3301, 0), new Tile(2675, 3296, 0),
    			new Tile(2670, 3297, 0), new Tile(2665, 3298, 0),
    			new Tile(2660, 3300, 0), new Tile(2655, 3300, 0),
    			new Tile(2650, 3298, 0), new Tile(2647, 3293, 0),
    			new Tile(2645, 3288, 0), new Tile(2646, 3283, 0),
    			new Tile(2651, 3282, 0), new Tile(2655, 3285, 0)};
    			
    	public String runTime(long i) {
    		DecimalFormat nf = new DecimalFormat("00");
    		long millis = System.currentTimeMillis() - i;
    		long hours = millis / (1000 * 60 * 60);
    		millis -= hours * (1000 * 60 * 60);
    		long minutes = millis / (1000 * 60);
    		millis -= minutes * (1000 * 60);
    		long seconds = millis / 1000;
    		return nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
    		}
    	public final static Area MinningArea = new Area(new Tile[] {
    			new Tile(2690, 3341, 0), new Tile(2690, 3328, 0),
    			new Tile(2697, 3328, 0), new Tile(2698, 3338, 0)
    	});
    	
    	public final static Tile[] ToMinning = {
    			new Tile(2649, 3284, 0), new Tile(2649, 3294, 0),
    			new Tile(2662, 3302, 0), new Tile(2669, 3304, 0),
    			new Tile(2677, 3305, 0), new Tile(2684, 3306, 0),
    			new Tile(2690, 3306, 0), new Tile(2694, 3317, 0),
    			new Tile(2693, 3324, 0), new Tile(2692, 3330, 0),
    			new Tile(2693, 3332, 0) 
    	};
    	
    

 public static boolean PlayerAtBank() {
 Area area = AREA_OF_BANK;
 return AREA_OF_BANK.contains(Players.getLocal().getLocation());
 }


    public void walkPath(final Tile... path) {
    if (Walking.getEnergy() > Random.nextInt(30, 50)) {
    Walking.setRun(true);
    }
        for (int i = path.length - 1; i >= 0; i--) {
                if (Calculations.distanceTo(path[i]) >= 15) {
                        continue;
                }
                if (Walking.walk(path[i])) {
                        break;
                }
        }

    	}
                	

    	public static boolean PlayeratMinningArea() {
    	Area area = MinningArea;
    	return MinningArea.contains(Players.getLocal().getLocation());
    		}
    	
    	


    	public void depositAllOf(int ItemId) {
        if (Inventory.contains(IRON_ID1)) {
                for (Item i : Inventory.getItems()) {
                        if (i.getId() == ItemId) {
                                i.getWidgetChild().interact("Deposit-All");
                                Task.sleep(1000);
                                break;
                        }
                }
        }
    	}


    		
    


    		     
        //START: Code generated using Enfilade's Easel
        private final Color color1 = new Color(204, 0, 0, 162);
        private final Color color2 = new Color(0, 0, 0);
        private final Color color3 = new Color(255, 255, 255);

        private final BasicStroke stroke1 = new BasicStroke(1);

        private final Font font1 = new Font("Arial", 0, 9);

        public void onRepaint(Graphics g1) {
            Graphics2D g = (Graphics2D)g1;
            g.setColor(color1);
            g.fillRect(551, 256, 185, 125);
            g.setColor(color2);
            g.setStroke(stroke1);
            g.drawRect(551, 256, 185, 125);
            g.setFont(font1);
            g.setColor(color3);
            g.drawString("NxPowerMiner", 617, 267);
            g.drawString("Time ran : " + runTime(startTime), 556, 286);
            g.drawString("Ores minned :" + String.valueOf(startOres), 554, 309);
            g.drawString("Status : " + String.valueOf(Status), 557, 328);
            g.drawString("Version :" + String.valueOf(version), 557, 351);
        }
        //END: Code generated using Enfilade's Easel
        
        
       
class gui extends JFrame {
		public gui() {
			initComponents();
		}

		private void button1ActionPerformed(ActionEvent e) {
			if(checkBox1.isSelected()){
				provide(new Dropping());
				provide(new dropGems());
			}
			if(checkBox2.isSelected()){
				provide(new Banking());
				provide(new Walkingtobank());
				provide(new Tominning());
			}
			guiWait = false;
			this.dispose();
			}
		

		private void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
			// Generated using JFormDesigner Evaluation license - bai pollard
			checkBox1 = new JCheckBox();
			label1 = new JLabel();
			checkBox2 = new JCheckBox();
			button1 = new JButton();

			//======== this ========
			Container contentPane = getContentPane();

			//---- checkBox1 ----
			checkBox1.setText("Enable Dropping (M3D3)");

			//---- label1 ----
			label1.setText("NxPowerMiner");
			label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 6f));

			//---- checkBox2 ----
			checkBox2.setText("Enable Banking");

			//---- button1 ----
			button1.setText("Start script");
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			GroupLayout contentPaneLayout = new GroupLayout(contentPane);
			contentPane.setLayout(contentPaneLayout);
			contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
					.addGroup(contentPaneLayout.createSequentialGroup()
						.addGap(23, 23, 23)
						.addComponent(label1, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
					.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(checkBox1))
					.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(checkBox2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
					.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(button1)
						.addGap(32, 32, 32))
			);
			contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
					.addGroup(contentPaneLayout.createSequentialGroup()
						.addComponent(label1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGap(7, 7, 7)
						.addComponent(checkBox1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(checkBox2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
						.addComponent(button1)
						.addContainerGap())
			);
			pack();
			setLocationRelativeTo(getOwner());
			// JFormDesigner - End of component initialization  //GEN-END:initComponents
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
		// Generated using JFormDesigner Evaluation license - bai pollard
		private JCheckBox checkBox1;
		private JLabel label1;
		private JCheckBox checkBox2;
		private JButton button1;
		// JFormDesigner - End of variables declaration  //GEN-END:variables
	}
}





	
