package me.element.customdeathmessages.listeners;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.element.customdeathmessages.CustomDeathMessages;
import me.element.customdeathmessages.enums.VersionEnums;
import me.element.customdeathmessages.other.HexChat;
import me.element.customdeathmessages.other.JsonChat;
import me.element.customdeathmessages.other.MsgToJson;
import me.element.customdeathmessages.other.SkullCreator;

public class PlayerDeathListener implements Listener {

	public CustomDeathMessages plugin;

	public PlayerDeathListener (CustomDeathMessages plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath (PlayerDeathEvent event)
	{
		Player victim = event.getEntity();
		Player killer = event.getEntity().getKiller();
		Location playerLocation = victim.getLocation();

		if (victim instanceof Player && killer instanceof Player && plugin.getConfig().getBoolean("enable-pvp-messages") && !killer.getName().equals(victim.getName()))  // Make sure the killer doesnt match the victims name
		{
			victim.sendMessage(HexChat.translateHexCodes(plugin.getConfig().getString("victim-message")
					.replace("%killer%", victim.getName())
					.replace("%killer-nick%", victim.getDisplayName())
					.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
					.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
					.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
					.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
					.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
					.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ())), plugin));


			killer.sendMessage(HexChat.translateHexCodes(plugin.getConfig().getString("killer-message")
					.replace("%victim%", victim.getName())
					.replace("%victim-nick%", victim.getDisplayName())
					.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
					.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
					.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
					.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
					.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
					.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ())), plugin));
		}

		double percent = plugin.getConfig().getDouble("drop-head-percentage"); // percent to drop head
		{
			Random rand = new Random();
			double randomDouble = rand.nextDouble(); // used to see if percent is bigger

			if (randomDouble <= percent) { // calculate to drop or not

				String headName = HexChat.translateHexCodes(plugin.getConfig().getString("head-name")
						.replace("%victim%", victim.getName()), plugin);

				if (plugin.getConfig().getBoolean("developer-mode")) // for debugging
					Bukkit.broadcastMessage("HEAD DROPPED");

				ItemStack skull = SkullCreator.itemFromUuid(victim.getUniqueId(), plugin);
				SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
				skullMeta.setDisplayName(headName);
				skull.setItemMeta(skullMeta);

				playerLocation.getWorld().dropItemNaturally(victim.getLocation(), skull);
			}
		}

		if (plugin.getConfig().getBoolean("do-lightning"))
		{
			playerLocation.getWorld().strikeLightningEffect(playerLocation);
		}

		if (plugin.getConfig().getBoolean("enable-global-messages"))
		{
			if (killer instanceof Player && !killer.getName().equals(victim.getName())) // Make sure the killer doesnt match the victims name
			{
				ItemStack killWeapon = getKillWeapon(killer);

				if (killWeapon.getType() != Material.AIR)
				{
					String weaponName = killWeapon.getItemMeta().hasDisplayName() ? killWeapon.getItemMeta().getDisplayName() : WordUtils.capitalize(killWeapon.getType().name().replaceAll("_", " ").toLowerCase());
					Random rand = new Random();
					List<String> msgs = plugin.getConfig().getStringList("global-pvp-death-messages");

					String msg = msgs.get(rand.nextInt(msgs.size()))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%killer%", killer.getName())
							.replace("%killer-nick%", killer.getDisplayName())
							.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
							.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
							.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
							.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
							.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
							.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ()));

					msg = HexChat.translateHexCodes(msg, plugin);

					if (plugin.getConfig().getBoolean("developer-mode"))
						Bukkit.broadcastMessage("msg test: " + msg);

					if (plugin.getConfig().getBoolean("enable-item-hover"))
					{
						event.setDeathMessage("");
						Bukkit.spigot().broadcast(new JsonChat().getTextComponent(msg, killWeapon, "kill-weapon"));
						return;
					}
					msg = msg.replace("%kill-weapon%", weaponName);
					event.setDeathMessage(msg);
				}
				else
				{
					String weaponName = killWeapon.getItemMeta().hasDisplayName() ? killWeapon.getItemMeta().getDisplayName() : WordUtils.capitalize(killWeapon.getType().name().replaceAll("_", " ").toLowerCase());
					Random rand = new Random();
					List<String> msgs = plugin.getConfig().getStringList("melee-death-messages");

					String msg = msgs.get(rand.nextInt(msgs.size()))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%killer%", killer.getName())
							.replace("%killer-nick%", killer.getDisplayName())
							.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
							.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
							.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
							.replace("%killer-x%", String.valueOf(killer.getLocation().getBlockX()))
							.replace("%killer-y%", String.valueOf(killer.getLocation().getBlockY()))
							.replace("%killer-z%", String.valueOf(killer.getLocation().getBlockZ()));

					msg = HexChat.translateHexCodes(msg, plugin);
					if (plugin.getConfig().getBoolean("developer-mode"))
						Bukkit.broadcastMessage("msg test: " + msg);

					if (plugin.getConfig().getBoolean("enable-item-hover"))
					{
						event.setDeathMessage("");
						Bukkit.spigot().broadcast(new JsonChat().getTextComponent(msg, killWeapon, "kill-weapon"));
						return;
					}
					msg = msg.replace("%kill-weapon%", weaponName);
					event.setDeathMessage(msg);
				}
			}
			else
			{
				DamageCause cause = DamageCause.CUSTOM;
				if (victim.getLastDamageCause() != null)
					cause = victim.getLastDamageCause().getCause();

				String path;

				switch (cause.toString()) {
					case "FALL":
						path = "fall-damage-messages";
						break;

					case "DROWNING":
						path = "drowning-messages";
						break;

					case "LAVA":
						path = "lava-messages";
						break;

					case "SUFFOCATION":
						path = "suffocation-messages";
						break;

					case "WITHER":
						path = "wither-messages";
						break;

					case "FIRE":
						path = "fire-messages";
						break;

					case "FIRE_TICK":
						path = "fire-tick-messages";
						break;

					case "STARVATION":
						path = "starvation-messages";
						break;

					case "CONTACT":
						path = "";
						break;

					case "MAGIC":
						path = "potion-messages";
						break;

					case "VOID":
						path = "void-messages";
						break;

					case "LIGHTNING":
						path = "lightning-messages";
						break;

					case "FLY_INTO_WALL": // Since 1.9
						path = "elytra-messages";
						break;

					case "HOT_FLOOR": // Since 1.10
						path = "magma-block-messages";
						break;

					case "FREEZE": // Since 1.17
						path = "freeze-messages";
						break;
					case "CUSTOM":
					default:
						path = "not-implemented-damagecause-message";
				}

				String msg = "";

				Random rand = new Random();
				List<String> msgs = plugin.getConfig().getStringList(path);
				if (path.equals("not-implemented-damagecause-message")) {
					if (plugin.deathMessage.get(victim.getName()) != null)
					{
						msg = HexChat.translateHexCodes(plugin.deathMessage.get(victim.getName()), plugin);
						plugin.deathMessage.clear();
					} else {
						msg = msgs.get(rand.nextInt(msgs.size()))
								.replace("%victim%", victim.getName())
								.replace("%victim-nick%", victim.getDisplayName())
								.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
								.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
								.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()))
								.replace("%damagecause-name%", cause.toString().toLowerCase().replace("_", " "));
						msg = HexChat.translateHexCodes(msg, plugin);
					}
				} else {
					msg = msgs.get(rand.nextInt(msgs.size()))
							.replace("%victim%", victim.getName())
							.replace("%victim-nick%", victim.getDisplayName())
							.replace("%victim-x%", String.valueOf(victim.getLocation().getBlockX()))
							.replace("%victim-y%", String.valueOf(victim.getLocation().getBlockY()))
							.replace("%victim-z%", String.valueOf(victim.getLocation().getBlockZ()));
					msg = HexChat.translateHexCodes(msg, plugin);
				}
				if (plugin.getConfig().getBoolean("original-hover-message"))
				{
					String previous = event.getDeathMessage();
					Bukkit.spigot().broadcast((MsgToJson.translate(msg, previous)));
					event.setDeathMessage("");
				}
				else event.setDeathMessage(msg);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public ItemStack getKillWeapon(Player killer)
	{
		if (plugin.getServerVersion().getVersionInt() >= VersionEnums.VERSION_19.getVersionInt())
			return killer.getInventory().getItemInMainHand();
		else
			return killer.getInventory().getItemInHand();
	}
}
