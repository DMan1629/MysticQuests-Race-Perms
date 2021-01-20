package me.DMan16.MysticQuestsRacePerms;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
	public CommandListener() {
		PluginCommand command = Main.main.getCommand(Main.pluginName);
		command.setExecutor(this);
		command.setTabCompleter(new TabComplete());
		command.setDescription(Utils.chatColors(Main.pluginNameColors + " &fcommand"));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) && !Main.PermissionsManager.hasCommandPermission((Player) sender)) return false;
		if (args.length == 0 || !TabComplete.base.contains(args[0].toLowerCase()))
			Utils.chatColorsPlugin(sender,"&fAvailable commands:\n" + Main.pluginNameColors + " &e" + TabComplete.base.get(0) +
					" &b- &fdisplay the config options\n" + Main.pluginNameColors + " &e" + TabComplete.base.get(1) + " &b- &freload the config");
		else if (args[0].equalsIgnoreCase(TabComplete.base.get(0))) displayConfig(sender);
		else if (args[0].equalsIgnoreCase(TabComplete.base.get(1))) reloadConfig(sender);
		return true;
	}
	
	private void displayConfig(CommandSender sender) {
		HashMap<String,Method> methods = new HashMap<String,Method>();
		for (Method method : Main.Config.getClass().getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers())) methods.put(method.getName(),method);
		}
		List<String> config = new ArrayList<String>();
		for (Field field : Main.Config.getClass().getDeclaredFields()) {
			boolean isString = field.getGenericType().getTypeName().toLowerCase().contains("string");
			try {
				config.add("&a" + field.getName() + "&f: " + (isString ? "\"" : "") + methods.get(field.getName()).invoke(Main.Config) + (isString ? "&f\"" : ""));
			} catch (Exception e) {}
		}
		Utils.chatColors(sender,String.join("\n",config));
	}
	
	private void reloadConfig(CommandSender sender) {
		try {
			Main.Config.readConfig();
			Utils.chatColorsPlugin(sender,"&aconfig reloaded!");
		} catch (IOException e) {
			Utils.chatColorsPlugin(sender,"&cunable to reload config!");
		}
	}
}