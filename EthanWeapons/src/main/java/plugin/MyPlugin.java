package plugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is the main class of your Minecraft plugin.
 */

public class MyPlugin extends JavaPlugin {
	
	public static MyPlugin plugin;
	
	@Override
	public void onEnable() {
		
		
		plugin = this;
		
		new HollowPurple(this);
		new MagicArrows(this);
		new MagicBow(this);
		new LaserObject(this);
		
		getServer().getPluginManager().registerEvents(new PlayerClickEvent(), plugin);
		getServer().getPluginManager().registerEvents(new PlayerShoot(), plugin);
		/*
		 * This code runs when you start your Minecraft server!
		 */
		
	}
	
	@Override
	public void onDisable() {
		
		/*
		 * This code runs when you stop your Minecraft server!
		 */
	}
	
	
	public static MyPlugin getPlug() {
		return plugin;
	}
}
