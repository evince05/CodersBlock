package plugin.features;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * SuperStoneTools turns ordinary stone tools into super tools!
 * Whenever you create a stone tool, it gets renamed and enchanted with efficiency 25.
 */

public class SuperStoneTools implements Listener {

	/**
	 * Overrides the default crafting recipe for stone tools and enchants them.
	 */

	@EventHandler
	public void upgradeTheStone(PrepareItemCraftEvent event) {

		if (event.getRecipe() == null) {
			return;
		}

		ItemStack result = event.getRecipe().getResult();

		if (result == null) {
			return;
		}

		String toolName = "";

		// Get the right tool name.
		switch (result.getType()) {

		case Material.STONE_PICKAXE:
			toolName = "Pickaxe";
			break;
		case Material.STONE_AXE:
			toolName = "Axe";
			break;
		case Material.STONE_SHOVEL:
			toolName = "Shovel";
			break;
		case Material.STONE_HOE:
			toolName = "Hoe";
			break;
		default:
			// Not a stone tool. Return.
			return;
		}

		// Thanks to the default statement, this code only runs if we craft a stone tool.
		
		ItemMeta im = result.getItemMeta();
		
		im.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Super " + 
				ChatColor.GREEN + toolName + "!");
		
		/*
		 * Damageable means the item has durability. Here, we set
		 * the max durability to 500 (better than iron!)
		 */
		
		((Damageable) im).setMaxDamage(500);
		
		im.addEnchant(Enchantment.EFFICIENCY, 25, true);
		result.setItemMeta(im);

		// Overrides the crafting recipe's result with the enchanted tool.
		event.getInventory().setResult(result);
		
	}
}
