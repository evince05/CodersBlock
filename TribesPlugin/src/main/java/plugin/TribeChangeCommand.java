package plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TribeChangeCommand implements CommandExecutor{
	PlayerJoin playerJoin = new PlayerJoin();
	
	public TribeChangeCommand(SuperPowers plugin) {
		plugin.getCommand("tribechange").setExecutor(this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String cmd = command.getName();
			
			if (cmd.equals("tribechange")) {
				Player player = (Player)sender;
				if (args.length < 1) {
					playerJoin.tribeChoose(player, false);
				} else if (args.length == 1) { 
					Player target = player.getServer().getPlayer(args[0]);
					if (target!=null) {
						playerJoin.tribeChoose(target, false);
					} else {
						player.sendMessage(ChatColor.RED + "Incorrect use of Command: Use /tribechange <username>");
					}
				} else if (args.length == 2) {
					Player target = player.getServer().getPlayer(args[0]);
					if (args[1].equals("op")) {
						playerJoin.tribeChoose(target, true);
					} else {
						player.sendMessage(ChatColor.RED + "Incorrect use of Command: Use /tribechange <username>");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Incorrect use of Command: Use /tribechange <username>");
				}
			}	
		}
		return false;
	}

}
