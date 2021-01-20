package me.DMan16.MysticQuestsRacePerms;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	static Main main;
	static final String pluginName = "MysticQuestsRacePerms";
	static final String pluginNameColors = "&6&lMystic&b&lQuests&e&lRace&a&lPerms";
	static Config Config;
	static Permissions PermissionsManager;

	public void onEnable() {
		main = this;
		String versionMC = Bukkit.getServer().getVersion().split("\\(MC:")[1].split("\\)")[0].trim().split(" ")[0].trim();
		if (Integer.parseInt(versionMC.split("\\.")[0]) < 1 || Integer.parseInt(versionMC.split("\\.")[1]) < 16) {
			Utils.chatColorsLogPlugin("&cunsupported version! Please use version 1.16+");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		try {
			Config = new Config();
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE,Utils.chatColorsPlugin("Could not save config file! Error:"));
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		PermissionsManager = new Permissions();
		registerListeners();
		new CommandListener();
		Utils.chatColorsLogPlugin("&aLoaded, running on version: &f" + versionMC + "&a, Java version: &f" + javaVersion());
	}
	
	private String javaVersion() {
		String javaVersion = "";
		Iterator<Entry<Object,Object>> systemProperties = System.getProperties().entrySet().iterator();
		while (systemProperties.hasNext() && javaVersion.isEmpty()) {
			Entry<Object,Object> property = (Entry<Object,Object>) systemProperties.next();
			if (property.getKey().toString().equalsIgnoreCase("java.version")) javaVersion = property.getValue().toString();
		}
		return javaVersion;
	}

	public void onDisable() {
		Utils.chatColorsLogPlugin("&aDisabling...");
		PermissionsManager.unregisterPermissions();
		Utils.chatColorsLogPlugin("&aDisabed successfully!");
	}

	private void registerListeners() {
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new GeneralListener(),this);
	}
}