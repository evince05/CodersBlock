package plugin;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AutoOreSmelt implements Listener{
	Random rng = new Random();
	
	@EventHandler
	public void oreSmelt(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack item= player.getInventory().getItemInMainHand();
		if (item != null ) {
			if (item.getItemMeta() != null) {
				if(!item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)){
					int dropCount = 1;
					int expDrop = 1;
					if (item.getItemMeta().hasEnchant(Enchantment.FORTUNE)) {
						dropCount += item.getItemMeta().getEnchantLevel(Enchantment.FORTUNE);
					}
					
					
					
					
					if (block.getType().toString().contains("_ORE") && event.isDropItems()) {
						
						
						
						
						//FIRE
						if (player.getPersistentDataContainer().get
								(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1) {
								if (block.getType().toString().contains("IRON_ORE")) {
									event.setDropItems(false);
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));
									player.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(expDrop);
								}
								
								if (block.getType().toString().contains("GOLD_ORE")) {
									event.setDropItems(false);
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
									player.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(expDrop);
								}
								
								if (block.getType().toString().contains("COPPER_ORE")) {
									event.setDropItems(false);
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COPPER_INGOT));
									player.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(expDrop);
								}
							
						}
						
						
						
						
						//EARTH
						if (player.getPersistentDataContainer().get
								(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 4 ||
								player.getPersistentDataContainer().get
								(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
							if (rng.nextInt(4) == 2) {
							
								if (block.getType().toString().contains("DIAMOND_ORE")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND));
								}
								if (block.getType().toString().contains("COAL_ORE")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COAL));
								}
								if (block.getType().toString().contains("LAPIS_ORE")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.LAPIS_LAZULI));
								}
								if (block.getType().toString().contains("REDSTONE_ORE")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.REDSTONE));
								}
								if (block.getType().toString().contains("IRON_ORE")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.RAW_IRON));
								}
								
								if (block.getType().toString().contains("GOLD_ORE") && block.getType().toString().contains("NETHER")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.RAW_GOLD));
								}
								
								if (block.getType().toString().contains("COPPER_ORE")) {
									for (int i = 0; i < dropCount; i++) player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.RAW_COPPER));
								}
							}
						
						}
					}
				}
			}
		}
	}
}
