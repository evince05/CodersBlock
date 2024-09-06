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

public class HollowPurple implements CommandExecutor{
	
	public HollowPurple(MyPlugin plugin) {
		plugin.getCommand("nahidwin").setExecutor(this);
	}
	
	public ItemStack createTechnique() {		
		ItemStack porple = new ItemStack(Material.PURPLE_DYE,1);
		
		
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("\"nah i'd win\"");
		lore.add("\"throughout heaven and earth i alone am the honoured one\"");
		lore.add("\"cursed technique reversal: scope him\"");
		
		ItemMeta im = porple.getItemMeta();
		im.setItemName("Hollow Purple");
		im.setLore(lore);
		im.setRarity(ItemRarity.EPIC);
		im.setEnchantmentGlintOverride(true);
		
		porple.setItemMeta(im);
		
		return porple;
	}
	
	public void giveTechnique(Player player) {
		player.getInventory().addItem(createTechnique());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String cmd = command.getName();
			
			if (cmd.equals("nahidwin")) {
				Player player = (Player)sender;
				giveTechnique(player);
			}
		}
		return false;
	}
}
