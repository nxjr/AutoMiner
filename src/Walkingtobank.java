import java.util.EnumSet;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.node.SceneObject;


	public class Walkingtobank extends Node {
		@Override
		public boolean activate() {
			SceneObject booth = SceneEntities.getNearest(NxPowerMiner.depositBox);
			return Inventory.isFull() && booth != null && !booth.isOnScreen(); 
				
     	
		}

		@Override
		public void execute() {
			NxPowerMiner.Status = "Walking to bank";
			Walking.newTilePath(NxPowerMiner.toBank).traverse(
			EnumSet.of(Path.TraversalOption.SPACE_ACTIONS));
			   Task.sleep(1000,1500);
		
				}
			}
		


	