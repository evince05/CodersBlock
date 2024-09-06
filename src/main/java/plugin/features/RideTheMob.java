package plugin.features;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import plugin.MyPlugin;

/**
 * A fun feature that allows you to ride any mob!<br>
 * While holding a saddle, right-click on a mob to ride them.<br>
 * This works with many mobs: chickens, villagers, the wither, and more!
 */

public class RideTheMob implements Listener {

	public RideTheMob(MyPlugin plugin) {
		// register the events
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void rideMyPet(PlayerInteractEntityEvent event) {
		
		Player plr = event.getPlayer();
		ItemStack hand = plr.getInventory().getItemInMainHand();
		
		// Check if the person is holding a saddle
		if (hand != null) {
			
			if (hand.getType() == Material.SADDLE) {
				
				// Get the pet we want to ride
				Entity pet = event.getRightClicked();
				
				// Make the player ride the mob
				pet.addPassenger(plr);				
			}
		}
	}
}