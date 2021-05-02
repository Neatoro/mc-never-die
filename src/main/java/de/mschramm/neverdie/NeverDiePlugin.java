package de.mschramm.neverdie;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.mschramm.neverdie.commands.LifeCommand;
import de.mschramm.neverdie.commands.StartCommand;
import de.mschramm.neverdie.database.SchemaProvider;
import de.mschramm.neverdie.events.Damage;
import de.mschramm.neverdie.events.Deaths;
import de.mschramm.neverdie.events.Displays;
import de.mschramm.neverdie.events.InitPlayer;
import de.mschramm.neverdie.events.Spectator;

public class NeverDiePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            SchemaProvider.initializeSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.registerCommands();
        this.registerEventHandlers();

        for (Player player : this.getServer().getOnlinePlayers()) {
            InitPlayer.initializePlayer(player);
        }
    }

    private void registerCommands() {
        this.getCommand("lifes").setExecutor(new LifeCommand());
        this.getCommand("startneverdie").setExecutor(new StartCommand());
    }

    private void registerEventHandlers() {
        this.getServer().getPluginManager().registerEvents(new InitPlayer(), this);
        this.getServer().getPluginManager().registerEvents(new Deaths(), this);
        this.getServer().getPluginManager().registerEvents(new Displays(), this);
        this.getServer().getPluginManager().registerEvents(new Spectator(), this);
        this.getServer().getPluginManager().registerEvents(new Damage(), this);
    }

};
