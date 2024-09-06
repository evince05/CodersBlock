package plugin.features.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import plugin.features.kits.KitManager.ItemTier;

/**
 * A Kit is a collection of items that a player can receive using /kit.
 * When a player selects a kit, a GUI opens with all the items in that kit.
 */

public class Kit {

	private static final Material[] DIAMOND_ARMOR = {
			Material.DIAMOND_HELMET,
			Material.DIAMOND_CHESTPLATE,
			Material.DIAMOND_LEGGINGS,
			Material.DIAMOND_BOOTS
	};

	private static final Material[] NETHERITE_ARMOR = {
			Material.NETHERITE_HELMET,
			Material.NETHERITE_CHESTPLATE,
			Material.NETHERITE_LEGGINGS,
			Material.NETHERITE_BOOTS
	};

	private List<ItemStack> items;

	private String name;

	private String prefix;

	private String permission;

	Kit(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
		this.items = new ArrayList<ItemStack>();
	}

	/**
	 * Gives a kit to a player, in the form of an Inventory GUI.
	 * @param player The player to receive the kit
	 */

	public void giveToPlayer(Player player) {

		if (permission != null) {

			if (!player.hasPermission(permission)) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use that kit!");
				return;
			}
		}

		// Prepare the inventory
		Inventory kitInventory = Bukkit.createInventory(player, 27, name);

		// Adds each item from the kit to the GUI
		items.forEach(item -> {
			kitInventory.addItem(item);
		});

		// The inventory is ready for the player.
		player.openInventory(kitInventory);


	}

	/**
	 * Adds a set of armor to the kit, with a set of default enchantments.
	 * @param itemTier The item tier of the kit
	 * @param enchantments The default enchantments to add to every item.
	 */

	public void addArmor(ItemTier itemTier, HashMap<Enchantment, Integer> enchantments) {

		Material[] armorMats = null;

		if (itemTier == ItemTier.DIAMOND) {
			armorMats = DIAMOND_ARMOR;
		}
		else if (itemTier == ItemTier.NETHERITE) {
			armorMats = NETHERITE_ARMOR;
		}

		for (Material armorPiece : armorMats) {

			// Create armor
			ItemStack armor = new ItemStack(armorPiece, 1);
			ItemMeta im = armor.getItemMeta();

			// Strips off the "diamond" or "netherite" prefix
			String armorName = armorPiece.toString().replace('_', ' ').split(" ")[1];

			im.setDisplayName(prefix + " " + armorName);

			// Adds each enchantment in the map
			enchantments.forEach((ench, level) -> {
				im.addEnchant(ench, level, true);
			});

			armor.setItemMeta(im);
			items.add(armor);
		}
	}

	/**
	 * Adds a basic item to the kit. (no enchants or custom data).
	 * @param material The material of the item
	 * @param amount The amount of the item
	 */
	
	public void addItem(Material material, int amount) {
		items.add(new ItemStack(material, amount));
	}
	
	public void addItem(ItemStack item) {
		items.add(item);
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the kit's prefix (the text that displays before all items).
	 * @param prefix The kit's prefix (supports {@link ChatColor})
	 */

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setPermission(String perm) {
		this.permission = perm;
	}

}
