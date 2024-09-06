package plugin;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveBuffs implements Listener{
	
	
	
	
	public static void Buff(int tribe, Player player) {
		if (tribe == 1) {
			//FIRE
			player.setAllowFlight(false);
			player.getAttribute(Attribute.GENERIC_GRAVITY).setBaseValue(0.08);
			player.getAttribute(Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER).setBaseValue(1);
			player.getAttribute(Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY).setBaseValue(0);
			player.getAttribute(Attribute.PLAYER_SUBMERGED_MINING_SPEED).setBaseValue(0.2);
			player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(1);
			player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);
			player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.42);
			
			player.removePotionEffect(PotionEffectType.WATER_BREATHING);
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			
			
			PotionEffect fireR = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false, false);
			fireR.apply(player);
		} else if (tribe == 2) {
			//WATER
			player.setAllowFlight(false);
			player.getAttribute(Attribute.GENERIC_GRAVITY).setBaseValue(0.08);
			player.getAttribute(Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER).setBaseValue(1);
			player.getAttribute(Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY).setBaseValue(1);
			player.getAttribute(Attribute.PLAYER_SUBMERGED_MINING_SPEED).setBaseValue(1);
			player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(1);
			player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);
			player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.42);
			
			
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			
			PotionEffect waterB = new PotionEffect(PotionEffectType.WATER_BREATHING,PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false, false);
			waterB.apply(player);
			
		} else if (tribe == 3) {
			//WIND
			player.setAllowFlight(true);
			player.getAttribute(Attribute.GENERIC_GRAVITY).setBaseValue(0.06);
			player.getAttribute(Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER).setBaseValue(0);
			player.getAttribute(Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY).setBaseValue(0);
			player.getAttribute(Attribute.PLAYER_SUBMERGED_MINING_SPEED).setBaseValue(0.2);
			player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(1);
			player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);
			player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.8);
			
			
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
			player.removePotionEffect(PotionEffectType.WATER_BREATHING);
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			
			
		} else if (tribe == 4) {
			//EARTH
			player.setAllowFlight(false);
			player.getAttribute(Attribute.GENERIC_GRAVITY).setBaseValue(0.08);
			player.getAttribute(Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER).setBaseValue(1);
			player.getAttribute(Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY).setBaseValue(0);
			player.getAttribute(Attribute.PLAYER_SUBMERGED_MINING_SPEED).setBaseValue(0.2);
			player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(3);
			player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
			player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.42);
			
			
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
			player.removePotionEffect(PotionEffectType.WATER_BREATHING);
			
			
			PotionEffect nightV = new PotionEffect(PotionEffectType.NIGHT_VISION,PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false, false);
			nightV.apply(player);
		} else if (tribe == 5) {
			player.setAllowFlight(false);
			player.getAttribute(Attribute.GENERIC_GRAVITY).setBaseValue(0.03);
			player.getAttribute(Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER).setBaseValue(0);
			player.getAttribute(Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY).setBaseValue(1);
			player.getAttribute(Attribute.PLAYER_SUBMERGED_MINING_SPEED).setBaseValue(1);
			player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(3);
			player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);
			player.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.4);
			
			
			PotionEffect fireR = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false, false);
			fireR.apply(player);
			PotionEffect nightV = new PotionEffect(PotionEffectType.NIGHT_VISION,PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false, false);
			nightV.apply(player);
			PotionEffect waterB = new PotionEffect(PotionEffectType.WATER_BREATHING,PotionEffect.INFINITE_DURATION, Integer.MAX_VALUE, false, false);
			waterB.apply(player);
		}
	}

	
}
