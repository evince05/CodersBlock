package plugin;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.joml.Math;

public class AirDoubleJump implements Listener{
	Dictionary<String, Boolean> pJumped = new Hashtable<>();
	Dictionary<String, Integer> fastRun = new Hashtable<>();
	Dictionary<String, Integer> waterHeal = new Hashtable<>();
	Dictionary<String, Integer> lavaHeal= new Hashtable<>();
	Dictionary<String, Boolean> waterHealing = new Hashtable<>();
	Dictionary<String, Boolean> lavaHealing = new Hashtable<>();
	
	Dictionary<String, Dictionary<String, Boolean>> opStackedMultipliers = new Hashtable<>();
	BukkitScheduler bukkitsched = Bukkit.getServer().getScheduler();
	HashMap<String, Boolean> togglewalk = FireWalkEnable.getHash();
	
	@EventHandler
	public void secondJump(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
			if (pJumped.get(player.getName())== false) {
				if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 3) {
					
					int taskid = bukkitsched.scheduleSyncRepeatingTask(SuperPowers.getPlugin(), new Runnable () {
						public void run() {
							player.setFallDistance(10F);
							pJumped.put(event.getPlayer().getName(), true);
						}
					}, 0, 1);
					bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
						public void run() {
							bukkitsched.cancelTask(taskid);
						}
					},15);
					player.setVelocity(player.getVelocity().setY(0.7));
					player.getWorld().spawnParticle(Particle.EXPLOSION, player.getLocation(),3,0.5,0,0.5);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BREEZE_DEFLECT, 1F, 1F);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BREEZE_DEATH, 0.2F, 1F);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BREEZE_LAND, 1F, 1F);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1F, 1F);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void pJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		pJumped.put(player.getName(), false);
		fastRun.put(player.getName(), -1);
		waterHeal.put(player.getName(), -1);
		waterHealing.put(player.getName(), false);
		lavaHealing.put(player.getName(), false);
		lavaHeal.put(player.getName(), -1);
		
		Dictionary<String, Boolean> multipliers = new Hashtable<>();
		opStackedMultipliers.put(player.getName(), multipliers);
		multipliers.put("lavaRun", false);
		multipliers.put("waterSwim", false);
		multipliers.put("airRun", false);
	}
	
	@EventHandler
	public void pQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		pJumped.remove(player.getName());
		fastRun.remove(player.getName());
		waterHeal.remove(player.getName());
		waterHealing.remove(player.getName());
		lavaHealing.remove(player.getName());
		lavaHeal.remove(player.getName());
		opStackedMultipliers.remove(player.getName());

	}
	
	@EventHandler
	public void landed(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		Dictionary<String, Boolean> multipliers = opStackedMultipliers.get(player.getName());
		
		
		
		if (player.isFlying() && (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR)) {
			player.setFlying(false);
		}
		
		
		// PARTICLE EFFECTS
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1) {
			player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 1, 0.2, 0, 0.2, 0.03);
		}
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 2) {
			player.getWorld().spawnParticle(Particle.FALLING_WATER, player.getLocation(), 1, 0.2, 0, 0.2, 0.2);
		}
		 
		if (player.getFallDistance() == 0F&& player.getVelocity().getY() < 0) {
			pJumped.put(player.getName(), false);
		}
		
		
		
		
		
		// WATER SWIMMING
		
		
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 2 ||
				player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
			if (waterHealing.get(player.getName()) == null) waterHealing.put(player.getName(), false);
			if (player.isInWater() && !waterHealing.get(player.getName())) {
				waterHealing.put(player.getName(), true);
				if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1) {
					player.setWalkSpeed(0.5F);
				} else {
					multipliers.put("waterSwim", true);
				}
				PotionEffect nv = new PotionEffect(PotionEffectType.NIGHT_VISION,PotionEffect.INFINITE_DURATION,8,false,false);
				nv.apply(player);
				waterHeal.put(player.getName(), bukkitsched.scheduleSyncRepeatingTask(SuperPowers.getPlugin(), new Runnable() {
					public void run() {
						if (!player.isDead() && player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {player.setHealth(Math.floor(player.getHealth())+1);}
						}
				},15, 10));
			} else if (!player.isInWater()) {
				if (waterHeal.get(player.getName()) != null) {
					bukkitsched.cancelTask(waterHeal.get(player.getName()));
					waterHealing.put(player.getName(), false);
					waterHeal.put(player.getName(),-1);
					if (player.getPersistentDataContainer().get
					(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) != 5) {
						player.setWalkSpeed(0.2F);
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					} else {
						multipliers.put("waterSwim", false);
					}
					
				}
			}
		}
		
		
		
		// LAVA RUNNING
		
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1 ||
				player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
			if (togglewalk.get(player.getName()) == null) togglewalk.put(player.getName(), true);
			if (togglewalk.get(player.getName())){
				for (int x = -2; x <= 2; x++) {
					for (int y = -2; y <= 2; y++) {
						if (player.getWorld().getBlockAt(player.getLocation().subtract(x, 0.8, y)).getType() == Material.LAVA ) {
							Block block = player.getWorld().getBlockAt(player.getLocation().subtract(x,0.8,y));
							if (block.getBlockData() instanceof Levelled) {
								Levelled bData = (Levelled)block.getBlockData();
								if (bData.getLevel() == 0) {
									block.setType(Material.OBSIDIAN);
									bukkitsched.runTaskLater(SuperPowers.getPlugin(), new Runnable() {
										public void run() {
											block.setType(Material.LAVA);
										}
									}, 100);
								}
							}
							
						}
					}
				}
			}
			if (player.getWorld().getBlockAt(player.getLocation()).getType() == Material.LAVA ||
					player.getWorld().getBlockAt(player.getLocation().subtract(0, 0.8, 0)).getType() == Material.OBSIDIAN) {
				if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1 ) {
					player.setWalkSpeed(0.4F);
				} else {
					multipliers.put("lavaRun", true);
				}
				
				if (lavaHealing.get(player.getName()) == null) lavaHealing.put(player.getName(), false);
				if (!lavaHealing.get(player.getName())) {
					
				
					lavaHealing.put(player.getName(), true);
					lavaHeal.put(player.getName(), bukkitsched.scheduleSyncRepeatingTask(SuperPowers.getPlugin(), new Runnable() {
						public void run() {
							if (!player.isDead() && player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {player.setHealth(Math.floor(player.getHealth())+1);}
							}
					},5, 5));
				}
			} else {
				if (lavaHeal.get(player.getName()) != null) {
					bukkitsched.cancelTask(lavaHeal.get(player.getName()));
					lavaHealing.put(player.getName(), false);
					lavaHeal.put(player.getName(),-1);
					if (player.getPersistentDataContainer().get
							(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 1) {
						player.setWalkSpeed(0.2F);
					} else {
						multipliers.put("lavaRun", false);
					}
					
				}
			}
		}
		
		
		
		// OP PEOPLE
		
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
			float increase = 1F;
			if (multipliers.get("lavaRun")) increase += 0.7F;
			if (multipliers.get("waterSwim")) increase += 0.7F;
			if (multipliers.get("airRun")) increase += 1.5F;
			player.setWalkSpeed(0.2F * increase);
		}
		
	}
	
	@EventHandler 
	public void sprinting(PlayerToggleSprintEvent event) {
		Player player = event.getPlayer();
		Dictionary<String, Boolean> multipliers = opStackedMultipliers.get(player.getName());
		
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 3) {
			if (event.isSprinting()) {
				player.setWalkSpeed(0.6F);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BREEZE_IDLE_AIR, 1F, 1F);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1F, 1F);
				fastRun.put(player.getName(), bukkitsched.scheduleSyncRepeatingTask(SuperPowers.getPlugin()
						, new Runnable() {
					public void run() {
						player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 4, 0, 0, 0, 0.2);
						player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 1, 0, 0, 0, 0.2);
					}
				},2,2));
			} else {
				player.setWalkSpeed(0.2F);
				bukkitsched.cancelTask(fastRun.get(player.getName()));
			}
		}
		if (player.getPersistentDataContainer().get
				(new NamespacedKey(SuperPowers.getPlugin(), "tribe"),PersistentDataType.INTEGER) == 5) {
			if (event.isSprinting()) {
				multipliers.put("airRun", true);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.4F, 1F);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.1F, 1F);
				fastRun.put(player.getName(), bukkitsched.scheduleSyncRepeatingTask(SuperPowers.getPlugin()
						, new Runnable() {
					public void run() {
						player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 4, 0, 0, 0, 0.2);
						player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 1, 0, 0, 0, 0.2);
					}
				},2,2));
			} else {
				bukkitsched.cancelTask(fastRun.get(player.getName()));
				multipliers.put("airRun", false);
			}
		}
		
	}
}
