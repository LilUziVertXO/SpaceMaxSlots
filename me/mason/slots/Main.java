package me.mason.slots;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.mason.slots.events.MaxPlayersCMD;
import me.mason.slots.events.MaxPlayersListener;
import net.md_5.bungee.api.ChatColor;

public class Main
  extends JavaPlugin
{
  private File configfile;
  private FileConfiguration config;
  int alert = 0;
  public static Plugin instance;
  
  @Override
public void onEnable()
  {
	  instance = this;
	  Bukkit.getConsoleSender().sendMessage("[SpaceMaxSlots] Has activated!");
		    announcements();
		    createFiles();
		    maxplayers();  
  }
  
  @Override
public void onDisable() {
	  instance = null;
	  Bukkit.getConsoleSender().sendMessage("[SpaceMaxSlots] Has deactivated!");
  }
  
  public String cc(String string)
  {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
  

  
  public void announcements() {
	  //add this?
  }
  
  public void maxplayers()
  {
    getCommand("maxplayers").setExecutor(new MaxPlayersCMD(this));
    Bukkit.getServer().getPluginManager().registerEvents(new MaxPlayersListener(this), this);
  }
  

  @Override
public FileConfiguration getConfig()
  {
    return this.config;
  }
  
  @Override
public void reloadConfig()
  {
    this.config = YamlConfiguration.loadConfiguration(this.configfile);
  }
  
  @Override
public void saveConfig()
  {
    try
    {
      this.config.save(this.configfile);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  public void createFiles()
  {
    this.configfile = new File(getDataFolder(), "config.yml");
    if (!getDataFolder().exists()) {
      getDataFolder().mkdirs();
    }
    if (!this.configfile.exists())
    {
      this.configfile.getParentFile().mkdirs();
      saveResource("config.yml", false);
      getLogger().info("[SpaceMaxSlots] config.yml not found, creating...");
    }
    this.config = new YamlConfiguration();
    try
    {
      this.config.load(this.configfile);
    }
    catch (InvalidConfigurationException e)
    {
      e.printStackTrace();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
