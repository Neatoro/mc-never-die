package de.mschramm.neverdie.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mschramm.neverdie.repository.LifeRepository;

public class LifeCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      LifeRepository repository = LifeRepository.getInstance();
      player.sendMessage("Remaining Lifes: " + repository.getLifesForPlayer(player));
    }
    return true;
  }

}
