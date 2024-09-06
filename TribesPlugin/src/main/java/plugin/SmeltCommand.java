package plugin;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class SmeltCommand implements CommandExecutor {

	
	public SmeltCommand(SuperPowers plugin) {
		plugin.getCommand("smelt").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String cmd = command.getName();
			Player player = (Player) sender;
			if (cmd.equals("smelt")) {
				if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1 || player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
					ItemStack item = player.getInventory().getItemInMainHand();
					if (item == null) {
						player.sendMessage(ChatColor.RED + "There's nothing in your hand");
					} else if (item.getItemMeta() == null) {
						player.sendMessage(ChatColor.RED + "There's nothing in your hand");
					} else {
						ItemStack result = null;
		                Iterator<Recipe> iter = Bukkit.recipeIterator();
		                while (iter.hasNext()) {
		                   Recipe recipe = iter.next();
		                   if (recipe instanceof FurnaceRecipe) {
		                	   if (((FurnaceRecipe)recipe).getInput().getType() == item.getType()) {
		                		   result = recipe.getResult();
		                		   result.setAmount(item.getAmount());
		                		   break;
		                	   	}
		                   	}
		                }
		                if (result != null) {
		                	player.playSound(player, Sound.ITEM_FIRECHARGE_USE, 1F, 1F);
			                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), result);
			                player.sendMessage("Succesfully cooked");
			                player.spawnParticle(Particle.FLAME, player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection()), 10,0, 0, 0, 0.04);
			                player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection()), 5,0, 0, 0, 0.02);
		                } else {
		                	player.sendMessage(ChatColor.RED + "You can't cook this");
		                }
					}
	                
				} else {
					player.sendMessage(ChatColor.RED + "Command is restricted to" + ChatColor.UNDERLINE + "FIRE TRIBE");
				}
			}
		
		}
		return false;
	}
	
}
