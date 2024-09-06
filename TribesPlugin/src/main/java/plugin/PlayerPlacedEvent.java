package plugin;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerPlacedEvent implements Listener{
	
	BukkitScheduler bukkitsched = Bukkit.getServer().getScheduler();

	@EventHandler
	public void placed(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		BlockData bData = block.getBlockData();
		if (bData instanceof Ageable && (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 4 || 
				player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5)) {
			Ageable age = (Ageable)bData;
			int taskid = bukkitsched.scheduleSyncRepeatingTask(SuperPowers.getPlugin(), new Runnable() {
				public void run() {
					age.setAge(age.getAge()+1);
					age.copyTo(bData);
					block.setBlockData(bData);
				}
			}, 50, 100);
			bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
				public void run() {
					bukkitsched.cancelTask(taskid);
				}
			}, (age.getMaximumAge())*150);
		}
	}
}
