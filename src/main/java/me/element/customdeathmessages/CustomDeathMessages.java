package me.element.customdeathmessages;

import me.element.customdeathmessages.commands.CustomDeathMessagesCommand;
import me.element.customdeathmessages.enums.VersionEnums;
import me.element.customdeathmessages.listeners.PlayerDeathListener;
import me.element.customdeathmessages.listeners.PlayerKilledByEntityListener;
import me.element.customdeathmessages.listeners.PlayerLoginListener;
import me.element.customdeathmessages.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

public class CustomDeathMessages extends JavaPlugin {

	public HashMap<String, String> deathMessage = new HashMap<String, String>();

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		checkVersion();
		registerStatistics();
		registerCommandsListeners();
		
		String version = getServer().getClass().getPackage().getName().substring(getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
		Bukkit.getLogger().info("[CustomDeathMessages] Enabling version support for " + Bukkit.getServer().getVersion() + "; " + version);
	}

	public VersionEnums getServerVersion()
	{
		if (getServer().getVersion().contains("1.8")) 
		{
			return VersionEnums.VERSION_18;
		}
		else if (getServer().getVersion().contains("1.9")) 
		{
			return VersionEnums.VERSION_19;
		}
		else if (getServer().getVersion().contains("1.10")) 
		{
			return VersionEnums.VERSION_110;
		}
		else if (getServer().getVersion().contains("1.11")) 
		{
			return VersionEnums.VERSION_111;
		}
		else if (getServer().getVersion().contains("1.12")) 
		{
			return VersionEnums.VERSION_112;
		}
		else if (getServer().getVersion().contains("1.13")) 
		{
			return VersionEnums.VERSION_113;
		}
		else if (getServer().getVersion().contains("1.14")) 
		{
			return VersionEnums.VERSION_114;
		}
		else if (getServer().getVersion().contains("1.15")) 
		{
			return VersionEnums.VERSION_115;
		}
		else if (getServer().getVersion().contains("1.16"))
		{
			return VersionEnums.VERSION_116;
		}
		else if (getServer().getVersion().contains("1.17"))
		{
			return VersionEnums.VERSION_117;
		}

		else return VersionEnums.OTHER_VERSION;
	}

	public void registerCommandsListeners()
	{		
		getServer().getPluginManager().registerEvents(new PlayerDeathListener (this), this);
		getServer().getPluginManager().registerEvents(new PlayerKilledByEntityListener (this), this);
		getServer().getPluginManager().registerEvents(new PlayerLoginListener (this), this);
		Objects.requireNonNull(getCommand("cdm")).setExecutor(new CustomDeathMessagesCommand(this));
		Objects.requireNonNull(getCommand("cdm")).setTabCompleter(new CustomDeathMessagesCommand(this));
	}

	public void registerStatistics()
	{
		boolean metricsEnable = getConfig().getBoolean("metrics.enable");
		if (!metricsEnable) {
			Bukkit.getLogger().info("[CustomDeathMessages] Metrics has been disabled.");
			return;
		}
		int pluginId = 7287;
		Metrics metrics = new Metrics(this, pluginId);

		metrics.addCustomChart(new Metrics.SimplePie("head_drop_percentage", () -> String.valueOf(getConfig().getDouble("drop-head-percentage"))));
		metrics.addCustomChart(new Metrics.SimplePie("give_killer_speed", () -> getConfig().getBoolean("give-killer-speed") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("heart_sucker", () -> getConfig().getBoolean("heart-sucker") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("do_lightning", () -> getConfig().getBoolean("do-lightning") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("enable_global_messages", () -> getConfig().getBoolean("enable-global-messages") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("enable_pvp_messages", () -> getConfig().getBoolean("enable-pvp-messages") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("enable_entity_name_messages", () -> getConfig().getBoolean("enable-custom-name-entity-messages") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("enable_original_hover_message",() -> getConfig().getBoolean("original-hover-message") ? "Enabled" : "Disabled" ));
		metrics.addCustomChart(new Metrics.SimplePie("enable_item_tooltip_message",() -> getConfig().getBoolean("enable-item-hover") ? "Enabled" : "Disabled" ));
		Bukkit.getLogger().info("[CustomDeathMessages] Metrics has been enabled and is registered on https://bstats.org/plugin/bukkit/CustomDeathMessages/7287.");
	}

	public void checkVersion()
	{
		if (getServerVersion() == VersionEnums.OTHER_VERSION)
		{	
			getLogger().warning("[CustomDeathMessages] This server version is unsupported by CustomDeathMessages. Though this should work with newer versions of minecraft, new mobs that can cause death will be missing until next update.");
			// getServer().getPluginManager().disablePlugin(this);
		}
	}
}