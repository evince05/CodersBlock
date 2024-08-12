package plugin.features;

import org.bukkit.Nameable;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.vehicle.VehicleEnterEvent;

/**
 * Let's make horses that go BOOOOOOOOOM!
 * 
 * This is a prank feature. To create an exploding horse,
 * use a nametag to give a horse the name "boom".
 */

public class ExplodingHorses implements Listener {

	@EventHandler
	public void makeHorseExplode(VehicleEnterEvent event) {

		// is the vehicle a horse?
		if (event.getVehicle() instanceof Horse) {

			if (!(event.getEntered() instanceof Player)) {
				// We only want players to explode from the horses.
				return;
			}
			
			// Now, we know that a player is riding a horse.
			Horse horse = (Horse) event.getVehicle();

			// Certain entities can have custom names (e.g. via a name tag)
			String name = ((Nameable) horse).getCustomName();

			/*
			 * Any horse with the name "Boom" (case-insensitive) will explode.
			 */
			
			if (name != null && name.equalsIgnoreCase("boom")) {
				horse.getWorld().createExplosion(horse.getLocation(), 8);
			}
		}
	}

	/**
	 * This method grants horses immunity from explosions.
	 * This way, the player gets a nice surprise, but the horse stays safe.
	 */
	
	@EventHandler
	public void keepHorseAlive(EntityDamageEvent event) {

		if (event.getEntity() instanceof Horse) {

			// check if the damage is from explosion
			if (event.getCause() == DamageCause.ENTITY_EXPLOSION || 
					event.getCause() == DamageCause.BLOCK_EXPLOSION) {

				// Cancels the damage to the horse.
				event.setCancelled(true);
			}
		}
	}
}
