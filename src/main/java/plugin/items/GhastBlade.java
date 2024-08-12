package plugin.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import plugin.MyPlugin;

/**
 * The GhastBlade is a regular iron sword that 
 * shoots fireballs when you right click.
 * 
 */

// By implementing Listener, we can listen for events in Minecraft.

public class GhastBlade implements Listener {

	private ItemStack sword;

	/**
	 * Creates and adds the GhastBlade to the server.
	 * @param plugin The main class of our plugin.
	 */
	
	public GhastBlade(MyPlugin plugin) {
		
		// Register all events listed in this class
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		this.sword = createGhastBlade();

		NamespacedKey key = new NamespacedKey(plugin, "ghast_blade");
		ShapedRecipe recipe = new ShapedRecipe(key, sword);

		/*
		 * Set the ingredients. In this case, you can craft the sword 
		 * in any of the three columns of the crafting table.
		 */
		
		recipe.shape("F", "G", "S");
		recipe.setIngredient('F', Material.FIRE_CHARGE);
		recipe.setIngredient('G', Material.GHAST_TEAR);
		recipe.setIngredient('S', Material.STICK);
		
		plugin.getServer().addRecipe(recipe);
		
	}

	/**
	 * Creates a copy of the GhastBlade.
	 */
	
	private ItemStack createGhastBlade() {

		// creating our sword!
		ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);

		// name and description.
		ItemMeta meta = sword.getItemMeta();
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
		meta.setDisplayName(ChatColor.GOLD + "Ghast Blade");

		sword.setItemMeta(meta);
		
		return sword;

	}

	/*
	 * Note: We need the @EventHandler tag above each event method we create.
	 * Otherwise, our Listener won't know that we want to listen to this method.
	 */
	
	@EventHandler
	public void shootFireball(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		ItemStack mainHand = player.getInventory().getItemInMainHand();

		if (mainHand == null || !mainHand.hasItemMeta()) {
			// The player isn't holding an item, or it doesn't have custom data.
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {


			if (event.getClickedBlock() != null) {
				
				if (event.getClickedBlock().getType() == Material.CHEST 
						|| event.getClickedBlock().getType() == Material.FURNACE) {
					
					/*
					 * If the player is using a chest or furnace, we don't want to 
					 * accidentally blow it up. We learned this the hard way in Session 1.
					 */
					
					return;
				}
			}

			if (player.getCooldown(Material.IRON_SWORD) == 0) {
				// The cooldown is done. We can fire!

				String itemName = mainHand.getItemMeta().getDisplayName();
				String swordName = sword.getItemMeta().getDisplayName();

				if (itemName == null) {

					// doesnt have custom name, so it can't be the GhastBlade
					return;
				}

				if (itemName.equals(swordName)) {
					// time to shoot a fireball

					// The getDirection() points to where the player is looking.
					Vector velocity = player.getLocation().getDirection().multiply(2.5);

					Location spawnLocation = player.getEyeLocation().add(velocity);

					// Spawn the fireball. Use responsibly!
					Fireball fireball = player.getWorld().spawn(spawnLocation, Fireball.class);
					fireball.setVelocity(velocity);

					// NOTE: This controls the explosion! Setting the yield to 0 will disable the explosion.
					fireball.setYield(2.5f);
					fireball.setIsIncendiary(true);
					
					// Simulates the swinging animation for a cool effect
					player.swingMainHand();

					/*
					 *  We run the cooldown for 5 seconds for the Iron Sword.
					 *  When you use the Ghast Blade, you can't shoot the fireball for another 5 seconds.
					 */

					float numSeconds = 5f;

					/*
					 * Cooldowns work in ticks. Most servers run at 20 ticks per second.
					 * 100 ticks = 5 seconds.
					 */
					
					player.setCooldown(Material.IRON_SWORD, Math.round(20 * numSeconds));

				}

			}
			else {
				// The cooldown is still on! Let's tell the player.
				player.sendMessage(ChatColor.RED + "Your " + ChatColor.GOLD + "Ghast Blade " 
						+ ChatColor.RED + "is charging!");

			}
		}
	}
}
