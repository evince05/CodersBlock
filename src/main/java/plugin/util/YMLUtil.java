package plugin.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class YMLUtil {
	
	/**
	 * Reads a .yml file from the specified path.
	 * If the file does not exist, this creates a new file.
	 * 
	 * @param path The path, starting from the plugin's data folder
	 * 
	 * @return A YamlConfiguration object representing that file, or null if the path is invalid.
	 */
	
	public static YamlConfiguration loadConfig(String path) {
		
		File file = new File(path);
		
		if (!file.exists()) {
			
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Cannot create new config file: " + path);
				e.printStackTrace();
			}
		}
		
		return YamlConfiguration.loadConfiguration(file);
	}
	
	/**
	 * Saves the specified yml config to the given path.
	 * 
	 * If the file does not exist, this saves the yml to a new file at the specified path.
	 * @param yml The yml file to save
	 * @param path The target path
	 */
	
	public static void saveConfig(YamlConfiguration yml, String path) {
		
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();

				yml.save(file);
			} catch (IOException e) {
				System.out.println("Could not save the file to " + path);
				e.printStackTrace();
			}
		}
		else {
			try {
				// Saves the yml file to the disk
				System.out.println("saving yml to " + path);
				yml.save(file);
				
			} catch (IOException e) {
				System.out.println("Error saving yml!");
				e.printStackTrace();
			}
		}
		
		
		
	}
}
