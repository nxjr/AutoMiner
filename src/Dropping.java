import java.awt.event.KeyEvent;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;

  public class Dropping extends Node {
           @Override
        public boolean activate() {
		return Inventory.getCount() >=3;
            	
         }
         @Override
         public void execute() {
        	NxPowerMiner.Status = "Dropping Ore";
        	 System.out.println("Dropping ore");
        	 Keyboard.sendKey((char) KeyEvent.VK_1);
          
        }
      }
