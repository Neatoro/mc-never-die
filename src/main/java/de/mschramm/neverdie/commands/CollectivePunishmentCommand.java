package de.mschramm.neverdie.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import de.mschramm.neverdie.repositories.PlayerRepository;

public class CollectivePunishmentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PlayerRepository repository = PlayerRepository.getInstance();
        repository.punishPlayers();
        return true;
    }

}
