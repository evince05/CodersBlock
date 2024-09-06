package plugin;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TribePickers {
	
	
	
	public static ItemStack createOP() {
		ItemStack op = new ItemStack(Material.HEAVY_CORE,1);
		
		ItemMeta im = op.getItemMeta();
		
		im.setItemName("OP");
		im.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + 
				ChatColor.MAGIC.toString() + "Unknown" + ChatColor.RESET + ChatColor.DARK_PURPLE.toString() + 
				ChatColor.BOLD.toString() + "?");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + ChatColor.MAGIC.toString() + " + You Stand Above All");
		lore.add(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + ChatColor.MAGIC.toString() + " + None Compare To You");
		lore.add(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + ChatColor.MAGIC.toString() + " + Raise Chaos");
		lore.add(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + " + Defend The Egg");
		im.setEnchantmentGlintOverride(true);
		im.setLore(lore);
		op.setItemMeta(im);
		
		
		return op;
	}
	
	public static ItemStack createFire() {

		ItemStack fire = new ItemStack(Material.FIRE_CHARGE,1);
		
		ItemMeta im = fire.getItemMeta();
		
		im.setItemName(ChatColor.RED + "Fire Tribe");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" + Fire Resistance");
		lore.add(" + Faster Lava Run Speed");
		lore.add(" + Fire Aspect");
		lore.add(" + Auto Smelt");
		im.setEnchantmentGlintOverride(true);
		im.setLore(lore);
		fire.setItemMeta(im);
		
		
		return fire;
	}
	
	public static ItemStack createWater() {
		
		ItemStack water = new ItemStack(Material.HEART_OF_THE_SEA,1);
		
		ItemMeta im = water.getItemMeta();
		
		im.setItemName(ChatColor.BLUE + "Water Tribe");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" + Water Breathing");
		lore.add(" + Faster Swim Speed");
		lore.add(" + Underwater Mining");
		lore.add(" + Heals in Water");
		im.setEnchantmentGlintOverride(true);
		im.setLore(lore);
		water.setItemMeta(im);
		
		return water;
	}

	public static ItemStack createAir() {
		
		ItemStack air = new ItemStack(Material.WIND_CHARGE,1);
		
		ItemMeta im = air.getItemMeta();
		
		im.setItemName(ChatColor.BOLD + "Air Tribe");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" + Faster Run Speed");
		lore.add(" + Slow Falling");
		lore.add(" + Double Jump");
		lore.add(" + No Fall Damage");
		im.setEnchantmentGlintOverride(true);
		im.setLore(lore);
		air.setItemMeta(im);
		
		return air;
	}

	public static ItemStack createEarth() {
		
		ItemStack earth = new ItemStack(Material.PITCHER_POD,1);
		
		ItemMeta im = earth.getItemMeta();
		
		im.setItemName(ChatColor.DARK_GREEN + "Earth Tribe");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" + Faster Mine Speed");
		lore.add(" + Higher Ore Drop Chance");
		lore.add(" + Faster Farming");
		lore.add(" + Night Vision");
		lore.add(" + Free Eggs");
		im.setEnchantmentGlintOverride(true);
		im.setLore(lore);
		earth.setItemMeta(im);
		
		return earth;
	}
}
