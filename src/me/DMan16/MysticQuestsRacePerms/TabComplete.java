package me.DMan16.MysticQuestsRacePerms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter {
	static List<String> base = Arrays.asList("config","reload");
	
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if ((sender instanceof Player) && !Main.PermissionsManager.hasCommandPermission((Player) sender)) return null;
		List<String> reusltList = new ArrayList<String>();
		for (String cmd : base) if (contains(args[0],cmd)) reusltList.add(cmd);
		return reusltList;
	}
	
	private boolean contains(String arg1, String arg2) {
		return (arg1 == null || arg1.isEmpty() || arg2.toLowerCase().contains(arg1.toLowerCase()));
	}
}