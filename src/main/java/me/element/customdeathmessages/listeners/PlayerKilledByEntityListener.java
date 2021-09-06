package me.element.customdeathmessages.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.element.customdeathmessages.CustomDeathMessages;
import me.element.customdeathmessages.enums.VersionEnums;

public class PlayerKilledByEntityListener implements Listener {

	public CustomDeathMessages plugin;

	public PlayerKilledByEntityListener (CustomDeathMessages plugin) 
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeathByMob (EntityDamageByEntityEvent event)
	{
		if (plugin.getConfig().getBoolean("enable-global-messages")) 
		{
			if (event.getEntity() instanceof Player && !(event.getDamager() instanceof Player))
			{
				Player victim = (Player) event.getEntity();

				if (victim.getHealth() <= event.getFinalDamage())
				{
					int versionInt = plugin.getServerVersion().getVersionInt();

					EntityType entity = event.getDamager().getType();
					String path = "not-implemented-messages"; // Most likely the mob isn't implemented so we will set this instead.

					boolean hasCustomName = event.getDamager().getCustomName() != null;

					switch(entity.toString()) {
						case "SLIME":
							path = "slime-messages";
							break;

						case "SKELETON":
							path = "skeleton-messages";
							break;

						case "MAGMA_CUBE":
							path = "magmacube-messages";
							break;

						case "SPIDER":
							path = "spider-messages";
							break;

						case "CAVE_SPIDER":
							path = "cavespider-messages";
							break;

						case "WITHER":
							path = "witherboss-messages";
							break;

						case "DRAGON_FIREBALL": // Since 1.11
						case "ENDER_DRAGON":
							path = "dragon-messages";
							break;

						case "PRIMED_TNT":
							path = "tnt-messages";
							break;

						case "CREEPER":
							path = "creeper-messages";
							break;

						case "GHAST":
							path = "ghast-messages";
							break;

						case "ENDERMAN":
							path = "enderman-messages";
							break;

						case "SILVERFISH":
							path = "silverfish-messages";
							break;

						case "WITCH":
							path = "witch-messages";
							break;

						case "GUARDIAN":
							path = "guardian-messages";
							break;

						case "IRON_GOLEM":
							path = "golem-messages";
							break;

						case "ENDERMITE":
							path = "endermite-messages";
							break;

						case "ZOMBIE":
							path = "zombie-messages";
							break;

						case "WOLF":
							path = "wolf-messages";
							break;

						case "BLAZE":
							path = "blaze-messages";
							break;

						case "FIREBALL":
							path = "fireball-messages";
							break;

						case "TIPPED_ARROW": // Since 1.9
						case "SPECTRAL_ARROW": // Since 1.9
						case "ARROW":
							if (((Projectile) event.getDamager()).getShooter() instanceof Skeleton) {
								path = "skeleton-messages";
							} else if (versionInt >= VersionEnums.VERSION_110.getVersionInt() && ((Projectile) event.getDamager()).getShooter() instanceof Stray) {
								path = "stray-messages";
							} else if (versionInt >= VersionEnums.VERSION_114.getVersionInt() && ((Projectile) event.getDamager()).getShooter() instanceof Pillager) {
								path = "pillager-messages";
							} else {
								path = "arrow-messages";
							}
							break;
						case "PIG_ZOMBIE": // Removed in 1.15 without an alternative. Left here for backwards compatablilty for legacy versions below 1.15. Completely removed in 1.16 in favor of zombie piglins
							path = "pigman-messages";
							break;

						case "SHULKER": // Since 1.9
							path = "shulker-messages";
							break;

						case "STRAY": // Since 1.10
							path = "stray-messages";
							break;

						case "LLAMA_SPIT": // Since 1.11
						case "LLAMA": // Since 1.11
						case "TRADER_LLAMA": //Since 1.14
							path = "llama-messages";
							break;

						case "WITHER_SKELETON": // Since 1.11 - differation of wither skeletons and skeletons
							path = "lightning-messages";
							break;

						case "ELDER_GUARDIAN": // Since 1.11 - differation of elder guardians and guardians
							path = "elderguardian-messages";
							break;

						case "ZOMBIE_VILLAGER": // Since 1.11 - differation of zombies and zombie villagers
							path = "zombievillager-messages";
							break;

						case "VEX": // Since 1.11
							path = "vex-messages";
							break;

						case "VINDICATOR": // Since 1.11
							path = "vindicator-messages";
							break;

						case "HUSK": // Since 1.11
							path = "husk-messages";
							break;

						case "EVOKER": // Since 1.11
							path = "evoker-messages";
							break;

						case "ILLUSIONER": // Since 1.12
							path = "illusioner-messages";
							break;

						case "PHANTOM": // Since 1.13
							path = "phantom-messages";
							break;

						case "DROWNED": // Since 1.13
							path = "drowned-messages";
							break;

						case "PUFFERFISH": // Since 1.13
							path = "pufferfish-messages";
							break;

						case "POLAR_BEAR": // Since 1.13
							path = "polar-bear-messages";
							break;

						case "DOLPHIN": // Since 1.13
							path = "dolphin-messages";
							break;

						case "PILLAGER": // Since 1.14
							path = "pillager-messages";
							break;

						case "RAVAGER": // Since 1.14
							path = "ravenger-messages";
							break;

						case "PANDA": // Since 1.14
							path = "panda-messages";
							break;

						case "BEE": // Since 1.15
							path = "bee-messages";
							break;

						case "PIGLIN": // Since 1.16
							path = "piglin-messages";
							break;

						case "ZOGLIN": // Since 1.16
							path = "zoglin-messages";
							break;

						case "HOGLIN": // Since 1.16
							path = "hoglin-messages";
							break;

						case "ZOMBIFIED_PIGLIN": // Since 1.16
							path = "zombified-piglin-messages";
							break;

						case "GOAT": // Since 1.17
							path = "goat-messages";
							break;

						default: // Most likely not implemented. This serves as a placeholder message
							path = "not-implemented-messages";

					}

					if (hasCustomName && plugin.getConfig().getBoolean("enable-custom-name-entity-messages")) {
						if (Bukkit.getServer().getPluginManager().isPluginEnabled("mcMMO") && !event.getDamager().getName().contains("❤") || !Bukkit.getServer().getPluginManager().isPluginEnabled("mcMMO"))
							path = "custom-name-entity-messages"; // Triggers for custom entities. Would recommend setting to true so that the not implemented setting wont be triggered.
					}

					Random rand = new Random();
					List<String> msgs = plugin.getConfig().getStringList(path);
					String message = msgs.get(rand.nextInt(msgs.size()))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%victim-x%", victim.getName())
							.replace("%victim-y%", victim.getName())
							.replace("%victim-z%", victim.getName());

					if (path.equals("custom-name-entity-messages"))
					{
						message = message.replace("%entity-name%", event.getDamager().getName());
					}

					if (path.equals("not-implemented-messages")) {
						message = message.replace( "%entity-name%", entity.toString().toLowerCase().replace("_", " "));
					}

					plugin.deathMessage.clear();
					plugin.deathMessage.put(victim.getName(), message);

					if (plugin.getConfig().getBoolean("developer-mode"))
					{
						Bukkit.broadcastMessage(plugin.deathMessage.get(victim.getName()));
						Bukkit.broadcastMessage(entity.toString());
					}
				}
			}
		}
	}
}
