package plugin;

import org.bukkit.plugin.java.JavaPlugin;

import plugin.features.ExplodingHorses;
import plugin.features.SuperStoneTools;
import plugin.items.GhastBlade;
import plugin.items.LegendCrossbow;

/**
 * This is the main class of your Minecraft plugin.
 */

public class MyPlugin extends JavaPlugin {

	@Override
	public void onEnable() {

		/*
		 * This code runs when you start your Minecraft server!
		 */

		setupSession1();
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

	@Override
	public void onDisable() {

		/*
		 * This code runs when you stop your Minecraft server!
		 */
	}
}
