package plugin;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoin implements Listener{
	
	BukkitScheduler bukkitsched = Bukkit.getServer().getScheduler();
	HashMap<String, Boolean> togglewalk = FireWalkEnable.getHash();
	
	public void tribeChoose(Player player, boolean isOp) {

		player.sendTitle(ChatColor.BOLD.toString() + ChatColor.UNDERLINE + ChatColor.DARK_PURPLE.toString() + "TRIBE UNCHOSEN"
				, ChatColor.BOLD.toString() + ChatColor.UNDERLINE + ChatColor.LIGHT_PURPLE.toString() + "CHOOSE NOW",20,70,20);
		
		player.playSound(player, Sound.BLOCK_END_PORTAL_SPAWN, 0.5F, 0.1F);
		
		Inventory inventory = Bukkit.createInventory(null, 9);
		
		bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
			public void run() {
				player.openInventory(inventory);
			}
		}, 100);
		
		
		inventory.setItem(1, TribePickers.createFire());
		inventory.setItem(3, TribePickers.createWater());
		inventory.setItem(5, TribePickers.createAir());
		inventory.setItem(7, TribePickers.createEarth());
		if (isOp) {
			inventory.setItem(4, TribePickers.createOP());
		}
		PotionEffect freeze = new PotionEffect(PotionEffectType.SLOWNESS,PotionEffect.INFINITE_DURATION,255, false);
		freeze.apply(player);
		PotionEffect slow = new PotionEffect(PotionEffectType.MINING_FATIGUE,PotionEffect.INFINITE_DURATION,255, false);
		slow.apply(player);
		PotionEffect jumpslow = new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false);
		jumpslow.apply(player);
	}

	
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (!player.getPersistentDataContainer().has(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER)) {
			player.getPersistentDataContainer().set(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER,0);

			tribeChoose(player, false);
			
		} else {
			PassiveBuffs.Buff(player.getPersistentDataContainer().get
					(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER),player);
			if (player.getPersistentDataContainer().get
					(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1) {
				togglewalk.put(player.getName(), true);
			}
		}
			
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
			public void run () {
				PassiveBuffs.Buff(player.getPersistentDataContainer().get
						(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER),player);
			}
		},10);
	}
}
