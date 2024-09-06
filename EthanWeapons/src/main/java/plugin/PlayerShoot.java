package plugin;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class PlayerShoot implements Listener{
	
	Random random = new Random();
	
	MyPlugin plugin = MyPlugin.getPlug();
	BukkitScheduler bukkitsched = Bukkit.getServer().getScheduler();
	
	
	
	
	@EventHandler
	public void PlayerShot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player)event.getEntity();
			
			
			
			if (event.getConsumable() != null) {
				if (event.getConsumable().getItemMeta().getItemName().equals("Magic Arrow") && 
						event.getBow().getItemMeta().getItemName().equals("Magic Bow")) {
						Entity projectile = event.getProjectile();
						projectile.setCustomName("Magic Arrow");
						projectile.setVelocity(projectile.getVelocity().multiply(5));
						for (int i = 0; i < 3; i++) {
							player.playSound(player.getLocation(), Sound.BLOCK_SCULK_SHRIEKER_SHRIEK, 0.2F, random.nextFloat(1F));
							player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT,1F, random.nextFloat(1F));
							player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH,1F, random.nextFloat(1F));
						}
						player.spawnParticle(Particle.SONIC_BOOM, player.getLocation().add(0,1.8,0).add(player.getLocation().getDirection()),10);
						bukkitsched.runTaskLater(plugin, new Runnable () {
							public void run() {
								int taskid = bukkitsched.scheduleSyncRepeatingTask(plugin, new Runnable () {
									public void run() {
										player.spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation().add(0,1.8,0).add(player.getLocation().getDirection()),15);
										}
									}, 1, 1);
								bukkitsched.runTaskLater(plugin, new Runnable () {
									public void run() {
										bukkitsched.cancelTask(taskid);
									}
								}, 6);
								player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1F, random.nextFloat(1F));
								
							}
						}, 10);
						
						
						
						
						
				} else if (event.getConsumable().getItemMeta().getItemName().equals("Magic Arrow")){	
					for (int i = 0; i < 100; i++) player.playSound(player.getLocation(), Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, 1.0F, 1.0F);
					for (int i = 0; i < 10; i++)player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.8F);
					PotionEffect darkness = new PotionEffect(PotionEffectType.NAUSEA,400,3);
					player.setFireTicks(150);
					player.spawnParticle(Particle.ANGRY_VILLAGER, player.getLocation(), 20,1,2, 1);
					player.spawnParticle(Particle.ENCHANT, player.getLocation(), 100,1,2, 1);
					player.getWorld().strikeLightning(player.getLocation());
					darkness.apply(player);
					event.setProjectile(null);
					player.sendMessage(ChatColor.UNDERLINE.toString() + ChatColor.BLACK.toString() + "WHO ARE YOU TO USE MY BLOOD?" );
				}
			}
		}
	}
	

	@EventHandler
	public void ArrowHit(ProjectileHitEvent event) {
		Entity arrow = event.getEntity();
		if (arrow.getName().equals("Magic Arrow")) {
			Location location = arrow.getLocation();
			World world = arrow.getWorld();
			world.playSound(location, Sound.BLOCK_END_PORTAL_SPAWN, 100F, 1F);
			arrow.remove();
			Location particlePos = new Location(world, location.getX(),location.getY(),location.getZ());
			
			int taskid = bukkitsched.scheduleSyncRepeatingTask(plugin, new Runnable () {
				public void run() {
					for (int i = 0; i < 10; i++) {
						double particleStartx = (particlePos.getX() + random.nextDouble(20) - 10);
						double particleStarty = (particlePos.getY() + random.nextDouble(10) - 5);
						double particleStartz = (particlePos.getZ() + random.nextDouble(20) - 10);
						Location particleStart = new Location(world, particleStartx, particleStarty, particleStartz);
						Location particleEnd = particlePos.clone().subtract(particleStart);
						double speed = particleEnd.length()/1000+0.02;
						world.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleStart, 0,particleEnd.getX(),particleEnd.getY(),particleEnd.getZ(),speed);
						world.spawnParticle(Particle.DRAGON_BREATH, particleStart, 0,particleEnd.getX(),particleEnd.getY(),particleEnd.getZ(),speed);
						}
					}
				}, 1, 1);
			bukkitsched.runTaskLater(plugin, new Runnable () {
				public void run() {
					bukkitsched.cancelTask(taskid);
					world.spawnEntity(arrow.getLocation(), EntityType.LIGHTNING_BOLT);
					world.createExplosion(arrow.getLocation(), 2F, true, true);	
					int secondtask = bukkitsched.scheduleSyncRepeatingTask(plugin, new Runnable () {
						public void run() {
							Location randomloc = new Location(world, random.nextDouble(20)-10,0,random.nextDouble(20)-10);
							Location newloc = location.clone().add(randomloc);
							double depth = 20;
							BlockIterator blockIter = new BlockIterator(world, newloc.toVector(), new Vector(0,-0.5,0),0,(int)depth);
							boolean ground = false;
							while (!ground && blockIter.hasNext()) {
								Block block = blockIter.next();
								if (block.getType() != Material.AIR) {
									ground = true;
									depth = block.getLocation().getY();
								}
							}
							newloc.setY(depth);
							world.spawnEntity(newloc, EntityType.LIGHTNING_BOLT);
							world.createExplosion(newloc, 2F, true, true);
							}
						}, 1, 1);
					bukkitsched.runTaskLater(plugin, new Runnable () {
						public void run() {
							bukkitsched.cancelTask(secondtask);
						}
					},120);
				}
			}, 70);
		}
	}

}
