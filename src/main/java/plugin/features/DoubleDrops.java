package plugin.features;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import plugin.MyPlugin;

/**
 * DoubleDrops is a feature that doubles certain items when you mine them.<br>
 * To add more drops to the list, add more values to the DROPS_TO_DOUBLE array.
 */

public class DoubleDrops implements Listener {
	
	public static Material[] DROPS_TO_DOUBLE = {
			Material.STONE,
			Material.COBBLESTONE,
			Material.ANDESITE,
			Material.GRAVEL,
			Material.GRANITE,
			Material.DIORITE,
			Material.SAND,
			Material.DIRT,
			Material.OAK_LOG,
			Material.JUNGLE_LOG,
			Material.BIRCH_LOG,
			Material.CHERRY_LOG,
			Material.CARROTS,
			Material.WHEAT,
			Material.POTATOES,
	};
	
	public DoubleDrops(MyPlugin plugin) {
		
		// STEP 1. Register our events
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void doubleMyItem(BlockDropItemEvent event) {
		// This code runs whenever a block drops an item.
		
		BlockState blockState = event.getBlockState();
		
		for (Material possibleDrop : DROPS_TO_DOUBLE) {
			
			if (blockState.getType() == possibleDrop) {
				
				// We can double the drop since it is in the array
				List<Item> items = event.getItems();
				
				// We double each drop.
				for (Item itemToDouble : items) {
					int amount = itemToDouble.getItemStack().getAmount();
					itemToDouble.getItemStack().setAmount(amount * 2);
				}
				
				return;
			}
		}
	}
}
