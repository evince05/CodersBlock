package plugin.features;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import plugin.MyPlugin;

/**
 * The AngryGolem is an upgraded version of the Iron Golem.
 * It is much larger, faster, and it chases players.
 */

public class AngryGolem implements Listener {
	
	private MyPlugin plugin;
	
	/*
	 * Feel free to add your username to this list!
	 * Watch out - if you hit an angry golem, it will still attack you!
	 * 
	 * In the Egg event, we added the names of the CodersBlock staff members.
	 */
	
	private static final String[] PLAYERS_TO_IGNORE = {
			"Steve", "xUltraaaa", "Herobrine"
	};
	
	public AngryGolem(MyPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		this.plugin = plugin;
	}
	
	@EventHandler
	public void buffIronGolem(EntitySpawnEvent event) {
		
		if (event.getEntityType() == EntityType.IRON_GOLEM) {
			
			IronGolem ig = (IronGolem) event.getEntity();
			
			// Edits the max health.
			ig.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
			
			// Sets the health of the IG so it has a full healthbar.
			ig.setHealth(200);
			ig.setCustomName(ChatColor.GOLD + "" + ChatColor.BOLD + "BIG STOMPA");
			
			/*
			 * Scale is what makes the IG larger. This is a new feature
			 * from Minecraft 1.20.5 and onwards, and it's awesome!
			 */
			
			ig.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.5);
			ig.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
			
			AttributeInstance baseDamage = ig.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
			baseDamage.setBaseValue(baseDamage.getBaseValue() * 1.5);
			
			// Creates a task to chase the nearest player.
			BukkitRunnable task = new BukkitRunnable() {

				@Override
				public void run() {
					
					boolean needsNewTarget = true;
					
					if (ig.getTarget() != null && ig.getTarget().getWorld().equals(ig.getWorld())) {
						
						double distanceSquared = ig.getLocation().distanceSquared(ig.getTarget().getLocation());
						if (distanceSquared <= 200) {
							/*
							 * There is a player within a close enough range
							 * that we don't need to find a new target.
							 */
							needsNewTarget = false;
						}
					}
					
					if (needsNewTarget) {
						
						List<Entity> entities = ig.getNearbyEntities(10, 4, 10);
						
						for (Entity entity : entities) {
							
							if (!(entity instanceof Player)) {
								// Skips over non-players
								continue;
							}
							
							// Target the player
							Player target = (Player) entity;
							
							boolean shouldTarget = true;
							
							for (String name : PLAYERS_TO_IGNORE) {
								
								// The IG found a target, but we ignore it.
								if (target.getName().equals(name)) {
									shouldTarget = false;
								}
							}
							
							if (shouldTarget) {
								ig.setTarget(target);
								
								// We found a target, so we'll exit the method for efficiency purposes.
								return;
							}
						}
					}
				}
				
			};
			
			// Runs the targetting method every 15 seconds.
			task.runTaskTimer(plugin, 0, 300);
		}
	}
	
	@EventHandler
	public void targetPlayer(EntityDamageByEntityEvent event) {
		
		if (event.getEntity() instanceof IronGolem) {
			
			IronGolem ig = (IronGolem) event.getEntity();
			
			if (event.getDamager() instanceof Player) {
				
				// A player hit an iron golem.
				Player player = (Player) event.getDamager();
				
				// Controls if the IG should fight back.
				boolean shouldTarget = true;
				
				for (String exemptPlr : PLAYERS_TO_IGNORE) {
					
					if (player.getName().equals(exemptPlr)) {
						// The player is exempt from IG attacks.
						shouldTarget = false;
						
						// Cancels the targetting (if the IG tried to attack them)
						ig.setTarget(null);
						return;
					}
				}
				
				if (shouldTarget) {
					ig.setTarget(player);
				}
			}
		}
	}
}