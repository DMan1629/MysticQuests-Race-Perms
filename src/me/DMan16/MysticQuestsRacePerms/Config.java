package me.DMan16.MysticQuestsRacePerms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.potion.PotionEffectType;

public class Config extends ConfigLoader {
	private String commandPerm;
	private boolean commandOp;
	//Horse Taming
	private String horseTamePerm;
	private boolean horseTameOp;
	private boolean horseTameDonkey;
	private boolean horseTameLlama;
	private boolean horseTameMule;
	private boolean horseTameSkeletonHorse;
	private boolean horseTameTraderLlama;
	private boolean horseTameZombieHorse;
	private String horseTameMsg;
	private boolean horseTamePM;
	private boolean horseTameActionBar;
	//Diamond to Netherite
	private String NetheritePerm;
	private boolean NetheriteOp;
	private String NetheriteMsg;
	private boolean NetheritePM;
	private boolean NetheriteActionBar;
	//Mudkip used Rain Dance
	private String waterPerm;
	private boolean waterOp;
	private List<Effect> inWaterEffects;
	private int outOfWaterDamageInterval;
	private double outOfWaterDamage;
	private boolean outOfWaterDamageKill;
	private List<Effect> outOfWaterEffects;
	
	public final List<String> perms;
	public Config() throws IOException  {
		super("config.yml",Main.pluginName + " config file");
		readConfig();
		perms = Arrays.asList(horseTamePerm,NetheritePerm,waterPerm);
	}
	
	public void readConfig() throws IOException {
		loadConfig();
		
		String permPrefix = Main.pluginName.toLowerCase();
		//Commands
		String[] startMSG = {"Please read each option's information and input type before changing","Default values are in [], type in ()",
				"Any option that is filled wrong or removed will be replaced by the [default] value!",
				"Disabled permissions will still use the Op option if it is turned on",
				"To use formatting codes (https://minecraft.gamepedia.com/Formatting_codes) in String values, do &<code>,","or use &#<hex> to use hex color codes" +
				"(https://htmlcolorcodes.com/color-picker)","","",
				"Command - command permission requirement"," ",
				"commandPerm - the required permission to use the command; leave blank to disable (String) [" + permPrefix + ".command]",
				"commandOp - do Op players have this permission by default (true/false) [true]"};
		commandPerm = ((String) addNewConfigOption(config,"commandPerm",permPrefix + ".command",startMSG)).toLowerCase();
		commandOp = ((Boolean) addNewConfigOption(config,"commandOp",Boolean.valueOf(true),null)).booleanValue();

		//Horse Taming
		String[] horseTameMSG = {"Horse Taming - disallow horse taming without proper permission"," ",
				"horseTamePerm - the required permission to tame a horse; leave blank to disable (String) [" + permPrefix + ".horsetame]",
				"horseTameOp - do Op players have this permission by default (true/false) [true]",
				"horseTameDonkey - is permission required to tame Donkeys (true/false) [false]",
				"horseTameLlama - is permission required to tame Llamas (true/false) [false]",
				"horseTameMule - is permission required to tame Mules (true/false) [false]",
				"horseTameSkeletonHorse - is permission required to tame Skeleton Horses (true/false) [false]",
				"horseTameTraderLlama - is permission required to tame Trader Llamas (true/false) [false]",
				"horseTameZombieHorse - is permission required to tame Zombie Horses (true/false) [false]",
				"horseTameMsg - the message sent to the player when trying to tame a horse without the permission; leave blank to disable (String) " +
				"[&cYou do not have permission to tame this animal!]",
				"horseTamePM - should a personal message be sent to the player when they try to tame a horse without permission (true/false) [true]",
				"horseTameActionBar - should a message be displayed on the player's Action Bar when they try to tame a horse without permission (true/false) [true]"};
		
		horseTamePerm = ((String) addNewConfigOption(config,"horseTamePerm",permPrefix + ".horsetame",horseTameMSG)).toLowerCase();
		horseTameOp = ((Boolean) addNewConfigOption(config,"horseTameOp",Boolean.valueOf(true),null)).booleanValue();
		horseTameDonkey = ((Boolean) addNewConfigOption(config,"horseTameDonkey",Boolean.valueOf(false),null)).booleanValue();
		horseTameLlama = ((Boolean) addNewConfigOption(config,"horseTameLlama",Boolean.valueOf(false),null)).booleanValue();
		horseTameMule = ((Boolean) addNewConfigOption(config,"horseTameMule",Boolean.valueOf(false),null)).booleanValue();
		horseTameSkeletonHorse = ((Boolean) addNewConfigOption(config,"horseTameSkeletonHorse",Boolean.valueOf(false),null)).booleanValue();
		horseTameTraderLlama = ((Boolean) addNewConfigOption(config,"horseTameTraderLlama",Boolean.valueOf(false),null)).booleanValue();
		horseTameZombieHorse = ((Boolean) addNewConfigOption(config,"horseTameZombieHorse",Boolean.valueOf(false),null)).booleanValue();
		horseTameMsg = ((String) addNewConfigOption(config,"horseTameMsg","&cYou do not have permission to tame horses!",null));
		horseTamePM = ((Boolean) addNewConfigOption(config,"horseTamePM",Boolean.valueOf(true),null)).booleanValue();
		horseTameActionBar = ((Boolean) addNewConfigOption(config,"horseTameActionBar",Boolean.valueOf(true),null)).booleanValue();
		
		//Diamond to Netherite
		String[] NetheriteMSG = {"Diamond to Netherite - disallow upgrading Diamond to Netherite without proper permission"," ",
				"NetheritePerm - the required permission to upgrade Diamond stuff to Netherite; leave blank to disable (String) [" + permPrefix + ".Netherite]",
				"NetheriteOp - do Op players have this permission by default (true/false) [true]",
				"NetheriteMsg - the message sent to the player when upgrading Diamond stuff to Netherite without the permission; leave blank to disable " +
				"(String) [&cYou do not have permission to upgrade &bDiamond &cto &7Netherite&c!]",
				"NetheritePM - should a personal message be sent to the player when they try upgrading Diamond to Netherite without permission (true/false) [true]",
				"NetheriteActionBar - should a message be displayed on the player's Action Bar when they try upgrading Diamond to Netherite without permission " +
				"(true/false) [true]"};
		
		NetheritePerm = ((String) addNewConfigOption(config,"NetheritePerm",permPrefix + ".netherite",NetheriteMSG)).toLowerCase();
		NetheriteOp = ((Boolean) addNewConfigOption(config,"NetheriteOp",Boolean.valueOf(true),null)).booleanValue();
		NetheriteMsg = ((String) addNewConfigOption(config,"NetheriteMsg","&cYou do not have permission to upgrade &bDiamond &cto &7Netherite&c!",null));
		NetheritePM = ((Boolean) addNewConfigOption(config,"NetheritePM",Boolean.valueOf(true),null)).booleanValue();
		NetheriteActionBar = ((Boolean) addNewConfigOption(config,"NetheriteActionBar",Boolean.valueOf(false),null)).booleanValue();
		
		//Mudkip used Rain Dance
		String[] waterMSG = {"Water - special effects when in/out of water"," ",
				"waterPerm - the required permission to have all the water effects applied; players in Creative/Spectator mode will not take damage (String) " +
				"[" + permPrefix + ".water]","waterOp - do Op players have this permission by default (true/false) [false]",
				"inWaterEffects - list of all effects applied to the player with this permission when in water; levels of 0 or less disable the effect " +
				"(list of effects) [CONDUIT_POWER 1, DOLPHINS_GRACE 1]",
				"outOfWaterDamageInterval - how often, in seconds, is the damage applied to players with this permission; values of 0 or less disable the damage " +
				"(Integer) [1]",
				"outOfWaterDamage - how much damage do players with this permission take when out of water every interval: 1 = half heart; " +
				"values of 0 or less disable the damage (Double) [1]",
				"outOfWaterDamageKill - can the damage kill the player (true/false) [true]",
				"outOfWaterEffects - list of all effects applied to the player with this permission when out of water; levels of 0 or less disable the effect " +
				"(list of effects) []"," ","List of effect names for \"inWaterEffects\" and \"outOfWaterEffects\": " +
				"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html"};
		
		waterPerm = ((String) addNewConfigOption(config,"waterPerm",permPrefix + ".water",waterMSG)).toLowerCase();
		waterOp = ((Boolean) addNewConfigOption(config,"waterOp",Boolean.valueOf(false),null)).booleanValue();
		inWaterEffects = readEffects(addNewConfigOption(config,"inWaterEffects",Arrays.asList(new Effect(PotionEffectType.CONDUIT_POWER,1,2).toMap(),
				new Effect(PotionEffectType.DOLPHINS_GRACE,1,2).toMap()),null),2);
		outOfWaterDamageInterval = ((int) addNewConfigOption(config,"outOfWaterDamageInterval",Integer.valueOf(1),null));
		outOfWaterDamage = ((double) addNewConfigOption(config,"outOfWaterDamage",Double.valueOf(1.0),null));
		outOfWaterDamageKill = ((Boolean) addNewConfigOption(config,"outOfWaterDamageKill",Boolean.valueOf(true),null)).booleanValue();
		outOfWaterEffects = readEffects(addNewConfigOption(config,"outOfWaterEffects",Arrays.asList(new Effect(PotionEffectType.GLOWING,0,
				outOfWaterDamageInterval + 1).toMap()),null),outOfWaterDamageInterval + 1);
		
		writeConfig();
	}
	
	private List<Effect> readEffects(List<Map<String,Object>> effects, int interval) {
		List<Effect> temp = new ArrayList<Effect>();
		for (Map<String,Object> effect : effects) {
			for (Entry<String,Object> entry : effect.entrySet()) if (entry.getValue() == null) entry.setValue("");
			try {
				temp.add(new Effect(effect,interval));
			} catch (Exception e) {}
		}
		return new ArrayList<Effect>(temp);
	}
	
	public String commandPerm() {
		return commandPerm;
	}
	
	public boolean commandOp() {
		return commandOp;
	}
	
	public String horseTamePerm() {
		return horseTamePerm;
	}
	
	public boolean horseTameOp() {
		return horseTameOp;
	}
	public boolean horseTameDonkey() {
		return horseTameDonkey;
	}

	public boolean horseTameLlama() {
		return horseTameLlama;
	}

	public boolean horseTameMule() {
		return horseTameMule;
	}

	public boolean horseTameSkeletonHorse() {
		return horseTameSkeletonHorse;
	}

	public boolean horseTameTraderLlama() {
		return horseTameTraderLlama;
	}

	public boolean horseTameZombieHorse() {
		return horseTameZombieHorse;
	}

	
	public String horseTameMsg() {
		return horseTameMsg;
	}
	
	public boolean horseTamePM() {
		return horseTamePM;
	}
	
	public boolean horseTameActionBar() {
		return horseTameActionBar;
	}
	
	public String NetheritePerm() {
		return NetheritePerm;
	}
	
	public boolean NetheriteOp() {
		return NetheriteOp;
	}
	
	public String NetheriteMsg() {
		return NetheriteMsg;
	}
	
	public boolean NetheritePM() {
		return NetheritePM;
	}
	
	public boolean NetheriteActionBar() {
		return NetheriteActionBar;
	}
	
	public String waterPerm() {
		return waterPerm;
	}
	
	public boolean waterOp() {
		return waterOp;
	}
	
	public List<Effect> inWaterEffects() {
		return inWaterEffects;
	}
	
	public int outOfWaterDamageInterval() {
		return outOfWaterDamageInterval;
	}
	
	public double outOfWaterDamage() {
		return outOfWaterDamage;
	}
	
	public boolean outOfWaterDamageKill() {
		return outOfWaterDamageKill;
	}
	
	public List<Effect> outOfWaterEffects() {
		return outOfWaterEffects;
	}
}