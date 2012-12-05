import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class dropGems extends Node {

	@Override
	public boolean activate() {
		
	return Inventory.contains(NxPowerMiner.GEM) && NxPowerMiner.PlayeratMinningArea();
	}

	@Override
	public void execute() {
		NxPowerMiner.Status = "Droping Gems";
		Inventory.getItem(NxPowerMiner.GEM).getWidgetChild().interact("Drop");

	}

}
