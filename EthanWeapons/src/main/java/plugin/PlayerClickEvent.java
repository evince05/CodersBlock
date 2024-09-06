package plugin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class PlayerClickEvent implements Listener{
	
	BukkitScheduler bukS = Bukkit.getScheduler();

	
	@EventHandler
	public void pC(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		ItemStack item = player.getInventory().getItemInMainHand();
		
		MyPlugin plugin = MyPlugin.getPlug();
		
		if (item != null) {
			if (item.getItemMeta()!= null) {
				//HOLLOW PURPLE fans
				
				if (item.getItemMeta().getItemName().equals("Hollow Purple")) {
					if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						
						player.getInventory().remove(item);
						
						final Location playerLoc = player.getLocation();
						
						
						final Location behind = playerLoc.clone().subtract(new Location(player.getWorld(), playerLoc.getDirection().getX(),-1,playerLoc.getDirection().getZ()).multiply(2));
						
						
						Location redLoc = behind.clone().add(new Location(player.getWorld(), playerLoc.getDirection().getZ()*2,0,playerLoc.getDirection().getX()*2));
						
						
						Location blueLoc = behind.clone().subtract(new Location(player.getWorld(), playerLoc.getDirection().getZ()*2,0,playerLoc.getDirection().getX()*2));

						
						player.setWalkSpeed(0);
						PotionEffect jumpslow = new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false);
						jumpslow.apply(player);
						
						BlockData lapseblue =  Material.BLUE_STAINED_GLASS.createBlockData();
						FallingBlock lapsedblue = player.getWorld().spawnFallingBlock(blueLoc, lapseblue);
						lapsedblue.setVisibleByDefault(false);
						lapsedblue.setGravity(false);
						lapsedblue.setDamagePerBlock(0);
						lapsedblue.setVelocity(new Vector(0,0,0));
						
						
						
						DustOptions redDust = new DustOptions(Color.RED, 5);
						BlockData reversalred =  Material.RED_STAINED_GLASS.createBlockData();
						FallingBlock reversered = player.getWorld().spawnFallingBlock(redLoc, reversalred);
						reversered.setGravity(false);
						reversered.setDamagePerBlock(0);
						reversered.setVelocity(new Vector(0,0,0));
						
						
						
						Block light = player.getWorld().getBlockAt(behind);
						if (light.getType().equals(Material.AIR)) {
							light.setType(Material.LIGHT);
						}
						player.getWorld().playSound(redLoc, Sound.BLOCK_CONDUIT_ACTIVATE, 1F, 1F);
						player.getWorld().playSound(redLoc, Sound.ENTITY_BREEZE_CHARGE, 1F, 1F);
						player.getWorld().playSound(redLoc, Sound.ENTITY_BREEZE_WIND_BURST, 1F, 1F);
						Bukkit.broadcastMessage(ChatColor.RED + "CURSED TECHNIQUE REVERSAL:");
						
						bukS.runTaskLater(plugin, new Runnable() {
							public void run() {
								Bukkit.broadcastMessage(ChatColor.RED + "RED!!!");
							}
						},30);
						
						player.getWorld().spawnParticle(Particle.DUST,
								redLoc,100,1.2,1.2,1.2,redDust);
						
						bukS.runTaskLater(plugin, new Runnable() {
							public void run() {
								DustOptions blueDust = new DustOptions(Color.BLUE, 5);
								lapsedblue.setVisibleByDefault(true);
								player.getWorld().playSound(blueLoc, Sound.BLOCK_CONDUIT_ACTIVATE, 1F, 1F);
								player.getWorld().playSound(blueLoc, Sound.ENTITY_BREEZE_CHARGE, 1F, 1F);
								player.getWorld().playSound(blueLoc, Sound.ENTITY_BREEZE_WIND_BURST, 1F, 1F);
								player.getWorld().spawnParticle(Particle.DUST,
										blueLoc,100,1.2,1.2,1.2,blueDust);
								Bukkit.broadcastMessage(ChatColor.BLUE + "CURSED TECHNIQUE LAPSE:");
							}
						}, 80);
						
						bukS.runTaskLater(plugin, new Runnable() {
							public void run() {
								Bukkit.broadcastMessage(ChatColor.BLUE + "BLUE!!!");
							}
						},110);
						
						bukS.runTaskLater(plugin, new Runnable() {
							public void run() {
								Location playerLoc = player.getLocation();
								lapsedblue.setVelocity(new Vector(playerLoc.getDirection().getZ(),-0,playerLoc.getDirection().getX()).multiply(0.1));
								reversered.setVelocity(new Vector(-playerLoc.getDirection().getZ(),-0,-playerLoc.getDirection().getX()).multiply(0.1));								
								Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "HOLLOW TECHNIQUE: ");
							}
						},200);
						
						bukS.runTaskLater(plugin, new Runnable() {
							public void run() {
								lapsedblue.remove();
								reversered.remove();
								
								BlockData hollowpurple =  Material.PURPLE_STAINED_GLASS.createBlockData();
								FallingBlock hollopurple = player.getWorld().spawnFallingBlock(behind, hollowpurple);
								hollopurple.setGravity(false);
								hollopurple.setDamagePerBlock(0);
								hollopurple.setVelocity(new Vector(0,0,0));
								player.getWorld().playSound(blueLoc, Sound.BLOCK_CONDUIT_ACTIVATE, 1F, 1F);
								player.getWorld().playSound(blueLoc, Sound.ENTITY_BREEZE_CHARGE, 1F, 1F);
								player.getWorld().playSound(blueLoc, Sound.ENTITY_BREEZE_WIND_BURST, 1F, 1F);
								player.getWorld().playSound(blueLoc, Sound.BLOCK_END_GATEWAY_SPAWN, 0.8F, 1F);
								DustOptions purpdust = new DustOptions(Color.PURPLE, 5);
								player.getWorld().spawnParticle(Particle.DUST,
										behind,100,1.2,1.2,1.2,purpdust);
								
								Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "PURPLE!!!!");
								bukS.runTaskLater(plugin, new Runnable() {
									public void run() {
										hollopurple.remove();
									}
								},40);
							}
						},220);
						
						bukS.runTaskLater(plugin, new Runnable() {
							public void run() {
								
								BlockIterator blockIt = new BlockIterator(player.getWorld(),behind.clone().toVector().add(playerLoc.clone().getDirection().multiply(12)),playerLoc.getDirection(),0,400);
								DustOptions purpdust = new DustOptions(Color.PURPLE, 5);
								light.setType(Material.AIR);
								player.setWalkSpeed(0.2F);
								player.removePotionEffect(PotionEffectType.JUMP_BOOST);
								player.getWorld().playSound(blueLoc, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1F, 1F);
								int id = bukS.scheduleSyncRepeatingTask(plugin, new Runnable() {
									public void run() {
										
										for(int g = 0; g < 2; g++) {
											if (blockIt.hasNext()) {
												Block block = blockIt.next();
												player.getWorld().createExplosion(block.getLocation(), 10);
												player.getWorld().spawnParticle(Particle.DUST,
													block.getLocation(),300,9,9,9,purpdust);
												block.setType(Material.LIGHT);
												bukS.runTaskLater(plugin, new Runnable() {
													public void run() {
														block.setType(Material.AIR);
													}
												},10);
											}
										}
									}
								}, 1, 0);
								if (!blockIt.hasNext()) {
									bukS.cancelTask(id);
								}
								
							}
						},260);
					}
				}
				
				
				
				
				//LASER GUN
				
				
				if (item.getItemMeta().getItemName().equals(ChatColor.RED + "Laser Gun")) {
					if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					
						RayTraceResult result = player.getWorld().rayTraceEntities(player.getLocation().add(
								new Vector(0,1.8,0)).add(player.getLocation().getDirection().multiply(3)), player.getLocation().getDirection(), 100, 5);						
						if (result.getHitEntity() != null) {
							if (result.getHitEntity() instanceof LivingEntity) {
								LivingEntity entity = (LivingEntity)result.getHitEntity();
								entity.damage(3,player);
							}
						}
						double length = result.getHitPosition().distance(player.getLocation().add(
								new Vector(0,1.8,0)).add(player.getLocation().getDirection().multiply(3)).toVector());
						Location loc = player.getLocation().add(
							new Vector(0,1.8,0)).add(player.getLocation().getDirection().multiply(3));
						for (double i = 0; i < length; i++) {
							
							DustOptions reddust = new DustOptions(Color.RED, 3);
							player.getWorld().spawnParticle(Particle.DUST,
								loc.add(player.getLocation().getDirection().multiply(length)),2,0,0,0,reddust);
						}
					}
				}
				// MAGIC BOW
				if (item.getItemMeta().getItemName().equals("Magic Bow") && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
					ItemStack[] charInv = player.getInventory().getContents();
					boolean hasArrow = false;
					for (int i = 0; i < charInv.length;i++) {
						if (charInv[i] != null) {
							if (charInv[i].getItemMeta().getItemName().equals("Magic Arrow")) {
								hasArrow = true;
							}
						}
					}
					
					if (!hasArrow) {
						
						player.dropItem(true);
						player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
						for (int i = 0; i < 100; i++) player.playSound(player.getLocation(), Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, 1.0F, 1.0F);
						for (int i = 0; i < 10; i++)player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.8F);
						PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS,200,1);
						PotionEffect darkness = new PotionEffect(PotionEffectType.DARKNESS,200,1);
						player.setFireTicks(150);
						player.spawnParticle(Particle.ANGRY_VILLAGER, player.getLocation(), 20,1,2, 1);
						player.spawnParticle(Particle.ENCHANT, player.getLocation(), 100,1,2, 1);
						player.damage(3);
						blindness.apply(player);
						darkness.apply(player);
						player.sendMessage(ChatColor.UNDERLINE.toString() + ChatColor.YELLOW.toString() + "YOU ARE NOT WORTHY OF THIS BOW" );
					
					
					} else {
						
						
						int taskid = bukS.scheduleSyncRepeatingTask(plugin, new Runnable () {
							public void run() {
								player.spawnParticle(Particle.ENCHANT, player.getLocation().add(0, 1, 0), 50,0.2,0.5, 0.2);
								}
							}, 1, 3);
						bukS.runTaskLater(plugin, new Runnable () {
							public void run() {
								bukS.cancelTask(taskid);
							}
						}, 30);
					}
				}
			}
		}
	}
}
