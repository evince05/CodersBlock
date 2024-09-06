package plugin;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MagicBow implements CommandExecutor{
	
	public MagicBow(MyPlugin plugin) {
		plugin.getCommand("powerbow").setExecutor(this);
	}
	
	public ItemStack createBreezeWand() {		
		ItemStack arrow = new ItemStack(Material.BOW,1);
		
		
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("A bow designed to rival the gods");
		
		ItemMeta im = arrow.getItemMeta();
		im.setItemName("Magic Bow");
		im.setLore(lore);
		im.setRarity(ItemRarity.EPIC);
		im.setEnchantmentGlintOverride(true);
		
		arrow.setItemMeta(im);
		
		return arrow;
	}
	
	public void giveWand(Player player) {
		player.getInventory().addItem(createBreezeWand());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String cmd = command.getName();
			
			if (cmd.equals("powerbow")) {
				Player player = (Player)sender;
				giveWand(player);
			}
		}
		return false;
	}
}
