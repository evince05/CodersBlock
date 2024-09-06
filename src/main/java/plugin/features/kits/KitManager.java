package plugin.features.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import plugin.MyPlugin;

/**
 * The KitManager stores a list of available kits, and handles the /kit command.
 */

public class KitManager implements CommandExecutor {
	
	// The spawn location in the castle world
	private static final Vector SPAWN_LOCATION = new Vector(110, 65, 405);
	
private List<Kit> kits;
	
	/**
	 * Creates the KitManager
	 * @param plugin The main plugin class
	 */
	
	public KitManager(MyPlugin plugin) {
		plugin.getCommand("kit").setExecutor(this);
		
		this.kits = new ArrayList<Kit>();
		setupKits();
	}

	private void setupKits() {
		
		Kit testKit = new Kit("test", ChatColor.LIGHT_PURPLE + "Test");
		
		HashMap<Enchantment, Integer> enchantsList = new HashMap<Enchantment, Integer>();
		enchantsList.put(Enchantment.PROTECTION, 7);
		enchantsList.put(Enchantment.UNBREAKING, 4);
		enchantsList.put(Enchantment.THORNS, 2);
		
		testKit.addArmor(ItemTier.DIAMOND, enchantsList);
		testKit.addItem(Material.COOKED_BEEF, 64);
		testKit.addItem(Material.WATER_BUCKET, 1);
		
		kits.add(testKit);
		
		// Creates the warrior kit
		Kit warriorKit = new Kit("Warrior", ChatColor.GOLD + "Warrior" + ChatColor.WHITE);
		
		HashMap<Enchantment, Integer> warriorEnchants = new HashMap<Enchantment, Integer>();
		warriorEnchants.put(Enchantment.PROTECTION, 10);
		warriorEnchants.put(Enchantment.UNBREAKING, 6);
		
		warriorEnchants.put(Enchantment.SWIFT_SNEAK, 3);
		warriorEnchants.put(Enchantment.MENDING, 1);
		
		// Adds a set of diamond armor with all the above enchants
		warriorKit.addArmor(ItemTier.DIAMOND, warriorEnchants);
		
		// Custom sword for the kit
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta swordM = sword.getItemMeta();
		swordM.addEnchant(Enchantment.SHARPNESS, 8, true);
		swordM.addEnchant(Enchantment.KNOCKBACK, 3, true);
		sword.setItemMeta(swordM);
		
		// Custom axe for the kit
		ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta axeM = axe.getItemMeta();
		axeM.addEnchant(Enchantment.SHARPNESS, 7, true);
		axeM.addEnchant(Enchantment.KNOCKBACK, 2, false);
		axe.setItemMeta(axeM);
		
		// Custom bow for the kit
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta bowM = bow.getItemMeta();
		bowM.addEnchant(Enchantment.POWER, 8, true);
		bowM.addEnchant(Enchantment.PUNCH, 1, false);
		bowM.addEnchant(Enchantment.INFINITY, 1, false);
		bow.setItemMeta(bowM);
		
		warriorKit.addItem(sword);
		warriorKit.addItem(axe);
		warriorKit.addItem(bow);
		
		// Some default items for fun
		warriorKit.addItem(Material.DIAMOND_PICKAXE, 1);
		warriorKit.addItem(Material.DIAMOND_SHOVEL, 1);
		
		warriorKit.addItem(Material.COOKED_BEEF, 64);
		warriorKit.addItem(Material.COOKED_BEEF, 64);
		
		warriorKit.addItem(Material.WATER_BUCKET, 1);
		warriorKit.addItem(Material.WATER_BUCKET, 1);
		
		warriorKit.addItem(Material.LAVA_BUCKET, 1);
		warriorKit.addItem(Material.LAVA_BUCKET, 1);
		
		warriorKit.addItem(Material.COBBLESTONE, 64);
		warriorKit.addItem(Material.OBSIDIAN, 64);
		
		warriorKit.addItem(Material.SPECTRAL_ARROW, 64);
		
		// VERY IMPORTANT! Adds the kit to our system
		kits.add(warriorKit);
		
		// Staff kit
		Kit staff = new Kit("staff", ChatColor.AQUA + "Staff" + ChatColor.WHITE);
		
		// Only ops can use this kit
		staff.setPermission("op");
		
		HashMap<Enchantment, Integer> staffEnchants = new HashMap<Enchantment, Integer>();
		staffEnchants.put(Enchantment.PROTECTION, 20);
		staffEnchants.put(Enchantment.UNBREAKING, 10);
		
		staffEnchants.put(Enchantment.SWIFT_SNEAK, 4);
		staffEnchants.put(Enchantment.MENDING, 1);
		
		// The staff kit gets some pretty awesome netherite armor
		staff.addArmor(ItemTier.NETHERITE, staffEnchants);
		
		// More custom items
		ItemStack sword2 = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta swordM2 = sword2.getItemMeta();
		swordM2.addEnchant(Enchantment.SHARPNESS, 12, true);
		swordM2.addEnchant(Enchantment.KNOCKBACK, 4, true);
		sword2.setItemMeta(swordM2);
		
		ItemStack axe2 = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta axeM2 = axe2.getItemMeta();
		axeM2.addEnchant(Enchantment.SHARPNESS, 12, true);
		axeM2.addEnchant(Enchantment.KNOCKBACK, 3, false);
		axe2.setItemMeta(axeM2);
		
		ItemStack bow2 = new ItemStack(Material.BOW, 1);
		ItemMeta bowM2 = bow2.getItemMeta();
		bowM2.addEnchant(Enchantment.POWER, 15, true);
		bowM2.addEnchant(Enchantment.PUNCH, 5, false);
		bowM2.addEnchant(Enchantment.INFINITY, 1, false);
		bow2.setItemMeta(bowM2);
		
		staff.addItem(sword);
		staff.addItem(axe);
		staff.addItem(bow);
		
		// Some very fun items!
		staff.addItem(Material.IRON_GOLEM_SPAWN_EGG, 64);
		staff.addItem(Material.OBSIDIAN, 64);
		staff.addItem(Material.SPECTRAL_ARROW, 64);
		
		// VERY IMPORTANT - register the kit in the system
		kits.add(staff);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (command.getName().equalsIgnoreCase("kit")) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use /kit!");
				return false;
			}
			
			// We know a player sent the command.
			Player player = (Player) sender;
			
			/*
			 * The player must be in the "castle_world" world
			 * If you aren't using the castle_world world, feel free to remove this part
			 */
			
			if (!player.getWorld().getName().equals("castle_world")) {
				player.sendMessage(ChatColor.RED + "You cannot use this yet");
				return false;
			}
			
			/*
			 * The player's must be close to their spawn to use /kit.
			 * If you want to allow players to use /kit wherever, remove this if statement.
			 */
			
			Location location = player.getLocation();
			
			if (location.toVector().distanceSquared(SPAWN_LOCATION) >= 10000 && !player.hasPermission("kit.bypass")) {
				// uses distanceSquared to avoid sqrt (for cpu benefit)
				player.sendMessage(ChatColor.RED + "Go back to your outpost to use this command!");
				return false;
			}
			
			// Now, we can use the command
			if (args.length == 0) {
				
				// Preview the kits
				
				player.sendMessage(ChatColor.GREEN + "Usage: " + 
						ChatColor.GOLD + "/kit <kitname>");
				
				StringBuilder msg = new StringBuilder(ChatColor.GREEN + "Available Kits: ");
				
				// Creates a nice display string
				Iterator<Kit> kitIter = kits.iterator();
				while (kitIter.hasNext()) {
					
					Kit currentKit = kitIter.next();
					msg.append(ChatColor.GOLD + currentKit.getName());
					
					// Adds a comma if there are still more kits
					if (kitIter.hasNext()) {
						msg.append(ChatColor.WHITE + ", ");
					}
				}
				
				player.sendMessage(msg.toString());
			}
			else if (args.length == 1) {
				
				for (Kit kit : kits) {
					
					if (kit.getName().equalsIgnoreCase(args[0])) {
						
						// We found the kit
						kit.giveToPlayer(player);
						return true;
					}
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Invalid usage! Use /kit, or /kit <kitname>");
			}
		}
		return false;
	}
	
	/**
	 * An item tier representing the types of armor/tools in the kit.
	 */
	public enum ItemTier {
		DIAMOND,
		NETHERITE
	}
}
