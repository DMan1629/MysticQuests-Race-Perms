package me.DMan16.MysticQuestsRacePerms;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Effect {
	public final PotionEffectType type;
	public final int level;
	private final int interval;
	
	public Effect(PotionEffectType type, int level, int interval) {
		this.type = type;
		this.level = level;
		this.interval = interval;
	}
	
	public Effect(Map<String,Object> map, int interval) throws Exception {
		type = PotionEffectType.getByName(((String) map.get("type")).toUpperCase().replace(" ","_"));
		int level;
		try{
			level = Integer.parseInt((String) map.get("level"));
		} catch (Exception e) {
			level = (int) map.get("level");
		}
		this.level = level;
		this.interval = interval;
	}
	
	public Map<String,Object> toMap() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type",type.getName());
		map.put("level",level);
		return map;
	}
	
	public void apply(Player player) {
		if (level <= 0) return;
		PotionEffect effect = new PotionEffect(type,interval * 20,level - 1,true);
		player.addPotionEffect(effect);
	}
	
	@Override
	public String toString() {
		return type.getName() + ": " + level;
	}
}