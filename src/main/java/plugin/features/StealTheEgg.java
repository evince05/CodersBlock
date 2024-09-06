package plugin.features;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import plugin.MyPlugin;

/**
 * This class handles the Egg Raid (an event in Session 3).
 */

public class StealTheEgg implements CommandExecutor, Listener {

	// A simple flag for controlling the eggraid
	private boolean isEggRaidEnabled;
	
	public StealTheEgg(MyPlugin plugin) {
		
		plugin.getCommand("egg").setExecutor(this);
		
		// Loads the Castle world, since it is not loaded by default.
		new WorldCreator("castle_world").createWorld().setPVP(true);
		plugin.getLogger().info("Loaded the Castle World!");
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if (command.getName().equals("egg")) {
				
				if (args.length == 0) {
					
					// OPs can teleport into the world even when the event is disabled
					if (isEggRaidEnabled || player.hasPermission("egg.bypass")) {
						
						// Teleports the player to the egg world.
						Location tpDest = new Location(Bukkit.getWorld("castle_world"), 110, 65, 405);
						
						if (player.getInventory().isEmpty() || player.hasPermission("egg.bypass")) {
							player.sendMessage(ChatColor.GREEN + "Welcome to the quest. Good luck!");
							player.teleport(tpDest);
						}
						else {
							player.sendMessage(ChatColor.RED + "Your inventory must be empty before starting this quest!");
						}
					}
					else {
						// egg raid is not yet enabled on the server
						player.sendMessage(ChatColor.RED + "Huh? How did we get here?");
					}
				}
				else if (args.length == 2) {
					
					if (!player.hasPermission("eggraid.steal")) {
						player.sendMessage(ChatColor.RED + "Don't try that again.");
						player.damage(10);
						
						// VERY important line, otherwise the player can still steal from people.
						return true;
					}
					
					if (args[0].equals("esteal")) {
						
						/*
						 * The syntax is /egg esteal <player>, so this gets
						 * the player listed in the command.
						 */
						
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							
							// sender opens their end chest
							player.openInventory(target.getEnderChest());
						}
					}
					else if (args[0].equals("steal")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							
							// sender opens their inventory
							player.openInventory(target.getInventory());
						}
					}
				}
				else if (args.length == 1) {
					
					if (args[0].equals("start")) {
						
						if (player.hasPermission("eggraid.start")) {
							// Only OPs (or people with this perm) can start the raid
							isEggRaidEnabled = true;
							Bukkit.broadcastMessage(ChatColor.GOLD + "Do or do not, there is no try." + ChatColor.GREEN + "- Master Yoda");
							Bukkit.broadcastMessage(ChatColor.GREEN + "Game on! Use " + ChatColor.LIGHT_PURPLE + "/egg" + ChatColor.GREEN + " to begin the quest.");
						}
						else {
							player.sendMessage(ChatColor.RED + "You dare awake the beast?!");
						}
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * This method ends the game when the player interacts with the dragon egg.
	 */
	
	@EventHandler
	public void onEggHit(PlayerInteractEvent event) {
		
		World world = event.getPlayer().getWorld();
		
		if (world.getName().equals("castle_world") && isEggRaidEnabled) {
			
			Block clickedBlock = event.getClickedBlock();
			if (clickedBlock != null && clickedBlock.getType() == Material.DRAGON_EGG) {
				
				// Prevents the dragon egg from teleporting
				event.setCancelled(true);
				
				// GAME OVER!
				Bukkit.broadcastMessage(ChatColor.AQUA + "Game over!! " + ChatColor.GOLD + event.getPlayer()
						.getName() + ChatColor.AQUA + " found the egg!");
				
				world.setPVP(false);
				isEggRaidEnabled = false;

				for (Player player : Bukkit.getOnlinePlayers()) {
					
					// send everyone to spawn in the castle world.
					player.teleport(new Location(world, 110, 65, 405));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		if (isEggRaidEnabled) {
			
			Player player = event.getEntity();
			
			World world = player.getWorld();
			
			if (world.getName().equals("castle_world")) {
				
				// Clears the player's inventory so they need to get a new kit.
				player.getInventory().clear();
			}
		}
	}
}
