package plugin;

import org.bukkit.plugin.java.JavaPlugin;

import plugin.features.AngryGolem;
import plugin.features.DoubleDrops;
import plugin.features.ExplodingHorses;
import plugin.features.RideTheMob;
import plugin.features.StealTheEgg;
import plugin.features.SuperStoneTools;
import plugin.features.Superpowers;
import plugin.features.UpgradedPickaxes;
import plugin.features.kits.KitManager;
import plugin.items.GhastBlade;
import plugin.items.LegendCrossbow;

/**
 * This is the main class of your Minecraft plugin.
 */

public class MyPlugin extends JavaPlugin {

	private Superpowers superpowerManager;
	
	@Override
	public void onEnable() {

		/*
		 * This code runs when you start your Minecraft server!
		 */

		setupSession1();
		setupSession2();
		setupSession3();
	}

	/**
	 * Loads all the code from session 1 into our server.
	 */
	
	public void setupSession1() {
		
		// Registers events, crafting recipes, etc.
		GhastBlade gb = new GhastBlade(this);
		LegendCrossbow bow = new LegendCrossbow(this);

		/*
		 * Since we don't create any crafting recipes or items here,
		 * we just need to register the events
		 * 
		 * "this" is a copy of our main plugin class. We use it to manage our plugin.
		 */

		getServer().getPluginManager().registerEvents(new SuperStoneTools(), this);
		getServer().getPluginManager().registerEvents(new ExplodingHorses(), this);

	}
	
	/**
	 * Loads all the code from session 2 onto the server.
	 */
	
	public void setupSession2() {
		
		// Loads the superpower class, command, and events
		this.superpowerManager = Superpowers.load(this);
		
		// This is just a feature, so we just need to register the events
		getServer().getPluginManager().registerEvents(new UpgradedPickaxes(), this);
	}
	
	/**
	 * Loads all the code from session 3 onto the server.
	 */
	
	public void setupSession3() {
		
		// Loads the DoubleDrops feature
		DoubleDrops doubleDropMgr = new DoubleDrops(this);
		
		RideTheMob mobRider = new RideTheMob(this);
		StealTheEgg eggRaid = new StealTheEgg(this);
		
		KitManager km = new KitManager(this);
		
		// Note: This registers the AngryGolem feature. It does not create an AngryGolem entity.
		AngryGolem angryGolem = new AngryGolem(this);
	}

	@Override
	public void onDisable() {

		/*
		 * This code runs when you stop your Minecraft server!
		 */
		
		// Save the superpower data
		superpowerManager.saveData();
	}
}
