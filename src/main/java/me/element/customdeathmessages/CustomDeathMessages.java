package me.element.customdeathmessages;

import me.element.customdeathmessages.commands.CustomDeathMessagesCommand;
import me.element.customdeathmessages.enums.VersionEnums;
import me.element.customdeathmessages.listeners.PlayerDeathListener;
import me.element.customdeathmessages.listeners.PlayerKilledByEntityListener;
import me.element.customdeathmessages.listeners.PlayerLoginListener;
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

	public void checkVersion()
	{
		if (getServerVersion() == VersionEnums.OTHER_VERSION)
		{	
			getLogger().warning("[CustomDeathMessages] This server version is unsupported by CustomDeathMessages. Though this should work with newer versions of minecraft, new mobs that can cause death will be missing until next update.");
			// getServer().getPluginManager().disablePlugin(this);
		}
	}
}