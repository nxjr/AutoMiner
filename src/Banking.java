import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Banking extends Node {
  @Override
    public boolean activate() {
  	return Inventory.isFull() && NxPowerMiner.PlayerAtBank();
  }

   @Override
  public void execute() {
		NxPowerMiner.Status = "Walking to bank";
	   System.out.println("Walking to bank");
   SceneObject bankBooth = SceneEntities.getNearest(NxPowerMiner.depositBox);
    if (bankBooth.isOnScreen() && !Widgets.get(762).validate()
        && !(Inventory.getCount() == 0)) {
   	bankBooth.interact("Bank");
    	Task.sleep(2000);
    if (Widgets.get(762).validate()){
    	Bank.depositInventory();
    	Task.sleep(1000);
    		}
	
 }
 
	}
}


            			
