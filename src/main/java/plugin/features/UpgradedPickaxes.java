package plugin.features;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The UpgradedPickaxes class is cool.
 * When a player mines certain ores, they have a chance to drop a bonus, upgraded ore.<br><br>
 * 
 * Coal has a chance to drop a bonus iron ingot.<br>
 * Iron has a chance to drop a bonus gold ingot.<br>
 * Gold has a chance to drop a bonus diamond.<br>
 * Diamond has a chance to drop a bonus netherite scrap.<br><br>
 * 
 * Redstone has a chance to double.<br>
 * Lapis lazuli has a chance to double.<br>
 */

public class UpgradedPickaxes implements Listener {

	private static final Random RNG = new Random();

	@EventHandler
	public void upgradeOre(BlockBreakEvent event) {

		// did the player break an ore?
		Player player = event.getPlayer();

		Block block = event.getBlock();

		if (block.getType().name().contains("ORE")) {
			// okay, it is an ore

			ItemStack currentItem = player.getInventory().getItemInMainHand();

			if (currentItem != null) {
				
				// cancel if using silk touch.
				if (currentItem.containsEnchantment(Enchantment.SILK_TOUCH)) {
					return;
				}
				
				// player is holding something. what is it?
				if (currentItem.getType().name().contains("PICKAXE")) {
					// player has pickaxe in hand

					World world = player.getWorld();
					Location location = block.getLocation();

					if (block.getType() == Material.COAL_ORE || block.getType() == Material.DEEPSLATE_COAL_ORE) {
						// player mines

						/*
						 * This simulates a 1 in 5 random chance.
						 * 
						 * If roll is less than 1, it will drop an ore.
						 * roll will be less than 1 only when roll equals 0.
						 * 
						 * Since roll can be an int from 0 - 4, there are 5 possible outcomes.
						 * Therefore, this is a 1 in 5 chance.
						 */
						
						int dropChance = 1;
						
						int roll = RNG.nextInt(5);

						if (roll < dropChance) {
							spawnBonus(world, location, Material.IRON_INGOT);
						}

					}
					else if (block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE) {
						
						// Same logic as above
						int dropChance = 1;

						int roll = RNG.nextInt(5);

						if (roll < dropChance) {
							spawnBonus(world, location, Material.GOLD_INGOT);
						}

					}
					else if (block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE) {

						int dropChance = 1;

						int roll = RNG.nextInt(7);

						if (roll < dropChance) {
							spawnBonus(world, location, Material.DIAMOND);
						}

					}
					else if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.DEEPSLATE_DIAMOND_ORE) {

						int dropChance = 1;

						int roll = RNG.nextInt(8);

						if (roll < dropChance) {
							spawnBonus(world, location, Material.NETHERITE_SCRAP);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void doubleOre(BlockDropItemEvent event) {
		
		// did the player break an ore?
		Player player = event.getPlayer();

		BlockState block = event.getBlockState();

		if (block.getType().name().contains("ORE")) {
			// okay, it is an ore

			ItemStack currentItem = player.getInventory().getItemInMainHand();

			if (currentItem != null) {
				
				if (currentItem.containsEnchantment(Enchantment.SILK_TOUCH)) {
					return;
				}
				
				// player is holding something. what is it?
				if (currentItem.getType().name().contains("PICKAXE")) {
					
					if (block.getType() == Material.REDSTONE_ORE || block.getType() == Material.DEEPSLATE_REDSTONE_ORE
							|| block.getType() == Material.LAPIS_ORE || block.getType() == Material.DEEPSLATE_LAPIS_ORE) {
						
						int dropChance = 1;
						int roll = RNG.nextInt(5);
						
						if (roll < dropChance) {
							
							// doubles drop
							
							// Note that Item is an Entity. We want to create an ItemStack instead.
							for (Item drop : event.getItems()) {
								
								// Creates an extra copy, therefore doubling the drop amount
								ItemStack bonus = new ItemStack(drop.getItemStack());
								
								// may cause double items?
								block.getWorld().dropItemNaturally(block.getLocation(), bonus);
										
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Utility method to spawn a bonus ore
	 * @param world The world where we want to drop the item
	 * @param location The location to drop
	 * @param material The material of the bonus ore.
	 */
	private void spawnBonus(World world, Location location, Material material) {
		ItemStack bonus = new ItemStack(material, 1);
		world.dropItemNaturally(location, bonus);
	}
}
