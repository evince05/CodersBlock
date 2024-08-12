package plugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import plugin.MyPlugin;

/**
 * The LegendCrossbow is a crossbow with high levels
 * of Multishot and Quick Charge.
 */

public class LegendCrossbow {

	private ItemStack bow;

	/*
	 * Sets up the LegendCrossbow and adds it to the server.
	 * @param plugin The main plugin class.
	 */

	public LegendCrossbow(MyPlugin plugin) {

		// create the crossbow
		this.bow = createLegendCrossbow();

		NamespacedKey recipeKey = new NamespacedKey(plugin, "legend_crossbow");
		ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, bow);
		
		// Builds the shape of the recipe
		shapedRecipe.shape(" N ", "DCD", " N ");
		
		// Each character in the shape() method corresponds to an ingredient.
		shapedRecipe.setIngredient('N', Material.NETHERITE_INGOT);
		shapedRecipe.setIngredient('D', Material.DISPENSER);
		shapedRecipe.setIngredient('C', Material.CROSSBOW);
		
		plugin.getServer().addRecipe(shapedRecipe);
	}
	
	/**
	 * Creates a copy of the LegendCrossbow.
	 */

	private ItemStack createLegendCrossbow() {

		ItemStack bow = new ItemStack(Material.CROSSBOW, 1);

		ItemMeta meta = bow.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "The Crossbow of Legends");

		// We add the enchants to the bow (most enchants can go to level 255 in our code)
		meta.addEnchant(Enchantment.MULTISHOT, 7, true);
		meta.addEnchant(Enchantment.QUICK_CHARGE, 4, true);

		// We have to update the bow's item meta once we make changes.
		bow.setItemMeta(meta);
		
		return bow;

	}



}
