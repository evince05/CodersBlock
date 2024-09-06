package plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public class InventoryItemClick implements Listener{

	
	
	BukkitScheduler bukkitsched = Bukkit.getServer().getScheduler();
	
	public void tribePicked(Player player, int tribe, Inventory inventory, ItemStack item) {
		inventory.clear();
		player.removePotionEffect(PotionEffectType.SLOWNESS);
		player.removePotionEffect(PotionEffectType.JUMP_BOOST);
		player.removePotionEffect(PotionEffectType.MINING_FATIGUE);
		player.closeInventory();
		
		if (player.getInventory().contains(item)) {
			bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
				public void run() {
					System.out.println(item.getItemMeta().getItemName());
					player.getInventory().remove(item);
					
				}
			},1);
		}
		
		World world = player.getWorld();
		
		world.spawnEntity(player.getLocation(), EntityType.FIREWORK_ROCKET);
		
		
		if (tribe != 5) {
			bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
				public void run() {
					world.playSound(player.getLocation().add(0,7,0), Sound.BLOCK_END_GATEWAY_SPAWN, 5F, 1F);
					world.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 5F, 1F);
					world.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 5F, 1F);
					
					
					Particle particle = null;
					double speed = 0.5;
					if (tribe == 1) {
						particle = Particle.FLAME;
						speed = 0.1;
					} else if (tribe == 2) {
						particle = Particle.SCULK_CHARGE_POP;
						speed = 0.3;
					} else if (tribe == 3) {
						particle = Particle.EFFECT;
						speed = 2;
					} else if (tribe == 4) {
						particle = Particle.SOUL;
						speed = 0.2;
					}
					
					
					world.spawnParticle(particle , player.getLocation().add(0,7,0),1000,0,0,0,speed);
					world.spawnParticle(Particle.CLOUD , player.getLocation().add(0,7,0),1000,0,0,0,1);
					
					Block block = world.getBlockAt(player.getLocation().add(0,7,0));
					if (block.getType() == Material.AIR) {
						block.setType(Material.LIGHT);
					}
						
					bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
						public void run() {
							block.setType(Material.AIR);
						}
					}, 3);
				}
			}, 40);
		} else {
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "A powerful being has invaded your realm...");
			world.playSound(new Location(world, 0,0,0), Sound.AMBIENT_CAVE, 1000000F, 1F);
			world.playSound(new Location(world, 0,0,0), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1000000F, 0.2F);
		}
		PassiveBuffs.Buff(tribe, player);
		
	}
	
	@EventHandler
	public void invClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player)event.getWhoClicked();
		if (item.getItemMeta()!= null) {
			if (item.getItemMeta().getItemName().equals(ChatColor.RED + "Fire Tribe")) {
				event.setCancelled(true);
				player.getPersistentDataContainer().set(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),
						PersistentDataType.INTEGER,1);
				player.sendTitle(ChatColor.RED + ChatColor.UNDERLINE.toString() + "FLAME TRIBE CHOSEN", null, 20, 40, 20);
				tribePicked(player, 1, event.getInventory(), item);
				
				
			} else if (item.getItemMeta().getItemName().equals(ChatColor.BLUE + "Water Tribe")) {
				event.setCancelled(true);
				player.getPersistentDataContainer().set(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),
						PersistentDataType.INTEGER,2);
				player.sendTitle(ChatColor.BLUE + ChatColor.UNDERLINE.toString() + "WATER TRIBE CHOSEN", "", 20, 40, 20);
				tribePicked(player, 2, event.getInventory(), item);
				
				
			} else if (item.getItemMeta().getItemName().equals(ChatColor.BOLD + "Air Tribe")) {
				event.setCancelled(true);
				player.getPersistentDataContainer().set(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),
						PersistentDataType.INTEGER,3);
				player.sendTitle(ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "AIR TRIBE CHOSEN", "", 20, 40, 20);
				tribePicked(player, 3, event.getInventory(), item);
				
				
			} else if (item.getItemMeta().getItemName().equals(ChatColor.DARK_GREEN + "Earth Tribe")){
				event.setCancelled(true);
				player.getPersistentDataContainer().set(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),
						PersistentDataType.INTEGER,4);
				player.sendTitle(ChatColor.DARK_GREEN + ChatColor.UNDERLINE.toString() + "EARTH TRIBE CHOSEN", "", 20, 40, 20);
				tribePicked(player, 4, event.getInventory(), item);
				
				
			} else if (item.getItemMeta().getItemName().equals("OP")){
				event.setCancelled(true);
				player.getPersistentDataContainer().set(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),
						PersistentDataType.INTEGER,5);
				player.sendTitle(ChatColor.DARK_PURPLE + ChatColor.UNDERLINE.toString() + "the fun begins :)", "", 20, 40, 20);
				tribePicked(player, 5, event.getInventory(), item);
				
			}
		}
		
	}
	
	@EventHandler
	public void onClose (InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		if (inv.getItem(1) != null) {
			if (inv.getItem(1).getItemMeta()!=null) {
				if (inv.getItem(1).getItemMeta().getItemName().equals(ChatColor.RED + "Fire Tribe")) {
					
					bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
						public void run() {
							event.getPlayer().openInventory(inv);
						}
					}, 1);
				}
			}
		}
	}
	

}
