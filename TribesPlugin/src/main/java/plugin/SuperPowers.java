package plugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is the main class of your Minecraft plugin.
 */

public class SuperPowers extends JavaPlugin {
	

	private static SuperPowers plugin;

	
	@Override
	public void onEnable() {
		
		/*
		 * This code runs when you start your Minecraft server!
		 */
		plugin = this;
		new TribeChangeCommand(this);
		new FireWalkEnable(this);
		new SmeltCommand(this);
		
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new AirDoubleJump(), this);
		getServer().getPluginManager().registerEvents(new InventoryItemClick(), this);
		getServer().getPluginManager().registerEvents(new FireAttack(), this);
		getServer().getPluginManager().registerEvents(new AutoOreSmelt(), this);
		getServer().getPluginManager().registerEvents(new PlayerPlacedEvent(), this);
		
	}
	
	@Override
	public void onDisable() {
		
		/*
		 * This code runs when you stop your Minecraft server!
		 */
	}
	
	public static SuperPowers getPlugin() {
		return plugin;
	}
	
	
}
