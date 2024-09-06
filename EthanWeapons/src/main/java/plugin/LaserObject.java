package plugin;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class LaserObject implements CommandExecutor{
	
	public LaserObject(MyPlugin plugin) {
		plugin.getCommand("pewpew").setExecutor(this);
	}
	
	public ItemStack createLG() {		
		ItemStack gun = new ItemStack(Material.ECHO_SHARD,1);
		
		
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("I smell something sizzlin'");

		ItemMeta im = gun.getItemMeta();
		im.setItemName(ChatColor.RED + "Laser Gun");
		im.setLore(lore);
		im.setEnchantmentGlintOverride(true);
		
		gun.setItemMeta(im);
		
		return gun;
	}
	
	public void giveLG(Player player) {
		player.getInventory().addItem(createLG());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String cmd = command.getName();
			
			if (cmd.equals("pewpew")) {
				Player player = (Player)sender;
				giveLG(player);
			}
		}
		return false;
	}
}
