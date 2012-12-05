import java.util.EnumSet;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Tominning extends Node {
   @Override
   public boolean activate() {
	   SceneObject rock = SceneEntities.getNearest(NxPowerMiner.IRON_ID);
     return (Inventory.getCount() == 0 && !rock.isOnScreen());    	
   }

   @Override
    public void execute() {
		NxPowerMiner.Status = "Walking to mine";
	   System.out.println("Walking to mine");
	   SceneObject rock = SceneEntities.getNearest(NxPowerMiner.IRON_ID);
	   Walking.walk(rock);
	   Task.sleep(1000,1500);
		}
	

}
	
	





