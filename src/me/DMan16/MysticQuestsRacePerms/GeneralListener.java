package me.DMan16.MysticQuestsRacePerms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneralListener implements Listener {
	NamespacedKey waterDMG = Utils.namespacedKey("water_damage");
	List<String> sentMsg = new ArrayList<String>();
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onHorseTame(EntityTameEvent event) {
		if (event.isCancelled() || !(event.getOwner() instanceof Player)) return;
		boolean Donkey = Main.Config.horseTameDonkey() && (event.getEntity() instanceof Donkey);
		boolean Llama = Main.Config.horseTameLlama() && (event.getEntity() instanceof Llama);
		boolean Mule = Main.Config.horseTameMule() && (event.getEntity() instanceof Mule);
		boolean SkeletonHorse = Main.Config.horseTameSkeletonHorse() && (event.getEntity() instanceof SkeletonHorse);
		boolean TraderLlama = Main.Config.horseTameTraderLlama() && (event.getEntity() instanceof TraderLlama);
		boolean ZombieHorse = Main.Config.horseTameZombieHorse() && (event.getEntity() instanceof ZombieHorse);
		boolean horsey = (event.getEntity() instanceof Horse) || Donkey || Llama || Mule || SkeletonHorse || TraderLlama || ZombieHorse;
		if (!horsey) return;
		Player player = (Player) event.getOwner();
		if (Main.Config.horseTamePerm() == null || Main.Config.horseTamePerm().isEmpty() || Main.PermissionsManager.hasHorseTamePermission(player)) return;
		event.setCancelled(true);
		String msg = Main.Config.horseTameMsg();
		if (msg == null || msg.isEmpty()) return;
		if (Main.Config.horseTamePM()) Utils.chatColors(player,msg);
		if (Main.Config.horseTameActionBar()) Utils.chatColorsActionBar(player,msg);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onNetherite(PrepareSmithingEvent event) {
		if (event.getViewers().size() <= 0) return;
		if (Utils.isNull(event.getInventory().getItem(0)) || Utils.isNull(event.getResult())) return;
		Player player = (Player) event.getViewers().get(0);
		if (Main.Config.NetheritePerm() == null || Main.Config.NetheritePerm().isEmpty() ||
				Main.PermissionsManager.hasNetheritePermission(player)) return;
		if (!event.getInventory().getItem(0).getType().name().toLowerCase().contains("diamond") ||
				!event.getResult().getType().name().toLowerCase().contains("netherite")) return;
		event.setResult(null);
		event.getInventory().setItem(2,null);
		player.updateInventory();
		if (!Utils.isNull(event.getResult()) || sentMsg.contains(player.getUniqueId().toString())) return;
		sentMsg.add(player.getUniqueId().toString());
		new BukkitRunnable() {
			public void run() {
				sentMsg.remove(player.getUniqueId().toString());
			}
		}.runTaskLater(Main.main,5);
		String msg = Main.Config.NetheriteMsg();
		if (msg == null || msg.isEmpty()) return;
		if (Main.Config.NetheritePM()) Utils.chatColors(player,msg);
		if (Main.Config.NetheriteActionBar()) Utils.chatColorsActionBar(player,msg);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerMoveWater(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (Main.Config.outOfWaterDamage() <= 0 || Main.Config.outOfWaterDamageInterval() <= 0) return;
		if (!Main.PermissionsManager.hasWaterPermission(player)) return;
		if (event.getTo().getBlock().getType() != Material.WATER) {
			if (player.getPersistentDataContainer().has(waterDMG,PersistentDataType.STRING)) return;
			player.getPersistentDataContainer().set(waterDMG,PersistentDataType.STRING,"");
			new BukkitRunnable() {
				public void run() {
					if (player.getLocation().getBlock().isLiquid() || !Main.PermissionsManager.hasWaterPermission(player)) {
						player.getPersistentDataContainer().remove(waterDMG);
						this.cancel();
						return;
					}
					if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
					if (Main.Config.outOfWaterDamageKill() || player.getHealth() - Main.Config.outOfWaterDamage() > 0) player.damage(Main.Config.outOfWaterDamage());
					for (Effect effect : Main.Config.outOfWaterEffects()) effect.apply(player);
				}
			}.runTaskTimer(Main.main,Main.Config.outOfWaterDamageInterval() * 20,Main.Config.outOfWaterDamageInterval() * 20);
		} else for (Effect effect : Main.Config.inWaterEffects()) effect.apply(player);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEffectWater(EntityPotionEffectEvent event) {
		if (!(event.getEntity() instanceof Player) || (event.getAction() != EntityPotionEffectEvent.Action.CLEARED &&
				event.getAction() != EntityPotionEffectEvent.Action.REMOVED)) return;
		Player player = (Player) event.getEntity();
		if (!player.getLocation().getBlock().isLiquid() || !Main.PermissionsManager.hasWaterPermission(player)) return;
		for (Effect effect : Main.Config.inWaterEffects()) effect.apply(player);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().getPersistentDataContainer().has(waterDMG,PersistentDataType.STRING)) event.getPlayer().getPersistentDataContainer().remove(waterDMG);
	}
}