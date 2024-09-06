package plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class FireAttack implements Listener{
	
	@EventHandler
	public void fireAttack (EntityDamageEvent event) {
		if (event.getDamageSource().getCausingEntity() instanceof Player) {
			Player player = (Player)event.getDamageSource().getCausingEntity();
			if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1) {
				event.getEntity().setFireTicks(100);
			}
		}
	}
}
