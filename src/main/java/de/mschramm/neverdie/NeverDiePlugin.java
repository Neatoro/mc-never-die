package de.mschramm.neverdie;

import org.bukkit.plugin.java.JavaPlugin;

import de.mschramm.neverdie.commands.LifeCommand;
import de.mschramm.neverdie.events.Deaths;
import de.mschramm.neverdie.events.InitPlayer;

public class NeverDiePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    this.getCommand("lifes").setExecutor(new LifeCommand());
    this.getServer().getPluginManager().registerEvents(new InitPlayer(), this);
    this.getServer().getPluginManager().registerEvents(new Deaths(), this);
  }

};
