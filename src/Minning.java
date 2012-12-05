import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Minning extends Node {
    @Override
    public boolean activate() {
    	return NxPowerMiner.PlayeratMinningArea()
    		
        && Players.getLocal() != null 
    	&& Players.getLocal().getInteracting() == null 
    	&& Players.getLocal().isIdle()
    	&& !Inventory.isFull();
    	
    }

    @Override
    public void execute() {
    	NxPowerMiner.Status = "Minning ore";
    	System.out.println("Minning ore");
    	SceneObject Iron  = SceneEntities.getNearest(NxPowerMiner.Iron_Filter);
    	if (Players.getLocal().isIdle()){
    		if(Iron != null){
    			if(Iron.isOnScreen()){
    				Iron.interact("Mine");
    				
    				
    				Task.sleep(750);
    				
    			}else{
    				Camera.turnTo(Iron);
    				Task.sleep(750);
    			}
    		}
    	}
   }
}