package plugin;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class FireWalkEnable implements CommandExecutor {
	
	public static HashMap<String, Boolean> togglewalk = new HashMap<String,Boolean>();
	
	public static HashMap<String, Boolean> getHash(){
		return togglewalk;
	};
	
	public FireWalkEnable(SuperPowers plugin) {
		plugin.getCommand("togglefirewalk").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String cmd = command.getName();
			Player player = (Player) sender;
			if (cmd.equals("togglefirewalk")) {
				if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1 ||
				player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
					if (togglewalk.get(player.getName()) == null) {
						player.sendMessage(ChatColor.RED + "Fire Walk Turned" + ChatColor.WHITE + " Off");
						togglewalk.put(player.getName(),false);
					} else if (togglewalk.get(player.getName()) == true) {
						player.sendMessage(ChatColor.RED + "Fire Walk Turned" + ChatColor.WHITE + " Off");
						togglewalk.put(player.getName(),false);
					} else if (togglewalk.get(player.getName()) == false) {
						player.sendMessage(ChatColor.RED + "Fire Walk Turned" + ChatColor.WHITE + "On");
						togglewalk.put(player.getName(),true);
					}
				} else {
					player.sendMessage(ChatColor.RED + "Command is restricted to FIRE TRIBE");
				}
			}
		
		}
		return false;
	}
	
}
