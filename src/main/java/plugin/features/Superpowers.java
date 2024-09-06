package plugin.features;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import plugin.MyPlugin;
import plugin.util.YMLUtil;

public class Superpowers implements CommandExecutor, Listener {

	public static final String YML_FILENAME = "superpowers.yml";
	
	private static String ymlFilePath = "";

	/**
	 * The list of available superpowers.
	 * You can change these as you wish.
	 */

	public static PotionEffectType[] LEVEL_1_POWERS = {
			PotionEffectType.WATER_BREATHING,
			PotionEffectType.NIGHT_VISION,
			PotionEffectType.HASTE,
			PotionEffectType.JUMP_BOOST,
			PotionEffectType.INVISIBILITY
	};

	private Map<UUID, Superpower> powers;
	private Random RNG;

	// Keeps it so only one version of the Superpowers class can exist.
	private static Superpowers instance;

	private Superpowers(MyPlugin plugin) {
		this.powers = new HashMap<UUID, Superpower>();
		this.RNG = new Random();

		// Registers the command and events to the server
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getCommand("superpower").setExecutor(this);
		
		instance = this;

		loadPlayerData();
	}

	/**
	 * Loads the Superpower data from the file.
	 * If the Superpower data is already loaded, this will return the loaded instance.
	 * 
	 * @param plugin An instance of the plugin's main class
	 */

	public static Superpowers load(MyPlugin plugin) {

		if (instance != null) {
			return instance;
		}
		else {
			
			// Sets the proper file path
			ymlFilePath = plugin.getDataFolder() + File.separator + YML_FILENAME;
			
			// Load data from json file.
			return new Superpowers(plugin);
		}
	}

	/**
	 * Loads the player data from the .yml file
	 */

	private void loadPlayerData() {

		// loads the yml file.
		YamlConfiguration yml = YMLUtil.loadConfig(ymlFilePath);

		ConfigurationSection powersSection = yml.getConfigurationSection("powers");

		if (powersSection == null) {
			// create the section (the file is likely being created if this runs)
			powersSection = yml.createSection("powers");
		}

		for (String key : powersSection.getKeys(false)) {

			ConfigurationSection playerRecord = (ConfigurationSection) powersSection.get(key);

			// Every player has their own UUID. This is a very powerful tool.
			UUID uuid = UUID.fromString(key);

			// Gather individual entry data
			String powerName = playerRecord.getString("superpower");
			int level = playerRecord.getInt("level");

			// Create and store the entry in our map.
			Superpower entry = new Superpower(PotionEffectType.getByName(powerName), level);

			powers.put(uuid, entry);

		}
	}

	/**
	 * Saves the superpower data
	 */

	public void saveData() {

		// Save data to yml file.
		YamlConfiguration yml = YMLUtil.loadConfig(ymlFilePath);

		ConfigurationSection powersSection = yml.getConfigurationSection("powers");
		
		if (powersSection == null) {
			// Create the section (if this runs, the file is likely being created)
			powersSection = yml.createSection("powers");
		}

		// Saves each player's superpower
		for (Map.Entry<UUID, Superpower> entry : powers.entrySet()) {

			String key = entry.getKey().toString();
			Superpower superpower = entry.getValue();

			ConfigurationSection cs = powersSection.createSection(key);
			cs.set("superpower", superpower.type.getName());
			cs.set("level", superpower.level);

		}

		// Saves the file to the disk
		YMLUtil.saveConfig(yml, ymlFilePath);

	}

	/**
	 * Applies a specified player's superpower to themselves.
	 * If the player does not have a superpower, nothing happens.
	 * 
	 * @param player The target player
	 * @return true if the player successfully applied a superpower, otherwise false
	 */

	public boolean applySuperpower(Player player) {

		UUID playerId = player.getUniqueId();

		// Player has a superpower, so we can apply it
		if (powers.containsKey(playerId)) {

			Superpower power = powers.get(playerId);

			// Creates an infinite potion effect
			PotionEffect effect = new PotionEffect(power.type, PotionEffect.INFINITE_DURATION, power.level);
			player.addPotionEffect(effect);
			power.setEnabled(true);

			return true;
		}

		return false;
	}

	/**
	 * Clears a specified player's superpower, if it exists.
	 * If the player does not have a superpower, nothing happens.
	 * If the player does not currently have their superpower enabled, nothing happens.
	 * 
	 * @param player The target player
	 * @return true if the player's effect was removed, otherwise false.
	 */

	public boolean clearSuperpower(Player player) {

		UUID playerId = player.getUniqueId();

		if (powers.containsKey(playerId)) {

			Superpower power = powers.get(playerId);

			// Check if player has a potion effect with the same type as their superpower
			PotionEffect currentEffect = player.getPotionEffect(power.type);

			if (currentEffect == null) {
				// player does not have this effect
				return false;
			}
			else {
				
				// player has an effect matching their superpower's type
				if (currentEffect.getDuration() == PotionEffect.INFINITE_DURATION) {
					
					// this is a superpower, since superpowers have infinite duration
					player.removePotionEffect(currentEffect.getType());

					/* 
					 * Players with the "Water Breathing" superpower get night vision when in water.
					 * So, this removes the infinite night vision if they have it.
					 */
					
					if (currentEffect.getType() == PotionEffectType.WATER_BREATHING) {

						PotionEffectType nightVisType = PotionEffectType.NIGHT_VISION;
						
						if (isPlayerUsingSuperpower(player, nightVisType)) {
							
							// Only removes if player has infinite night vision.
							player.removePotionEffect(nightVisType);

						}
					}

					power.setEnabled(false);
					return true;

				}
				else {
					// they have the right effect, but it's not a superpower. do nothing
					return false;
				}
			}
		}

		return false;

	}

	@EventHandler
	public void giveFirstPower(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		if (powers.containsKey(player.getUniqueId())) {
			// player already has a superpower
			return;
		}
		else {

			/*
			 * Give player a random new power.
			 * This should always happen when a player joins for the first time.
			 */

			int random = RNG.nextInt(LEVEL_1_POWERS.length);

			// Register the powers (level 1 potion is 0 in code)
			Superpower power = new Superpower(LEVEL_1_POWERS[random], 0);

			// To balance the powers, some have higher levels.
			if (power.type == PotionEffectType.JUMP_BOOST) {
				// set jump to level 3
				power.level = 2;
			}
			else if (power.type == PotionEffectType.HASTE) {
				// set haste to 2
				power.level = 1;
			}

			powers.put(player.getUniqueId(), power);

			// applies the superpower
			applySuperpower(player);

			player.sendMessage(ChatColor.GREEN + "You've been given a " + ChatColor.GOLD + "superpower!!");
			player.sendMessage(ChatColor.GREEN + "Your power is: " + ChatColor.LIGHT_PURPLE + power.type.getName());
		}
	}

	/**
	 * Some Superpowers have special abilities on top of their base superpower.
	 */
	
	@EventHandler
	public void giveBuffsOnMove(PlayerMoveEvent event) {

		Player player = event.getPlayer();

		UUID playerId = player.getUniqueId();

		if (!powers.containsKey(playerId)) {
			// player does not have any superpower.
			return;
		}
		else {
			// player has a power. is it water breathing?
			Superpower superpower = powers.get(playerId);
			PotionEffectType powerType = superpower.type;

			if (powerType == PotionEffectType.WATER_BREATHING) {

				if (superpower.isEnabled()) {
					
					PotionEffectType nightVisType = PotionEffectType.NIGHT_VISION;
					
					boolean inWater = player.isInWater();
					boolean hasEffect = isPlayerUsingSuperpower(player, nightVisType);
					
					if (inWater && !hasEffect) {
						// w = true, e = false -> give eff
						
						PotionEffect nightVision = new PotionEffect(nightVisType, 
								PotionEffect.INFINITE_DURATION, 0);
						
						player.addPotionEffect(nightVision);
					}
					else if (!inWater && hasEffect) {
						// w = false, e = true -> remove effect
						
						player.removePotionEffect(nightVisType);
					}
				}
				
				/*
				if (player.isInWater() && superpower.isEnabled()) {
					
					// We only want to give night vision if it isn't already active
					PotionEffectType nightVisType = PotionEffectType.NIGHT_VISION;
					
					if (!isPlayerUsingSuperpower(player, nightVisType)) {
						
						// give night vision, since the player is in water
						PotionEffect nightVision = new PotionEffect(nightVisType,
								PotionEffect.INFINITE_DURATION, 0);
						
						player.addPotionEffect(nightVision);
					}
				}
				else {
					// The player is not in water, but they might still have residual night vision.
					if (superpower.isEnabled()) {
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					}
				}
				*/
			}
			else if (powerType == PotionEffectType.JUMP_BOOST) {

				double jumpDistance = event.getTo().getY() - event.getFrom().getY();

				if (jumpDistance >= 0.5 && !player.hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
					
					// 60 ticks = 3 seconds
					PotionEffect slowFall = new PotionEffect(PotionEffectType.SLOW_FALLING, 60, 1);
					player.addPotionEffect(slowFall);
				}
			}
		}
	}
	
	/**
	 * A quick utility method to check if a player has the effect of their superpower.
	 * 
	 * @param player The player to check
	 * @param power The type of superpower to check
	 * 
	 * @return true if the player has the effect, otherwise false.
	 */
	
	private boolean isPlayerUsingSuperpower(Player player, PotionEffectType power) {
	
		// Gets the player's PotionEffect of this type (will be null if they don't have it)
		PotionEffect superpowerEffect = player.getPotionEffect(power);
		
		return superpowerEffect != null && superpowerEffect.getDuration() 
				== PotionEffect.INFINITE_DURATION;
	}

	@EventHandler
	public void clearPowerOnDeath(PlayerDeathEvent event) {

		// This fixes a bug where players can't use /sp once they die.
		clearSuperpower(event.getEntity());
	}

	@EventHandler
	public void clearPowerOnQuit(PlayerQuitEvent event) {

		// Makes it so players don't keep superpowers once they leave
		clearSuperpower(event.getPlayer());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;
			
			if (command.getName().equalsIgnoreCase("superpower")) {

				UUID playerId = player.getUniqueId();

				if (powers.containsKey(playerId)) {
					// The player has a superpower
					Superpower power = powers.get(playerId);

					// Toggle the superpower
					if (power.isEnabled()) {
						clearSuperpower(player);
					}
					else {
						applySuperpower(player);
					}

					// Everything works! :)
					return true;
				}
				else {
					// Player does not have a superpower.
					return false;
				}
			}
		}

		return false;
	}

	/**
	 * The Superpower class stores data about the powers for each player.
	 * Since each player has their own superpower object, we can use it to
	 * toggle whether the superpower is on (see {@link Superpower#isEnabled}).
	 */

	private class Superpower {

		public PotionEffectType type;
		public int level;

		/**
		 *  Does the player currently have a superpower enabled
		 */
		private boolean isEnabled;

		/**
		 * Simple default constructor. Disables the superpower by default.
		 * @param type The superpower's effect
		 * @param level The level of the superpower
		 */

		public Superpower(PotionEffectType type, int level) {
			this.type = type;
			this.level = level;
			this.isEnabled = false;
		}

		public boolean isEnabled() {
			return isEnabled;
		}

		public void setEnabled(boolean isEnabled) {

			this.isEnabled = isEnabled;
		}
	}
}